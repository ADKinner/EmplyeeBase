package com.bsuir.service.inter;

import com.bsuir.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee getEmployee(Long id);

    List<Employee> getAllEmployees();

    void deleteEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee, Long id);
}
