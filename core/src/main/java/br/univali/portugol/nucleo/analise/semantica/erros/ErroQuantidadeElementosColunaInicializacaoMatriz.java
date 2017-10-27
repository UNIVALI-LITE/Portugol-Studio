package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroQuantidadeElementosColunaInicializacaoMatriz extends ErroSemantico {
    
    private final int linha;
    private final String nome;
    private final int numeroElementosEsperados;
    private final int numeroElementosDeclarados;

    public ErroQuantidadeElementosColunaInicializacaoMatriz(TrechoCodigoFonte trechoCodigoFonte, String nome, int linha, int numeroElementosEsperados, int numeroElementosDeclarados)
    {
        super(trechoCodigoFonte);
        this.linha = linha;
        this.nome = nome;
        this.numeroElementosEsperados = numeroElementosEsperados;
        this.numeroElementosDeclarados = numeroElementosDeclarados;
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorTexto = new StringBuilder();

        construtorTexto.append("A linha [");
        construtorTexto.append(linha);
        construtorTexto.append("] na inicialização da matriz \"");
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
