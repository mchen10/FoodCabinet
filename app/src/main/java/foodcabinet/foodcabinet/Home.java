package foodcabinet.foodcabinet;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import java.lang.reflect.Array;

/**
 * Created by Sahaj on 5/12/16.
 */
public class Home extends AppCompatActivity{
    private ArrayList<Product> products;
    public void addProduct(Product p) {
        products.add(p);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle("Food Cabinet");

        LinearLayout homeMain = (LinearLayout) findViewById(R.id.HomeMain);
        products = new ArrayList<Product>();
        products.add(new Product("Bread", "August 25, 2015", "August 20, 2015"));
        products.add(new Product("Bread", "August 25, 2015", "August 20, 2015"));
        products.add(new Product("Bread", "August 25, 2015", "August 20, 2015"));
        products.add(new Product("Bread", "August 25, 2015", "August 20, 2015"));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < products.size(); i+=3) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            lp.setMargins(10, 5, 10, 20);
            layout.setLayoutParams(lp);
            if (i + 1 == products.size()) {
                LinearLayout b1 = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic1.setMaxWidth(50);
                pic1.setMaxHeight(50);
                lp.setMargins(0, 0, 0, 10);
                pic1.setLayoutParams(lp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(lp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setText(products.get(i).getUDate());
                text2.setLayoutParams(lp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setText(products.get(i).getEDate());
                text3.setLayoutParams(lp);
                b1.addView(text3);

                b1.setBackgroundColor(Color.BLUE);
                layout.addView(b1);
            } else if (i + 2 == products.size()) {
                LinearLayout b1 = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic1.setMaxWidth(50);
                pic1.setMaxHeight(50);
                lp.setMargins(0, 0, 0, 10);
                pic1.setLayoutParams(lp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(lp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setText(products.get(i).getUDate());
                text2.setLayoutParams(lp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setText(products.get(i).getEDate());
                text3.setLayoutParams(lp);
                b1.addView(text3);

                b1.setBackgroundColor(Color.BLUE);
                lp.setMargins(0, 0, 15, 0);
                b1.setLayoutParams(lp);
                layout.addView(b1);

                LinearLayout b2 = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                ImageView pic21 = new ImageView(this);
                pic21.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic21.setMaxWidth(50);
                pic21.setMaxHeight(50);
                lp.setMargins(0, 0, 0, 10);
                pic21.setLayoutParams(lp);
                b2.addView(pic21);

                TextView text21 = new TextView(this);
                text21.setText(products.get(i + 1).getName());
                text21.setLayoutParams(lp);
                b2.addView(text21);

                TextView text22 = new TextView(this);
                text22.setText(products.get(i + 1).getUDate());
                text22.setLayoutParams(lp);
                b2.addView(text22);

                TextView text23 = new TextView(this);
                text23.setText(products.get(i + 1).getEDate());
                text23.setLayoutParams(lp);
                b2.addView(text23);

                b2.setBackgroundColor(Color.BLUE);
                layout.addView(b2);
            } else {
                LinearLayout b1 = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic1.setMaxWidth(50);
                pic1.setMaxHeight(50);
                lp.setMargins(0, 0, 0, 10);
                pic1.setLayoutParams(lp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(lp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setText(products.get(i).getUDate());
                text2.setLayoutParams(lp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setText(products.get(i).getEDate());
                text3.setLayoutParams(lp);
                b1.addView(text3);

                b1.setBackgroundColor(Color.BLUE);
                lp.setMargins(0, 0, 15, 0);
                b1.setLayoutParams(lp);
                layout.addView(b1);

                LinearLayout b2 = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                ImageView pic21 = new ImageView(this);
                pic21.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic21.setMaxWidth(50);
                pic21.setMaxHeight(50);
                lp.setMargins(0, 0, 0, 10);
                pic21.setLayoutParams(lp);
                b2.addView(pic21);

                TextView text21 = new TextView(this);
                text21.setText(products.get(i + 1).getName());
                text21.setLayoutParams(lp);
                b2.addView(text21);

                TextView text22 = new TextView(this);
                text22.setText(products.get(i + 1).getUDate());
                text22.setLayoutParams(lp);
                b2.addView(text22);

                TextView text23 = new TextView(this);
                text23.setText(products.get(i + 1).getEDate());
                text23.setLayoutParams(lp);
                b2.addView(text23);

                b2.setBackgroundColor(Color.BLUE);
                lp.setMargins(0, 0, 15, 0);
                b2.setLayoutParams(lp);
                layout.addView(b2);

                LinearLayout b3 = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                ImageView pic31 = new ImageView(this);
                pic31.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic31.setMaxWidth(50);
                pic31.setMaxHeight(50);
                lp.setMargins(0, 0, 0, 10);
                pic31.setLayoutParams(lp);
                b3.addView(pic31);

                TextView text31 = new TextView(this);
                text31.setText(products.get(i + 1).getName());
                text31.setLayoutParams(lp);
                b3.addView(text31);

                TextView text32 = new TextView(this);
                text32.setText(products.get(i + 1).getUDate());
                text32.setLayoutParams(lp);
                b3.addView(text32);

                TextView text33 = new TextView(this);
                text33.setText(products.get(i + 1).getEDate());
                text33.setLayoutParams(lp);
                b3.addView(text33);

                b3.setBackgroundColor(Color.BLUE);
                layout.addView(b3);
            }
            homeMain.addView(layout);
        }
    }

    /**
     * Method called when the user selects the button to take a picture of an item
     */
    public void takePicture()
    {

    }

}