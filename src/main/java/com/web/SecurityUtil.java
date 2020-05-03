package com.web;


import com.AuthorizedUser;
import com.model.AbstractBaseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {

//    private static int id = AbstractBaseEntity.START_SEQ;
//
//    private SecurityUtil() {
//    }
//
//    public static int authUserId() {
//        return id;
//    }
//
//    public static void setAuthUserId(int id) {
//        SecurityUtil.id = id;
//    }

    private SecurityUtil() {
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static int authUserId() {
        return get().getUserTo().id();
    }
}