package l02;

public class Account {
  private int balance;

  public Account(int balance) {
    this.balance = balance;
  }

  public int deposit(int amount) {
    this.balance += amount;
    return balance;
  }

  public int withdraw(int amount) {
    if (this.balance - amount < 0) {
      return -1; // if a call to this method returned -1, then the withdrawal failed
    }

    this.balance -= amount;
    return balance;
  }

  public int getBalance() {
    return this.balance;
  }

  public static int transfer(Account account1, Account account2, int amount) {
    if (account1.withdraw(amount) == -1) {
      return -1; // if a call to this method returned -1, then the transfer failed
    }

    account2.deposit(amount);
    return account2.getBalance();
  }
}
