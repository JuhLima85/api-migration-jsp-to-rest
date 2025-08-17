package br.com.devsibre.UtilsReports;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.devsibre.Domain.Entity.Formulario;

public interface Formulario_Report_Service {

	public boolean creatPdf(List<Formulario> cad, ServletContext context, HttpServletRequest request, HttpServletResponse response, String membroFilter);

    boolean createExcel(List<Formulario> cad, ServletContext context, HttpServletRequest request, HttpServletResponse response);
}
