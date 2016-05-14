package foodcabinet.foodcabinet;

/**
 * Created by Sahaj on 5/14/16.
 */
public class Product {
    private String name, eDate, uDate;
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
}
