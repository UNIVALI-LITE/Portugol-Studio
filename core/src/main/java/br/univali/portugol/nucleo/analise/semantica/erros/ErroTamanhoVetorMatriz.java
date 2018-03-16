package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroTamanhoVetorMatriz extends ErroSemantico 
{
    private NoDeclaracao declaracao;
    private NoExpressao tamanho;
        
    public ErroTamanhoVetorMatriz(NoDeclaracao declaracao, NoExpressao tamanho)
    {
        super(tamanho.getTrechoCodigoFonte(),"ErroSemantico.ErroTamanhoVetorMatriz");
        this.declaracao = declaracao;
        this.tamanho = tamanho;
    }    

    @Override
    protected String construirMensagem()
    {
        if (declaracao instanceof NoDeclaracaoVetor)
        {
            return String.format("O tamanho do vetor '%s' deve ser um valor ou uma constante do tipo inteiro", declaracao.getNome());
        }
        else
        {            
            String aux = (tamanho == ((NoDeclaracaoMatriz) declaracao).getNumeroLinhas())? "linhas" : "colunas";
            
            return String.format("O número de %s da matriz '%s' deve ser um valor ou uma constante do tipo inteiro", aux, declaracao.getNome());
        }
    }
}
