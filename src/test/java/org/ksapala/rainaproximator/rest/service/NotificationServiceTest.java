package org.ksapala.rainaproximator.rest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.rest.bean.AproximationBean;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author krzysztof
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @SpyBean
    private FirebaseService firebaseService;

    @Test
    public void shouldNotify() {
        // given
        AproximationBean aproximationBean = new AproximationBean(NotificationServiceTest.class.toString(), "day",
                null, "",true, null, null);

        // when
        notificationService.notify(aproximationBean, AproximationBean::isNotificationSuggested);

        // then
        Mockito.verify(firebaseService, Mockito.times(1)).notify(aproximationBean);
    }

    @Test
    public void shouldNotNotify() {
        // given
        AproximationBean aproximationBean = new AproximationBean(NotificationServiceTest.class.toString(), "day",
                null, "",false, null, null);

        // when
        notificationService.notify(aproximationBean, AproximationBean::isNotificationSuggested);

        // then
        Mockito.verify(firebaseService, Mockito.never()).notify(aproximationBean);
    }
}