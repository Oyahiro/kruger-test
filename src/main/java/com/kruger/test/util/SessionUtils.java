package com.kruger.test.util;

import com.kruger.test.enitity.PrimaryUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

public class SessionUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static PrimaryUser getPrimaryUser() {
        Authentication authentication = getAuthentication();
        return Objects.nonNull(authentication) && !authentication.getPrincipal().equals("anonymousUser")
                ? (PrimaryUser) authentication.getPrincipal() : null;
    }

    public static Map<String, Object> getSessionMap() {
        final PrimaryUser primaryUser = getPrimaryUser();
        return (Objects.nonNull(primaryUser))
                ? primaryUser.getProperties() : new HashMap<>();
    }

    public static <T> T getValueFromSession(String key) {
        Map<String, Object> sessionValues = getSessionMap();
        return (Objects.nonNull(sessionValues))
                ? (T) sessionValues.get(key) : null;
    }

}
