create table if not exists employee
(
    first_name    varchar   not null,
    last_name     varchar   not null,
    department_id bigint    not null,
    job_title     varchar   not null,
    gender        varchar   not null,
    date_of_birth date      not null,
    id            bigserial not null constraint employee_pk primary key
);

alter table employee owner to postgres;

create unique index employee_id_uindex on employee (id);