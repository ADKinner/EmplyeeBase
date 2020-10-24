package com.bsuir.service.impl;

import com.bsuir.dao.inter.EmployeeDao;
import com.bsuir.model.Employee;
import com.bsuir.service.inter.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Qualifier("jpaDao")
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeDao.delete(id);
    }

    @Override
    public Employee getEmployee(Long id) {
        return employeeDao.get(id);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeDao.create(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee, Long id) {
        employee.setId(id);
        return employeeDao.update(employee);
    }
}
