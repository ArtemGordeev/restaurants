package com;

import com.model.Role;
import com.model.User;
import com.web.json.JsonUtil;

import java.util.Collections;
import java.util.Date;

import static com.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "registered", "votes", "password");

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

    public static final int USER_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ + 1;
    public static final int ADMIN_ID = START_SEQ + 2;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User USER2 = new User(USER2_ID, "User2", "user2@yandex.ru", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }
}
