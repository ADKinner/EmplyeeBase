package com.bsuir.dao.impl.jdbc;

import com.bsuir.dao.exception.EmployeeNotFoundException;
import com.bsuir.dao.inter.EmployeeDao;
import com.bsuir.dao.mapper.EmployeeMapper;
import com.bsuir.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import static com.bsuir.dao.parameter.JdbcSqlStatement.*;
import static com.bsuir.dao.parameter.ResultSetLabels.ID_LABEL;

@Repository("jdbcDao")
public class SpringJdbcEmployeeDaoImpl implements EmployeeDao {

    private static final Logger logger = LoggerFactory.getLogger(SpringJdbcEmployeeDaoImpl.class);

    private final EmployeeMapper employeeMapper = EmployeeMapper.getInstance();
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Employee get(Long id) {
        try {
            Employee employee = jdbcTemplate.queryForObject(SQL_GET_EMPLOYEE, new Object[]{id}, employeeMapper);
            logger.info("Successfully get employee with id: " + id);
            return employee;
        } catch (DataAccessException ex) {
            logger.warn("Try to get not existing employee (id:" + id + ")");
            throw new EmployeeNotFoundException(id);
        }
    }

    @Override
    public List<Employee> getAll() {
        logger.info("Successfully get all employees");
        return jdbcTemplate.query(SQL_GET_ALL_EMPLOYEES, employeeMapper);
    }

    @Override
    public Employee create(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setLong(3, employee.getDepartmentId());
            ps.setString(4, employee.getJobTitle());
            ps.setString(5, employee.getGender());
            ps.setDate(6, Date.valueOf(employee.getDateOfBirth()));
            return ps;
        }, keyHolder);
        logger.info("Successfully create employee");
        employee.setId((Long) keyHolder.getKeyList().get(keyHolder.getKeyList().size() - 1).get(ID_LABEL));
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setLong(3, employee.getDepartmentId());
            ps.setString(4, employee.getJobTitle());
            ps.setString(5, employee.getGender());
            ps.setDate(6, Date.valueOf(employee.getDateOfBirth()));
            ps.setLong(7, employee.getId());
            return ps;
        }, keyHolder);
        if (keyHolder.getKeyList().isEmpty()) {
            logger.warn("Try to update not existing employee (id:" + employee.getId() + ")");
            throw new EmployeeNotFoundException(employee.getId());
        } else {
            logger.info("Successfully update employee with id: " + employee.getId());
            return get((Long) keyHolder.getKeyList().get(keyHolder.getKeyList().size() - 1).get(ID_LABEL));
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_EMPLOYEE, id);
        logger.info("Successfully delete employee with id: " + id);
    }
}