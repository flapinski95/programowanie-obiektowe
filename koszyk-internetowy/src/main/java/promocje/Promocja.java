package promocje;

import koszyk.Product;

import java.util.List;

public interface Promocja {
    void apply(List<Product> produkty);
    boolean isApplicable(List<Product> produkty);
    String getName();
}
