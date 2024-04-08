import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;
import java.util.List;

import MyExceptions.*;

public class hw0 {
    public static void main(String[] args) throws IOException {
        Bank bank = new Bank();
        char choice;
        boolean notDone = true;
        File testFile = new File("src\\sampleTestCases.txt");
        PrintWriter output = new PrintWriter(new File("output1.txt"));
        Scanner fromFile = new Scanner(testFile);

        try {
            readAccts(bank);
            printAccounts(bank, output);
        } catch (NullPointerException exception) {
            System.out.println("Error: Initial Database is not available");
        }
        printHW20(bank,output);
        output.println("\n***********************************");
        output.printf("\nTotal amount in CD Accounts: %.2f", bank.getTotalAmountInCDAccts());
        output.printf("\nTotal Amount in Checking Accounts: %.2f", bank.getTotalAmountInCheckingAccts());
        output.printf("\nTotal Amount in Saving Accounts: %.2f", bank.getTotalAmountInSavingAccts());
        output.printf("\nTotal Amount in all accounts: %.2f\n", bank.getTotalAmountInAllAccts());
        output.println("\n***********************************");
        menu();
        do {
            System.out.println();
            System.out.println("-------------------------------------------------------");
            System.out.println("Please choose operation");
            choice = fromFile.next().charAt(0);
            try {
                switch (choice) {
                    case 'q':
                    case 'Q':
                        notDone = false;
                        break;
                    case 'b':
                    case 'B':
                        balance(bank, output, fromFile);
                        break;
                    case 'd':
                    case 'D':
                        deposit(bank, output, fromFile);
                        break;
                    case 'w':
                    case 'W':

                        withdrawal(bank, output, fromFile);

                        break;
                    case 'c':
                    case 'C':

                        clearCheck(bank, output, fromFile);

                        break;
                    case 'n':
                    case 'N':

                        newAcct(bank, output, fromFile);

                        break;
                    case 's':
                    case 'S':

                        closeAcct(bank, output, fromFile);


                        break;
                    case 'r':
                    case 'R':

                        reopenAcct(bank, output, fromFile);


                        break;
                    case 'x':
                    case 'X':

                        deleteAcct(bank, output, fromFile);


                        break;
                    case 'i':
                    case 'I':
                        acctInfo(bank, output, fromFile);

                        break;
                    case 'h':
                    case 'H':
                        accInfoWithTransactionHistory(bank, output, fromFile);
                        break;

                    default:
                        throw new InvalidMenuSelectionException("Entered value: " + choice + " is not an option");
                }
            } catch (InvalidAccountException ex) {
                output.println(ex.getMessage());
            } catch (InvalidAmountException ex) {
                output.println(ex.getMessage());
            } catch (InvalidInputException ex) {
                output.println(ex.getMessage());
            } catch (InsufficientFundsException ex) {
                output.println(ex.getMessage());
            } catch (InvalidMenuSelectionException ex) {
                output.println(ex.getMessage());
            } catch (PostDatedCheckException ex) {
                output.println(ex.getMessage());
            } catch (CheckTooOldException ex) {
                output.println(ex.getMessage());
            } catch (CDMaturityDateException ex) {
                output.println(ex.getMessage());
            } catch (Exception ex) {
                output.println(ex.getMessage());
            }

        } while (notDone);

            processLine(bank, output);
        output.println("\n\nUnsorted Database of Accounts: ");
        printAccounts(bank,output);
        printHW20(bank,output);
        output.println("\n***********************************");
        output.printf("\nTotal amount in CD Accounts: %.2f", bank.getTotalAmountInCDAccts());
        output.printf("\nTotal Amount in Checking Accounts: %.2f", bank.getTotalAmountInCheckingAccts());
        output.printf("\nTotal Amount in Saving Accounts: %.2f", bank.getTotalAmountInSavingAccts());
        output.printf("\nTotal Amount in all accounts: %.2f\n", bank.getTotalAmountInAllAccts());
        output.println("\n***********************************");

        output.close();


    }
    //HW20
    //prints database of accounts unsorted, sorted based on acct number, ssn
    //name, balance. Uses methods in bak to receive sorted arraylists of copies accounts,
    //prints them
    public static void printHW20(Bank bank, PrintWriter output){
        try {
            output.println("Sorted By Account Number: ");
            ArrayList<Account> copiesOfAccts = new ArrayList<>();
            copiesOfAccts=bank.getSortedByAccountNumber();
            for(int i=0;i<copiesOfAccts.size();i++) {
                output.print(copiesOfAccts.get(i));
            }
            output.println("\n***********************************\n");

            output.println("Sorted By SSN: ");
            copiesOfAccts=bank.getSortedBySSN();
            for(int i=0;i<copiesOfAccts.size();i++)
                output.print(copiesOfAccts.get(i));
            output.println("\n***********************************\n");

            output.println("Sorted By Name: ");
            copiesOfAccts=bank.getSortedByName();
            for(int i=0;i<copiesOfAccts.size();i++)
                output.print(copiesOfAccts.get(i));
            output.println("\n***********************************\n");

            output.println("Sorted By Balance: ");
            copiesOfAccts=bank.getSortedByBalance();
            for(int i=0;i<copiesOfAccts.size();i++)
                output.print(copiesOfAccts.get(i));
            output.println("\n***********************************\n");
        }catch (EmptyHeapException ex){
            output.println(ex.getMessage());
        }


        }
    // HW19
    //this method takes in bank and output. processes all tickets in bank queue, prints reciepts as it processes transactions,
    // if there is something wrong catches exceptions and prints message
    public static void processLine(Bank bank, PrintWriter output ){
        while(bank.hasNextTicket()) {
            try {
                TransactionReceipt rec = bank.dequeue();
                printReceipt(rec, output);
            } catch(Exception ex){
                output.println(ex.getMessage());
            }
        }
    }
    /*
    Method prints menu of available commands for the user
     */
    public static void menu() {
        System.out.println("W-Withdrawal");
        System.out.println("D-Deposit");
        System.out.println("C-Clear check");
        System.out.println("N-New Account");
        System.out.println("B-Balance");
        System.out.println("I-Account Info");
        System.out.println("H-Account Info plus Transaction History");
        System.out.println("S-Close(Shut) Account");
        System.out.println("R-Reopen Account");
        System.out.println("X-Delete Account");
        System.out.println("Q-quit");
    }

