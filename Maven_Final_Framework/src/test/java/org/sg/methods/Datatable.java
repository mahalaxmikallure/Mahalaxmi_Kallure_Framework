package org.sg.methods;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sg.driver.DriverScript;
import org.testng.Assert;

public class Datatable extends DriverScript{
	/******************************************
	 * Method Name		: getDataFromExcel
	 * 
	 * 
	 * 
	 ******************************************/
	public Map<String, String> getDataFromExcel(String moduleName, String sheetName, String logicalName){
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		String key = null;
		String value = null;
		Map<String, String> objData = null;
		Calendar cal = null;
		String day = null;
		String month = null;
		String year = null;
		int rowNum = 0;
		try {
			objData = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir") + "\\TestData\\" + moduleName + ".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			Assert.assertNotNull(sh, "The sheet '"+sheetName+"' was not in the excel file");
			
			//Find the logicalName rowNumber, if present, otherwise fail with proper message
			int rowNumber = sh.getPhysicalNumberOfRows();
			for(int r=0; r<rowNumber; r++) {
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if(cell1.getStringCellValue().trim().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			if(rowNum > 0) {
				row1 = sh.getRow(0);
				row2 = sh.getRow(rowNum);
				
				for(int c=0; c<row1.getPhysicalNumberOfCells(); c++) {
					cell1 = row1.getCell(c);
					key = cell1.getStringCellValue();
					
					cell2 = row2.getCell(c);
					
					if(cell2==null || cell2.getCellType()==CellType.BLANK) {
						value = "";
					}
					else if(cell2.getCellType()==CellType.BOOLEAN) {
						value = String.valueOf(cell2.getBooleanCellValue());
					}
					else if(cell2.getCellType()==CellType.STRING) {
						value = cell2.getStringCellValue();
					}
					else if(cell2.getCellType()==CellType.NUMERIC) {
						if(DateUtil.isCellDateFormatted(cell2)==true) {
							double dt = cell2.getNumericCellValue();
							cal = Calendar.getInstance();
							cal.setTime(DateUtil.getJavaDate(dt));
							
							//if day is <10 then prefix with zero
							if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
								day = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}else {
								day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							//if month is <10 then prefix with zero
							if((cal.get(Calendar.MONTH)+1) < 10) {
								month = "0" + (cal.get(Calendar.MONTH)+1);
							}else {
								month = String.valueOf((cal.get(Calendar.MONTH)+1));
							}
							
							year = String.valueOf(cal.get(Calendar.YEAR));
							
							value = day +"/"+ month + "/" + year;
						}else {
							value = String.valueOf(cell2.getNumericCellValue());
						}
					}
					
					objData.put(key, value);
				}
			}else {
				reports.writeResult(null, "Fail", "Failed to find the logicalName '"+logicalName+"' in the sheet '"+sheetName+"'");
				return null;
			}
			
			return objData;
			
		}catch(Exception e) {
			reports.writeResult(null,"Exception", "Exception in the 'getDataFromExcel()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeResult(null,"Exception", "Assetion error in the 'getDataFromExcel()' method. " + e);
			return null;
		}finally
		{
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				sh = null;
				wb.close();
				wb = null;
				cal = null;
				day = null;
				month = null;
				year = null;
				key = null;
				value = null;
			}catch(Exception e) {}
		}
	}
	
	
	
	/******************************************
	 * Method Name		: getDataFromExcel
	 * 
	 * 
	 * 
	 ******************************************/
	public Object[][] createDataProvider(String fileName, String sheetName){
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		Object data[][] = null;
		int rowCount = 0;
		int colCount = 0;
		List<String> colName = null;
		Map<String, String> cellData = null;
		String strValue = null;
		int executionCount = 0;
		Calendar cal = null;
		String day = null;
		String month = null;
		String year = null;
		int actualRows = 0;
		try {
			fin = new FileInputStream(System.getProperty("user.dir") + "\\ExecutionController\\"+fileName+".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			Assert.assertNotNull(sh, "The sheet '"+sheetName+"' doesnot exist");

			rowCount = sh.getPhysicalNumberOfRows();
			row = sh.getRow(0);
			colCount = row.getPhysicalNumberOfCells();
			
			//loop to number of rows to find the count of the test cases selected for the execution
			for(int rows=0; rows<rowCount; rows++) {
				row = sh.getRow(rows);
				cell = row.getCell(4);
				if(cell.getStringCellValue().equalsIgnoreCase("Yes")) {
					executionCount++;
				}
			}
			
			data = new Object[executionCount][1];
			colName = new ArrayList<String>();
			for(int col=0; col<colCount; col++) {
				row = sh.getRow(0);
				cell = row.getCell(col);
				colName.add(col, cell.getStringCellValue());
			}
			
			
			for(int r=0; r<rowCount; r++) {
				row = sh.getRow(r);
				cell = row.getCell(4);
				if(cell.getStringCellValue().equalsIgnoreCase("Yes")) {
					cellData = new HashMap<String, String>();
					
					for(int col=0; col<colCount; col++) {
						cell = row.getCell(col);
						
						if(cell==null || cell.getCellType()==CellType.BLANK) {
							strValue = "";
						}
						else if(cell.getCellType()==CellType.BOOLEAN) {
							strValue = String.valueOf(cell.getBooleanCellValue());
						}
						else if(cell.getCellType()==CellType.STRING) {
							strValue = cell.getStringCellValue();
						}
						else if(cell.getCellType()==CellType.NUMERIC) {
							if(DateUtil.isCellDateFormatted(cell)==true) {
								double dt = cell.getNumericCellValue();
								cal = Calendar.getInstance();
								cal.setTime(DateUtil.getJavaDate(dt));
								
								//if day is <10 then prefix with zero
								if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
									day = "0" + cal.get(Calendar.DAY_OF_MONTH);
								}else {
									day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
								}
								
								//if month is <10 then prefix with zero
								if((cal.get(Calendar.MONTH)+1) < 10) {
									month = "0" + (cal.get(Calendar.MONTH)+1);
								}else {
									month = String.valueOf((cal.get(Calendar.MONTH)+1));
								}
								
								year = String.valueOf(cal.get(Calendar.YEAR));
								
								strValue = day +"/"+ month + "/" + year;
							}else {
								strValue = String.valueOf(cell.getNumericCellValue());
							}
						}
						cellData.put(colName.get(col), strValue);
						System.out.println(cellData);
					}
					data[actualRows][0] = cellData;
					actualRows++;
				}
			}
			System.out.println(Arrays.deepToString(data));
			return data;
		}catch(Exception e) {
			reports.writeResult(null,"Exception", "Exception in the 'createDataProvider()' method. " + e);
			return null;
		}
		catch(AssertionError e) {
			reports.writeResult(null,"Exception", "Assetion error in the 'createDataProvider()' method. " + e);
			return null;
		}finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
				cal = null;
				day = null;
				month = null;
				year = null;
			}catch(Exception e) {}
		}
	}
}
