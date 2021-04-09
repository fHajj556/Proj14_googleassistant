import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Instructor {
    public static void InsertIntoInstructor(ArrayList<Object> row, String connectionUrl) {
        String SQLquery = "INSERT INTO PROJ14.instructor (instructor_id,first_name,last_name) VALUES (?,?,?)";
        int resultSet = 0;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
        ) {
            PreparedStatement statement = connection.prepareStatement(SQLquery);
//            statement.setString(2, (String) row.get(4)); TODO: email can be null for  now but i need to change it
            statement.setString(1,null);//id comes from sec_instructor:(
            statement.setString(2, (String) row.get(33));//f.n
            statement.setString(3, (String) row.get(34));//l.n

            resultSet = statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
