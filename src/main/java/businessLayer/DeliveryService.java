package businessLayer;

import dataLayer.OutputFileGenerator;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing, Serializable {
    private HashSet<MenuItem> products;
    private HashMap<Order, Collection<MenuItem>> orders;
    private AccountStorage accounts;

    public DeliveryService() {
        products = new HashSet<>();
        orders = new HashMap<>();
        accounts = new AccountStorage();
    }

    // admin operations
    /**
     * Method imports from .csv file a collection of base products and adds them to the hashset of menu items
     */
    @Override
    public void importFromCsv() {
        try {
            FileReader fileReader = new FileReader("./products.csv");
            BufferedReader reader = new BufferedReader(fileReader);

            String[] productFields;
            String entry;
            reader.readLine(); // skip the first line (header)
            while((entry = reader.readLine()) != null) {
                productFields = entry.split(",");
                BaseProduct newItem = new BaseProduct(productFields[0], Double.parseDouble(productFields[1]), Integer.parseInt(productFields[2]), Integer.parseInt(productFields[3]), Integer.parseInt(productFields[4]), Integer.parseInt(productFields[5]), Double.parseDouble(productFields[6]));
                addMenuItem(newItem);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
            System.out.println("Error: cannot import from CSV!\n");
        } catch(NumberFormatException ex) {
            ex.printStackTrace();
            System.out.println("Error: invalid product(s) data!\n");
        }
    }

    /**
     * Method adds item to the hashset of menu items
     * @param newItem menu item that will be added
     */
    @Override
    public void addMenuItem(MenuItem newItem) {
        products.add(newItem);
    }

    /**
     * Method removes item from the hashset of menu items
     * @param item menu item that will be removed
     */
    @Override
    public void removeMenuItem(MenuItem item) {
        products.remove(item);
    }

    /**
     * Method removes a current item from hashset of orders then updates it with a new one
     * @param item item that has to be modified
     * @param updatedItem item that will be changed with
     */
    @Override
    public void editMenuItem(MenuItem item, MenuItem updatedItem) {
        products.remove(item);
        products.add(updatedItem);
    }

    /**
     * Method generates report type 1: Orders performed between a given start and end hour
     * @param startHour start hour of the orders
     * @param endHour end hour of the orders
     */
    @Override
    public void generateReport1(String startHour, String endHour) {
        List<Order> filteredOrders = orders.keySet().stream()
                        .filter(f -> Integer.parseInt(startHour) <= f.getOrderDate().getHour() && f.getOrderDate().getHour() <= Integer.parseInt(endHour)).collect(Collectors.toList());
        OutputFileGenerator.generateReport1(startHour, endHour, filteredOrders, orders);
    }

    /**
     * Method generates report type 2: Products ordered more than a specified number of times so far
     * @param timesOrdered times ordered of a product
     */
    @Override
    public void generateReport2(int timesOrdered) {
        List<MenuItem> filteredItems = products.stream()
                .filter(f -> orders.values().stream()
                        .flatMapToLong(f1 -> LongStream.of(f1.stream()
                                .filter(entry -> f.getTitle().equals(entry.getTitle()))
                                .count()))
                        .sum() > timesOrdered)
                .collect(Collectors.toList());
        OutputFileGenerator.generateReport2(timesOrdered, filteredItems);
    }

    /**
     * Method generates report type 3: Clients that have ordered more than a specified number of times so far and value of the order war higher that a specified amount
     * @param timesOrdered times ordered by a client
     * @param valueOrdered value ordered by a client
     */
    @Override
    public void generateReport3(int timesOrdered, int valueOrdered) {
        List<Account> filteredItems = accounts.getAccounts().stream()
                .filter(f -> orders.keySet().stream()
                        .filter(f1 -> f1.getClientId() == ((Client)f).getId())
                        .filter(f1 -> orders.get(f1).stream()
                                .flatMapToDouble(entry -> DoubleStream.of(entry.getPrice()))
                                .sum() >= valueOrdered)
                        .count() >= timesOrdered)
                .collect(Collectors.toList());
        OutputFileGenerator.generateReport3(timesOrdered, valueOrdered, filteredItems);
    }

    /**
     * Method generates report type 4: Products ordered within a specified day with the number of times they have been order
     * @param day product order day
     */
    @Override
    public void generateReport4(int day) {
        List<MenuItem> products = new ArrayList<>();
        orders.keySet().stream()
                .filter(f -> f.getOrderDate().getDayOfMonth() == day)
                .map(f -> orders.get(f))
                .forEach(products::addAll);

        Map<MenuItem, Long> map = products.stream().collect(
                Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));

        OutputFileGenerator.generateReport4(day, map, new HashSet<>(products));
    }

    // client operations

    /**
     * Method adds an order to the orders hashmap and generates a bill
     * @param order order that has to be placed
     * @param listOfItems items ordered
     */
    @Override
    public void addOrder(Order order, ArrayList<MenuItem> listOfItems) {
        orders.put(order, listOfItems);
        OutputFileGenerator.generateOrderBill(order, listOfItems);
        setChanged();
        notifyObservers(orders);
    }

    /**
     * Method returns a menu item by specified criteria
     * @param title of the menu item
     * @param rating of the menu item
     * @param calories of the menu item
     * @param protein of the menu item
     * @param fat of the menu item
     * @param sodium of the menu item
     * @param price of the menu item
     * @return returns a menu item
     */
    @Override
    public List<MenuItem> findByCriteria(String title, double rating, int calories, int protein, int fat, int sodium, double price) {
        List<MenuItem> menu = new ArrayList<>(products);

        return menu.stream()
                .filter(f -> f.getTitle().contains(title))
                .filter(f -> f.getRating() >= rating)
                .filter(f -> f.getProtein() >= protein)
                .filter(f -> f.getFat() >= fat)
                .filter(f -> f.getSodium() >= sodium)
                .filter(f -> f.getPrice() >= price)
                .collect(Collectors.toList());
    }

    // employee operations
    /**
     * Getter for list of orders
     * @return list of orders
     */
    @Override
    public Collection<Collection<MenuItem>> getListOfOrders() {
        return orders.values();
    }

    /**
     * Getter for orders
     * @return orders
     */
    public HashMap<Order, Collection<MenuItem>> getOrders() {
        return orders;
    }

    /**
     * Getter for accounts
     * @return accounts created so far
     */
    public AccountStorage getAccounts() {
        return accounts;
    }

    /**
     * Getter for products
     * @return products added so far
     */
    public HashSet<MenuItem> getProducts() {
        return products;
    }
}