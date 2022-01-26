public class CustomerModel {
    Integer customerId;
    Integer credentialId;   // This can be null until person registers for an account
    private String firstName;
    private String lastName;
    // The following is going to be populated from the database / mostly fluff details
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    // Set the credential ID.  Intended for use during registration process.
    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }

    CustomerModel(int customerId) {
        this.customerId = customerId;
    }

    // This is to create a brand-new customer record (ex. joint account, registration)
    // It prompts the user for basic details (just the name for simplicity) and returns a CustomerModel w/ info.
    // It's currently used by login (new bank customers) though it is very similar to what is used for joint acct users
    public void createNewCustomerRecord() {
        System.out.print("What is your first name?: ");
        Input input = Input.getInputReference();
        String firstName = input.getName();
        // User cancelled process
        if (firstName == null) {
            return;
        }

        System.out.print("What is your last name?: ");
        String lastName = input.getName();
        // User cancelled process
        if (lastName == null) {
            return;
        }

        String email = input.getEmail();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }

    // This is used during new registration - new CustomerModel being added
    CustomerModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Used when creating new credentials
    CustomerModel() {}
}
