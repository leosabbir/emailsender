package com.namobuddha.emailsender.util.cs202;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.namobuddha.emailsender.model.StudentDetails;

public class FinalGradeTemplateReader {
	
	public final String VM_LOCATION;
	
	public FinalGradeTemplateReader(String vmLocation) {
		this.VM_LOCATION = vmLocation;
	}
	
	public String generate(StudentDetails studentDetail, int[] hwColumnsIndices, int[] midtermColumnsIndices, int finalColumn, int finalScoreColumn, int finalGradeColumn) {

		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		
		/* next, get the Template */
		Template t = ve.getTemplate(this.VM_LOCATION);
		
		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		context.put("name", studentDetail.getName());
		//context.put("score", studentDetail.getTotalMarks());
		int scoreCounter = 1;
		String[] marks = studentDetail.getRemarks().split("::");
		for (int index : hwColumnsIndices) {
			context.put("score" + scoreCounter, marks[scoreCounter-1]);
			scoreCounter++;
		}
		
		for (int index : midtermColumnsIndices) {
			context.put("score" + scoreCounter, marks[scoreCounter-1]);
			scoreCounter++;
		}
		
		context.put("score" + scoreCounter, marks[scoreCounter-1]);
		scoreCounter++;
		context.put("score" + scoreCounter, marks[scoreCounter-1]);
		scoreCounter++;
		context.put("score" + scoreCounter, marks[scoreCounter-1]);
		
		
		
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		
		/* show the World */
		//System.out.println(writer.toString());
		
		return writer.toString();

	}

}
