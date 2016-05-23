package foodcabinet.foodcabinet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AddProductsScreen extends AppCompatActivity {
    private Product p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_screen);


    }
    public AddProductsScreen()
    {
        p = new Product("","");
    }

    public void AddProductName(String s)
    {

    }

}
