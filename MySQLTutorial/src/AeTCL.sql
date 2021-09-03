/*
 TCL事务控制语言
 事务：一个或一组sql语句组成的一个执行单元，这个执行单元要么全部执行，要么全部不执行
 */

SHOW ENGINES; #查看mysql支持的存储引擎，默认innodb支持事务，而myisam，memory等不支持事务

/*
 事务的ACID属性
 Atomicity：原子性，一个事务不可再分，要么都执行，要么都不执行
 Consistency：一致性，事务必须使数据库从一个一致性状态转换到另一个一致性状态
 Isolation:隔离性，不受其他事务的干扰
 Durability持久性：事务一旦被提交，它对数据库中数据的改变就是永久性的
 */

/*
 事务的创建：
 隐式事务：事务没有明显的开始和结束的标记(比如INSERT,UPDATE,DELETE)
 显式事务：事物具有明显的开启和结束的标记（前提：必须先设置自动提交同能为禁用）
 */

SET AUTOCOMMIT = 0;
START TRANSACTION; # 可选，上一句默认开启事务
#SELECT INSERT UPDATE DELETE
#...
SAVEPOINT a; #设置保存点
# 下面的两条语句结合JDBC达到失败回滚，成功提交的效果
COMMIT; # 提交
ROLLBACK; # 回滚
ROLLBACK TO a; #回滚到保存点a

# 回滚支持DELETE 但不支持 TRUNCATE


/*
 四种事务隔离级别：
 1.READ UNCOMMITTED 读未提交数据 （脏读，不可重复读，幻读）
 2.READ COMMIT 读已提交数据（不可重复读，幻读）
 3.REPEATABLE READ 可重复读（幻读）
 4.SERIALIZABLE 串行化（）

 脏读：别人还没commit的数据读到了
 不可重复读：在我的一个事务中，对于同一个数据多次的读取得到不同结果
 幻读：我想修改的数据集合在一个事务操作过程中因其他事务而被改变

 */

SELECT @@tx_isolation; #查看当前事务隔离级别
SET SESSION TRANSACTION ISOLATION LEVEL read committed;
SET GLOBAL TRANSACTION ISOLATION LEVEL read committed;

