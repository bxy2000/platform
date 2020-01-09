package com.boxy.platform.web.rest;

import com.boxy.platform.domain.User;
import com.boxy.platform.domain.UserExtends;
import com.boxy.platform.repository.UserRepository;
import com.boxy.platform.service.UserExtendsQueryService;
import com.boxy.platform.service.UserExtendsServiceExtends;
import com.boxy.platform.service.dto.UserDTO;
import com.boxy.platform.service.dto.UserExtendsCriteria;
import com.boxy.platform.service.mapper.UserMapper;
import com.boxy.platform.web.rest.errors.BadRequestAlertException;
import com.boxy.platform.web.rest.errors.EmailAlreadyUsedException;
import com.boxy.platform.web.rest.errors.LoginAlreadyUsedException;
import com.boxy.platform.web.rest.vm.UserExtendsVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserExtends.
 */
@RestController
@RequestMapping("/api/ext")
public class UserExtendsResourceExtends {

    private final Logger log = LoggerFactory.getLogger(UserExtendsResourceExtends.class);

    private static final String ENTITY_NAME = "userExtends";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserRepository userRepository;

    private final UserExtendsServiceExtends userExtendsServiceExtends;

    private final UserExtendsQueryService userExtendsQueryService;

    private final UserMapper userMapper;

    public UserExtendsResourceExtends(
        UserRepository userRepository,
        UserExtendsServiceExtends userExtendsServiceExtends,
        UserExtendsQueryService userExtendsQueryService,
        UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userExtendsServiceExtends = userExtendsServiceExtends;
        this.userExtendsQueryService = userExtendsQueryService;
        this.userMapper = userMapper;
    }

    /**
     * POST  /user-extends : Create a new userExtends.
     *
     * @param userExtendsVM the userExtends to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExtends, or with status 400 (Bad Request) if the userExtends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-extends")
    public ResponseEntity<UserExtends> createUserExtends(@Valid @RequestBody UserExtendsVM userExtendsVM) throws URISyntaxException {
        log.debug("REST request to save create UserExtends : {}", userExtendsVM);
        if (userExtendsVM.getId() != null) {
            throw new BadRequestAlertException("A new userExtends cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if (userExtendsVM.getUser() == null) {
            throw new BadRequestAlertException("The user of userExtends cannot be null", ENTITY_NAME, "user==null");
        }

        if (userExtendsVM.getUser().getId() != null) {
            throw new BadRequestAlertException("A new user Of the userExtends cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userRepository.findOneByLogin(userExtendsVM.getUser().getLogin().toLowerCase()).ifPresent(u -> {
            throw new LoginAlreadyUsedException();
        });
        userRepository.findOneByEmailIgnoreCase(userExtendsVM.getUser().getEmail()).ifPresent(u -> {
            throw new EmailAlreadyUsedException();
        });

        UserExtends result = userExtendsServiceExtends.register(userExtendsVM);

        return ResponseEntity.created(new URI("/api/ext/user-extends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);

    }

    /**
     * PUT  /user-extends : Updates an existing userExtends.
     *
     * @param userExtendsVM the userExtends to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExtends,
     * or with status 400 (Bad Request) if the userExtends is not valid,
     * or with status 500 (Internal Server Error) if the userExtends couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-extends")
    public ResponseEntity<UserExtends> updateUserExtends(@RequestBody UserExtendsVM userExtendsVM) throws URISyntaxException {
        log.debug("REST request to update UserExtends : {}", userExtendsVM);
        if (userExtendsVM.getId() == null) {
            return createUserExtends(userExtendsVM);
        }

        UserDTO userDTO = this.userMapper.userToUserDTO(userExtendsVM.getUser());

        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }

        UserExtends result = userExtendsServiceExtends.updateUserExtends(userExtendsVM);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userExtendsVM.getId().toString()))
            .body(result);
    }

    /**
     * @param pageable
     * @return
     */
    @GetMapping("/user-extends")
    public ResponseEntity<List<UserExtends>> getAllUserExtends(UserExtendsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserExtends by admin: {}", criteria);
        Page<UserExtends> page = userExtendsServiceExtends.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-extends/:id : get the "id" userExtends.
     *
     * @param id the id of the userExtends to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExtends, or with status 404 (Not Found)
     */
    @GetMapping("/user-extends/{id}")
    public ResponseEntity<UserExtends> getUserExtends(@PathVariable Long id) {
        log.debug("REST request to get UserExtends : {}", id);
        Optional<UserExtends> userExtends = userExtendsServiceExtends.findOne(id);
        return ResponseUtil.wrapOrNotFound(userExtends);
    }

    /**
     * DELETE  /user-extends/:id : delete the "id" userExtends.
     *
     * @param id the id of the userExtends to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-extends/{id}")
    public ResponseEntity<Void> deleteUserExtends(@PathVariable Long id) {
        log.debug("REST request to delete UserExtends : {}", id);
        userExtendsServiceExtends.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/user-extends/reset-password")
    public void resetPassword(@RequestBody String login) {
        log.debug("REST request to reset password by login: {}", login);
        userExtendsServiceExtends.resetPassword("123456", login);
    }
}
