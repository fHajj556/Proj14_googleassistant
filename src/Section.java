import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Section {
    public static void InsertIntoSection(ArrayList<Object> row, String connectionUrl) {
        String SQLquery = "INSERT INTO PROJ14.section" +
                " (crn,section,actual_enrollment,seats_available,"
                + "capacity,building_1,building_2,"
                + "time_1,time_2,room_1,room_2,term,"
                + "title,credit_hours)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        // TODO: Put the execute statement in try catch statement
        int resultSet = 0;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
        ) {
            PreparedStatement statement = connection.prepareStatement(SQLquery);
            statement.setString(1, (String) row.get(1));//crn
            statement.setString(2, (String) row.get(4));//section
            statement.setString(3, (String) row.get(9));//actual
            statement.setString(4, (String) row.get(10));//avail
            int x = Integer.parseInt((String) row.get(9));
            int y = Integer.parseInt((String) row.get(10));
            String z = (x + y) + "";
            statement.setString(5, z);//cap
            statement.setString(6, (String) row.get(13));//bu1
            statement.setString(7, (String) row.get(24));//bu2
            statement.setString(8, convertToDatetime((String) row.get(11)));//t1
            statement.setString(9, convertToDatetime((String) row.get(22)));//t2
            statement.setString(10, (String) row.get(14));//r1
            statement.setString(11, (String) row.get(25));//r2
            statement.setString(12, (String) row.get(0));//term
            statement.setString(13, (String) row.get(5));//title
            statement.setString(14, (String) row.get(6));//credits
            resultSet = statement.executeUpdate();
            System.out.println(row.get(1));
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public static String convertToDatetime(String time){
        String newTime ="";
        if(time.length() == 1){
            newTime = null;
        }else{
            newTime = time.substring(0,2)+":"+time.substring(2);
        }
        return newTime;

    }

}
