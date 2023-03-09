package businessLayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IDeliveryServiceProcessing {

    void importFromCsv();
    void addMenuItem(MenuItem newItem);
    void removeMenuItem(MenuItem item);
    void editMenuItem(MenuItem item, MenuItem updatedItem);
    void generateReport1(String startHour, String endHour);
    void generateReport2(int timesOrdered);
    void generateReport3(int timesOrdered, int valueOrdered);
    void generateReport4(int day);

    void addOrder(Order o, ArrayList<MenuItem> listOfItems);
    List<MenuItem> findByCriteria(String title, double rating, int calories, int protein, int fat, int sodium, double price);

    Collection<Collection<MenuItem>> getListOfOrders();
}
