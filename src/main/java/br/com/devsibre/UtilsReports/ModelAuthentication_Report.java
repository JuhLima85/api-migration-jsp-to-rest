package br.com.devsibre.UtilsReports;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public class ModelAuthentication_Report {

    /**
     * Este método verifica se o usuário está autenticado. Caso o usuário esteja
     * autenticado, adiciona o atributo "isAuthenticated" ao modelo com o valor
     * true; caso contrário, adiciona com o valor false. Esse atributo pode ser
     * utilizado nas views para condicionar a exibição de elementos baseados no
     * estado de autenticação do usuário.
     */
    public static void addAuthenticationStatusToModel(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
    }
}
