package l02;

public class L02 {
    
    
    public static void main(String[] args) {
        
        Account accountOne = new Account(0);
        Account accountTwo = new Account(0);
        accountOne.deposit(200);
        accountTwo.deposit(300);
        
        Thread t1 = new Thread(){
            public void run(){
                Account.transfer(accountOne, accountTwo, 300);
                
                
                
            }
        };
        Thread t2 = new Thread(){
            public void run(){
                Account.transfer(accountTwo, accountOne, 400);

            }
        };
        t1.start();
        t2.start();
        System.out.println(accountOne.getBalance());
        System.out.println(accountTwo.getBalance());
    }
    }
