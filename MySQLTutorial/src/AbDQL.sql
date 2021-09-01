# 基础查询 ======================================================================================
/*
 语法：
 select 查询内容（可多个）:表中字段，常量值，表达式，函数
 from 表名;

 注意：查询表是一个虚拟的表格
 */

USE myemployees;

# 查询表中的单个字段（字段可选加着重号，用以区分字段名和关键字)
SELECT `last_name`
FROM employees;
# 查询表中的多个字段
SELECT last_name, salary, email
FROM employees;
# 查询表中的所有字段
SELECT *
FROM employees;

#查询常量值
SELECT 100;
SELECT 'John';

#查询表达式
SELECT 100 % 98;

#查询函数
SELECT VERSION();

#起别名
/*
 1.便于理解
 2.如果要查询的字段有重名的情况，使用别名可以区分开来
 */
SELECT 100 % 98 AS Result;
SELECT last_name AS Nachname, first_name AS Vorname
FROM employees; # AS
SELECT last_name Nachname, first_name Vorname
FROM employees; # 空格
SELECT salary AS "out put"
FROM employees;
# 带空格的别名用双引号


#去重
SELECT DISTINCT department_id
FROM employees;

#拼接
/*
 mysql中的+号仅有一个功能：运算符
 SELECT 100+90; #加法运算
 SELECT '123'+90; #一方为字符型，试图将字符型转换为数值型,转换失败则将字符型转成0
 SELECT null+90; #只要其中一方为null，则结果一直是null
 */
SELECT CONCAT(first_name, last_name) AS Name
FROM employees;

# 条件查询 ======================================================================================
/*
 语法：
 SELECT 查询列表
 FROM 表明
 WHERE 筛选条件;
 分类：
 1.按条件表达式筛选 （条件表达式:>,<,=,<>,>=,<=）
 2.按逻辑表达式筛选 （逻辑运算符：AND OR NOT)
 3.模糊查询 （LIKE,BETWEEN AND, IN, IS NULL）
 */
#按条件表达式筛选
SELECT *
FROM employees
WHERE salary > 12000;
SELECT last_name, department_id
FROM employees
WHERE department_id <> 90;

#按逻辑表达式筛选
SELECT last_name, salary, commission_pct
FROM employees
WHERE salary >= 10000
  AND salary <= 20000;

#模糊查询
/*
 LIKE：
 一般和通配符搭配使用，通配符：
 % 任意多个字符，包含0个字符
 _ 任意单个字符
 */
SELECT *
FROM employees
WHERE last_name LIKE '%a%';
SELECT last_name, salary
FROM employees
WHERE last_name LIKE '__n_l%';
SELECT last_name
FROM employees
WHERE last_name LIKE '__\_%'; #转义
SELECT last_name
FROM employees
WHERE last_name LIKE '__$_%' ESCAPE '$';
#转义（*）
/*
 BETWEEN AND
 包含两临界值，不能颠倒，>=左值且<=右值
 */
SELECT *
FROM employees
WHERE employee_id BETWEEN 100 AND 120;
/*
 IN
 判断某字段的值是否属于in列表中的某一项
 IN列表的值类型必须一致或兼容
 不支持通配符
 */
SELECT last_name, job_id
FROM employees
WHERE job_id IN ('IT_PROT', 'AD_VP', 'AD_PRES');
/*
 IS (NOT) NULL
 */
SELECT last_name, commission_pct
FROM employees
WHERE commission_pct IS NULL;
SELECT last_name, commission_pct
FROM employees
WHERE commission_pct IS NOT NULL;
/*
 安全等与<=>
 既可以判断NULL值，又可以判断普通的数值
 */
SELECT last_name, commission_pct
FROM employees
WHERE commission_pct <=> NULL;
SELECT last_name, commission_pct
FROM employees
WHERE salary <=> 12000;

# 排序查询 ======================================================================================
/*
 语法：
 SELECT 查询列表
 FROM 表
 [WHERE 筛选条件]
 ORDER BY 排序列表 [ASC/DESC] (默认升序)
 注意：ORDER BY一般放在最后，LIMIT之前
 */
