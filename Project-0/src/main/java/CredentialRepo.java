import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialRepo {
    private final Connection connection;

    // Handles the write operations for the Credentials.
    // This is meant for people registering for the first time
    // Returns true if successful, else it returns false
    public Boolean register(CredentialModel model) {
        try {
            String sql = "INSERT INTO credential(username, password, customer_id) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, model.getUsername());
            statement.setString(2, model.getPassword());
            statement.setInt(3, model.getCustomerId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // Retrieves a credential record based on the ID number.
    // Intended to be used with new registrations
    public CredentialModel getById(Integer id) {
        try {
            String sql = "SELECT * from credential WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            // Just using the blank one for now
            CredentialModel model = new CredentialModel();
            while(results.next()) {
                model.setCredentialId(results.getInt("credential_id"));
                model.setUsername(results.getString("username"));
                model.setPassword(results.getString("password"));
                model.setCustomerId(results.getInt("customer_id"));
            }
            return model;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieves a single credential record based on the username and password.
    // If the username and password do not match, then null is returned.
    // Intended to be used with existing registrations
    public CredentialModel getByCredentials(String username, String password) {
        try {
            String sql = "SELECT * from credential WHERE (username = ? AND password = ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();

            CredentialModel model = null;
            while(results.next()) {
                model = new CredentialModel();
                model.setCredentialId(results.getInt("credential_id"));
                model.setUsername(results.getString("username"));
                model.setPassword(results.getString("password"));
                model.setCustomerId(results.getInt("customer_id"));
            }
            return model;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Checks if a username is taken (unique)
    // Returns true if the username already exists in the database, else returns false
    // Intended to be used with new registrations
    public Boolean usernameIsInUse(String username) {
        try {
            String sql = "SELECT * from credential WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();

            if(results.next()) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Omitting update and delete methods for now


    // Constructor: Initialize the connection
    CredentialRepo() {
        this.connection = ConnectionManager.getConnection();
    }
}
