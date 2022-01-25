
// This models the joint_account table, which maps multiple owners (customers) of an account to the account (owners_id)
// customerid's can be repeated (own multiple accounts) but jointAccount should be unique.
public class AccountOwnerModel {
    Integer ownerId;     // This is auto-incremented in the database
    Integer accountId;          // This indicates which account the joint entry is mapped to
    Integer customerId;

    public Integer getOwnerId() {
        return ownerId;
    }

    // This shouldn't really be used (auto-incremented)
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    // This needs to be an empty constructor because we won't know the account_id until after account is inserted?
    // or jdbc will work with this even without account added
   AccountOwnerModel () {
   }
}
