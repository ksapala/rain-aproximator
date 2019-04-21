package org.ksapala.rainaproximator.rest.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.debug.Debug;
import org.ksapala.rainaproximator.aproximation.result.Accuracy;
import org.ksapala.rainaproximator.aproximation.scan.Scanner;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.firebase.FirebaseApplications;
import org.ksapala.rainaproximator.rest.bean.AproximationBean;
import org.ksapala.rainaproximator.utils.TimeUtils;
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

import static org.junit.Assert.*;

/**
 * @author krzysztof
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FirebaseServiceTest {

    public final String TOPIC_DEV = "rain-aproximator-dev";

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private Configuration configuration;

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
        AproximationBean aproximationBean = new AproximationBean("Deszcz testowy",
                TimeUtils.timeToString(LocalDateTime.now().plusHours(1), configuration),
                "", Accuracy.of(1.0, 0, 1, 1, 5), new Debug());

        // when
        String response = firebaseService.doNotify(aproximationBean, TOPIC_DEV);

        // then
        assertNotNull(FirebaseApp.getApps());
        assertFalse(FirebaseApp.getApps().isEmpty());
        assertNotNull(FirebaseMessaging.getInstance());
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}