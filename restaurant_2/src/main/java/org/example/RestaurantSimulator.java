package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.Utils.*;

public class RestaurantSimulator {

    public static void main(String[] args) {
        List<Customer> customerList = new ArrayList<>();
        Restaurant restaurant = new Restaurant(NUMBER_OF_TABLES);
        new Thread(() -> {
            for (int i = 1; i <= NUMBER_OF_CUSTOMERS; i++) {
                Customer customer = new Customer(restaurant, "Customer-" + i);
                customer.start();
                customerList.add(customer);
                System.out.println("Customer-" + i + " has arrived.");
                Utils.sleep(SECOND);
            }
        }).start();
        while (true) {
            Utils.sleep(5 * SECOND);
            System.out.println("------------");
            System.out.println("Total customers: " + customerList.size());
            System.out.println("Total waiting customers: " + customerList.stream().filter(Customer::isWaiting).toList().size());
            System.out.println("Total eating customers: " + customerList.stream().filter(Customer::isEating).toList().size());
            System.out.println("Total done customers: " + customerList.stream().filter(Customer::isDone).toList().size());
            System.out.println("------------");
        }
    }
}
