package com.bsuir.dao.impl.spring;

import com.bsuir.dao.exception.EmployeeNotFoundException;
import com.bsuir.dao.inter.EmployeeDao;
import com.bsuir.dao.repository.EmployeeRepository;
import com.bsuir.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SpringJpaEmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee get(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.orElseThrow(() -> new EmployeeNotFoundException(id));
        return optionalEmployee.get();
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            return employeeRepository.save(employee);
        } else {
            throw new EmployeeNotFoundException(employee.getId());
        }
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
