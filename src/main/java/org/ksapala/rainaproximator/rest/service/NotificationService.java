package org.ksapala.rainaproximator.rest.service;

import org.ksapala.rainaproximator.rest.bean.AproximationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

/**
 * @author krzysztof
 */
@Component
public class NotificationService {

    @Autowired
    private FirebaseService firebaseService;

    public void notify(AproximationBean aproximationBean, Predicate<AproximationBean> shouldNotify) {
        if (shouldNotify.test(aproximationBean)) {
            firebaseService.notify(aproximationBean);
        }
    }

}
