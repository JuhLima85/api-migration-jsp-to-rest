package br.com.devsibre.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.devsibre.UtilsReports.Cantina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.devsibre.Service.CantinaServiceImpl;
import br.com.devsibre.UtilsReports.Cantina_reports;

import static br.com.devsibre.UtilsReports.ModelAuthentication_Report.addAuthenticationStatusToModel;

@Controller
public class CantinaControl {

	 @Autowired
	    private CantinaServiceImpl cadService;
	    
	    @Autowired
	    private Cantina_reports candreport;

	    @Autowired
	    private ServletContext context;
	    
	    //Metodo para listar debitos
	    @RequestMapping(method = RequestMethod.GET, value="/listacantina")
	    public ModelAndView listarCantina(Model model, Authentication authentication){
			addAuthenticationStatusToModel(model, authentication);
	      ModelAndView v = new ModelAndView("lista_cantina.html");
	      List<Cantina> cantina = new ArrayList<>();
	      cantina = cadService.listAll();
	      v.addObject("cantina", cantina);
	        return v;  
	    }
	    
	     //Metodo para incluir novo cadastro
	    @RequestMapping(method = RequestMethod.GET, value = "/novo_debito")
	    public ModelAndView novoCadastro(Model model, Authentication authentication) {
			addAuthenticationStatusToModel(model, authentication);
	        ModelAndView v = new ModelAndView("fiado_cantina.html");
	        v.addObject(new Cantina());
	        v.setViewName("fiado_cantina");
	        return v;
	    }

	    //Metodo para salvar debitos
	    @RequestMapping(method = RequestMethod.POST, value = "/salvar_debito")
	    public String salvar(Cantina c, Model model, Authentication authentication) {
			addAuthenticationStatusToModel(model, authentication);
	        cadService.saveOrUpdate(c);
	        return "redirect:/novo_debito";
	    }
	    
	    //Metodo para alterar débitos  
	    @GetMapping("/editeDebito/{id_n}")
	    public String editar(@PathVariable long id_n, Model m, Model model, Authentication authentication) {
			addAuthenticationStatusToModel(model, authentication);
	        Cantina cant = cadService.getId(id_n);
	        m.addAttribute("cant", cant);
	        return "editarDebitos";
	    }

	    //Metodo para alterar débitos
	    @RequestMapping(value = "/editsaveCantina", method = RequestMethod.POST)
	    public ModelAndView editsaveCantina(@ModelAttribute("cant") Cantina emp, Model model, Authentication authentication) {
			addAuthenticationStatusToModel(model, authentication);
	        boolean idd = Boolean.getBoolean("id_n");
	        idd = cadService.alterar(emp);
	        return new ModelAndView("redirect:/listacantina");
	    }

	    //Metodo para excluir dados do débitos
	    @GetMapping("/apagaDebito/{id_n}")
	    public String remover(@PathVariable long id_n, Model model, Authentication authentication) {
			addAuthenticationStatusToModel(model, authentication);
	        cadService.delete(id_n);
	        return "redirect:/listacantina";
	    }
	    
	    //Metodo para listar todos os débitos pagos
	    @RequestMapping(method = RequestMethod.GET, value="/lista_pagos")
	    public ModelAndView listarPagos(Model model, Authentication authentication){
		  addAuthenticationStatusToModel(model, authentication);
	      ModelAndView v = new ModelAndView("lista_pagos.html");
	      List<Cantina> pagos = new ArrayList<>();
	      pagos = cadService.listAll();
	      v.addObject("pagos", pagos);
	        return v;  
	    }
	    
	    //Metodo para excluir dados do débitos
	    @GetMapping("/apagaPagos/{id_n}")
	    public String remove_Pagos(@PathVariable long id_n, Model model, Authentication authentication) {
			addAuthenticationStatusToModel(model, authentication);
	        cadService.delete(id_n);
	        return "redirect:/lista_pagos";
	    }
	    
	    @GetMapping(value = "/pdf_cantina")
	    public void createPdf(HttpServletRequest request, HttpServletResponse response, Model model, Authentication authentication) {
			addAuthenticationStatusToModel(model, authentication);
	        List<Cantina> cant = cadService.listAll();
	        boolean isFlag = candreport.creatPdf2(cant, context, request, response);
	        if (isFlag) {
	            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "cant" + ".pdf");
	            filedownload(fullPath, response, "cant.pdf");
	        }
	    }
	    
	     @GetMapping(value = "/Exls_cantina")
	    public void createExcel2(HttpServletRequest request, HttpServletResponse response, Model model, Authentication authentication) {
			addAuthenticationStatusToModel(model, authentication);
	        List<Cantina> cant = cadService.listAll();
	        boolean isFlag = candreport.createExcel2(cant, context, request, response);
	    }

	    private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
	        File file = new File(fullPath);

	        final int BUFFER_SIZE = 4096;
	        if (file.exists()) {
	            try {
	                FileInputStream inputStream = new FileInputStream(file);
	                String mimeType = context.getMimeType(fullPath);
	                response.setContentType(mimeType);
	                response.setHeader("content-disposition", "attachment; filename=" + fileName);
	                OutputStream outputStream = response.getOutputStream();

	                byte[] buffer = new byte[BUFFER_SIZE];
	                int bytesRead = -1;
	                while ((bytesRead = inputStream.read(buffer)) != -1) {
	                    outputStream.write(buffer, 0, bytesRead);
	                }
	                inputStream.close();
	                outputStream.close();
	                file.delete();
	            } catch (Exception e) {
	            }
	        }
	    }
}
