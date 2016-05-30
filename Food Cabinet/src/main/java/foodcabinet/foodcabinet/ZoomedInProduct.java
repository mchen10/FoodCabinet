package foodcabinet.foodcabinet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Michael on 5/16/2016.
 */
public class ZoomedInProduct extends AppCompatActivity{
    private Cabinet c;
    private Product p;
    private ArrayList<ArrayList<String>> database;
    /**
     * Called method when a new Instance of a Zoom In Product is to be created and displayed in the application
     * @param savedInstanceState The Bundle of which should be created in the application
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoomed_in_product);

        p = (Product) getIntent().getSerializableExtra("Product");
        c = (Cabinet) getIntent().getSerializableExtra("Cabinet");
        database = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("Database");

        float scaleWidth =getResources().getDisplayMetrics().widthPixels;
        float scaleHeight = getResources().getDisplayMetrics().heightPixels;

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/MODERNA_.TTF");

        LinearLayout.LayoutParams sideEmpty = new LinearLayout.LayoutParams((int)(0.075*scaleWidth+0.5f), LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams topEmpty = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(0.06*scaleHeight+0.5f));
        LinearLayout.LayoutParams pLayout = new LinearLayout.LayoutParams((int)(0.85*scaleWidth+0.5f), (int)(0.85*scaleHeight+0.5f));

        LinearLayout up = (LinearLayout) findViewById(R.id.UpEmptyZoomed), down = (LinearLayout) findViewById(R.id.DownEmptyZoomed);
        LinearLayout left = (LinearLayout) findViewById(R.id.LeftEmptyZoomed), right = (LinearLayout) findViewById(R.id.RightEmptyZoomed);
        LinearLayout pU = (LinearLayout) findViewById(R.id.ProductZoomed);

        up.setLayoutParams(topEmpty);
        down.setLayoutParams(topEmpty);
        right.setLayoutParams(sideEmpty);
        left.setLayoutParams(sideEmpty);
        pU.setLayoutParams(pLayout);

        LinearLayout.LayoutParams picL = new LinearLayout.LayoutParams((int)(0.5 * scaleWidth + 0.5f), (int)(0.3 * scaleHeight + 0.5f));
        picL.setMargins((int)(0.175 * scaleWidth + 0.5f), 0, 0, 0);

        LinearLayout.LayoutParams textL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textL.setMargins((int)(0.22 * scaleWidth + 0.5f), (int)(0.01 * scaleWidth + 0.5f),0, (int)(0.05 * scaleHeight + 0.5f));

        TextView name = (TextView) findViewById(R.id.NameZoomed);
        ImageView image = (ImageView) findViewById(R.id.ImageZoomed);
        image.setLayoutParams(picL);
        TextView usedDate = (TextView) findViewById(R.id.UsedDateZoomed);
        TextView expirDate = (TextView) findViewById(R.id.ExpirationDateZoomed);
        usedDate.setLayoutParams(textL);
        expirDate.setLayoutParams(textL);

        name.setText(p.getName());
        name.setTypeface(font);

        image.setImageResource(getResources().getIdentifier(p.getName().toLowerCase(), "drawable", getPackageName()));

        Date date = p.getBDate();

        int uDate = p.getUDays(), eDate = p.getEDays();
        GregorianCalendar calendar = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
        calendar.add(Calendar.DATE, uDate);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        usedDate.setText(format.format(calendar.getTime()));
        usedDate.setTypeface(font);

        calendar.add(Calendar.DATE, -uDate);
        calendar.add(Calendar.DATE, eDate);

        expirDate.setText(format.format(calendar.getTime()));
        expirDate.setTypeface(font);

        TextView uLabel = (TextView) findViewById(R.id.UsedDateLabel);
        TextView eLabel = (TextView) findViewById(R.id.ExpirationDateLabel);
        uLabel.setTypeface(font);
        eLabel.setTypeface(font);
    }

    /**
     * Sends and Intent back to the Home Page and displays it on the application
     *
     * @param v View object that should be displayed in the application
     */
    public void returnHome(View v) {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("Cabinet", c);
        intent.putExtra("Database", database);

        startActivity(intent);
    }

    /**
     * Deletes the selected product
     * @param v View object
     */
    public void deleteObject(View v) {
        c.removeProduct(p);

        returnHome(v);
    }
}
