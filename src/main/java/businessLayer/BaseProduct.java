package businessLayer;

import java.util.Objects;

public class BaseProduct extends MenuItem {

    public BaseProduct() {

    }

    public BaseProduct(String title, double rating, int calories, int protein, int fat, int sodium, double price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    @Override
    public double computeRating() {
        return this.rating;
    }

    @Override
    public int computeCalories() {
        return this.calories;
    }

    @Override
    public int computeProteins() {
        return this.protein;
    }

    @Override
    public int computeFats() {
        return this.fat;
    }

    @Override
    public int computeSodium() {
        return this.sodium;
    }

    @Override
    public double computePrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseProduct that = (BaseProduct) o;
        return Double.compare(that.rating, rating) == 0 && calories == that.calories && protein == that.protein && fat == that.fat && sodium == that.sodium && Double.compare(that.price, price) == 0 && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, rating, calories, protein, fat, sodium, price);
    }

    @Override
    public String toString() {
        return title + '\'' +
                ", rating=" + rating +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", price=" + price;
    }
}
