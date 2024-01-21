package br.com.devsibre.Controller;

import java.util.ArrayList;
import java.util.List;

import br.com.devsibre.Domain.Entity.Patrimonio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.devsibre.Service.PatrimonioServiceImpl;

@Controller
public class PatrimonioControl {

	 @Autowired
	    private PatrimonioServiceImpl psl;
	    
	    //Metodo para listar todos os cadastros
	    @RequestMapping(method = RequestMethod.GET, value="/lista_patrimonio")
	    public ModelAndView listarPatrimonio(){
	      ModelAndView v = new ModelAndView("lista_patrimonio");
	      List<Patrimonio> pat = new ArrayList<>();
	      pat = psl.listAll();
	      v.addObject("pat", pat);
	        return v;  
	    }
	    
	     //Metodo para incluir novo cadastro
	    @RequestMapping(method = RequestMethod.GET, value = "/novo_Patrimonio")
	    public ModelAndView novo_Invent() {
	        ModelAndView v = new ModelAndView("patrimonio.html");
	        v.addObject(new Patrimonio());
	        v.setViewName("patrimonio");
	        return v;
	    }

	    //Metodo para salvar debitos
	    @RequestMapping(method = RequestMethod.POST, value = "/salvar_Patrimonio")
	    public String salvar_Invent(Patrimonio c) {
	        psl.saveOrUpdate(c);
	        return "redirect:/novo_Patrimonio";
	    }
	    
	    //Metodo para alterar débitos  
	    @GetMapping("/editePatrimonio/{id_p}")
	    public String editarInvent(@PathVariable long id_p, Model m) {
	    	Patrimonio pts = psl.getId(id_p);
	        m.addAttribute("pts", pts);
	        return "editaPatrimonio";
	    }

	    //Metodo para alterar débitos
	    @RequestMapping(value = "/editsavePatrimonio", method = RequestMethod.POST)
	    public ModelAndView editsavePatrimonio(@ModelAttribute("patri") Patrimonio patri) {
	        boolean idd = Boolean.getBoolean("id_p");
	        idd = psl.alterar(patri);
	        return new ModelAndView("redirect:/lista_patrimonio");
	    }

	        
	    //Metodo para excluir dados do débitos
	    @GetMapping("/apagaPatrimonio/{id_p}")
	    public String remove_Inventario(@PathVariable long id_p) {
	        psl.delete(id_p);
	        return "redirect:/lista_patrimonio";
	    }
}
