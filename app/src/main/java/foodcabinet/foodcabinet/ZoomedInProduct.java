package foodcabinet.foodcabinet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Michael on 5/16/2016.
 */
public class ZoomedInProduct extends AppCompatActivity{
    private Cabinet c;
    private Product p;
    /**
     * Called method when a new Instance of a Zoom In Product is to be created and displayed in the application
     * @param savedInstanceState The Bundle of which should be created in the application
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoomed_in_product);

        p = (Product) getIntent().getSerializableExtra("Product");
        c = (Cabinet) getIntent().getSerializableExtra("Cabinet");

        TextView name = (TextView) findViewById(R.id.NameZoomed);
        ImageView image = (ImageView) findViewById(R.id.ImageZoomed);
        TextView usedDate = (TextView) findViewById(R.id.UsedDateZoomed);
        TextView expirDate = (TextView) findViewById(R.id.ExpirationDateZoomed);

        name.setText(p.getName());

        image.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));

        Date date = p.getBDate();

        int uDate = p.getUDays(), eDate = p.getEDays();
        GregorianCalendar calendar = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDay());
        calendar.add(Calendar.DATE, uDate);
        calendar.add(Calendar.MONTH, uDate);
        calendar.add(Calendar.YEAR, uDate);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        usedDate.setText(format.format(calendar.getTime()));

        calendar.add(Calendar.DATE, eDate);
        calendar.add(Calendar.MONTH, eDate);
        calendar.add(Calendar.YEAR, eDate);

        expirDate.setText(format.format(calendar.getTime()));
    }

    /**
     * Sends and Intent back to the Home Page and displays it on the application
     *
     * @param v View object that should be displayed in the application
     */
    public void returnHome(View v) {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("Cabinet", c);

        startActivity(intent);
    }

    public void deleteObject(View v) {
        c.removeProduct(p);

        returnHome(v);
    }
}
