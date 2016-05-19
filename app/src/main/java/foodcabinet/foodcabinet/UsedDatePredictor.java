package foodcabinet.foodcabinet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jeff on 5/14/2016.
 */
public class UsedDatePredictor {
    private Product item;

    /** Constructs a UsedDatePredictor for a specified product
     *
     * @param product a given product
     */
    public UsedDatePredictor(Product product){
        item = product;
    }

    /** Constructs a UsedDatePredictor to be used
     *
     */
    public UsedDatePredictor(){

    }

    /** Predicts the date of when the product is used
     *
     * @param product product who's use date is to be predicted
     * @return Calendar date of when the product is to be finished
     */
    public static Calendar predict(Product product){
		ArrayList<Integer> previousUseDays = product.getUsedUDays();
		int[] temp = new int[previousUseDays.size()];
		Arrays.sort(temp);
		previousUseDays = new ArrayList<Integer>();
		for (int i = 0; i < temp.length; i++){
			previousUseDays.add(temp[i]);
		}
    	double useDate = 0;
    	/*products.sort(new Comparator<Product>() {
			@Override
			public int compare(Product arg0, Product arg1) {
				// TODO Auto-generated method stub
				return Integer.compare(arg0.getEDays(), arg1.getEDays());
			}

    	});*/
    	double size = previousUseDays.size();
    	double weight = 0;

    	for (int i = 0; i < previousUseDays.size(); i++){
    		if (previousUseDays.size() <= 5){
    			useDate += previousUseDays.get(i);
    		} else {
    			if (size/(Math.abs(i-size/2)) != 0){
    				useDate += previousUseDays.get(i) * size/(Math.abs(i-size/2));
    				weight += size/(Math.abs(i-size/2));
    			} else {
    				useDate += previousUseDays.get(i) * size;
    				weight += size;
    			}
    		}
    	}
    	if (previousUseDays.size() <= 5){
    		useDate = useDate/previousUseDays.size();
		} else {
			useDate = useDate/weight;
		}
    	
        Date buyDate = product.getBDate();
        Calendar cal = new GregorianCalendar();
        //cal.setTime(buyDate);
        cal.add(Calendar.DAY_OF_YEAR, (int)(useDate + 0.5));
        return cal;
    }

}