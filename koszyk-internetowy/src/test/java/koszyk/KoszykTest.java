package koszyk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import promocje.*;

import java.util.List;

class KoszykTest {

    private Koszyk koszyk;

    @BeforeEach
    void setUp() {
        koszyk = new Koszyk();
        koszyk.getProdukty().clear();
    }

    @Test
    void testDodawanieProduktow() {
        koszyk.dodajProdukt(new Product("P001", "Mleko", 4.50, null));
        koszyk.dodajProdukt(new Product("P002", "Chleb", 3.00, null));

        List<Product> produkty = koszyk.getProdukty();
        Assertions.assertEquals(2, produkty.size());
        Assertions.assertEquals("Mleko", produkty.get(0).name);
    }

    @Test
    void testPromocjaDarmowyKubek() {
        koszyk.dodajProdukt(new Product("P003", "Masło", 6.20, null));
        koszyk.dodajPromocje(new PromocjaDarmowyKubek());
        koszyk.zastosujPromocje(koszyk.getProdukty());

        boolean zawieraKubek = koszyk.getProdukty().stream()
                .anyMatch(p -> p.name.equalsIgnoreCase("Kubek"));

        Assertions.assertTrue(zawieraKubek);
    }

    @Test
    void testPromocja5ProcentDziala() {
        koszyk.dodajProdukt(new Product("P010", "Telewizor", 3200.00, null));
        koszyk.dodajPromocje(new Promocja5Procent());
        koszyk.zastosujPromocje(koszyk.getProdukty());

        Product produkt = koszyk.getProdukty().get(0);
        Assertions.assertNotNull(produkt.discountPrice);
        Assertions.assertEquals(3200.00 * 0.95, produkt.discountPrice, 0.01);
    }

    @Test
    void testPromocja5ProcentNieDzialaPonizej300zl() {
        koszyk.dodajProdukt(new Product("P001", "Długopis", 3.00, null));
        koszyk.dodajProdukt(new Product("P002", "Zeszyt", 4.00, null));

        koszyk.dodajPromocje(new Promocja5Procent());
        koszyk.zastosujPromocje(koszyk.getProdukty());

        for (Product p : koszyk.getProdukty()) {
            Assertions.assertNull(p.discountPrice);
        }
    }

    @Test
    void testPromocjaTrzyZaDwaDziala() {
        koszyk.dodajProdukt(new Product("P001", "Książka", 50.00, null));
        koszyk.dodajProdukt(new Product("P002", "Notes", 20.00, null));
        koszyk.dodajProdukt(new Product("P003", "Długopis", 5.00, null));

        koszyk.dodajPromocje(new PromocjaTrzyZaDwa());
        koszyk.zastosujPromocje(koszyk.getProdukty());

        long darmowe = koszyk.getProdukty().stream()
                .filter(p -> p.discountPrice != null && p.discountPrice == 0.0)
                .count();

        Assertions.assertEquals(1, darmowe);
    }

    @Test
    void testPromocjaTrzyZaDwaNieDzialaDlaDrogichProduktow() {
        koszyk.dodajProdukt(new Product("P004", "Laptop", 1500.00, null));
        koszyk.dodajProdukt(new Product("P005", "Monitor", 800.00, null));
        koszyk.dodajProdukt(new Product("P006", "Telefon", 2000.00, null));

        koszyk.dodajPromocje(new PromocjaTrzyZaDwa());
        koszyk.zastosujPromocje(koszyk.getProdukty());

        for (Product p : koszyk.getProdukty()) {
            Assertions.assertNull(p.discountPrice);
        }
    }

    @Test
    void testZastosujNajlepszaKolejnoscPromocji() {
        // Dodajemy produkty pasujące do różnych promocji
        koszyk.dodajProdukt(new Product("P001", "Słuchawki", 150.00, null));
        koszyk.dodajProdukt(new Product("P002", "Etui", 70.00, null));
        koszyk.dodajProdukt(new Product("P003", "Ładowarka", 90.00, null));
        koszyk.dodajProdukt(new Product("P004", "Telefon", 2000.00, null));

        // Dodajemy promocje
        koszyk.dodajPromocje(new Promocja5Procent());
        koszyk.dodajPromocje(new PromocjaDarmowyKubek());
        koszyk.dodajPromocje(new PromocjaTrzyZaDwa());

        koszyk.zastosujNajlepszaKolejnoscPromocji();

        double suma = Koszyk.calculateTotalPrice(koszyk.getProdukty());
        Assertions.assertTrue(suma > 0);
        Assertions.assertTrue(suma < 2310.00); // poniżej oryginalnej sumy
        Assertions.assertEquals(4 + 1, koszyk.getProdukty().size()); // dodano kubek
    }
}