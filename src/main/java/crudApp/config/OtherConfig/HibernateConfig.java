package crudApp.config.OtherConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan("crudApp")
@EnableTransactionManagement
public class HibernateConfig {
    final Environment environment;

    public HibernateConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("db.driver")));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.pass"));
        return dataSource;
    }
    @Bean
    public Properties hibernateProperties(){
        final Properties properties = new Properties();
        properties.put( "hibernate.dialect", environment.getProperty("hib.dialect") );
        properties.put( "hibernate.hbm2ddl.auto", environment.getProperty("hib.hbm2ddl") );
        properties.put( "hibernate.show_sql", environment.getProperty("hib.show_sql") );
        properties.put( "hibernate.connection.characterEncoding", environment.getProperty("hib.characterEncoding") );
        properties.put( "hibernate.connection.CharSet", environment.getProperty("hib.CharSet") );
        properties.put( "hibernate.connection.useUnicode", environment.getProperty("hib.useUnicode") );
        return properties;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(){
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource( dataSource() );
        em.setPackagesToScan( "crudApp" );
        em.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
        em.setJpaProperties( hibernateProperties() );
        return em;
    }
    @Bean
    public PlatformTransactionManager getTransactionManager(){
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return manager;
    }
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
