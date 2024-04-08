import java.util.Comparator;

public class AccountComparatorBySSN implements Comparator<Account> {
    /*
    takes in two accounts, compares them by ssn, if they are equal compares by account nummber
     */
    public int compare(Account one, Account two) {
        int ssn1 = one.getDepositor().getSsn();
        int ssn2 = two.getDepositor().getSsn();
        if (ssn1 == ssn2) {
            int accNum1 = one.getAccountNumber();
            int accNum2 = two.getAccountNumber();
            return accNum1 - accNum2;
        }
        return ssn1 - ssn2;
    }
}
