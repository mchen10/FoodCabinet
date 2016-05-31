package foodcabinet.Foodcabinet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class Home extends AppCompatActivity {
    private Cabinet cabinet;
    private String currentSelection = "ALL";
    private ArrayList<Product> display = new ArrayList<Product>();
    private ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();
    private static final String foodDataKey = "pZ657QOXz5HciP7gfwvoyLaYccsLbkw51XIDrbGU";
    private int totalButtons = 0;
    private Typeface font;

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable("Cabinet", cabinet);
        savedInstanceState.putSerializable("Database", database);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        cabinet = (Cabinet) savedInstanceState.getSerializable("Cabinet");
        database = (ArrayList<ArrayList<String>>) savedInstanceState.getSerializable("Database");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        if (getIntent().hasExtra("Database")) {
            database = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("Database");
        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putBoolean("firstTime", false).commit();
        if (prefs.getBoolean("firstTime", false) == false) {
            try {
                DatabaseCreator create = new DatabaseCreator();
                create.execute("http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=3500&max=165",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0300&max=368",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1800&max=879",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1300&max=961",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1400&max=371",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0800&max=356",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=2000&max=181",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0100&max=283",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=2100&max=363",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0400&max=220",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1500&max=265",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0900&max=360",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1700&max=464",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1600&max=381",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=2200&max=125",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1200&max=137",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1000&max=341",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0500&max=389",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=3600&max=109",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0700&max=170",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=2500&max=177",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0600&max=465",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=0200&max=64",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1900&max=360",
                        "http://api.nal.usda.gov/ndb/search/?format=xml&api_key="+foodDataKey + "&fg=1100&max=836");
            } catch (Exception e) {

            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        if (getIntent().hasExtra("Cabinet")) {
            cabinet = (Cabinet) getIntent().getSerializableExtra("Cabinet");
        } else {
            cabinet = new Cabinet();
            cabinet.addProduct(new Product("Bread", "Food", 5, 5));
            cabinet.addProduct(new Product("Cheese", "Dairy", 5, 5));
            cabinet.addProduct(new Product("Broccoli", "Dairy", 5, 5));
        }

        display = cabinet.getCurrentProducts();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setTitle("Food Cabinet");

        font = Typeface.createFromAsset(getAssets(), "fonts/MODERNA_.TTF");
        collapsingToolbar.setCollapsedTitleTypeface(font);
        collapsingToolbar.setExpandedTitleTypeface(font);

        updateSelectionScroll();
        updateScreen();
    }

    public void updateSelectionScroll() {
        for (int i = 0; i < totalButtons; i++) {
            Button layout = (Button) findViewById(i + 1000);
            if(layout!=null)
            {
                ((ViewGroup) layout.getParent()).removeView(layout);
            }
        }

        totalButtons = 0;

        float scale = getResources().getDisplayMetrics().widthPixels;

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

        CollapsingToolbarLayout.LayoutParams collapse = new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        collapse.setCollapseMode(CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX);
        LinearLayout temp = new LinearLayout(this);
        temp.setLayoutParams(collapse);
        temp.setOrientation(LinearLayout.VERTICAL);
        LinearLayout temp2 = new LinearLayout(this);
        LinearLayout.LayoutParams tempParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(scale * 0.35 + 0.5f));
        temp2.setLayoutParams(tempParams);
        temp.addView(temp2);

        LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams((int)(scale * 0.285 + 0.5f), (int)(scale * 0.1 + 0.5f));
        bParams.setMargins((int)(0.075 * scale + 0.5f), 0, 0, 0);

        HorizontalScrollView scroll = new HorizontalScrollView(this);
        LinearLayout scrollLayout = new LinearLayout(this);
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
            b.setBackgroundResource(R.drawable.selection_button_layout);
            b.setTextColor(Color.rgb(Integer.valueOf("00", 16), Integer.valueOf("66", 16), Integer.valueOf("8C", 16)));
            b.setId(totalButtons + 1000);
            b.setLayoutParams(bParams);
            b.setText(products.get(i).getGroup());
            b.setTypeface(font);
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
            b.setBackgroundResource(R.drawable.selection_button_layout);
            b.setTextColor(Color.rgb(Integer.valueOf("00", 16), Integer.valueOf("66", 16), Integer.valueOf("8C", 16)));
            b.setId(totalButtons + 1000);
            b.setLayoutParams(bParams);
            b.setTypeface(font);
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

        temp.addView(scroll);
        collapsingToolbar.addView(temp);
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

    private PictureToText convert;

    /**
     * Receives the picture taken and parses it
     * @param requestCode default value passed by system
     * @param resultCode default value passed by system
     * @param data default value passed by system
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            clearScreen();
            Bundle bundle = data.getExtras();
            Bitmap image = (Bitmap) bundle.get("data");
            convert = new PictureToText(image, database);
            setContentView(R.layout.loading);
            CountDownTimer timer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    ArrayList<ArrayList<String>> products = convert.getProducts();
                    parseImage(products);
                }
            };
            timer.start();
        }
    }

    /**
     * Takes in the converted products and adds them to the cabinet
     * @param products the list of products
     */
    public void parseImage(ArrayList<ArrayList<String>> products) {
        setContentView(R.layout.activity_home);
        UsedDatePredictor predictU = new UsedDatePredictor();
        ExpirationDatePredictor predictE = new ExpirationDatePredictor();

        ArrayList<Product> needSubmit = new ArrayList<Product>();

        for (int i = 0; i < products.size(); i++) {
            String prod = products.get(i).get(0);
            boolean found = false;
            for (int j = 0; j < cabinet.getCurrentProducts().size(); j++) {
                Product p = cabinet.getCurrentProducts().get(j);
                if (p.getName().equals(prod)) {
                    if (p.getNumTimesEntered() < 3) {
                        boolean add = true;
                        for (int x = 0; x < needSubmit.size(); x++) {
                            if (needSubmit.get(x).getName().equals(p.getName())) {
                                add = false;
                            }
                        }
                        if (add) {
                            needSubmit.add(p);
                        }
                    } else {
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
                    found = true;
                }
            }
            if (!found) {
                Product p = new Product(prod, products.get(i).get(1));
                cabinet.addProduct(p);
                needSubmit.add(p);
            }
        }

        if (needSubmit.size() > 0) {
            Intent intent = new Intent(this, AddProductsScreen.class);
            intent.putExtra("Cabinet", cabinet);
            intent.putExtra("NeedUpdates", needSubmit);
            intent.putExtra("Database", database);
            startActivity(intent);
        }

        display = cabinet.getCurrentProducts();
        updateScreen();
    }

    private int TextId = 5;
    /**
     * Clears the user interface
     */
    public void clearScreen() {
        for (int i = 0; i < display.size(); i++) {
            LinearLayout layout = (LinearLayout) findViewById(i);
            ((ViewGroup) layout.getParent()).removeView(layout);
        }
        for (int i = 0; i < display.size(); i+=2) {
            LinearLayout layout = (LinearLayout) findViewById(i + 100000);
            ((ViewGroup) layout.getParent()).removeView(layout);
        }
        TextView layout = (TextView) findViewById(TextId);
        ((ViewGroup) layout.getParent()).removeView(layout);
    }

    /**
     * Updates the user interface with the newly added products
     */
    public void updateScreen() {
        updateSelectionScroll();
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setTitle("Food Cabinet");
        collapsingToolbar.setExpandedTitleTypeface(font);
        collapsingToolbar.setCollapsedTitleTypeface(font);

        ArrayList<Product> products = display;

        LinearLayout homeMain = (LinearLayout) findViewById(R.id.HomeMain);
        float scale=getResources().getDisplayMetrics().widthPixels;

        LinearLayout.LayoutParams layoutLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutLp.setMargins(0, (int)(0.016*scale+0.5f), (int)(0.0001*scale+0.5f), (int)(0.064*scale+0.5f));

        LinearLayout.LayoutParams prodLp = new LinearLayout.LayoutParams((int)(0.425*scale+0.5f), LinearLayout.LayoutParams.WRAP_CONTENT);
        prodLp.setMargins((int) (0.05 * scale + 0.5f), 0, 0, 0);

        LinearLayout.LayoutParams picLp = new LinearLayout.LayoutParams((int)(0.35*scale+0.5f), (int)(0.24*scale+0.5f));
        picLp.setMargins((int)(0.0096*scale+0.5f), (int)(0.016*scale+0.5f), (int)(0.0096*scale+0.5f), (int)(0.016*scale+0.5f));
        LinearLayout.LayoutParams textLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textLp.setMargins(0, 0, 0, (int)(0.02*scale+0.5f));
        for (int i = 0; i < products.size(); i+=2) {
            LinearLayout layout = new LinearLayout(this);
            layout.setId(i + 100000);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(layoutLp);
            if (i + 1 == products.size()) {
                LinearLayout b1 = new LinearLayout(this);
                b1.setBackgroundResource(R.drawable.product_layout);
                b1.setGravity(Gravity.CENTER);
                b1.setId(i);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), ZoomedInProduct.class);
                        intent.putExtra("Product", cabinet.getCurrentProducts().get(v.getId()));
                        intent.putExtra("Cabinet", cabinet);
                        intent.putExtra("Database", database);
                        startActivity(intent);
                    }
                });
                b1.setOrientation(LinearLayout.VERTICAL);

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier(products.get(i).getName().toLowerCase(), "drawable", getPackageName()));
                pic1.setLayoutParams(picLp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setTextColor(Color.rgb(Integer.valueOf("00", 16), Integer.valueOf("66", 16), Integer.valueOf("8C", 16)));
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                text1.setTextSize((int)(0.015 * scale + 0.5f));
                text1.setTypeface(font);
                b1.addView(text1);

                b1.setLayoutParams(prodLp);
                layout.addView(b1);
            } else {
                LinearLayout b1 = new LinearLayout(this);
                b1.setBackgroundResource(R.drawable.product_layout);
                b1.setGravity(Gravity.CENTER);
                b1.setId(i);
                b1.setOrientation(LinearLayout.VERTICAL);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), ZoomedInProduct.class);
                        intent.putExtra("Product", cabinet.getCurrentProducts().get(v.getId()));
                        intent.putExtra("Cabinet", cabinet);
                        intent.putExtra("Database", database);
                        startActivity(intent);
                    }
                });

                ImageView pic1 = new ImageView(this);
                pic1.setImageResource(getResources().getIdentifier(products.get(i).getName().toLowerCase(), "drawable", getPackageName()));
                pic1.setLayoutParams(picLp);
                b1.addView(pic1);

                TextView text1 = new TextView(this);
                text1.setTextColor(Color.rgb(Integer.valueOf("00", 16), Integer.valueOf("66", 16), Integer.valueOf("8C", 16)));
                text1.setTextSize((int)(0.015 * scale + 0.5f));
                text1.setText(products.get(i).getName());
                text1.setLayoutParams(textLp);
                text1.setTypeface(font);
                b1.addView(text1);

                b1.setLayoutParams(prodLp);
                layout.addView(b1);

                LinearLayout b2 = new LinearLayout(this);
                b2.setBackgroundResource(R.drawable.product_layout);
                b2.setGravity(Gravity.CENTER);
                b2.setId(i + 1);
                b2.setOrientation(LinearLayout.VERTICAL);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), ZoomedInProduct.class);
                        intent.putExtra("Product", cabinet.getCurrentProducts().get(v.getId()));
                        intent.putExtra("Cabinet", cabinet);
                        intent.putExtra("Database", database);
                        startActivity(intent);
                    }
                });

                ImageView pic21 = new ImageView(this);
                pic21.setImageResource(getResources().getIdentifier(products.get(i + 1).getName().toLowerCase(), "drawable", getPackageName()));
                pic21.setLayoutParams(picLp);
                b2.addView(pic21);

                TextView text21 = new TextView(this);
                text21.setTextColor(Color.rgb(Integer.valueOf("00", 16), Integer.valueOf("66", 16), Integer.valueOf("8C", 16)));
                text21.setTextSize((int)(0.015 * scale + 0.5f));
                text21.setText(products.get(i + 1).getName());
                text21.setLayoutParams(textLp);
                text21.setTypeface(font);
                b2.addView(text21);

                b2.setLayoutParams(prodLp);
                layout.addView(b2);
            }
            homeMain.addView(layout);
        }

        LinearLayout.LayoutParams tL = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tL.setMargins((int)(0.27 * scale + 0.5f), (int)(0.1 * scale + 0.5f),0, 0);
        TextView addText = new TextView(this);
        addText.setTextColor(Color.LTGRAY);
        addText.setText("Add More Products\n\nUsing Camera");
        addText.setGravity(Gravity.CENTER);
        addText.setTextSize((int)(0.015 * scale + 0.5f));
        addText.setLayoutParams(tL);
        addText.setId(TextId);
        homeMain.addView(addText);
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
                        currentGroup.add("American Indian/Alaska Native Foods");
                    } else if (i == 1) {
                        currentGroup.add("Baby Foods");
                    } else if (i == 2) {
                        currentGroup.add("Baked Products");
                    } else if (i == 3) {
                        currentGroup.add("Beef Products");
                    } else if (i == 4) {
                        currentGroup.add("Beverages");
                    } else if (i == 5) {
                        currentGroup.add("Breakfast Cereals");
                    } else if (i == 6) {
                        currentGroup.add("Cereal Grains and Pasta");
                    } else if (i == 7) {
                        currentGroup.add("Dairy and Egg Products");
                    } else if (i == 8) {
                        currentGroup.add("Fast Foods");
                    } else if (i == 9) {
                        currentGroup.add("Fats and Oils");
                    } else if (i == 10) {
                        currentGroup.add("Finfish and Shellfish Products");
                    } else if (i == 11) {
                        currentGroup.add("Fruits and Fruit Juices");
                    } else if (i == 12) {
                        currentGroup.add("Lamb, Veal, and Game Products");
                    } else if (i == 13) {
                        currentGroup.add("Legumes and Legume Products");
                    } else if (i == 14) {
                        currentGroup.add("Meals, Entrees, and Side Dishes");
                    } else if (i == 15) {
                        currentGroup.add("Nut and Seed Products");
                    } else if (i == 16) {
                        currentGroup.add("Pork Products");
                    } else if (i == 17) {
                        currentGroup.add("Poultry Products");
                    } else if (i == 18) {
                        currentGroup.add("Restaurant Foods");
                    } else if (i == 19) {
                        currentGroup.add("Sausages and Luncheon Meats");
                    } else if (i == 20) {
                        currentGroup.add("Snacks");
                    } else if (i == 21) {
                        currentGroup.add("Soups, Sauces, and Gravies");
                    } else if (i == 22) {
                        currentGroup.add("Spices and Herbs");
                    } else if (i == 23) {
                        currentGroup.add("Sweets");
                    } else if (i == 24) {
                        currentGroup.add("Vegetables and Vegetable Products");
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

