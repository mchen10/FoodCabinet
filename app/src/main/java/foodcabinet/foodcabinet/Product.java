package foodcabinet.foodcabinet;

import android.media.Image;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by Sahaj on 5/14/16.
 */
public class Product implements Serializable{
    private Date  startDate,eDate, uDate;
    private String name;

    /**
     * Creates a new Instance of a product
     * @param name String of the type of product
     * @param purchaseDate Date that the product was bought
     * @param expirationDate Date that the product is estimated to expire
     * @param usedDate Date that the product is estimated to be finished
     */
    public Product(String name, Date purchaseDate,Date expirationDate, Date usedDate) {
        this.name = name;
        startDate=purchaseDate;
        eDate = expirationDate;
        uDate = usedDate;
    }
    public String getName() {
        return name;
    }

    /**
     * Returns the predicted date of expiration
     * @return Date of expiration
     */
    public Date getEDate() {
        return eDate;
    }

    /**
     * Returns the Predicted date of finishing
     * @return Date of expiration
     */
    public Date getUDate() {
        return uDate;
    }


    /**
     * Changes the Expiration Date to a different Date
     * @param newDate Date to change the expiration date to
     */
    public void modifyDate(Date newDate)
    {
        eDate=newDate;

    }

}
