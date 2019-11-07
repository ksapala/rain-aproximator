package org.ksapala.rainaproximator.rest.service;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.rest.user.User;
import org.ksapala.rainaproximator.json.UserJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author krzysztof
 */

@Component
public class FirebaseDatabaseService {

    private final Logger logger = LoggerFactory.getLogger(FirebaseDatabaseService.class);

    @Autowired
    private Configuration configuration;

    public List<User> getUsers() {
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<Map<String, UserJson>> type = new ParameterizedTypeReference<Map<String, UserJson>>() {};

        Optional<Map<String, UserJson>> result = Optional.ofNullable(
                restTemplate.exchange(configuration.getFirebase().getUsersUrl(), HttpMethod.GET, null, type).getBody()
        );

        List<User> users = result.map(map -> map.entrySet().stream()
                .map(e -> new User(e.getKey(), e.getValue().getLatitude(), e.getValue().getLongitude()))
                .collect(Collectors.toList())).orElse(new ArrayList<>());

        logger.debug("Users count: " + users.size());
        return users;
    }




}
