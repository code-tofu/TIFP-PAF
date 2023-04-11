    public static final String SELECT_ALL_CUSTOMERS = "select id, company, first_name, last_name  from customers limit ? , ?";
    public static final String SELECT_CUSTOMER_BY_ID = "select id, company, first_name, last_name  from customers where id = ?";
    public static final String SELECT_ORDERS_FOR_CUSTOMERS = "select c.id as customer_id, c.company, o.id as order_id,o.ship_name, o.shipping_fee  from customers c, orders1 o where c.id = o.customer_id and customer_id = ?";
    public static final String SELECT_ALL_RSVP = "select id, name, email, phone, DATE_FORMAT(confirmation_date,\"%d/%m/%Y\") as confirmation_date, comments from rsvp";
    public static final String SELECT_RSVP_BY_NAME = "select id, name, email, phone, DATE_FORMAT(confirmation_date,\"%d/%m/%Y\") as confirmation_date, comments from rsvp where name like ?";
    public static final String SELECT_RSVP_BY_EMAIL ="select * from rsvp where email = ? ";
    public static final String INSERT_NEW_RSVP ="INSERT INTO rsvp (name, email, phone, confirmation_date, comments) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_RSVP_BY_EMAIL = "update rsvp set name =?, phone =?, confirmation_date = ?, comments = ? where email = ?";
    public static final String TOTAL_RSVP_COUNT = "select count(*) as total_count from rsvp";
    public static final String ORDER_DETAILS_WITH_DISCOUNT_QUERY ="select o."+ "id as order_id, DATE_FORMAT(o.order_date, \"%d/%m/%Y\") as order_date, o."+ "customer_id,sum(od.quantity * od.unit_price) as total_price, sum(od." +"quantity * od.unit_price * od.discount) as discount, sum(od.quantity "+"* od.unit_price) - sum(od.quantity * od.unit_price * od.discount) as "+"discounted_price,sum(od.quantity * p.standard_cost) as cost_price from "+"Orders o left join Order_details od on o.id = od.order_id left join "+"products p on od.product_id = p.id where o.id = ?";

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