package ro.VisualDB.Connections;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 31/03/13
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.*;
import org.postgresql.Driver;

public class ConnectionInstance {

    private void initiateConnectionPostgreSQL(String host, String port, String dbName, String userName, String pass) {
        System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");

        Connection connection = null;

        try {
//          baza de pe Heroku.com
//          connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-225-69-193.compute-1.amazonaws.com:5432/d67ok9eheqe9a1", "fpflivmxynqdma","Ol-0WAibcjv5nmqtowfwazb7tR");
            connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + dbName, userName, pass);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            executeMyStatement(connection);
        } else {
            System.out.println("Failed to initiate connection!");
        }
    }

    private void executeMyStatement(Connection connection){
        Statement st = null;

        try {
            st = connection.createStatement();
            String sql = "select * from abc;";

            st.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) {
        new ConnectionInstance().initiateConnectionPostgreSQL("localhost", "5432", "MyNewDatabase", "gogu", "gogurocks");
    }
}
