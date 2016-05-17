package foodcabinet.foodcabinet;


import android.widget.ImageView;

import java.util.Calendar;
import java.util.Date;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.client.json.JsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahaj on 5/15/16.
 */
public class PictureToText {
    private static String API_KEY = "AIzaSyCcuM1ltlLLmp1woOqsyXZpjsL0qHq_9eU";
    private String translated;
    private static Vision vision;
    private ImageView picture;
    private Product prod;

    /**
     * Method connects to Google's External Library, Cloud Vision
     *
     * @return A Vision object of which the Cloud Vision software is connected to
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static Vision connectToOCR() throws IOException, GeneralSecurityException {

        GoogleCredential credential = GoogleCredential.getApplicationDefault().createScoped(VisionScopes.all());
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
                .setApplicationName("foodcabinet")
                .build();


    }

    /**
     * Constructs a new instance of a PictureToText Translator
     * @param a Image that is translated into products
     */
    public PictureToText(ImageView a) {
        picture = a;

    }

    /**
     * Turns the inputted text string into produts that are stored in the Cabinet
     */
    public void TextToProduct() {

        Date current = new Date(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH);
        for(int i =0;i<translated.length();i++)
        {

        }


    }



    /**
     * Sets the invoiced text from the image and stores it in the instance variable defined by the class
     * @param e The invoiced response given from the image to text translation that is to be translated
     */
    public void setText(BatchAnnotateImagesResponse e) {
        List<EntityAnnotation> comp= e.getResponses().get(0).getLabelAnnotations();
        for(EntityAnnotation x:comp)
        {
            translated += String.format( x.getScore().toString(), x.getDescription())+"\n";

        }
    }

    /**
     * Sends an Image request to Google's external Image Recognition library and extracts text from the image
     * @throws IOException Exception thrown when Input Image is not correctly read
     */
    private void callCloudVision() throws IOException {

                HttpTransport httpTransport = new HttpTransport() {
                    @Override
                    protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                        return new MockLowLevelHttpRequest(url);
                    }
                };
                JsonFactory jsonFactory = new JacksonFactory();

                Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                builder.setVisionRequestInitializer(new
                        VisionRequestInitializer(API_KEY));
                Vision vision = builder.build();

                BatchAnnotateImagesRequest bAIR = new BatchAnnotateImagesRequest();
                ArrayList<AnnotateImageRequest> imageRequests = new ArrayList<AnnotateImageRequest>();
                AnnotateImageRequest imageReq = new AnnotateImageRequest();

                ArrayList<Feature> features = new ArrayList<Feature>();
                Feature picToText = new Feature();
                picToText.setType("TEXT_DETECTION");
                features.add(picToText);
                imageReq.setFeatures(features);

                imageRequests.add(imageReq);
                bAIR.setRequests(imageRequests);


                try {
                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(bAIR);
                    annotateRequest.setDisableGZipContent(true);
                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                     setText(response);
                } catch (IOException e) {

                }

            }

}

