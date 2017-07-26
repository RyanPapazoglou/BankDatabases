import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class jdbcClass
{
    static Scanner    s      = new Scanner(System.in);
    static Connection myConn = null;
    static Statement  myStmt = null;
    static ResultSet  myRs   = null;
    static Random     rand   = new Random();

    public static void main(String[] args)
        throws SQLException
    {
        try
        {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/demo",
                "student",
                "password");

            System.out.println("Database connection successful!\n");

            // 2. Create a statement
            myStmt = myConn.createStatement();

            String input = null;

            do
            {
                input = null;
                System.out.println(
                    "Welcome to the Bank, what would you like to access?");
                System.out.println("Are you an existing customer or new?: ");
                System.out.println();
                System.out.println("Type 999 to quit");
                System.out.print("(existing/new): ");
                input = s.next();

                if (input.equalsIgnoreCase("existing"))
                {
                    existingCustomer();
                }
                else if (input.equalsIgnoreCase("new"))
                {
                    newCustomer();
                }
                else if (input.equals("999"))
                {
                    break;
                }
                else
                {
                    System.out.println("Invalid input, try again.");
                    main(null);
                }
            }
            while (!input.equals("999"));
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
        finally
        {
            if (myRs != null)
            {
                myRs.close();
            }

            if (myStmt != null)
            {
                myStmt.close();
            }

            if (myConn != null)
            {
                myConn.close();
            }
        }
    }


    public static ResultSet viewBalance()
    {
        System.out.print("Please enter account ID: ");
        String acc_id = null;
        String query = null;

        try
        {
            acc_id = s.next();
            System.out.println("Retrieving balance for ID: " + acc_id);
            query = "select balance from bank_account where id = " + acc_id;

            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);

            return myRs;

        }
        catch (Exception e)
        {

            System.out
                .println("Something went wrong! Please re-enter account ID.");
            viewBalance();

        }

        return myRs;

    }


    public static ResultSet withdraw()
    {
        System.out.print("Please enter account ID: ");
        String acc_id = null;
        String query = null;
        double amount = 0;
        double currentBal = 0;
        double newBal = 0;
        int rowsAffected = 0;

        try
        {
            acc_id = s.next();
            System.out.print("How much would you like to withdraw?: ");
            amount = s.nextDouble();

            query = "select balance from bank_account where id = " + acc_id;
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);

            while (myRs.next())
            {
                currentBal = Double.parseDouble(myRs.getString("balance"));
            }

            newBal = currentBal - amount;

            System.out.println("Withdrawing " + amount);

            query = "UPDATE bank_account SET balance= " + newBal
                + " where id = " + acc_id + ";";

            rowsAffected = myStmt.executeUpdate(query);
            // myRs.next();

            query = "select balance from bank_account where id = " + acc_id;

            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);

            return myRs;

        }
        catch (Exception e)
        {
            System.out.println("Opps something went wrong please try again!");
            System.out.println(e);
            withdraw();
        }

        return myRs;

    }


    public static ResultSet deposit()
    {
        System.out.print("Please enter account ID: ");
        String acc_id = null;
        String query = null;
        double amount = 0;
        double currentBal = 0;
        double newBal = 0;
        int rowsAffected = 0;
        try
        {
            acc_id = s.next();
            System.out.print("How much would you like to deposit?: ");
            amount = s.nextDouble();

            query = "select balance from bank_account where id = " + acc_id;
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);

            while (myRs.next())
            {
                currentBal = Double.parseDouble(myRs.getString("balance"));
            }

            newBal = currentBal + amount;

            System.out.println("Depositing " + amount);

            query = "UPDATE bank_account SET balance= " + newBal
                + " where id = " + acc_id + ";";

            rowsAffected = myStmt.executeUpdate(query);
            // myRs.next();

            query = "select balance from bank_account where id = " + acc_id;

            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);

            return myRs;

        }
        catch (Exception e)
        {
            System.out.println("Error occured!");
            System.out.println(e);
            deposit();
        }
        return myRs;
    }


    public static void newCustomer()
    {
        String query = null;
        String fName = null;
        String lName = null;
        String email = null;
        // int acc_id = 0;
        double bal = 0;
        int rowsAffected = 0;

        try
        {
            System.out.println(
                "Welcome to the bank! We need some info from you to set up your account!");
            System.out.print("Please enter your first name: ");
            fName = s.next();
            System.out.print("Please enter your Last name: ");
            lName = s.next();
            System.out.print("Please enter your email address: ");
            email = s.next();
            System.out.print("Enter your starting balance: ");
            bal = s.nextDouble();

            // acc_id = rand.nextInt(1000);

            query =
                "INSERT INTO bank_account (`last_name`, `first_name`, `email`, `balance`) VALUES ("
                    + "'" + lName + "', '" + fName + "', '" + email + "', "
                    + bal + ");";

            rowsAffected = myStmt.executeUpdate(query);
            // System.out.println("Rows affected: " + rowsAffected);
            System.out.println("Setting up account....");
            System.out.println("Account created!");

            query = "select * from bank_account where first_name = '" + fName
                + "' AND last_name = '" + lName + "'";

            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);

            int acc_id = 0;

            while (myRs.next())
            {
                System.out.println(
                    "Welcome " + myRs.getString("first_name") + " "
                        + myRs.getString("last_name") + ", "
                        + " your account has been successfully created with id "
                        + myRs.getString("id") + " and a balance of "
                        + myRs.getString("balance"));
                System.out.println(
                    "An email will be sent to " + myRs.getString("email"));

                acc_id = Integer.parseInt(myRs.getString("id"));
            }

            createLogin(acc_id);

        }
        catch (Exception e)
        {
            System.out
                .println("Oops error " + e + " has occured! \n Try again!");
            newCustomer();
        }
    }


    public static void existingCustomer()
        throws SQLException
    {

        try
        {

            if (authentication())
            {
                System.out.println("Welcome! What would you like to do today?");
                System.out.println(
                    "Please enter one of the following typed exactly the same:");
                System.out.println("1. Check Balance");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Delete Account");
                System.out.print("Input answer (1/2/3/4): ");

                String input = s.next();

                if (input.equalsIgnoreCase("1"))
                {
                    displayUpdates(viewBalance());
                }
                else if (input.equalsIgnoreCase("2"))
                {
                    displayUpdates(withdraw());
                }
                else if (input.equalsIgnoreCase("3"))
                {
                    displayUpdates(deposit());

                }
                else if (input.equals("4"))
                {
                    deleteAccount();
                }
            }
            else
            {
                System.out.println("Invalid password!");
                System.out.println("Please enter again");
                existingCustomer();
            }
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong! Please try again");
            System.out.println(e);
            existingCustomer();
        }
    }


    public static void createLogin(int acc_id)
    {

        String query = null;
        int rowsAffected = 0;

        try
        {
            System.out.println(
                "Now that your account has been created, please create a username and password below.");
            System.out.print("Username: ");
            String un = s.next();
            System.out.print("Password: ");
            String pw = s.next();

            query =
                "INSERT INTO login (`username`, `password`, `acc_id`) VALUES ('"
                    + un + "', '" + pw + "', " + acc_id + ");";

            rowsAffected = myStmt.executeUpdate(query);

            query = "select * from login where username = '" + un + "'";

            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);

            while (myRs.next())
            {
                System.out.println(
                    "Login created for " + myRs.getString("username")
                        + " with password " + myRs.getString("password")
                        + " and account id of " + myRs.getString("acc_id"));
            }

            System.out.println("Directing to portal");
            existingCustomer();

        }
        catch (Exception e)
        {
            System.out.println(
                "Possible duplicate username, please enter a different one");
            System.out.println(e);
            createLogin(acc_id);
        }
    }


    public static void deleteAccount()
    {
        String acc_id = null;
// String fName = null;
// String lName = null;
        String query = null;
        String email = null;
        String inputEmail = null;
        String un = null;
// String id = null;
        int rowsAffected = 0;

        try
        {
            System.out.println(
                "Sorry to see you go, please login so we can confirm you are the owner of this account.");
// System.out.print("Please enter your first name: ");
// fName = s.next();
// System.out.print("Please enter your last name: ");
// lName = s.next();
            System.out.print("Please enter your account ID: ");
            acc_id = s.next();

            if (authentication())
            {
                query = "select * from bank_account where id =" + acc_id;

                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery(query);

                while (myRs.next())
                {
                    email = myRs.getString("email");
                }

                query = "select * from login where acc_id =" + acc_id;

                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery(query);

                while (myRs.next())
                {
                    un = myRs.getString("username");
                }

                System.out.print("Please confirm your account email address: ");
                inputEmail = s.next();

                if (email.equals(inputEmail))
                {
                    System.out
                        .println("Confirmed.... deleting account for " + un);
                    query = "delete from bank_account where id= " + acc_id;
                    rowsAffected = myStmt.executeUpdate(query);

                    query = "delete from login where username = '" + un + "'";
                    rowsAffected = myStmt.executeUpdate(query);
                }
                else
                {
                    System.out.println(
                        "Sorry invalid email address... account will not be deleted");
                    System.out.println("Redirecting to main menu");
                    existingCustomer();
                }
            }
            else
            {
                System.out.println("Invalid password, please try again.");
                deleteAccount();
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
            deleteAccount();
        }
    }


    public static boolean authentication()
        throws SQLException
    {
        String un = null;
        String pw = null;
        String query = null;
        boolean auth = false;

        try
        {
            System.out.println("Please enter your username: ");
            un = s.next();
            System.out.println("Please enter your password: ");
            pw = s.next();

            query = "select * from login where username = '" + un + "'";

            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(query);
            String dbpw = null;
            while (myRs.next())
            {
                dbpw = myRs.getString("password");
            }

            if (dbpw.equals(pw))
            {
                System.out.println("Successful login");
                auth = true;
            }
        }
        catch (Exception e)
        {
            System.out.println("Authentication failed.");
            main(null);
        }
        return auth;
    }

    public static void displayUpdates(ResultSet rs)
        throws SQLException
    {
        String balance = null;

        try
        {
            while (rs.next())
            {
                System.out.println(
                    "New balance for account is " + rs.getString("balance"));
            }
        }
        catch (Exception e)
        {
            System.out.println("Display error occured.... rebooting");
            main(null);
        }
    }
}
