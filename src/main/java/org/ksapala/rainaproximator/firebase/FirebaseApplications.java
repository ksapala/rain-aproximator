package org.ksapala.rainaproximator.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.ksapala.rainaproximator.rest.service.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author krzysztof
 */
@Component
public class FirebaseApplications {

    private FirebaseMessaging firebaseMessaging;

    @Autowired
    private AwsS3Service awsS3Service;


    public FirebaseMessaging getFirebaseMessaging() throws IOException {
        if (this.firebaseMessaging == null) {
            try (InputStream firebaseCredentials = awsS3Service.getFirebaseCredentialsFile()) {
                GoogleCredentials googleCredentials = GoogleCredentials.fromStream(firebaseCredentials);
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(googleCredentials)
//                        .setDatabaseUrl("https://rain-timer.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);

                this.firebaseMessaging = FirebaseMessaging.getInstance();
            }
        }
        return this.firebaseMessaging;
    }
}
