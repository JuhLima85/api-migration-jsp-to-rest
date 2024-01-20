//package br.com.devsibre;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import br.com.devsibre.Enuns.StatusEnum;
//import br.com.devsibre.Model.FilhoModel;
//import br.com.devsibre.Model.FormularioModel;
//import br.com.devsibre.ServiceImpl.FormularioServiceImpl;
//
//@SpringBootTest
//class DevsibreApplicationTests {
//
//    @Autowired
//    private FormularioServiceImpl formularioService;    
//
//    @Test
//    void contextLoads() {
//        FormularioModel formulario = criarFormulario();
//      formularioService.saveOrUpdate(formulario);      
//
//    }
//
//    private FormularioModel criarFormulario() {
//        FormularioModel formulario = new FormularioModel();
//        formulario.setNome("Ciclano2");
//        formulario.setFone("369852741");
//        formulario.setEmail("beltrano@teste.com");
//        formulario.setData("01/02/03");
//        formulario.setUf("DF");
//        formulario.setCep("72601108");
//        formulario.setBairro("Recanto das Emas");
//        formulario.setLocalidade("Brasilia");
//        formulario.setLogradouro("Quadra 101");
//        formulario.setStatus("Membro");
//
////        ConjugeModel familia = criarFamilia();
////        formulario.setFamilia(familia);
//
//        return formulario;
//    }
//
////    private ConjugeModel criarFamilia() {
////        ConjugeModel familia = new ConjugeModel();
////        familia.setNomeDoConjuge("Larissa Gomes");
////        familia.setTelefone("159852357");
////        familia.setEmail("larissa@test.com");
////        familia.setdataNascEsp("02/03");
////        familia.setQuantidadeFilhos(3);
////        familia.setStatus(StatusEnum.Membro);
////        familia.setSeBatizado(StatusEnum.Batizado);
////
////        List<FilhoModel> filhos = new ArrayList<>();
////
////        FilhoModel filho1 = criarFilho("1 Filho do Ciclano ", " 01/01", " 123456789", " filho1@test.com ", StatusEnum.Membro, StatusEnum.Batizado, familia);
////        filhos.add(filho1);
////
////        FilhoModel filho2 = criarFilho("2 Filho do Ciclano ", " 02/02", " 987654321", " filho2@test.com ", StatusEnum.NaoMembro, StatusEnum.NaoBatizado, familia);
////        filhos.add(filho2);
//////
////        FilhoModel filho3 = criarFilho("3 Filho do Ciclano ", " 03/03", " 789665412", " filho3@test.com ", StatusEnum.Membro, StatusEnum.Batizado, familia);
////        filhos.add(filho3);
////
////        familia.setFilhos(filhos);
////
////        return familia;
////    }
////
////    private FilhoModel criarFilho(String nome, String dataNascProl, String telefone, String email, StatusEnum seBatizado, StatusEnum status, ConjugeModel familia) {
////        FilhoModel filho = new FilhoModel();
////        filho.setNome(nome);
////        filho.setDataNascProl(dataNascProl);
////        filho.setTelefone(telefone);
////        filho.setEmail(email);
////        filho.setStatus(status);
////        filho.setSeBatizado(seBatizado);
////        filho.setFamilia(familia);
////     
////        return filho;
////    }   
//       
//}