    /*
    input:bank and output to file
    Processing: prints into output file all accounts in the bank
     */
    public static void printAccounts(Bank bank, PrintWriter output) {

        for (int i = 0; i < bank.getTotalNumberOfAccounts(); i++) {
            output.print(bank.getAccount(i));
        }
        output.println("\n***********************************\n");

    }


    /*
    input: take sin bank object
    processing: reads account database from txt file, adds all counts to bank
    output: bank object with accounts
     */
    public static void readAccts(Bank bank) throws FileNotFoundException {
        File initialBase = new File("src\\initialDatabase.txt");
        Scanner input = new Scanner(initialBase);
        while (input.hasNext()) {
            Name currentName = new Name(input.next(), input.next());
            int ssn = input.nextInt();
            int acctNum = input.nextInt();
            String acctType = input.next();
            double balance = input.nextDouble();
            Depositor currentDepositor = new Depositor(currentName, ssn);
            TransactionTicket openingAccountTicket = new TransactionTicket("Account Opening", balance, acctNum);
            if(acctType.equals("CD")){
                String maturityDate = input.next();
                openingAccountTicket.setCDExpirationDateForNewAccount(maturityDate);
            }

            openingAccountTicket.setDepositor(currentDepositor);
            openingAccountTicket.setAccountType(acctType);
            bank.openNewAccount(openingAccountTicket);



        }
    }

    /*
    Input: Bank object, output to file, scanner input from file
    Processing: takes in account number
    creates ticket with request, fills it with info and adda to the queue
     */
    public static void balance(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please Enter the Account number");
        int accNum = input.nextInt();
        TransactionTicket balanceTicket = new TransactionTicket("Balance Inquiry", 0, accNum);
        bank.enqueue(balanceTicket);
    }

    /*
    Input: Bank object, output to file, scanner input fromf file
    Processing: takes in account number
    takes in amount if negative prints error receipt
    creates ticket, fills it with info and adds to the queue
    void
     */
    public static void deposit(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please Enter the Account number");
        int accNum = input.nextInt();
        System.out.println("Please enter amount to deposit");
        double depositAmount = input.nextDouble();
        System.out.println("Please enter CDterms");
        int newCD = input.nextInt();
        TransactionTicket depositTicket = new TransactionTicket("Deposit", depositAmount, accNum, newCD);
        bank.enqueue(depositTicket);
    }

    /*
    Input: Bank object, output to file, scanner input fromf file
    Processing: takes in account number, amount to withdraw and cd terms if neccesary
    creates a ticket with all required info for transaction and queues it to the banks's tickets queue
     */
    public static void withdrawal(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please Enter the Account number");
        int accNum = input.nextInt();
        System.out.println("Please enter amount to withdraw");
        double withdrawalAmount = input.nextDouble();

        System.out.println("Please enter CDterms");
        int newCD = input.nextInt();
        TransactionTicket ticket = new TransactionTicket("Withdrawal", withdrawalAmount, accNum, newCD);
        bank.enqueue(ticket);

    }

