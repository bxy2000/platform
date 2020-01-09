package com.boxy.platform.service;

import com.boxy.platform.domain.*;
import com.boxy.platform.repository.*;
import com.boxy.platform.security.AuthoritiesConstants;
import com.boxy.platform.security.SecurityUtils;
import com.boxy.platform.service.dto.MenuDTO;
import com.boxy.platform.service.dto.UserDTO;
import com.boxy.platform.service.dto.UserExtendsCriteria;
import com.boxy.platform.service.dto.UserExtendsDTO;
import com.boxy.platform.service.mapper.UserMapper;
import com.boxy.platform.service.util.RandomUtil;
import com.boxy.platform.web.rest.vm.UserExtendsVM;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing UserExtends.
 */
@Service
@Transactional
public class UserExtendsServiceExtends {

    private final Logger log = LoggerFactory.getLogger(UserExtendsServiceExtends.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserExtendsRepository userExtendsRepository;
    private final UserExtendsQueryService userExtendsQueryService;
    private final MenuRepository menuRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final CacheManager cacheManager;
    private final UserMapper userMapper;

    public UserExtendsServiceExtends(
        UserRepository userRepository,
        RoleRepository roleRepository,
        UserExtendsRepository userExtendsRepository,
        UserExtendsQueryService userExtendsQueryService,
        MenuRepository menuRepository,
        PasswordEncoder passwordEncoder,
        AuthorityRepository authorityRepository,
        CacheManager cacheManager,
        UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userExtendsRepository = userExtendsRepository;
        this.userExtendsQueryService = userExtendsQueryService;
        this.menuRepository = menuRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.userMapper = userMapper;
    }

    /**
     * Get all the userExtends.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserExtends> findAll(Pageable pageable) {
        log.debug("Request to get all UserExtends");

        return userExtendsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one userExtends by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserExtends> findOne(Long id) {
        log.debug("Request to get UserExtends : {}", id);
        return userExtendsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the userExtends by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExtends : {}", id);
        UserExtends userExtends = userExtendsRepository.findById(id).orElse(null);
        // 删除用户扩展
        userExtendsRepository.deleteById(id);
        // 删除用户
        userRepository.findOneByLogin(userExtends.getUser().getLogin()).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
        });
    }

    public UserExtends register(UserExtendsVM userExtendsVM) {
        User newUser = new User();

        // 设置默认权限 ROLE_USER
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);

        // 密码加密
        String encryptedPassword = passwordEncoder.encode(userExtendsVM.getPassword());

        // *** 用户名（用于系统登录）
        newUser.setLogin(userExtendsVM.getUser().getLogin());
        // *** 密码
        newUser.setPassword(encryptedPassword);
        // 姓名
        newUser.setFirstName(userExtendsVM.getUsername());
        newUser.setLastName(userExtendsVM.getUsername());
        newUser.setEmail(userExtendsVM.getUser().getEmail());
        newUser.setImageUrl(userExtendsVM.getUser().getImageUrl());
        newUser.setLangKey(userExtendsVM.getUser().getLangKey());

        // *** 当前用户默认为激活状态
        newUser.setActivated(true);
        // 激活码
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        // *** 用户权限
        newUser.setAuthorities(authorities);

        newUser = userRepository.save(newUser);

        // this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);

        UserExtends userExtends = userExtendsVM.toUserExtends();
        userExtends.setUser(newUser);


        userExtends = userExtendsRepository.save(userExtends);
        log.debug("Created Information for UserExtends: {}", userExtends);

        Set<Role> roles = new HashSet<>();
        for (Role role : userExtends.getRoles()) {
            roleRepository.findById(role.getId()).ifPresent(u -> {
                // u.setMenus(null);
                roles.add(u);
            });
        }
        userExtends.setRoles(roles);
        return userExtends;
    }

    public UserExtends updateUserExtends(UserExtendsVM userExtendsVM) {
        UserDTO userDTO = userMapper.userToUserDTO(userExtendsVM.getUser());

        // 设置用户权限
        // Set<Authority> authorities = new HashSet<>();
        // authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);

        Optional<UserDTO> result = Optional.of(userRepository
            .findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(true);
                user.setLangKey(userDTO.getLangKey());
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                userExtendsVM.setUser(user);
                return user;
            })
            .map(UserDTO::new);

        return userExtendsRepository.save(userExtendsVM.toUserExtends());
    }

    public boolean comparePassword(String password) {
        final Boolean[] result = new Boolean[]{true};
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                result[0] = passwordEncoder.matches(password, currentEncryptedPassword);
                log.debug("Compare password for User: {}", user);
            });

        return result[0];
    }

    public Optional<User> resetPassword(String newPassword, String login) {
        log.debug("Reset user password for login {}", login);
        return userRepository.findOneByLogin(login)
            .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    this.clearUserCaches(user);
                    return user;
                });
    }

    /**
     * 清除缓存
     *
     * @param user
     */
    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }

