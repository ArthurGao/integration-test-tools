package com.arthur.integrationtest.extension;

import static org.assertj.core.api.Assertions.assertThat;

import com.arthur.integrationtest.extension.MySQLExtension.MySQLInitScript;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MySQLExtension.class})
@MySQLInitScript(dbName = "subscription", initScript = "sql/schema.sql")
public class MySQLExtensionITest {

  @Test
  void testMySQLConnection() throws SQLException, ClassNotFoundException {
    String url = System.getProperty("spring.datasource.url"); // table details
    String username = System.getProperty("spring.datasource.username");
    String password = System.getProperty("spring.datasource.password");
    String query = "select * from subscription.account"; // query to be run
    Class.forName("com.mysql.cj.jdbc.Driver"); // Driver name
    Connection con = DriverManager.getConnection(url, username, password);
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(query); // Execute query
    rs.next();
    String name = rs.getString("name"); // Retrieve name from db
    assertThat(name).isEqualTo("account1");
    st.close(); // close statement
    con.close(); // close connection
  }
}
