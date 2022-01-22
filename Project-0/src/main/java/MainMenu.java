import java.util.Hashtable;

public class MainMenu {
    CustomerModel customer;
    Account account;
    Input input;

    public void doMenu(CustomerModel customer) {
        Integer choice;
        do {
            choice = getUserMenuSelection();
            switch(choice) {
                case 1: {
                    System.out.println("View current account balance.");
                    //customer.printAccounts();
                    break;
                }
                case 2: {
                    System.out.println("Deposit funds into an account.");
                    break;
                }
                case 3: {
                    System.out.println("Withdraw funds from an account.");
                    account.withdrawFrom();
                    break;
                }
                case 4: {
                    System.out.println("Transfer funds from an account.");
                    break;
                }
                case 5: {
                    // Alternatively could make this into returning to login screen
                    System.out.println("Exit program.");
                    break;
                }
            }
        } while(choice != 5);
    }

    private Integer getUserMenuSelection() {
        System.out.println("Main Menu");
        System.out.println("Please select one of the following options:");
        System.out.println("1. View current account balance");
        System.out.println("2. Deposit funds into an account.");
        System.out.println("3. Withdraw funds from an account.");
        System.out.println("4. Transfer funds from an account.");
        System.out.println("5. Create a new bank account.");
        System.out.println("6. Exit program.");
        System.out.print("Your selection: ");
        String choice = input.getString();
        return parseInput(choice);
    }

    // Takes string input and converts it to an integer (menu choice) using a list of predefined words.
    private Integer parseInput(String input) {
        // Checking for some common words in the login menu
        Hashtable<Integer, String[]> wordList = new Hashtable<Integer, String[]>(){};

        String[] wordList1 = new String[]{"view", "balance", "one", "1"};
        String[] wordList2 = new String[]{"deposit", "two", "2"};
        String[] wordList3 = new String[]{"withdraw", "three", "3"};
        String[] wordList4 = new String[]{"transfer", "four", "4"};
        String[] wordList5 = new String[]{"create", "five", "5"};
        String[] wordList6 = new String[]{"quit", "exit", "leave", "close", "end", "stop", "signout", "logoff", "logout", "escape", "six", "6"};

        wordList.put(1, wordList1);
        wordList.put(2, wordList2);
        wordList.put(3, wordList3);
        wordList.put(4, wordList4);
        wordList.put(5, wordList5);
        wordList.put(6, wordList6);

        for(int i=1; i<=6; i++) {
            String[] list = wordList.get(i);
            for (String word : list)
                if (input.equals(word)) {
                    return i;
                }
        }

        System.out.println("Debug: Didn't find menu choice in wordlist.");
        // Some non-input was selected.
        return -1;
    }

    MainMenu(CustomerModel customer) {
        this.customer = customer;
        this.account = new Account(customer.getCustomerId());
        this.input = Input.getInputReference();
    }
}