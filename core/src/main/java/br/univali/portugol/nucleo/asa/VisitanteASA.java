package br.univali.portugol.nucleo.asa;

/**
 * Esta interface serve como base para a implementação de um objeto caminhador da ASA
 * utilizando o padrão visitor.
 * 
 * @author Fillipi Domingos Pelz
 * @author Luiz Fernando Noshchang
 * 
 * @version 1.0
 */
public interface VisitanteASA
{
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA;

    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA;

    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA;

    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA;

    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA;
    
    public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA;

    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA;

    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA;

    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA;

    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA;
    
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA;

    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA;

    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA;
    
    public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA;

    public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA;

    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA;

    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA;

    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA;

    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA;

    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA;
    
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA;
    
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA;
       
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA;

    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA;

    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA;

    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA;

    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA;

    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA;

    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA;

    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA;
    
    public Object visitar(NoSenao noSenao) throws ExcecaoVisitaASA;
    
    public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA;
    
    public Object visitar(NoVaPara noVaPara) throws ExcecaoVisitaASA;

    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA;

    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA;
    
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA;
}
