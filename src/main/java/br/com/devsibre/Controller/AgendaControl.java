package br.com.devsibre.Controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.devsibre.Service.Inteface.AgendaService;
import br.com.devsibre.Domain.Entity.Agenda;

@Controller
public class AgendaControl {

	@Autowired
	private AgendaService Pservice;

	@RequestMapping(method = RequestMethod.GET, path = "/entrar")
	public String entrar() {
		return "entrar";
	}

	@GetMapping({"/index" })
	public String Incio() {
		return "index";
	}

	// Metodo para listar posts da agenda sem Edição
	@RequestMapping(value = "/agendas", method = RequestMethod.GET)
	public ModelAndView getAgenda() {
		ModelAndView mv = new ModelAndView("agenda.html");
		List<Agenda> agendas = Pservice.listAll();
		mv.addObject("agenda", agendas);
		return mv;
	}

	// Metodo para listar posts da agenda sem Edição
	@RequestMapping(value = "/agendas_User", method = RequestMethod.GET)
	public ModelAndView getAgenda_User() {
		ModelAndView mv = new ModelAndView("agenda_User.html");
		List<Agenda> agendas = Pservice.listAll();
		mv.addObject("agenda", agendas);
		return mv;
	}

	// Admin
	@RequestMapping(value = "/agendas/{id}", method = RequestMethod.GET)
	public ModelAndView getAgendaDetails(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView("agendaDetails.html");
		Agenda agenda = Pservice.getById(id);
		mv.addObject("agenda", agenda);
		return mv;
	}

	// User
	@RequestMapping(value = "/agendas_User/{id}", method = RequestMethod.GET)
	public ModelAndView getAgendaDetails_User(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView("agendaDetails_User.html");
		Agenda agenda = Pservice.getById(id);
		mv.addObject("agenda", agenda);
		return mv;
	}

	@RequestMapping(value = "/newagenda", method = RequestMethod.GET)
	public String getAgendaForm() {
		return "agendaForm";
	}

	@RequestMapping(value = "/newagenda", method = RequestMethod.POST)
	public String saveAgenda(@Valid Agenda agendas, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
			return "redirect:/newagenda";
		}

		agendas.setData(LocalDate.now());
		Pservice.saveOrUpdate(agendas);
		return "redirect:/agendas";
	}

//   Metodo para excluir dados do cadastro
	@GetMapping("/remover/{id}")
	public String excluir(@PathVariable long id) {
		Pservice.delete(id);
		return "redirect:/agendas";
	}
}
