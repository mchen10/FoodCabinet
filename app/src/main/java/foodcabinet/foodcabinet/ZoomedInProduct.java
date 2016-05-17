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

    public void returnHome(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
