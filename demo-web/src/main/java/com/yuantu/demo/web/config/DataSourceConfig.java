package com.yuantu.demo.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.spy.sea.monitor.mybatis.MybatisMonitorInterceptor;
import com.github.spy.sea.sofa.tracer.plugins.datasource.mybatis.MybatisSofaTracerInterceptor;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangjingwei
 * @date 2019/7/16 19:36
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.druid")
@MapperScan(basePackages = {"com.yuantu.plancenter.dao"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    private List<String> mapperLocations;

    private String filters;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() throws SQLException {
        DruidDataSource druid = new DruidDataSource();

        // ??????????????????
        druid.setDriverClassName(driverClassName);
        druid.setUsername(username);
        druid.setPassword(password);
        druid.setUrl(url);

        //???????????????????????????????????????
        druid.setInitialSize(initialSize);
        //?????????????????????
        druid.setMaxActive(maxActive);
        //?????????????????????
        druid.setMinIdle(minIdle);
        //???????????????????????????????????????????????????
        druid.setMaxWait(maxWait);
        //??????????????????????????????????????????????????????????????????
        druid.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        //??????????????????????????????????????????
        druid.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        //?????????????????????????????????sql
        druid.setValidationQuery(validationQuery);
        //???????????????true?????????????????????????????????????????????
        druid.setTestWhileIdle(testWhileIdle);
        //?????????????????????validationQuery????????????????????????
        druid.setTestOnBorrow(testOnBorrow);
        druid.setTestOnReturn(testOnReturn);
        //????????????preparedStatement????????????PSCache???oracle??????true???mysql??????false????????????????????????????????????false
        druid.setPoolPreparedStatements(poolPreparedStatements);
        // ??????PSCache???????????????????????????PSCache?????????
        druid.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        return druid;
    }


    /**
     * ?????????????????????????????????
     */
    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource primaryDataSource) throws SQLException {
        return new DataSourceTransactionManager(primaryDataSource);
    }

    /**
     * ??????Mybatis???????????????????????????
     */
    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource primaryDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // ???????????????bean
        sessionFactory.setDataSource(primaryDataSource);
        Resource[] resources = resolveMapperLocations(mapperLocations);
        sessionFactory.setMapperLocations(resources);

        List<Interceptor> plugins = new ArrayList<>();

        plugins.add(mybatisMonitorInterceptor());
        plugins.add(mybatisSofaTracerInterceptor());
        int size = plugins.size();
        if(size >0 ){
            sessionFactory.setPlugins(plugins.toArray(new Interceptor[size]));
        }
        return sessionFactory.getObject();
    }

    @Bean
    public MybatisSofaTracerInterceptor mybatisSofaTracerInterceptor(){
        return new MybatisSofaTracerInterceptor();
    }

    @Bean
    public MybatisMonitorInterceptor mybatisMonitorInterceptor() {
        return new MybatisMonitorInterceptor();
    }

    public Resource[] resolveMapperLocations(List<String> mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList();
        if (CollectionUtils.isNotEmpty(mapperLocations)) {


            for(int i = 0; i < mapperLocations.size(); ++i) {
                String mapperLocation = mapperLocations.get(i);

                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException var8) {
                }
            }
        }

        return (Resource[])resources.toArray(new Resource[resources.size()]);
    }
}
