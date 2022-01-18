public class Transaction {
    // Each transaction is a single debit/credit that forms an account's transaction history.
    Integer transactionId;
    Integer accountId;
    // TODO: This is a Date object, fix when more awake
    String date;
    Double amount; // or should this be a float
    Boolean success;    // Not sure if needed, but if it didn't work....
}
