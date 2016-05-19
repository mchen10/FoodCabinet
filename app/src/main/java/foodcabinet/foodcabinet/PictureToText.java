package foodcabinet.foodcabinet;



import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.client.json.JsonFactory;


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
    private ArrayList<String> prod;
    private ArrayList<String> database;

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
    public PictureToText(Bitmap a) {
        database = new ArrayList<String>();
        database.add("bread");
        database.add("milk");
        database.add("meat");
        database.add("dairy");
        database.add("cereal");
        database.add("fruits");
        database.add("produce");
        picture = a;
        prod = new ArrayList<String>();
        translated = new ArrayList<String>();
        try {
            callCloudVision();
        } catch (IOException e) {

        }
    }

    private void callCloudVision() throws IOException {
        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... a) {
                try {
                    HttpTransport httpTransport = new NetHttpTransport();

                    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(new
                            VisionRequestInitializer("AIzaSyCcuM1ltlLLmp1woOqsyXZpjsL0qHq_9eU"));
                    Vision vision = builder.build();


                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        Image encoded = new Image();
                        picture.compress(Bitmap.CompressFormat.JPEG, 90, new ByteArrayOutputStream());
                        annotateImageRequest.setImage(encoded);
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
                    inputToString(res);
                } catch (IOException e) {

                }
                return "";
            }
        };
    }
            public void inputToString(BatchAnnotateImagesResponse e) {
                List<EntityAnnotation> text = e.getResponses().get(0).getTextAnnotations();
                for (EntityAnnotation ann : text) {
                    translated.add(ann.getDescription());
                }
            }

            /**
             * Method used to translate the input text into a products
             */
            public void textToProduct() {
                for (String s : translated) {

                }
            }

            /**
             * Get the Products that are translated by the Image
             *
             * @return Products translated from the Image
             */
            public ArrayList<String> getProducts() {
                for (String a : translated) {
                    String word = "";
                    int least = 0;
                    for (String s : database) {
                        int diff = findMin(a, s);
                        if (diff < a.length() / 2) {

                            if (diff < least) {
                                least = diff;
                                word = s;
                            }
                        }
                    }
                    prod.add(word);

                }

                return prod;
            }

            /**
             * Finds the amount of changes needed to turn one string into another so that the food type can be determined
             *
             * @param a String to check for differences
             * @param b String to check against
             * @return number of changes needed to make the two strings equal
             */
            public int findMin(String a, String b) {
                int first[] = new int[a.length() + 1];
                int second[] = new int[a.length() + 1];
                int one;
                int two;
                char each;
                int len;
                for (one = 0; one <= a.length(); one++) {
                    first[one] = one;
                }

                for (two = 1; two <= b.length(); two++) {
                    each = b.charAt(two - 1);
                    second[0] = two;

                    for (one = 1; one <= a.length(); one++) {
                        if (each == a.charAt(one - 1)) {
                            len = 0;
                        } else {
                            len = 1;
                        }
                        second[one] = Math.min(Math.min(second[one - 1] + 1, first[one] + 1), first[one - 1] + len);
                    }

                    int placed[] = first;
                    first = second;
                    second = placed;
                }


                return first[a.length()];
            }
        }




