import org.jsoup.nodes.Element;
import java.io.IOException;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class WriteToDatabase {
    //general URL for all subjects
    static final String url = "https://www-banner.aub.edu.lb/catalog/schd_";
    static int index = 0;
    static final String connectionUrl =
            "jdbc:sqlserver://cmps.database.windows.net:1433;"
                    + "database=adventureworks;"
                    + "user=mbdeir;"
                    + "password=!!Cmps253!!;"
                    + "encrypt=true;"
                    + "trustServerCertificate=false;"
                    + "loginTimeout=30;";

    public static ArrayList<ArrayList<Object>> makeMatrixFromTableURL(String tableURL) throws IOException {
        Element table = Parser.getTableFromDoc(tableURL, 1);
        return Parser.makeMatrix(table);
    } //returns a 2 dimensional array of the rows in a table (all rows in table of A, or all rows table of B courses....) depending on the URL passed


    public static ArrayList<ArrayList<ArrayList<Object>>> makeMatrixOfAllTables() throws IOException { //each table is the  courses of an alphabet
        var allTables = new ArrayList<ArrayList<ArrayList<Object>>>();
        for (char c = 'E'; c <= 'U'; c ++){
            String URL = url + c + ".htm";
            allTables.add(makeMatrixFromTableURL(URL));
            System.out.println("getting table: " + c);
        }
        return allTables;
    }//return a 3 dimensional array
    //first dimension contains 26 lists, each list for a letter in the alpabet
    //second dimension contains x lists which are the rows in each table (all rows(courses) in A, all rows in B...)
    //third dimension (arraylist of objects) is the course's information (term, subject, linked CRN...) it is an OBJECT because it could be a:
    // string object (ex: subject, term)
    // or it could be a list object of the linked CRNs (cmps277 has labs: [1111 (B1),2222 (B2), 3333 (B3)])
    //optional fourth dimension, the object which could be a string or an array of the linked CRNs which has 0+ lab CRNs in it

    public static void main(String args[]) throws IOException {
        var allTables = makeMatrixOfAllTables();
        //allTables is now a 3 dimensional array as described above
        //3 loops to loop through each dimension
        //extra loop inside if for linkedCRN



        for (int i=0; i<allTables.size(); i++){
            //this loop will get each table table A, table B which is a 2 dimensional array (dimensions 2 and 3 of above)

            var alphaTable = allTables.get(i);
            //alphaTable is a 2 dimensional array (dimensions 2 and 3 of above)

            for(int j =0; j<alphaTable.size(); j++){
                //j will get the rows of each table that the i loop gets, a row is a 1 dimensional array containing the information about each course

                var row = alphaTable.get(j);
                //row is is a row in each table,  dimensional array (dimension 3 of the above)
                //(first row in A, second row in A, first row in B, second row in B)

//TODO: section method here

                for(int k=0; k<row.size(); k++){
                    //k will get each element in the row which might be a string or a list of linked CRNs
                    var elem = row.get(k);
                    //elem is an element in the row (subject, term, linked CRN) (notice it is of type of type object: string or arraylist)

                    if(!(elem instanceof String)){
                        //if the element is not a string, i.e. a list loop through the list; the linked CRNs

                        ArrayList<String> temp = (ArrayList<String >) elem;
                        //casting the object elem into an arraylist

                        for(int l =0; l<temp.size(); l++){
                            //looping over the linked CRNs of a row course
                            var linkedCrn = temp.get(l);
                            //linkedCRN is a string of the CRN of a lab/recitation

//
                            if(!linkedCrn.equals("")){
                                //if the CRN isn't an empty string
                                //perform operations on linkedCRN which will be a string that is the linked CRN (lab/recitation) of a course
                            }

                        }

                    }
                }



            }

        }

    }


    private static void addLinkedcrn(ArrayList<Object> row, String connectionUrl) {
        String SQLquery = "INSERT INTO PPROJ14.linked_crn (sec_crn,lab_crn) VALUES (?,?)";
        int resultSet = 0;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
        ) {
            PreparedStatement statement = connection.prepareStatement(SQLquery);
            statement.setString(1, (String) row.get(1));//
            statement.setString(2, (String) row.get(4));//section
            resultSet = statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void addInstructor(ArrayList<Object> row, String connectionUrl) {
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

    private static void addSection(ArrayList<Object> row, String connectionUrl) {
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
            index++;
            System.out.println("index: " + index);
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




//        for(int k=0; k<row.size(); k++){
////        k will get each element in the row which might be a string or a list of linked CRNs
////        var elem = row.get(k);
//                    System.out.println(row.get(0));
////elem is an element in the row (subjects, term, linked CRN) (notice it is of type of type object: string or arraylist)
//                    if(!(elem instanceof String)){
////if the element is not a string, i.e. a list loop through the list; the linked CRNs
//                        ArrayList<String> temp = (ArrayList<String >) elem;
//                        //casting the object elem into an arraylist
//
//                        for(int l =0; l<temp.size(); l++){
//                            //looping over the linked CRNs of a row course
//                            var linkedCrn = temp.get(l);
//                            //linkedCRN is a string of the CRN of a lab/recitation
//
//
//                            if(!linkedCrn.equals("")){
//                                //if the CRN isn't an empty string
//                                //perform operations on linkedCRN which will be a string that is the linked CRN (lab/recitation) of a course
//                            }
//
//                        }
//
//                    }
//        }v