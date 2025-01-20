package br.com.devsibre.Controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.devsibre.Service.Inteface.AgendaService;
import br.com.devsibre.Domain.Entity.Agenda;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static br.com.devsibre.UtilsReports.ModelAuthentication_Report.addAuthenticationStatusToModel;

@Tag(name = "Agenda", description = "API para operações da agenda")
@Controller
public class AgendaControl {

	@Autowired
	private AgendaService Pservice;

	@GetMapping("/")
	public String home(Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/entrar")
	public String entrar(Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		return "entrar";
	}

	@GetMapping({ "/bem_vindo" })
	public String Incio(Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		return "bem_vindo";
	}

	@RequestMapping(value = "/agendas", method = RequestMethod.GET)
	public ModelAndView getAgenda(Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		ModelAndView mv = new ModelAndView("agenda.html");
		List<Agenda> agendas = Pservice.listAll();
		mv.addObject("agenda", agendas);
		return mv;
	}

	@RequestMapping(value = "/agendas_User", method = RequestMethod.GET)
	public ModelAndView getAgenda_User(Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		ModelAndView mv = new ModelAndView("agenda_User.html");
		List<Agenda> agendas = Pservice.listAll();
		mv.addObject("agenda", agendas);
		return mv;
	}
	
	@RequestMapping(value = "/agendas/{id}", method = RequestMethod.GET)
	public ModelAndView getAgendaDetails(@PathVariable("id") long id, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		ModelAndView mv = new ModelAndView("agendaDetails.html");
		Agenda agenda = Pservice.getById(id);
		mv.addObject("agenda", agenda);
		return mv;
	}

	@RequestMapping(value = "/agendas_User/{id}", method = RequestMethod.GET)
	public ModelAndView getAgendaDetails_User(@PathVariable("id") long id, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		ModelAndView mv = new ModelAndView("agendaDetails_User.html");
		Agenda agenda = Pservice.getById(id);
		mv.addObject("agenda", agenda);
		return mv;
	}

	@RequestMapping(value = "/newagenda", method = RequestMethod.GET)
	public String getAgendaForm(Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		return "agendaForm";
	}

	@RequestMapping(value = "/newagenda", method = RequestMethod.POST)
	public String saveAgenda(@Valid Agenda agendas, BindingResult result, RedirectAttributes attributes, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
			return "redirect:/newagenda";
		}

		agendas.setData(LocalDate.now());
		Pservice.saveOrUpdate(agendas);
		return "redirect:/agendas";
	}

	@GetMapping("/remover/{id}")
	public String excluir(@PathVariable long id,  Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		Pservice.delete(id);
		return "redirect:/agendas";
	}
}
