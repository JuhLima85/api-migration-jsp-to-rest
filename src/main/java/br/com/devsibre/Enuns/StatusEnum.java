package br.com.devsibre.Enuns;

public enum StatusEnum {

	TODOS,
	MEMBRO,
	NAO_MEMBRO;

	public static StatusEnum from(String s) {
		if (s == null) return TODOS;
		return switch (s.toLowerCase()) {
			case "membro" -> MEMBRO;
			case "naomembro", "naoMembro", "nao_membro", "nao" -> NAO_MEMBRO;
			default -> TODOS;
		};
	}

}
