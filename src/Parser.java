import com.sun.tools.jconsole.JConsolePlugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.regex.*;

import java.io.IOException;
import java.util.ArrayList;

//Elements extends ArrayList<Element>

public class Parser {
    static Element getTableFromDoc (String URL, int tableIndex) throws IOException {
        //maxbody and the line after allows files of unlimited size
        Document doc = Jsoup.connect(URL).maxBodySize(0).get();
        Elements items = doc.getElementsByClass("manga_info");
        Elements tables = doc.getElementsByTag("table");
        return tables.get(tableIndex);
    }

    static ArrayList<ArrayList<Object>> makeMatrix(Element table) {
        ArrayList<ArrayList<Object>> matrix = new ArrayList<>();
        ArrayList<Element> allCells = table.select("td");
        for (int i = 38; i < allCells.size(); i += 37) { //skip 37 "td"s because they are the table headers
            int j = i + 37;
            if (j > allCells.size()) {
                break;
            }
            ArrayList<Object> row = new ArrayList<Object>();
            for (int k = i ; k<j; k++){
                row.add(allCells.get(k).text());
            }
//            System.out.println(row.get(35));
            ArrayList<String> linkedCRN = new ArrayList<String>(Arrays.asList(((String)row.get(35)).split(", or ")));
            for(int x =0; x<linkedCRN.size(); x++){
                linkedCRN.set(x, linkedCRN.get(x).replace("< /TD>", ""));
            }

            row.set(35, linkedCRN);
            matrix.add(row);
        }
        return matrix;
    }


}