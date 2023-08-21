package org.example;

import java.util.stream.IntStream;

class Customer extends Thread {
    private final Restaurant restaurant;
    private int cycles;
    private int state;
    private static final int WAITING_FOR_TABLE = 1;
    private static final int EATING = 2;
    private static final int DONE = 3;

    public Customer(Restaurant restaurant, String customerName) {
        super(customerName);
        this.restaurant = restaurant;
        this.state = WAITING_FOR_TABLE;
    }

    public void run() {
        try {
            IntStream.range(1, Utils.MAX_CYCLES).forEach(number -> {
                try {
                    Utils.sleep((int) (Math.random() * 3 * Utils.SECOND));
                    this.state = WAITING_FOR_TABLE;
                    restaurant.takeTable();
                    Utils.sleep((int) (Math.random() * 3 * Utils.SECOND));
                    this.state = EATING;
                    restaurant.freeTable();
                    this.cycles++;
                    System.out.println(Thread.currentThread().getName() + " has completed " + this.cycles + " cycles.");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            this.state = DONE;
            System.out.println(Thread.currentThread().getName() + " has completed ALL cycles.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDone () {
        return this.state == DONE;
    }

    public boolean isWaiting () {
        return this.state == WAITING_FOR_TABLE;
    }

    public boolean isEating () {
        return this.state == EATING;
    }

}
