package com.ait.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ait.entity.CitizenPlan;
import com.ait.repo.CitizenPlanRepository;
import com.ait.request.SearchRequest;
import com.ait.util.EmailUtils;
import com.ait.util.ExcelGenerator;
import com.ait.util.PdfGenerator;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository planRepo;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private ExcelGenerator excelGenerator;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public List<String> getPlanNames() {
		return planRepo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {
		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {

		CitizenPlan entity = new CitizenPlan();

		if (null != request.getPlanName() && !"".equals(request.getPlanName())) {
			entity.setPlanName(request.getPlanName());
		}
		if (null != request.getPlanStatus() && !"".equals(request.getPlanStatus())) {
			entity.setPlanStatus(request.getPlanStatus());
		}
		if (null != request.getGender() && !"".equals(request.getGender())) {
			entity.setGender(request.getGender());
		}
		if (null != request.getStartDate() && !"".equals(request.getStartDate())) {

			String startDate = request.getStartDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// convert String to LocalDate
			LocalDate localDate = LocalDate.parse(startDate, formatter);

			entity.setPlanStartDate(localDate);
		}

		if (null != request.getEndDate() && !"".equals(request.getEndDate())) {

			String endDate = request.getEndDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// convert String to LocalDate
			LocalDate localDate = LocalDate.parse(endDate, formatter);

			entity.setPlanStartDate(localDate);
		}

		return planRepo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {

		File f = new File("Plan.xlsx");
		List<CitizenPlan> plans = planRepo.findAll();

		excelGenerator.generator(response, plans, f);

		String subject = "Test the subject";
		String body = "<h1>Test the body</h1>";
		String to = "mrperfectboyajayaj@gmail.com";

		emailUtils.sendEmail(subject, body, to, f);
		f.delete();

		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {

		File f = new File("Plan.pdf");
		List<CitizenPlan> plans = planRepo.findAll();

		pdfGenerator.generator(response, plans, f);

		String subject = "Test the subject";
		String body = "<h1>Test the body</h1>";
		String to = "mrperfectboyajayaj@gmail.com";

		emailUtils.sendEmail(subject, body, to, f);
		f.delete();

		return true;
	}

}
