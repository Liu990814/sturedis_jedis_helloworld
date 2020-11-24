package pers.ljt;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author ljt
 * @create 2020-11-24 19:28
 */
public class JedisTest {
    @Test
    public void testJedis(){
        //1.连接redis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //2.操作redis
        jedis.set("name", "ljt");
        String name = jedis.get("name");
        System.out.println(name);
        //3.关闭连接
        jedis.close();
    }
}
