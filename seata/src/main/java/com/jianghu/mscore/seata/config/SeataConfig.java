package com.jianghu.mscore.seata.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value = DruidDataSourceAutoConfigure.class)})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SeataConfig  {


    /**
     * Druid data source druid data source.
     *
     * @return the druid data source
     */
    @Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    /**
     * Data source data source.
     *
     * @param druidDataSource the druid data source
     * @return the data source
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(DruidDataSource druidDataSource) {
        DataSourceProxy dataSourceProxy = new DataSourceProxy(druidDataSource);
        return dataSourceProxy;
    }

}
