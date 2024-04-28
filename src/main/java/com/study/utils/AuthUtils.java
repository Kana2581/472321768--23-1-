package com.study.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Collection;

public class AuthUtils {
    public static boolean isAdmin(Collection<? extends GrantedAuthority> authorities)
    {

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_admin")) {
                return true;
            }
        }

        return false;
    }
    public static boolean isMe(int uid, String sessionUid)
    {

        return uid==Integer.parseInt(sessionUid);
    }
    public static boolean isMe(int uid, int sessionUid)
    {
        return uid==sessionUid;
    }
}
