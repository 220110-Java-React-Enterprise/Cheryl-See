
public class Main {
    public static void main(String[] args) {
        Login user = new Login();
        User customer = user.doLogin();
        if (customer != null) {
            System.out.println("Hello, " + customer.firstName + "!");
            System.out.println("Menu displayed.");
            customer.printAccounts();
            MainMenu.doMenu(customer);
        }
    }
}
