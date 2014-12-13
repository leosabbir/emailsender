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
public class FinalExamExcelReader {

	private final int FIRST_SHEET_INDEX;
	private final int[] NUMBER_OF_STUDENTS;
	private final String FILE_LOCATION;
	private final int[] HW_COLUMNS_INDICES;
	private final int[] MIDTERM_COLUMNS_INDICES;
	private final int FINAL_COLUMN_INDEX;
	private final int FINAL_SCORE_COLUMN_INDEX;
	private final int FINAL_GRADE_COLUMN_INDEX;
	
	

	public FinalExamExcelReader(int firstSheetIndex, int[] nrOfStds, int[] hw_columns, int[] midterm_columns,
			int final_column, int final_score_column, int final_grade_column, String fileLocation) {
		this.FIRST_SHEET_INDEX = firstSheetIndex;
		this.NUMBER_OF_STUDENTS = nrOfStds;
		this.HW_COLUMNS_INDICES = hw_columns;
		this.MIDTERM_COLUMNS_INDICES = midterm_columns;
		this.FINAL_COLUMN_INDEX = final_column;
		this.FINAL_SCORE_COLUMN_INDEX = final_score_column;
		this.FINAL_GRADE_COLUMN_INDEX = final_grade_column;
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
				rowIterator.next();
				for (int j = 0; j < this.NUMBER_OF_STUDENTS[i]; j++) {
					StudentDetails student = new StudentDetails();
					Row row = rowIterator.next();

					StringBuilder sb = new StringBuilder();
					double score;
					for (int index : this.HW_COLUMNS_INDICES) {
						try {
							score = row.getCell(index).getNumericCellValue();
						} catch (Exception e) {
							score = 0;
						}
						sb.append(score);
						sb.append("::");
					}
					
					for (int index : this.MIDTERM_COLUMNS_INDICES) {
						try {
							score = row.getCell(index).getNumericCellValue();
						} catch (Exception e) {
							score = 0;
						}
						sb.append(score);
						sb.append("::");
					}
					
					try {
						score = row.getCell(this.FINAL_COLUMN_INDEX).getNumericCellValue();
					} catch (Exception e) {
						score = 0;
					}
					sb.append(score);
					sb.append("::");
					
					try {
						score = row.getCell(this.FINAL_SCORE_COLUMN_INDEX).getNumericCellValue();
					} catch (Exception e) {
						score = 0;
					}
					sb.append(score);
					sb.append("::");
					
					String grade;
					grade = row.getCell(this.FINAL_GRADE_COLUMN_INDEX).getStringCellValue();
					sb.append(grade);
					
					String name = row.getCell(2).getStringCellValue();
					String emailId = row.getCell(1).getStringCellValue();
					String remarks = null;
					remarks = sb.toString();

					student.setName(name);
					student.setEmailId(emailId);
					student.setTotalMarks(-1);
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
}