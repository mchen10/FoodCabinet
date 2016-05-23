package foodcabinet.foodcabinet;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AddProductsScreen extends AppCompatActivity {
    private Cabinet cabinet;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products_screen);

        cabinet = (Cabinet) getIntent().getSerializableExtra("Cabinet");
    }

    public void takePicture() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            intent = data;
        }
    }

    public void SubmitData(View v) {
    	EditText product = (EditText) findViewById(R.id.DataEnterProduct);
    	EditText date = (EditText) findViewById(R.id.DataEnterDate);
    	
    	String prod = product.getText().toString();
    	Integer days = Integer.parseInt(date.getText().toString());
    	
    	Intent submit = new Intent(this, Home.class);
    	submit.putExtra("ProductName", prod);
    	submit.putExtra("DateName", days+"");
    	submit.putExtra("Cabinet", cabinet);
    	submit.putExtra("Picture", intent);
    	startActivity(submit);
    }

}
