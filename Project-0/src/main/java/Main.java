
public class Main {
    public static void main(String[] args) {
        Login login = new Login();
        CustomerModel customer = login.doLogin();

        if (customer != null) {
            System.out.println("Hello, " + customer.getFirstName() + "!");
            System.out.println("Menu displayed.");
            //customer.printAccounts();
            //MainMenu.doMenu(customer);
        }
    }
}
