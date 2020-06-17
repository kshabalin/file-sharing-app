package com.example.demo.persistence;

import com.example.demo.entity.File;
import com.example.demo.entity.User;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.testUtils.FileUtils;
import com.example.demo.testUtils.UserUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-17
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class FileRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void whenUserIdIsNull_thenExceptionThrown() {
        assertThrows(PersistenceException.class, () -> {
            File file = FileUtils.createFile("file.txt", null);
            entityManager.persist(file);
            entityManager.flush();
        });
    }

    @Test
    public void whenFileNameIsNull_thenExceptionThrown() {
        assertThrows(PersistenceException.class, () -> {
            File file = FileUtils.createFile(null, null);
            entityManager.persist(file);
            entityManager.flush();
        });
    }

    @Test
    public void whenFileIsValid_thenExceptionThrown() {
        User user = UserUtils.createUser("email@mail.com");
        entityManager.persist(user);
        entityManager.flush();

        User foundUser = userRepository.findByEmail(user.getEmail());

        File file = FileUtils.createFile("file.txt", foundUser.getId());
        user.setOwnedFiles(Arrays.asList(file));
        entityManager.persist(user);
        entityManager.flush();

        List<File> files = fileRepository.findAll();
        assertThat(files.size()).isEqualTo(1);

        assertThat(foundUser.getOwnedFiles().size()).isEqualTo(1);
        assertThat(foundUser.getOwnedFiles().get(0).getName()).isEqualTo(files.get(0).getName());
        assertThat(foundUser.getOwnedFiles().get(0).getId()).isEqualTo(files.get(0).getId());
    }

    @Test
    public void whenFileIsShared_thenExceptionThrown() {
        User user1 = UserUtils.createUser("email1@mail.com");
        User user2 = UserUtils.createUser("email2@mail.com");
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();

        User foundUser1 = userRepository.findByEmail(user1.getEmail());

        File file = FileUtils.createFile("file.txt", foundUser1.getId());
        foundUser1.setOwnedFiles(Arrays.asList(file));
        entityManager.persist(user1);
        entityManager.flush();

        File foundFile = foundUser1.getOwnedFiles().get(0);
        User foundUser2 = userRepository.findByEmail(user2.getEmail());
        foundUser2.setSharedFiles(Arrays.asList(foundFile));
        entityManager.persist(foundUser2);
        entityManager.flush();

        foundUser2 = userRepository.findByEmail(user2.getEmail());

        assertThat(foundUser2.getSharedFiles().size()).isEqualTo(1);
        File sharedFile = foundUser2.getSharedFiles().get(0);
        assertThat(sharedFile.getName()).isEqualTo(file.getName());
    }
}
