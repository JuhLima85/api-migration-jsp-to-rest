package br.com.devsibre.util;

public final class Mensagens {

    private Mensagens() {
        // Impede instanciação
    }

    // ==================================================
    // 👤 PESSOA
    // ==================================================
    public static final class pessoa {
        public static final String NAO_EXISTE = "Pessoa não encontrada.";
        public static final String RELACIONAMENTO_DUPLICADO = "Esse vínculo já existe.";
    }

    // ==================================================
    // 👤 USUÁRIO
    // ==================================================
    public static final class Usuario {
        public static final String USUARIO_DUPLICADO = "Já existe um usuário com este nome de cadastrado.";
        public static final String EMAIL_DUPLICADO = "Já existe um usuário com este e-mail cadastrado.";
        public static final String SEM_ROLE = "Pelo menos um papel deve ser informado para o usuário.";
        public static final String ADMIN_IMEXCLUIVEL = "❌ Não é permitido excluir o usuário administrativo principal (sibre-admin).";
        public static final String UNICO_ADMIN = "⚠️ Não é possível excluir o único administrador do sistema.";
        public static final String FALHA_LISTAR = "Falha ao listar usuários.";
        public static final String FALHA_CRIAR = "Erro ao criar usuário.";
        public static final String FALHA_ATUALIZAR = "Falha ao atualizar usuário.";
        public static final String FALHA_EXCLUIR = "Falha ao excluir usuário.";
        public static final String FALHA_SENHA = "Falha ao atualizar senha.";
        public static final String SENHA_ATUALIZADA = "🔑 Senha do usuário atualizada com sucesso!";
        public static final String SUCESSO_CRIAR = "Usuário criado com sucesso no Keycloak!";
        public static final String SUCESSO_ATUALIZAR = "Usuário atualizado com sucesso!";
        public static final String SUCESSO_EXCLUIR = "Usuário excluído com sucesso!";
        public static final String CAMPO_VAZIO = "É obrigatório preencher todos os campos antes de cadastrar o usuário.";
    }

    // ==================================================
    // 🔐 TOKEN / AUTENTICAÇÃO
    // ==================================================
    public static final class Token {
        public static final String NULO = "❌ Token administrativo retornou nulo. Verifique as credenciais do client no Keycloak.";
        public static final String INVALIDO = "❌ Falha ao obter token administrativo. Verifique client_id e client_secret.";
    }

    // ==================================================
    // ⚙️ SISTEMA / ERROS GERAIS
    // ==================================================
    public static final class Sistema {
        public static final String ERRO_INESPERADO = "Ocorreu um erro inesperado. Tente novamente mais tarde.";
        public static final String ERRO_COMUNICACAO = "Falha ao comunicar com o serviço externo.";
        public static final String OPERACAO_NAO_PERMITIDA = "Operação não permitida.";
        public static final String DADOS_INVALIDOS = "Os dados fornecidos são inválidos.";
    }
}
