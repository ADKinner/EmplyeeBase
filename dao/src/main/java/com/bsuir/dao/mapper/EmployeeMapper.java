package com.bsuir.dao.mapper;

import com.bsuir.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.bsuir.dao.parameter.ResultSetLabels.*;

public class EmployeeMapper implements RowMapper<Employee> {

    private static EmployeeMapper employeeMapper;

    public static EmployeeMapper getInstance() {
        if (employeeMapper == null) {
            synchronized (EmployeeMapper.class) {
                if (employeeMapper == null) {
                    employeeMapper = new EmployeeMapper();
                }
            }
        }
        return employeeMapper;
    }

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
