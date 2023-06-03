package com.ait.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.ait.entity.CitizenPlan;

@Component
public class ExcelGenerator {
	
	public void generator(HttpServletResponse response , List<CitizenPlan> plans ,File f ) throws Exception {
		
		//List<CitizenPlan> records = planRepo.findAll();

		Workbook workbook = new XSSFWorkbook(); // file extension will be xlsx
		// Workbook workbook = new HSSFWorkbook(); //file extension will be xls
		Sheet sheet = workbook.createSheet("plan-data");
		Row headerRow = sheet.createRow(0);

		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("CITIZEN NAME");
		headerRow.createCell(2).setCellValue("PLAN NAME");
		headerRow.createCell(3).setCellValue("PLAN STATUS");
		headerRow.createCell(4).setCellValue("PLAN START DATE");
		headerRow.createCell(5).setCellValue("PLAN END DATE");
		headerRow.createCell(6).setCellValue("BENEFITS AMT");

		int dataRowIndex = 1;

		for (CitizenPlan plan : plans) {

			Row dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(plan.getCitizenId());
			dataRow.createCell(1).setCellValue(plan.getCitizenName());
			dataRow.createCell(2).setCellValue(plan.getPlanName());
			dataRow.createCell(3).setCellValue(plan.getPlanStatus());

			if (null != plan.getPlanStartDate()) {
				dataRow.createCell(4).setCellValue(plan.getPlanStartDate() + "");
			} else {
				dataRow.createCell(4).setCellValue("N/A");
			}
			if (null != plan.getPlanEndDate()) {
				dataRow.createCell(5).setCellValue(plan.getPlanEndDate() + "");
			} else {
				dataRow.createCell(5).setCellValue("N/A");
			}

			if (null != plan.getBenefitAmt() && !"".equals(plan.getBenefitAmt())) {
				dataRow.createCell(6).setCellValue(plan.getBenefitAmt());
			} else {
				dataRow.createCell(6).setCellValue("N/A");
			}
			dataRowIndex++;
		}
		
		FileOutputStream fos = new FileOutputStream(f);
		workbook.write(fos);
		fos.close();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
	}

}
