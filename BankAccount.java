import java.util.Random;
import java.util.Scanner;

public abstract class BankAccount
{
    public String accountName;
    protected double balance;
    private int accountNumber;
    Scanner s = new Scanner(System.in);
    Random r = new Random();

    public BankAccount() {
        System.out.print("Would you like to set up as new account?: (y/n) ");
        String answer = s.next();
        if(answer.equalsIgnoreCase("y")) {
            userInput();
        }
    }

    public BankAccount(String accountName, double balance, int accountNumber) {
        this.accountName = accountName;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public double deposit(double amount) {
        this.balance += amount;
        return this.balance;
    }

    public String withdraw(double amount) {
        if((this.balance-amount) < 0) {
            return "Insufficient funds";
        }else {
            this.balance -= amount;
            return "Withdrawl successful, account balance: " + this.balance;
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String name) {
        this.accountName = name;
        System.out.println("Your account name is now " + this.accountName);
    }

    public void userInput()
    {

        boolean isEnded = false;
        this.balance = 0;

        if(this.accountName == null) {
            System.out.print("Please enter your name: ");
            this.accountName = s.next();
        }

        //while (!isEnded)
        //{
            try
            {
               System.out.print("Please enter your current balance: ");
                this.balance = s.nextDouble();
                while (this.balance < 0)
                {
                    System.out
                        .println("Oops, try again, number was not positive.");
                    this.balance = s.nextDouble();
                }
                isEnded = true;
            }
            catch (Exception e)
            {
                System.out.println("Incorrect input, please try again");
                s.nextLine();
                userInput();
            }
        //}


        this.accountNumber = r.nextInt(100);
    }
}
