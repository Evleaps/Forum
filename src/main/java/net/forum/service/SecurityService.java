package net.forum.service;

/**
 * Service for Security.
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String login, String password);
}
