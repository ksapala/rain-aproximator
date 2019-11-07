package org.ksapala.rainaproximator.rest.service;

import org.ksapala.rainaproximator.rest.answer.AproximationAnswer;
import org.ksapala.rainaproximator.rest.answer.ScanAnswer;
import org.ksapala.rainaproximator.rest.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author krzysztof
 */
@Component
public class RainTimerService {

    private final Logger logger = LoggerFactory.getLogger(RainTimerService.class);

    @Autowired
    private RainAproximatorService rainAproximatorService;

    @Autowired
    private ScanService scanService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FirebaseDatabaseService firebaseDatabaseService;

    public void aproximateForUsers() {
        logger.info("All operations start.");
        long start = System.currentTimeMillis();

        ScanAnswer scan = scanService.scan();

        List<User> users = firebaseDatabaseService.getUsers();

        users.parallelStream()
                .forEach(this::aproximateForUser);

        long end = System.currentTimeMillis();
        logger.info("All operations end. Time: " + (end - start));
    }

    private void aproximateForUser(User user) {
        logger.info("Aproximating for user: " + user);
        AproximationAnswer aproximationAnswer = rainAproximatorService.aproximate(user.getLatitude(), user.getLongitude());
        notificationService.notify(user, aproximationAnswer);
    }
}
