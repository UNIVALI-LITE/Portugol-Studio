package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
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
    private NoDeclaracaoBase declaracao;
    private NoExpressao tamanho;
    private String codigo = "ErroSemantico.ErroTamanhoVetorMatriz.";
        
    public ErroTamanhoVetorMatriz(NoDeclaracaoBase declaracao, NoExpressao tamanho)
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
        	codigo += "1";
        	super.setCodigo(codigo);
        	
            return String.format("O tamanho do vetor '%s' deve ser um valor, uma constante ou uma variavel inteira declarada em escopo local e positiva \n Ex: vetor[3] ou leia(n) e inteiro vetor[n]", declaracao.getNome());
        }
        else
        {            
            String aux = (tamanho == ((NoDeclaracaoMatriz) declaracao).getNumeroLinhas())? "linhas" : "colunas";
            codigo += "2";
            super.setCodigo(codigo);
            
            return String.format("O número de %s da matriz '%s' deve ser um valor ou uma constante do tipo inteiro e positivo \n Ex: matriz[5][4]", aux, declaracao.getNome());
        }
    }
}
