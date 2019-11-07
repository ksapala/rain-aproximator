package org.ksapala.rainaproximator.rest.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.rest.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author krzysztof
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FirebaseDatabaseServiceTest {


    @Autowired
    private FirebaseDatabaseService firebaseDatabaseService;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldGetUsers() {
        // given

        // when
        List<User> users = firebaseDatabaseService.getUsers();

        // then
        assertNotNull(users);
        if (!users.isEmpty()) {
            User user = users.get(0);
            assertNotNull(user.getId());;
            assertTrue(user.getLatitude() > 0);
            assertTrue(user.getLongitude() > 0);
        }
    }
}