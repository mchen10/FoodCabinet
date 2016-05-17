package foodcabinet.foodcabinet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mems on 5/16/2016.
 */
public class ZoomedInProduct extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoomed_in_product);

        Product p = (Product) getIntent().getSerializableExtra("Product");
    }
}
