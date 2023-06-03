package com.ait.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ait.entity.CitizenPlan;
import com.ait.request.SearchRequest;

public interface ReportService {

	public List<String> getPlanNames();

	public List<String> getPlanStatus();

	public List<CitizenPlan> search(SearchRequest request);

	public boolean exportExcel(HttpServletResponse response) throws Exception;

	public boolean exportPdf(HttpServletResponse response) throws Exception;

}
