package koszyk;

public class Product {
    String code;
    public String name;
    public double price;
    public Double discountPrice ;
    public Product(Product other) {
        this.code = other.code;
        this.name = other.name;
        this.price = other.price;
        this.discountPrice = other.discountPrice;
    }
    public Product(String code, String name, double price, Double discountPrice) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    @Override
    public String toString() {
        if (discountPrice == null) {
            return name + " (" + code + ") - Cena: " + price + " zł";
        }
        return name + " (" + code + ") - Cena: " + price + " zł, Cena po rabacie: " + discountPrice + " zł";
    }

    public void setDiscountPrice(double newPrice) {
        this.discountPrice = newPrice;
    }
}
