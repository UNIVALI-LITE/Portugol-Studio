package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author noschang
 */
public class ErroFuncaoInicioInexistente extends ErroSemantico
{
    
    private String codigo = "ErroSemantico.ErroFuncaoInicioInexistente";
            
    public ErroFuncaoInicioInexistente(int tamanhoTotalCodigo)
    {
        super(montarTrechoCodigoFonte(tamanhoTotalCodigo));
        super.setCodigo(codigo);
    }
    
    private static TrechoCodigoFonte montarTrechoCodigoFonte(int tamanhoTotalCodigo)
    {
        return new TrechoCodigoFonte(1, 1, tamanhoTotalCodigo);
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorMensagem = new StringBuilder();
        
        construtorMensagem.append("A função \"");
        construtorMensagem.append("inicio");
        construtorMensagem.append("\" não existe no seu código. Ela é necessária pois será a primeira a ser chamada na execução do código");
        
        return construtorMensagem.toString();
    }
}
