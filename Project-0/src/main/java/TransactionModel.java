import java.util.Date;

public class TransactionModel {
    // Each transaction is a single debit/credit that forms an account's transaction history.
    Integer transactionId;  // autoincremented
    Integer accountId;
    Date date;    // Timestamp
    Double amount;
    String type; // type of transaction
    Integer destination;    // destination account or null if withdrawal
    Integer source; // origin account or null if deposit

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }


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

    // This constructor is meant for t
    public TransactionModel(Integer accountId, Double amount, String type, Integer sourceDest) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        switch(type) {
            case "deposit":
                // Depositing to this account (creating money) so using placeholder -1
                this.source = sourceDest; //self
                this.destination = accountId;
                break;
            case "withdraw":
                // Removing money from this account so there is no source or destination?
                this.source = accountId;
                this.destination = sourceDest;  // self/user
                break;
            case "transfer":
                // There needs to be two records, one with +/- and flipped source/dest
                if (amount > 0) {
                    // This is a positive amount, so it is receiving money
                    this.source = sourceDest;
                    this.destination = accountId;
                } else {
                    // This is a negative amount, so it is losing money
                    this.source = accountId;
                    this.destination = sourceDest;
                }
                break;
        }
    }

    // This constructor is intended for transfers - unused now?
    public TransactionModel(Integer accountId, Double amount, Integer destination) {
        this.accountId = accountId;
        this.amount = amount;
        this.destination = destination;
    }

    // Empty constructor used by TransactionRepo
    public TransactionModel() {
    }
}
