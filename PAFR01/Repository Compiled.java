    // WORKSHOP 21
    public static final String SELECT_ALL_CUSTOMERS = "select id, company, first_name, last_name  from customers limit ? , ?";
    public static final String SELECT_CUSTOMER_BY_ID = "select id, company, first_name, last_name  from customers where id = ?";
    public static final String SELECT_ORDERS_FOR_CUSTOMERS = "select c.id as customer_id, c.company, o.id as order_id,o.ship_name, o.shipping_fee  from customers c, orders1 o where c.id = o.customer_id and customer_id = ?";
    // WORKSHOP 22
    public static final String SELECT_ALL_RSVP = "select id, name, email, phone, DATE_FORMAT(confirmation_date,\"%d/%m/%Y\") as confirmation_date, comments from rsvp";
    public static final String SELECT_RSVP_BY_NAME = "select id, name, email, phone, DATE_FORMAT(confirmation_date,\"%d/%m/%Y\") as confirmation_date, comments from rsvp where name like ?";
    public static final String SELECT_RSVP_BY_EMAIL ="select * from rsvp where email = ? ";
    public static final String INSERT_NEW_RSVP ="INSERT INTO rsvp (name, email, phone, confirmation_date, comments) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_RSVP_BY_EMAIL = "update rsvp set name =?, phone =?, confirmation_date = ?, comments = ? where email = ?";
    public static final String TOTAL_RSVP_COUNT = "select count(*) as total_count from rsvp";
    //WORKSHOP 23
    public static final String ORDER_DETAILS_WITH_DISCOUNT_QUERY ="select o."+ "id as order_id, DATE_FORMAT(o.order_date, \"%d/%m/%Y\") as order_date, o."+ "customer_id,sum(od.quantity * od.unit_price) as total_price, sum(od." +"quantity * od.unit_price * od.discount) as discount, sum(od.quantity "+"* od.unit_price) - sum(od.quantity * od.unit_price * od.discount) as "+"discounted_price,sum(od.quantity * p.standard_cost) as cost_price from "+"Orders o left join Order_details od on o.id = od.order_id left join "+"products p on od.product_id = p.id where o.id = ?";
    //WORKSHOP 24
    public static final String GET_ALL_PRODUCTS = "select * from fruits_products";
    public static final String INSERT_PURCHASE_ORDER = "insert into purchase_order(order_id, order_date, customer_name, ship_address, notes, tax ) values (?, SYSDATE(), ?, ?, ?, 0.05)";
    public static final String INSERT_PURCHASE_ORDER_DETAILS = "insert into purchase_order_details(product, unit_price, discount, quantity, order_id) values (?,?,?,?,?)";    


    //ROWMAPPER CLASS
    public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();

        customer.setId(rs.getInt("id"));
        customer.setCompany(rs.getString("company"));
        customer.setLastName(rs.getString("last_name"));
        customer.setFirstName(rs.getString("first_name"));

        return customer;
    }

    //CREATE OBJECT FROM ROWSET
     public static Order create(SqlRowSet rs) {
        Order order = new Order();
        Customer customer = new Customer();

        customer.setId(rs.getInt("customer_id"));
        order.setCustomer(customer);
        order.setId(rs.getInt("order_id"));
        order.setShipName(rs.getString("ship_name"));
        order.setShippingFee(rs.getDouble("shipping_fee"));

        return order;
    }

        //QUERY FOR ROWSET - CREATE FROM ROWSET WITH OBJECT VARARGS
    public List<Customer> getAllCustomer(Integer offset, Integer limit) {

        List<Customer> csts = new ArrayList<Customer>();

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ALL_CUSTOMERS,
                offset, limit);

        while (rs.next()) {
            csts.add(Customer.create(rs));
        }

        return csts;
    }

    //QUERY - USING ROWMAPPER
    public Customer findCustomerById(Integer id) {

        List<Customer> customers = jdbcTemplate.query(SELECT_CUSTOMER_BY_ID, new CustomerRowMapper(),
                new Object[] { id });

        return customers.get(0);

    }

    //QUERY FOR ROWSET - USING ROWSET AND OBJECT ARRAY
     public List<Order> getCustomerOrders(Integer id)
     {
        List<Order> orders = new ArrayList<Order>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_ORDERS_FOR_CUSTOMERS,
        new Object[] { id });

        while (rs.next()) {
            orders.add(Order.create(rs));
        }

        return orders;
     }

    //QUERY FOR ROWSET
    public List<RSVP> getAllRSVP() {
        List<RSVP> rsvps = new ArrayList<RSVP>();
        SqlRowSet rs = null;
        rs = jdbcTemplate.queryForRowSet(SELECT_ALL_RSVP);
        while (rs.next())
            rsvps.add(RSVP.create(rs));
        return rsvps;
    }

    // QUERY FOR ROWSET - CREATE FROM ROWSET WITH OBJECT ARRAY
    public List<RSVP> getRSVPByName(String name) {
        List<RSVP> rsvps = new ArrayList<RSVP>();
        SqlRowSet rs = null;

        rs = jdbcTemplate.queryForRowSet(SELECT_RSVP_BY_NAME, new Object[] { "%" + name + "%" });

        while (rs.next())
            rsvps.add(RSVP.create(rs));
        return rsvps;
    }

    // QUERY FOR ROWSET - CREATE FROM ROWSET WITH OBJECT VARARGS
    private RSVP getRSVPByEmail(String email) {
        List<RSVP> rsvpList = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_RSVP_BY_EMAIL, email);

        System.out.println("checking resultset ----> " + Objects.isNull(rs));

        while (rs.next()) {
            System.out.println("inside while loop");
            rsvpList.add(RSVP.create(rs));
        }

        System.out.println("size of rsvp list ---- > " + rsvpList.size());
        if (rsvpList.size() == 0)
            return null;
        return rsvpList.get(0);
    }

    //USING UPDATE WITH PREPAREDSTATEMENT CREATOR WITH KEYHOLDER
    public RSVP createRsvp(RSVP rsvp) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        RSVP existingRSVP = getRSVPByEmail(rsvp.getEmail());

        if (Objects.isNull(existingRSVP)) {

            System.out.println("inside If loop--->");
            // insert record
            jdbcTemplate.update(conn -> {
                PreparedStatement statement = conn.prepareStatement(INSERT_NEW_RSVP, Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, rsvp.getName());
                statement.setString(2, rsvp.getEmail());
                statement.setString(3, rsvp.getPhone());
                statement.setTimestamp(4, new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()));
                statement.setString(5, rsvp.getComments());
                return statement;
            }, keyHolder);

            BigInteger primaryKey = (BigInteger) keyHolder.getKey();

            rsvp.setId(primaryKey.intValue());

        } else {
            System.out.println("inside else loop--->");
            // update existing record
            existingRSVP.setName(rsvp.getName());
            existingRSVP.setPhone(rsvp.getPhone());
            existingRSVP.setConfirmationDate(rsvp.getConfirmationDate());
            existingRSVP.setComments(rsvp.getComments());

            boolean isUpdated = updateRSVP(existingRSVP);

            if (isUpdated) {
                rsvp.setId(existingRSVP.getId());
            }

        }

        return rsvp;
    }

    //UPDATE WITH OBJECT ARGS
    public boolean updateRSVP(RSVP existingRSVP) {
        return jdbcTemplate.update(UPDATE_RSVP_BY_EMAIL,
                existingRSVP.getName(),
                existingRSVP.getPhone(),
                new Timestamp(existingRSVP.getConfirmationDate().toDateTime().getMillis()),
                existingRSVP.getComments(),
                existingRSVP.getEmail()) > 0;
    }

    // QUERY FOR LIST RETURNING LIST OF OBJECTS MAPPED TO COLUMN NAMES
    public Long getTotalRSVPCount() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(TOTAL_RSVP_COUNT);
        return (Long) rows.get(0).get("total_count");
    }

    // QUERY FOR ROWSET - CREATE FROM ROWSET WITH OBJECT VARARGS
    public OrderDetails getOrderDetailsWithDiscount(Integer orderId) {
        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        
        System.out.println("Query ------ >" +ORDER_DETAILS_WITH_DISCOUNT_QUERY);
        SqlRowSet resulSet = jdbcTemplate.queryForRowSet(ORDER_DETAILS_WITH_DISCOUNT_QUERY, orderId);

        while (resulSet.next())
            orderDetailsList.add(OrderDetails.create(resulSet));
        return orderDetailsList.get(0);
    }

    //USING A LAMBDA ROWMAPPING
    public List<FruitProducts> getProducts() {
        String query = GET_ALL_PRODUCTS;

        return jdbcTemplate.query(query, (rs, rownum) -> {
            FruitProducts fruitProducts = new FruitProducts();
            fruitProducts.setId(rs.getInt("id"));
            fruitProducts.setName(rs.getString("name"));
            fruitProducts.setStandardPrice(rs.getBigDecimal("standard_price"));
            fruitProducts.setDiscount(rs.getBigDecimal("discount"));
            return fruitProducts;
        });
    }

    //USING BATCH UPDATE METHOD
    public void addLineItems(List<LineItem> lineItems, String orderId) {
        List<Object[]> data = lineItems.stream()
                .map(li -> {
                    Object[] l = new Object[5];
                    l[0] = li.getProduct();
                    l[1] = OrderUtility.calculateUnitPrice(li.getProduct(), li.getQuantity());
                    l[2] = discount;
                    l[3] = li.getQuantity();
                    l[4] = orderId;
                    return l;
                })
                .toList();

        // product, unit_price, discount, quantity, order_id
        jdbcTemplate.batchUpdate(INSERT_PURCHASE_ORDER_DETAILS, data);
    }

    //USING TRANSACTIONS
     @Transactional(rollbackFor = OrderException.class)
    public void createOrder(PurchaseOrder purchaseOrder) throws OrderException {
        // create order id
        Random rand = new Random();
        int ordId = rand.nextInt(10000000);
        String orderId = String.valueOf(ordId);

        purchaseOrder.setOrderId(ordId);
        FruitProducts.fruitProducts = itemRepository.getProducts();

        purchaseOrderRepository.insertPurchaseOrder(purchaseOrder);

        if (purchaseOrder.getLineItems().size() > 5) {
            throw new OrderException("Can not order more than 5 items");
        }
        itemRepository.addLineItems(purchaseOrder.getLineItems(), orderId);
    }


