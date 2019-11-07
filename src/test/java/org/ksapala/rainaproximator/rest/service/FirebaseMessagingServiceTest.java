package org.ksapala.rainaproximator.rest.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.debug.Debug;
import org.ksapala.rainaproximator.aproximation.result.Accuracy;
import org.ksapala.rainaproximator.rest.answer.AproximationAnswer;
import org.ksapala.rainaproximator.rest.user.User;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author krzysztof
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FirebaseMessagingServiceTest {

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @Autowired
    private FirebaseDatabaseService firebaseDatabaseService;

    @MockBean
    private AwsS3Service awsS3Service;


    @Before
    public void setUp() throws FileNotFoundException {
        InputStream firebaseCredentialsFile = new FileInputStream("src/test/resources/rain-timer-firebase.json");
        Mockito.when(awsS3Service.getFirebaseCredentialsFile()).thenReturn(firebaseCredentialsFile);
    }

    @Test
    public void shouldNotify() throws IOException, FirebaseMessagingException {
        // given
        AproximationAnswer aproximationAnswer = new AproximationAnswer("Deszcz testowy",
            "Dziś", LocalDateTime.now().plusHours(1),
                "", true, Accuracy.of(1.0, 0, 1, 1,
                5), new Debug());

        List<User> users = firebaseDatabaseService.getUsers();

        User lastUser = users.get(0); // this depends how many users is un firebase db

        // when
        String response = firebaseMessagingService.doNotify(lastUser, aproximationAnswer);

        // then
        assertNotNull(FirebaseApp.getApps());
        assertFalse(FirebaseApp.getApps().isEmpty());
        assertNotNull(FirebaseMessaging.getInstance());
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}