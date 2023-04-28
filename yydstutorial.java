// firstName (Entity) --> first_name (mySQL)
    // firstname (entity) --> firstname (mySQL)

create table room (
	id int not null auto_increment,
    room_type enum('standard', 'deluxe', 'president', 'suite') not null,
    price int not null,
    primary key(id)

create table tv_shows (
	prog_id int not null,
    title char(64) not null,
    lang char(16) not null,
    official_site varchar(256),
    rating enum('G', 'PG', 'NC16', 'M18', 'R21') not null,
    user_rating float default '0.0',
    release_date date,
    image blob,
    constraint pk_prog_id primary key (prog_id),
    constraint chk_user_rating
		check(user_rating between 0.0 and 10.0)
);

city_id smallint(5)
id tinyint(3) 


select cust.id as cust_id, cust.company, cust.last_name, cust.first_name, cust.job_title,
ord.order_date, ord.shipped_date, ord.ship_name, ord.shipping_fee
from customers as cust 
inner join orders ord 
on cust.id = ord.customer_id
order by cust.company;

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


@Data
@AllArgsConstructor
@NoArgsConstructor


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
        .info(new Info()
        .title("My OpenAPI documentation - Day 21 Lecture")
        .description("PAF Day 22")
        .version("version 1.0"));
    }
}

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

public RSVP findByName(String fullname) {
        return jdbcTemplate.queryForObject(selectByNameSQL, BeanPropertyRowMapper.newInstance(RSVP.class), fullname);
    }

    public Integer countAll() {
        return jdbcTemplate.queryForObject(countSQL, Integer.class);
    }


public Boolean save(Employee employee) {
        Boolean bSaved = false;

        PreparedStatementCallback<Boolean> psc = new PreparedStatementCallback<Boolean>() {

            @Override
            @Nullable
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setInt(3, employee.getSalary());
                Boolean rslt = ps.execute();
                return rslt;
            }

        };

        bSaved = jdbcTemplate.execute(insertSQL, psc);

        return bSaved;
    }

public int update(Employee employee) {
        int iUpdated = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {

            // "update employee set first_name = 1, last_name = 2, salary = 3 where id = 4 "
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, employee.getFirstName());
                ps.setString(2, employee.getLastName());
                ps.setInt(3, employee.getSalary());
                ps.setInt(4, employee.getId());
            }
        };

        iUpdated = jdbcTemplate.update(updateSQL, pss);

        return iUpdated;
    }

public int delete(Integer id) {
        int bDeleted = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {

                ps.setInt(1, id);
            }
        };

        bDeleted = jdbcTemplate.update(deleteSQL, pss);

        return bDeleted;
    }

public Boolean save(Room room) {
        Boolean saved = false;

        saved = jdbcTemplate.execute(insertSQL, new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException {
                ps.setString(1, room.getRoomType());
                ps.setFloat(2, room.getPrice());
                Boolean rslt = ps.execute();

                return rslt;
            }
        });

        return saved;
    }




    public int update(Room room) {
        int updated = 0;

        updated = jdbcTemplate.update(updateSQL, new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setFloat(1, room.getPrice());
                ps.setInt(2, room.getId());
            }
        });

        return updated;
    }

    public int deleteById(Integer id) {
        int deleted = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        };

        deleted = jdbcTemplate.update(deleteByIdSQL, pss);

        return deleted;
    }

return iResult > 0 ? true : false;


