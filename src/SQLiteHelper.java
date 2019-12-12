import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper {
    private static final String DATABASE_NAME = "databaseImages.db";
    private static final String CONNECTION_URL = "jdbc:sqlite:databases/" + DATABASE_NAME;
    private static final String ID = "id";
    private static final String IMAGE_PATH = "path";
    private static final String TABLE_IMAGES = "tableImages";
    private static final String NAME = "name";
    private static final String ALL_PIXELS = "allPixels";
    private static final String UNIQUE_COLORS = "uniqueColors";
    private Connection connection;
    protected List<ImageInfo> images;

    public SQLiteHelper(){
        getConnection();
        createImagesTable();
    }

    private void getConnection(){
        try{
            connection = DriverManager.getConnection(CONNECTION_URL);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void createImagesTable(){
        String sqlCreate = "CREATE TABLE " + TABLE_IMAGES + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, " + IMAGE_PATH + " TEXT, " + ALL_PIXELS + " INTEGER, " + UNIQUE_COLORS + " INTEGER)";
        if(connection != null && !tableExists()){
            try{
                Statement statement = connection.createStatement();
                statement.execute(sqlCreate);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(){
        //close the connection that we have opened
        if(connection != null){
            try {
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void insertImage(ImageInfo image){
        String sqlInsert = "INSERT INTO " + TABLE_IMAGES + " VALUES(null, '" + image.getName() + "', '" + image.getPath()
                + "', " + image.getAllPixels() + ", " + image.getUniqueColors() + ")";
        System.out.println(sqlInsert);
        try{
            Statement statement = connection.createStatement();
            statement.execute(sqlInsert);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteAllImages(){
        String sqlDeleteAll = "DELETE FROM " + TABLE_IMAGES;
        try{
            Statement statement = connection.createStatement();
            statement.execute(sqlDeleteAll);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ImageInfo getLastUsedImage(){
        String sqlSelect = "SELECT * FROM " + TABLE_IMAGES;
        ImageInfo returnImageInfo;
        List<ImageInfo> images = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelect);
            while(resultSet.next()){
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                String path = resultSet.getString(IMAGE_PATH);
                int allPixels = resultSet.getInt(ALL_PIXELS);
                int uniqueColors = resultSet.getInt(UNIQUE_COLORS);

                ImageInfo image = new ImageInfo(name, path, allPixels, uniqueColors);
                images.add(image);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        returnImageInfo = images.get(images.size() - 1);
        return returnImageInfo;
    }

    public void getAllImages(){
        images = new ArrayList<>();
        String sqlSelectAll = "SELECT * FROM " + TABLE_IMAGES;

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAll);
            while(resultSet.next()){
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                String path = resultSet.getString(IMAGE_PATH);
                int allPixels = resultSet.getInt(ALL_PIXELS);
                int uniqueColors = resultSet.getInt(UNIQUE_COLORS);

                ImageInfo image = new ImageInfo(name, path, allPixels, uniqueColors);
                images.add(image);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private boolean tableExists() {
        DatabaseMetaData md = null;
        boolean hasNext = false;
        try {
            md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, TABLE_IMAGES, null);
            hasNext = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasNext;
    }
}


