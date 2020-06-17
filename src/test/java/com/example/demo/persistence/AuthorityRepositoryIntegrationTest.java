package com.example.demo.persistence;

import com.example.demo.entity.Authority;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.testUtils.AuthorityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * @author Konstantin Shabalin
 * @since 2020-06-17
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthorityRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void whenUserNameIsNull_thenExceptionThrown() {
        assertThrows(PersistenceException.class, () -> {
            Authority authority = AuthorityUtils.createAuthority(null, "USER");
            entityManager.persist(authority);
            entityManager.flush();
        });
    }

    @Test
    public void whenAuthorityIsNull_thenExceptionThrown() {
        assertThrows(PersistenceException.class, () -> {
            Authority authority = AuthorityUtils.createAuthority("mail@mail.com", null);
            entityManager.persist(authority);
            entityManager.flush();
        });
    }

    @Test
    public void whenFindByUserName_thenReturnAuthority() {
        Authority authority = AuthorityUtils.createAuthority("mail@mail.com", "USER");
        entityManager.persist(authority);
        entityManager.flush();

        Authority found = authorityRepository.findByUsername(authority.getUsername());

        assertThat(found.getUsername()).isEqualTo(authority.getUsername());
        assertThat(found.getAuthority()).isEqualTo(authority.getAuthority());
    }
}
