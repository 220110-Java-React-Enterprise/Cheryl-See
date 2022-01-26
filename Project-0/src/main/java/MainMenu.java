
// This class handles the main loop after logging in and selects an action (primarily Account-related).

public class MainMenu {
    CustomerModel customer;
    Account account;
    Input input;

    public void doMenu() {
        Integer choice;
        do {
            choice = getUserMenuSelection();
            System.out.println(""); // This is just to add a space / formatting after input
            switch(choice) {
                case 1: {
                    account.printAllAccountData();
                    break;
                }
                case 2: {
                    account.deposit();
                    break;
                }
                case 3: {
                    account.withdraw();
                    break;
                }
                case 4: {
                    account.transfer();
                    break;
                }
                case 5: {
                    account.transactionHistory();
                    break;
                }
                case 6: {
                    account.createAccount();
                    break;
                }
                case 7: {
                    System.out.println("Exiting program.  Have a nice day!\n");
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("That is not a valid selection.  Please choose one of the following options.");
                }
            }
        } while(true);
    }

    // This is a helper function for doMenu() which displays the menu options and
    // prompts the user for a selection.  Works with parseInput to return an integer
    // representing the choice made.
    private Integer getUserMenuSelection() {
        System.out.println("\nPlease select one of the following options:");
        System.out.println("1. View an account overview.");
        System.out.println("2. Deposit funds into an account.");
        System.out.println("3. Withdraw funds from an account.");
        System.out.println("4. Transfer funds from one account to another.");
        System.out.println("5. View transaction history for a specific account.");
        System.out.println("6. Create a new bank account.");
        System.out.println("7. Exit program.");
        System.out.print("Your selection: ");
        String choice = input.getString();
        return parseInput(choice);
    }

    // Helper function for getUserMenuSelection() which focuses on parsing the user menu selection.
    // Takes string input and converts it to an integer (menu choice) using a list of predefined words.
    // This helps check for some possible words (instead of numerical input) used in the main menu.
    private Integer parseInput(String input) {
        String[] wordList1 = new String[]{"view", "balance", "summary", "overview", "one", "1"};
        String[] wordList2 = new String[]{"deposit", "two", "2"};
        String[] wordList3 = new String[]{"withdraw", "three", "3"};
        String[] wordList4 = new String[]{"transfer", "four", "4"};
        String[] wordList5 = new String[]{"transaction", "history", "transactions", "five", "5"};
        String[] wordList6 = new String[]{"create", "new", "six", "6"};
        String[] wordList7 = new String[]{"quit", "exit", "leave", "close", "end", "stop", "signout", "sign out", "logoff", "logout", "escape", "seven", "7"};
        String[][] wordLists = new String[][]{wordList1, wordList2, wordList3, wordList4, wordList5, wordList6, wordList7};
        Integer selection = 1;

        for (String[] list : wordLists) {
            for (String word : list) {
                if (input.equals(word)) {
                    return selection;
                }
            }
            selection++;
        }

        return -1;
    }

    MainMenu(CustomerModel customer) {
        this.customer = customer;
        this.account = new Account(customer.getCustomerId());
        this.input = Input.getInputReference();
    }
}