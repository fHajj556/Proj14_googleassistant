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
        for (char c = 'A'; c <= 'U'; c ++){
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
//                    LinkedCrn.InsertIntoLinkedcrn(row,connectionUrl);
//                    break;

//TODO: section method here


                       //BELOW IS CODE TO USE FOR LINKEDCRN

//                for(int k=0; k<row.size(); k++){
//                    //k will get each element in the row which might be a string or a list of linked CRNs
//                    var elem = row.get(k);
//                    //elem is an element in the row (subject, term, linked CRN) (notice it is of type of type object: string or arraylist)
//
//                    if(!(elem instanceof String)){
//                        //if the element is not a string, i.e. a list loop through the list; the linked CRNs
//
//                        ArrayList<String> temp = (ArrayList<String >) elem;
//                        //casting the object elem into an arraylist
//
//                        for(int l =0; l<temp.size(); l++){
//                            //looping over the linked CRNs of a row course
//                            var linkedCrn = temp.get(l);
//                            //linkedCRN is a string of the CRN of a lab/recitation
//
////
//                            if(!linkedCrn.equals("")){
//                                //if the CRN isn't an empty string
//                                //perform operations on linkedCRN which will be a string that is the linked CRN (lab/recitation) of a course
//                            }
//
//                        }
//
//                    }
//                }



            }

        }

    }

}

