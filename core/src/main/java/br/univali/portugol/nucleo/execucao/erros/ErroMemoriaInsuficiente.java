package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroMemoriaInsuficiente extends ErroExecucao
{
	private String codigo = "ErroExecucao.ErroMemoriaInsuficiente";
	
	public ErroMemoriaInsuficiente() {
		super.setCodigo(codigo);
	}
	
    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorTexto = new StringBuilder();
        
        construtorTexto.append("O programa atingiu a quantidade máxima de memória RAM permitida.");
        construtorTexto.append("\n");
        construtorTexto.append("Verifique se o programa não está utilizando muitos recursos (imagens, sons, etc) e se estes recursos não são muito grandes.");
        
        
        return construtorTexto.toString();
        
    }
    
}
