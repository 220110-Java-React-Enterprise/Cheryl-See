
// The login class is an abstraction that gets the user's login credentials and retrieves their customer information.
// When this process is complete, the customer's data should be retrieved and user should be logged in.
public class Login {
    CustomerModel customer;
    CustomerRepo customerRepo;
    CredentialRepo credentialRepo;
    CredentialModel credentials;
    Input input;

    // This is the login menu.  Prompts user for a menu choice and guides them through the login process.
    // When complete it returns the customer's data.
    public CustomerModel doLogin() {
        do {
            // storing the User in a separate variable so 'null' forces it through login loop unless 'quit' selected
            switch (getUserMenuSelection()) {
                case 1: {
                    System.out.println("Existing user.");
                    customer = getLoginCredentials();
                    if (customer != null) {
                        return customer;
                    }
                    break;
                }
                case 2: {
                    System.out.println("New user.");
                    customer = registerNewUser();
                    if (customer != null) {
                        return customer;
                    }
                    break;
                }
                case 3: {
                    System.out.println("Exit Program.");
                    return null;
                }

                default: {
                    System.out.println("Error: Not a valid option.");
                    break;
                }
            }
        } while (true);
    }

    private Integer getUserMenuSelection() {
        System.out.println("Do you have an online account already?");
        System.out.println("1. Yes, I am an existing user.");
        System.out.println("2. No, I do not have an online account yet.");
        System.out.println("3. I would like to exit the program.");
        System.out.print("Your selection: ");
        String string = Input.getString();
        return parseInput(string);
    }

    // Translate string input (number or text) to an integer input (menu choice)
    // Returns -1 if the input is invalid.
    private int parseInput(String string) {
        // Checking for some common words in the login menu
        String[] wordList1 = new String[]{"existing", "returning", "login", "one", "1"};
        String[] wordList2 = new String[]{"new", "create", "register", "sign", "signup", "two", "2"};
        String[] wordList3 = new String[]{"quit", "exit", "leave", "close", "end", "stop", "signout", "logoff", "logout", "escape", "three", "3"};
        for (String loginWord : wordList1) {
            if (string.equals(loginWord)) {
                return 1;
            }
        }

        for (String registerWord : wordList2) {
            if (string.equals(registerWord)) {
                return 2;
            }
        }

        for (String quitWord : wordList3) {
            if (string.equals(quitWord)) {
                return 3;
            }
        }

        //System.out.println("String input detected: returning placeholder value.");
        // Decide on legal values for characters
        // a-z, A-Z
        // Some non-input was selected.
        return -1;
    }

    // Collects an existing user's login credentials and attempts to log them in.
    // Returns the customer's data if found; else it returns null and the user will go through the login again.
    // Attempts the process three times before returning to the login menu.
    private CustomerModel getLoginCredentials() {
        System.out.println("Please enter your username: ");

        for (int attempts=3; attempts > 0; attempts--) {
            String username = input.getUsername();
            String password = input.getPassword();
            //CredentialRepo credentialRepo = new CredentialRepo();
            CredentialModel credentials = credentialRepo.getByCredentials(username, password);
            if (credentials != null) {
                //CustomerRepo customerRepo = new CustomerRepo();
                CustomerModel customer = customerRepo.getCustomerById(credentials.getCustomerId());
                // final check just to make sure customer exists, even though it should
                if (customer != null) {
                    return customer;
                }
            }
            System.out.println("Sorry, the username and password did not work.  You have " + attempts + " remaining.");
        }
        return null;
    }

    // Prompts the user for a customer ID, username, and password and saves their credentials.
    // Returns the customer's data if found; else it returns null and the user will go through the login again.
    // This attempts the registration process three times before returning to the login menu.
    private CustomerModel registerNewUser() {
        for (int i=0; i<3; i++) {
            System.out.println("Please enter your customer account number: ");
            Integer customerId = input.getInteger();
            if (customerId >= 0) {
                // Valid account number found, retrieving customer data
                customer = customerRepo.getCustomerById(customerId);

                if (customer != null) {
                    String username = input.getUsername();
                    String password = input.getPassword();
                    // Customer data has been located, add their credentials to the database
                    credentials = new CredentialModel(customerId, username, password);
                    credentialRepo.register(credentials);
                    return customer;
                }
                else {
                    System.out.println("Debug: Error: Customer number " + customerId + " not found.");
                }
            }
        }

        // User was unable to register for some reason/ too many invalid attempts
        System.out.println("Registration cancelled.");
        return null;
    }

    Login() {
        this.input = Input.getInputReference();
        this.customerRepo = new CustomerRepo();
        this.credentialRepo = new CredentialRepo();
    }
}

