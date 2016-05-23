package foodcabinet.foodcabinet;



import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.Image;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahaj on 5/15/16.
 */
public class PictureToText {

    private ArrayList<String> translated;
    private Bitmap picture;
    private ArrayList<ArrayList<String>> prod;
    private ArrayList<ArrayList<String>> database;
    private AsyncTask task;

    /**
     * Connects to Google's External Image Recognition Library, Cloud Vision
     * @return A Vision object that is defined by the passed in Image
     * @throws IOException Exception thrown when Image is unable to be processed by the library
     * @throws GeneralSecurityException Exception thrown when connection to external library is not properly done
     */


    /**
     * Creates a new Instance of Picture to Text object, defined by the passed in Image parameter
     *
     * @param a Image to be translated into a product
     */
    public PictureToText(Bitmap a, ArrayList<ArrayList<String>> database) {
        this.database = database;
        picture = a;
        prod = new ArrayList<ArrayList<String>>();
        translated = new ArrayList<String>();
        try {
            callCloudVision();
            task.execute().get();
        } catch (IOException e) {
            Log.d("PicToText", "Failed to Call Cloud Vision");
        } catch (Exception e){

        }
    }


    /*
     * Calls Googles External Library, Cloud Vision OCR to extract data from the input picture
     * @throws IOException Exception thrown when the call to Cloud Vision fails or the picture is not inputted correctly
     */

    private void callCloudVision() throws IOException {

        task=new AsyncTask<Object, Void, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(Object... a) {
                try {
                    Vision.Builder builder = new Vision.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), null);
                    builder.setVisionRequestInitializer(new VisionRequestInitializer("AIzaSyCcuM1ltlLLmp1woOqsyXZpjsL0qHq_9eU"));
                    Vision vision = builder.build();
                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        Image encoded = new Image();
                        ByteArrayOutputStream x = new ByteArrayOutputStream();
                        picture.compress(Bitmap.CompressFormat.JPEG, 90, x);
                        annotateImageRequest.setImage(encoded.encodeContent(x.toByteArray()));
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature detect = new Feature();
                            detect.setType("TEXT_DETECTION");
                            add(detect);
                        }});


                        add(annotateImageRequest);

                    }});
                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);
                    BatchAnnotateImagesResponse res = annotateRequest.execute();
                    return inputToString(res);
                } catch (IOException e) {
                    Log.d("PicToText", "IO EXCEPTION OH NOOOO");
                }
                return new ArrayList<String>();
                };
            protected void onPostExecute(ArrayList<String> items) {
                for (int i = 0; i < items.size(); i++) {
                    if(!items.get(i).contains(" "))
                    {
                        translated.add(items.get(i));
                    }
                }
                textToProduct();
            }
    };
}

    /**
     * Method to translate the respone of the Google Cloud Vision OCR into the translated Arraylist in the class
     * @param e The BatchAnnotateImagesResponse sent by Cloud Vision
     */
    public ArrayList<String> inputToString(BatchAnnotateImagesResponse e) {
        ArrayList<String> trans=new ArrayList<String>();
        List<EntityAnnotation> text = e.getResponses().get(0).getTextAnnotations();
        for (EntityAnnotation ann : text) {
            trans.add(ann.getDescription());
            //Log.d("Testing",ann.getDescription());
        }
        return trans;
    }

    /**
     * Method used to translate the input text into a products
     */
    public void textToProduct() {
        //Log.d("Products"," "+translated.size());
        for (String a : translated) {
            String word = "";
            String group = "";
            int least = 0;
            for (int i = 0; i < database.size(); i++) {
                for (int j = 1; j < database.get(i).size(); j++) {
                    String s = database.get(i).get(j);
                    int diff = findMin(a, s);
                    Log.d("TextToProduct",diff+"     " + a.length() + "      " +  s  + "      "+a);
                    if (diff < a.length() / 2) {
                        if (diff < least) {
                            least = diff;
                            word = s;
                            group = database.get(i).get(0);
                            //Log.d("TextToProduct",a + "    " + word);
                        }
                    }
                }
            }
            ArrayList<String> temp = new ArrayList<String>();
            if(word.length()>0)
            {
                temp.add(word);
                temp.add(group);
                prod.add(temp);
            }
        }
    }

    /**
     * Get the Products that are translated by the Image
     * @return Products translated from the Image
     */
    public ArrayList<ArrayList<String>> getProducts() {
        return prod;
    }

    /**
     * Finds the amount of changes needed to turn one string into another so that the food type can be determined
     * @param a String to check for differences
     * @param b String to check against
     * @return number of changes needed to make the two strings equal
     */
    public int findMin(String a, String b) {
        int l1 = a.length();
        int l2 = b.length();

        int[][] dp = new int[l1 + 1][l2 + 1];

        for (int i = 0; i <= l1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= l2; j++) {
            dp[0][j] = j;
        }

        for (int i = 0; i < l1; i++) {
            char c1 = a.charAt(i);
            for (int j = 0; j < l2; j++) {
                char c2 = b.charAt(j);

                if (c1 == c2) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int r = dp[i][j] + 1;
                    int p = dp[i][j + 1] + 1;
                    int d = dp[i + 1][j] + 1;
                    int min=0;
                    if(r>p) {
                        min=r;
                    } else{
                        min=r;
                    }
                    if(d<=min) {
                        min=d;
                    }
                    dp[i + 1][j + 1] = min;
                }
            }
        }
        return dp[l1][l2];
    }

}