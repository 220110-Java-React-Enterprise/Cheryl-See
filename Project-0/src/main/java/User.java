public class User {
    // The user class will track the currently logged in user
    Integer customerId;
    String firstName;
    String lastName;
    String address1;
    String address2;
    String city;
    String state;
    Integer zipCode;

    CustomLinkedList<Account> accounts;
    // TODO: temporary
    static Integer userCount = 0;

    // This constructor is intended to be used by registration
    User(String firstName, String lastName, String address1, String address2, String city, String state, Integer zipCode) {
        // CustomerId should be populated by figuring out which is the next unused id
        this.customerId = getNewCustomerId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;

        // TODO: Store the customer data.  It shouldn't be needed further but needs to be persisted.
        this.accounts = retrieveAccounts(customerId);
    }

    // This constructor is intended for use by returning users
    User(Integer customerId) {
        // lookup customer information
        // TODO: Fix this.  Currently dummy data
        this.firstName = "Bob";
        this.lastName = "Smith";
        this.address1 = "123 Main Street";
        this.address2 = "";
        this.city = "New York";
        this.state = "NY";
        this.zipCode = 12345;
        this.accounts = retrieveAccounts(customerId);
        // Does the username/password need to be stored still?  Maybe just to cache it?
    }

    // Brand new registering user
    User() {
    }

    // TODO: Retrieve customer data from database
    // Looks up the customer ID and retrieves the customer's accounts
    private CustomLinkedList<Account> retrieveAccounts(Integer customerId) {
        // This is dummy data
        Account account1 = new Account("checking", 2.50, customerId);
        Account account2 = new Account("checking", 300.21, customerId);
        Account account3 = new Account(customerId);

        CustomLinkedList<Account> accounts = new CustomLinkedList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        return accounts;
    }

    public void printAccounts() {
        for(int i=0; i<accounts.size(); i++) {
            Account account = accounts.get(i);
            account.printAccountInformation();
        }
    }

    // TODO: Retrieve the last known customer ID / total # of customerIds
    // retrieves the last customerId and returns the next one.
    private Integer getNewCustomerId() {
        userCount++;
        return userCount;
    }
}
