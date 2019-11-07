package org.ksapala.rainaproximator.rest.service;

import org.ksapala.rainaproximator.rest.answer.AproximationAnswer;
import org.ksapala.rainaproximator.rest.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author krzysztof
 */
@Component
public class NotificationService {

    @Autowired
    private FirebaseMessagingService firebaseService;

    public void notify(User user, AproximationAnswer aproximationAnswer) {
        if (aproximationAnswer.isNotificationSuggested()) {
            firebaseService.notify(user, aproximationAnswer);
        }
    }

}
