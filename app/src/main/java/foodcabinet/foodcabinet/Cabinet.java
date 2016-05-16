package foodcabinet.foodcabinet;

import java.util.ArrayList;

/**
 * Created by Jeff on 5/16/2016.
 */
public class Cabinet {
    private ArrayList<Product> products;

    public Cabinet(){
        products = new ArrayList<Product>();
    }

    public Cabinet(ArrayList<Product> products){
        this.products = products;
    }

    public Product getProduct(int i){
        return products.get(i);
    }

    public void addProduct(Product p){
        products.add(p);
    }

    public void removeProduct(int i){
        products.remove(i);
    }

}
