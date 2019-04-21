package org.ksapala.rainaproximator.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.firebase.FirebaseApplications;
import org.ksapala.rainaproximator.rest.bean.AproximationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author krzysztof
 */
@Component
public class FirebaseService {

    private final Logger logger = LoggerFactory.getLogger(FirebaseService.class);

    private static final String APROXIMATION = "aproximation";
    private static final String TAG_APROXIMATION = "tag-aproximation";

    @Autowired
    private Configuration configuration;

    @Autowired
    private FirebaseApplications firebaseApplications;

    public FirebaseService() {
    }

    public void notify(AproximationBean aproximationBean) {
        try {
            doNotify(aproximationBean, configuration.getFirebaseTopic());
        } catch (Exception e) {
            logger.error("Exception while sending notification.", e);
        }
    }

    String doNotify(AproximationBean aproximationBean, String topic) throws FirebaseMessagingException, IOException {
        Notification notification = buildNotification(aproximationBean);
        ObjectMapper mapper = new ObjectMapper();

        String aproximationBeanString = mapper.writeValueAsString(aproximationBean);
        Message message = Message.builder()
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setNotification(
                                        AndroidNotification.builder()
//                                                .setTitle(aproximationBean.getInfo())
//                                                .setBody(aproximationBean.getTime())
                                                .setTag(TAG_APROXIMATION)
                                                .build())
                                .build())
                .putData(APROXIMATION, aproximationBeanString)
                .setNotification(notification)
                .setTopic(topic)
                .build();

        return firebaseApplications.getFirebaseMessaging().send(message);
    }

    private Notification buildNotification(AproximationBean aproximationBean) {
        return new Notification(aproximationBean.getInfo(), aproximationBean.getTime());
    }
}
