package com.bsuir.dao.repository;

import com.bsuir.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsById(Long id);

    Employee getById(Long id);

    List<Employee> findAll();

    void deleteById(Long id);
}
