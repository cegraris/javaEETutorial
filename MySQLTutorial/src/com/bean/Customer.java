package com.bean;

import java.sql.Date;

/**
 * @author Jiahao Wu
 * @date 2021/9/9 - 1:37
 */

/* ORM编程思想（object relational mapping)
 一个数据表对应一个数据类
 表中的一条记录对应相应类的一个对象
 表中的一个字段对应相应类的一个属性

+--------------------+--------------------------+
| Java               | SQL                      |
+--------------------+--------------------------+
| boolean            | BIT                      |
+--------------------+--------------------------+
| short              | SMALLINT                 |
+--------------------+--------------------------+
| int                | INTEGER                  |
+--------------------+--------------------------+
| long               | BIGINT                   |
+--------------------+--------------------------+
| String             | CHAR,VARCHAR,LONGVARCHAR |
+--------------------+--------------------------+
| byte array         | BINARY,VAR BINARY        |
+--------------------+--------------------------+
| java.sql.Date      | DATE                     |
+--------------------+--------------------------+
| java.sql.Time      | TIME                     |
+--------------------+--------------------------+
| java.sql.Timestamp | TIMESTAMP                |
+--------------------+--------------------------+


 */
public class Customer {
    private int id;
    private String name;
    private String email;
    private Date birth;

    public Customer() {
        super();
    }

    public Customer(int id, String name, String email, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }
}
