package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 * Erro gerado pelo analisador semântico quando o número de parâmetros passado durante uma chamada de função
 * é diferente do número de parâmetros esperados.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 */
public final class ErroNumeroParametrosFuncao extends ErroSemantico
{
    private int numeroParametrosPassados;
    private int numeroParametrosEsperados;

    private NoChamadaFuncao chamadaFuncao;

    /**
     * 
     * @param numeroParametrosPassados      o número de parâmetros que foi passado na chamada da função.
     * @param numeroParametrosEsperados     o número de parãmetros que eram esperados pela função.
     * @param funcao                        a função que foi chamada.
     * @param chamadaFuncao                 o nó da ASA correspondente à chamada da função.
     */    
    public ErroNumeroParametrosFuncao(int numeroParametrosPassados, int numeroParametrosEsperados, NoChamadaFuncao chamadaFuncao)
    {
        super(chamadaFuncao.getTrechoCodigoFonteNome(), "ErroSemantico.ErroNumeroParametrosFuncao");

        this.numeroParametrosPassados = numeroParametrosPassados;
        this.numeroParametrosEsperados = numeroParametrosEsperados;

        this.chamadaFuncao = chamadaFuncao;
    }

    /**
     * Obtém o número de parãmetros que eram esperados pela função.
     * 
     * @return     o número de parãmetros que eram esperados pela função.
     * @since 1.0
     */
    public int getNumeroParametrosEsperados()
    {
        return numeroParametrosEsperados;
    }

    /**
     * Obtém o número de parâmetros que foi passado na chamada da função.
     * 
     * @return     o número de parâmetros que foi passado na chamada da função.
     * @since 1.0
     */
    public int getNumeroParametrosPassados()
    {
        return numeroParametrosPassados;
    }
    
    /**
     * Obtém o nó da ASA correspondente à chamada da função.
     * 
     * @return     o nó da ASA correspondente à chamada da função.
     * @since 1.0
     */
    public NoChamadaFuncao getChamadaFuncao()
    {
        return chamadaFuncao;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorString = new StringBuilder();
        String nomeFuncao =  chamadaFuncao.getNome();

        construtorString.append("A função \"");
        construtorString.append(nomeFuncao);
        construtorString.append("\"");        
        
        if (numeroParametrosEsperados == 0)
        {
            construtorString.append(" não espera nenhum parâmetro, ");
        }
        else
            
        if (numeroParametrosEsperados == Integer.MAX_VALUE)
        {
            construtorString.append(" espera ao menos um parâmetro, ");
        }
        else
        {        
            construtorString.append(" espera ");
            construtorString.append(numeroParametrosEsperados);

            if (numeroParametrosEsperados == 1)
            {
                construtorString.append(" parâmetro, ");
            }
            else
            {
                construtorString.append(" parâmetros, ");
            }
        }
        
        if (numeroParametrosPassados == 0)
        {
            construtorString.append("mas não foi passado nenhum parâmetro");
        }
        else
        {
            if (numeroParametrosPassados == 1)
            {
                construtorString.append("mas foi passado ");
            }
            else 
            {
                construtorString.append("mas foram passados ");
            }

            if (numeroParametrosEsperados > numeroParametrosPassados)
            {
                construtorString.append("apenas ");
            }
            
            construtorString.append(numeroParametrosPassados);

            if (numeroParametrosPassados == 1)
            {
                construtorString.append(" parâmetro.");
            }
            else 
            {
                construtorString.append(" parâmetros.");
            }
        }

        return construtorString.toString();
    }
}