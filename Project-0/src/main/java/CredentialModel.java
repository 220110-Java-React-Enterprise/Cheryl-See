import javax.sound.midi.Soundbank;

public class CredentialModel {
    Integer credentialId; // This can be null - probably not used, just serving as a PK
    Integer customerId;
    String username;
    String password;

    CredentialModel(Integer customerId, String username, String password) {
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer id) {
        this.credentialId = id;
    }
}
