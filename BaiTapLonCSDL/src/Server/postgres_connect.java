/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tran minh hai
 */
public class postgres_connect {
    Connection connection = null ;
    public Connection getConnectToMSSQL() {
        try {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost/hai_project", "postgres", "hedspi");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(postgres_connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
        
    }
    public void Close() {
        if(connection!=null)
            try {
                if(!connection.isClosed())
                    connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(postgres_connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection=null;
    }
}
