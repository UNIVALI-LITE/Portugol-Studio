package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Luiz Fernando Noschang
 */
public abstract class VisitanteASABasico implements VisitanteASA
{
    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }
    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }
 
    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }
    
    @Override
    public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }    
    
    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }
    
    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoSenao noSenao) throws ExcecaoVisitaASA 
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }
    
    @Override
    public Object visitar(NoVaPara noVaPara) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }
    @Override
    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
    {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }

    @Override
    public Object visitar(NoParametroFuncao noParametroFuncao) throws ExcecaoVisitaASA {
        throw new ExcecaoVisitaASA(new UnsupportedOperationException("Esta operação ainda não foi implementada. " + this.getClass() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName()), null, null) ;
    }    
}
