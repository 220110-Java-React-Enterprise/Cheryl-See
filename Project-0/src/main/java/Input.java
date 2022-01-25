import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// This class handles all the input and most of the validation.  Most functions either return
// null or -1 if bad input is detected, though some loop and force re-entry until satisfied.
// Those typically include accepting "cancel" or "quit" to exit the loop.

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


    // Gets user input (alphanumeric only) and returns the string.
    public String getString() {
        String input = getInput();
        if (isValidInputOfType("alphanumeric", input)) {
            return input;
        }
        else {
            return "";
        }
    }


    // Similar to getString, this gets a user's name, validates that it is only letters and returns a string.
    // Intended for use with registration / new customer record.
    public String getName() {
        do {
            System.out.print("> ");
            String name = input.getInput();
            if (userInitiatedCancel(name)) {
                System.out.println("Cancelling registration.");
                // check what effect this really has
                return null;
            }
            if (isValidInputOfType("name", name)) {
                // Capitalize the first letter of the string and make the rest lower case
                String result = name.substring(0,1).toUpperCase();
                // Attach the rest of the string (lower case)
                result += name.substring(1).toLowerCase();
                return result;
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
    // Not displayed, but the user can also enter quit, exit or cancel to leave this loop.
    public String getUsername() {
        do {
            System.out.print("Please enter a username: ");
            String input = getInput();
            if (isValidInputOfType("username", input)) {
                return input;
            }
            else {
                if (input.equals("quit") || input.equals("cancel") || input.equals("exit")) {
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
    // Returns the account type (string).
    public String getCheckingType() {
        do {
            System.out.println("What type of account would you like to open?");
            System.out.println("1. Checking account");
            System.out.println("2. Savings account");
            System.out.print("I want to open: ");
            String text = getInput();

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
            String input = getInput();

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


    // This is identical to what is in login, but maybe this version should be used instead.
    // Checks whether a user entered input that meant cancelling out of the current menu.
    // Returns a true if it finds something like cancel, false otherwise.
    private Boolean userInitiatedCancel(String input) {
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
        // pattern = Pattern.compile("");
        switch(type) {
            case "username": {
                // ^[a-zA-Z0-9@~._-]{8,}$
                // anything that passes this pattern is valid
                pattern = Pattern.compile("^[a-zA-Z0-9@~._-]{8,}$");
                //pattern = Pattern.compile("[^\\w_\\-.@]");
                break;
            }
            case "password": {
                // This checks for alphanumeric characters, some symbols, and checks if the string
                // is between 8-50 characters long.
                pattern = Pattern.compile("^[a-zA-Z0-9@^%$#/\\,;|~._-]{8,50}$");
                break;
            }
            case "alphanumeric": {
                pattern = Pattern.compile("^[a-zA-Z0-9]$");
                break;
            }
            case "email": {
                pattern = Pattern.compile("^[a-zA-Z0-9._-]+@{1}[a-zA-Z0-9-_]+[.]{1}[a-zA-Z0-9]+[a-zA-Z_.-]*$");
                // Anything that passes this pattern is valid
                break;
            }
            case "integer": {
                // ^[0-9]+$
                pattern = Pattern.compile("^[0-9]+$");
                break;
            }
            case "currency": {
                // ^[$]?[0-9]+[.]?[0-9]+$
                pattern = Pattern.compile("^[$]?[0-9]+[.]?[0-9]+$");
                // Backup one that catches something like ".11": ^[$]?[.]?[0-9]+$
                pattern2 = Pattern.compile("^[$]?[.]?[0-9]+$");
                break;
            }
            // Only letters are allowed
            case "name": {
                pattern = Pattern.compile("^[a-zA-Z -]+$");
                break;
            }
            case "string":
            case "letters":
            default: {
                // ^[a-zA-Z]+$
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

}