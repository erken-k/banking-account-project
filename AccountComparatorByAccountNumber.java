import java.util.Comparator;

public class AccountComparatorByAccountNumber implements Comparator<Account> {
    //takes in two accounts compare them by aacount number return positive if acc1>acc2 0 if equal, negative if less
    public int compare(Account one, Account two){
        int accNumOne=one.getAccountNumber();
        int accNumTwo=two.getAccountNumber();
        return accNumOne-accNumTwo;
    }
}
