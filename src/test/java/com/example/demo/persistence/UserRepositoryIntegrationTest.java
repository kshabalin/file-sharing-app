package com.example.demo.persistence;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.testUtils.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-17
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenEmailIsNull_thenExceptionThrown() {
        assertThrows(PersistenceException.class, () -> {
            User user = UserUtils.createUser(null);
            entityManager.persist(user);
            entityManager.flush();
        });
    }

    @Test
    public void whenEmailIsInvalid_thenExceptionThrown() {
        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            User user = UserUtils.createUser("email");
            entityManager.persist(user);
            entityManager.flush();
        });

        String expectedMessage = "Email should be valid";
        String actualMessage = exception.getMessage();

        System.out.println("actualMessage: " + actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        User user = UserUtils.createUser("email@mail.com");
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByEmail(user.getEmail());

        assertThat(found.getEmail()).isEqualTo(user.getEmail());
        assertThat(found.getOwnedFiles()).isEqualTo(null);
        assertThat(found.getSharedFiles()).isEqualTo(null);
    }
}
