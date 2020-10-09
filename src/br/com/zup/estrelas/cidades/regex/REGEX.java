package br.com.zup.estrelas.cidades.regex;

public class REGEX {
	public static final String NOME = "[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]*";

	public static final String ESTADO = "[A-Z]{2}";

	public static final String CEP = "[0-9]{8}";

	public static final String FORMATO_NUMERICO = "[0-9]*";

	public static final String DATA = "((15|16|17|18|19|20)[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";
}
