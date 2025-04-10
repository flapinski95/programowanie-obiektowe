package koszyk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import promocje.Promocja;
import promocje.PromocjaDarmowyKubek;

public class Koszyk {
    private List<Promocja> promocje;
    private static List<Product> dodaneProdukty;

    public Koszyk() {
        this.promocje = new ArrayList<>();
        this.dodaneProdukty = new ArrayList<>();
    }

    public static void dodajProdukt(Product produkt) {
        dodaneProdukty.add(produkt);
    }

    public List<Product> getProdukty() {
        return dodaneProdukty;
    }

    public void dodajPromocje(Promocja promocja) {
        promocje.add(promocja);
    }

    public void zastosujPromocje(List<Product> produkty) {
        for (Promocja p : promocje) {
            p.apply(produkty);
        }
    }

    public static Product findCheapestProduct(List<Product> produkty) {
        Product cheapest = produkty.get(0);
        for (int i = 1; i < produkty.size(); i++) {
            if (produkty.get(i).price < cheapest.price) {
                cheapest = produkty.get(i);
            }
        }
        return cheapest;
    }

    public Product findMostExpensiveProduct(List<Product> produkty) {
        Product mostExpensive = produkty.get(0);
        for (int i = 1; i < produkty.size(); i++) {
            if (produkty.get(i).price > mostExpensive.price) {
                mostExpensive = produkty.get(i);
            }
        }
        return mostExpensive;
    }

    public static double calculateTotalPrice(List<Product> produkty) {
        double total = 0;
        for (Product p : produkty) {
            total += p.price;
        }
        return total;
    }

    public List<Product> sortProductsByPriceAndName(List<Product> produkty) {
        List<Product> sortedProducts = new ArrayList<>(produkty);

        sortedProducts.sort(Comparator
                .comparingDouble((Product p) -> p.price)
                .thenComparing(p -> p.name)
        );

        return sortedProducts;
    }

    public static void main(String[] args) {
        Koszyk koszyk = new Koszyk();

        koszyk.dodajProdukt(new Product("P001", "Mleko", 4.50, null));
        koszyk.dodajProdukt(new Product("P002", "Chleb", 3.00, null));
        koszyk.dodajProdukt(new Product("P003", "Masło", 6.20, null));
        koszyk.dodajProdukt(new Product("P004", "Ekspres do kawy", 999.99, null));
        koszyk.dodajProdukt(new Product("P005", "Smartfon", 2699.00, null));
        koszyk.dodajProdukt(new Product("P006", "Telewizor 55\"", 3199.00, null));

        koszyk.dodajPromocje(new PromocjaDarmowyKubek());

        koszyk.zastosujPromocje(koszyk.getProdukty());

        System.out.println("\nPo promocji:");
        for (Product p : koszyk.getProdukty()) {
            System.out.println(p);
        }
    }
}
