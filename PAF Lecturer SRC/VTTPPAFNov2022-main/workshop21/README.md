## Workshop 21

1. Add mysql connector library and json-p

```
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<version>8.0.31</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.glassfish/jakarta.json -->
<dependency>
	<groupId>org.glassfish</groupId>
	<artifactId>jakarta.json</artifactId>
	<version>2.0.1</version>
</dependency>

```

2. Change password for the db user

```
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password123';
ALTER USER 'root'@'localhost' IDENTIFIED WITH auth_socket;
```

3. Add JDBC properties to the spring boot application boot properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/northwind
spring.datasource.username=kenneth
```

4. Run spring boot app

```
mvnw spring-boot:run
```

5. Create the northwind mysql database instance

```
CREATE DATABASE northwind;
```

6. Create a new user 'fred' and provide privileges

```
CREATE USER 'fred'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'fred'@'localhost' WITH GRANT OPTION;
```

7. Execute northwind sql scripts

```
source northwind.sql;

source northwind-data.sql;

commit;
```
