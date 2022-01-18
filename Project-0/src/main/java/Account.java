public class Account {
    Integer id;
    String type;
    Double balance;
    Integer customerId;
    // TODO: Transaction history link maybe
    // TODO: totalAccounts is temporary
    static Integer totalAccounts = 0;

    Account(Integer customerId) {
        this.id = getNewAccountId();
        this.type = "savings";
        this.balance = 0.00;
        this.customerId = customerId;
    }

    // TODO: This is only intended for testing?
    Account(String type, Double balance, Integer customerId) {
        this.id = getNewAccountId();
        this.type = type;
        this.balance = balance;
        this.customerId = customerId;
    }

    public void printAccountInformation() {
        System.out.println("Account ID: " + this.id);
        System.out.println("Type: " + this.type);
        // TODO: format currency
        System.out.println("Balance: " + balance);
    }

    // Retrieves the total accounts and generates the next account Id
    private Integer getNewAccountId() {
        totalAccounts++;
        // TODO: Maybe double check to make sure this account does not already exist.
        return totalAccounts;
    }

    private void doDeposit(Double amount) {
        // TODO: verify it is a legal amount of money
        balance += amount;
    }

    private void doWithdrawal(Double amount) {
        // TODO: definitely verify it is a legal amount of money
        balance -= amount;
    }

    private void doTransfer(Double amount, Account sourceId, Account destinationId) {

    }

    public Account createAccount() {
        // TODO: Prompt user for this stuff, initial deposit, verify
        return new Account("savings", 100.00, 1);
    }
}
