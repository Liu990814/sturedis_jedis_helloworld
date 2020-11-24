package pers.ljt;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * @author ljt
 * @create 2020-11-24 19:52
 */
public class Service {
    private String id;
    private Integer num;
    public Service(){

    }
    public Service(String id,int num){
        this.id = id;
        this.num = num;
    }
    //控制单元
    public void service(){
        Jedis jedis = new Jedis("127.0.0.1",6379 );
        String value = jedis.get("compid:" + id);
        //判断该值是否存在
        try {
            if (value == null){
                //不存在,创建该值
                jedis.setex("compid:"+id, 15, Long.MAX_VALUE-num + "");
            }else {
                //存在自增
                Long val = jedis.incr("compid:" + id);
                business(id,val);
            }
        } catch (JedisDataException e) {
            System.out.println("使用已经达到次数上限，请升级会员级别");
            return;
        } finally {
            jedis.close();
        }
    }
    //业务操作
    public void business(String id,long val){
        System.out.println("用户："+ id +"调用业务操作，执行第" + (-Long.MAX_VALUE + val + num) + "次");
    }
}

class MyThread extends Thread{
    Service service;
    public MyThread(){

    }
    public MyThread(String id,int num){
        service = new Service(id,num);
    }
    public void run(){
        while (true){
            service.service();
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Main{
    public static void main(String[] args) {
        MyThread myThread = new MyThread("初级用户",10);
        MyThread myThread1 = new MyThread("高级用户",20);
        MyThread myThread2 = new MyThread("中级用户",30);
        myThread.start();
        myThread1.start();
        myThread2.start();
    }
}