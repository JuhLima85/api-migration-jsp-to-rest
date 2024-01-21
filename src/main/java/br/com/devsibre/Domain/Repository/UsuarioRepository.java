package br.com.devsibre.Domain.Repository;//package br.com.devsibre.Repository;

import br.com.devsibre.Domain.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String>{

	Usuario findByLogin(String login);
}
