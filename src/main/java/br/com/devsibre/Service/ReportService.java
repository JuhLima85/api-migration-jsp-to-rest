package br.com.devsibre.Service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.devsibre.Model.FormularioModel;

public interface ReportService {
	
	 public boolean creatPdf(List<FormularioModel> cad, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	    
	   boolean createExcel(List<FormularioModel> cad, ServletContext context, HttpServletRequest request, HttpServletResponse response);

}
