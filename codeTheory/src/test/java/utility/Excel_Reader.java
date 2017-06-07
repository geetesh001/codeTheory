package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_Reader {
	
	public XSSFWorkbook workbook=null;
	public String excelPath;
	public Excel_Reader(String excelPath) throws IOException{
			this.excelPath=excelPath;
			FileInputStream fis = new FileInputStream(excelPath);
			workbook=new XSSFWorkbook(fis);
			
			if(workbook!=null){
				System.out.println("Connection successful");
			}
			else
				System.out.println("Connection Failed");
			
			fis.close();
	}
	
	public XSSFSheet setSheet(String sName){
		XSSFSheet st = workbook.getSheet(sName);
		return st;
	}

	public int getRowCount(String sheetName){
		XSSFSheet sheet=setSheet(sheetName);
		int rownum= sheet.getLastRowNum()+1;
		return rownum;
	}
	
	public int getColumnCount(String sheetName,int rowNumber){
		XSSFSheet sheet=setSheet(sheetName);
		int colcount= sheet.getRow(rowNumber).getLastCellNum();
		return colcount;
	}
	
	public String readCellValue(String sheetName,int rowNumber,int colNumber){
		String cellMsg="";
		XSSFSheet sheet=setSheet(sheetName);
		XSSFCell cell = sheet.getRow(rowNumber-1).getCell(colNumber-1);
		if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			cellMsg=cell.getStringCellValue();
		else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
			cellMsg=String.valueOf(cell.getNumericCellValue());
		else if (cell.getCellType()==Cell.CELL_TYPE_BLANK)
			cellMsg="";
		
		return cellMsg;
			
	}
	
	public void writeCellValue(String sheetName,int rowNum, int colNum, String value) throws IOException{
		XSSFSheet sheet=setSheet(sheetName);
		XSSFRow row = sheet.getRow(rowNum-1);
		if(row==null)
			sheet.createRow(rowNum-1);
		XSSFCell cell = sheet.getRow(rowNum-1).getCell(colNum-1);
		if(cell==null)
			sheet.getRow(rowNum-1).createCell(colNum-1);
		
		cell.setCellValue(value);
		writeToExcel();	
	}
	
	public void writeToExcel() throws IOException{
		FileOutputStream fos = new FileOutputStream(excelPath);
		workbook.write(fos);
		fos.close();	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
