package promocje;

import koszyk.Koszyk;
import koszyk.Product;

import java.util.List;

public class PromocjaDarmowyKubek implements Promocja {
    @Override
    public void apply(List<Product> produkty) {
        if (!isApplicable(produkty)) return;

        Product kubek = new Product("P007", "Kubek", 20.0, 0.0);
        produkty.add(kubek);
        System.out.println("Promocja: darmowy kubek został dodany do koszyka.");
    }

    @Override
    public boolean isApplicable(List<Product> produkty) {
        if (Koszyk.calculateTotalPrice(produkty) < 200.0) return false;

        for (Product p : produkty) {
            if (p.name.equalsIgnoreCase("Kubek")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Promocja: darmowy kubek";
    }
}