import java.sql.*;

public class AccountRepo {
    private final Connection connection;

    // Creates a new (bank) account given an AccountModel and the customerId of the owner(s).
    // Pass a -1 for owner2 if this is a singly held account.
    // Returns a boolean value depending on its success.
    public Integer create(AccountModel newAccount, Integer owner1Id, Integer owner2Id) {
        try {
            String sql = "INSERT INTO account (type, balance) VALUES (?, ?)";
            // Note to self: Stop trying to add ownerID here.  It isn't needed.
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newAccount.getType());
            statement.setDouble(2, newAccount.getBalance());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();

            // Retrieve the newest account_id (autogenerated)
            result.next();
            Integer accountId = result.getInt(1);

            // Now that we have the accountId - make the AccountOwner link
            // Using the blank constructor so the parameters aren't so ambiguous
            AccountOwnerModel owner1 = new AccountOwnerModel();
            owner1.setAccountId(accountId);
            owner1.setCustomerId(owner1Id);
            AccountOwnerRepo accountOwnersRepo = new AccountOwnerRepo();
            Boolean addOwnerResult1 = accountOwnersRepo.addAccountOwnerEntry(owner1);
            System.out.println("Debug: adding owner result: " + addOwnerResult1);

            // TODO: implement this (gotta look up second user)
            // This is a joint account with a second owner, so another entry is needed
            if (owner2Id > 0) {
                AccountOwnerModel owner2 = new AccountOwnerModel();
                owner2.setAccountId(accountId);
                owner2.setCustomerId(owner2Id);
                accountOwnersRepo.addAccountOwnerEntry(owner2);
            }

            return accountId;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Retrieves all the accounts for a specified customer (ID) in a CustomLinkedList.
    public CustomLinkedList<AccountModel> getAccounts(Integer customerId) {
        try {
            String sql = "SELECT account.account_id, account.balance, account.type FROM pzero.account JOIN account_owner ON account.account_id = account_owner.account_id WHERE account_owner.customer_id = ?;";
            // Can also use AccountOwnerRepo.getAccountsByCustomerId()
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet results = statement.executeQuery();
            CustomLinkedList<AccountModel> accounts = new CustomLinkedList<>();

            while(results.next()) {
                AccountModel model = new AccountModel();
                model.setAccountId(results.getInt("account_id"));
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
    public Boolean updateBalance(AccountModel model) {
        try {
            String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, model.balance);
            statement.setInt(2, model.accountId);
            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public AccountRepo() {
        connection = ConnectionManager.getConnection();
    }
}
