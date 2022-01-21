import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepo {
    private final Connection connection;

    // Retrieves all the accounts for a specified customer (ID) in a CustomLinkedList.
    public CustomLinkedList<AccountModel> getAccounts(Integer customerId) {
        try {
            String sql = "SELECT * FROM account WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet results = statement.executeQuery();
            CustomLinkedList<AccountModel> accounts = new CustomLinkedList<>();

            while(results.next()) {
                // TODO: Check this
                AccountModel model = new AccountModel();
                model.setAccountId(results.getInt("account_id"));
                model.setCustomerId(results.getInt("customer_id"));
                model.setBalance(results.getDouble("balance"));
                model.setType(results.getString("type"));
                accounts.add(model);
            }
            return accounts;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Given the account number, it returns the account information.
    public AccountModel getAccountInformation(Integer accountId) {
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet result = statement.executeQuery();
            AccountModel model = new AccountModel();

            while(result.next()) {
                model.setAccountId(result.getInt("account_id"));
                model.setCustomerId(result.getInt("customer_id"));
                model.setBalance(result.getDouble("balance"));
                model.setType(result.getString("type"));
            }
            return model;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // We are not creating new accounts, but we are updating them (balance)
    // updateBalance assumes that this update has been verified/approved through other methods first.
    public AccountModel updateBalance(AccountModel model) {
        try {
            String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, model.balance);
            statement.setInt(2, model.accountId);
            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public AccountRepo() {
        connection = ConnectionManager.getConnection();
    }
}
