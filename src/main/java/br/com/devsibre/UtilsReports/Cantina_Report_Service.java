package br.com.devsibre.UtilsReports;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.devsibre.UtilsReports.Cantina;

public interface Cantina_Report_Service {

	 public boolean creatPdf2(List<Cantina> cant, ServletContext context, HttpServletRequest request, HttpServletResponse response);

	    boolean createExcel2(List<Cantina> cant, ServletContext context, HttpServletRequest request, HttpServletResponse response);
}
