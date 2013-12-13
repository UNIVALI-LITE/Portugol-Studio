package br.univali.ps.exception;

/**
 *
 * @author Elieser
 */
public class CarregamentoDeExercicioException extends Exception {

    private static final long serialVersionUID = 1L;

    private String urlDoArquivoDeExercicio;

    public CarregamentoDeExercicioException(String urlDoArquivoDeExercicio) {
        this(urlDoArquivoDeExercicio, null);
    }

    public CarregamentoDeExercicioException(String urlDoArquivoDeExercicio, Throwable cause) {
        super("Não foi possível carregar o arquivo do exercício: " + urlDoArquivoDeExercicio + "\n" + cause.getMessage(), cause);
        this.urlDoArquivoDeExercicio = urlDoArquivoDeExercicio;
    }

}
