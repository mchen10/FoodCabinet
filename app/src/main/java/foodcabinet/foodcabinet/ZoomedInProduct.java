package foodcabinet.foodcabinet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Mems on 5/16/2016.
 */
public class ZoomedInProduct extends AppCompatActivity{
    /**
     * Called method when a new Instance of a Zoom In Product is to be created and displayed in the application
     * @param savedInstanceState The Bundle of which should be created in the application
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoomed_in_product);

        Product p = (Product) getIntent().getSerializableExtra("Product");
        TextView name = (TextView) findViewById(R.id.NameZoomed);
        ImageView image = (ImageView) findViewById(R.id.ImageZoomed);
        TextView usedDate = (TextView) findViewById(R.id.UsedDateZoomed);
        TextView expirDate = (TextView) findViewById(R.id.ExpirationDateZoomed);

        name.setText(p.getName());


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    /**
     * Sends and Intent back to the Home Page and displays it on the application
     *
     * @param v View object that should be displayed in the application
     */
    public void returnHome(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
