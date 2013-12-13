package br.univali.ps;

import br.univali.portugol.corretor.dinamico.model.Questao;

/**
 *
 * @author Elieser
 */
public interface ParserDeQuestao {
    
    Questao getQuestao(String conteudoDoArquivoPex) throws Exception;
    
}
