package promocje;

import koszyk.Koszyk;
import koszyk.Product;

import java.util.List;

public class Promocja5Procent implements Promocja {
    @Override
    public void apply(List<Product> produkty){
        for (Product p : produkty) {
            if (p.discountPrice == null) {
                p.setDiscountPrice(p.price * 0.95);
            }
        }
        System.out.println("Zastosowano promocję 5%");
    }

    @Override
    public boolean isApplicable(List<Product> produkty) {
        return Koszyk.calculateTotalPrice(produkty) > 300;
    }

    @Override
    public String getName(){
        return "Promocja 5%";
    }
}