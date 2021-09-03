/*
 视图VIEW
 一种虚拟存在的表，行和列的数据来自定义试图的查询中使用的表，并且是在使用视图时动态生成的，
 只保存了sql逻辑，不保存查询结果
 */

USE myemployees;
#创建
CREATE VIEW v1
AS
SELECT first_name, last_name
FROM employees e;

CREATE VIEW v2
AS
SELECT first_name, last_name
FROM employees e;

#使用
SELECT *
FROM v1
WHERE first_name LIKE '%k%';

#修改
# 方法一
CREATE OR REPLACE VIEW v1
AS
SELECT job_id, email
FROM employees e;
# 方法二
    ALTER VIEW v1
    AS
        SELECT *
        FROM employees;

#删除
DROP VIEW v1,v2;

#查看视图
#方法一
DESC v1;
#方法二
SHOW CREATE VIEW v1;

#更新(很少这么做)
# 插入
INSERT INTO v1
VALUES ('Jan', 'Peter');
#实际上是自动转化到插入原始表

# 修改
UPDATE v1
SET last_name = 'John'
WHERE first_name = 'Jan';
#实际上是自动转化到修改原始表

#删除
DELETE
FROM v1
WHERE last_name = 'John';
