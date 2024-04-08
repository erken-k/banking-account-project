import MyExceptions.InsufficientFundsException;
import MyExceptions.PostDatedCheckException;

import java.util.Calendar;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        super("Checking");
    }
    public CheckingAccount(Account account){
        super(account);
    }

    public CheckingAccount(Depositor depositor, int accountNumber, String acctType, String acctStatus, double balance) {
        super(depositor, accountNumber, acctType, acctStatus, balance);
    }
    // clear check method takes in ticket and calendar date of check as
    // well as Bank object, it checks wheter check is postdated
    //or too old. if yes sends receipt for failed transaction
    // otherwise processes check and returns transactionreceipt
    // this method also updates total amount static variable in bank

    public TransactionReceipt clearCheck(TransactionTicket ticket, Calendar dateOfCheck) {
        Calendar todaysDate = Calendar.getInstance();
        TransactionReceipt currentTransactionReceipt = new TransactionReceipt();
        double preTransactionBalance = getAccountBalance();
        double transactionAmount = ticket.getAmountOfTransaction();
        currentTransactionReceipt.setTransactionTicket(ticket);
        currentTransactionReceipt.setPreTransactionBalance(preTransactionBalance);
        Calendar oldestPossible = Calendar.getInstance();
        oldestPossible.add(Calendar.MONTH, -6);
        if (dateOfCheck.compareTo(todaysDate) == 1) {
            currentTransactionReceipt.setSuccessIndicatorFlag(false);
            currentTransactionReceipt.setReasonForFailure("Post Dated Check");
            return currentTransactionReceipt;
        }
        if (oldestPossible.compareTo(dateOfCheck) == 1) {
            currentTransactionReceipt.setSuccessIndicatorFlag(false);
            currentTransactionReceipt.setReasonForFailure("Check is older than 6 month");
            return currentTransactionReceipt;
        }
        if (preTransactionBalance < 2500) {
            transactionAmount += 1.5;
        }
        if (preTransactionBalance - transactionAmount >= 0) {
            currentTransactionReceipt.setPostTransactionBalance(preTransactionBalance - transactionAmount);
            super.setAccountBalance(preTransactionBalance - transactionAmount);
            currentTransactionReceipt.setSuccessIndicatorFlag(true);
            super.addToTransactionHistory(currentTransactionReceipt);
            return currentTransactionReceipt;
        } else {
            currentTransactionReceipt.setSuccessIndicatorFlag(false);
            currentTransactionReceipt.setReasonForFailure("Insufficient funds: 2.5$ Overdraft fee has been charged");
            TransactionTicket overdraftFeeTicket = new TransactionTicket("Overdraft fee", 2.5,
                    super.getAccountNumber());
            TransactionReceipt overdraftReceipt = new TransactionReceipt(overdraftFeeTicket, true, preTransactionBalance,
                    preTransactionBalance - 2.5);
            super.setAccountBalance(preTransactionBalance - 2.5);
            currentTransactionReceipt.setPostTransactionBalance(preTransactionBalance-2.5);
            super.addToTransactionHistory(currentTransactionReceipt);
            super.addToTransactionHistory(overdraftReceipt);
            return currentTransactionReceipt;
        }

    }
    /*
    takes in transaction ticket, which is filled in the main. creates receipt and returns it with current balance.
     */
    public TransactionReceipt getBalance(TransactionTicket ticket) {
        TransactionReceipt currentTransaction = new TransactionReceipt(ticket, true,
                accountBalance, accountBalance);
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
