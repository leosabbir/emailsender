package com.namobuddha.emailsender.util.cs477;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.namobuddha.emailsender.model.StudentDetails;

public class AlgorithmExcelReader {

	private final String FILE_LOCATION;
	private final int NUMBER_OF_STUDENTS;
	private final int FIRST_SHEET_INDEX;

	public AlgorithmExcelReader(int firstSheetIndex, int nrOfStds,
			String fileLocation) {
		this.FIRST_SHEET_INDEX = firstSheetIndex;
		this.FILE_LOCATION = fileLocation;
		this.NUMBER_OF_STUDENTS = nrOfStds;
	}

	public List<StudentDetails> getStudentsDetails() {
		List<StudentDetails> students = new ArrayList<StudentDetails>();
		try {
			FileInputStream file = new FileInputStream(new File(FILE_LOCATION));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(this.FIRST_SHEET_INDEX);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			for (int j = 0; j < this.NUMBER_OF_STUDENTS; j++) {
				StudentDetails student = new StudentDetails();
				Row row = rowIterator.next();

				String name = row.getCell(0).getStringCellValue();
				String emailId = row.getCell(1).getStringCellValue();
				if (emailId == null || emailId.isEmpty()) {
					continue;
				}
				String marks="";// = 0;//CHANGED
				if (row.getCell(2) != null) {
					//System.out.println(row.getCell(2).getStringCellValue());
					//marks = row.getCell(2).getNumericCellValue();
					marks = row.getCell(2).getStringCellValue();//CHANGED
				}
				String remarks = null;
				if (row.getCell(3) != null) {
					remarks = row.getCell(3).getStringCellValue();
				}
				if (remarks == null || remarks.isEmpty()) {
					remarks = "no comments";
				}
				

				student.setName(name);
				student.setEmailId(emailId);
				//student.setTotalMarks(new Double(marks).intValue());
				student.setRemarks(marks);//CHANGED

				students.add(student);
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return students;
	}
	
	public static void main(String[] args) {
		int nrOfStds = 38; 
		AlgorithmExcelReader reader = new AlgorithmExcelReader(1, nrOfStds, "data/CS477-HW1.xlsx");
		
		int i = 0;
		for (StudentDetails student : reader.getStudentsDetails()) {
			i++;
			System.out.println(student.getName() + " \t\t " + student.getEmailId() + " \t\t " + student.getTotalMarks() + " \t " + student.getRemarks());
		}
		
		System.out.println("\n\n" + i);
	}
}