SELECT *
FROM employees
ORDER BY salary DESC;
SELECT *
FROM employees
ORDER BY salary;
#按别名排序
SELECT *, salary * 12 * (1 + IFNULL(commission_pct, 0)) AS YearSalary
FROM employees
ORDER BY YearSalary DESC;
#按函数排序
SELECT LENGTH(last_name) AS NameLength, last_name, salary
FROM employees
ORDER BY LENGTH(last_name) DESC;
#按多个字段排序
SELECT *
FROM employees
ORDER BY salary ASC, employee_id DESC;

# 常见函数 ======================================================================================
/*
 语法：
 SELECT function(parameters) [FROM table];
 单行函数：（R->R）CONCAT,LENGTH,IFNULL
 分组函数：统计(R^N->R)
 */

#单行函数--------------------------------------------
#字符函数
#1.LENGTH（）(获取参数值的字节个数（不是字符个数！！）)
SELECT LENGTH('John');
#2.CONCATE（）拼接字符串
SELECT CONCAT(last_name, '_', first_name) AS Name
FROM employees;
#3.UPPER,LOWER
SELECT UPPER('John');
SELECT LOWER('JOHN');
SELECT CONCAT(UPPER(last_name), LOWER(first_name)) AS Name
FROM employees;
#4. SUBSTR,SUBSTRING
SELECT SUBSTR('123456789', 7) AS out_put; #索引从1开始，截取第七个及其之后的字符串子串
SELECT SUBSTR('123456789', 2, 3) AS out_put;
#截取从第二个开始的三个字符
#5. INSTR
SELECT INSTR('123456789', '678') AS out_put;
#获取子串第一次出现的第一个字符索引，找不到返回0
#6. TRIM
SELECT TRIM('    hello     ') AS out_put; #去除前后空格
SELECT TRIM('a' FROM 'aaaaaheaaaalloaaaaa') AS out_put;
#去除前后a
#7. LPAD,RPAD
SELECT LPAD('hello', 10, '*') AS out_put; # 用指定的字符实现左填充(截断）指定长度
SELECT RPAD('hello', 10, '*') AS out_put;
# 用指定的字符实现右填充(截断）指定长度
#8. REPLACE
SELECT REPLACE('abcabcabcabcHELLOabcabc', 'abc', 'Hello') AS out_put;
#替换

#数学函数
#1.ROUND
SELECT ROUND(1.65); #四舍五入
SELECT ROUND(-1.65); #先取绝对值，四舍五入，再加上正负号
SELECT ROUND(1.567, 2);
#保留两位小数
#2.SEIL，FLOOR
SELECT CEIL(1.47); #向上取整
SELECT FLOOR(1.56);
#向下取整
#3. TRUNCATE
SELECT TRUNCATE(1.572378, 1);
#截断
#4. MOD
SELECT MOD(10, 3);
#取余，正负看被除数（与JAVA一致）（MOD(a,b)=a-a/b*b）

#日期函数
#1.NOW
SELECT NOW();
#返回系统日期与时间
#2. CURDATE
SELECT CURDATE();
#返回日期，不包含时间
#4. CURTIME
SELECT CURTIME();
#返回时间，不包含日期
#5. YEAR,MONTH,DAY,HOUR,MINUTE,SECOND
SELECT YEAR(NOW()); #获取年
SELECT YEAR('1998-1-1');
SELECT MONTHNAME(NOW());
#6. STR_TO_DATE
/*
 %Y 四位年份
 %y 两位年份
 %m 补零月份
 %c 不补零月份
 %d 补零日
 %H 24小时
 %h 12小时
 %i 补零分钟
 %s 补零秒
 */
SELECT STR_TO_DATE('1998-3-2', '%Y-%m-%d');
#通过指定格式将字符转化为日期
#7. DATE_FORMAT
SELECT DATE_FORMAT(NOW(), '%yYear%mMonth%dDAY') AS out_put;
#将日期转化为指定格式

#其他函数
SELECT VERSION(); #查看当前版本
SELECT DATABASE(); #查看当前库
SELECT USER();
#查看当前用户

#流程控制函数
#1.IF
SELECT IF(10 > 5, 'yes', 'no');
#2.CASE
# 用法一：相当于Java中switch
SELECT salary     originSalary,
       department_id,
       CASE department_id
           WHEN 30 THEN salary * 1.1
           WHEN 40 THEN salary * 1.2
           WHEN 50 THEN salary * 1.3
           ELSE salary
           END AS newSalary
