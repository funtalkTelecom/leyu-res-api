package com.leyu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableRedisHttpSession
public class RedisConfig extends CachingConfigurerSupport {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.max-total}")
    private int maxTotal;
    /*@Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;
    @Value("${spring.redis.password}")
    private String password;*/
    
    @Bean
    public ShardedJedisPool shardedJedisPool() {
        List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();
        jedisShardInfos.add(new JedisShardInfo(host,port));
        return new ShardedJedisPool(jedisPoolConfig(), jedisShardInfos);
    }
    
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
//      jedisPoolConfig.setMaxIdle(maxIdle);
//      jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return jedisPoolConfig;
    }
    
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory cf) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        template.setConnectionFactory(cf);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(redisSerializer);
        //key haspmap序列化
        template.setHashKeySerializer(redisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(redisSerializer);
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        //默认超时时间,单位秒
        cacheManager.setDefaultExpiration(3000);
        //根据缓存名称设置超时时间,0为不超时
        Map<String,Long> expires = new ConcurrentHashMap<String,Long>();
        cacheManager.setExpires(expires);
        return cacheManager;
    }
}