package DataBase.Table;

public class AttributeClass implements AttributeInterface{

    private String name;
    private AttributeTypeEnum type;
    private boolean notnull;

    public AttributeClass(String name, AttributeTypeEnum type, boolean notnull) {
        this.name = name;
        this.type = type;
        this.notnull = notnull;
    }

    public String getName() {
        return name;
    }

    public AttributeTypeEnum getType() {
        return type;
    }

    public boolean isNotnull() {
        return notnull;
    }

    @Override
    public String toString(){
        return this.name + " " + type.getValue() + (notnull ? " NOT NULL" : "");
    }
}