    /*
    Input: Bank object, output to file, scanner input fromf file
    Processing: takes in account number.
    takes in amount if amount
    takes in date.
   creates a ticket with all required info for transaction and queues it to the banks's tickets queue

     */
    public static void clearCheck(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please Enter the Account number");
        int accNum = input.nextInt();
        System.out.println("Please enter amount of the check");
        double amount = input.nextDouble();
        System.out.println("Please enter date of check (mm/dd/year)");
        String date = input.next();
        Check check = new Check(accNum, amount, date);
        TransactionTicket ticket = new TransactionTicket("Check Clearance", amount, accNum);
        ticket.setCheck(check);


        bank.enqueue(ticket);


    }

    /*
    Input: Bank object, output to file, scanner input fromf file
Processing: takes in account number
takes in name, surname, ssn, account type, cddate
creates a ticket with all required info for transaction and queues it to the banks's tickets queue
     */
    public static void newAcct(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please Enter the Account number");
        int accNum = input.nextInt();
        int accIndex = bank.findAcct(accNum);
        System.out.println("Please enter Depositors first name: ");
        String firstName = input.next();
        System.out.println("Please enter Depositors last name: ");
        String lastName = input.next();
        System.out.println("Please enter depositors ssn");
        int ssn = input.nextInt();
        if (ssn < 0 || ssn > 999999999) {
            input.nextLine();
            throw new InvalidInputException("SSN", "" + ssn, "Account Opening", accNum, "Isn't in Acceptable Format");
        }
        Depositor depositor = new Depositor(new Name(firstName, lastName), ssn);
        System.out.println("Please enter account type (Checking, CD, Savings):");
        String acctType = input.next();
        double initialDeposit = 0;
        TransactionTicket ticket = new TransactionTicket("Account Opening", initialDeposit, accNum);
        if (acctType.equals("CD")) {
            System.out.println("Please enter intital Deposit");
            initialDeposit = input.nextDouble();
            ticket.setAmountOfTransaction(initialDeposit);
            System.out.println("Please Enter CD maturity date");
            String terms = input.next();
            ticket.setCDExpirationDateForNewAccount(terms);
        }
        ticket.setDepositor(depositor);
        ticket.setAccountType(acctType);
        bank.enqueue(ticket);

    }

    /*
    Input: Bank object, output to file, scanner input fromf file
    Processing: takes in account number,
   creates ticket, creates a ticket with all required info for transaction and queues it to the banks's tickets queue
     */
    public static void deleteAcct(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please Enter the Account number");
        int accNum = input.nextInt();
        int accIndex = bank.findAcct(accNum);
        TransactionTicket ticket = new TransactionTicket("Account Deletion", 0, accNum);
        bank.enqueue(ticket);

    }

    /*
    Input: Bank object, output to file, scanner input fromf file
    Processing: takes in account number,
    creates a ticket with all required info for transaction and queues it to the banks's tickets queue

     */
    public static void closeAcct(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please Enter the Account number");
        int accNum = input.nextInt();
        TransactionTicket ticket = new TransactionTicket("Account Closure", 0, accNum);
        bank.enqueue(ticket);
    }

    /*
    Input: Bank object, output to file, scanner input fromf file
    Processing: takes in account number,
    fills in ticket, queues ticket to ticket queue in bank
     */
    public static void reopenAcct(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please Enter the Account number");
        int accNum = input.nextInt();
        TransactionTicket ticket = new TransactionTicket("Account Reopening", 0, accNum);
        bank.enqueue(ticket);
    }

    /*
    Input: Bank object, output to file, scanner input fromf file
    Processing: takes in ssn,
    creates a ticket with all required info for transaction and queues it to the banks's tickets queue
     */
    public static void acctInfo(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please enter ssn");
        int ssn = input.nextInt();
        TransactionTicket ticket=new TransactionTicket("Account Info",0,0);
        ticket.setSSN(ssn);
        bank.enqueue(ticket);


    }

    /*
    Input: Bank object, output to file, scanner input fromf file
Processing: takes in ssn, fills ticket representing current transaction adds it to queue
     */

    public static void accInfoWithTransactionHistory(Bank bank, PrintWriter output, Scanner input) throws Exception {
        System.out.println("Please enter ssn");
        Account currentAccount;
        int ssn = input.nextInt();
        TransactionTicket ticket=new TransactionTicket("Account Info With History",0,0);
        ticket.setSSN(ssn);
        bank.enqueue(ticket);

    }

    /*
    takes output to file, prints receipt after transaciton has been made
     */
    public static void printReceipt(TransactionReceipt receipt, PrintWriter output) {
        output.print( receipt.toStringAsColumn());

    }


}
