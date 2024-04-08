import MyExceptions.InsufficientFundsException;

public class SavingAccount extends Account {
    //no arg constructor which calls parent constructor and passes "Savings" type

    public SavingAccount() {
        super("Savings");
    }

    //copy constructor which creates a new SavnigsAccount object with the same values as passed one
    public SavingAccount(Account account) {
        super(account);
    }
    public SavingAccount(int accNum){
        super(accNum);
    }

    //constructor which takes in Depositor, Account Number, Account type, Account status and balance, and invokes parents
    //costructor by passing these values
    public SavingAccount(Depositor depositor, int accountNumber, String acctType, String acctStatus, double balance) {
        super(depositor, accountNumber, acctType, acctStatus, balance);
    }
    /*
    takes in transaction ticket, which is filled in the main. creates receipt and returns it with current balance.
     */
    public TransactionReceipt getBalance(TransactionTicket ticket) {
        TransactionReceipt currentTransaction = new TransactionReceipt(ticket, true,
                getAccountBalance(), getAccountBalance());
        transactionHistory.add(currentTransaction);
        return new TransactionReceipt(currentTransaction);

    }
    /*
takes in ticket, performs transaction, fills transactionReceipt, and returns it to the caller
 */
    public TransactionReceipt makeDeposit(TransactionTicket ticket) {
        TransactionReceipt currentReceipt;
        double preTransactionBalance = accountBalance;
        double depositAmount = ticket.getAmountOfTransaction();
        double postTransactionBalance = accountBalance + depositAmount;
        accountBalance = postTransactionBalance;
        currentReceipt = new TransactionReceipt(ticket, true, preTransactionBalance,
                postTransactionBalance);
        transactionHistory.add(currentReceipt);
        return new TransactionReceipt(currentReceipt);

    }
    /*
   Takes in ticket, performs transaction described in the ticket,
    returns Transaction Receipt which is filled with infromation of this transaction

    */
    public TransactionReceipt makeWithdrawal(TransactionTicket ticket) {
        TransactionReceipt currentReceipt;
        double preTransactionBalance = accountBalance;
        double withdrawalAmount = ticket.getAmountOfTransaction();
        double postTransactionBalance = preTransactionBalance - withdrawalAmount;
        if (preTransactionBalance < withdrawalAmount) {
            currentReceipt = new TransactionReceipt(ticket, false, "Insufficient Funds",
                    preTransactionBalance, preTransactionBalance);
            transactionHistory.add(currentReceipt);
            throw new InsufficientFundsException("Withdrawal", accountNumber, ticket.getAmountOfTransaction(),
                    getAccountBalance(), "Insufficient funds");

        }
        accountBalance = postTransactionBalance;
        currentReceipt = new TransactionReceipt(ticket, true, preTransactionBalance,
                postTransactionBalance);

        transactionHistory.add(currentReceipt);
        return new TransactionReceipt(currentReceipt);

    }
    //returns String representing account. String is pre formated using String.format method
    public String toString() {

        String toReturn=String.format("\n%10s %10d %10s %10.2f",depositor.toString(),accountNumber,
                accountType,accountBalance);
        if (accountStatus.equals("Closed"))
            toReturn += "\tclosed";
        return toReturn;
    }
}
