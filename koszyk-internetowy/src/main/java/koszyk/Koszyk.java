package koszyk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import promocje.Promocja;
import promocje.PromocjaDarmowyKubek;
import promocje.Promocja5Procent;
import promocje.PromocjaTrzyZaDwa;

public class Koszyk {
    private List<Promocja> promocje;
    private static List<Product> dodaneProdukty;
    private List<Promocja> najlepszaKolejnosc; // <-- dodane pole

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

    public void zastosujNajlepszaKolejnoscPromocji() {
        List<Product> najlepszeProdukty = null;
        double najnizszaCena = Double.MAX_VALUE;
        najlepszaKolejnosc = null;

        List<List<Promocja>> permutacje = permutacjePromocji(promocje);

        for (List<Promocja> permutacja : permutacje) {
            List<Product> kopia = new ArrayList<>();
            for (Product p : dodaneProdukty) {
                kopia.add(new Product(p)); // konstruktor kopiujący
            }

            for (Promocja p : permutacja) {
                p.apply(kopia);
            }

            double cena = calculateTotalPrice(kopia);
            if (cena < najnizszaCena) {
                najnizszaCena = cena;
                najlepszeProdukty = kopia;
                najlepszaKolejnosc = new ArrayList<>(permutacja);
            }
        }

        dodaneProdukty = najlepszeProdukty;
        promocje = najlepszaKolejnosc;
    }

    private List<List<Promocja>> permutacjePromocji(List<Promocja> lista) {
        List<List<Promocja>> wynik = new ArrayList<>();
        permutacjeRekurencyjnie(lista, 0, wynik);
        return wynik;
    }

    private void permutacjeRekurencyjnie(List<Promocja> lista, int index, List<List<Promocja>> wynik) {
        if (index == lista.size() - 1) {
            wynik.add(new ArrayList<>(lista));
            return;
        }

        for (int i = index; i < lista.size(); i++) {
            Collections.swap(lista, i, index);
            permutacjeRekurencyjnie(lista, index + 1, wynik);
            Collections.swap(lista, i, index);
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
            total += p.discountPrice != null ? p.discountPrice : p.price;
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

    public List<Promocja> getNajlepszaKolejnosc() {
        return najlepszaKolejnosc;
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
        koszyk.dodajPromocje(new Promocja5Procent());
        koszyk.dodajPromocje(new PromocjaTrzyZaDwa());

        koszyk.zastosujNajlepszaKolejnoscPromocji();

        System.out.println("\nPo promocji:");
        for (Product p : koszyk.getProdukty()) {
            System.out.println(p);
        }

        System.out.println("\nCena końcowa: " + Koszyk.calculateTotalPrice(koszyk.getProdukty()) + " zł");

        System.out.println("\nNajlepsza kolejność promocji:");
        for (Promocja p : koszyk.getNajlepszaKolejnosc()) {
            System.out.println("- " + p.getClass().getSimpleName());
        }
    }
}