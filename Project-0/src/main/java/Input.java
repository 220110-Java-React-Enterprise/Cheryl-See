// This class handles all the input and maybe adds some validation methods
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    Scanner scanner = new Scanner(System.in);
    Input() {
    }

    public String getInput() {
        String input = scanner.nextLine();
        return input;
    }

    public Integer getNumericalInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            System.out.println("Error in getNumericalInput(): Not a number.");
            return -1;
        }
    }

    // Gets the username and verifies that it is composed of legal characters.
    public String getUsername() {
        boolean isValidUsername = false;
        do {
            String input = getInput();
            isValidUsername = checkIfValidUserName(input);
            if (isValidUsername) {
                // may not be necessary
                isValidUsername = true;
                return input;
            }
            else {
                System.out.println("Invalid character detected.  Usernames can only consist of alphanumeric characters and some symbols (. _-@)");
            }
        } while(!isValidUsername);

        // This is just to keep the compiler happy.
        return "";
    }

    // Parse user's input - must be a valid username
    // Returns true if it is valid, false if it is not valid.
    private Boolean checkIfValidUserName(String username) {
        Pattern pattern = Pattern.compile("[^\\w_\\-.@]");
        Matcher m = pattern.matcher(username);
        boolean invalidCharacterPresent = m.find();

        if (invalidCharacterPresent) {
            return false;
        } else {
            return true;
        }
    }

    // Gets user's password and verifies it is composed of legal characters.
    public String getPassword() {
        boolean isValidPassword = false;
        do {
            String input = getInput();
            isValidPassword = checkIfValidPassword(input);
            if (isValidPassword) {
                // may not be necessary
                isValidPassword = true;
                return input;
            }
            else {
                // TODO: Adjust this for password-specific characters
                System.out.println("Invalid character detected.  Usernames can only consist of alphanumeric characters and some symbols (. _-@)");
            }
        } while(!isValidPassword);

        // This is just to keep the compiler happy.
        return "";
    }

    // Parse user's input - must be a valid password
    // Returns true if it is valid, false if it is not valid.
    private Boolean checkIfValidPassword(String username) {
        // TODO: Check if this pattern is correct
        // Should actually modify it so it accepts more symbols and checks if the phrase contains each?
        // ex. (not working) [^\w_-.@!?;~#]
        Pattern pattern = Pattern.compile("[^\\w_\\-.@]");
        Matcher m = pattern.matcher(username);
        boolean invalidCharacterPresent = m.find();

        if (invalidCharacterPresent) {
            return false;
        } else {
            return true;
        }
    }

    // This will get a string and check that it only includes letters
    public String getString() {
        return "";
    }

    // write a verifying method that takes a specified pattern and checks if the input is valid based on that.
    private Boolean checkIfValidCharacters(String type, String input) {
        Pattern pattern;
        switch(type) {
            case "username": {
                pattern = Pattern.compile("[^\\w_\\-.@]");
                break;
            }
            case "password": {
                pattern = Pattern.compile("[^\\w_!?#$'~,%;.@\\-^]");
                break;
            }
            case "alphanumeric": {
                pattern = Pattern.compile("[^\\w .,-]");
                break;
            }
            // Only letters are allowed
            case "letters":
            default: {
                pattern = Pattern.compile("[^a-zA-Z]");
            }
        }

        Matcher m = pattern.matcher(input);
        boolean invalidCharacterPresent = m.find();
        if (invalidCharacterPresent) {
            return false;
        }
        else {
            return true;
        }
    }
}



/*
Use this for password check
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
 */