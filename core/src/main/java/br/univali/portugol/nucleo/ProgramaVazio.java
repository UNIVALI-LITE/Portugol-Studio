package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.programa.Programa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Fernando Noschang
 */
final public class ProgramaVazio extends Programa
{
    private static final ProgramaVazio INSTANCIA = new ProgramaVazio();

    private ProgramaVazio()
    {
        inicializaASA();
    }

    public static Programa novaInstancia()
    {
        return new ProgramaVazio();
    }
    
    public static Programa getInstancia()
    {
        return INSTANCIA;
    }

    private void inicializaASA()
    {
        ASAPrograma asa = new ASAPrograma();
        List<NoDeclaracao> declaracoesGlobais = new ArrayList<>();
        declaracoesGlobais.add(new NoDeclaracaoFuncao("inicio", TipoDado.VAZIO, Quantificador.VALOR));
        asa.setListaDeclaracoesGlobais(declaracoesGlobais);
        setArvoreSintaticaAbstrata(asa);
    }

    @Override
    protected void executar(String[] parametros) throws ErroExecucao, InterruptedException
    {
        throw new ErroExecucao()
        {
            @Override
            protected String construirMensagem()
            {
                return "Este programa não é executável";
            }
        };
    }
}
