use northwind;

SELECT Customers.first_name, Orders.*
FROM Orders
INNER JOIN Customers ON Orders.Customer_ID = Customers.ID;

SELECT Customers.First_Name, Orders.ID
FROM Customers
LEFT JOIN Orders
ON Customers.ID=Orders.Customer_ID
ORDER BY Customers.first_Name;

SELECT Orders.ID, Employees.Last_Name, Employees.First_Name
FROM Orders
RIGHT JOIN Employees ON Orders.Employee_ID = Employees.ID
ORDER BY Orders.ID;


CREATE VIEW Boston_Customers AS
SELECT first_name, last_name, job_title
FROM Customers
WHERE city = 'boston';

select * from boston_customers;

create view customer_order_count as 
SELECT customer_id, count(customer_id) cnt
FROM orders group by customer_id;

select * from customer_order_count;


SELECT Ship_city, count(*) FROM
(SELECT Customers.first_name, Orders.*
FROM Orders
INNER JOIN Customers ON Orders.Customer_ID = Customers.ID
where employee_id in (9) 
and shipped_date between '2006-01-01' and '2006-03-31') Customer_Order
Group by Ship_City;

# step 1
SELECT Customers.first_name, Orders.*
FROM Orders
INNER JOIN Customers ON Orders.Customer_ID = Customers.ID
where shipped_date between '2006-01-01' and '2006-03-31';

#step 2
SELECT employee_id, Ship_city, count(*) as cnt FROM
(SELECT Customers.first_name, Orders.*
FROM Orders
INNER JOIN Customers ON Orders.Customer_ID = Customers.ID
where shipped_date between '2006-01-01' and '2006-03-31') Customer_Order
Group by employee_id, Ship_City;

#step 3
SELECT employee_id, sum(cnt) FROM
(SELECT employee_id, Ship_city, count(*) as cnt FROM
(SELECT Customers.first_name, Orders.*
FROM Orders
INNER JOIN Customers ON Orders.Customer_ID = Customers.ID
where shipped_date between '2006-01-01' and '2006-03-31') Customer_Order
Group by employee_id, Ship_City) Overview
group by employee_id


