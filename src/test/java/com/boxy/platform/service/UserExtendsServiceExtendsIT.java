package com.boxy.platform.service;

import com.boxy.platform.PlatformApp;
import com.boxy.platform.config.Constants;
import com.boxy.platform.domain.User;
import com.boxy.platform.repository.UserRepository;
import com.boxy.platform.service.dto.UserDTO;
import com.boxy.platform.service.util.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Integration tests for {@link UserService}.
 */
@SpringBootTest(classes = PlatformApp.class)
@Transactional
public class UserExtendsServiceExtendsIT {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
    }

    @Test
    @Transactional
    public void assertThatUserMustExistToResetPassword() {
        String password = passwordEncoder.encode("admin!@#");

        //System.out.println(password);
    }
}
