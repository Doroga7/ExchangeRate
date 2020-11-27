import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * класс взаимодействия с БД
 */
class DataBase {
    private final String URL = "jdbc:mysql://localhost:3306/mydbtest?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String USERNAME = "root";
    private final String PASSWORD = "ASqw12AS";
    private String INSERT_NEW = "INSERT INTO test_new VALUES(?)";
    String DateNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    LoadWeb loadWeb = new LoadWeb();
    String[][] workingArray = new String[34][5];

    /**
     * метод загрузки в БД
     */
    public void uploadingDataToDatabase(String NumCode, String CharCode, String Nominal, String Name, String Value) {
        String INSERT_NEW = "INSERT INTO CBR (DateNow, NumCode, CharCode, Nominal, Name, Value) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); //TODO проверить и удалить
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW)) {
            preparedStatement.setString(1, DateNow);
            preparedStatement.setString(2, NumCode);
            preparedStatement.setString(3, CharCode);
            preparedStatement.setString(4, Nominal);
            preparedStatement.setString(5, Name);
            preparedStatement.setString(6, Value);
            preparedStatement.execute(); // забрасываем в БД
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * метод выгрузки из БД
     */
    public String[][] loadingDataToDatabase() {
        String query1 = "SELECT * FROM CBR"; // синтаксис для MySQL

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            if (!chekDataToDatabase()) { // проверка на корректность БД
                clearingDataBase();
                loadWeb.init();
            }
            ResultSet resultSet = statement.executeQuery(query1);
            int numberRow = 0;
            while (resultSet.next()) {
                System.out.println();
                workingArray[numberRow][0] = resultSet.getString("Id");
                workingArray[numberRow][1] = resultSet.getString("Nominal");
                workingArray[numberRow][2] = resultSet.getString("Value");
                numberRow++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workingArray;
    }

    /**
     * метод очищения БД
     */
    public void clearingDataBase() {
        PreparedStatement preparedStatement = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CBR");
            resultSet.next();
            int rowCount = resultSet.getInt(1);
            if (rowCount > 1) {
                preparedStatement = connection.prepareStatement("DELETE FROM CBR");
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * метод проверки БД на актуальность
     */
    public boolean chekDataToDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSetDate = statement.executeQuery("SELECT * FROM CBR ORDER BY ID DESC LIMIT 1");
            while (resultSetDate.next()) {
                if (!DateNow.equals(resultSetDate.getString("DateNow"))) { // если не актуальные, то загрузить
                    return false;
                }
            }
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CBR");
            if (!resultSet.next()) {
                return false;
            }
            resultSetDate.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}