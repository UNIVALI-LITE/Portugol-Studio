package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public final class ErroQuantidadeLinhasIncializacaoMatriz extends ErroSemantico
{
    private String nome;
    private int numeroLinhasEsperadas;
    private int numeroLinhasDeclaradas;

    public ErroQuantidadeLinhasIncializacaoMatriz(TrechoCodigoFonte trechoCodigoFonte, String nome, int numeroLinhasEsperadas, int numeroLinhasDeclaracadas)
    {
        super(trechoCodigoFonte);
        this.nome = nome;
        this.numeroLinhasEsperadas = numeroLinhasEsperadas;
        this.numeroLinhasDeclaradas = numeroLinhasDeclaracadas;
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorTexto = new StringBuilder();

        construtorTexto.append("A inicialização da matriz \"");
        construtorTexto.append(nome);
        construtorTexto.append("\" deve possuir ");
        construtorTexto.append(numeroLinhasEsperadas);
        construtorTexto.append(" linha");
        
        if (numeroLinhasEsperadas > 1)
        {
            construtorTexto.append("s");
        }
        
        int diferenca = 0;
        
        if (numeroLinhasDeclaradas > numeroLinhasEsperadas)
        {
            diferenca = numeroLinhasDeclaradas - numeroLinhasEsperadas;
            construtorTexto.append(". Remova ");
        }
        else if (numeroLinhasDeclaradas < numeroLinhasEsperadas)
        {
            diferenca = numeroLinhasEsperadas - numeroLinhasDeclaradas;
            construtorTexto.append(". Insira mais ");
        }
        
        construtorTexto.append(diferenca);
        construtorTexto.append(" linha");

            
        if (diferenca > 1)
        {
            construtorTexto.append("s");
        }
        
        construtorTexto.append(" para corrigir o problema");
        
        return construtorTexto.toString();
    }
}
