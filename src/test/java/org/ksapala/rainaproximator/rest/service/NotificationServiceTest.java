package org.ksapala.rainaproximator.rest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.rest.answer.AproximationAnswer;
import org.ksapala.rainaproximator.rest.user.User;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author krzysztof
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @SpyBean
    private FirebaseMessagingService firebaseService;

    @Test
    public void shouldNotify() {
        // given
        AproximationAnswer aproximationAnswer = new AproximationAnswer(NotificationServiceTest.class.toString(), "day",
                null, "",true, null, null);
        User dummyUser = new User("dummy", 0, 0);

        // when
        notificationService.notify(dummyUser, aproximationAnswer);

        // then
        Mockito.verify(firebaseService, Mockito.times(1)).notify(dummyUser, aproximationAnswer);
    }

    @Test
    public void shouldNotNotify() {
        // given
        AproximationAnswer aproximationAnswer = new AproximationAnswer(NotificationServiceTest.class.toString(), "day",
                null, "",false, null, null);
        User dummyUser = new User("dummy", 0, 0);

        // when
        notificationService.notify(dummyUser, aproximationAnswer);

        // then
        Mockito.verify(firebaseService, Mockito.never()).notify(dummyUser, aproximationAnswer);
    }
}