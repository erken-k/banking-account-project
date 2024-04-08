import MyExceptions.*;
import com.sun.source.tree.Tree;

import java.sql.Array;
import java.util.*;



public class Bank {
    private ArrayHeap<Account> accounts;
    private ArrayHeap<Account> heap;
    private LinkedListAccounts sortedByAcctNum;
    private GenericLinkedListQueue<TransactionTicket> tickets;
    static double totalAmountInSavingAccts;
    private static double totalAmountInCheckingAccts;
    private static double totalAmountInCDAccts;
    private static double totalAmountInAllAccts;
    private static ArrayList<Integer> accNumBubbleSortKey;

    public Bank() {
        //accounts = new ArrayList<Account>();
        accounts = new ArrayHeap<>();
        heap=new ArrayHeap<>();
        sortedByAcctNum=new LinkedListAccounts();
        accNumBubbleSortKey=new ArrayList<>();
        tickets=new GenericLinkedListQueue<>();
        totalAmountInAllAccts = 0;
        totalAmountInCDAccts = 0;
        totalAmountInCheckingAccts = 0;
        totalAmountInSavingAccts = 0;

    }

    /*
    takes in ticket, and acct
    Creates new account,  and receipt representing transaction. adds this receipt to account history.
    updates static variables which represent totals
    This method can throw exceptions if:
    account exists
    invalid deposit ammount enttered
     */
    public TransactionReceipt openNewAccount(TransactionTicket ticket) {
        int accNum=ticket.getAccountNumber();
        Depositor depositor=ticket.getDepositor();
        String acctType=ticket.getAccountType();
        int accIndex = findAcct(accNum);
        if (accIndex != -1) {
            throw new InvalidAccountException("Account opening ", accNum, "Account Already Exists");
        }
        if (ticket.getAmountOfTransaction() < 0) {
            throw new InvalidAmountException("Account opening", accNum, ticket.getAmountOfTransaction(), -1,
                    "Invalid Initial Deposit Amount");
        }
        Account currentAccount;
        switch (acctType) {
            case "Checking":
                currentAccount = new CheckingAccount(depositor, accNum, acctType, "Open", ticket.getAmountOfTransaction());
                break;
            case "Savings":
                currentAccount = new SavingAccount(depositor, accNum, acctType, "Open", ticket.getAmountOfTransaction());
                break;
            case "CD":
                currentAccount = new CDAccount(depositor, accNum, acctType, "Open", ticket.getAmountOfTransaction(),
                        ticket.getCDExpirationDateForNewAccount());
                break;
            default:
                throw new InvalidInputException("Account Type", acctType, "Account Opening", accNum,
                        "You can only choose between CD/Checking/Saving");

        }

        double amountOfTransaction = ticket.getAmountOfTransaction();
        accounts.add(currentAccount);
        sortedByAcctNum.addSortedByAcctNum(currentAccount);

        TransactionReceipt currentTransactionReceipt = new TransactionReceipt(ticket, true, 0,
                amountOfTransaction);
        updateTotalAmount(acctType, amountOfTransaction);
        currentAccount.addToTransactionHistory(currentTransactionReceipt);
        return currentTransactionReceipt;
    }

    /*
    takes in ticket, and index of acct, deletes account at a given index
    This method can throw exceptions if:
    account doesnt exist
    balance is not 0
     */
    public TransactionReceipt deleteAcct(TransactionTicket ticket) {
        int accNum=ticket.getAccountNumber();
        int accIndex = findAcct(accNum);
        if (accIndex == -1) {
            throw new InvalidAccountException("Account deletion", accNum, "Account Doesn't Exist");
        }
        if (getAccount(accIndex).getAccountBalance() != 0) {
            throw new InvalidAmountException("Account deletion", accNum, -1,
                    getAccount(accIndex).getAccountBalance(), "Balance not 0");
        }
        TransactionReceipt currentReceipt = new TransactionReceipt(ticket, true,
                0, 0);
        Account copy=getAccount(accIndex);
        accounts.remove(accIndex);

        sortedByAcctNum.remove(copy);


        return currentReceipt;

    }

