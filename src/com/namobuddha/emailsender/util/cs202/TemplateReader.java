package com.namobuddha.emailsender.util.cs202;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.namobuddha.emailsender.model.StudentDetails;

public class TemplateReader {
	
	public final String VM_LOCATION;
	
	public TemplateReader(String vmLocation) {
		this.VM_LOCATION = vmLocation;
	}
	
	public String generate(StudentDetails studentDetail) {

		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		
		/* next, get the Template */
		Template t = ve.getTemplate(this.VM_LOCATION);
		
		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		context.put("name", studentDetail.getName());
		context.put("score", studentDetail.getTotalMarks());
		context.put("remark", studentDetail.getRemarks());
		
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		
		/* show the World */
		//System.out.println(writer.toString());
		
		return writer.toString();

	}

}
