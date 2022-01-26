
// This class handles the main loop after logging in and selects an action (primarily Account-related).

public class MainMenu {
    CustomerModel customer;
    Account account;
    Input input;

    public void doMenu() {
        Integer choice;
        do {
            choice = getUserMenuSelection();
            switch(choice) {
                case 1: {
                    System.out.println("View account summary.\n");
                    account.printAllAccountData();
                    break;
                }
                case 2: {
                    System.out.println("Deposit funds into an account.\n");
                    account.deposit();
                    break;
                }
                case 3: {
                    System.out.println("Withdraw funds from an account.\n");
                    account.withdraw();
                    break;
                }
                case 4: {
                    System.out.println("Transfer funds from an account.\n");
                    account.transfer();
                    break;
                }
                case 5: {
                    System.out.println("View transaction history for a specified account.\n");
                    account.transactionHistory();
                    break;
                }
                case 6: {
                    System.out.println("Create a new bank account.\n");
                    account.createAccount();
                    break;
                }
                case 7: {
                    // Alternatively could make this into returning to login screen
                    System.out.println("Exiting program.  Have a nice day!\n");
                    System.exit(0);
                    break;
                }
            }
        } while(choice != 7);
    }

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

    // Takes string input and converts it to an integer (menu choice) using a list of predefined words.
    // This helps check for some possible words (instead of numerical input) used in the main menu.
    private Integer parseInput(String input) {
        String[] wordList1 = new String[]{"view", "balance", "summary", "overview", "one", "1"};
        String[] wordList2 = new String[]{"deposit", "two", "2"};
        String[] wordList3 = new String[]{"withdraw", "three", "3"};
        String[] wordList4 = new String[]{"transfer", "four", "4"};
        String[] wordList5 = new String[]{"transaction", "history", "transactions", "five", "5"};
        String[] wordList6 = new String[]{"create", "new", "six", "6"};
        String[] wordList7 = new String[]{"quit", "exit", "leave", "close", "end", "stop", "signout", "logoff", "logout", "escape", "seven", "7"};
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

        System.out.println("Debug: Didn't find menu choice in wordlist.");
        return -1;
    }

    MainMenu(CustomerModel customer) {
        this.customer = customer;
        this.account = new Account(customer.getCustomerId());
        this.input = Input.getInputReference();
    }
}