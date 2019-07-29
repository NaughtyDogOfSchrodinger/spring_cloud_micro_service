package com.jianghu.mscore.cache.config;

import com.jianghu.mscore.cache.exception.CacheException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * redis工厂类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties({RedisProperties.class})
@Scope("singleton")
public class RedisFactory extends CachingConfigurerSupport {

    private Logger logger = LoggerFactory.getLogger(RedisFactory.class);

    @Resource
    private RedisProperties redisProperties;

    @Resource
    private JedisPoolConfig jedisPoolConfig;


    /**
     * Jedis pool连接池配置信息
     *
     * @return the jedis pool config
     * @since 2018.12.19
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        if (jedisPoolConfig == null) {
            jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(this.redisProperties.getPool().getMaxActive());
            jedisPoolConfig.setMaxIdle(this.redisProperties.getPool().getMaxIdle());
            jedisPoolConfig.setMinIdle(this.redisProperties.getPool().getMinIdle());
            jedisPoolConfig.setMaxWaitMillis((long)this.redisProperties.getPool().getMaxWaitMillis());
            jedisPoolConfig.setNumTestsPerEvictionRun(this.redisProperties.getPool().getNumTestsPerEvictionRun());
            jedisPoolConfig.setTimeBetweenEvictionRunsMillis((long)this.redisProperties.getPool().getTimeBetweenEvictionRunsMillis());
            jedisPoolConfig.setMinEvictableIdleTimeMillis((long)this.redisProperties.getPool().getMinEvictableIdleTimeMillis());
            jedisPoolConfig.setSoftMinEvictableIdleTimeMillis((long)this.redisProperties.getPool().getSoftMinEvictableIdleTimeMillis());
            jedisPoolConfig.setTestOnBorrow(this.redisProperties.getPool().getTestOnBorrow());
            jedisPoolConfig.setTestWhileIdle(this.redisProperties.getPool().getTestWhileIdle());
            jedisPoolConfig.setTestOnReturn(this.redisProperties.getPool().getTestOnReturn());
            jedisPoolConfig.setBlockWhenExhausted(this.redisProperties.getPool().getBlockWhenExhausted());
        }
        return jedisPoolConfig;
    }

    /**
     * jedis连接工厂
     *
     * @param jedisPoolConfig the jedis pool config
     * @return the jedis connection factory
     * @since 2018.12.19
     */
    @Bean
    public JedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        try {
            RedisProperties properties = this.redisProperties;
            if (StringUtils.isEmpty(properties.getHost())) {
                throw new CacheException("Failed to initialize redis connection. Please configure redis host!");
            } else if (StringUtils.isEmpty(properties.getPort())) {
                throw new CacheException("Failed to initialize redis connection. Please configure redis port!");
            } else if (StringUtils.isEmpty(properties.getPassword())) {
                throw new CacheException("Failed to initialize redis connection. Please configure redis password!");
            } else {
                this.logger.info("**** cache: Redis=> host:[{}] port:[{}] ****", properties.getHost(), properties.getPort());
                RedisStandaloneConfiguration redisStandaloneConfiguration =
                        new RedisStandaloneConfiguration();
                redisStandaloneConfiguration.setHostName(properties.getHost());
                redisStandaloneConfiguration.setPort(NumberUtils.toInt(properties.getPort()));
                redisStandaloneConfiguration.setPassword(RedisPassword.of(properties.getPassword()));
                redisStandaloneConfiguration.setDatabase(properties.getDatabase());
                JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                        (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
                jpcb.poolConfig(jedisPoolConfig);

                JedisClientConfiguration jedisClientConfiguration = jpcb.build();
                return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
            }
        } catch (Exception var4) {
            this.logger.error("**** hj-cache: {} ****", var4.getMessage());
            System.exit(-1);
            return null;
        }
    }

    /**
     * 生成redis template.
     *
     * @param factory the factory
     * @return the redis template
     * @since 2018.12.19
     */
    @Bean
    public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        template.setValueSerializer(new RedisObjectSerializer());
        template.setHashKeySerializer(jdkSerializationRedisSerializer);
        template.setHashValueSerializer(jdkSerializationRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 初始化缓存
     *
     * @param factory the factory
     * @return the cache manager
     * @since 2018.12.29
     */
    @Bean
    public CacheManager initRedisCacheManager(RedisConnectionFactory factory) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder.fromConnectionFactory(factory);
        return builder.build();
    }
}

