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

USE analysis;
SELECT * FROM app_user;
SELECT * FROM app_role;
SELECT * FROM user_roles;
SELECT * FROM student;

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