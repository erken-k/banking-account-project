import java.util.Comparator;

public class AccountComparatorByBalance implements Comparator<Account> {
    public int compare(Account one, Account two){
        double balanceOne=one.getAccountBalance();
        double balanceTwo=two.getAccountBalance();
        if(balanceOne==balanceTwo){
            return one.getAccountNumber()-two.getAccountNumber();
        }
        return (balanceOne>balanceTwo) ? 1:-1;
    }

}
