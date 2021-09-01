# 启动与关闭服务器
方式一：计算机->管理->服务 <br/>
方式二：CMD（管理员模式下）
```cmd
net stop mysql80
net start mysql80
```
# 登录与退出MySQL服务
```cmd
mysql -h localhost -P 3306 -u root -p
exit
```
-h -> host <br/>
-P -> Port <br/>
若是默认host和端口，直接用mysql -u root -p即可

# 查看版本
```cmd
mysql --version
```
# 基本操作
MySQL不区分大小写，但建议关键字大写，表名和列名小写 <br/>
每条命令最好用分号结尾 <br/>
每条命令可以根据需要进行缩进或换行 <br/>
```
show databases;
use test;
show tables;  
show tables from mysql; #仅是临时查表，目前位置仍在test库中
select database(); #查询当前所在哪个库

#新建表
create table stuinfo( 
id int,
name varchar(20));

desc stuinfo; #查看表中各变量的具体属性
select * from stuinfo; #查看表
insert into stuinfo (id,name) values(1,'John'); #添加数据条
update stuinfo set name="Peter" where id=1; #改数据
delete from stuinfo where id=1; #删数据

select version(); #查看版本

#单行注释
-- 单行注释（一定要带空格）
/* 多行注释 */
```