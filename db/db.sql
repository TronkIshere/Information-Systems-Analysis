ALTER LOGIN sa WITH PASSWORD = '1234';
ALTER LOGIN sa ENABLE;
GO

ALTER LOGIN sa ENABLE;
GO

SELECT name, is_disabled FROM sys.sql_logins WHERE name = 'sa';

ALTER LOGIN sa WITH PASSWORD = '1234' UNLOCK;
GO

SELECT DISTINCT local_net_address, local_tcp_port 
FROM sys.dm_exec_connections;

USE master;
GO

ALTER DATABASE analysis SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO

DROP DATABASE analysis;
GO

CREATE DATABASE analysis;
GO

USE analysis;

SELECT * FROM course;
SELECT * FROM department;
SELECT * FROM department_course;
SELECT * FROM lecturer;
SELECT * FROM lecturer_course;
SELECT * FROM receipt;
SELECT * FROM semester;
SELECT * FROM student;* 
SELECT * FROM cashier;
SELECT * FROM course_offering;
SELECT * FROM student_course;
SELECT * FROM course_prerequisite;

SELECT 
    COLUMN_NAME, 
    DATA_TYPE 
FROM 
    INFORMATION_SCHEMA.COLUMNS 
WHERE 
    TABLE_NAME = 'app_user' 
    AND COLUMN_NAME = 'password';

SELECT 
    COLUMN_NAME, 
    DATA_TYPE 
FROM 
    INFORMATION_SCHEMA.COLUMNS 
WHERE 
    TABLE_NAME = 'app_role' 
    AND COLUMN_NAME = 'id';