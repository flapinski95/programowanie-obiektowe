package promocje;

import koszyk.Koszyk;
import koszyk.Product;

import java.util.List;

public class PromocjaTrzyZaDwa implements Promocja {
    @Override
    public void apply(List<Product> produkty) {
        if (!isApplicable(produkty)) return;

        Product cheapest = Koszyk.findCheapestProduct(produkty.stream()
                .filter(p -> p.price < 100)
                .toList());

        if (cheapest != null) {
            cheapest.setDiscountPrice(0.0);
            System.out.println("Zastosowano promocję 3 za 2");
        }
    }

    @Override
    public boolean isApplicable(List<Product> produkty) {
        long count = produkty.stream().filter(p -> p.price < 100).count();
        return count >= 3;
    }

    @Override
    public String getName() {
        return "Promocja 3 za 2";
    }
}