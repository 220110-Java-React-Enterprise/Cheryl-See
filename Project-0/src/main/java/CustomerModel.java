public class CustomerModel {
    int customerId;
    int credentialId;   // Can be null until person registers for an account
    // The following is going to be populated from the database
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private CustomLinkedList<AccountModel> accounts;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCredentialId() {
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

    // This is used during joint account - new CustomerModel being added
    CustomerModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    // This is used during new registration - new CustomerModel being added
    CustomerModel(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
