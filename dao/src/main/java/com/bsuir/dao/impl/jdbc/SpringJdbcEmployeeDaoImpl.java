package com.bsuir.dao.impl.jdbc;

import com.bsuir.dao.exception.EmployeeNotFoundException;
import com.bsuir.dao.inter.EmployeeDao;
import com.bsuir.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static com.bsuir.dao.parameter.JdbcSqlStatement.*;
import static com.bsuir.dao.parameter.ResultSetLabels.*;

@Repository
public class SpringJdbcEmployeeDaoImpl implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SpringJdbcEmployeeDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Employee get(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_EMPLOYEE, new Object[]{id}, new EmployeeMapper());
        } catch (DataAccessException ex) {
            throw new EmployeeNotFoundException(id);
        }
    }

    @Override
    public List<Employee> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL_EMPLOYEES, new EmployeeMapper());
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
            throw new EmployeeNotFoundException(employee.getId());
        } else {
            return get((Long) keyHolder.getKeyList().get(keyHolder.getKeyList().size() - 1).get(ID_LABEL));
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_EMPLOYEE, id);
    }

    private static class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Employee(
                    resultSet.getLong(ID_LABEL),
                    resultSet.getString(FIRST_NAME_LABEL),
                    resultSet.getString(LAST_NAME_LABEL),
                    resultSet.getLong(DEPARTMENT_ID_LABEL),
                    resultSet.getString(JOB_TITLE_LABEL),
                    resultSet.getString(GENDER_LABEL),
                    LocalDate.parse(resultSet.getDate(DATE_OF_BIRTH_LABEL).toString())
            );
        }
    }
}