public int[] batchUpdate(List<RSVP> rsvps) {

        return jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, rsvps.get(i).getFullName());
                ps.setString(2, rsvps.get(i).getEmail());
                ps.setString(3, rsvps.get(i).getPhone());
                ps.setDate(4, rsvps.get(i).getConfirmationDate());
                ps.setString(5, rsvps.get(i).getComments());
            }

            public int getBatchSize() {
                return rsvps.size();
            }
        });
    }

}


 @DeleteMapping("/{employee-id}")
    public ResponseEntity<Integer> delete(@PathVariable("employee-id") Integer id) {
        Integer iDeleted = empSvc.delete(id);

        return new ResponseEntity<Integer>(iDeleted, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        List<Employee> employees = empSvc.findAll();

        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }



public List<Employee> findAll() {

        ResponseEntity<List<Employee>> result = restTemplate.exchange(GET_EMPLOYEES_ENDPOINT_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Employee>>() {
                });
        return result.getBody();
    }

    public Employee findById(Integer id) {

        Employee employee = restTemplate.getForObject(GET_EMPLOYEEBYID_ENDPOINT_URL, Employee.class, Map.of("id", id));
        return employee;
    }

    public Boolean save(Employee employee) {

        Boolean bResult = restTemplate.postForObject(CREATE_EMPLOYEE_ENDPOINT_URL, employee, Boolean.class);
        return bResult;
    }

    public int update(Employee employee) {

        restTemplate.put(UPDATE_EMPLOYEE_ENDPOINT_URL, employee);
        return 1;
    }

    public int delete(Integer id) {

        restTemplate.delete(DELETE_EMPLOYEE_ENDPOINT_URL, Map.of("id", id));
        return 1;
    }

}

public List<Order> findOrderbyId(Integer orderId) {

        ResponseEntity<List<Order>> results = restTemplate.exchange(GETORDERBYID_ENDPOINT_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Order>>() {
                }, orderId);

        return results.getBody();
    }


public Integer createResv(Resv resv) {
        // Create GeneratedKeyHolder object
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[] {"id"});

                ps.setDate(1, resv.getResvDate());
                ps.setString(2, resv.getFullName());
                return ps;
            }
        };

        jdbcTemplate.update(psc, generatedKeyHolder);

        // Get auto-incremented ID
        Integer returnedId = generatedKeyHolder.getKey().intValue();

        return returnedId;
    }
}

 

KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[] {"id"});

                ps.setInt(1, resv.getBookId());
                ps.setInt(2, resv.getResvId());
                return ps;
            }
        };

        jdbcTemplate.update(psc, generatedKeyHolder);

        // Get auto-incremented ID
        Integer returnedId = generatedKeyHolder.getKey().intValue();

        return returnedId;
    }

public List<Book> findAll() {
        return jdbcTemplate.query(SELECT_SQL, BeanPropertyRowMapper.newInstance(Book.class));
    }

bWithdrawalAccountValid = withdrawalAccount.getIsActive();
        bDepositAccountValid = depositAccount.getIsActive();
        if (bWithdrawalAccountValid && bDepositAccountValid)
            bProceed = true;


    @Transactional //(isolation = Isolation.READ_UNCOMMITTED)
    public Boolean transferMoney(Integer accountFrom, Integer accountTo, Float amount) {
        Boolean bTransferred = false;
        Boolean bWithdrawn = false;
        Boolean bDeposited = false;

        BankAccount depositAccount = null;
        BankAccount withdrawalAccount = null;
        Boolean bDepositAccountValid = false;
        Boolean bWithdrawalAccountValid = false;
        Boolean bProceed = false;

        // check if accounts (withdrawer and depositor) are valid (active)
        withdrawalAccount = bankAcctRepo.retrieveAccountDetails(accountFrom);
        depositAccount = bankAcctRepo.retrieveAccountDetails(accountTo);
        bWithdrawalAccountValid = withdrawalAccount.getIsActive();
        bDepositAccountValid = depositAccount.getIsActive();
        if (bWithdrawalAccountValid && bDepositAccountValid)
            bProceed = true;

        // check withdrawn account has more money than withdrawal amount
        if (bProceed) {
            if (withdrawalAccount.getBalance() < amount)
                bProceed = false;
        }

        if (bProceed) {
            // perform the withdrawal (requires transaction)
            bWithdrawn = bankAcctRepo.withdrawAmount(accountFrom, amount);

            bWithdrawn = false;
            if (!bWithdrawn) {
                throw new IllegalArgumentException("Simulate error before Withdrawal");
            }

            // perform the deposit (requires transaction)
            bDeposited = bankAcctRepo.depositAmount(accountTo, amount);
        }

        // return transactions successful
        if (bWithdrawn && bDeposited)
            bTransferred = true;

        return bTransferred;
    }
