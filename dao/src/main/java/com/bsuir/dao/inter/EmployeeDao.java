package com.bsuir.dao.inter;

import com.bsuir.model.Employee;

import java.util.List;

public interface EmployeeDao {

    Employee get(Long id);

    List<Employee> getAll();

    Employee create(Employee employee);

    Employee update(Employee employee);

    void delete(Long id);
}

