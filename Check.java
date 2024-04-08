import java.util.Calendar;

public class Check {
    private int accountNumber;
    private double checkAmount;
    private Calendar dateOfCheck;

    //constructor that takes in date as calendar objest, int account number, and double amount of check
    // and assigns these values to corresponding data fields of this object
    public Check(int accountNumber, double checkAmount, Calendar dateOfCheck) {
        this.accountNumber = accountNumber;
        this.checkAmount = checkAmount;
        this.dateOfCheck = (Calendar) dateOfCheck.clone();
    }
    public Check(Check ch){
        this.accountNumber=ch.getAccountNumber();
        this.checkAmount=ch.getCheckAmount();
        this.dateOfCheck=(Calendar) ch.getDateOfCheck();
    }

    //constructor which takes in date as string, int account number, and double amount of check
    // it converts string data to Calendar object and assigns it to dateOFcheck as well as the
    // other values to their corresponding data fields
    public Check(int accountNumber, double checkAmount, String date) {
        this.accountNumber = accountNumber;
        this.checkAmount = checkAmount;
        Calendar dateOfCheck = Calendar.getInstance();
        int startIndex = 0;
        int endIndex = date.indexOf("/");
        String month = date.substring(0, endIndex);
        startIndex = endIndex + 1;
        endIndex = date.indexOf("/", startIndex);
        String day = date.substring(startIndex, endIndex);
        String year = date.substring(endIndex + 1, date.length());
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        int dayInt = Integer.parseInt(day);

        dateOfCheck.set(yearInt, monthInt - 1, dayInt);


        this.dateOfCheck = dateOfCheck;
    }

    //gets int account number
    public int getAccountNumber() {
        return accountNumber;
    }

    // gets double check amount
    public double getCheckAmount() {
        return checkAmount;
    }

    // gets a copy of calendar object representing matuiry date of the check
    public Calendar getDateOfCheck() {
        return (Calendar) dateOfCheck.clone();
    }

    //takes in int and assigns it to account number
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    // takes  in double and assigns it to check amount
    public void setCheckAmount(double checkAmount) {
        this.checkAmount = checkAmount;
    }

    // takes in Calendar, creates a copy of it, assigning it to the dateOFCheck datafield
    public void setDateOfCheck(Calendar dateOfCheck) {
        this.dateOfCheck = (Calendar) dateOfCheck.clone();
    }

}
