/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author noschang
 */
public class ErroFuncaoInicioNaoAceitaParametros extends ErroSemantico
{
    private final NoDeclaracaoFuncao declaracao;
            
    public ErroFuncaoInicioNaoAceitaParametros(NoDeclaracaoFuncao declaracao)
    {
        super(montarTrechoCodigoFonte(declaracao));
        
        this.declaracao = declaracao;
    }
    
    private static TrechoCodigoFonte montarTrechoCodigoFonte(NoDeclaracaoFuncao declaracao)
    {
        TrechoCodigoFonte trechoPrimeiroParametro = declaracao.getParametros().get(0).getTrechoCodigoFonte();
        TrechoCodigoFonte trechoUltimoParametro = declaracao.getParametros().get(declaracao.getParametros().size() - 1).getTrechoCodigoFonte();
        int tamanho;
        
//        if (declaracao.getParametros().size() == 1)
//        {
//            tamanho = trechoPrimeiroParametro.getTamanhoTexto();
//        }
//        else
//        {
//            
//        }        
        tamanho = (trechoUltimoParametro.getColuna() + trechoUltimoParametro.getTamanhoTexto()) - trechoPrimeiroParametro.getColuna(); // Assume que os parâmetros estejam todos na mesma linha
        
        return new TrechoCodigoFonte(trechoPrimeiroParametro.getLinha(), trechoPrimeiroParametro.getColuna(), tamanho);
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorMensagem = new StringBuilder();
        
        construtorMensagem.append("A função \"");
        construtorMensagem.append(declaracao.getNome());
        construtorMensagem.append("\" não aceita a passagem de parâmetros. Por favor, remova todos os parâmetros da função");
        
        return construtorMensagem.toString();
    }
}
