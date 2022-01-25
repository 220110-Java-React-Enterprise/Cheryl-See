import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


// Handles the connection between the AccountOwnerModel and account_owner table in the database.
public class AccountOwnerRepo {
    private final Connection connection;

    // Creates an entry for an account in the joint account table (required for any new accounts)
    // Returns a T/F based on success or failure
    public Boolean addAccountOwnerEntry(AccountOwnerModel model) {
        try {
            String sql = "INSERT INTO account_owner (account_id, customer_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getAccountId());
            statement.setInt(2, model.getCustomerId());
            statement.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // If a user looks up a specific account, this would be called to reflect who is the
    // owner(s) of a given account.  Accepts the accountOwnerId of the account and returns a linked list of owners (id).
    public CustomLinkedList<Integer> geCustomersForOwnerId(Integer ownerId) {
        try {
            String sql = "SELECT customer_id from account_owner WHERE (owner_id = ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, ownerId);
            ResultSet result = statement.executeQuery();
            CustomLinkedList<Integer> owners = new CustomLinkedList<>();
            while(result.next()) {
                owners.add(result.getInt("customer_id"));
            }
            return owners;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Retrieves all the accountIds for a given customerId
    // Returns a CustomLinkedList with the associated accounts or else returns null if an error occurred.
    // An empty linked list means that there are no accounts.
    public CustomLinkedList<Integer> getAccountsByCustomerId(Integer customerId) {
        try {
            String sql = "SELECT account_id FROM account_owner WHERE (customer_id = ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet result = statement.executeQuery();
            CustomLinkedList<Integer> accounts = new CustomLinkedList<>();

            while(result.next()) {
                accounts.add(result.getInt("account_id"));
            }
            return accounts;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AccountOwnerRepo() {
        connection = ConnectionManager.getConnection();
    }
}
