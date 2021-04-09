import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class LinkedCrn {
    public static void InsertIntoLinkedcrn(ArrayList<Object> row, String connectionUrl) {
        String SQLquery = "INSERT INTO PROJ14.linked_crn (sec_crn,lab_crn) VALUES (?,?)";
        int resultSet = 0;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
        ) {
            PreparedStatement statement = connection.prepareStatement(SQLquery);
            statement.setString(1, (String) row.get(1));
            statement.setString(2, (String) row.get(4));//section
            resultSet = statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
