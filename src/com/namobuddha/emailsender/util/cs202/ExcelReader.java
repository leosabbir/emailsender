package com.namobuddha.emailsender.util.cs202;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.namobuddha.emailsender.model.StudentDetails;

//import statements
public class ExcelReader {

	private final int FIRST_SHEET_INDEX;
	private final int[] NUMBER_OF_STUDENTS;
	private final String FILE_LOCATION;

	public ExcelReader(int firstSheetIndex, int[] nrOfStds, String fileLocation) {
		this.FIRST_SHEET_INDEX = firstSheetIndex;
		this.NUMBER_OF_STUDENTS = nrOfStds;
		this.FILE_LOCATION = fileLocation;
	}

	public List<StudentDetails> getStudentsDetails() {
		List<StudentDetails> students = new ArrayList<StudentDetails>();
		try {
			FileInputStream file = new FileInputStream(new File(FILE_LOCATION));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			for (int i = 0; i < 2; i++) {

				// Get first/desired sheet from the workbook
				XSSFSheet sheet = workbook.getSheetAt(this.FIRST_SHEET_INDEX + i);

				// Iterate through each rows one by one
				Iterator<Row> rowIterator = sheet.iterator();
				rowIterator.next();
				for (int j = 0; j < this.NUMBER_OF_STUDENTS[i]; j++) {
					StudentDetails student = new StudentDetails();
					Row row = rowIterator.next();

					String name = row.getCell(0).getStringCellValue();
					String emailId = row.getCell(1).getStringCellValue();
					double comment = 0;
					if (row.getCell(2) != null) {
						comment = row.getCell(2).getNumericCellValue();
					}
					double compile = 0;
					if (row.getCell(3) != null) {
						compile = row.getCell(3).getNumericCellValue();
					}
					double execute = 0;
					if (row.getCell(4) != null) {
						execute = row.getCell(4).getNumericCellValue();
					}
					String remarks = null;
					if (row.getCell(6) != null) {
						remarks = row.getCell(6).getStringCellValue();
					}

					student.setName(name);
					student.setEmailId(emailId);
					student.setTotalMarks(new Double(comment + compile + execute).intValue());
					student.setRemarks(remarks);
					
					students.add(student);
				}
				file.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return students;
	}
	
	public static void main(String[] args) {
		int[] nrOfStds = new int[]{36, 32};
		ExcelReader reader = new ExcelReader(0, nrOfStds, "data/CS202_HW1.xlxs");
		
		int i = 0;
		for (StudentDetails student : reader.getStudentsDetails()) {
			i++;
			System.out.println(student.getName() + " --- " + student.getEmailId() + " --- " + student.getTotalMarks() + " --- " + student.getRemarks());
		}
		
		System.out.println("\n\n" + i);
	}
}