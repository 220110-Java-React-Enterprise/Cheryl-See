
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
                    //System.out.println("Existing user.");
                    customer = getLoginCredentials();
                    if (customer != null) {
                        return customer;
                    }
                    break;
                }
                case 2: {
                    //System.out.println("New user.");
                    customer = registerNewUser();
                    if (customer != null) {
                        return customer;
                    }
                    break;
                }
                case 3: {
                    System.out.println("Exiting program.  Have a nice day!");
                    return null;
                }

                default: {
                    System.out.println("Error: Not a valid option.");
                    break;
                }
            }
        } while (true);
    }

    // Displays the main login menu and prompts the user to select a choice.
    // Returns the selection in integer form.  Meant for use with doLogin().
    private Integer getUserMenuSelection() {
        System.out.println("\nDo you have an online account already?");
        System.out.println("1. Yes, I am an existing user.");
        System.out.println("2. No, I do not have an online account yet.");
        System.out.println("3. I would like to exit the program.");
        System.out.print("Your selection: ");
        String string = input.getString();
        return parseInput(string);
    }

    // Translate string input (number or text) to an integer input (menu choice)
    // Returns -1 if the input is invalid.
    private int parseInput(String string) {
        // Checking for some common words in the login menu
        String[] wordList1 = new String[]{"existing", "returning", "login", "one", "1"};
        String[] wordList2 = new String[]{"new", "create", "register", "sign", "signup", "two", "2"};
        String[] wordList3 = new String[]{"quit", "exit", "leave", "close", "end", "stop", "signout", "logoff", "logout", "escape", "three", "3"};
        String[][] wordLists = new String[][]{wordList1, wordList2, wordList3};

        Integer selection = 1;
        for (String[] list : wordLists) {
            for (String word : list) {
                if (string.equals(word)) {
                    return selection;
                }
            }
            selection++;
        }
        return -1;
    }

    // Collects an existing user's login credentials and attempts to log them in.
    // Returns the customer's data if found; else it returns null and the user will go through the login again.
    // Attempts the process three times before returning to the login menu.
    private CustomerModel getLoginCredentials() {
        for (int attempts=3; attempts > 0; attempts--) {
            String username = input.getUsername();
            if (username == null) {
                System.out.println("Cancelling login.");
                return null;
            }
            String password = input.getPassword();
            if (password == null) {
                System.out.println("Cancelling login.");
                return null;
            }
            //CredentialRepo credentialRepo = new CredentialRepo();
            CredentialModel credentials = credentialRepo.getByCredentials(username, password);
            // Credentialrepo generates a new credentials object and returns it, so check if has anything

            if (credentials != null) {
                //CustomerRepo customerRepo = new CustomerRepo();
                CustomerModel customer = customerRepo.getCustomerById(credentials.getCustomerId());
                // final check just to make sure customer exists, even though it should
                if (customer != null) {
                    return customer;
                }
            }
            System.out.println("Sorry, the username and password did not work.  You have " + (attempts-1) + " attempts remaining.");
        }
        return null;
    }

    // Prompts the user for a customer ID, username, and password and saves their credentials.
    // Returns the customer's data if found; else it returns null and the user will go through the login again.
    // This attempts the registration process three times before returning to the login menu.
    private CustomerModel registerNewUser() {
        System.out.println("Are you an existing customer and have your customer ID # on hand?");
        if (input.getYesOrNo()) {
            // Customer has their ID number - do lookup by id and register
            System.out.println("Please enter your customer account number: ");
            Integer customerId = input.getInteger();

            // Lookup this id number and retrieve the CustomerModel record if it exists.
            CustomerModel user = customerRepo.getCustomerById(customerId);
            // A record was retrieved - see if it is the user
            if (user != null) {
                System.out.print("What is your first name?: ");
                String firstName = input.getName();
                System.out.print("What is your last name?: ");
                String lastName = input.getName();

                // User has matched their name correctly with the record / supplied customer ID
                if (firstName.equals(user.getFirstName()) && lastName.equals(user.getLastName())) {
                    // Check if the user already has login credentials
                    setCustomer(user);
                    if (user.getCredentialId() != null) {
                        // User already has login credentials
                        CredentialModel credentials = credentialRepo.getCredentialsByCustomerId(customerId);
                        System.out.println("Hello, " + firstName + ", you already have a username and password.");
                        System.out.println("Your username is: " + credentials.getUsername());
                        return null;
                    }
                    // User has no login credentials, but customer record has been located
                    else {
                        // The user does not have login credentials yet, proceed with credential registration
                        createCredentials();
                        return customer;
                    }
                }
                // User was unable to match personal data with what bank has for that customerId - proceed like new account
                else {
                    System.out.println("Sorry, your information does not match what is on record for that customer ID.");
                    System.out.println("Please try again, or open a new account.");
                    return null;
                }
            } else {
                System.out.println("Sorry, we were unable to locate a customer with that ID number.");
                System.out.println("Please try again, or open a new account.");
                return null;
                // Proceed to normal (bank account) registration
            }
        }
        // User is either new to the bank or does not have the customer ID number.
        else {
            CustomerModel newCustomer = new CustomerModel();
            System.out.println("Let's create a new customer record for you.");
            newCustomer.createNewCustomerRecord();
            Integer customerId = customerRepo.addNewCustomer(newCustomer);
            newCustomer.setCustomerId(customerId);
            this.setCustomer(newCustomer);
            System.out.println("You have been registered as a customer.  Your customer ID# is " + customerId);
        }

        // Checking if error occurred or user wanted to cancel
        if (customer == null) {
            return null;
        }

        // Prompts the user to create login credentials (reference saved to Login.credentials)
        createCredentials();
        return customer;
    }


    // Prompts user for their username, password, and an email address.
    // Creates a CredentialModel and assigns it to Login.credentials
    // Should only be called once Login.customer has been populated
    private void createCredentials() {
        if (customer == null) {
            System.out.println("Error: Do not user createCredentials() without retrieving/creating customer information first.");
        }
        System.out.println("Creating new login credentials.");
        System.out.println("Usernames must be between 8 to 30 characters in length and can only consist of alphanumeric characters and some symbols (. _-@)");
        String username = input.getUsername();

        if (username == null) {
            System.out.println("Cancelling registration.");
            return;
        }

        // Check if username is already in use
        while (credentialRepo.usernameIsInUse(username)) {
            System.out.println("Sorry, this username is already in use.  Please select another username.");
            username = input.getUsername();
            if (username == null) {
                System.out.println("Cancelling registration.");
                return;
            }
        }

        System.out.println("Passwords must be between 8 to 50 characters in length and can only consist of alphanumeric characters and some symbols (. _-@!?;~#)");
        String password = input.getPassword();

        if (input.userInitiatedCancel(password)) {
            return;
        }

        // Get the user's email address if there is none on record
        if (customer.getEmail() == null) {
            customer.setEmail(input.getEmail());
            customerRepo.addEmailAddress(customer);
        }

        // Set login credentials
        // Customer data has been located, add their credentials to the database
        credentials = new CredentialModel(customer.getCustomerId(), username, password);
        Integer newCredentialId = credentialRepo.register(credentials);

        if (newCredentialId != null) {
            Boolean success = customerRepo.addNewCredentials(customer.getCustomerId(), newCredentialId);
            return;
        }
        else {
            System.out.println("Error: Some error occurred when adding credentials.");
            return;
        }
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    Login() {
        this.input = Input.getInputReference();
        this.customerRepo = new CustomerRepo();
        this.credentialRepo = new CredentialRepo();
    }
}


