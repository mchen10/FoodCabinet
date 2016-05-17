package foodcabinet.foodcabinet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
    	double expDate = 0;
    	/*products.sort(new Comparator<Product>() {
			@Override
			public int compare(Product arg0, Product arg1) {
				// TODO Auto-generated method stub
				return Integer.compare(arg0.getEDays(), arg1.getEDays());
			}

    	});*/
    	int size = products.size();
    	double weight = 0;
    	for (int i = 0; i < products.size(); i++){
    		if (products.size() <= 5){
    			expDate += products.get(i).getEDays();
    		} else {
    			if (size/(Math.abs(i-size/2)) != 0){
    				expDate += products.get(i).getEDays() * size/(Math.abs(i-size/2));
    				weight += size/(Math.abs(i-size/2));
    			} else {
    				expDate += products.get(i).getEDays() * size;
    				weight += size;
    			}
    		}
    	}
    	if (products.size() <= 5){
    		expDate = expDate/products.size();
		} else {
			expDate = expDate/weight;
		}
    	
        Date buyDate = product.getBDate();
        Calendar cal = new GregorianCalendar();
        cal.setTime(buyDate);
        cal.add(Calendar.DAY_OF_YEAR, (int)(expDate + 0.5));
        return cal;
    }
}