FROM employees;
# 用法二：相当于多重IF
SELECT salary,
       CASE
           WHEN salary > 20000 THEN 'A'
           WHEN salary > 15000 THEN 'B'
           WHEN salary > 10000 THEN 'C'
           ELSE 'D'
           END AS salaryLevel
FROM employees;

#分组函数--------------------------------------------
#1.SUM
SELECT SUM(salary)
FROM employees;
#2.AVG
SELECT AVG(salary)
FROM employees;
#3.MAX
SELECT MAX(salary)
FROM employees;
#4.MIN
SELECT MIN(salary)
FROM employees;
#5.COUNT
SELECT COUNT(salary)
FROM employees;
#非空个数

# SUM,AVG主要支持数值型，MAX,MIN,COUNT 支持任何类型，所有函数都忽略NULL
# 可以和DISTINCT搭配
SELECT SUM(DISTINCT salary)
FROM employees;
SELECT COUNT(DISTINCT salary)
FROM employees;
# COUNT 的三种用法
SELECT COUNT(salary)
FROM employees; #忽略NULL
SELECT COUNT(*)
FROM employees; #统计总行数
SELECT COUNT(1)
FROM employees;
#统计总行数
# 和分组函数一同查询的字段要求是group by后的字段

#分组查询 ======================================================================================
/*
 SELECT column,group_function(column)
 FROM table
 [WHERE condition]
 [GROUP BY group_by_expression]
 [ORDER BY column]
 查询列表必须特殊，要求是分组函数和group by后出现的字段
 1.分组查询中的筛选条件分为两类：
                数据源         位置                  关键字
    分组前筛选    原始表         group by子句的前面     where
    分组后筛选    分组后的结果集  group by子句的后面     having
    分组函数做条件肯定放在having子句中
 2.能用分组前筛选的，就优先考虑使用分组前筛选
 */
SELECT MAX(salary), job_id
FROM employees
GROUP BY job_id;
SELECT COUNT(*), location_id
FROM departments
GROUP BY location_id;

#添加分组前的筛选
SELECT AVG(salary), department_id
FROM employees
WHERE email LIKE '%a%'
GROUP BY department_id;

SELECT MAX(salary), manager_id
FROM employees
WHERE commission_pct IS NOT NULL
GROUP BY manager_id;

# 添加分组后的筛选
SELECT COUNT(*), department_id
FROM employees
GROUP BY department_id
HAVING COUNT(*) > 2;

SELECT MAX(salary), job_id
FROM employees
WHERE commission_pct IS NOT NULL
GROUP BY job_id
HAVING MAX(salary) > 12000;

SELECT MIN(salary), manager_id
FROM employees
WHERE manager_id > 102
GROUP BY manager_id
HAVING MIN(salary) > 5000;

#按表达式或函数分组
SELECT COUNT(*) AS c, LENGTH(last_name) AS len_name
FROM employees
GROUP BY len_name #GROUP BY 后可用别名
HAVING c > 5;
# HAVING 后可用别名

#按多个字段分组
SELECT AVG(salary), department_id, job_id
FROM employees
GROUP BY department_id, job_id;

#添加排序
SELECT AVG(salary), department_id, job_id
FROM employees
GROUP BY department_id, job_id
HAVING AVG(salary > 10000)
ORDER BY AVG(salary) DESC;
#可用别名

#连接查询 ======================================================================================
USE girls;

SELECT name, boyName
from boys,
     beauty;
#两表笛卡尔乘积


/*
 sql92标准：仅支持内连接
 sql99标准：
 内连接（*）：等值连接，非等值连接，自连接
 外连接：左外连接（*），右外连接（*），全外连接
 交叉连接（*）
 （*）：MySQL支持
 */
#sql92标准：等值连接
SELECT g.name, b.boyName
from boys AS b, #为表起别名,如果为表其别名就不能用原来的表名去限定
     beauty AS g
WHERE g.boyfriend_id = b.id;
#添加有效连接
# 还可以加筛选，分组，排序，多表连接

