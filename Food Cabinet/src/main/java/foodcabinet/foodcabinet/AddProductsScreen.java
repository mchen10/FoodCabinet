package foodcabinet.foodcabinet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import foodcabinet.Foodcabinet.R;


public class AddProductsScreen extends AppCompatActivity {
    private Cabinet cabinet;
    private ArrayList<Product> needUpdate;
    private ArrayList<ArrayList<String>> database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_screen);

        cabinet = (Cabinet) getIntent().getSerializableExtra("Cabinet");
        needUpdate = (ArrayList<Product>) getIntent().getSerializableExtra("NeedUpdates");
        database = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("Database");

        LinearLayout layout = (LinearLayout) findViewById(R.id.DataEnterLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 20);
        for (int i = 0; i < needUpdate.size(); i++) {
            TextView name = new TextView(this);
            name.setText(needUpdate.get(i).getName());
            name.setLayoutParams(params);

            EditText eDate = new EditText(this);
            eDate.setHint("Enter days until expired");
            eDate.setLayoutParams(params);
            eDate.setId(10000 + i);

            EditText uDate = new EditText(this);
            uDate.setHint("Enter days until predicted use");
            uDate.setLayoutParams(params);
            uDate.setId(20000 + i);

            layout.addView(name);
            layout.addView(eDate);
            layout.addView(uDate);
        }

        Button b = new Button(this);
        b.setHint("Submit Dates");
        b.setWidth(layout.getWidth());
        b.setHeight(30);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData(v);
            }
        });

        layout.addView(b);
    }

    /**
     * Saves the data inputted by the user
     * @param v the button clicked by the user
     */
    public void submitData(View v) {
        for (int i = 0; i < needUpdate.size(); i++) {
            needUpdate.get(i).increaseEntered();
            EditText eDate = (EditText) findViewById(10000+i);
            Integer eD = Integer.parseInt(eDate.getText().toString());
            EditText uDate = (EditText) findViewById(20000+i);
            Integer uD = Integer.parseInt(uDate.getText().toString());
            needUpdate.get(i).setUDays(uD);
            needUpdate.get(i).setEDays(eD);

            for (int j = 0; j < cabinet.getCurrentProducts().size(); j++) {
                if (cabinet.getCurrentProducts().get(j).getName().equals(needUpdate.get(i).getName())) {
                    cabinet.getCurrentProducts().remove(j);
                    cabinet.getCurrentProducts().add(needUpdate.get(i));
                    break;
                }
            }
        }

        Intent intent = new Intent(this, Home.class);
        intent.putExtra("Cabinet", cabinet);
        intent.putExtra("Database", database);
        startActivity(intent);
    }

}
