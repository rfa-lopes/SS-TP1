package DataBase.Table;

import java.sql.SQLException;

public interface TableInterface {

    TableClass addPrimaryKey(String name, AttributeTypeEnum type);
    TableClass addNotNullAttribute(String name, AttributeTypeEnum type);
    TableClass addAttribute(String name, AttributeTypeEnum type);

    TableClass create();
    void drop();

    void insert(String ... elems) throws SQLException;
    void remove(String primKey) throws SQLException;
    void update(String primKey, String UpdateCol, String UpdateValue) throws SQLException;

}
