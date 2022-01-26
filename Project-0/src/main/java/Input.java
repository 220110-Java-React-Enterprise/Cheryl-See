import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// This class handles all the input and most of the validation.  Most functions either return
// null or -1 if bad input is detected, though some loop and force re-entry until satisfied.
// Those typically include accepting "cancel" or "quit" to exit the loop.
// There are also a few input-related helper methods.

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

    // Making this a helper function because apparently I don't like using the scanner.
    // Gets input (not validated) and returns the string.
    public String getInput() {
        return scanner.nextLine();
    }

    // Gets user input (alphanumeric only, no length requirements) and returns the string.
    // Returns an empty string if invalid input (ex. symbols) were detected.
    public String getString() {
        String input = getInput();
        if (isValidInputOfType("alphanumeric", input)) {
            return input;
        }
        else {
            return "";
        }
    }

    // Similar to getString, this gets a user's name, validates that it is only letters and returns a capitalized string.
    // Intended for use with registration / new customer record.
    public String getName() {
        do {
            System.out.print("> ");
            String name = input.getInput();
            if (userInitiatedCancel(name)) {
                System.out.println("Cancelling registration.");
                return null;
            }
            if (isValidInputOfType("name", name)) {
                return capitalize(name);
            }
            else {
                System.out.println("Invalid characters detected.  Please try again.");
            }
        } while(true);
    }

    // Collects a currency value from the user.  This should only accept positive values (or zero).
    // Returns a negative value (-1.0) if some input error was detected (ex. gibberish, nonnumerical entry)
    // Note: This can return 0, so make sure to check if that is a valid input in the calling method.
    public Double getCurrency() {
        try {
            String input = getInput();
            Double value;
            if (isValidInputOfType("currency", input)) {
                value = Double.parseDouble(input);
                if (value >= 0) {
                    // Attempting to round it to 2 decimal places
                    // pattern parameter - Using "0.00" returns a String, but "#.##" returns a Double (that cuts off trailing 0's)
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    // This produces a string form of the rounded number - seems to be necessary intermediate step
                    String roundedValue = decimalFormat.format(value);
                    // This attempts to parse the string into a Number.
                    Number number = decimalFormat.parse(roundedValue);
                    // Converting the number into a double (also rounds)
                    return number.doubleValue();
                }
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
        do {
            String input = getInput();
            if (isValidInputOfType("integer", input)) {
                return Integer.parseInt(input);
            }
            else {
                System.out.println("Please enter a valid number.");
            }
        } while(true);
    }

    // Gets the username and verifies that it is composed of legal characters.
    // If it is invalid for some reason, it will inform the user of the username rules.
    // Not displayed, but the user can also enter quit, exit or cancel to leave this loop. (null is returned)
    public String getUsername() {
        do {
            System.out.print("Please enter a username: ");
            String input = getInput();
            if (isValidInputOfType("username", input)) {
                return input;
            }
            else {
                if (userInitiatedCancel(input)) {
                    return "";
                }
                System.out.println("Invalid username.  Usernames must be between 8 to 30 characters in length and can only consist of alphanumeric characters and some symbols.");
            }
        } while (true);
    }

    // Gets user's password and verifies it is composed of legal characters.
    // Loops infinitely until a valid password is entered.
    // Returns a string between 8-30 characters and legal characters.
    public String getPassword() {
        do {
            System.out.print("Please enter a password: ");
            String input = getInput();

            if (isValidInputOfType("password", input)) {
                return input;
            }
            System.out.println("Passwords must be between 8 to 50 characters in length and can only consist of alphanumeric characters and some symbols.");
        } while(true);
    }

    // This is like the string method but only accepts checking type input (checking, savings)
    // Returns the account type (string) or null if the user cancelled entry.
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
                    return null;
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
                case "y":
                case "1":
                    return true;
                case "no":
                case "n":
                case "2":
                    return false;
                default:
                    System.out.println("Invalid response.  Please respond with yes or no.");
            }
        } while (true);
    }

    // Checks email input for a valid pattern - ex. *@*.* - and also requires verification of the email address (spelling).
    // Returns the string (email) if valid, else it will return an empty string "".
    public String getEmail() {
        do {
            System.out.print("Please enter your email address: ");
            String input = getInput();
            if (isValidInputOfType("email", input)) {
                // Do a second check (confirming email address)
                String emailRetyped = "";
                do {
                    System.out.print("Please re-enter your email address: ");
                    emailRetyped = getInput();
                    if (input.equals(emailRetyped)) {
                        return input;
                    }
                    else {
                        System.out.println("The email address you entered does not match what you already entered.");
                    }
                } while (true);
            }
            else {
                System.out.println("Invalid e-mail address entered.  Please try again.");
            }
        } while (true);
    }

    // Checks whether a user entered input that meant cancelling out of the current menu.
    // Returns a true if it finds something like cancel, false otherwise.
    public Boolean userInitiatedCancel(String input) {
        if (input.equals("quit") || input.equals("cancel") || input.equals("exit")) {
            return true;
        }
        else {
            return false;
        }
    }

    // This is a helper method that checks for valid input for the various input types.
    // Expects a "type" of input as well as the input itself (both strings)
    // Returns T/F if the input is valid (or not)
    private Boolean isValidInputOfType(String type, String input) {
        Pattern pattern, pattern2 = null;
        switch(type) {
            case "username": {
                pattern = Pattern.compile("^[a-zA-Z0-9@~._-]{8,}$");
                break;
            }
            case "password": {
                pattern = Pattern.compile("^[a-zA-Z0-9@^%$#/\\,;|~._-]{8,50}$");
                break;
            }
            case "alphanumeric": {
                pattern = Pattern.compile("^[a-zA-Z0-9]+$");
                break;
            }
            case "email": {
                pattern = Pattern.compile("^[a-zA-Z0-9._-]+@{1}[a-zA-Z0-9-_]+[.]{1}[a-zA-Z0-9]+[a-zA-Z_.-]*$");
                break;
            }
            case "integer": {
                pattern = Pattern.compile("^[0-9]+$");
                break;
            }
            case "currency": {
                pattern = Pattern.compile("^[$]?[0-9]+[.]?[0-9]+$");
                pattern2 = Pattern.compile("^[$]?[.]?[0-9]+$");
                break;
            }
            case "name": {
                pattern = Pattern.compile("^[a-zA-Z -]+$");
                break;
            }
            case "string":
            case "letters":
            default: {
                pattern = Pattern.compile("^[a-zA-Z]+$");
            }
        }

        Matcher m = pattern.matcher(input);
        Boolean isValidInput = m.find();
        // This is a second check to see if the user entered ".99"
        if (type.equals("currency") && !isValidInput) {
            Matcher m2 = pattern2.matcher(input);
            isValidInput = m2.find();
        }

        if (isValidInput) {
            return true;
        }
        else {
            return false;
        }
    }

    // Helper method which accepts a string, returns a capitalized + lower cased version of the string.
    // Intended for use in proper names, etc.
    public String capitalize(String text) {
        // Capitalize the first letter of the string
        String result = text.substring(0,1).toUpperCase();
        // Attach the rest of the string (lower case)
        result += text.substring(1);
        return result;
    }
}