    @Transactional(readOnly = true)
    public UserExtendsDTO findUserExtendsByLogin(String login) {
        UserExtendsDTO result = null;
        // 获取用户id
        Long userID = userRepository.findOneByLogin(login)
            .map(user -> user.getId()).orElse(null);

        if (userID == null) return result;

        UserExtendsCriteria criteria = new UserExtendsCriteria();

        LongFilter userIDFilter = new LongFilter();
        userIDFilter.setEquals(userID);

        criteria.setUserId(userIDFilter);

        List<UserExtends> userExtendsList = userExtendsQueryService.findByCriteria(criteria);

        if (userExtendsList == null || userExtendsList.size() == 0) {
            return result;
        }

        Long userExtendsID = userExtendsList.get(0).getId();

        // 获得用户扩展
        UserExtends userExtends = userExtendsRepository.findOneWithEagerRelationships(userExtendsID).orElse(null);

        result = new UserExtendsDTO(userExtends);

        return result;
    }

    @Transactional(readOnly = true)
    public List<MenuDTO> findMenusByLogin(String login) {
        log.debug("Request to get Menus by login : {}", login);

        List<MenuDTO> result = new ArrayList<>();

        // 获取用户id
        Long userID = userRepository.findOneByLogin(login)
            .map(user -> user.getId()).orElse(null);

        if (userID == null) return result;

        UserExtendsCriteria criteria = new UserExtendsCriteria();

        LongFilter userIDFilter = new LongFilter();
        userIDFilter.setEquals(userID);

        criteria.setUserId(userIDFilter);

        List<UserExtends> userExtendsList = userExtendsQueryService.findByCriteria(criteria);

        if (userExtendsList == null || userExtendsList.size() == 0) {
            return result;
        }

        Long userExtendsID = userExtendsList.get(0).getId();

        // 获得用户扩展
        UserExtends userExtends = userExtendsRepository.findOneWithEagerRelationships(userExtendsID).orElse(null);

        List<Menu> menus = new ArrayList<>();
        List<Menu> menusBack = new ArrayList<>();

        userExtends.getRoles().forEach(r -> {
            r.getMenus().forEach(m -> {
                if (!menus.contains(m)) {
                    menus.add(m);
                    menusBack.add(m);
                }
            });
        });
        menusBack.forEach(u -> {
            addParents(menus, u);
        });

        buildTree(result, menus, null);

        return result;
    }

    private void addParents(List<Menu> menus, Menu menu) {
        while (menu.getParent() != null && menu.getParent().getId() != null) {

            Menu parent = menuRepository.findById(menu.getParent().getId()).orElse(null);

            if (!menus.contains(parent)) {
                menus.add(parent);
            }

            menu = parent;
        }
    }

    private void buildTree(List<MenuDTO> source, List<Menu> target, Long parentID) {
        target.stream()
            .filter(u ->
                parentID == null && u.getParent() == null ||
                    u.getParent() != null && u.getParent().getId() == parentID)
            .sorted(Comparator.comparing(Menu::getId))
            .forEach(u -> {
                MenuDTO uDTO = new MenuDTO(u);
                source.add(uDTO);
                buildTree(uDTO.getChildren(), target, u.getId());
            });
    }
}
