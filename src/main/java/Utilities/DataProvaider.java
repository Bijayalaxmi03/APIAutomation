package Utilities;

import org.apache.poi.sl.usermodel.Sheet;
import org.testng.annotations.DataProvider;

import javax.xml.crypto.Data;
import java.io.IOException;

public class DataProvaider {
    @DataProvider(name = "Data")
    public String[][] getAllData() throws IOException {
        String path = System.getProperty("user.dir") + "//TestData//UserData.xlsx";
        XUtilities xl = new XUtilities(path);
        int rownum = xl.getRowCount("Sheet1");
        int colCount = xl.getCellCount("Sheet1", 1);
        String apiData[][] = new String[rownum][colCount];
        for (int i = 1; i <= rownum; i++) {
            for (int j = 0; j < colCount; j++) {
                apiData[i-1][j] = xl.getCellData("Sheet1", i, j);
            }
        }
        return apiData;
    }

    @DataProvider(name = "UserNames")
    public String[] getUserName() throws IOException {
        String path = System.getProperty("user.dir") + "//TestData//UserData.xlsx";
        XUtilities xl = new XUtilities(path);
        int rownum = xl.getRowCount("Sheet1");
        String apiData[] = new String[rownum];
        for (int i = 0; i < rownum; i++) {
            apiData[i] = xl.getCellData("Sheet1", i+1, 1);
        }
        return apiData;
    }
}
