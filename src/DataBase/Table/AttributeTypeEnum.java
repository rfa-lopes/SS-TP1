package DataBase.Table;

public enum AttributeTypeEnum {

    INT("INT"),
    INTEGER("INTEGER"),
    TINYINT("TINYINT"),
    SMALLINT("SMALLINT"),
    MEDIUMINT("MEDIUMINT"),
    BIGINT("BIGINT"),
    UNSIGNED_BIG_INT("UNSIGNED BIG INT"),
    INT2("INT2"),
    INT8("INT8"),

    CHARACTER("CHARACTER(20)"),
    VARCHAR("VARCHAR(255)"),
    VARYING_CHARACTER("VARYING CHARACTER(255)"),
    NCHAR("NCHAR(55)"),
    NATIVE_CHARACTER("NATIVE CHARACTER(70)"),
    NVARCHAR("NVARCHAR(100)"),
    TEXT("TEXT"),
    CLOB("CLOB"),

    BLOB("BLOB"),

    REAL("REAL"),
    DOUBLE("DOUBLE"),
    DOUBLE_PRECISION("DOUBLE PRECISION"),
    FLOAT("FLOAT"),

    NUMERIC("NUMERIC"),
    DECIMAL("DECIMAL"),
    BOOLEAN("BOOLEAN"),
    DATE("DATE"),
    DATETIME("DATETIME");

    private final String value;

    AttributeTypeEnum(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }

}