/**
 * redis配置
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
@ConfigurationProperties(prefix = "spring.redis")
class RedisProperties {

    private String host;

    private String port;

    private String timeout;

    private String password;

    private int database = 0;

    private Pool pool;

    /**
     * 构造器
     *
     * @author hujiang.
     * @version 1.0
     * @since 2018.12.19
     */
    RedisProperties() {
    }

    /**
     * 获取连接池配置
     *
     * @return the pool
     */
    public Pool getPool() {
        return this.pool;
    }

    /**
     * 设定连接池配置
     *
     * @param pool the pool
     */
    public void setPool(Pool pool) {
        this.pool = pool;
    }

    /**
     * 获取数据库
     *
     * @return the database
     */
    public int getDatabase() {
        return this.database;
    }

    /**
     * 设定数据库
     *
     * @param database the database
     */
    public void setDatabase(int database) {
        this.database = database;
    }

    /**
     * 获取主机
     *
     * @return the host
     */
    public String getHost() {
        return this.host;
    }

    /**
     * 设定主机
     *
     * @param host the host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 获取端口
     *
     * @return the port
     */
    public String getPort() {
        return this.port;
    }

    /**
     * 设定端口
     *
     * @param port the port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * 获取超时时间
     *
     * @return the timeout
     */
    public String getTimeout() {
        return this.timeout;
    }

    /**
     * 设定超时时间
     *
     * @param timeout the timeout
     */
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    /**
     * 获取密码
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设定密码
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 连接池配置类
     *
     * @author hujiang.
     * @version 1.0
     * @since 2018.12.19
     */
    public static class Pool {
        /**
         * The Max active.
         */
        int maxActive;
        /**
         * The Max idle.
         */
        int maxIdle;
        /**
         * The Min idle.
         */
        int minIdle;
        /**
         * The Max wait millis.
         */
        int maxWaitMillis;
        /**
         * The Test on borrow.
         */
        Boolean testOnBorrow;
        /**
         * The Test while idle.
         */
        Boolean testWhileIdle;
        /**
         * The Test on return.
         */
        Boolean testOnReturn;
        /**
         * The Num tests per eviction run.
         */
        int numTestsPerEvictionRun;
        /**
         * The Time between eviction runs millis.
         */
        int timeBetweenEvictionRunsMillis;
        /**
         * The Min evictable idle time millis.
         */
        int minEvictableIdleTimeMillis;
        /**
         * The Soft min evictable idle time millis.
         */
        int softMinEvictableIdleTimeMillis;
        /**
         * The Block when exhausted.
         */
        Boolean blockWhenExhausted;

        /**
         * The type Pool.
         * Description
         *
         * @author hujiang.
         * @version 1.0
         * @since 2018.12.19
         */
        Pool() {
        }

        /**
         * Gets max active.
         *
         * @return the max active
         */
        public int getMaxActive() {
            return maxActive;
        }

        /**
         * Sets max active.
         *
         * @param maxActive the max active
         */
        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        /**
         * Gets max idle.
         *
         * @return the max idle
         */
        public int getMaxIdle() {
            return maxIdle;
        }

        /**
         * Sets max idle.
         *
         * @param maxIdle the max idle
         */
        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        /**
         * Gets min idle.
         *
         * @return the min idle
         */
        public int getMinIdle() {
            return minIdle;
        }

        /**
         * Sets min idle.
         *
         * @param minIdle the min idle
         */
        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        /**
         * Gets max wait millis.
         *
         * @return the max wait millis
         */
        public int getMaxWaitMillis() {
            return maxWaitMillis;
        }

        /**
         * Sets max wait millis.
         *
         * @param maxWaitMillis the max wait millis
         */
        public void setMaxWaitMillis(int maxWaitMillis) {
            this.maxWaitMillis = maxWaitMillis;
        }

        /**
         * Gets test on borrow.
         *
         * @return the test on borrow
         */
        public Boolean getTestOnBorrow() {
            return testOnBorrow;
        }

        /**
         * Sets test on borrow.
         *
         * @param testOnBorrow the test on borrow
         */
        public void setTestOnBorrow(Boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        /**
         * Gets test while idle.
         *
         * @return the test while idle
         */
        public Boolean getTestWhileIdle() {
            return testWhileIdle;
        }

        /**
         * Sets test while idle.
         *
         * @param testWhileIdle the test while idle
         */
        public void setTestWhileIdle(Boolean testWhileIdle) {
            this.testWhileIdle = testWhileIdle;
        }

        /**
         * Gets test on return.
         *
         * @return the test on return
         */
        public Boolean getTestOnReturn() {
            return testOnReturn;
        }

        /**
         * Sets test on return.
         *
         * @param testOnReturn the test on return
         */
        public void setTestOnReturn(Boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }

        /**
         * Gets num tests per eviction run.
         *
         * @return the num tests per eviction run
         */
        public int getNumTestsPerEvictionRun() {
            return numTestsPerEvictionRun;
        }

        /**
         * Sets num tests per eviction run.
         *
         * @param numTestsPerEvictionRun the num tests per eviction run
         */
        public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
            this.numTestsPerEvictionRun = numTestsPerEvictionRun;
        }

        /**
         * Gets time between eviction runs millis.
         *
         * @return the time between eviction runs millis
         */
        public int getTimeBetweenEvictionRunsMillis() {
            return timeBetweenEvictionRunsMillis;
        }

        /**
         * Sets time between eviction runs millis.
         *
         * @param timeBetweenEvictionRunsMillis the time between eviction runs millis
         */
        public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
            this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        }

        /**
         * Gets min evictable idle time millis.
         *
         * @return the min evictable idle time millis
         */
        public int getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        /**
         * Sets min evictable idle time millis.
         *
         * @param minEvictableIdleTimeMillis the min evictable idle time millis
         */
        public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }

        /**
         * Gets soft min evictable idle time millis.
         *
         * @return the soft min evictable idle time millis
         */
        public int getSoftMinEvictableIdleTimeMillis() {
            return softMinEvictableIdleTimeMillis;
        }

        /**
         * Sets soft min evictable idle time millis.
         *
         * @param softMinEvictableIdleTimeMillis the soft min evictable idle time millis
         */
        public void setSoftMinEvictableIdleTimeMillis(int softMinEvictableIdleTimeMillis) {
            this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
        }

        /**
         * Gets block when exhausted.
         *
         * @return the block when exhausted
         */
        public Boolean getBlockWhenExhausted() {
            return blockWhenExhausted;
        }

        /**
         * Sets block when exhausted.
         *
         * @param blockWhenExhausted the block when exhausted
         */
        public void setBlockWhenExhausted(Boolean blockWhenExhausted) {
            this.blockWhenExhausted = blockWhenExhausted;
        }
    }
}







