## Workshop 22

```
SELECT
    o.id as order_id,
    DATE_FORMAT(o.order_date, "%d/%m/%Y") as order_date,
    o.customer_id as customer_id,
    sum(od.quantity * od.unit_price) as total_price,
    sum(od.quantity * od.unit_price * od.discount) as discount,
    sum(od.quantity * od.unit_price) - sum(od.quantity * od.unit_price * od.discount) as discounted_price,
    sum(od.quantity * p.standard_cost) as cost_price
FROM
    orders o,
    order_details od,
    products p
WHERE
    o.id = od.order_id
        AND od.product_id = p.id
	and o.id = 30
```
