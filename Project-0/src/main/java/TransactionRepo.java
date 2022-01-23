import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRepo {
    private final Connection connection;

    // Retrieves all the transactions for a specified accountID.
    // Returns a CustomLinkedList with TransactionModel objects.
    // Returns null if it fails (ex. account does not exist).
    public CustomLinkedList<TransactionModel> retrieveTransactions(int accountId) {
        try {
            String sql = "SELECT * FROM transaction WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            ResultSet results = statement.executeQuery();
            CustomLinkedList<TransactionModel> transactions = new CustomLinkedList<>();

            while(results.next()) {
                TransactionModel model = new TransactionModel();
                model.setTransactionId(results.getInt("transaction_id"));
                model.setAccountId(results.getInt("account_id"));
                model.setAmount(results.getDouble("amount"));
                model.setType(results.getString("type"));
                model.setSource(results.getInt("source"));
                model.setDestination(results.getInt("destination"));
                model.setDate(results.getDate("date"));
                transactions.add(model);
            }
            return transactions;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Creates a new transaction (ex. a withdrawal was just performed).
    // Assumes calling method has already verified.
    // Returns a boolean indicating success (true/false)
    public Boolean addTransaction(TransactionModel model) {
        try {
            String sql = "INSERT INTO transaction (account_id, amount, type, source, destination) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getAccountId());
            statement.setDouble(2, model.getAmount());
            statement.setString(3, model.getType());
            statement.setInt(4, model.getSource());
            statement.setInt(5, model.getDestination());
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    TransactionRepo() {
        connection = ConnectionManager.getConnection();
    }
}
