package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Fillipi Domingos Pelz
 * @author Luiz Fernando Noschang
 */
public final class ErroQuantidadeElementosInicializacaoVetor extends ErroSemantico
{
    private String nome;
    private int numeroElementosEsperados;
    private int numeroElementosDeclarados;

    public ErroQuantidadeElementosInicializacaoVetor(TrechoCodigoFonte trechoCodigoFonte, String nome, int numeroElementosEsperados, int numeroElementosDeclarados)
    {
        super(trechoCodigoFonte,"ErroSemantico.ErroQuantidadeElementosInicializacaoVetor");
        
        this.nome = nome;
        this.numeroElementosEsperados = numeroElementosEsperados;
        this.numeroElementosDeclarados = numeroElementosDeclarados;
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorTexto = new StringBuilder();

        construtorTexto.append("A inicialização do vetor \"");
        construtorTexto.append(nome);
        construtorTexto.append("\" deve possuir ");
        construtorTexto.append(numeroElementosEsperados);
        construtorTexto.append(" elemento");
        
        if (numeroElementosEsperados > 1)
        {
            construtorTexto.append("s");
        }
        
        int diferenca = 0;
        
        if (numeroElementosDeclarados > numeroElementosEsperados)
        {
            diferenca = numeroElementosDeclarados - numeroElementosEsperados;
            construtorTexto.append(". Remova ");
        }
        else if (numeroElementosDeclarados < numeroElementosEsperados)
        {
            diferenca = numeroElementosEsperados - numeroElementosDeclarados;            
            construtorTexto.append(". Insira mais ");
        }
        
        construtorTexto.append(diferenca);
        construtorTexto.append(" elemento");

            
        if (diferenca > 1)
        {
            construtorTexto.append("s");
        }
        
        construtorTexto.append(" para corrigir o problema");
        
        return construtorTexto.toString();
    }    
}
