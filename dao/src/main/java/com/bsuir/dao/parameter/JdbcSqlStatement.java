package com.bsuir.dao.parameter;

public interface JdbcSqlStatement {

    String SQL_GET_EMPLOYEE = "SELECT * FROM employee WHERE id = ?";

    String SQL_GET_ALL_EMPLOYEES = "SELECT * FROM employee";

    String SQL_CREATE_EMPLOYEE = "INSERT INTO employee " +
            "(first_name, last_name, department_id, job_title, gender, date_of_birth) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    String SQL_UPDATE_EMPLOYEE = "UPDATE employee " +
            "SET first_name = ?, last_name = ?, department_id = ?, job_title = ?, gender = ?, date_of_birth = ? " +
            "WHERE id = ?";

    String SQL_DELETE_EMPLOYEE = "DELETE FROM employee WHERE id = ?";
}
