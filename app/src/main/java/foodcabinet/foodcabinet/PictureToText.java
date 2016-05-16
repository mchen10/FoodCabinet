package foodcabinet.foodcabinet;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.client.json.JsonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by Sahaj on 5/15/16.
 */
public class PictureToText {

        private String translated;
        private Image picture;
        private Product prod;
<<<<<<< Updated upstream
        /*public static Vision connectToOCR() throws IOException,GeneralSecurityException
=======

    /**
     * Method to send an invoice 
     * @return A Vis
     * @throws IOException
     * @throws GeneralSecurityException
     */
        public static Vision connectToOCR() throws IOException,GeneralSecurityException
>>>>>>> Stashed changes
        {
                GoogleCredential credential = GoogleCredential.getApplicationDefault().createScoped(VisionScopes.all());
                JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
                return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
                        .setApplicationName("foodcabinet")
                        .build();

        }*/


        public PictureToText(Image a)
        {
            picture=a;

        }

        public void TextToProduct()
        {


        }

        public static void main(String args[])
        {
            try{
            connectToOCR();

            }catch (IOException e)
            {

            }catch(GeneralSecurityException e)
            {

            }
        }



<<<<<<< Updated upstream
    }

=======
}
>>>>>>> Stashed changes
