package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Elieser
 */
public interface NoDeclaracao {
    
    Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA;
    
    String getNome();
    
}
