package foodcabinet.foodcabinet;

import android.media.Image;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Sahaj on 5/14/16.
 */
public class Product {
    private String name, eDate, uDate;
    private Date bDate;
    private Image pic;
    public Product(String name, String expirationDate, String usedDate) {
        this.name = name;
        eDate = expirationDate;
        uDate = usedDate;
        bDate = new Date();
    }
    public String getName() {
        return name;
    }
    public String getEDate() {
        return eDate;
    }
    public String getUDate() {
        return uDate;
    }
    public Date getBDate(){
        return bDate;
    }
    public Image getPic() {
        return pic;
    }
}
