package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.DisabledCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.UserRepository;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserCredentials userCredentials) {
        User user = new User();
        user.setLogin(userCredentials.getLogin());
        userRepository.save(user);
        userRepository.updatePassword(user.getId(), userCredentials.getLogin(), userCredentials.getPassword());
        return user;
    }

    public boolean isLoginFree(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public Long parseId(String id) throws ValidationException {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
//            throw new ValidationException("Error!!!!");
            return null;
        }
    }

    public void setDisabled(long id, String disabled) throws ValidationException {
        boolean mode = getDisStatus(disabled);
        userRepository.setDisabled(id, mode);
    }

    private boolean getDisStatus(String status) throws ValidationException {
        return switch(status) {
            case "disable" -> true;
            case "enable" -> false;
            default -> throw new ValidationException("Unexpected validate error!");
        };
    }

    public String makeAction(User user, User userSession, DisabledCredentials disabledForm) throws ValidationException {
        if (userSession == null) {
            return "If you want to change state of users, you status must be enabled, not disabled";
        }
        User sessionUser = findById(userSession.getId());
        if (sessionUser == null || sessionUser.isDisabled()) {
            return "If you want to change state of users, you status must be enabled, not disabled";
        } else if (!user.equals(sessionUser)) {
            setDisabled(disabledForm.getUserId(), disabledForm.getDisabled());
            return "Mode of " + user.getLogin() + " has been changed!";
        } else {
            return "You cant change your Mode!";
        }
    }
}
