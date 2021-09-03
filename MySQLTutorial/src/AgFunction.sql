/*
 变量：
 系统变量
    全局变量（每次启动初始化，可以跨会话有效，但重启后无效）
    会话变量（仅针对当前会话（连接）有效）
 自定义变量
    用户变量（针对当前会话（连接）有效）
    局部变量（仅在定义它的BEGIN END中有效）
 */

# 系统变量
/*
#查看所有系统变量
SHOW GLOBAL VARIABLES; #查看全局变量
SHOW SESSION VARIABLES; #查看会话变量（可省略SESSION）
# 查看满足条件的部分系统变量
SHOW GLOBAL VARIABLES LIKE '%char%'; #查找某变量
# 查看指定的某个系统变量的值
SELECT @@global.系统变量名; # 查看某一指定全局变量
SELECT @@SESSION.系统变量名; # 查看某一指定会话变量（可省略Session）
# 为系统变量赋值
SET global/[session] 系统变量名 = value;
SET @@global/[session.]系统变量名 = value;
 */

/*
 自定义变量
 */
#用户变量：无需限定类型
#声明/赋值
SET @user_var_1 = 1;
SET @user_var_1 := 1;
SELECT @user_var_1 := 1;

USE myemployees;
SELECT COUNT(*)
INTO @user_var_1
FROM employees;

#使用
SELECT @user_var_1;

#局部变量:需要限定类型(BEGIN END中第一句话）
/*
 语法：
 1.声明
 DECLARE 变量名 类型;
 DECLARE 变量名 类型　DEFAULT值;
 2.赋值
 方式一：通过SET或SELECT
    SET 局部变量名=值;
    SET 局部变量名:=值;
    SELECT @局部变量名:=值;
 方式二：通过SELECT INTO
    SELECT 字段 INTO 局部变量名
    FROM 表;
 3.使用
 SELECT 局部变量名;
 */

/*
 存储过程和函数
 */

#存储过程（可以有0到多个返回，适合批量插入，更新）
/*
 参数模式
 IN：该参数可以作为输入
 OUT：该参数可以作为返回值
 INOUT：该参数既可以作为输入，又可以作为输出
 */
DELIMITER $ #设置结束标记
CREATE PROCEDURE procedure_1(IN stuname VARCHAR(20), OUT res VARCHAR(20))
BEGIN
    #如果仅一句话，BEGIN，END可省略
    SET res := stuname;
END $

SET @RES := 0 $
CALL procedure_1('skdf', @RES)$

SHOW CREATE PROCEDURE procedure_1$ #查看存储过程的信息

DROP PROCEDURE procedure_1$ #删除过程

# 函数（必须有且仅有一个返回，适合处理数据后返回一个结果）
CREATE FUNCTION function_2(base INT) RETURNS INT
BEGIN
    RETURN base + 5;
end $

SELECT function_2(7)$

SHOW CREATE FUNCTION function_2$

DROP FUNCTION function_2$

/*
 分支结构
 IF(表达式1，表达式2，表达式3);
 若表达式1成立，执行表达式2，否则执行表达式3

 CASE（作为独立的语句只能放在BEGIN END中）
 CASE 变量/表达式/字段
 WHEN 要判断的值 THEN 返回的值1或语句1;
 WHEN 要判断的值 THEN 返回额值2或语句2;
 ...
 ELSE 要返回的值N或语句N;
 END CASE;

 CASE
 WHEN 要判断的条件1 THEN 返回的值1或语句1;
 WHEN 要判断的条件2 THEN 返回额值2或语句2;
 ...
 ELSE 要返回的值N或语句N;
 END CASE;

 IF（作为独立的语句只能放在BEGIN END中）
 IF 条件1 THEN 语句1;
 ELSEIF 条件2 THEN 语句2;
 ...
 [ELSE 语句N];
 END IF;
 */

/*
 循环：WHILE,LOOP,REPEAT（作为独立的语句只能放在BEGIN END中）
 iterate 类似于java中continue
 leave 类似于Java中break

 WHILE 语法：
 [标签:] WHILE 循环条件 DO
   循环体;
 END WHILE [标签];

 LOOP语法：
 [标签:] LOOP
   循环体;
 END LOOP [标签];

 REPEAT语法:
 [标签：] REPEAT
   循环体;
 UNTIL 结束循环的条件
 END REPEAT[标签]
 */