// Account acts as an abstraction for all the account-related menu options.
// This acts as an extension of MainMenu.

public class Account {
    CustomLinkedList<AccountModel> list;
    Input input;
    AccountRepo accountRepo = new AccountRepo();
    TransactionRepo transactionRepo = new TransactionRepo();

    // Withdraws money from an account.  Called after making a menu selection.
    // Prompts user to select an account, the amount to withdraw, and goes through validation/update process.
    public void withdraw() {
        // Get the amount to withdraw
        Double amount = input.getCurrency();
        // Check if this is a valid currency amount; if -1.0 some input error was detected.
        if (amount <= 0) {
            System.out.println("Only positive values are accepted.  Currency can only consist of numbers and a decimal.");
            return;
        }

        // Verify you have the correct amount
        System.out.println("You are withdrawing $" + amount + ".");
        System.out.println("From which account would you like to withdraw funds?");
        AccountModel account = promptUserForAccountSelection();

        // Check if user has cancelled transaction
        if (account == null) {
            return;
        }

        // See if person has the funds for this transaction
        if (doesUserHaveFundsIn(account, amount)) {
            if (doAccountBalanceUpdate(account, "withdraw", amount, account.getAccountId(), -1)) {
                System.out.println("You have withdrawn " + amount + ".  Your account has " + account.balance);
            }
            else {
                System.out.println("An error has occurred in doAccountBalanceUpdate().");
            }
        }
        else {
            System.out.println("Sorry, you do not have the funds to perform this transaction.");
            System.out.println("You currently have $" + account.getBalance() + " and are trying to remove $" + amount + ".");
        }
    }

    // Checks if user has the funds for a transaction in the specified account.
    // Does not apply to a deposit.
    // Returns a boolean if true (able to do so) or false if there is not enough funds.
    private Boolean doesUserHaveFundsIn(AccountModel account, Double amount) {
        Double currentBalance = account.getBalance();
        if (currentBalance - amount <= 0) {
            return false;
        }

        // User does have the funds for this transaction
        else {
            return true;
        }
    }

    // Does the math and performs the update/transactions
    // Should only be called once appropriate checks have been performed.
    // Account is the account being operated upon.  Type - deposit, withdraw, transfer
    // sourceId - account ID of where to transfer from, or -1 if self (deposit).
    // destinationId - accountID of where to transfer to, or -1 if self (withdraw).
    // For transfers, just send the account it is being added to (destination). (maybe logic double checks this - positive amount)
    // Returns a boolean if the transaction was successfully performed.
    private Boolean doAccountBalanceUpdate(AccountModel account, String type, Double amount, Integer source, Integer destination) {
        Double newBalance;
        Double transferFromBalance; // Only used in transfers
        switch (type) {
            case "withdraw":
                newBalance = account.getBalance() - amount;
                // Update the change locally then send to the accountRepo to make changes
                account.setBalance(newBalance);
                TransactionModel transaction = new TransactionModel(account.getAccountId(), amount, "withdraw", -1);
                if (doTransaction(account, transaction)) {
                    return true;
                }
                else {
                    System.out.println("Error: Unable to perform transaction in doTransaction()");
                    return false;
                }
            case "deposit":
                newBalance = account.getBalance() + amount;
                // Update the change locally then send to the accountRepo to make changes
                account.setBalance(newBalance);
                TransactionModel transaction2 = new TransactionModel(account.getAccountId(), amount, "deposit", -1);
                if (doTransaction(account, transaction2)) {
                    return true;
                }
                else {
                    System.out.println("Error: Unable to perform transaction in doTransaction()");
                    return false;
                }
            case "transfer":
                // Handle the deposit first - Maybe rewrite this, so it is handled as one larger transaction
                newBalance = account.getBalance() + amount;
                // Update the change locally then send to the accountRepo to make changes
                account.setBalance(newBalance);
                TransactionModel transactionDeposit = new TransactionModel(account.getAccountId(), amount, "transfer", -1);
                if (doTransaction(account, transactionDeposit)) {
                    // Attempt the second transaction / account update
                    AccountModel accountTransferredFrom = accountRepo.getAccountInformation(source);
                    newBalance = accountTransferredFrom.getBalance() - amount;
                    accountTransferredFrom.setBalance(newBalance);
                    // Get the absolute value for the "withdrawal" part
                    amount = 0 - amount;
                    TransactionModel transactionWithdraw = new TransactionModel(accountTransferredFrom.getAccountId(), amount, "transfer", account.getAccountId());
                    doTransaction(accountTransferredFrom, transactionWithdraw);
                    return true;
                }
                else {
                    System.out.println("Error: Unable to perform transaction in doTransaction()");
                    return false;
                }
            default:
                return false;
        }
    }

    // Performs the transactions given accountModel and TransactionModel.
    // All checks should be performed before calling this method.
    private Boolean doTransaction(AccountModel account, TransactionModel transaction) {
        if (accountRepo.updateBalance(account)) {
            if (transactionRepo.addTransaction(transaction)) {
                return true;
            }
            else {
                System.out.println("An error has occurred adding the transaction.");
            }
        }
        else {
            System.out.println("An error has occurred when updating the account balance.");
        }
        return false;
    }

