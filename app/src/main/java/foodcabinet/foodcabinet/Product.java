package foodcabinet.foodcabinet;

import android.media.Image;

/**
 * Created by Sahaj on 5/14/16.
 */
public class Product {
    private String name, eDate, uDate;
    private Image pic;
    public Product(String name, String expirationDate, String usedDate) {
        this.name = name;
        eDate = expirationDate;
        uDate = usedDate;
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
    public Image getPic() {
        return pic;
    }
}
