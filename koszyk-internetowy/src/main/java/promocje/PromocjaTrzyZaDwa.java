package promocje;

import koszyk.Koszyk;
import koszyk.Product;

import java.util.List;

public class PromocjaTrzyZaDwa implements Promocja{
    @Override
    public void apply(List<Product> produkty) {
        int count = 0;
        for (Product p : produkty) {
            if (p.price < 100) {
                count++;
            }
        }
        if (count >= 3) {
            Product cheapest = Koszyk.findCheapestProduct(produkty);
            cheapest.setDiscountPrice(0.0);
        } else {
            System.out.println("Koszyk nie kwalifikuje się do promocji 3 za 2");
        }
    }

    @Override
    public String getName() {
        return "Promocja 3 za 2";
    }
}
