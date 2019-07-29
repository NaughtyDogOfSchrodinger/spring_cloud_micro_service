package com.jianghu.mscore.data.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.github.pagehelper.PageInterceptor;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.sql.DataSource;

import com.jianghu.mscore.data.exception.DataException;
import com.jianghu.mscore.data.interceptor.MapperDecryptInterceptor;
import com.jianghu.mscore.data.interceptor.MapperEncryptInterceptor;
import com.jianghu.mscore.data.interceptor.PerformanceInterceptor;
import com.jianghu.mscore.data.interceptor.QueryEncryptInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
@EnableTransactionManagement
@MapperScan({"com.**.**.mapper"})
public class MybatisDataSource {
    private final Logger logger = LoggerFactory.getLogger(MybatisDataSource.class);
    @Value("${spring.datasource.username}")
    private String druidUser;

    @Value("${spring.datasource.password}")
    private String druidPassword;
    @Resource
    private DataSourceProperties dataSourceProperties;
    private DruidDataSource pool;

    @Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource dataSource() {
        try {
            DataSourceProperties config = this.dataSourceProperties;
            this.pool = new DruidDataSource();
            this.pool.setDriverClassName(config.getDriverClassName());
            if (StringUtils.isEmpty(config.getUrl())) {
                throw new DataException("请配置数据库连接路径!");
            }

            if (StringUtils.isEmpty(config.getUsername())) {
                throw new DataException("请配置数据库用户!");
            }

            if (StringUtils.isEmpty(config.getPassword())) {
                throw new DataException("请配置数据库密码!");
            }

            this.pool.setUrl(config.getUrl());
            this.pool.setUsername(config.getUsername());
            this.pool.setPassword(config.getPassword());
            this.pool.setInitialSize(config.getInitialSize());
            this.pool.setMinIdle(config.getMinIdle());
            this.pool.setMaxActive(config.getMaxActive());
            this.pool.setMaxWait(config.getMaxWait());
            this.pool.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
            this.pool.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
            this.pool.setTestWhileIdle(true);
            this.pool.setTestOnBorrow(false);
            this.pool.setValidationQuery(config.getValidationQuery());
            if (config.getSupportUtf8mb4().equals(NumberUtils.INTEGER_ONE)) {
                List<String> initSqls = Lists.newArrayList();
                initSqls.add("SET NAMES utf8mb4");
                this.pool.setConnectionInitSqls(initSqls);
                this.logger.info("数据库连接支持Utf8mb4编码");
            } else {
                this.logger.info("数据库连接不支持Utf8mb4编码,如需要支持请配置supportUtf8mb4属性为1");
            }

            this.pool.setFilters("!stat,wall,log4j");
        } catch (Exception var3) {
            this.logger.error("数据库连接初始化失败!,{}", var3.getMessage());
            System.exit(-1);
        }

        return this.pool;
    }

    @PreDestroy
    public void destroy() {
        if (this.pool != null) {
            this.pool.close();
        }

    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(this.dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        if (StringUtils.isEmpty(this.dataSourceProperties.getMapperLocations())) {
            this.logger.error("请配置Mapper文件扫描目录!");
            throw new DataException("请配置Mapper文件扫描目录!");
        } else {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources(this.dataSourceProperties.getMapperLocations()));
            List<Interceptor> interceptors = Lists.newArrayList();
//            interceptors.add(new MapperEncryptInterceptor());
//            interceptors.add(new MapperDecryptInterceptor());
//            interceptors.add(new QueryEncryptInterceptor());
//            interceptors.add(new PerformanceInterceptor());
            interceptors.add(new PageInterceptor());
            Interceptor[] interceptorArray = new Interceptor[interceptors.size()];
            sqlSessionFactoryBean.setPlugins((Interceptor[])interceptors.toArray(interceptorArray));
            return sqlSessionFactoryBean.getObject();
        }
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(this.dataSource());
        transactionManager.setDefaultTimeout(5);
        return transactionManager;
    }

    /**
     * 注册一个StatViewServlet
     *
     * @return servlet registration bean
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>(
                new StatViewServlet(), "/druid/*");

        servletRegistrationBean.addInitParameter("loginUsername", druidUser);
        servletRegistrationBean.addInitParameter("loginPassword", druidPassword);
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return filter registration bean
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> druidStatFilter() {

        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>(
                new WebStatFilter());

        // 添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        // 添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}

