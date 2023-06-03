package com.ait.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.ait.entity.CitizenPlan;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class PdfGenerator {
	
	public void generator(HttpServletResponse response, List<CitizenPlan> plans, File f) throws Exception {
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream(f));
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		// Paragraph p = new Paragraph("Citizen Plan Info");

		// Creating font
		// Setting font style and size
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		// Creating paragraph
		Paragraph p = new Paragraph("Citizen Plan Info", fontTiltle);

		// Aligning the paragraph in document
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);

		PdfPTable table = new PdfPTable(7);
		table.setSpacingBefore(5);

		table.addCell("Id");
		table.addCell("Citizen Name");
		table.addCell("Plan Name");
		table.addCell("Plan Status");
		table.addCell("Plan Start Date");
		table.addCell("Plan End Date");
		table.addCell("Benefit Amt");

		//List<CitizenPlan> plans = planRepo.findAll();

		for (CitizenPlan plan : plans) {
			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(plan.getCitizenName());
			table.addCell(plan.getPlanName());
			table.addCell(plan.getPlanStatus());

			if (null != plan.getPlanStartDate()) {
				table.addCell(plan.getPlanStartDate() + "");
			} else {
				table.addCell("N/A");
			}
			if (null != plan.getPlanEndDate()) {
				table.addCell(plan.getPlanEndDate() + "");
			} else {
				table.addCell("N/A");
			}
			if (null != plan.getBenefitAmt()) {
				table.addCell(String.valueOf(plan.getBenefitAmt()));
			} else {
				table.addCell("N/A");
			}
		}

		document.add(table);
		document.close();
		
	}

}
