package ca.example.login.service;

import ca.example.login.model.AppUser;
import ca.example.login.model.Role;
import ca.example.login.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
private static final String ADMIN_INVITE_CODE = "1234";
public class RegistrationService {
    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final String adminInviteCode;

    public RegistrationService(AppUserRepository users, PasswordEncoder passwordEncoder,
            @Value("${app.security.admin-invite-code:}") String adminInviteCode) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.adminInviteCode = adminInviteCode;
    }

    @Transactional
    public void register(String email, String username, String password, Role requestedRole, String inviteCode) {
        if (users.existsByUsernameIgnoreCase(username)) {
            throw new IllegalArgumentException("That username is already taken.");
        }
        if (users.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("An account with that email already exists.");
        }

        Role role = Role.USER;
        if (requestedRole == Role.ADMIN) {
             if (!ADMIN_INVITE_CODE.equals(adminInviteCode)) {
            throw new IllegalArgumentException("Invalid admin invite code.");
        }
            role = Role.ADMIN;
        }

        String hash = passwordEncoder.encode(password);
        users.save(new AppUser(email.trim().toLowerCase(), username.trim(), hash, role));
    }
}
