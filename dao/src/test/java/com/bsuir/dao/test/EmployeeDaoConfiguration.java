package com.bsuir.dao.test;

import com.bsuir.dao.impl.jdbc.SpringJdbcEmployeeDaoImpl;
import com.bsuir.dao.impl.spring.SpringJpaEmployeeDaoImpl;
import com.bsuir.dao.inter.EmployeeDao;
import com.bsuir.model.Employee;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
@SpringBootConfiguration
@EnableAutoConfiguration
@EnableJdbcRepositories
@EnableJpaRepositories("com.bsuir.dao.repository")
@EntityScan(basePackageClasses = Employee.class)
public class EmployeeDaoConfiguration {

    @Bean
    public EmployeeDao jpaDao() {
        return new SpringJpaEmployeeDaoImpl();
    }

    @Bean
    public EmployeeDao jdbcDao() {
        return new SpringJdbcEmployeeDaoImpl();
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/Company_DB");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("7694");
        return driverManagerDataSource;
    }
}
