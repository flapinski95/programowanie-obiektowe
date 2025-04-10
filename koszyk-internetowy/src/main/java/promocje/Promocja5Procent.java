package promocje;

import koszyk.Koszyk;
import koszyk.Product;

import java.util.List;

public class Promocja5Procent implements Promocja{
    @Override
    public void apply(List<Product> produkty){
        if (Koszyk.calculateTotalPrice(produkty) > 300) {
            for (Product p : produkty) {
                double newPrice = p.price * 0.95;
                p.setDiscountPrice(newPrice);
            }
        } else {
            System.out.println("Koszyk nie kwalifikuje się do rabatu 5%");
        }
    }
    @Override
    public String getName(){
        return "Promocja 5%";
    }
}
