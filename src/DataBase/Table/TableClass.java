package DataBase.Table;

import DataBase.Connection.DBConnection;
import Utils.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableClass implements TableInterface{

    private String tableName;
    private AttributeInterface primaryKeyAttribute;
    private List<AttributeInterface> attributes;


    public TableClass(String tableName) {
        this.tableName = tableName;
        attributes = new LinkedList();
    }

    @Override
    public TableClass addPrimaryKey(String name, AttributeTypeEnum type) {
        primaryKeyAttribute = new AttributeClass(name, type, true);
        return this;
    }

    @Override
    public TableClass addNotNullAttribute(String name, AttributeTypeEnum type) {
        attributes.add(new AttributeClass(name, type, true));
        return this;
    }

    @Override
    public TableClass addAttribute(String name, AttributeTypeEnum type) {
        attributes.add(new AttributeClass(name, type, false));
        return this;
    }

    @Override
    public TableClass create(){
        Connection conn = DBConnection.getInstance();
        String query = "CREATE TABLE " + tableName + " (";
        query += primaryKeyAttribute.toString() + " PRIMARY KEY,";
        for(AttributeInterface att : attributes)
            query += att.toString() + ", ";
        query = query.substring(0, query.length() - 2) + ")";
        try {
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.execute();
            Log.info("Table create: %s", tableName);
        } catch (SQLException e) {
            Log.warn("Table create: %s", tableName);
        }
        return this;
    }

    @Override
    public void drop() {
        Connection conn = DBConnection.getInstance();
        String query = "DROP TABLE " + tableName;
        try {
            PreparedStatement sttm = conn.prepareStatement(query);
            sttm.executeUpdate();
            Log.info("Table drop:   %s", tableName);
        } catch (SQLException e) {
            Log.warn("Table drop:   %s", tableName);
        }
    }

    @Override
    public void insert(String ... elems) throws SQLException {
        Connection conn = DBConnection.getInstance();
        String query = "INSERT INTO " + tableName + " VALUES (";
        for( String elem : elems)
            query = query + "'" + elem + "', ";
        query = query.substring(0, query.length() - 2) + ")";
        PreparedStatement sttm = conn.prepareStatement(query);
        sttm.executeUpdate();
        Log.info("Table insert:   %s", elems[0]);
    }

    @Override
    public void remove(String primKey) throws SQLException {
        Connection conn = DBConnection.getInstance();
        String query = "DELETE FROM " + tableName + " WHERE " + primaryKeyAttribute.getName() + " LIKE '" + primKey + "'";
        PreparedStatement sttm = conn.prepareStatement(query);
        sttm.execute();
        Log.info("Table remove:   %s", primKey);
    }

    @Override
    public void update(String primKey, String UpdateCol, String UpdateValue) throws SQLException {
        Connection conn = DBConnection.getInstance();
        String query = "UPDATE " + tableName + " SET "+UpdateCol+ "= '"+UpdateValue+"' WHERE "+primaryKeyAttribute.getName()+" LIKE '" + primKey+"'";
        PreparedStatement sttm = conn.prepareStatement(query);
        sttm.executeUpdate();
        Log.info("Table update:   %s", primKey);
    }

    @Override
    public ResultSet getOne(String primKey) throws SQLException {
        Connection conn = DBConnection.getInstance();
        String query = "SELECT * FROM " + tableName + " WHERE " + primaryKeyAttribute.getName() + " LIKE '" + primKey + "'";
        PreparedStatement sttm = conn.prepareStatement(query);
        Log.info("Table getOne:   %s", primKey);
        return sttm.executeQuery();
    }

}
