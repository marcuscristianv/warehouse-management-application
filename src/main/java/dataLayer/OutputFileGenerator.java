package dataLayer;

import businessLayer.Account;
import businessLayer.MenuItem;
import businessLayer.Order;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class OutputFileGenerator {
    public static void generateOrderBill(Order order, ArrayList<MenuItem> listOfItems) {
        try {
            File file = new File("Bill_" + LocalDateTime.now().getHour() + "h" + LocalDateTime.now().getMinute() + "m" + LocalDateTime.now().getSecond() + "s.txt");
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write("Order id: " + order.getOrderId() + "\nClient id: " + order.getClientId() + "\nDate: " + order.getOrderDate() + "\n\nNUMBER OF ITEMS: " + listOfItems.size() + "\n\n");

            double grandTotal = 0;
            for(MenuItem item: listOfItems) {
                grandTotal += item.getPrice();
                fileWriter.write(item.getTitle() + " - $" + item.getPrice() + "\n");
            }
            fileWriter.write("\n\nGRAND TOTAL: $" + grandTotal);
            fileWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Error: can't generate bill!\n");
        }
    }

    public static void generateReport1(String startHour, String endHour, List<Order> orders, HashMap<Order, Collection<MenuItem>> orderList) {
        try {
            File file = new File("Report1_" + LocalDateTime.now().getHour() + "h" + LocalDateTime.now().getMinute() + "m" + LocalDateTime.now().getSecond() + "s.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("REPORT TYPE 1\n\nOrders performed between " + startHour + ":00 and " + endHour + ":00\n\n");

            for(Order crt: orders) {
                fileWriter.write("Order id: " + crt.getOrderId() +  ", client id: "  + crt.getClientId() + "\n" + orderList.get(crt).toString() + "\n\n\n");
            }
            fileWriter.write("TOTAL OF ORDERS THAT MATCH THE CRITERIA: " + orders.size());
            fileWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Error: can't generate report type 1 output file!\n");
        }
    }

    public static void generateReport2(int timesOrdered, List<MenuItem> items) {
        try {
            File file = new File("Report2_" + LocalDateTime.now().getHour() + "h" + LocalDateTime.now().getMinute() + "m" + LocalDateTime.now().getSecond() + "s.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("REPORT TYPE 2\n\nProducts ordered more than " + timesOrdered + " times\n\n");

            for(MenuItem crt: items) {
                fileWriter.write(crt.toString() + "\n\n\n");
            }
            fileWriter.write("TOTAL OF PRODUCTS THAT MATCH THE CRITERIA: " + items.size());
            fileWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Error: can't generate report type 2 output file!\n");
        }
    }

    public static void generateReport3(int timesOrdered, int valueOrdered, List<Account> items) {
        try {
            File file = new File("Report3_" + LocalDateTime.now().getHour() + "h" + LocalDateTime.now().getMinute() + "m" + LocalDateTime.now().getSecond() + "s.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("REPORT TYPE 3\n\nClients that have ordered more than " + timesOrdered + " times and the value of the order was higher than " + valueOrdered + "\n\n");

            for(Account crt: items) {
                fileWriter.write(crt.toString() + "\n\n\n");
            }
            fileWriter.write("TOTAL OF CLIENTS THAT MATCH THE CRITERIA: " + items.size());
            fileWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Error: can't generate report type 3 output file!\n");
        }
    }

    public static void generateReport4(int day, Map<MenuItem, Long> map, HashSet<MenuItem> items) {
        try {
            File file = new File("Report4_" + LocalDateTime.now().getHour() + "h" + LocalDateTime.now().getMinute() + "m" + LocalDateTime.now().getSecond() + "s.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("REPORT TYPE 4\n\nProducts ordered within day '" + day + "' with the number of times ordered\n\n");

            for(MenuItem crt: items) {
                fileWriter.write(crt.toString() + "\n     x " + map.get(crt) + " times ordered\n\n\n");
            }
            fileWriter.write("TOTAL OF PRODUCTS THAT MATCH THE CRITERIA: " + items.size());
            fileWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Error: can't generate report type 4 output file!\n");
        }
    }
}
