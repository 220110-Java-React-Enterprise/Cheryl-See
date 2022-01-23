
public class Main {
    public static void main(String[] args) {
        Login login = new Login();
        CustomerModel customer = login.doLogin();

        if (customer != null) {
            System.out.println("Hello, " + customer.getFirstName() + "!\n");

            MainMenu menu = new MainMenu(customer);
            menu.doMenu();
        }
    }
}
