// Name: Saugat Adhikari
// Course: CSCI 2003 Spring 2024
// Program Description: This class creates a constructor that takes the accounts information, and 
//                      gets as well as sets the values accordingly if needed. It also deposits, withdraws
//                      and calculates the interests as well as adds it to the balance.

/**
 * Represents a bank account with a balance, account number, interest rate, and interest earned.
 */
public class BankAccount {

   /** The account number of the bank account. */
   private double balance;
    /** The initial balance of the bank account.*/
   private int accountNumber;
    /** The interest rate of the bank account. */
   private double intRate;
    /** The interest earned on the bank account. */
   private double intEarned;

   /**
    * Constructs a bank account with the specified parameters.
    *
    * @param accountNumber The account number of the bank account.
    * @param balance       The initial balance of the bank account.
    * @param intRate       The interest rate of the bank account.
    * @param intEarned     The interest earned on the bank account.
    */
   public BankAccount(int accountNumber, double balance, double intRate, double intEarned) {
       this.balance = balance;
       this.accountNumber = accountNumber;
       this.intRate = intRate;
       this.intEarned = intEarned;
   }

   /**
     * Sets the interest rate of the bank account.
     *
     * @param newRate The new interest rate to set.
     */
   public void setRate(double newRate) {
      this.intRate = newRate;
   }  

   /**
   * Retrieves the account number of the bank account.
   *
   * @return The account number.
   */
   public int getAccountNumber() {
      return this.accountNumber;
   }

   /**
   * Retrieves the interest rate of the bank account.
   *
   * @return The interest rate.
   */
   public double getInterestRate() {
      return this.intRate;
   }

   /**
   * Retrieves the interest earned on the bank account.
   *
   * @return The interest earned.
   */
   public double getInterestEarned() {
      return this.intEarned;
   }

   /**
   * Retrieves the balance of the bank account.
   *
   * @return The balance.
   */
   public double getBalance() {
      return balance;
   }

   /**
     * Deposits the specified amount into the bank account.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) {
      balance += amount;
  }

   /**
   * Withdraws the specified amount from the bank account.
   *
   * @param amount The amount to withdraw.
   */
   public void withdraw(double amount) {
      balance -= amount;
   }

   /**
   * Adds interest to the bank account based on the interest rate.
   */
   public void addInterest() {
      double increasedAmount = this.balance * this.intRate;
      this.balance += increasedAmount;
      this.intEarned += increasedAmount;
   }
   
   /**
   * Returns a string representation of the bank account, including account number, balance, interest rate, and interest earned.
   *
   * @return A formatted string containing account information.
   */
   public String toString(){
      String formattedString = String.format("%-16s: %-8d\n%-16s: $%-8.2f\n%-16s: %-5.3f%%\n%-16s: $%-8.2f\n", "Account Number", getAccountNumber(), "Balance", getBalance(),"Interest Rate", getInterestRate(),"Interest Earned" , getInterestEarned());
      return formattedString;
   }
}
