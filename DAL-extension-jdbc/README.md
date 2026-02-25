DAL Extension of DataBase use JDBC:

Given the following data table in database H2

orders:

 id  | customer 
-----|----------
 S01 | Tom      

products:

 id | name   
----|--------
 p1 | iPhone |
 p2 | MBP    |

order_lines:

 id | product_id | order_id | quantity |
----|------------|----------|----------
 1  | p1         | S01      | 1        
 2  | p2         | S01      | 100      

Then you can assert database through DAL:

```
    Connection connection=DriverManager.getConnection("jdbc:h2:mem:test","sa","");

    expect(new DataBaseBuilder().connect(connection)).should("""
        : {
            products: | id | name   |
                      | p1 | iPhone |
                      | p2 | MBP    |
                      
            orders: [{
                customer= Tom
                ::hasMany[order_lines]: | id | product_id | order_id | quantity | ::belongsTo[products].name |
                                        | 1  | p1         | S01      | 1        | iPhone                     |
                                        | 2  | p2         | S01      | 100      | MBP                        |
            }]
        }
    """);
```

You can also define some 'method' on table row

```
    DataBaseBuilder builder = new DataBaseBuilder();
    builder.tableStrategy("orders").registerRowMethod("orderLines", row ->
            row.hasMany("order_lines"));
            
    builder.tableStrategy("order_lines").registerRowMethod("product", row ->
            row.belongsTo("products"));
```

And then you can query association data directly

```
    expect(builder.connect(connection)).should("""
        : {
            orders: [{
                customer= Tom
                orderLines: | id | quantity | products.name |
                            | 1  | 1        | iPhone        |
                            | 2  | 100      | MBP           |
            }]
        }
    """);
```

You can see more examples by test case
in [here](https://github.com/leeonky/DAL-extension-jdbc/tree/main/src/test/resources/features)