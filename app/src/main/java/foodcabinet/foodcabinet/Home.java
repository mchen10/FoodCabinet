package foodcabinet.foodcabinet;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

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
        LinearLayout.LayoutParams picLp = new LinearLayout.LayoutParams((screenWidth - 30) / 3, screenWidth / 3);
        LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textLp.setMargins(0, 0, 0, 10);
        LinearLayout.LayoutParams layoutLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams prodLp = new LinearLayout.LayoutParams((screenWidth - 20) / 3, 100);
        prodLp.setMargins(0, 0, 0, 20);
        for (int i = 0; i < products.size(); i+=3) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layoutLp.setMargins(15, 5, 15, 20);
            layout.setLayoutParams(layoutLp);
            if (i + 1 == products.size()) {
                LinearLayout b1 = new LinearLayout(this);
                b1.setClickable(true);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                b1.setOrientation(LinearLayout.VERTICAL);

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                picLp.setMargins(0, 0, 0, 10);
                pic1.setLayoutParams(picLp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setText(products.get(i).getUDate());
                text2.setLayoutParams(textLp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setText(products.get(i).getEDate());
                text3.setLayoutParams(textLp);
                b1.addView(text3);

                b1.setLayoutParams(prodLp);
                b1.setBackgroundColor(Color.BLUE);
                layout.addView(b1);
            } else if (i + 2 == products.size()) {
                LinearLayout b1 = new LinearLayout(this);
                b1.setOrientation(LinearLayout.VERTICAL);

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                picLp.setMargins(0, 0, 0, 10);
                pic1.setLayoutParams(picLp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setText(products.get(i).getUDate());
                text2.setLayoutParams(textLp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setText(products.get(i).getEDate());
                text3.setLayoutParams(textLp);
                b1.addView(text3);

                b1.setBackgroundColor(Color.BLUE);
                layoutLp.setMargins(0, 0, 15, 0);
                b1.setLayoutParams(prodLp);
                layout.addView(b1);

                LinearLayout b2 = new LinearLayout(this);
                b2.setOrientation(LinearLayout.VERTICAL);

                ImageView pic21 = new ImageView(this);
                pic21.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                picLp.setMargins(0, 0, 0, 10);
                pic21.setLayoutParams(picLp);
                b2.addView(pic21);

                TextView text21 = new TextView(this);
                text21.setText(products.get(i + 1).getName());
                text21.setLayoutParams(textLp);
                b2.addView(text21);

                TextView text22 = new TextView(this);
                text22.setText(products.get(i + 1).getUDate());
                text22.setLayoutParams(textLp);
                b2.addView(text22);

                TextView text23 = new TextView(this);
                text23.setText(products.get(i + 1).getEDate());
                text23.setLayoutParams(textLp);
                b2.addView(text23);

                b2.setLayoutParams(prodLp);
                b2.setBackgroundColor(Color.BLUE);
                layout.addView(b2);
            } else {
                LinearLayout b1 = new LinearLayout(this);
                b1.setOrientation(LinearLayout.VERTICAL);

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                picLp.setMargins(0, 0, 0, 10);
                pic1.setLayoutParams(picLp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setText(products.get(i).getUDate());
                text2.setLayoutParams(textLp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setText(products.get(i).getEDate());
                text3.setLayoutParams(textLp);
                b1.addView(text3);

                b1.setBackgroundColor(Color.BLUE);
                layoutLp.setMargins(0, 0, 15, 0);
                b1.setLayoutParams(prodLp);
                layout.addView(b1);

                LinearLayout b2 = new LinearLayout(this);
                b2.setOrientation(LinearLayout.VERTICAL);

                ImageView pic21 = new ImageView(this);
                pic21.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                picLp.setMargins(0, 0, 0, 10);
                pic21.setLayoutParams(picLp);
                b2.addView(pic21);

                TextView text21 = new TextView(this);
                text21.setText(products.get(i + 1).getName());
                text21.setLayoutParams(textLp);
                b2.addView(text21);

                TextView text22 = new TextView(this);
                text22.setText(products.get(i + 1).getUDate());
                text22.setLayoutParams(textLp);
                b2.addView(text22);

                TextView text23 = new TextView(this);
                text23.setText(products.get(i + 1).getEDate());
                text23.setLayoutParams(textLp);
                b2.addView(text23);

                b2.setBackgroundColor(Color.BLUE);
                layoutLp.setMargins(0, 0, 15, 0);
                b2.setLayoutParams(prodLp);
                layout.addView(b2);

                LinearLayout b3 = new LinearLayout(this);
                b3.setOrientation(LinearLayout.VERTICAL);

                ImageView pic31 = new ImageView(this);
                pic31.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                picLp.setMargins(0, 0, 0, 10);
                pic31.setLayoutParams(picLp);
                b3.addView(pic31);

                TextView text31 = new TextView(this);
                text31.setText(products.get(i + 1).getName());
                text31.setLayoutParams(textLp);
                b3.addView(text31);

                TextView text32 = new TextView(this);
                text32.setText(products.get(i + 1).getUDate());
                text32.setLayoutParams(textLp);
                b3.addView(text32);

                TextView text33 = new TextView(this);
                text33.setText(products.get(i + 1).getEDate());
                text33.setLayoutParams(textLp);
                b3.addView(text33);

                b3.setLayoutParams(prodLp);
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