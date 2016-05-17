package foodcabinet.foodcabinet;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.client.json.JsonFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * Created by Sahaj on 5/15/16.
 */
public class PictureToText {

        private String translated;
        private Image picture;
        private ArrayList<Product> prod;

        /**
         * Connects to Google's External Image Recognition Library, Cloud Vision
         * @return A Vision object that is defined by the passed in Image
         * @throws IOException Exception thrown when Image is unable to be processed by the library
         * @throws GeneralSecurityException Exception thrown when connection to external library is not properly done
         */
        public static Vision connectToOCR() throws IOException,GeneralSecurityException
        {
                GoogleCredential credential =
                        GoogleCredential.getApplicationDefault().createScoped(VisionScopes.all());
                JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
                return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
                        .setApplicationName("FoodCabinet")
                        .build();

        }

    /**
     * Connects to Google's External Cloud Vision API and sends an Image reqeust to be retrieved
     */
        public void sendToCloudVision()
        {

        }
        /**
         * Creates a new Instance of Picture to Text object, defined by the passed in Image parameter
         * @param a Image to be translated into a product
         */
        public PictureToText(Image a)
        {
            picture=a;
            prod= new ArrayList<Product>();
            translated="";
        }
    /*
    private void callCloudVision(final Bitmap bitmap) throws IOException {

        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(new
                VisionRequestInitializer("AIzaSyCcuM1ltlLLmp1woOqsyXZpjsL0qHq_9eU"));
        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();


            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("TEXT_DETECTION");
                labelDetection.setMaxResults(10);
                add(labelDetection);
            }});

            add(annotateImageRequest);
        }});
    }
    */
        /**
         * Method used to translate the input text into a product object
         */
        public void TextToProduct()
        {


        }




    }

