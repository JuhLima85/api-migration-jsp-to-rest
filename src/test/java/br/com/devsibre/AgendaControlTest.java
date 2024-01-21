//package br.com.devsibre;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import br.com.devsibre.Domain.Entity.Agenda;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//import br.com.devsibre.Controller.AgendaControl;
//import br.com.devsibre.Service.Inteface.AgendaService;
//
//@ExtendWith(MockitoExtension.class)
//class AgendaControlTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private AgendaService agendaService;
//
//    @InjectMocks
//    private AgendaControl agendaControl;
//
//    @BeforeEach
//    void setup() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".html");
//
//        mockMvc = MockMvcBuilders.standaloneSetup(agendaControl)
//                .setViewResolvers(viewResolver)
//                .build();
//    }
//
//    @Test
//    void testGetAgenda() throws Exception {
//        List<Agenda> agendas = new ArrayList<>();
//        agendas.add(new Agenda(1L, "Agenda 1", null, LocalDate.now(), null));
//        agendas.add(new Agenda(2L, "Agenda 2", null, LocalDate.now(), null));
//        when(agendaService.listAll()).thenReturn(agendas);
//
//        mockMvc.perform(get("/agendas"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("agenda.html"))
//                .andExpect(model().attribute("agenda", agendas));
//    }
//
//    @Test
//    void testGetAgendaDetails() throws Exception {
//        long agendaId = 1L;
//        Agenda agenda = new Agenda(agendaId, "Agenda 1", null, LocalDate.now(), null);
//        when(agendaService.getById(agendaId)).thenReturn(agenda);
//
//        mockMvc.perform(get("/agendas/{id}", agendaId))
//                .andExpect(status().isOk())
//                .andExpect(view().name("agendaDetails.html"))
//                .andExpect(model().attribute("agenda", agenda));
//    }
//
//    @Test
//    void testSaveAgenda_ValidAgenda() throws Exception {
//        Agenda agenda = new Agenda();
//        agenda.setId(1L);
//        agenda.setAutor("Nova Agenda");
//        agenda.setData(LocalDate.now());
//
//        mockMvc.perform(post("/newagenda")
//                .flashAttr("agendas", agenda))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/agendas"));
//
//        verify(agendaService, times(1)).saveOrUpdate(agenda);
//    }
//
//    @Test
//    void testSaveAgenda_InvalidAgenda() throws Exception {
//        Agenda agenda = new Agenda();
//        agenda.setAutor(""); // Campo obrigatório não preenchido
//
//        mockMvc.perform(post("/newagenda")
//                .flashAttr("agendas", agenda))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/newagenda"))
//                .andExpect(flash().attributeExists("mensagem"));
//
//        verify(agendaService, never()).saveOrUpdate(agenda);
//    }
//
//    @Test
//    void testExcluir() throws Exception {
//        long agendaId = 1L;
//
//        mockMvc.perform(get("/remover/{id}", agendaId))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/agendas"));
//
//        verify(agendaService, times(1)).delete(agendaId);
//    }
//}
//
