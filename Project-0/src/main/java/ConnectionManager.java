
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Adding in the database connection logic here
// Need a hostname, port, database name, username, password
// This makes a singleton class so there can only be one connection manager
public class ConnectionManager {

    private ConnectionManager connectionManager;
    private static Connection connection;

    private ConnectionManager() {
    }

    // public getter method
    public ConnectionManager getConnectionManager() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    public static Connection getConnection() {
        if (connection == null) {
            connection = connect();
        }
        return connection;
    }

    // Connection logic here
    private static Connection connect() {
        // Need the address of the driver - ex. jdbc:mariadb://hostname:port/databaseName?user=username&password=password
        try {
            Properties props = new Properties();
            FileReader fr = new FileReader("src/main/resources/connection.properties");
            props.load(fr);

            // Concatenate string into the format needed
            String connectionString = "jdbc:mariadb://" +
                props.getProperty("hostname") + ":" +
                props.getProperty("port") + "/" +
                props.getProperty("dbname") + "?user=" +
                props.getProperty("username") + "&password=" +
                props.getProperty("password");

            // This line can go anywhere - required by MariaDB in documentation?
            // Probably don't need this as long as you have the dependency in pom.xml
            //Class.forName("org.mariadb.jdbc.Driver");
            // Tells it to load this class into memory by name; don't even need the reference to it

            connection = DriverManager.getConnection(connectionString);
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
