package foodcabinet.Foodcabinet;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jeff on 5/16/2016.
 */
public class Cabinet implements Serializable {
    private ArrayList<Product> products;
    private ArrayList<Product> doneProducts;

    /** Construct a cabinet with an empty list of products and an empty
     * list of products not in use
     */
    public Cabinet(){
        products = new ArrayList<Product>();
        doneProducts = new ArrayList<Product>();
    }

    /** Construct a cabinet with a list of products and an empty
     * list of products not in use
     *
     * @param products Array of existing products
     */
    public Cabinet(ArrayList<Product> products){
        this.products = products;
        doneProducts = new ArrayList<Product>();
    }

    /** Gives the product at a specified index
     *
     * @param i index of product
     * @return product at specified index
     */
    public Product getProduct(int i){
        return products.get(i);
    }

    /** Adds a product to the cabinet
     *
     * @param p product to be added
     */
    public void addProduct(Product p){
        products.add(p);
    }

    /** Removes a specified product and moves it to a list of
     * products that are no longer in use
     *
     * @param i index of item to be removed
     */
    public void removeProduct(int i){
        doneProducts.add(products.remove(i));
    }

    public void removeProduct(Product p) {
        for (int i = 0; i < products.size(); i++) {
            if (p.getName().equals(products.get(i).getName())) {
                products.remove(i);
                break;
            }
        }
    }

    /**
     *  Returns the list of products that are no longer used
     * @return array of products that are no longer used
     */
    public ArrayList<Product> getDoneProducts(){
        return doneProducts;
    }

    /**
     * Returns the list of current products
     * @return the list of current products
     */
    public ArrayList<Product> getCurrentProducts() { return products; }

}
