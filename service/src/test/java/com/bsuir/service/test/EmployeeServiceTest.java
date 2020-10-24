package com.bsuir.service.test;

import com.bsuir.dao.inter.EmployeeDao;
import com.bsuir.model.Employee;
import com.bsuir.service.inter.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

@SpringBootTest(classes = EmployeeServiceConfiguration.class)
class EmployeeServiceTest {

    private static final Long EMPLOYEE_ID = 1L;

    @MockBean
    @Qualifier("jpaDao")
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testGetAllEmployees() {
        employeeService.getAllEmployees();
        Mockito.verify(employeeDao, Mockito.times(1)).getAll();
    }

    @Test
    public void testGetEmployee() {
        employeeService.getEmployee(EMPLOYEE_ID);
        Mockito.verify(employeeDao, Mockito.times(1)).get(EMPLOYEE_ID);
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee(
                "Vova",
                "Ford",
                5L,
                "Lawyer",
                "Male",
                LocalDate.of(1975, 2, 16)
        );
        employeeService.createEmployee(employee);
        Mockito.verify(employeeDao, Mockito.times(1)).create(employee);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = Mockito.mock(Employee.class);
        employeeService.updateEmployee(employee, EMPLOYEE_ID);
        Mockito.verify(employee).setId(EMPLOYEE_ID);
        Mockito.verify(employeeDao, Mockito.times(1)).update(employee);
    }

    @Test
    public void testDeleteEmployees() {
        employeeService.deleteEmployeeById(EMPLOYEE_ID);
        Mockito.verify(employeeDao, Mockito.times(1)).delete(EMPLOYEE_ID);
    }
}