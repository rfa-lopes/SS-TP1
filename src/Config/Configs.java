package Config;

public class Configs {

    //Upgrades to TP2:
    //Use Properties and config.properties.

    public static final String[] API_ADMIN = {"/create","/remove","/get","/lock","/unlock","/list","/loggers"};

    public static final String[] API_ACCOUNT = {"/home","/change","/logout"};

    public static final String[] API_PUBLIC = {"/login"};

    public static final long JWT_TIME_MINUTES = 10;

    public static final long REFRESH_TOKEN_TIME_HOURS = 20; //Nota final

    public static final int MIN_PASSWORD_SIZE = 8;

    public static final boolean ENCRYPT_TOKENS = true;

    public static final boolean LOGS = true;

    public static final String ROOT_USERNAME = "root";

    public static final String ROOT_PASSWORD = "toor";

    public static final String JWT_KEY_VALUE = "password_ultra_S$cret@--123456";

    public static final String SYM_ENCRYPTION_KEY = "FCT/UNLrocks!!di";

    public static final long TRIES_TIME = 5;

    public static final boolean ENABLE_TLS = true;

    //true = MariaDB
    //false = SQLite
    public static final boolean USE_MARIA = false;

    public static final String MARIA_USER = "root";

    public static final String MARIA_PASSWORD = "mariadb";

    public static final String DATE_PATTERN = "dd-MM-yyyy HH:mm:ss";

}
