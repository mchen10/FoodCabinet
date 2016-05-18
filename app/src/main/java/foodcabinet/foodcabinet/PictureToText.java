package foodcabinet.foodcabinet;



import android.media.Image;
import android.util.Log;

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
//import com.google.api.services.vision.v1.model.Image;
import com.google.api.client.json.JsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

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


        /**
         * Creates a new Instance of Picture to Text object, defined by the passed in Image parameter
         * @param a Image to be translated into a product
         */
        public PictureToText(Image a)
        {
            picture=a;
            prod= new ArrayList<Product>();
            translated="";
            try {
                callCloudVision();
                textToProduct();
            }catch(IOException e)
            {

            }
        }

    private void callCloudVision() throws IOException {

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


            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature detect = new Feature();
                detect.setType("TEXT_DETECTION");
                detect.setMaxResults(10);
                add(detect);
            }});



            add(annotateImageRequest);

        }});
        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        BatchAnnotateImagesResponse res= annotateRequest.execute();
        inputToString(res);


    }
    public void inputToString(BatchAnnotateImagesResponse e)
    {
        List<EntityAnnotation> text=e.getResponses().get(0).getTextAnnotations();
        for(EntityAnnotation ann:text) {
            translated += ann.getDescription();
        }
        Log.d("", "inputToString() returned: " + "Testing");
    }
        /**
         * Method used to translate the input text into a product object
         */
        public Product textToProduct()
        {

            return null;
        }
    }

