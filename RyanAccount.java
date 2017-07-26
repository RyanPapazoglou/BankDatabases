import java.util.Random;
import java.util.Scanner;

public class RyanAccount
{
    @SuppressWarnings("null")
    public static void main(String[] args)
    {
        /*CheckingAccount cAccount = new CheckingAccount("Ryan", 100.00, 1001);
        SavingsAccount sAccount = new SavingsAccount("Ryan", 1000, 1001);
        */

        CheckingAccount yourAccount = new CheckingAccount();

        System.out.println(
            "Account set up with name: " + yourAccount.getAccountName()
                + ". Number: " + yourAccount.getAccountNumber() + ". Balance: "
                + yourAccount.getBalance());
    }
}
