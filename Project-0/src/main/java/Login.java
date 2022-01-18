import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {
    String username = "";
    String password = "";
    String customerId = "";

    // display the menu and prompt user for input
    // returns the user's customer Id as an integer.
    public User doLogin() {
        User customer;
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

                case 4: {
                    // TODO: This option shouldn't exist.  Only for debugging linked list.
                    System.out.println("Testing linked list.");
                    CustomLinkedList<Integer> list = new CustomLinkedList<>();
                    // Populate list
                /*
                for(int i=1; i<11; i++) {
                    list.add(i);
                }
                */
                    list.add(1);
                    list.testPrintAllElements();
                    System.out.println("original size: " + list.size());
                    list.remove(0);
                    System.out.println("new size: " + list.size());
                    list.testPrintAllElements();
                    break;
                } // Remove this

                default: {
                    System.out.println("Error: Not a valid option.");
                    break;
                }
            }

        } while (true);
    }

    private Integer getUserMenuSelection() {
        displayLoginMenu();
        System.out.print("Your selection: ");
        Scanner s = new Scanner(System.in);

        // Parse user's input
        if (s.hasNextInt()) {
            return s.nextInt();
        } else {
            return parseInput(s.nextLine());
        }
    }

    // Collects the information to create a new user.
    private User registerNewUser() {
        // Collect data
        System.out.print("First name: ");
        String firstName = getUserStringInput();
        System.out.print("Last name: ");
        String lastName = getUserStringInput();
        System.out.print("Address line 1: ");
        String address1 = getUserStringInput();
        System.out.print("Address line 2:");
        String address2 = getUserStringInput();
        System.out.print("City: ");
        String city = getUserStringInput();
        System.out.print("State: ");
        String state = getUserStringInput();
        System.out.print("Zip code: ");
        Integer zipCode = getUserNumericalInput();
        System.out.print("Username: ");
        String username = getUserStringInput();
        System.out.print("Password: ");
        String password = getUserStringInput();


        User newUser = new User(firstName, lastName, address1, address2, city, state, zipCode);
        this.username = username;
        this.password = password;



        return newUser;
    }

    // This gets input for registration
    private String getUserStringInput() {
        Scanner input = new Scanner(System.in);
        if (input.hasNextLine()) {
            return input.nextLine();
        }
        return "";
    }

    // Also gets input for registration - integers only
    private Integer getUserNumericalInput() {
        Scanner input = new Scanner(System.in);
        if(input.hasNextInt()) {
            return input.nextInt();
        }
        return -1;
    }

    // Collects an existing user's login credentials
    private User getLoginCredentials() {
        // Dummy data for testing
        String username = "bankuser";
        String password = "password";

        System.out.println("Please enter your username: ");
        // a-z, A-Z, numbers, [.-_ @]
        Scanner s1 = new Scanner(System.in);
        //s1.useDelimiter("\n");
        String usernameInput = s1.nextLine();
        //System.out.println("The input: ===" + input + "===");

        // Parse user's input - must be a valid username
        Pattern pattern = Pattern.compile("[^\\w_\\-.@]");
        Matcher m = pattern.matcher(usernameInput);
        boolean invalidCharacterPresent = m.find();

        if (invalidCharacterPresent) {
            System.out.println("Invalid character detected.  Usernames can only consist of alphanumeric characters and some symbols (. _-@)");
        } else {
            System.out.println("Please enter your password: ");
            //s1.reset();
            //String passwordInput = s1.nextLine();
            Scanner s2 = new Scanner(System.in);
            String passwordInput = s2.nextLine();
            if (passwordInput.equals(password)) {
                // TODO: The following should be the user's customer ID, this is a dummy value
                User customer = new User(1);
                return customer;
            } else {
                System.out.println("Sorry, the username and password combination were incorrect.  Please try again.");
            }
        }
        //s1.close();
        return null;
    }

    public static void displayLoginMenu() {
        System.out.println("Do you have an online account already?");
        System.out.println("1. Yes, I am an existing user.");
        System.out.println("2. No, I do not have an online account yet.");
        System.out.println("3. I would like to exit the program.");
    }

    // Translate string input to an integer input
    // Returns -1 if the input is invalid.
    private static int parseInput(String input) {
        // Checking for some common words in the login menu
        String[] wordList1 = new String[]{"existing", "returning", "login", "one", "1"};
        String[] wordList2 = new String[]{"new", "create", "register", "sign", "signup", "two", "2"};
        String[] wordList3 = new String[]{"quit", "exit", "leave", "close", "end", "stop", "signout", "logoff", "logout", "escape", "three", "3"};
        for (String loginWord : wordList1) {
            if (input.equals(loginWord)) {
                return 1;
            }
        }

        for (String registerWord : wordList2) {
            if (input.equals(registerWord)) {
                return 2;
            }
        }

        for (String quitWord : wordList3) {
            if (input.equals(quitWord)) {
                return 3;
            }
        }

        //System.out.println("String input detected: returning placeholder value.");
        // Decide on legal values for characters
        // a-z, A-Z
        // Some non-input was selected.
        return -1;
    }
}

/* Test removal:

System.out.println("Testing linked list.");
                CustomLinkedList<Integer> list = new CustomLinkedList<>();
                // Populate list
                for(int i=1; i<11; i++) {
                    list.add(i);
                }
                System.out.println("original size: " + list.size());
                list.clear();
                System.out.println("new size: " + list.size());
 */