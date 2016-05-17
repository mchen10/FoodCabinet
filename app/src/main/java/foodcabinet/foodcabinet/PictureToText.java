package foodcabinet.foodcabinet;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.AndroidCharacter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.Json.*;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.client.json.JsonFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * Created by Sahaj on 5/15/16.
 */
public class PictureToText {
    private static String API_KEY = "AIzaSyCcuM1ltlLLmp1woOqsyXZpjsL0qHq_9eU";
    private String translated;
    private ImageView picture;
    private Product prod;

    /**
     * Method connects to Google's External Library, Cloud Vision
     *
     * @return A Vis
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


    public PictureToText(ImageView a) {
        picture = a;

    }

    public void TextToProduct() {

    }

    public static void main(String args[]) {
        try {
            connectToOCR();

        } catch (IOException e) {

        } catch (GeneralSecurityException e) {

        }
    }

    public String setText(BatchAnnotateImagesResponse e) {

        return "";
    }


    private void callCloudVision(final Bitmap bitmap) throws IOException {

        new AsyncTask<Object, Void, String>() {
            protected String doInBackground(Object... params) {
                HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                JsonFactory jsonFactory = new GsonFactory();

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
                    return setText(response);
                } catch (IOException e) {

                }

                return "";
            }
        };

    }

}

