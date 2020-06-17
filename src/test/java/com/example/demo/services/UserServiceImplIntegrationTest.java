package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.testUtils.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplIntegrationTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void whenValidEmail_thenUserShouldBeFound() {
        User user = UserUtils.createUser("mail@email.com");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User foundUser = userService.findUserByEmail(user.getEmail());
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenValidEmail_thenUserShouldBeSaved() {
        User user = UserUtils.createUser("mail@email.com");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User created = userService.saveUser(user);

        assertThat(created.getEmail()).isEqualTo(user.getEmail());
        assertThat(created.getId()).isEqualTo(user.getId());
        assertThat(created.getEnabled()).isEqualTo(user.getEnabled());
        assertThat(created.getPassword()).isEqualTo(user.getPassword());
        assertThat(created.getOwnedFiles()).isEqualTo(user.getOwnedFiles());
        assertThat(created.getSharedFiles()).isEqualTo(user.getSharedFiles());
    }
}
