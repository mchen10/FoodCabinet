package foodcabinet.foodcabinet;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Michael on 5/12/16.
 */
public class Home extends AppCompatActivity{
    private Cabinet cabinet;
    private String currentSelection = "ALL";
    private ArrayList<Product> display = new ArrayList<Product>();
    private ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();
    private static final String foodDataKey = "pZ657QOXz5HciP7gfwvoyLaYccsLbkw51XIDrbGU";
    private int totalButtons = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstTime", false) == false) {
            new DatabaseCreator().execute("http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0900&max=360",
                    "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1100&max=836",
                    "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0100&max=283");

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        setContentView(R.layout.activity_home);
        cabinet = new Cabinet();
        cabinet.addProduct(new Product("Bread", "Food", 5, 5));
        cabinet.addProduct(new Product("Bread", "Dairy", 5, 5));
        cabinet.addProduct(new Product("Bread", "Dairy", 5, 5));
        cabinet.addProduct(new Product("Bread", "Dairy", 5, 5));
        display = cabinet.getCurrentProducts();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle("Food Cabinet");

        //updateSelectionScroll();
        updateScreen();
    }

    boolean color = false;
    public void test(View view) {
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        if (!color) {
            layout.setBackgroundColor(Color.RED);
        } else {
            layout.setBackgroundColor(Color.BLUE);
        }
        color = !color;
    }

    public void test2(View view) {
        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        layout.setBackgroundColor(Color.GREEN);
    }

    public void updateSelectionScroll() {
        //Log.d("TESTING", ""+totalButtons);
        for (int i = 0; i < totalButtons; i++) {
            Button layout = (Button) findViewById(i + 1000);
            ((ViewGroup) layout.getParent()).removeView(layout);
        }

        totalButtons = 0;

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bParams.setMargins(75, 150, 0, 0);
        HorizontalScrollView.LayoutParams scrollParams = new HorizontalScrollView.LayoutParams(HorizontalScrollView.LayoutParams.WRAP_CONTENT, HorizontalScrollView.LayoutParams.WRAP_CONTENT);
        scrollParams.setMargins(0, 300, 0, 0);

        HorizontalScrollView scroll = new HorizontalScrollView(this);
        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test(v);
            }
        });
        //scroll.setLayoutParams(scrollParams);
        LinearLayout scrollLayout = new LinearLayout(this);
        scrollLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "HIHIHI");
                test(v);
            }
        });
        scrollLayout.setLayoutParams(scrollParams);
        scrollLayout.setOrientation(LinearLayout.HORIZONTAL);
        ArrayList<Button> tempButtons = new ArrayList<Button>();
        ArrayList<Product> products = cabinet.getCurrentProducts();
        for (int i = 0; i < products.size(); i++) {
            boolean exists = false;
            for (Button b: tempButtons) {
                if (products.get(i).getGroup().equals(b.getText())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                continue;
            }
            if (currentSelection.equals(products.get(i).getGroup())) {
                continue;
            }
            Button b = new Button(this);
            b.setId(totalButtons + 1000);
            //Log.d("TESTING", (totalButtons + 1000) + "");
            b.setLayoutParams(bParams);
            b.setText(products.get(i).getGroup());
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearScreen();
                    display = new ArrayList<Product>();
                    for (int j = 0; j < cabinet.getCurrentProducts().size(); j++) {
                        if (((Button) v).getText().equals(cabinet.getCurrentProducts().get(j).getGroup())) {
                            display.add(cabinet.getCurrentProducts().get(j));
                        }
                    }
                    currentSelection = ((Button) v).getText().toString();
                    updateScreen();
                }
            });
            totalButtons++;
            tempButtons.add(b);
            scrollLayout.addView(b);
        }
        if (currentSelection != "ALL") {
            Button b = new Button(this);
            b.setId(totalButtons + 1000);
            b.setLayoutParams(bParams);
            b.setText("All Products");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearScreen();
                    display = cabinet.getCurrentProducts();
                    currentSelection = "ALL";
                    updateScreen();
                }
            });
            scrollLayout.addView(b);
            totalButtons++;
        }
        scroll.addView(scrollLayout);
        collapsingToolbar.addView(scroll);
    }

    /**
     * Method called when the user selects the button to take a picture of an item
     */
    public void takePicture(View view) {
        Log.d("TEST", "HERERE");
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
            PictureToText convert = new PictureToText(image, database);
            convert.textToProduct();
            ArrayList<ArrayList<String>> products= convert.getProducts();
            for(int i = 0; i < products.size(); i++) {
                String prod = products.get(i).get(0);
                boolean found = false;
                for (int j = 0; j < cabinet.getCurrentProducts().size(); j++) {
                    Product p = cabinet.getCurrentProducts().get(j);
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
                    Product p = new Product(prod, products.get(i).get(1));
                    cabinet.addProduct(p);
                }
                Log.d("test",products.size()+"");
            }
            updateScreen();
        }
    }

    public void clearScreen() {
    	for (int i = 0; i < display.size(); i++) {
    		LinearLayout layout = (LinearLayout) findViewById(i);
    		((ViewGroup) layout.getParent()).removeView(layout);
    	}
    }
    
    public void updateScreen() {
        Log.d("Picture Button","Log Works");
        //updateSelectionScroll();
        ArrayList<Product> products = display;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;

        LinearLayout homeMain = (LinearLayout) findViewById(R.id.HomeMain);
        LinearLayout.LayoutParams picLp = new LinearLayout.LayoutParams((screenWidth - 224) / 3, (screenWidth - 120) / 3);
        picLp.setMargins(23, -30, 20, -50);
        LinearLayout.LayoutParams layoutLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textLp.setMargins(0, 0, 0, 10);
        LinearLayout.LayoutParams prodLp = new LinearLayout.LayoutParams((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 112, getResources().getDisplayMetrics()), LinearLayout.LayoutParams.WRAP_CONTENT);
        prodLp.setMargins(0, 15, 20, 15);
        for (int i = 0; i < products.size(); i+=3) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layoutLp.setMargins(15, 5, 15, 20);
            layout.setLayoutParams(layoutLp);
            if (i + 1 == products.size()) {
                LinearLayout b1 = new LinearLayout(this);
                b1.setGravity(Gravity.CENTER);
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
                text1.setTextColor(Color.WHITE);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setTextColor(Color.WHITE);
                text2.setText(products.get(i).getUDays()+"");
                text2.setLayoutParams(textLp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setTextColor(Color.WHITE);
                text3.setText(products.get(i).getEDays()+"");
                text3.setLayoutParams(textLp);
                b1.addView(text3);

                b1.setLayoutParams(prodLp);
                b1.setBackgroundColor(Color.BLUE);
                layout.addView(b1);
            } else if (i + 2 == products.size()) {
                LinearLayout b1 = new LinearLayout(this);
                b1.setGravity(Gravity.CENTER);
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
                text1.setTextColor(Color.WHITE);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setTextColor(Color.WHITE);
                text2.setText(products.get(i).getUDays()+"");
                text2.setLayoutParams(textLp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setTextColor(Color.WHITE);
                text3.setText(products.get(i).getEDays()+"");
                text3.setLayoutParams(textLp);
                b1.addView(text3);

                b1.setBackgroundColor(Color.BLUE);
                layoutLp.setMargins(0, 0, 15, 0);
                b1.setLayoutParams(prodLp);
                layout.addView(b1);

                LinearLayout b2 = new LinearLayout(this);
                b2.setGravity(Gravity.CENTER);
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
                text21.setTextColor(Color.WHITE);
                text21.setText(products.get(i + 1).getName());
                text21.setLayoutParams(textLp);
                b2.addView(text21);

                TextView text22 = new TextView(this);
                text22.setTextColor(Color.WHITE);
                text22.setText(products.get(i + 1).getUDays()+"");
                text22.setLayoutParams(textLp);
                b2.addView(text22);

                TextView text23 = new TextView(this);
                text23.setTextColor(Color.WHITE);
                text23.setText(products.get(i + 1).getEDays()+"");
                text23.setLayoutParams(textLp);
                b2.addView(text23);

                b2.setLayoutParams(prodLp);
                b2.setBackgroundColor(Color.BLUE);
                layout.addView(b2);
            } else {
                LinearLayout b1 = new LinearLayout(this);
                b1.setGravity(Gravity.CENTER);
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
                text1.setTextColor(Color.WHITE);
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                b1.addView(text1);

                TextView text2 = new TextView(this);
                text2.setTextColor(Color.WHITE);
                text2.setText(products.get(i).getUDays()+"");
                text2.setLayoutParams(textLp);
                b1.addView(text2);

                TextView text3 = new TextView(this);
                text3.setTextColor(Color.WHITE);
                text3.setText(products.get(i).getEDays()+"");
                text3.setLayoutParams(textLp);
                b1.addView(text3);

                b1.setBackgroundColor(Color.BLUE);
                layoutLp.setMargins(0, 0, 15, 0);
                b1.setLayoutParams(prodLp);
                layout.addView(b1);

                LinearLayout b2 = new LinearLayout(this);
                b2.setGravity(Gravity.CENTER);
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
                text21.setTextColor(Color.WHITE);
                text21.setText(products.get(i + 1).getName());
                text21.setLayoutParams(textLp);
                b2.addView(text21);

                TextView text22 = new TextView(this);
                text22.setTextColor(Color.WHITE);
                text22.setText(products.get(i + 1).getUDays() + "");
                text22.setLayoutParams(textLp);
                b2.addView(text22);

                TextView text23 = new TextView(this);
                text23.setTextColor(Color.WHITE);
                text23.setText(products.get(i + 1).getEDays()+"");
                text23.setLayoutParams(textLp);
                b2.addView(text23);

                b2.setBackgroundColor(Color.BLUE);
                layoutLp.setMargins(0, 0, 15, 0);
                b2.setLayoutParams(prodLp);
                layout.addView(b2);

                LinearLayout b3 = new LinearLayout(this);
                b3.setGravity(Gravity.CENTER);
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
                text31.setTextColor(Color.WHITE);
                text31.setText(products.get(i + 2).getName());
                text31.setLayoutParams(textLp);
                b3.addView(text31);

                TextView text32 = new TextView(this);
                text32.setTextColor(Color.WHITE);
                text32.setText(products.get(i + 2).getUDays()+"");
                text32.setLayoutParams(textLp);
                b3.addView(text32);

                TextView text33 = new TextView(this);
                text33.setTextColor(Color.WHITE);
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

    class DatabaseCreator extends AsyncTask<String, Void, ArrayList<ArrayList<String>>> {
        @Override
        protected ArrayList<ArrayList<String>> doInBackground(String... params) {
            ArrayList<ArrayList<String>> products = new ArrayList<ArrayList<String>>();
            try {
                for (int i = 0; i < params.length; i++) {
                    URL connect = new URL(params[i]);
                    URLConnection foodConnect = connect.openConnection();
                    BufferedReader read = new BufferedReader(new InputStreamReader(foodConnect.getInputStream()));
                    String input;
                    ArrayList<String> currentGroup = new ArrayList<String>();
                    if (i ==  0) {
                        currentGroup.add("Fruits");
                    } else if (i == 1) {
                        currentGroup.add("Vegetables");
                    } else {
                        currentGroup.add("Dairy");
                    }
                    while ((input = read.readLine()) != null){
                        if (input.contains("<name>")) {
                            if (input.trim().length() < 8) {
                                String temp = read.readLine().trim();
                                temp = temp.substring(0, temp.indexOf(","));
                                if (currentGroup.contains(temp) == false) {
                                    currentGroup.add(temp);
                                }
                            } else {
                                String temp = input.trim();
                                Log.d("HOME", temp + " " + temp.indexOf("<name>"));
                                temp = temp.substring(temp.indexOf("<name>") + 6, temp.indexOf("</name>"));
                                if (temp.indexOf(",") == -1) {
                                    if (currentGroup.contains(temp) == false) {
                                        currentGroup.add(temp);
                                    }
                                } else {
                                    temp = temp.substring(0, temp.indexOf(","));
                                    if (currentGroup.contains(temp) == false) {
                                        currentGroup.add(temp);
                                    }
                                }
                            }
                        }
                    }
                    products.add(currentGroup);
                }
                return products;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException io) {
                io.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(ArrayList<ArrayList<String>> prods) {
            for (int i = 0; i < prods.size(); i++) {
                database.add(prods.get(i));
            }
        }
    }
}

