
public class SavingsAccount extends BankAccount
{
    public double interest;


    public SavingsAccount(String accountName, double balance, int accountNumber) {
        super(accountName, balance, accountNumber);
    }

    public SavingsAccount(String accountName, double balance, int accountNumber, double interest) {
        super(accountName, balance, accountNumber);
        this.interest = interest;
    }

    public double calcInterest() {
        double decimalInterest = this.interest/100;
        double bankInterest = decimalInterest * this.balance;
        this.balance += bankInterest;
        return this.balance;
    }

    public double calcInterest(double interest) {
        double decimalInterest = this.balance * (interest/100);
        double bankInterest = decimalInterest + this.balance;
        this.balance = bankInterest;
        return this.balance;
    }
}
