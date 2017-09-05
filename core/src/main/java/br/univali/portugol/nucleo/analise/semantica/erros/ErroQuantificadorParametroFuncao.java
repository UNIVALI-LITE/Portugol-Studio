package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.Quantificador;
import static br.univali.portugol.nucleo.asa.Quantificador.MATRIZ;
import static br.univali.portugol.nucleo.asa.Quantificador.VALOR;
import static br.univali.portugol.nucleo.asa.Quantificador.VETOR;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroQuantificadorParametroFuncao extends ErroSemantico
{
    private NoChamadaFuncao chamadaFuncao;
    private int indiceParametro;
    private String nomeParametro;
    private Quantificador quantificadorParametroEsperado;
    private Quantificador quantificadorParametroPassado;

    public ErroQuantificadorParametroFuncao(NoChamadaFuncao chamadaFuncao, int indiceParametro, String nomeParametro, Quantificador quantificadorParametroEsperado, Quantificador quantificadorParametroPassado)
    {
        super(chamadaFuncao.getParametros().get(indiceParametro).getTrechoCodigoFonte(),"ErroSemantico.ErroQuantificadorParametroFuncao");
        
        this.chamadaFuncao = chamadaFuncao;
        this.indiceParametro = indiceParametro;
        this.nomeParametro = nomeParametro;
        this.quantificadorParametroEsperado = quantificadorParametroEsperado;
        this.quantificadorParametroPassado = quantificadorParametroPassado;
    }

    public String getNomeParametro()
    {
        return nomeParametro;
    }
    
    public int getIndiceParametro()
    {
        return indiceParametro;
    }
    
    public NoChamadaFuncao getChamadaFuncao()
    {
        return chamadaFuncao;
    }

    public Quantificador getQuantificadorParametroEsperado()
    {
        return quantificadorParametroEsperado;
    }

    public Quantificador getQuantificadorParametroPassado()
    {
        return quantificadorParametroPassado;
    }
    
    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorTexto = new StringBuilder();
       
        construtorTexto.append("O parâmetro \"");
        construtorTexto.append(nomeParametro);
        construtorTexto.append("\" da função \"");
        construtorTexto.append(chamadaFuncao.getNome());
        construtorTexto.append("\" espera ");
        construtorTexto.append(obterDefinicao(quantificadorParametroEsperado));
        construtorTexto.append(", mas foi passado ");
        construtorTexto.append(obterDefinicao(quantificadorParametroPassado));
        
        return construtorTexto.toString();
    }
    
    private String obterDefinicao(Quantificador quantificador)
    {
        String definicao = "";
        
        switch (quantificador)
        {
            case VALOR :  definicao = "um valor";   break;
            case VETOR :  definicao = "um vetor";   break;
            case MATRIZ : definicao = "uma matriz"; break;
        }
        
        return definicao;
    }
}