/*
Get a list of all customers
offset - return the first result from n records from the first; n is the number given by offset parameter
limit - return the number of records specified by limit, The default value for offset is 0 and limit is 5.
GET /api/customers
Accept: application/json

Get the details of a customer with the customer’s id
GET /api/customer/<customer_id>
Accept: application/json
Return a 404 and an appropriate error object if the customer does not exist

Get all orders for a customer
GET /api/customer/<customer_id>/orders
Accept: application/json
This endpoint returns an array of orders in JSON. If the customer does not 
have any orders, the endpoint should return an empty array
Return a 404 and an appropriate error object if the customer does not exist. 




Write a HTML form to search for the total price of an order and the discount 
that was given. When a user enters an order number, send the following HTTP 
request is made to the SpringBoot backend
GET /order/total/<order_id>
Accept: text/html
The Spring Boot application returns the following details 
- Order id
- Order date – order_date
- Customer id – customer_id
- Total – price of the order
- Computed from order_details – quantity * unit_price * discount
- Cost price
- Computed from products – quantity * standard_cost
Return an appropriate error message if the order is not found


Get all RSVPs - get all the RSVPs from the database
GET /api/rsvps
Accept: application/json


Search for a RSVP - search a RSVP by name (or parts of)
GET /api/rsvp?q=fred
Accept: application/json
Return a 404 and an appropriate error object if you cannot find the RSVP. 
This method should return all matching RSVP records.


Add a new RSVP - add a new RSVP into the table. If it is an existing RSVP, 
overwrite the RSVP in the table with this new RSVP
POST /api/rsvp
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Return a 201 if the operation is successful.

Update an existing RSVP
PUT /api/rsvp/fred@gmail.com
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Return a 201 if the update operation is successful; a 404 if the email is not found

Get the number of RSVPs - get the number of people who have RSVPs
GET /api/rsvps/count
Accept: application/json
Return a 201 if the operation is successful.


Write a HTML form to create an order (with order details) in the database.
The form should use the following HTTP to send the details of the order to the 
server
POST /order
Content-Type: application/x-www-form-urlencoded


Write the Spring Boot application to process this request. The endpoint should 
then insert the new order into the orders table. 
Return appropriate error message if the insert fails.
A new order involves inserting one or more records into orders and 
order_details table
*/