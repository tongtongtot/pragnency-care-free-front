1. Check database:

    ```
    use information_schema;
    
    select distinct table_schema from information_schema.tables;
    ```

2. Exit database:

    ```
    use name;
    ```

3. 删除原有的表

```
drop table topic;

drop table topic01;

drop table user_info;
```

4. 重新创建表

```
create table topic(postid int(10) primary key not null auto_increment,request_type varchar(20),piccnt varchar(20),data varchar(100),picurl varchar(100),title varchar(100),create_ varchar(100),userid varchar(10),user_type INT(10),user_name varchar(10));

create table topic01(postid int(10) primary key not null auto_increment,request_type varchar(20),piccnt varchar(20),data varchar(100),picurl varchar(100),title varchar(100),create_ varchar(100),userid varchar(10),user_type INT(10),user_name varchar(10));

 create table user_info (user_id int(10) primary key not null auto_increment,user_name varchar(10),password varchar(10),user_phone varchar(10),sex varchar(10),user_type varchar(10));
```

5. 选择table

    ```
    select * from table_name;
    ```

    