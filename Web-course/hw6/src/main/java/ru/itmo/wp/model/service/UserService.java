package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {
    private static final String PASSWORD_SALT = "1174f9d7bc21e00e9a5fd0a783a44c9a9f73413c";
    private static final String EMAIL_MATCHING = "^(.+)@(\\S+)";
    private static final String LOGIN_MATCHING = "[a-z]+";
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final EventRepository eventRepository = new EventRepositoryImpl();
    public void validateRegistration(User user, String password, String passwordConfirmation) throws ValidationException {
        //User validation
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches(LOGIN_MATCHING)) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }
        //Password validation
        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.trim().isEmpty()) {
            throw new ValidationException("Password can't have only spaces");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 64) {
            throw new ValidationException("Password can't be longer than 64 characters");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new ValidationException("Password must be matching with Confirmation Password.");
        }
        //Email validation
        if (Strings.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        if (!user.getEmail().matches(EMAIL_MATCHING)) {
            throw new ValidationException("Email must contain one character @");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
        }

    }
    public void createEvent(long userId, Event.types type) {
        Event event = new Event();
        event.setUserId(userId);
        event.setType(type);
        eventRepository.save(event);
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User validateEnter(String loginOrEmail, String password) throws ValidationException {
        User user;
        if (loginOrEmail.matches(EMAIL_MATCHING)) {
            user = userRepository.findByEmailAndPasswordSha(loginOrEmail, getPasswordSha(password));
        } else if (loginOrEmail.matches(LOGIN_MATCHING)) {
            user = userRepository.findByLoginAndPasswordSha(loginOrEmail, getPasswordSha(password));
        } else {
            throw new ValidationException("Login or Email is not matching required pattern!");
        }
        if (user == null) {
            throw new ValidationException("Invalid login/Email or password");
        }
        return user;
//        if (!patternMatchingLoginOrEmail(loginOrEmail)) {
//            throw new ValidationException("Login or Email is not matching required pattern!");
//        }
//        User user = userRepository.findByLoginAndPasswordSha(loginOrEmail, getPasswordSha(password));
//        if (user == null) {
//            user = userRepository.findByEmailAndPasswordSha(loginOrEmail, getPasswordSha(password));
//            if (user == null) {
//                throw new ValidationException("Invalid login/Email or password");
//            }
//        }
//        return user;
    }

    private boolean patternMatchingLoginOrEmail(String loginOrEmail) {
        return loginOrEmail.matches(LOGIN_MATCHING) || loginOrEmail.matches(EMAIL_MATCHING);
    }

    public int findCount() {
        return userRepository.findCount();
    }

}
