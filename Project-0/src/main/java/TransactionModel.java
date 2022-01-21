import java.util.Date;

public class TransactionModel {
    // Each transaction is a single debit/credit that forms an account's transaction history.
    Integer transactionId;  // autoincremented
    Integer accountId;
    Date date;    // Timestamp
    Double amount;
    Integer destination;    // destination account or -/0 if withdrawal

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

}
