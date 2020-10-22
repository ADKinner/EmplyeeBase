package com.bsuir.dao.repository;

import com.bsuir.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    boolean existsById(Long id);

    Optional<Employee> findById(Long id);

    List<Employee> findAll();

    void deleteById(Long id);
}
