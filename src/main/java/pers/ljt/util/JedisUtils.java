package pers.ljt.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * @author ljt
 * @create 2020-11-25 14:49
 */
public class JedisUtils {
    private static JedisPool jp = null;
    private static String host = null;
    private static Integer port = null;
    private static Integer maxTotal = null;
    private static Integer maxIdle = null;
    static {
        ResourceBundle rb = ResourceBundle.getBundle("redis");
        JedisPoolConfig jpc = new JedisPoolConfig();
        host = rb.getString("redis.host");
        port = Integer.parseInt(rb.getString("redis.port"));
        maxTotal = Integer.parseInt(rb.getString("redis.maxTotal"));
        maxIdle = Integer.parseInt(rb.getString("redis.maxIdle"));
        jpc.setMaxTotal(maxTotal);
        jpc.setMaxIdle(maxIdle);
        jp = new JedisPool(jpc,host ,port);
    }
    public static Jedis getJedis(){
        return jp.getResource();
    }

    public static void main(String[] args) {
        JedisUtils.getJedis();
    }
}
