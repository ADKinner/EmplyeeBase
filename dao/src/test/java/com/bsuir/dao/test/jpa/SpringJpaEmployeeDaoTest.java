package com.bsuir.dao.test.jpa;

import com.bsuir.dao.exception.EmployeeNotFoundException;
import com.bsuir.dao.inter.EmployeeDao;
import com.bsuir.dao.repository.EmployeeRepository;
import com.bsuir.dao.test.EmployeeDaoConfiguration;
import com.bsuir.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = EmployeeDaoConfiguration.class)
public class SpringJpaEmployeeDaoTest {

    private static final Long EMPLOYEE_ID = 1L;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    @Qualifier("jpaDao")
    private EmployeeDao employeeDao;

    @Test
    public void testGetAll() {
        employeeDao.getAll();
        Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testSuccessfulGet() {
        Mockito.when(employeeRepository.existsById(EMPLOYEE_ID)).thenReturn(true);
        employeeDao.get(EMPLOYEE_ID);
        Mockito.verify(employeeRepository, Mockito.times(1)).existsById(EMPLOYEE_ID);
        Mockito.verify(employeeRepository, Mockito.times(1)).getById(EMPLOYEE_ID);
    }

    @Test
    public void testUnsuccessfulGet() {
        Mockito.when(employeeRepository.existsById(EMPLOYEE_ID))
                .thenReturn(false)
                .thenThrow(new EmployeeNotFoundException(EMPLOYEE_ID));
        try {
            employeeDao.get(EMPLOYEE_ID);
        } catch (Exception e) {
            Assertions.assertEquals("Could not find employee with such id = " + EMPLOYEE_ID, e.getMessage());
            Mockito.verify(employeeRepository, Mockito.times(1)).existsById(EMPLOYEE_ID);
            Mockito.verify(employeeRepository, Mockito.times(0)).getOne(EMPLOYEE_ID);
        }
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = Mockito.mock(Employee.class);
        employeeDao.create(employee);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee);
    }

    @Test
    public void testSuccessfulUpdateEmployee() {
        Employee employee = Mockito.mock(Employee.class);
        Mockito.when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        employeeDao.update(employee);
        Mockito.verify(employeeRepository, Mockito.times(1)).existsById(employee.getId());
        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee);
    }

    @Test
    public void testUnsuccessfulUpdateEmployee() {
        Employee employee = Mockito.mock(Employee.class);
        Mockito.when(employeeRepository.existsById(employee.getId()))
                .thenReturn(false)
                .thenThrow(new EmployeeNotFoundException(employee.getId()));
        try {
            employeeDao.update(employee);
        } catch (Exception e) {
            Assertions.assertEquals("Could not find employee with such id = " + employee.getId(), e.getMessage());
            Mockito.verify(employeeRepository, Mockito.times(1)).existsById(employee.getId());
            Mockito.verify(employeeRepository, Mockito.times(0)).save(employee);
        }
    }

    @Test
    public void testDeleteEmployees() {
        employeeDao.delete(EMPLOYEE_ID);
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(EMPLOYEE_ID);
    }
}
