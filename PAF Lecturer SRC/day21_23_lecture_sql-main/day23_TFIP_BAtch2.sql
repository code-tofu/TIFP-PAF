use northwind;

select * from customers;

select * from orders;

select cust.id as cust_id, cust.company, cust.last_name, cust.first_name, cust.job_title,
ord.order_date, ord.shipped_date, ord.ship_name, ord.shipping_fee
from customers as cust 
inner join orders ord 
on cust.id = ord.customer_id
order by cust.company;

select cust.id cust_id, cust.last_name,
ord.id as order_id
from customers as cust
left join orders as ord
on cust.id = ord.customer_id
order by cust.last_name;

select cust.id cust_id, cust.last_name,
ord.id as order_id
from customers as cust
right join orders as ord
on ord.customer_id = cust.id
order by cust.last_name;


create view customer_orders as
select cust.id as cust_id, cust.company, cust.last_name, cust.first_name, cust.job_title,
ord.order_date, ord.shipped_date, ord.ship_name, ord.shipping_fee
from customers as cust 
inner join orders ord 
on cust.id = ord.customer_id
order by cust.company;

select * from customer_orders;

select id, product_code, product_name from products
where id in (select distinct product_id from purchase_order_details);

select distinct prod.id, prod.product_code, prod.product_name 
from products as prod
inner join purchase_order_details as pod
on pod.product_id = prod.id;

select id, company, last_name, first_name, job_title from suppliers 
where id in (select distinct supplier_ids from products);

select sup.id, sup.company, sup.last_name, sup.first_name, sup.job_title 
from suppliers as sup
inner join products prod
on prod.supplier_ids = sup.id





