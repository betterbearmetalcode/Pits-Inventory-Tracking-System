package org.bearmetal.pits_inventory_tracking_system.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Handles SQLite database access.
 * 
 * @author Colin Rice
 */
public class DatabaseManager {
    
    private static Connection connection;
    private static String databaseName;
    private static HashMap<String, String> tables;
    private static final Object[] emptyParams = {};

    /** Sets the map of table names to schemas used by ensureTables.
     * 
     * @param t
     * A HashMap of table name to SQL schema.
     */
    public static void setTables(HashMap<String, String> t){
        tables = t;
    }

    /** Ensures all required tables exist.
     * <p>
     * This depends on the 'tables' field being set through <code>DatabaseManager.setTables</code>.
     * 
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    public static void ensureTables() throws SQLException, IllegalArgumentException{
        if (tables == null){
            throw new IllegalArgumentException("'tables' must be set before calling ensureTables!");
        }
        System.out.println("Making sure all required tables exist.");
        for (Map.Entry<String, String> entry : tables.entrySet()){
            String table = entry.getKey();
            String schema = entry.getValue();
            execNoReturn("CREATE TABLE IF NOT EXISTS " + table + "(" + schema + ")");
        }
        System.out.println("All required tables exist.");
    }

    private static void setParam(PreparedStatement statement, Integer index, Object param) throws SQLException{
        if (param instanceof String){
            statement.setString(index, param.toString());
        } else if (param instanceof Integer){
            statement.setInt(index, (int) param);
        } else if (param instanceof Boolean){
            statement.setBoolean(index, (Boolean) param);
        }
    }

    /** Executes a SQL statement that will not return results.
     * <p>
     * The statement will not be executed if it returns results.
     * 
     * @param statement
     * The statement to execute.
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    public static void execNoReturn(String statement) throws SQLException, IllegalArgumentException{
        execNoReturn(statement, DatabaseManager.emptyParams);
    }

    /** Executes a SQL statement (with parameters) that will not return results.
     * <p>
     * The statement will not be executed if it returns results.
     * @param statement
     * The statement to execute.
     * @param params
     * Parameters to pass to the statement. Question marks in the statement are substituted with parameters.
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    public static void execNoReturn(String statement, Object[] params) throws SQLException, IllegalArgumentException{
        try {
            PreparedStatement toExec = connection.prepareStatement(statement);
            Integer count = 1;
            for (Object param : params){
                setParam(toExec, count, param);
                count++;
            }
            toExec.executeUpdate();
            connection.commit();
        } catch (SQLException err){
            System.err.println("SQLException while executing statement '" + statement + "'.");
            System.err.println("Rolling back transaction.");
            connection.rollback();
        }
    }

    /**Executes a SQL statement that will return results.
     * <p>
     * The statement will not be executed if it doesn't return results.
     * @param statement
     * The statement to execute.
     * @return 
     * A list of mappings of column name to value.
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    public static ArrayList<HashMap<String, Object>> exec(String statement) throws SQLException, IllegalArgumentException{
        return exec(statement, DatabaseManager.emptyParams);
    }


    /**Executes a SQL statement (with parameters) that will return results.
     * <p>
     * The statement will not be executed if it doesn't return results.
     * @param statement
     * The statement to execute.
     * @param params
     * Parameters to pass to the statement. Question marks in the statement are substituted with parameters.
     * @return 
     * A list of mappings of column name to value.
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    public static ArrayList<HashMap<String, Object>> exec(String statement, Object[] params) throws SQLException, IllegalArgumentException{
        try {
            PreparedStatement toExec = connection.prepareStatement(statement);
            Integer count = 1;
            for (Object param : params){
                setParam(toExec, count, param);
                count++;
            }
            ResultSet results = toExec.executeQuery();
            connection.commit();
            ArrayList<HashMap<String, Object>> toReturn = processResultSet(results);
            results.close();
            toExec.close();
            return toReturn;
        } catch (SQLException err){
            System.err.println("SQLException while executing statement '" + statement + "'.");
            System.err.println("Rolling back transaction.");
            connection.rollback();
            return null;
        }
    }

    private static ArrayList<HashMap<String, Object>> processResultSet(ResultSet data) throws SQLException{
        ResultSetMetaData md = data.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        while (data.next()){
            HashMap<String, Object> row = new HashMap<String, Object>(columns);
            for (int i=1; i<=columns; ++i) {           
                row.put(md.getColumnName(i), data.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

    /** Attempts to connect to database <code>db</code>.
     * 
     * @param db
     * The name of the database to connect to.
     * @throws SQLException
     */
    public static void connect(String db) throws SQLException{
        databaseName = db;
        System.out.println("Opening connection to database " + databaseName);
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");
        connection.setAutoCommit(false);
    }
}

