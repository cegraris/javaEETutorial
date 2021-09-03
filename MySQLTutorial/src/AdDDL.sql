/*
 DDL 数据定义语言
 库和表的管理 （创建，修改，删除）
 CREATE,ALTER,DROP
 */
#1.库的管理======================================================================================
# 库的创建
CREATE DATABASE IF NOT EXISTS books;
# 库的修改
ALTER DATABASE books CHARACTER SET gbk;
#修改库的字符集
# 库的删除
DROP DATABASE IF EXISTS books;

#2.表的管理======================================================================================
#表的创建
/*
 语法
 CREATE talbe 表名(
       列名 列的类型 [(长度) 约束],
       列名 列的类型 [(长度) 约束],
       ...
       列名 列的类型 [(长度) 约束]
       [表级约束]
 );

 常见的数据类型：
 数值型：
   整形：Tinyint(1byte),Smallint(2byte),Mediumint(3byte),Int/Integer(4byte),Bigint(8byte)
       t1 INT <-默认有符号
       t2 INT UNSIGNED <-无符号
       如果插入的数值超出了范围，会报异常，然后插入临界值
       t1 INT(7) ZEROFILL
       如果不设置长度，会用默认长度，主要用于显示的宽度，ZEROFILL会用零填充高位且默认无符号
   小数：
       定点数：DEC/DECIMAL(M,D)(M+2byte)
       浮点数: float(M,D)(4byte),double(M,D)(8byte)
       M：总位数，D：小时位数
       如果超过插入临界值
       M,D可省略，DECIMAL是（10，0），FLOAT和DOUBLE依据插入的数值的精度决定
       定点型的精度较高，如果要求插入数值的精度较高如货币运算则考虑使用
   字符型
       较短的文本：
           char(M)     (M:0~255)#固定长度字符，存储空间大，效率高，可省略M，默认为1
           varchar(M)      (M:0~65535)#可变长度的字符，存储空间小，效率低，不可省略M
           存最多M个字符数
           binary和varbinary用于保存较短的二进制（使用较少）
           ENUM('a','b',...) 枚举类，不区分大小写，插入时可以选一个
           SET('a','b',...) 集合类，不区分大小写，插入时可选多个 例如：'a,b'
       较长的文本：text,blob（较大的二进制）
   日期型：
         date 年月日（4byte）
         datetime 年月日和时间（8byte）不受时区影响 （1000-9999）
         timestamp 毫秒（4byte）(*)时区兼容 （1970-2038）
               SET time_zone = '+9:00' 设置时间到东九区
         time 仅时间（3byte）
         year 仅年（1byte）

 常见约束
   NOT NULL 非空
   DEFAULT 默认
   PRIMARY KEY 主键（唯一非空）（至多一个）
   UNIQUE 唯一(可以是空)
   CHECK 检查（添加需满足的条件）（MySQL不支持）
   REFERENCE 外键，用于限制两个表的关系，用于保证该字段的值必须来自于主表的关联列的值（MySQL列级不支持，表级支持）
        1.要求在从表设置外键关系
        2.从表的外键列的类型和主表的关联列的类型要求一致或兼容，名称无要求
        3.主表的关联列必须是一个key（一般是主键或唯一）
        4.插入数据时应该先插入主表，再插入从表，删除时先删除从表，再删除主表
   列级约束：六大约束语法上都支持，但是外键没有效果
   表级约束：除了非空、默认，其他的都支持([CONSTRAINT 约束名] 约束类型（字段名）)(默认约束名为字段名)
 */
CREATE TABLE IF NOT EXISTS book
(
    id          INT PRIMARY KEY AUTO_INCREMENT, # 自增长，插入时该处参数填NULL即可(
                # 自增长可以与主键，唯一，外键搭配，且一个表最多一个，标识列类型只能是数值型)
                #可以通过手动插入值设置起始值，通过SET auto_increment_increment = 3; #设置自增长的步长
    bname       VARCHAR(20) NOT NULL DEFAULT 'No NAME', #最多20个字符
    price       DOUBLE               DEFAULT 0,
    authorID    INT,
    publishDate DATETIME,
    gender      CHAR(1),

    CONSTRAINT pk PRIMARY KEY (id, bname),              #组合主键，由两个参数共同确定一个唯一的主键
    CONSTRAINT uq UNIQUE (bname, price),                #组合唯一键
    CONSTRAINT ck CHECK (gender = '男' OR gender = '女'),
    CONSTRAINT fk_authorID FOREIGN KEY (authorID) REFERENCEs beauty (id)
);

SHOW  INDEX FROM book; #查看表的key（主键，唯一，外键）

SET auto_increment_increment = 3; #设置自增长的步长
SHOW VARIABLES LIKE '%auto_increment%';


#表的修改(列名，类型或约束，添加新列，删除列，修改表名)
#列名
ALTER TABLE book
    CHANGE COLUMN publishdate pubDate DATETIME;
#将publishdate列改名为pubDate
#类型或约束
ALTER TABLE book
    MODIFY COLUMN pubdate TIMESTAMP NOT NULL; # 增加列级约束
ALTER TABLE book
    MODIFY COLUMN pubdate TIMESTAMP NULL; # 删除非空约束
ALTER TABLE book
    ADD PRIMARY KEY (id); # 增加表级约束
#添加新列
ALTER TABLE book
    ADD COLUMN annual DOUBLE;
#删除
ALTER TABLE book
    DROP COLUMN annual;
ALTER TABLE book DROP PRIMARY KEY; # 删除主键
# ALTER TABLE book DROP INDEX bname; # 删除唯一
ALTER TABLE book DROP FOREIGN KEY fk_authorID; # 删除外键
#修改表名
ALTER TABLE book RENAME TO book_2;


#表的删除
DROP TABLE IF EXISTS book_2;


#表的复制
#1.仅复制表的结构：
CREATE TABLE book_copy LIKE book_2;
#2.复制表的结构和数据
CREATE TABLE book_copy2
SELECT *
FROM book_2; #也可以选择部分数据拷贝
CREATE TABLE book_copy3
SELECT id, bname
FROM book_2
WHERE 1 = 2; #可以起到复制部分表的结构的目的

#补充：删除外键
#方式一：级联删除（在添加外键的语句后再加上ON DELETE CASCADE）,这样删除主表中的相关条时会自动删除从表中相关行
#方式二：级联置空（在添加外键的语句后再加上ON DELETE SET NULL）,这样删除主表中的相关条时会自动将从表中相关数据改成NULL