USE myemployees;
#sql92标准：非等值连接
SELECT salary, grade_level
FROM employees e,
     job_grades g
WHERE salary BETWEEN g.lowest_sal AND g.highest_sal;

#sql92标准：自连接
SELECT e.employee_id, e.last_name, m.employee_id, m.last_name
FROM employees e,
     employees m
WHERE e.manager_id = m.employee_id;

#sql99标准：(推荐使用)
/*
 SELECT 查询列表
 FROM 表1 别名 [连接类型]
 JOIN 表2 别名
 ON 连接条件
 */
#内连接-等值连接
SELECT last_name, department_name
FROM employees e
         INNER JOIN departments d
                    ON e.department_id = d.department_id;

SELECT last_name, job_title
FROM employees e
         JOIN jobs j # INNER 可省略
              ON e.job_id = j.job_id
WHERE e.last_name LIKE '%e%';

SELECT city, COUNT(*)
FROM departments d
         INNER JOIN locations l
                    ON d.location_id = l.location_id
GROUP BY city
HAVING COUNT(*) > 3;

SELECT COUNT(*), department_name
FROM employees e
         INNER JOIN departments d
                    ON e.department_id = d.department_id
GROUP BY department_name
HAVING COUNT(*) > 3
ORDER BY COUNT(*) DESC;

SELECT last_name, department_name, job_title
FROM employees e
         INNER JOIN departments d ON e.department_id = d.department_id
         INNER JOIN jobs j ON e.job_id = j.job_id
ORDER BY department_name DESC;

#内连接-非等值连接
SELECT salary, grade_level
FROM employees e
         JOIN job_grades g
              ON e.salary BETWEEN g.lowest_sal AND g.highest_sal;

#自连接
SELECT e.last_name, m.last_name
FROM employees e
         JOIN employees m
              ON e.manager_id = m.employee_id
WHERE e.last_name LIKE '%k%';

#外连接:查询结果为主表中的所有记录，如果从表中有和它匹配的，则显示匹配的值，如果没有，则显示null
# 左外连接：left左边的是主表
# 右外连接：right join右边的是主表
# 全外连接：内连接结果+表一有但表二没有+表二有但表一没有（MySQL不支持）
USE girls;

SELECT b.name, bo.*
FROM beauty b
         LEFT OUTER JOIN boys bo
                         ON b.boyfriend_id = bo.id
WHERE bo.id IS NULL;

#交叉连接(笛卡尔乘积）
SELECT b.*, bo.*
FROM beauty b
         CROSS JOIN boys bo;

#子查询 ======================================================================================
/*
 出现在其他语句中的SELECT语句，称为子查询或内查询
 外部的查询语句称为主查询或外查询
 分类：
 按子查询出现的位置：
    SELECT后面：仅仅支持标量子查询
    FROM后面：支持表子查询
    WHERE或HAVING后面：标量子查询（*），列子查询（*），行子查询（较少使用）
    EXISTS后面（相关子查询）：表子查询
 按结果集的行列数不同：
    标量子查询（结果集只有一行一列）
    列子查询（结果集只有一列多行）
    行子查询（结果集有一行多列或多行）
    表子查询（结果集一般为多行多列）
 */

# WHERE或HAVING后面--------------------------------------------
/*
1.子查询放在小括号内
2.子查询一般放在条件的右侧
3.标量子查询，一般搭配着单行操作符使用（>,<,>=,<=,=,<>）
4.列子查询，一般搭配着多行操作符使用（in,any/some,all）
5.子查询的执行优先于主查询执行
 */

# 标量子查询
SELECT *
FROM employees
WHERE salary > (SELECT salary
                FROM employees
                WHERE last_name = 'Abel'
);

SELECT last_name, job_id, salary
FROM employees
WHERE job_id = (
    SELECT job_id
    FROM employees
    WHERE employee_id = 141
)
  AND salary > (
    SELECT salary
    FROM employees
    WHERE employee_id = 143
);

SELECT last_name, job_id, salary
FROM employees
WHERE salary = (
    SELECT MIN(salary)
    FROM employees
);

SELECT MIN(salary), department_id
FROM employees
GROUP BY department_id
HAVING MIN(salary) > (
    SELECT MIN(salary)
    FROM employees
    WHERE department_id = 50
);

