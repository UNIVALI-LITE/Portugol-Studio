package br.univali.portugol.nucleo.analise;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.asa.ASA;

/**
 * Esta classe provê uma fachada (Facade) que abstrai o processo de análise do código fonte.
 * <p>
 * Esta abstração é feita através do método {@link AnalisadorAlgoritmo#analisar(java.lang.String) }, o qual instancia e
 * chama na ordem correta os objetos responsáveis pela análise sintática e pela análise semântica.
 * <p>
 * A unificação das duas análises fica totalmente transparente aos objetos que utilizam a fachada.
 * 
 * @author Luiz Fernando Noschang
 * @since 1.0
 * 
 * @see AnalisadorSemantico
 * @see AnalisadorSintatico 
 */
public final class AnalisadorAlgoritmo
{
    private ASA arvoreSintaticaAbstrata;
    
    public AnalisadorAlgoritmo()
    {
        
    }

    /**
     * Obtém a ASA gerada durante a análise.
     * 
     * @return     a ASA gerada durante a análise.
     * @since 1.0
     */
    public ASA getASA() 
    {
        return arvoreSintaticaAbstrata;
    }
    
    /**
     * 
     * Realiza a análise sintática e semântica de um código fonte.
     * 
     * @param codigo     o código fonte a ser analisado.
     * @return           um objeto contendo informações sobre a análise relizada.
     */    
    public ResultadoAnalise analisar(String codigo)
    {
        ObservadorAnaliseAlgoritmo observadorAnaliseAlgoritmo = new ObservadorAnaliseAlgoritmo();
        AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico();
        AnalisadorSemantico analisadorSemantico = new AnalisadorSemantico();
                
        analisadorSintatico.adicionarObservador(observadorAnaliseAlgoritmo);
        analisadorSemantico.adicionarObservador(observadorAnaliseAlgoritmo);
        
        ASA asa = analisadorSintatico.analisar(codigo);
        arvoreSintaticaAbstrata = asa;
        analisadorSemantico.analisar(asa);
        
        return observadorAnaliseAlgoritmo.getResultadoAnalise();
    }
}