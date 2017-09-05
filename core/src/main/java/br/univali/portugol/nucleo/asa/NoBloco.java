package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.Programa;
import java.util.logging.Logger;

/*
 *  NOTA: o nome NoBloco não está legal. Parecia uma boa idéia no início, mas acho que
 *  NoComando ou NoInstrucao fica melhor. 
 * 
 *  Se for alterado aqui, lembrar de alterar a documentação e a gramática para usarem o 
 *  o novo nome 
 */
/**
 * Classe base para todos os nós da ASA que representam blocos de comandos.
 * <p>
 * No Portugol cada linha do código fonte é considerado um bloco de comandos. Um
 * bloco de comandos por sua vez, pode conter outros blocos de comandos e assim
 * por diante.
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 *
 * @see NoCaso
 * @see NoDeclaracao
 * @see NoEnquanto
 * @see NoEscolha
 * @see NoExpressao
 * @see NoFacaEnquanto
 * @see NoPara
 * @see NoPare
 * @see NoPercorra
 * @see NoRetorne
 * @see NoSe
 */
public abstract class NoBloco extends No
{
    private static final Logger LOGGER = Logger.getLogger(NoBloco.class.getName());
    
    protected static final TrechoCodigoFonte TRECHO_NULO = new TrechoCodigoFonte(-1, -1, 0);
    protected TrechoCodigoFonte trechoCodigoFonte = TRECHO_NULO;
    
    
    public TrechoCodigoFonte getTrechoCodigoFonte()
    {
        if(trechoCodigoFonte == TRECHO_NULO){
            //LOGGER.warning("trechoDoCodigo fonte NULO em " + getClass().getName());
        }
        return trechoCodigoFonte;
    }

    public void setTrechoCodigoFonte(TrechoCodigoFonte trechoCodigoFonte)
    {
        this.trechoCodigoFonte = trechoCodigoFonte;
    }
    
    @Override
    public boolean ehParavel(Programa.Estado estado)
    {
        return super.ehParavel(estado) || estado == Programa.Estado.STEP_OVER;
    }
}
