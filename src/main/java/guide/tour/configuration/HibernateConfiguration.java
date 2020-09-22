package guide.tour.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
 
/**
 * Hibernate configuration class.
 *
 * @author Anna Likhachova
 * @version 1.0
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"guide.tour.dao"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfiguration {
 
    @Autowired
    private Environment environment;
     
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("database.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("database.url"));
        dataSource.setUsername(environment.getRequiredProperty("database.username"));
        dataSource.setPassword(environment.getRequiredProperty("database.password"));
        return dataSource;
    }

    /**
     * The method that configures the entity manager factory
     * */
    @Bean
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("guide.tour.model", "guide.tour.neural.network");

        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", environment.getRequiredProperty("hibernate.temp.use_jdbc_metadata_defaults"));

        entityManagerFactoryBean.setJpaProperties(properties);
        return entityManagerFactoryBean;
    }

    /**
     * The method that configures the transaction manager
     * */
    @Bean
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        return localContainerEntityManagerFactoryBean().getObject();
    }

    @Bean
    public EntityManager entityManager(){
        return entityManagerFactory().createEntityManager();
    }

}
