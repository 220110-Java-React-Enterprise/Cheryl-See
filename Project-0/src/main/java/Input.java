// This class handles all the input and maybe adds some validation methods
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    private static Input input;
    private final static Scanner scanner = new Scanner(System.in);

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
    // TODO: But what of numbers?  Ex. "1" should be legal but not text11
    public String getString() {
        String input = scanner.nextLine();
        return input;
    }

    // Collects a currency value from the user.  This should only accept positive values (or zero).
    // Returns a negative value (-1.0) if some input error was detected (ex. gibberish, nonnumerical entry)
    // Note: This can return 0, so make sure to check if that is a valid input in the calling method.
    public Double getCurrency() {
        try {
            Double value = Double.parseDouble(scanner.nextLine());
            if (value >= 0) {
                // Attempting to round it to 2 decimal places
                // pattern parameter - Using "0.00" returns a String, but "#.##" returns a Double (that cuts off trailing 0's)
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                // This produces a string form of the rounded number - seems to be necessary intermediate step
                String roundedValue = decimalFormat.format(value);
                // This attempts to parse the string into a Number.
                Number number = decimalFormat.parse(roundedValue);
                // Converting the number into a double (also rounds)
                Double numericValue = number.doubleValue();

                return numericValue;
            }
        }
        catch (NumberFormatException | ParseException e) {
            // This catches the listed errors and returns -1.0 (placed below to keep IntellJ happy)
            // The -1.0 (error number) should be handled by the calling method.
        }
        return -1.0;
    }

    // No prompt is given in this method, but accepts an integer from the user, validates and returns the Integer.
    // If some error is detected, a -1 is returned and should be handled by the calling method.
    // Currently, this is being used for customerId entry in registerNewUser() and promptUserForAccountSelection().
    public Integer getInteger() {
        try {
            return Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    // Gets the username and verifies that it is composed of legal characters.
    // If it is invalid for some reason, it will inform the user of the username rules.
    // Not displayed, but the user can also enter quit, exit or cancel to leave this loop.
    public String getUsername() {
        do {
            System.out.print("Please enter a username: ");
            String input = getString();
            if (isValidUsername(input)) {
                return input;
            }
            else {
                if (input.equals("quit") || input.equals("cancel") || input.equals("exit")) {
                    return "";
                }
                System.out.println("Invalid username.  Usernames must be between 8 to 30 characters in length and can only consist of alphanumeric characters and some symbols (. _-@)");
            }
        } while (true);
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
            System.out.print("Please enter a password: ");
            String input = getString();

            if (isValidPassword(input)) {
                return input;
            }
            System.out.println("Passwords must be between 8 to 50 characters in length and can only consist of alphanumeric characters and some symbols (. _-@!?;~#)");
        } while(true);
    }


    // This is like the string method but only accepts checking type input (checking, savings)
    // Returns the account type (string).
    public String getCheckingType() {
        do {
            System.out.println("What type of account would you like to open?");
            System.out.println("1. Checking account");
            System.out.println("2. Savings account");
            System.out.print("I want to open: ");
            String text = getString();

            switch (text.toLowerCase()) {
                case "checking":
                case "1":
                case "one":
                    return "checking";
                case "savings":
                case "2":
                case "two":
                    return "savings";
                case "quit":
                case "cancel":
                case "exit":
                    return "";
                default:
                    System.out.println("Sorry, that is not an option.  Please select either 'checking' or 'savings'.");
            }
        } while (true);
    }

    // Gets a yes or no input from the user.
    // Returns a Boolean - true if "yes", false if "no"
    public Boolean getYesOrNo() {
        do {
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("> ");
            String input = getString();

            switch(input.toLowerCase()) {
                case "yes":
                case "1":
                    return true;
                case "no":
                case "2":
                    return false;
                default:
                    System.out.println("Invalid response.  Please respond with yes or no.");
            }
        } while (true);
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



    // (unused??) write a verifying method that takes a specified pattern and checks if the input is valid based on that.
    /*
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
    */
}