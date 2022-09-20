package restrictions;

import base.BaseAPI;


public abstract class UserBase extends BaseAPI {

    public static final String BASE_PATH_USER_PAGE = "users/{page}";
    public static final String BASE_PATH_USER = "users";
    public static final String BASE_PATH_UNKNOWN = "unknown";
    public static final String BASE_PATH_REGISTER = "register";
    public static final String BASE_PATH_LOGIN = "login";
    public static final String BASE_PATH_USER_DELAY = "users/{delay}";

    public UserBase() {

    }

}
