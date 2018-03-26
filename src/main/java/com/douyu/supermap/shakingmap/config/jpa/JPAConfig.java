package com.douyu.supermap.shakingmap.config.jpa;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages = "com.douyu.supermap.shakingmap.dao")
@EnableTransactionManagement
//@ConfigurationProperties(prefix="spring.datasource")
public class JPAConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    DruidDataSource druidDataSource;

    @PostConstruct
    public void init() {
        druidDataSource = new DruidDataSource();
    }

    @Bean
    public DruidDataSource dataSource() {
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(5);//初始大小
        druidDataSource.setMinIdle(5);//最小
        druidDataSource.setMaxActive(20);//最大
        druidDataSource.setMaxWait(6000);//配置获取连接等待超时的时间
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(300000);//配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setTestWhileIdle(false);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);//打开PSCache，并且指定每个连接上PSCache的大小
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return druidDataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("com.douyu.supermap.shakingmap.common.entity");
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory managerFactory){
        return new JpaTransactionManager(managerFactory);
    }

}
