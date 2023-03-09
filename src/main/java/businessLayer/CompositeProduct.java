package businessLayer;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {
    private ArrayList<MenuItem> listOfMenuItems;

    public CompositeProduct(String title, ArrayList<MenuItem> listOfMenuItems) {
        this.title = title;
        this.listOfMenuItems = listOfMenuItems;
    }

    @Override
    public double computeRating() {
        double totalRating = 0;
        for(MenuItem i: listOfMenuItems) {
            totalRating += i.getRating();
        }

        this.setRating(totalRating/listOfMenuItems.size());
        return totalRating/listOfMenuItems.size();
    }

    @Override
    public int computeCalories() {
        int totalCalories = 0;
        for(MenuItem i: listOfMenuItems) {
            totalCalories += i.getCalories();
        }

        this.setCalories(totalCalories);
        return totalCalories;
    }

    @Override
    public int computeProteins() {
        int totalProteins = 0;
        for(MenuItem i: listOfMenuItems) {
            totalProteins += i.getProtein();
        }

        this.setProtein(totalProteins);
        return totalProteins;
    }

    @Override
    public int computeFats() {
        int totalFats = 0;
        for(MenuItem i: listOfMenuItems) {
            totalFats += i.getFat();
        }

        this.setFat(totalFats);
        return totalFats;
    }

    @Override
    public int computeSodium() {
        int totalSodium = 0;
        for(MenuItem i: listOfMenuItems) {
            totalSodium += i.getSodium();
        }

        this.setSodium(totalSodium);
        return totalSodium;
    }

    @Override
    public double computePrice() {
        double totalPrice = 0;
        for(MenuItem i: listOfMenuItems) {
            totalPrice += i.getPrice();
        }

        this.setPrice(totalPrice);
        return totalPrice;
    }
}
