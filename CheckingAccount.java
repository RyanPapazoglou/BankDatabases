
public class CheckingAccount extends BankAccount
{
    public CheckingAccount(String accountName, double balance, int accountNumber) {
        super(accountName, balance, accountNumber);
    }

    public CheckingAccount()
    {
        // TODO Auto-generated constructor stub
        super();
    }

    public String withdraw(double amount, double fee) {
        double decimalFee = fee/100;
        double totalWithdrawl = amount + decimalFee;
        if((this.getBalance() - totalWithdrawl) < 0) {
            System.out.println("Not enough");
        }else {
            this.balance -= totalWithdrawl;
        }
        return "Your balance is now " + this.balance;
    }
}