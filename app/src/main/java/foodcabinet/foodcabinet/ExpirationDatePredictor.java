package foodcabinet.foodcabinet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jeff on 5/14/2016.
 */
public class ExpirationDatePredictor {
    private Product item;

    /** Constructs a ExpirationDatePredictor for a specified product
     *
     * @param product a given product
     */
    public ExpirationDatePredictor(Product product){
        item = product;
    }

    /** Constructs a ExpirationDatePredictor to be used
     *
     */
    public ExpirationDatePredictor(){

    }

    /** Predicts the date of when the product is expired
     *
     * @param product product who's expire date is to be predicted
     * @param products Array of products of the same type that are no longer in use
     * @return Calendar date of when the product is to be expired
     */
    public Calendar predict(Product product, ArrayList<Product> products){
        Date buyDate = product.getBDate();
        Calendar cal = new GregorianCalendar();
        cal.setTime(buyDate);
        cal.add(Calendar.DAY_OF_YEAR, 7);
        return cal;
    }
}