    // Transfers money from an account
    public void transfer() {
        // Get the amount to transfer
        Double amount = input.getCurrency();
        // Check if this is a valid currency amount; if -1.0 some input error was detected.
        if (amount <= 0) {
            System.out.println("Only positive values are accepted.  Currency can only consist of numbers and a decimal.");
            return;
        }

        // Verify you have the correct amount
        System.out.println("You are transferring $" + amount + ".");
        System.out.println("To which account would you like to transfer your funds?");
        AccountModel accountDestination = promptUserForAccountSelection();

        // Check if user has cancelled transaction
        if (accountDestination == null) {
            return;
        }

        // Prompt a second time to get the source account
        System.out.println("From which account would you like to transfer your funds?");
        AccountModel accountSource = promptUserForAccountSelection();

        // Check if user has cancelled transaction
        if (accountSource == null) {
            return;
        }

        // See if person has the funds for this transaction
        if (doesUserHaveFundsIn(accountSource, amount)) {
            if (doAccountBalanceUpdate(accountDestination, "transfer", amount, accountSource.getAccountId(), accountDestination.getAccountId())) {
                System.out.println("You have transferred " + amount + ".  Your account has " + accountDestination.getBalance());
            }
            else {
                System.out.println("An error has occurred in doAccountBalanceUpdate().");
            }
        }
        else {
            System.out.println("Sorry, you do not have the funds to perform this transaction.");
            System.out.println("You currently have $" + accountSource.getBalance() + " and are trying to transfer $" + amount + ".");
        }
    }

    // Allows user to deposit to an account
    // Prompts user to select an account, the amount to deposit, and goes through update process.
    public void deposit() {
        // Get the amount to withdraw
        Double amount = input.getCurrency();
        // Check if this is a valid currency amount; if -1.0 some input error was detected.
        if (amount <= 0) {
            System.out.println("Only positive values are accepted.  Currency can only consist of numbers and a decimal.");
            return;
        }

        // Verify you have the correct amount
        System.out.println("You are depositing $" + amount + ".");
        System.out.println("To which account would you like to deposit your funds?");
        AccountModel account = promptUserForAccountSelection();

        // Check if user has cancelled transaction
        if (account == null) {
            return;
        }

        // Since we are freely printing money - skip validation and go straight to adding money
        if (doAccountBalanceUpdate(account, "deposit", amount, -1, account.getAccountId())) {
            System.out.println("You have deposited " + amount + ".  Your account has " + account.getBalance() + ".");
        }
        else {
            System.out.println("An error has occurred.  No deposit has been made.");
        }
    }

    // Goes through the list of the customer's accounts and retrieves the balances.
    // Intended for use as a high level overview of account information.
    public void printAllAccountData() {
        for (int i=0; i<list.size(); i++) {
            AccountModel account = list.get(i);
            System.out.print("AccountNumber\t\tAccount Type\t\tBalance");
            System.out.printf("%d\t\t%s\t\t%.2f%n", account.getAccountId(), account.getType(), account.getBalance());
        }
    }

    // Retrieves the transaction history for the specified account.
    // Prompts user and prints a list of transactions for the selected account.
    public void transactionHistory() {
        System.out.println("For which account would you like to see the transaction history?");
        AccountModel account = promptUserForAccountSelection();
        if (account == null) {
            System.out.println("Going back to main menu.");
            return;
        }
        CustomLinkedList<TransactionModel> transactions = transactionRepo.retrieveTransactions(account.getAccountId());

        // Iterate and print the transactions
        for(int i=0; i<transactions.size(); i++) {
            TransactionModel transaction = transactions.get(i);
            System.out.print("Transaction #\t\tDate\t\tType\t\tAmount\t\tSource\t\tDestination");
            String source, destination;
            if (transaction.getSource() == -1) {
                source = "";
            }
            else {
                source = transaction.getSource().toString();
            }

            if (transaction.getDestination() == -1) {
                destination = "";
            }
            else {
                destination = transaction.getDestination().toString();
            }

            System.out.printf("%d\t\t%s\t\t%.2f\t\t%s\t\t%s%n", transaction.getTransactionId(), transaction.getDate(), transaction.getAmount(), source, destination);
        }
    }

    // This displays a list of the user's accounts and asks the user to select an account.
    // Returns the AccountModel of the selected account, or returns null if the user chooses to cancel the transaction.
    // Note: returns null if the user selects "cancel transaction".
    public AccountModel promptUserForAccountSelection() {
        // List the user's accounts as well as a "quit" choice
        for (int i=1; i<list.size()+1; i++) {
            if (i==list.size()) {
                System.out.println(i + ". Cancel withdrawal.");
            }
            else {
                AccountModel acct = list.get(i);
                System.out.println(i + ". " + acct.getType() + " account #" + acct.getAccountId());
            }
        }

        // Prompt user for a menu (account) selection
        Integer accountIndex = input.getInteger();
        if (accountIndex == list.size()) {
            System.out.println("Cancelling transaction.");
            return null;
        }

        else {
            // Verify account exists and retrieve data
            // Is it bad that this is pulling from the db instead of a local copy?
            Integer accountNumber = list.get(accountIndex).getAccountId();
            AccountModel account = accountRepo.getAccountInformation(accountNumber);
            if (account == null) {
                System.out.println("Error: account unable to be retrieved.");
                return null;
            }
            else {
                return account;
            }
        }
    }

    Account(int customerID) {
        // retrieve the customer's accounts
        AccountRepo accountRepo = new AccountRepo();
        list = accountRepo.getAccounts(customerID);
        input = Input.getInputReference();
    }
}
