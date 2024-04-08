import MyExceptions.InsufficientFundsException;

import java.util.Calendar;

public class CDAccount extends Account {
    private Calendar maturityDate;
    private String stringDate;

    //no arg constructo which just calls parent's constructor passing account type
    public CDAccount() {
        super("CD");
    }

    // copy constructor which takes in CDAccount object and assigns data fields of it to the corresponding
    //data fields of this object
    public CDAccount(CDAccount account) {
        super(account);
        this.stringDate = account.stringDate;
        this.maturityDate = (Calendar) account.maturityDate.clone();
    }

    /*Constructo which takes in Depositor, int representing account number,String representing
    account type, String representing account status, double represening balance and int representin terms
     method calls super constructor passing depositor, account number, acocunt type, account status, balance
     and terms to it. it also creates Calendar object adjusts its value to represent matuiry date of the account and
     sets it value to maturitydate datafield
     */
    public CDAccount(Depositor depositor, int accountNumber, String acctType, String acctStatus,
                     double balance, int terms) {
        super(depositor, accountNumber, acctType, acctStatus, balance);
        Calendar maturity = Calendar.getInstance();
        maturity.add(Calendar.MONTH, +terms);
        int month = maturity.get(Calendar.MONTH) + 1;
        int day = maturity.get(Calendar.DAY_OF_MONTH);
        int year = maturity.get(Calendar.YEAR);
        String date = month + "/" + day + "/" + year;
        this.stringDate = date;
    }

/*Constructo which takes in Depositor, int representing account number,String representing
    account type, String representing account status, double represening balance and string representing date
     method calls super constructor passing depositor, account number, acocunt type, account status, balance
     and terms to it. It also calls setMaturityDate() passing string representing maturity date to it
 */

    public CDAccount(Depositor depositor, int accountNumber, String acctType, String acctStatus,
                     double balance, String date) {
        super(depositor, accountNumber, acctType, acctStatus, balance);
        setMaturityDate(date);

    }

    /*takes in String, representing date in the format of mm/dd/year. and creates calendar object with the same date
    as in this string. sets this calendar date to the maturity data field of this object.
    * */
    public void setMaturityDate(String date) {
        this.stringDate = date;
        Calendar maturityDate = Calendar.getInstance();
        int startIndex = 0;
        int endIndex = date.indexOf("/");
        String month = date.substring(0, endIndex);
        startIndex = endIndex + 1;
        endIndex = date.indexOf("/", startIndex);
        String day = date.substring(startIndex, endIndex);
        String year = date.substring(endIndex + 1, date.length());
        maturityDate.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        this.maturityDate = maturityDate;
    }

    // returns already formated line with account info
    // format is done using String.format
    public String toString() {
        Depositor depositor = super.getDepositor();
        int acctNum = super.getAccountNumber();
        String acctType = super.getAccountType();
        double balance = super.getAccountBalance();

        String toReturn=String.format("\n%10s %10d %10s %10.2f %10s",depositor.toString(),acctNum,acctType,balance,stringDate);

        if (super.getAccountStatus().equals("Closed"))
            toReturn += "\tclosed";
        return toReturn;


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
    // takes in ticket, checks if maturity date on this object has been reached. if yes calls
    //makeDeposit() from parent class, passing ticket there.
    public TransactionReceipt makeDeposit(TransactionTicket ticket) {
        if (maturityDate.compareTo(ticket.getDateOfTransaction()) ==1)
            return new TransactionReceipt(ticket, false,
                    "Maturity Date not Reached ("+stringDate+")",
                    super.getAccountBalance(), super.getAccountBalance());

        updateMaturityDate(ticket.getTermOfCDC());
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

    // takes in ticket, checks if maturity date on this object has been reached. if yes calls
    //makeWithdrawal() from parent class, passing ticket there.
    public TransactionReceipt makeWithdrawal(TransactionTicket ticket) {
        if (maturityDate.compareTo(ticket.getDateOfTransaction()) ==1)
            return new TransactionReceipt(ticket, false,
                    "Maturity Date not Reached ("+stringDate+")",
                    super.getAccountBalance(), super.getAccountBalance());
        updateMaturityDate(ticket.getTermOfCDC());
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

    // gets a copy Calendar which represents maturity date of this object
    public Calendar getMaturityDate() {
        return (Calendar) maturityDate.clone();
    }

    //takes in int value representing months to update maturity date
    // updates maturity data datafield of this object by the number of passed month
    public void updateMaturityDate(int terms) {
        Calendar maturity = Calendar.getInstance();
        maturity.add(Calendar.MONTH, terms);
        int month = maturity.get(Calendar.MONTH) + 1;
        int day = maturity.get(Calendar.DAY_OF_MONTH);
        int year = maturity.get(Calendar.YEAR);
        String date = month + "/" + day + "/" + year;
        this.stringDate = date;
        this.maturityDate=maturity;
    }
}
