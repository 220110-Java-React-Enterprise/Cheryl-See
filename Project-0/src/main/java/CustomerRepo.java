import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepo {
    private final Connection connection;

    // Retrieves customer info based on credential ID (user has just logged in successfully)
    public CustomerModel getCustomerById(int customerId) {
        try {
            String sql = "SELECT * FROM customer WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet result = statement.executeQuery();

            CustomerModel model = new CustomerModel(customerId);
            while (result.next()) {
                model.setFirstName(result.getString("first_name"));
                model.setLastName(result.getString("last_name"));
                model.setAddress1(result.getString("address1"));
                model.setAddress2(result.getString("address2"));
                model.setCity(result.getString("city"));
                model.setState(result.getString("state"));
                model.setZipCode(result.getString("zip_code"));
                model.setEmail(result.getString("email"));
            }
            return model;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // We aren't creating new customers, but we are modifying existing customers' records (create login).
    // This is an update method
    public Boolean addNewCredentials(int customerId, int credentialId) {
        // Retrieve customer data by customer Id
        try {
            String sql = "UPDATE customer SET credential_id = ? WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, credentialId);
            statement.setInt(2, customerId);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    CustomerRepo() {
        this.connection = ConnectionManager.getConnection();
    }
}
