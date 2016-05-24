package foodcabinet.foodcabinet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.media.Image;

/**
 * Created by Sahaj on 5/14/16.
 */
public class Product implements Serializable{
    private String name, group;
    private int eDays, uDays;
    private ArrayList<Integer> pastEDays, pastUDays;
    private Date bDate;
    private Image pic;
    private int numTimesEntered = 0;
    
    /** Constructs a product with a given type, expiration date, and days of use
     * 
     * @param name type of product
     * @param expirationDays days until the product expires
     * @param usedDays days until the product is used up
     */
    public Product(String name, String group, int expirationDays, int usedDays) {
        this.name = name;
        this.group = group;
        eDays = expirationDays;
        uDays = usedDays;
        bDate = new Date();
        pastEDays = new ArrayList<Integer>();
        pastUDays = new ArrayList<Integer>();
    }
    
    /** Constructs a product with a given type
     * 
     * @param name type of product
     */
    public Product(String name, String group){
    	this.name = name;
        this.group = group;
    	bDate = new Date();
        pastEDays = new ArrayList<Integer>();
        pastUDays = new ArrayList<Integer>();
    }

    public void addUsedEDay(Integer date) {
        pastEDays.add(date);
    }

    public void addUsedUDay(Integer date) {
        pastUDays.add(date);
    }

    public ArrayList<Integer> getUsedEDays() {
        return pastEDays;
    }

    public ArrayList<Integer> getUsedUDays() {
        return pastUDays;
    }

    public String getGroup() {
        return group;
    }

    /**
     * Sets the amount of days the product will be finished in
     * @param days integer days betewwen the time the prodcut will be finished
     */
    public void setUDays(int days){
    	uDays = days;
    }

    /**Sets the amount of days that the product will expire in
     *
     * @param days integer days between the time the product will expire
     */
    public void setEDays(int days){
    	eDays = days;
    }

    /**
     * Method to retrieve the name of the product
     * @return Name of the Product
     */
    public String getName() {
        return name;
    }

    /**
     * Method to retrieve the days between expiry
     * @return days before expiry
     */
    public int getEDays() {
        return eDays;
    }

    /**
     * Method to retrieve the days before finishing the prodcut
     * @return days before the product will be finished
     */
    public int getUDays() {
        return uDays;
    }

    /**
     * Retirives the date that the product was purchased
     * @return date that the product is purchased
     */
    public Date getBDate(){
    	return bDate;
    }

    /**
     * Returns the image that this product is defined by
     * @return Image that represents the type of product
     */
    public Image getPic() {
        return pic;
    }

    public void updateBDate() {
        bDate = new Date();
    }

    public void increaseEntered() {
        numTimesEntered++;
    }

    public int getNumTimesEntered() {
        return numTimesEntered;
    }

    public String toString() {
        return name + " " + eDays + " " + uDays;
    }
}
