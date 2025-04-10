package koszyk;

public class Product {
    String code;
    public String name;
    public double price;
    Double discountPrice ;

    public Product(String code, String name, double price, Double discountPrice) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    @Override
    public String toString() {
        return name + " (" + code + ") - Cena: " + price + " zł, Cena po rabacie: " + discountPrice + " zł";
    }

    public void setDiscountPrice(double newPrice) {
        this.discountPrice = newPrice;
    }
}
