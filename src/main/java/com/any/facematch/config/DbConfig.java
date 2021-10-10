package com.any.facematch.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableJpaRepositories(basePackages = {"com.*"})
public class DbConfig {

//    @Bean
//    public PersonsRepository personsRepository() {
//        PersonsRepository personsRepository = new SimpleJpaRepository<PersonEntry,String>(PersonEntry.class,);
//        return personsRepository;
//    }
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String user;
//
//    @Value("${spring.datasource.driverClassName}")
//    private String driver;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Bean
//    public DriverManagerDataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, password);
//        dataSource.setDriverClassName(driver);
//        return dataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(dataSource());
//        emf.setPackagesToScan("com");
//        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        emf.setJpaProperties(additionalProperties());
//        return emf;
//    }
//
//    Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "update");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
//
//        return properties;
//    }
//
//
//    @Bean
//    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//
//        return transactionManager;
//    }

}
