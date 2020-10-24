package com.bsuir.dao.impl.spring;

import com.bsuir.dao.exception.EmployeeNotFoundException;
import com.bsuir.dao.impl.jdbc.SpringJdbcEmployeeDaoImpl;
import com.bsuir.dao.inter.EmployeeDao;
import com.bsuir.dao.repository.EmployeeRepository;
import com.bsuir.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jpaDao")
public class SpringJpaEmployeeDaoImpl implements EmployeeDao {

    private static final Logger logger = LoggerFactory.getLogger(SpringJpaEmployeeDaoImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee get(Long id) {
        if (employeeRepository.existsById(id)) {
            logger.info("Successfully get employee with id: " + id);
            return employeeRepository.getById(id);
        } else {
            logger.warn("Try to get not existing employee (id:" + id + ")");
            throw new EmployeeNotFoundException(id);
        }
    }

    @Override
    public List<Employee> getAll() {
        logger.info("Successfully get all employees");
        return employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee) {
        logger.info("Successfully create employee");
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            logger.info("Successfully update employee with id: " + employee.getId());
            return employeeRepository.save(employee);
        } else {
            logger.warn("Try to update not existing employee (id:" + employee.getId() + ")");
            throw new EmployeeNotFoundException(employee.getId());
        }
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
        logger.info("Successfully delete employee with id: " + id);
    }
}
