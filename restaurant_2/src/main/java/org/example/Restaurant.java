package org.example;

class Restaurant {

    private int numberOfTablesAvailable;
    private final Object tableLock = new Object();

    public Restaurant(int numberOfTables) {
        this.numberOfTablesAvailable = numberOfTables;
    }

    public void takeTable() throws InterruptedException {
        synchronized (tableLock) {
            while (numberOfTablesAvailable == 0) {
                System.out.println(Thread.currentThread().getName() + " wants to take a table but none are available. Waiting...");
                tableLock.wait();
            }
            Thread.sleep(50); // Simulate delay
            numberOfTablesAvailable--;
            System.out.println(Thread.currentThread().getName() + " has taken a table. Tables left: " + numberOfTablesAvailable);
        }
    }

    public void freeTable() throws InterruptedException {
        synchronized (tableLock) {
            Thread.sleep(50); // Simulate delay
            numberOfTablesAvailable++;
            System.out.println(Thread.currentThread().getName() + " freed a table. Tables left: " + numberOfTablesAvailable);
            tableLock.notify(); // Notify a waiting customer that a table is available
        }
    }
}
