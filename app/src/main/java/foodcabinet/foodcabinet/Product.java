package foodcabinet.foodcabinet;

import java.io.Serializable;
import java.util.Date;

import android.media.Image;

/**
 * Created by Sahaj on 5/14/16.
 */
public class Product implements Serializable{
    private String name;
    private int eDays, uDays;
    private Date bDate;
    private Image pic;
    
    /** Constructs a product with a given type, expiration date, and days of use
     * 
     * @param name type of product
     * @param expirationDays days until the product expires
     * @param usedDays days until the product is used up
     */
    public Product(String name, int expirationDays, int usedDays) {
        this.name = name;
        eDays = expirationDays;
        uDays = usedDays;
        bDate = new Date();
        
    }
    
    /** Constructs a product with a given type
     * 
     * @param name type of product
     */
    public Product(String name){
    	this.name = name;
    	bDate = new Date();
    }
    
    public void setUDays(int days){
    	uDays = days;
    }
    public void setEDays(int days){
    	eDays = days;
    }
    public String getName() {
        return name;
    }
    public int getEDays() {
        return eDays;
    }
    public int getUDays() {
        return uDays;
    }
    public Date getBDate(){
    	return bDate;
    }
    public Image getPic() {
        return pic;
    }
}
