package foodcabinet.foodcabinet;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.lang.reflect.Array;

/**
 * Created by Michael on 5/12/16.
 */
public class Home extends AppCompatActivity{
    private Cabinet cabinet;
    private ArrayList<String> database = new ArrayList<String>();
    private static final String foodDataKey = "pZ657QOXz5HciP7gfwvoyLaYccsLbkw51XIDrbGU";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstTime", false) == false) {
            addToFoodDatabase("Fruit");
            addToFoodDatabase("Veggie");
            addToFoodDatabase("Dairy");
        }

        setContentView(R.layout.activity_home);
        cabinet = new Cabinet();
        cabinet.addProduct(new Product("Bread", 5, 5));
        cabinet.addProduct(new Product("Bread", 5, 5));
        cabinet.addProduct(new Product("Bread", 5, 5));
        cabinet.addProduct(new Product("Bread", 5, 5));

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle("Food Cabinet");

        updateScreen();
    }

    public void addToFoodDatabase(String type) {
        String urlLink = "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey;

        if (type.equals("Fruit")) {
            urlLink += "&fg=0900&max=360";
        } else if (type.equals("Veggie")) {
            urlLink += "&fg=1100&max=836";
        } else if (type.equals("Dairy")) {
            urlLink += "&fg=0100&max=283";
        }

        try {
            URL connect = new URL(urlLink);
            URLConnection foodConnect = connect.openConnection();
            BufferedReader read = new BufferedReader(new InputStreamReader(foodConnect.getInputStream()));
            String input;
            while ((input = read.readLine()) != null){
                if (input.contains("<name>")) {
                    String temp = input.substring(input.indexOf("<name>" + 6), input.indexOf("</name"));
                    temp = temp.substring(0, temp.indexOf(","));
                    if (database.contains(temp) == false) {
                        database.add(temp);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    /**
     * Method called when the user selects the button to take a picture of an item
     */
    public void takePicture(View view) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
        	clearScreen();
            Bundle bundle = data.getExtras();
            Bitmap image = (Bitmap) bundle.get("data");
            UsedDatePredictor predictU = new UsedDatePredictor();
            ExpirationDatePredictor predictE = new ExpirationDatePredictor();
            PictureToText convert = new PictureToText(image);
            convert.textToProduct();
            ArrayList<String> products= convert.getProducts();
            for(String prod:products) {
                boolean found = false;
                for (int i = 0; i < cabinet.getCurrentProducts().size(); i++) {
                    Product p = cabinet.getCurrentProducts().get(i);
                    if (p.getName().equals(prod)) {
                        Calendar used = predictU.predict(p);
                        Calendar expir = predictE.predict(p);
                        p.updateBDate();
                        p.addUsedEDay(p.getEDays());
                        p.setEDays(used.get(Calendar.DAY_OF_YEAR));

                        p.addUsedUDay(p.getUDays());
                        p.setUDays(expir.get(Calendar.DAY_OF_YEAR));
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    Product p = new Product(prod);
                    cabinet.addProduct(p);
                }
            }
            updateScreen();
        }
    }

    public void clearScreen() {
    	ArrayList<Product> products = cabinet.getCurrentProducts();
    	
    	for (int i = 0; i < products.size(); i++) {
    		LinearLayout layout = (LinearLayout) findViewById(i);
    		((ViewGroup) layout.getParent()).removeView(layout);
    	}
    }
    
    public void updateScreen() {
        ArrayList<Product> products = cabinet.getCurrentProducts();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;

        LinearLayout homeMain = (LinearLayout) findViewById(R.id.HomeMain);
        LinearLayout.LayoutParams picLp = new LinearLayout.LayoutParams((screenWidth - 224) / 3, (screenWidth - 120) / 3);
        picLp.setMargins(30, -30, 30, 0);
        LinearLayout.LayoutParams layoutLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        textLp.setMargins(0, 0, 0, 10);
        LinearLayout.LayoutParams prodLp = new LinearLayout.LayoutParams((screenWidth - 50) / 3, (screenWidth - 50) / 3);
        prodLp.setMargins(0, 15, 20, 15);
        for (int i = 0; i < products.size(); i+=3) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layoutLp.setMargins(15, 5, 15, 20);
            layout.setLayoutParams(layoutLp);
            if (i + 1 == products.size()) {
                LinearLayout b1 = new LinearLayout(this);
                b1.setId(i);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), ZoomedInProduct.class);
                        intent.putExtra("Product", cabinet.getCurrentProducts().get(v.getId()));
                        startActivity(intent);
                    }
                });
                b1.setOrientation(LinearLayout.VERTICAL);

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic1.setLayoutParams(picLp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setText(products.get(i).getUDays()+"");
                text2.setLayoutParams(textLp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setText(products.get(i).getEDays()+"");
                text3.setLayoutParams(textLp);
                b1.addView(text3);

                b1.setLayoutParams(prodLp);
                b1.setBackgroundColor(Color.BLUE);
                layout.addView(b1);
            } else if (i + 2 == products.size()) {
                LinearLayout b1 = new LinearLayout(this);
                b1.setId(i);
                b1.setOrientation(LinearLayout.VERTICAL);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), ZoomedInProduct.class);
                        intent.putExtra("Product", cabinet.getCurrentProducts().get(v.getId()));
                        startActivity(intent);
                    }
                });

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic1.setLayoutParams(picLp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setText(products.get(i).getUDays()+"");
                text2.setLayoutParams(textLp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setText(products.get(i).getEDays()+"");
                text3.setLayoutParams(textLp);
                b1.addView(text3);

                b1.setBackgroundColor(Color.BLUE);
                layoutLp.setMargins(0, 0, 15, 0);
                b1.setLayoutParams(prodLp);
                layout.addView(b1);

                LinearLayout b2 = new LinearLayout(this);
                b2.setId(i + 1);
                b2.setOrientation(LinearLayout.VERTICAL);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), ZoomedInProduct.class);
                        intent.putExtra("Product", cabinet.getCurrentProducts().get(v.getId()));
                        startActivity(intent);
                    }
                });

                ImageView pic21 = new ImageView(this);
                pic21.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic21.setLayoutParams(picLp);
                b2.addView(pic21);

                TextView text21 = new TextView(this);
                text21.setText(products.get(i + 1).getName());
                text21.setLayoutParams(textLp);
                b2.addView(text21);

                TextView text22 = new TextView(this);
                text22.setText(products.get(i + 1).getUDays()+"");
                text22.setLayoutParams(textLp);
                b2.addView(text22);

                TextView text23 = new TextView(this);
                text23.setText(products.get(i + 1).getEDays()+"");
                text23.setLayoutParams(textLp);
                b2.addView(text23);

                b2.setLayoutParams(prodLp);
                b2.setBackgroundColor(Color.BLUE);
                layout.addView(b2);
            } else {
                LinearLayout b1 = new LinearLayout(this);
                b1.setOrientation(LinearLayout.VERTICAL);
                b1.setId(i);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), ZoomedInProduct.class);
                        intent.putExtra("Product", cabinet.getCurrentProducts().get(v.getId()));
                        startActivity(intent);
                    }
                });

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic1.setLayoutParams(picLp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setText(products.get(i).getUDays()+"");
                text2.setLayoutParams(textLp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setText(products.get(i).getEDays()+"");
                text3.setLayoutParams(textLp);
                b1.addView(text3);

                b1.setBackgroundColor(Color.BLUE);
                layoutLp.setMargins(0, 0, 15, 0);
                b1.setLayoutParams(prodLp);
                layout.addView(b1);

                LinearLayout b2 = new LinearLayout(this);
                b2.setOrientation(LinearLayout.VERTICAL);
                b2.setId(i + 1);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), ZoomedInProduct.class);
                        intent.putExtra("Product", cabinet.getCurrentProducts().get(v.getId()));
                        startActivity(intent);
                    }
                });

                ImageView pic21 = new ImageView(this);
                pic21.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic21.setLayoutParams(picLp);
                b2.addView(pic21);

                TextView text21 = new TextView(this);
                text21.setText(products.get(i + 1).getName());
                text21.setLayoutParams(textLp);
                b2.addView(text21);

                TextView text22 = new TextView(this);
                text22.setText(products.get(i + 1).getUDays() + "");
                text22.setLayoutParams(textLp);
                b2.addView(text22);

                TextView text23 = new TextView(this);
                text23.setText(products.get(i + 1).getEDays()+"");
                text23.setLayoutParams(textLp);
                b2.addView(text23);

                b2.setBackgroundColor(Color.BLUE);
                layoutLp.setMargins(0, 0, 15, 0);
                b2.setLayoutParams(prodLp);
                layout.addView(b2);

                LinearLayout b3 = new LinearLayout(this);
                b3.setOrientation(LinearLayout.VERTICAL);
                b3.setId(i + 2);
                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), ZoomedInProduct.class);
                        intent.putExtra("Product", cabinet.getCurrentProducts().get(v.getId()));
                        startActivity(intent);
                    }
                });

                ImageView pic31 = new ImageView(this);
                pic31.setImageResource(getResources().getIdentifier("bread", "drawable", getPackageName()));
                pic31.setLayoutParams(picLp);
                b3.addView(pic31);

                TextView text31 = new TextView(this);
                text31.setText(products.get(i + 2).getName());
                text31.setLayoutParams(textLp);
                b3.addView(text31);

                TextView text32 = new TextView(this);
                text32.setText(products.get(i + 2).getUDays()+"");
                text32.setLayoutParams(textLp);
                b3.addView(text32);

                TextView text33 = new TextView(this);
                text33.setText(products.get(i + 2).getEDays()+"");
                text33.setLayoutParams(textLp);
                b3.addView(text33);

                b3.setLayoutParams(prodLp);
                b3.setBackgroundColor(Color.BLUE);
                layout.addView(b3);
            }
            homeMain.addView(layout);
        }
    }
}

