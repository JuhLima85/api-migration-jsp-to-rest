package br.com.devsibre;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import br.com.devsibre.Controller.CantinaControl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CantinaControlTest {

//    @Test
//    void testListarCantina() {
//        CantinaControl cantinaControl = new CantinaControl();
//        ModelAndView modelAndView = cantinaControl.listarCantina();
//
//        assertEquals("lista_cantina.html", modelAndView.getViewName());
//    }

    @Test
    void testNovoCadastro() {
        CantinaControl cantinaControl = new CantinaControl();
        ModelAndView modelAndView = cantinaControl.novoCadastro();

        assertEquals("fiado_cantina", modelAndView.getViewName());
    }

  

}