# 列子查询（多行子查询）
SELECT last_name
FROM employees
WHERE department_id IN (
    SELECT DISTINCT department_id
    FROM departments
    WHERE location_id IN (1400, 1700)
);

SELECT last_name, employee_id, job_id, salary
FROM employees
WHERE salary < ANY (
    SELECT DISTINCT salary
    FROM employees
    WHERE job_id = 'IT_PROG'
)
  AND job_id <> 'IT_PROG';
#等价于
SELECT last_name, employee_id, job_id, salary
FROM employees
WHERE salary < (
    SELECT MAX(salary)
    FROM employees
    WHERE job_id = 'IT_PROG'
)
  AND job_id <> 'IT_PROG';

SELECT last_name, employee_id, job_id, salary
FROM employees
WHERE salary < ALL (
    SELECT DISTINCT salary
    FROM employees
    WHERE job_id = 'IT_PROG'
)
  AND job_id <> 'IT_PROG';
#等价于
SELECT last_name, employee_id, job_id, salary
FROM employees
WHERE salary < (
    SELECT MIN(salary)
    FROM employees
    WHERE job_id = 'IT_PROG'
)
  AND job_id <> 'IT_PROG';

# 行子查询（结果集一行多列或多行多列）（使用较少）
SELECT *
FROM employees
WHERE (employee_id, salary) = (
    SELECT MIN(employee_id), MAX(salary)
    FROM employees
);

# SELECT后面--------------------------------------------
#仅支持标量子查询
SELECT d.*,
       (
           SELECT COUNT(*)
           FROM employees e
           WHERE e.department_id = d.department_id
       )
FROM departments d;

SELECT (
           SELECT department_name
           FROM departments d
                    INNER JOIN employees e ON d.department_id = e.department_id
           WHERE e.employee_id = 102
       );

# FROM后面--------------------------------------------
SELECT ag_dep.*, g.grade_level
FROM (SELECT AVG(salary) ag, department_id
      FROM employees
      GROUP BY department_id) ag_dep
         INNER JOIN job_grades g
                    ON ag_dep.ag BETWEEN lowest_sal AND highest_sal;

# EXISTS后面（相关子查询）--------------------------------------------
# exists(完成的查询语句），若有查到相关数据返回1，否则返回0
SELECT EXISTS(SELECT employee_id FROM employees);

SELECT department_name
FROM departments d
WHERE EXISTS(
              SELECT *
              FROM employees e
              WHERE d.department_id = e.department_id
          );

#分页查询 ======================================================================================
/*
 当要显示的数据，一页显示不全，需要分页提交sql请求
 语法：
 SELECT 查询列表        ->[7]
 FROM 表             ->[1]
 [
 join type join 表2  ->[2]
 on 连接条件            ->[3]
 where 筛选条件         ->[4]
 group by 分组字段      ->[5]
 having 分组后的筛选      ->[6]
 order by 排序的字段     ->[8]
 ]
 LIMIT OFFSET,SIZE;     ->[9]

 其中OFFSET是要显示条目的起始索引(起始索引从0开始)
 SIZE为要显示的条目的个数

 第page页，每页size条查询通用公式：
 LIMIT (page-1)*size,size;
 */

SELECT * FROM employees LIMIT 0,5;
SELECT * FROM employees LIMIT 5; #从第一条可以省略第一个参数
SELECT * FROM employees LIMIT 10,15; #查询第11条到第25条

#联合查询 ======================================================================================
/*
 将多条查询语句的结果合并成一个结果
 语法：
 查询语句1
 union
 查询语句2
 union
 ...
 应用场景:要查询的结果来自于多个表，且多个表没有直接的连接关系，单查询的信息一致时
 1.要求多条查询语句的查询列数是一致的
 2.要求多条查询语句的查询的每一列的类型和顺序最好一致
 3.会自动去重，不想去重使用UNION ALL代替UNION
 */
SELECT * FROM employees WHERE email LIKE '%a%'
UNION
SELECT * FROM employees WHERE department_id>90;

SELECT * FROM employees WHERE email LIKE '%a%'
UNION ALL
SELECT * FROM employees WHERE department_id>90;