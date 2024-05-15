
// importing all required files
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BallerBank {
    public static void main(String[] args) throws FileNotFoundException{

        //declaring and initializing variables and objects
        final int OVERDRAFT_FEE = 25;
        int count = 0;
        int accountNumber;
        double balance, interestRate, interestEarned;
        BankAccount[] accounts = new BankAccount[100];

        File accountFile = new File("accounts.txt");
        Scanner infile = new Scanner(accountFile);

        // adding the bank informations to the accounts array
        while (infile.hasNextLine()){
            accountNumber = Integer.parseInt(infile.next());
            balance = Double.parseDouble(infile.next());
            interestRate = Double.parseDouble(infile.next());
            interestEarned = Double.parseDouble(infile.next());
            accounts[count] = new BankAccount(accountNumber, balance, interestRate, interestEarned);
            count++;
        }
        infile.close();

        // declaring the files, scanner and other account variables
        File transactionFile = new File("transactions.txt");
        Scanner infile2 = new Scanner(transactionFile);

        String transactionMethod;
        int transactionAccountNumber;
        double transactionAmount;

        // creating a loop that checks all the informations in the transactions file and runs it accordingly
        while (infile2.hasNextLine()){

            transactionMethod = infile2.next();

            //checking for the three word line in transaction file
            if (transactionMethod.equals("DEP") || transactionMethod.equals("ATM") || transactionMethod.equals("ACH")){

                transactionAccountNumber = Integer.parseInt(infile2.next());
                transactionAmount = Double.parseDouble(infile2.next());

                // Doing the operations of the three word lines accordingly
                switch (transactionMethod) {

                    //deposits the amount given in the respective account
                    case "DEP":
                        for (int i = 0; i < count; i++){
                            if (transactionAccountNumber == accounts[i].getAccountNumber()){
                                accounts[i].deposit(transactionAmount);
                                System.out.printf("\n- Deposit of $%,.2f in account %d.\n",transactionAmount, transactionAccountNumber);
                            }
                        }
                        break;
                    
                    // withdraws the amount accordingly from the account
                    // also gives failed message if withdrawl amount is more than balance
                    case "ATM":
                        for (int i = 0; i < count; i++){
                            if (transactionAccountNumber == accounts[i].getAccountNumber()){
                                if (accounts[i].getBalance() < transactionAmount){
                                    System.out.printf("\n>> Withdrawl failed! Insufficient fund in account %d<<\n", transactionAccountNumber);
                                } else{
                                    accounts[i].withdraw(transactionAmount);
                                    System.out.printf("\n- Withdrawl of $%,.2f from account %d.\n", transactionAmount, transactionAccountNumber);
                                }
                            }
                        }
                        break;
                    
                    // does the ACH transfer and checks for the overdraft and charges overdraft fee if balance less than zero
                    // also gives failed message if balance goes belows -500
                    case "ACH":
                        for (int i = 0; i < count; i++){
                            if (transactionAccountNumber == accounts[i].getAccountNumber()){
                                if ((accounts[i].getBalance() - transactionAmount) < -500 ){
                                    System.out.printf("\n>> ACH transfer failed! Insufficient fund in account %d <<\n", transactionAccountNumber );
                                    System.out.print(" **Overdraft fee of $25 is assessed**\n");
                                    accounts[i].withdraw(OVERDRAFT_FEE);
                                } else{
                                    System.out.printf("\n- ACH transfer of $%,.2f from account %d\n", transactionAmount, transactionAccountNumber);  
                                    if (transactionAmount > accounts[i].getBalance()){
                                        System.out.print(" **Overdraft fee of $25 is assessed**\n");
                                        accounts[i].withdraw(OVERDRAFT_FEE);
                                    }
                                    accounts[i].withdraw(transactionAmount);                                 
                                    
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            // checks for the BAL keyword in transaction file and executes if necessary
            // show the balance of the account given in the file
            else if (transactionMethod.equals( "BAL")){
                
                transactionAccountNumber = Integer.parseInt(infile2.next());
                for (int i = 0; i < count; i++){
                    if (transactionAccountNumber == accounts[i].getAccountNumber()){
                        System.out.printf("\nBalance for account number %s: $%,.2f.\n", transactionAccountNumber, accounts[i].getBalance() );
                    }
                }                               
            }

            // checks for the FEE keyword in transaction file and executes if necessary
            // adds the given fee to all the accounts in the array
            else if (transactionMethod.equals("FEE")){
                transactionAmount = Double.parseDouble(infile2.next());
                for (int i = 0; i < count; i++){
                    accounts[i].withdraw(transactionAmount);
                    if (accounts[i].getBalance() < 0){
                        accounts[i].withdraw(OVERDRAFT_FEE);
                    }
                }  
                System.out.println("\nService fee of $" + transactionAmount + " is assessed from all accounts.");             

            }

            //checking for the one word line in transaction file
            else if (transactionMethod.equals("OVR") || transactionMethod.equals("AVG") || transactionMethod.equals("INT") || transactionMethod.equals("TIN") || transactionMethod.equals("MAX")){

                switch (transactionMethod) {

                    //prints all the overdrafted accounts
                    case "OVR":
                        System.out.println("\nOverdrafted Accounts:");
                        for (int i = 0; i < count; i++){
                            if (accounts[i].getBalance() < 0){
                                System.out.println("-> Account " + accounts[i].getAccountNumber());
                            }
                        }
                        break;
                    
                    // calculates the average balance of all the accounts in the array/file
                    case "AVG":
                        double total = 0;
                        for (int i = 0; i < count; i++){
                           total += accounts[i].getBalance(); 
                        }
                        double averageBalance = total / count;
                        System.out.printf("\nAverage of all account balances: %,.2f.\n", averageBalance);

                        break;
                    
                    // adds the interest to the balance for all of the accounts
                    case "INT":
                        for (int i = 0; i < count; i++){
                            accounts[i].addInterest();
                        }
                        break;
                    
                    // prints the total interest earned by all the accounts
                    case "TIN":
                        double totalInterestEarned = 0;
                        for (int i = 0; i < count; i++){
                            totalInterestEarned += accounts[i].getInterestEarned();
                        }
                        System.out.printf("\nTotal interest earned by all accounts: %,.2f.\n",totalInterestEarned);
                        break;

                    // prints the account that has the highest balance 
                    case "MAX":
                        double maxBalance = accounts[0].getBalance();
                        int maxBalanceAccount = 0;
                        for (int i = 1; i < count; i++){
                            if (maxBalance < accounts[i].getBalance()){
                                maxBalance = accounts[i].getBalance();
                                maxBalanceAccount = accounts[i].getAccountNumber();
                            }
                        }
                        System.out.printf("\nAccount %d has the highest balance: $%.2f\n",maxBalanceAccount, maxBalance);
                        break;
                    default:
                        break;
                }
            }
        }
        infile2.close();
        
        // calling the method that updates all the informations into an updatedAccounts file
        updateAccountFile(count, accounts);

    }// end main

    // method that updates the infos of all the accounts and stores into the file updatedAccounts
    public static void updateAccountFile(int count, BankAccount[] accounts) throws FileNotFoundException{
        PrintWriter output = new PrintWriter("updatedAccounts.txt");
        for (int i = 0; i < count; i++){
            output.printf("%d %.2f %.3f %.2f\n",accounts[i].getAccountNumber(), accounts[i].getBalance(), accounts[i].getInterestRate(), accounts[i].getInterestEarned());
        }
        output.close();
    }// end updateAccountFile

}// end class
