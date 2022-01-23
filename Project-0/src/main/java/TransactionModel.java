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

    // This constructor is meant specifically for transfers
    public TransactionModel(Integer accountId, Double amount, String type, Integer source, Integer destination) {
        if (type.equals("transfer")) {
            this.accountId = accountId;
            this.amount = amount;
            this.type = type;
            // There needs to be two records, one with +/- and flipped source/dest
            this.source = source;
            this.destination = destination;
        }
        else {
            // This is technically the wrong constructor, so maybe we can call the other?
            System.out.println("Wrong constructor was called - should only be for transfers.");
        }
    }

    // This constructor is intended for deposits and withdrawals (one-way money direction)
    public TransactionModel(Integer accountId, Double amount, String type) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        if (type.equals("deposit")) {
            this.source = -1;
            this.destination = accountId;
        }
        else if (type.equals("withdraw")) {
            this.source = accountId;
            this.destination = -1;
        }
        else {
            System.out.println("Error: This constructor should not be used for anything other than withdraw/deposit.");
        }
    }

    // Empty constructor used by TransactionRepo
    public TransactionModel() {
    }
}
