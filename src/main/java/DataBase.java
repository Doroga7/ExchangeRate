import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * класс СУБД
 * TODO в разработке
 */
class DataBase {
    private String URL = "jdbc:mysql://localhost:3306/mydbtest?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String USERNAME = "root";
    private String PASSWORD = "ASqw12AS";
    private String INSERT_NEW = "INSERT INTO CBR (DateNow, NumCode, CharCode, Nominal, Name, Value) VALUES(?, ?, ?, ?, ?, ?)"; // запись


    /**
     * метод загрузки в БД
     */
    public void uploadingDataToDatabase(String NumCode, String CharCode, String Nominal, String Name, String Value) {
        String DateNow = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

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
    public void loadingDataToDatabase() {
    }

    /**
     * метод очищения БД
     */
    public void clearingDataBase() {
    }

}