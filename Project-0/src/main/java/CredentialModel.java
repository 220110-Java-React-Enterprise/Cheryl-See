public class CredentialModel {
    int credentialId; // This can be null - probably not used, just serving as a PK
    int customerId;
    String username;
    String password;

    /*
    CredentialModel(int customerId) {
        this.customerId = customerId;
        // Not sure where or if this will be used yet
    } */

    CredentialModel(int customerId, String username, String password) {
        this.customerId = customerId;
        this.username = username;
        this.password = password;
    }

    // Empty constructor meant for new registrations
    CredentialModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int id) {
        this.credentialId = id;
    }
}
