package promocje;

import koszyk.Product;
//import koszyk.Promocja;

import java.util.List;

public class PromocjaDarmowyKubek implements Promocja {
    @Override
    public void apply(List<Product> produkty) {
        boolean alreadyHasKubek = false;

        for (Product p : produkty) {
            if (p.name.equalsIgnoreCase("Kubek")) {
                alreadyHasKubek = true;
                break;
            }
        }

        if (!alreadyHasKubek) {
            Product kubek = new Product("P007", "Kubek", 20.0, 0.0);
            produkty.add(kubek);
            System.out.println("Promocja: darmowy kubek został dodany do koszyka.");
        } else {
            System.out.println("Kubek już znajduje się w koszyku – promocja nie została ponownie zastosowana.");
        }
    }

    @Override
    public String getName() {
        return "Promocja: darmowy kubek";
    }
}
