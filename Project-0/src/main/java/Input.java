// This class handles all the input and maybe adds some validation methods
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    private static Input input;
    private static Scanner scanner = new Scanner(System.in);

    public static Input getInputReference() {
        if (input == null) {
            input = new Input();
        }
        return input;
    }

    private Input(){}

    // Gets user input an returns a string.
    // TODO: Validation stuff and abstractions
    // This should be a different name? What if I want a more generic/unchecked one?
    // This will get a string and check that it only includes letters
    public static String getString() {
        String input = scanner.nextLine();
        return input;
    }

    // Not sure if this will be used.
    public Double getCurrency() {
        try {
            return Double.parseDouble(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            System.out.println("Error in getNumericalInput(): Not a number.");
            return -1.0;
        }
    }

    public Integer getInteger() {
        try {
            return Integer.parseInt(scanner.nextLine());
            // TODO: regex, make sure this is a whole number
        }
        catch (NumberFormatException e) {
            System.out.println("Debug: Error in getInteger(): Not a number.");
            return -1;
        }
    }

    // Gets the username and verifies that it is composed of legal characters.
    public String getUsername() {
        do {
            System.out.println("Usernames must be between 8 to 30 characters in length and can only consist of alphanumeric characters and some symbols (. _-@)");
            System.out.print("Please enter a username: ");
            String input = getString();

            if (isValidUsername(input)) {
                return input;
            }
            else {
                System.out.println("Invalid character detected.  Usernames must be between 8 to 30 characters in length and can only consist of alphanumeric characters and some symbols (. _-@)");
            }
        } while(true);
    }

    // Parse user's input - must be a valid username
    // Returns true if it is valid, false if it is not valid.
    private static Boolean isValidUsername(String username) {
        Pattern pattern = Pattern.compile("[^\\w_\\-.@]");
        Matcher m = pattern.matcher(username);
        boolean invalidCharacterPresent = m.find();

        if (invalidCharacterPresent || username.length() < 8 || username.length() > 30) {
            return false;
        } else {
            return true;
        }
    }

    // Gets user's password and verifies it is composed of legal characters.
    // Loops infinitely until a valid password is entered.
    // Returns a string between 8-30 characters and legal characters.
    public String getPassword() {
        do {
            System.out.println("Passwords must be between 8 to 50 characters in length and can only consist of alphanumeric characters and some symbols (. _-@!?;~#)");
            System.out.print("Please enter a password: ");
            String input = getString();

            if (isValidPassword(input)) {
                return input;
            }
        } while(true);
    }

    // Parse user's input - must be a valid password
    // Returns true if it is valid, false if it is not valid.
    private Boolean isValidPassword(String password) {
        // TODO: Check if this pattern is correct for passwords
        //System.out.println("Passwords must be between 8 to 50 characters in length and can only consist of alphanumeric characters and some symbols (. _-@!?;~#)");
        // Should actually modify it so it accepts more symbols and checks if the phrase contains each?
        // ex. (not working) [^\w_-.@!?;~#]
        Pattern pattern = Pattern.compile("[^\\w_\\-.@]");
        Matcher m = pattern.matcher(password);
        boolean invalidCharacterPresent = m.find();

        if (invalidCharacterPresent || password.length() < 8 || password.length() > 50) {
            return false;
        } else {
            return true;
        }
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