    /*
    takes in acct index and ticket, fills in info into receipt,
    closes account,adds reciept to transaction history, returns receipt
    This method can throw exceptions if:
    account doesnt exist
     */
    public TransactionReceipt closeAcct(TransactionTicket ticket) {
        int accNum=ticket.getAccountNumber();
        int accIndex = findAcct(accNum);
        if (accIndex == -1) {
            throw new InvalidAccountException("Account Closure", accNum, "Doesn't Exist");
        }
        Account currentAccount = accounts.get(accIndex);
        double preTransactionBalance = currentAccount.getAccountBalance();
        currentAccount.setAccountStatus("Closed");
        TransactionReceipt currentTransactionReceipt = new TransactionReceipt(ticket, true,
                preTransactionBalance, preTransactionBalance);
        currentAccount.addToTransactionHistory(currentTransactionReceipt);
        return currentTransactionReceipt;
    }

    /*
    takes in ticket and acct index, fills receipt which represents current
    transaction ,reopens account, returns receipt
    This method can throw exceptions if:
    account doesnt exist
     */
    public TransactionReceipt reopenAcct(TransactionTicket ticket) {
        int accNum = ticket.getAccountNumber();
        int accIndex = findAcct(accNum);
        if (accIndex == -1) {
            throw new InvalidAccountException("Account reopening", accNum, "Doesn't Exist");
        }
        Account currentAccount = accounts.get(accIndex);
        double preTransactionBalance = currentAccount.getAccountBalance();
        currentAccount.setAccountStatus("Open");
        TransactionReceipt currentTransactionReceipt = new TransactionReceipt(ticket, true,
                preTransactionBalance, preTransactionBalance);
        currentAccount.addToTransactionHistory(currentTransactionReceipt);
        return currentTransactionReceipt;
    }

