package br.com.devsibre.Service.Inteface;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.devsibre.Domain.Entity.Formulario;

public interface ReportService {
	
	 public boolean creatPdf(List<Formulario> cad, ServletContext context, HttpServletRequest request, HttpServletResponse response);
	    
	   boolean createExcel(List<Formulario> cad, ServletContext context, HttpServletRequest request, HttpServletResponse response);

}
