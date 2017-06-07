package dataProvider;

import utility.Excel_Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

public class DP_ReportValidation {

	@DataProvider(name="reportValidationDP")
	public static Iterator<Object[]> report_validation() throws Exception
	{
		Excel_Reader er=new Excel_Reader(System.getProperty("user.dir")+"//resource//dataSheet//Test_Data.xlsx");
		List<Object[]> list=new ArrayList<Object[]>();
		int rowCount = er.getRowCount("ReportValidation");
		
		for(int i=1;i<=rowCount;i++){
			if (er.readCellValue("ReportValidation", i, 2).equals("ValidationOfDailyReport")){
				
					Object[] obj = new Object[8];
					obj[0]=er.readCellValue("ReportValidation", i, 3);
					obj[1]=er.readCellValue("ReportValidation", i, 4);
					obj[2]=er.readCellValue("ReportValidation", i, 5);
					obj[3]=er.readCellValue("ReportValidation", i, 6);
					obj[4]=er.readCellValue("ReportValidation", i, 7);
					obj[5]=er.readCellValue("ReportValidation", i, 8);
					obj[6]=er.readCellValue("ReportValidation", i, 9);
					obj[7]=er.readCellValue("ReportValidation", i, 10);
					
					list.add(obj);
				
			}
		}
		return list.iterator();
	}
	
}
