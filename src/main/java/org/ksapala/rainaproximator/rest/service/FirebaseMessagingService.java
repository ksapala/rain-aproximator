package org.ksapala.rainaproximator.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.firebase.FirebaseApplications;
import org.ksapala.rainaproximator.rest.answer.AproximationAnswer;
import org.ksapala.rainaproximator.rest.user.User;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author krzysztof
 */
@Component
public class FirebaseMessagingService {


    private final Logger logger = LoggerFactory.getLogger(FirebaseMessagingService.class);

    @Autowired
    private Configuration configuration;

    @Autowired
    private FirebaseApplications firebaseApplications;

    @Autowired
    private ObjectMapper objectMapper;

    public FirebaseMessagingService() {
    }

    public void notify(User user, AproximationAnswer aproximationAnswer) {
        try {
            doNotify(user, aproximationAnswer);
//        } catch (FirebaseMessagingException fme) {
//            logger.error("fme.", fme);
//            fme.getErrorCode()
            // registration-token-not-registered
        } catch (Exception e) {
            logger.error("Exception while sending notification.", e);
        }
    }

    String doNotify(User user, AproximationAnswer aproximationAnswer) throws FirebaseMessagingException, IOException {
        Notification notification = buildNotification(aproximationAnswer);

        String aproximationAnswerString = objectMapper.writeValueAsString(aproximationAnswer);
        Message message = Message.builder()
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setNotification(
                                        AndroidNotification.builder()
                                                .setTag(configuration.getFirebase().getMessageTag())
                                                .build())
                                .build())
                .putData(configuration.getFirebase().getMessageData(), aproximationAnswerString)
                .setNotification(notification)
                .setToken(user.getId())
                .build();

        return firebaseApplications.getFirebaseMessaging().send(message);
    }

    private Notification buildNotification(AproximationAnswer aproximationAnswer) {
        if (aproximationAnswer.getDay().isEmpty()) {
            return new Notification(aproximationAnswer.getInfo(), aproximationAnswer.getTime().toString());
        }
        String time = TimeUtils.timeToString(aproximationAnswer.getTime(), configuration.getMobileTimeFormat());
        return new Notification(aproximationAnswer.getInfo(), aproximationAnswer.getDay() + " " + time);
    }

}
