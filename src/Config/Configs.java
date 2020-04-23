package Config;

public class Configs {

    public static final String[] API_ADMIN = {"/create","/remove","/get","/lock","/unlock"};

    public static final String[] API_ACCOUNT = {"/home","/change","/logout"};

    public static final String[] API_PUBLIC = {"/login"};

    public static final long JWT_TIME_MINUTES = 10;

    public static final long REFRESH_TOKEN_TIME_HOURS = 20;

    public static final int MIN_PASSWORD_SIZE = 8;

    public static final boolean ENCRYPT_TOKENS = false;

    public static final boolean LOGS = true;

    public static final String ROOT_USERNAME = "root";

    public static final String ROOT_PASSWORD = "toor";

    public static final String JWT_KEY_VALUE = "password_ultra_S$cret@--123456";

    public static final String SYM_ENCRYPTION_KEY = "FCT/UNLrocks!!di";

}
