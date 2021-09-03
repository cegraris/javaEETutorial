/*
 DML语言(插入数据操作语言)
 插入：INSERT
 修改：UPDATE
 删除：DELETE
 */

 #1.插入语句======================================================================================
/*
 语法：
 INSERT INTO 表名(列名,...) VALUES(值1,...)
 */

# 插入方法一：经典插入(较多使用)
#`1.插入的值的类型要与列的类型一致或兼容
INSERT INTO beauty(id,name,sex,borndate,phone,photo,boyfriend_id)
VALUES(13,'唐艺昕','女','1990-4-23','1898888888',NULL,2);
#2.可以为NULL的列如何插入值?(不可一为NULL的列必须插入值)
#方式一：插入NULL
INSERT INTO beauty(id,name,sex,borndate,phone,photo,boyfriend_id)
VALUES(13,'唐艺昕','女','1990-4-23','1898888888',NULL,2);
#方式二：忽略该参数（列的顺序可以调换，但列数和值的个数必须一致）
INSERT INTO beauty(id,name,sex,phone)
VALUES(13,'唐艺昕','女','1898888888');
#3.可以省略列名，默认所有列，而且列的顺序和表中列的顺序一致
INSERT INTO beauty
VALUES(13,'唐艺昕','女','1990-4-23','1898888888',NULL,2);
#4.支持插入多行：
INSERT INTO beauty
VALUES(13,'唐艺昕','女','1990-4-23','1898888888',NULL,2)
,(14,'唐艺昕','女','1990-4-23','1898888888',NULL,2)
,(15,'唐艺昕','女','1990-4-23','1898888888',NULL,2);
#5.支持子查询
INSERT INTO beauty(id,name,phone)
SELECT 26,'宋茜','11809866';

# 插入方法二
# 不支持插入多行，不支持子查询
INSERT INTO beauty
SET id=19,NAME='刘涛',phone='999';


#2.修改语句======================================================================================
/*
 1.修改单表记录
 语法:
 UPDATE 表名
 SET 列=新值,列=新值,...
 WHERE 筛选条件;
 2.修改多表的记录
 语法：
 SQL92:
 UPDATE 表1 别名，表2 别名
 SET 列=值,...
 WHERE 连接条件
 AND 筛选条件
 SQL99:
 UPDATE 表1 别名，
 INNER/LEFT/RIGHT JOIN 表2 别名
 ON 连接条件
 SET 列=值,...
 WHERE 筛选条件
 */

#修改单表记录
UPDATE beauty
SET phone='1389988899'
WHERE NAME LIKE '唐%';

UPDATE boys
SET boyName='张飞',usercp=10
WHERE id=2;

#修改多表的记录
UPDATE boys bo
INNER JOIN beauty b ON bo.id=b.boyfriend_id
SET b.phone=114
WHERE bo.boyName='张无忌';

#3.删除语句======================================================================================
/*
 方式一：DELETE
 （有返回值，自增长列会从断点开始，可以回滚）
 语法：
 1.单表的删除：
 DELETE FROM 表名 WHERE 筛选条件;
 2.多表的删除：
 SQL92:
 DELETE 表1的别名，表2的别名 （取决于想删哪个表中的记录）
 FROM 表1 别名，表2 别名
 WHERE 连接条件
 AND 筛选条件
 SQL99：
 DELETE 表1的别名，表2的别名 （取决于想删哪个表中的记录）
 FROM 表1 别名
 INNER/LEFT/RIGHT JOIN 表2 别名 ON 连接条件
 WHERE 筛选条件
 方式二：TRUNCATE (整个表内容清空,效率高些)
 （无返回值，自增长列会从1开始，不能回滚）
 语法：
 TRUNCATE TABLE 表名;
 */
# DELETE删单表
DELETE FROM beauty WHERE phone LIKE '%9';
# DELETE删多表
DELETE b
FROM beauty b
INNER JOIN boys bo ON b.boyfriend_id=bo.id
WHERE bo.boyName = '张无忌';
#TRUNCATE
TRUNCATE TABLE boys;