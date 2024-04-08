import java.util.Comparator;

public class AccountComparatorByName implements Comparator<Account> {
    /*
    takes in two accounts, compares them by name, if names are equal compares by account number
    uses name.compareTo which consideres both first and last name
     */
    public int compare(Account first, Account second){
        Name nameOne=first.getDepositor().getName();
        Name nameTwo=second.getDepositor().getName();
        if(nameOne.equals(nameTwo)){
            int accNumOne=first.getAccountNumber();
            int accNumTwo=second.getAccountNumber();
            return accNumOne-accNumTwo;
        }
        return nameOne.compareTo(nameTwo);
    }
}