    // this method takes in transaction ticket
    // it finds all accounts with ssn in trnsaction ticket
    // creates a string and adds all accounts as strings neetly formated to it
    // Creates receipt that represents succesful transaction, assigns string with info to receipts'
    //variable that is meant to hold messages
    public TransactionReceipt acctInfoBySSN(TransactionTicket ticket) {
        String message="";
        int ssn=ticket.getSSN();
        if (ssn < 0 || ssn > 999999999) {
            throw new InvalidInputException("SSN", ssn + "", "Account Info with History", 0,
                    "Isn't in Acceptable Format");
        }
        ArrayList<Account> accountsWithSSN = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            if (ssn == accounts.get(i).getDepositor().getSsn()) {
                accountsWithSSN.add(accounts.get(i));
                message += ("\n\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                message += ("\n" + accounts.get(i).toString());
                message += ("\n\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            }
        }
        if (accountsWithSSN.size() <= 0) {
            throw new InvalidInputException("SSN", ssn + "", "Account Info with History", 0,
                    "Depositor Doesn't have any accounts");
        }
        TransactionReceipt rec=new TransactionReceipt();
        rec.setTransactionTicket(ticket);
        rec.setAccInfo(message);
        return rec;
    }
    // this method takes in transaction ticket
    // it finds all accounts with ssn in trnsaction ticket
    // creates a string and adds all accounts as strings neetly formated to it
    // Creates receipt that represents succesful transaction, assigns string with info to receipts'
    //variable that is meant to hold messages
    public TransactionReceipt getAccInfoWithHistory(TransactionTicket ticket){
        String message="";
        int ssn=ticket.getSSN();
        if (ssn < 0 || ssn > 999999999) {
            throw new InvalidInputException("SSN", ssn + "", "Account Info with History", 0,
                    "Isn't in Acceptable Format");
        }
        ArrayList<Account> accountsWithSSN = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            if (ssn == accounts.get(i).getDepositor().getSsn()) {
                Account currentAccount=accounts.get(i);
                accountsWithSSN.add(currentAccount);
                message += ("\n\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                message += ("\n" + currentAccount.toString());
                message += ("\n\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                message += ("\n\t***History of Transactions***\t");
                String header = String.format("%-10s %20s %10s %20s %20s %20s %20s %20s ", "Date",
                        "Type", "Account #", "Balance before", "Amount of t-n", "Balance After", "Completion Status",
                        "New Maturity Date");

                if (currentAccount.getTransactionHistory().size() != 0) {
                    message += ("\n" + header);
                    ArrayList<TransactionReceipt> currentHistory = currentAccount.getTransactionHistory();
                    for (int j = 0; j < currentHistory.size(); j++) {
                        String toPrint = currentHistory.get(j).toStringAsLine();
                        if (currentAccount.getAccountType().equals("CD"))
                            toPrint += String.format("%20s", currentHistory.get(j).getTransactionTicket().getNewMaturityDate());
                            message+=("\n"+toPrint);
                    }
                } else {
                    message+=("\n\tThis Account doesnt have any transactions\t");
                }
                message+=("\n\n\t***End of Transaction History***\t");
            }
        }
        TransactionReceipt cur=new TransactionReceipt();
        cur.setTransactionTicket(ticket);
        cur.setAccInfo(message);
        return cur;
    }

    //takes in int account, searches through all accounts for this account number.
    //return index of account if found. if not return -1
    // throws exception if ssn is not in correct form or no depositor found
    public int findAcct(int accNum){
        bubbleSortByComparator(accNumBubbleSortKey,accounts,new AccountComparatorByAccountNumber());
            ArrayList<Integer> keys=accNumBubbleSortKey;
            int start=0;
            int end=accounts.size()-1;
            while(start<=end){
                int m=start+(end-start)/2;
                if(accounts.get(keys.get(m)).getAccountNumber()==accNum)
                    return keys.get(m);
                if(accounts.get(keys.get(m)).getAccountNumber()<accNum) {
                    start = m + 1;
                }else{
                    end=m-1;
                }
            }
            return -1;
    }

    /*
    takes in String representing type of account and double representing amount of transaction
    updates the corresponding static datafields of this object
     */
    public void updateTotalAmount(String type, double amountOfTransaction) {
        switch (type) {
            case "Checking":
                totalAmountInCheckingAccts += amountOfTransaction;
                break;
            case "Savings":
                totalAmountInSavingAccts += amountOfTransaction;
                break;
            case "CD":
                totalAmountInCDAccts += amountOfTransaction;
                break;
        }
        totalAmountInAllAccts += amountOfTransaction;

    }


    //gets double representing total amount of money in all acoounts in this bank
    public double getTotalAmountInAllAccts() {
        return totalAmountInAllAccts;
    }

    //returns double represening  total amount of money in Checking accounts in this bank
    public double getTotalAmountInCheckingAccts() {
        return totalAmountInCheckingAccts;
    }

    //returns double representing total amount of money in cd accounts in this bank
    public double getTotalAmountInCDAccts() {
        return totalAmountInCDAccts;
    }

    // returns double representing total amount of money in saving accounts in this bank
    public double getTotalAmountInSavingAccts() {
        return totalAmountInSavingAccts;
    }

    // takes in integer, returns account at the index of this int in AllAccount datafield
    public Account getAccount(int i) {
        return makeCopy(accounts.get(i));
    }
    public Account makeCopy(Account acc){
        Account copy;
        switch (acc.getAccountType()) {
            case "Checking":
                copy = new CheckingAccount(acc);
                break;
            case "Savings":
                copy = new SavingAccount(acc);
                break;
            default:
                copy = new CDAccount((CDAccount) acc);
        }
        return copy;
    }


    //method takes in ticket and index of account, it gets account at a given index and
    // calls .get ticket) for it.
    //method returns transactionreceipt recieved from getbalance(ticket)
    //This method can throw exceptions if:
    //    account doesnt exist

    public TransactionReceipt balanceCheck(TransactionTicket ticket) {
        int accIndex = findAcct(ticket.getAccountNumber());
        if (accIndex == -1) {
            throw new InvalidAccountException("Balance inquiry", ticket.getAccountNumber(), "Doesn't exists");
        }
        Account currentAccount = accounts.get(accIndex);
        TransactionReceipt currentReceipt = currentAccount.getBalance(ticket);
        return currentReceipt;
    }

    /*
    method recieves ticket and index of account
    it  gets account at given index calls makeDeposit method for it, recieves receipt and returns it to caller
    This method can throw exceptions if:
    account doesnt exist
    invalid amount entered
    account is closed
     */
    public TransactionReceipt deposit(TransactionTicket ticket) {
        int accNum=ticket.getAccountNumber();
        int accIndex = findAcct(accNum);
        if (accIndex == -1) {
            throw new InvalidAccountException("Deposit", accNum, " Doesn't exists");
        }
        Account currentAccount = getAccount(accIndex);
        if (currentAccount.getAccountStatus().equals("Closed")) {
            throw new InvalidAccountException("Deposit", accNum, "Account is closed");
        }
        if (ticket.getAmountOfTransaction() <= 0) {
            throw new InvalidAmountException("Deposit", accNum, ticket.getAmountOfTransaction(), currentAccount.getAccountBalance(),
                    "Invalid number entered");
        }
        TransactionReceipt currentReceipt=accounts.get(accIndex).makeDeposit(ticket);
        if(currentReceipt.getSuccessIndicatorFlag())
            updateTotalAmount(currentAccount.getAccountType(),ticket.getAmountOfTransaction());
        Account acct=getAccount(accIndex);


        return currentReceipt;
    }

    //methods recieves ticket and index of account
    // it gets account at given index, calls makeWithdrawal for it, recieves receipt and returns it to caller
    //This method can throw exceptions if:
    //    account doesnt exist
    // account is closed
    // invalid amount entered
    public TransactionReceipt withdrawal(TransactionTicket ticket) {
        int accNum=ticket.getAccountNumber();
        int accIndex=findAcct(accNum);
        if (accIndex == -1) {
            throw new InvalidAccountException("Withdrawal", accNum, " Doesn't exists");
        }
        Account currentAccount = getAccount(accIndex);
        if (currentAccount.getAccountStatus().equals("Closed")) {
            throw new InvalidAccountException("Withdrawal", accNum, " Account is closed");
        }
        if (ticket.getAmountOfTransaction() <= 0) {
            throw new InvalidAmountException("Withdrawal", accNum, ticket.getAmountOfTransaction(), currentAccount.getAccountBalance(),
                    "Invalid number entered");
        }
        TransactionReceipt currentReceipt=accounts.get(accIndex).makeWithdrawal(ticket);
        if (currentReceipt.getSuccessIndicatorFlag())
            updateTotalAmount(currentAccount.getAccountType(), -ticket.getAmountOfTransaction());
        Account acct=getAccount(accIndex);

        return currentReceipt;
    }

    //it receives ticket, index of account and Calendar object representing expiration date of object
    // inside it calls clearCheck for a given account, gets receipt back, if receipt is succesfull, updates total amount
    // in static variable for checking account
    // returns receipt to caller
    //This method can throw exceptions if:
    //    account doesnt exist
    //account is closed
    //invalid amount
    public TransactionReceipt clearCheck(TransactionTicket ticket) {
        Calendar date=ticket.getCheck().getDateOfCheck();
        int accNum = ticket.getAccountNumber();
        int accIndex = findAcct(accNum);
        if (accIndex == -1) {
            throw new InvalidAccountException("Check Clearance", accNum, "Account doesn't exist");
        }
        Account currentAccount = getAccount(accIndex);
        if (currentAccount.getAccountStatus().equals("Closed")) {
            throw new InvalidAccountException("Check Clearance", accNum, " Account is closed");
        }
        if (ticket.getAmountOfTransaction() < 0) {
            throw new InvalidAmountException("Check Clearance", accNum, ticket.getAmountOfTransaction(), currentAccount.getAccountBalance(),
                    "Invalid number entered");
        }
        TransactionReceipt currentReceipt = accounts.get(accIndex).clearCheck(ticket, date);
        if (currentReceipt.getSuccessIndicatorFlag())
            updateTotalAmount("Checking", -(currentReceipt.getPreTransactionBalance() - currentReceipt.getPostTransactionBalance()));
        else if (!currentReceipt.getSuccessIndicatorFlag()
                && currentReceipt.getReasonForFailure().equals("Insufficient funds: 2.5$ Overdraft fee has been charged")) {
            updateTotalAmount("Checking", -2.5);
        }

        Account acct=getAccount(accIndex);

        return currentReceipt;
    }

    // returns int representing total number of accounts in the bank
    public int getTotalNumberOfAccounts() {
        return accounts.size();
    }


    //takes in arraylist of integers returns a copy of it;
    public ArrayList<Integer> deepCopy(ArrayList<Integer> arr) {
        ArrayList<Integer> copy = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            copy.add(arr.get(i));
        }
        return copy;
    }
    /*
   this method is bubble sort using comparator. it takes in Arraylist of keys, accounts and Comparator
   it performs sorting on accounts list using bubble sort, using Comparator to compare elements.
   It doesnt swap elements in accounts, insted shifting corresponding values in keys list
   method is void
    */
    private void bubbleSortByComparator(ArrayList<Integer> key,ArrayHeap<Account> listOfAccounts,
                                        Comparator<Account> comparator){
        key.clear();
        for(int i=0;i<accounts.size();i++)
            key.add(i);

        boolean notDone=true;
        int index1, index2;
        Account acc1,acc2;
        do{
            notDone=false;
            for(int i=0;i<key.size()-1;i++){
                index1=key.get(i);
                index2=key.get(i+1);
                acc1=listOfAccounts.get(index1);
                acc2=listOfAccounts.get(index2);
                if(comparator.compare(acc1,acc2)>0){
                    key.set(i,index2);
                    key.set(i+1,index1);
                    notDone=true;
                }

            }
        }while(notDone);
    }

    // accepts already set up ticket and adds it to the queue
    public void enqueue(TransactionTicket ticket){
        tickets.enqueue(ticket);

    }
    // returns boolean representing whether there is anything left in queue
    public boolean hasNextTicket(){
        return !tickets.empty();
    }
    // method dequeues a single ticket from tickets queue
    // it procceses the transacton baed on its type
    // returns Transaction Receipt
    public TransactionReceipt dequeue() {
        TransactionTicket ticket = tickets.dequeue();
        String type = ticket.getTypeOfTransaction();
        switch (type) {
            case ("Account Opening"):
                 return openNewAccount(ticket);
            case ("Balance Inquiry"):
                return balanceCheck(ticket);

            case ("Deposit"):
                return deposit(ticket);

            case ("Withdrawal"):
                return withdrawal(ticket);

            case ("Check Clearance"):
                return clearCheck(ticket);

            case ("Account Deletion"):
                return deleteAcct(ticket);

            case ("Account Closure"):
                return closeAcct(ticket);

            case ("Account Reopening"):
                return reopenAcct(ticket);

            case ("Account Info"):
                return acctInfoBySSN(ticket);

            default:
                return getAccInfoWithHistory(ticket);

    }
    }
//returns sorted arraylist of accounts using heap sort. no comparators are passed, default heap sorting critearia is Account numer
//all accounts are safe copies, for security reasons.
    public ArrayList<Account> getSortedByAccountNumber() throws EmptyHeapException {
        for (int i = 0; i < accounts.size(); i++) {
            heap.add(accounts.get(i));
        }
        ArrayList<Account> temp = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            temp.add(makeCopy(heap.removeMin()));
        }
        return temp;

    }
    // returns an arrayList of Accounts sorted based on name, using heap sort and Comparators
    //all accounts are safe copies, for security reasons.
    public ArrayList<Account>  getSortedByBalance()throws EmptyHeapException {
        for(int i=0;i<accounts.size();i++) {
            heap.add(accounts.get(i), new AccountComparatorByBalance());
        }
            ArrayList<Account> temp=new ArrayList<>();
            for(int i=0;i<accounts.size();i++){
                temp.add(makeCopy(heap.removeMin(new AccountComparatorByBalance())));
            }
            return temp;
    }
    //returns an ArrayList of accts, sorted based on name, using comparators and heap sort
    //all accounts are safe copies, for security reasons.
    public ArrayList<Account>  getSortedByName()throws EmptyHeapException{
        for(int i=0;i<accounts.size();i++) {
            heap.add(accounts.get(i), new AccountComparatorByName());
        }
            ArrayList<Account> temp=new ArrayList<>();
            for(int i=0;i<accounts.size();i++){
                temp.add(makeCopy(heap.removeMin(new AccountComparatorByName())));
            }
            return temp;
    }
    //returns an ArrayList with accounts sorted based on ssn using account heap, heap sort and comparator
    //all accounts are safe copies, for security reasons.
    public ArrayList<Account>  getSortedBySSN() throws EmptyHeapException{
        for(int i=0;i<accounts.size();i++){
            heap.add(accounts.get(i),new AccountComparatorBySSN());
        }
        ArrayList<Account> temp=new ArrayList<>();
        for(int i=0;i<accounts.size();i++){
            temp.add(makeCopy(heap.removeMin(new AccountComparatorBySSN())));
        }
        return temp;
    }

}
