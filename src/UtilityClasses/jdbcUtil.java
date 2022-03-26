package UtilityClasses;

import java.sql.*;

import adtImplementation.ArrayList;
import adtImplementation.HashMap;
import adtInterfaces.ListInterface;
import adtInterfaces.MapInterface;

public final class jdbcUtil {
    private static ResultSet queryRslt;
    private static Connection dbLink;
    private static final String url = "jdbc:mysql://127.0.0.1:3306/LiveSaleSystemDB?allowMultiQueries=true&rewriteBatchedStatements=true";
    private static final String user = "liveSale";
    private static final String pwrd = "liveSale";
    private static Statement SQL;
    private final static jdbcUtil INSTANCE = new jdbcUtil();


    private jdbcUtil() {
    }

    public static jdbcUtil getInstance() {
        return INSTANCE;
    }

    private static void openConnection() {
        try {
            jdbcUtil.dbLink = DriverManager.getConnection(url, user, pwrd);
            initSQL();
        } catch (SQLException exc) {
            System.out.println("A database error occured: " + exc.getMessage());
        }
    }

    private static void initSQL() {
        try {
            SQL = jdbcUtil.dbLink.createStatement
                    (
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    );
        } catch (SQLException exc) {
            System.out.println("A database error occured: " + exc.getMessage());
        }
    }

    public static int executeCUD(String statement) {
        openConnection();
        try {
            return SQL.executeUpdate(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Something wrong There is an error when inserting or updating the database");
            return 0;
        } finally {
            closeConnection();
        }
    }

    public static MapInterface<String, Object> readOne(String statement) {
        openConnection();
        HashMap<String, Object> record = new HashMap<String, Object>();
        try {
            queryRslt = SQL.executeQuery(statement);

            if (!queryRslt.next()) {
                return null;
            }

            queryRslt.first();
            int colQty = queryRslt.getMetaData().getColumnCount();
            ListInterface<String> labes = getColumnLabel(queryRslt);

            for (int i = 1; i <= colQty; i++) {
                Object obj = queryRslt.getObject(i);
                record.put(labes.get(i - 1), obj);
            }
            return record;
        } catch (Exception e) {
            System.out.println("A database error occured: " + e.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    public static ListInterface<String> getColumnLabel(ResultSet queryRslt) {
        ArrayList<String> labels = new ArrayList<String>();
        try {
            int colCount = queryRslt.getMetaData().getColumnCount();
            for (int i = 1; i <= colCount; i++) {
                String label = queryRslt.getMetaData().getColumnLabel(i);
                labels.add(label);
            }
        } catch (SQLException e) {
            System.out.print("Database error occured : " + e.getMessage());
        }
        return labels;
    }

    public static ListInterface<MapInterface<String, Object>> readAll(String statement) {
        openConnection();
        ListInterface<MapInterface<String, Object>> hMapList = new ArrayList<>();
        try {
            queryRslt = SQL.executeQuery(statement);
            ListInterface<String> columnNames = getColumnLabel(queryRslt);
            int colQty = columnNames.size();
            while (queryRslt.next()) {
                MapInterface<String, Object> hMap = new HashMap<String, Object>();
                for (int i = 1; i <= colQty; i++) {
                    Object obj = queryRslt.getObject(i);
                    hMap.put(columnNames.get(i - 1), obj);
                }
                hMapList.add(hMap);
            }
            queryRslt.beforeFirst();
            return hMapList;
        } catch (Exception e) {
            System.out.println("A database error occured: " + e.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    public static String getNextId(String table) throws SQLException {
        openConnection();
        String column = table.toLowerCase() + "ID";
        queryRslt = SQL
                .executeQuery(String.format("SELECT MAX(`%s`) as %s FROM `%s`", column, column, table));
        queryRslt.first();
        String currId = (String) queryRslt.getObject(column);
        int id = Integer.parseInt(currId.replaceAll("\\D+", ""));
        closeConnection();
        return String.format("%s%05d", table.substring(0, 1).toUpperCase(), id + 1);
    }

    public static void closeConnection() {
        if (jdbcUtil.dbLink != null) {
            try {
                jdbcUtil.dbLink.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
