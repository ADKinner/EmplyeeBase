package com.bsuir.dao.test.jdbc;

import com.bsuir.dao.exception.EmployeeNotFoundException;
import com.bsuir.dao.inter.EmployeeDao;
import com.bsuir.dao.test.EmployeeDaoConfiguration;
import com.bsuir.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

@SpringBootTest(classes = EmployeeDaoConfiguration.class)
public class SpringJdbcEmployeeDaoTest {

    private static final Long EMPLOYEE_ID = 1L;

    @Qualifier("jdbcDao")
    @MockBean
    private EmployeeDao employeeDao;

    @Test
    public void testGetAll() {
        Mockito.when(employeeDao.getAll()).thenReturn(Arrays.asList(new Employee(), new Employee()));
        Assertions.assertEquals(employeeDao.getAll().size(), 2);
    }

    @Test
    public void testGet() {
        Mockito.when(employeeDao.get(EMPLOYEE_ID)).thenThrow(new EmployeeNotFoundException(EMPLOYEE_ID));
        try {
            employeeDao.get(EMPLOYEE_ID);
        } catch (Exception e) {
            Assertions.assertEquals("Could not find employee with such id = " + EMPLOYEE_ID, e.getMessage());
        }
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = Mockito.mock(Employee.class);
        employeeDao.create(employee);
        Mockito.verify(employeeDao, Mockito.times(1)).create(employee);
    }

    @Test
    public void testUnsuccessfulUpdateEmployee() {
        Employee employee = Mockito.mock(Employee.class);
        Mockito.when(employeeDao.create(employee)).thenThrow(new EmployeeNotFoundException(EMPLOYEE_ID));
        try {
            employeeDao.create(employee);
        } catch (Exception e) {
            Assertions.assertEquals("Could not find employee with such id = " + EMPLOYEE_ID, e.getMessage());
        }
    }

    @Test
    public void testDeleteEmployees() {
        employeeDao.delete(EMPLOYEE_ID);
        Mockito.verify(employeeDao, Mockito.times(1)).delete(EMPLOYEE_ID);
    }
}
