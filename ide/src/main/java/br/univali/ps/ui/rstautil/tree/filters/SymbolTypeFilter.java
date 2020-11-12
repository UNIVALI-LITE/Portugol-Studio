package br.univali.ps.ui.rstautil.tree.filters;

import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoBitwiseNao;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoContinue;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoEscolha;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoLogico;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoMenosUnario;
import br.univali.portugol.nucleo.asa.NoNao;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseE;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseLeftShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseOu;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseRightShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseXOR;
import br.univali.portugol.nucleo.asa.NoOperacaoDivisao;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaDiferenca;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaE;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaIgualdade;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaior;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaiorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenor;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaOU;
import br.univali.portugol.nucleo.asa.NoOperacaoModulo;
import br.univali.portugol.nucleo.asa.NoOperacaoMultiplicacao;
import br.univali.portugol.nucleo.asa.NoOperacaoSoma;
import br.univali.portugol.nucleo.asa.NoOperacaoSubtracao;
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoPare;
import br.univali.portugol.nucleo.asa.NoReal;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoSenao;
import br.univali.portugol.nucleo.asa.NoTitulo;
import br.univali.portugol.nucleo.asa.NoVaPara;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class SymbolTypeFilter implements ASTFilter
{
    public static enum SymbolType
    {
        VARIAVEL, VETOR, MATRIZ, FUNCAO
    };

    private final List<SymbolType> acceptedSymbolTypes = new ArrayList<>();
    private final List<SymbolTypeFilterListener> listeners = new ArrayList<>();
    private final SymbolTypeVisitor visitor = new SymbolTypeVisitor();

    private boolean acceptConstants = true;
    private boolean acceptVariables = true;

    public SymbolTypeFilter(boolean acceptAll)
    {
        if (acceptAll)
        {
            acceptAll();
        }
    }

    public void addListener(SymbolTypeFilterListener listener)
    {
        if (!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }

    public void removeListener(SymbolTypeFilterListener listener)
    {
        if (listeners.contains(listener))
        {
            listeners.remove(listener);
        }
    }

    private void fireSymbolTypeAccepted(SymbolType symbolType)
    {
        for (SymbolTypeFilterListener listener : listeners)
        {
            listener.symbolTypeAccepted(symbolType);
        }
    }

    private void fireSymbolTypeRejected(SymbolType symbolType)
    {
        for (SymbolTypeFilterListener listener : listeners)
        {
            listener.symbolTypeRejected(symbolType);
        }
    }

    private void fireConstantsRejected()
    {
        for (SymbolTypeFilterListener listener : listeners)
        {
            listener.constantsRejected();
        }
    }

    private void fireVariablesRejected()
    {
        for (SymbolTypeFilterListener listener : listeners)
        {
            listener.variablesRejected();
        }
    }

    private void fireConstantsAccepted()
    {
        for (SymbolTypeFilterListener listener : listeners)
        {
            listener.constantsAccepted();
        }
    }

    private void fireVariablesAccepted()
    {
        for (SymbolTypeFilterListener listener : listeners)
        {
            listener.variablesAccepted();
        }
    }

    public void acceptAll()
    {
        rejectAll();

        List<SymbolType> types = Arrays.asList(new SymbolType[]
        {
            SymbolType.VARIAVEL, SymbolType.VETOR, SymbolType.MATRIZ, SymbolType.FUNCAO

        });

        for (SymbolType type : types)
        {
            accept(type);
        }

        acceptConstants();
        acceptVariables();
    }

    public void rejectAll()
    {
        List<SymbolType> types = new ArrayList<>(acceptedSymbolTypes);

        for (SymbolType symbolType : types)
        {
            reject(symbolType);
        }

        acceptVariables();
        acceptConstants();
    }

    public void accept(SymbolType symbolType)
    {
        if (!acceptedSymbolTypes.contains(symbolType))
        {
            acceptedSymbolTypes.add(symbolType);
            fireSymbolTypeAccepted(symbolType);
        }
    }

    public void reject(SymbolType symbolType)
    {
        if (acceptedSymbolTypes.contains(symbolType))
        {
            acceptedSymbolTypes.remove(symbolType);
            fireSymbolTypeRejected(symbolType);
        }
    }

    public void rejectConstants()
    {
        acceptConstants = false;
        fireConstantsRejected();
    }

    public void rejectVariables()
    {
        acceptVariables = false;
        fireVariablesRejected();
    }

    public void acceptVariables()
    {
        acceptVariables = true;
        fireVariablesAccepted();
    }

    public void acceptConstants()
    {
        acceptConstants = true;
        fireConstantsAccepted();
    }

    public boolean isAccepting(SymbolType symbolType)
    {
        return acceptedSymbolTypes.contains(symbolType);
    }

    public boolean isAcceptingConstants()
    {
        return acceptConstants;
    }

    public boolean isAcceptingVariables()
    {
        return acceptVariables;
    }

    public boolean isAcceptingAll()
    {
        return isAcceptingConstants() && isAcceptingVariables() && isAccepting(SymbolType.MATRIZ) && isAccepting(SymbolType.FUNCAO) && isAccepting(SymbolType.VARIAVEL) && isAccepting(SymbolType.VETOR);
    }

    public List<SymbolType> getAcceptedSymbolTypes()
    {
        return new ArrayList<>(acceptedSymbolTypes);
    }

    @Override
    public boolean accepts(No no)
    {
        try
        {
            return (Boolean) no.aceitar(visitor);
        }
        catch (ExcecaoVisitaASA ex)
        {
            Logger.getLogger(SymbolTypeFilter.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }

    private final class SymbolTypeVisitor implements VisitanteASA
    {
        @Override
        public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
        {
            return acceptedSymbolTypes.contains(SymbolType.FUNCAO);
        }

        @Override
        public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
        {
            if ((noDeclaracaoMatriz.constante() && acceptConstants) || (!noDeclaracaoMatriz.constante() && acceptVariables))
            {
                return acceptedSymbolTypes.contains(SymbolType.MATRIZ);
            }

            return false;
        }

        @Override
        public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA
        {
            if (acceptVariables)
            {
                boolean isVariable = (noDeclaracaoParametro.getQuantificador() == Quantificador.VALOR && acceptedSymbolTypes.contains(SymbolType.VARIAVEL));
                boolean isArray = (noDeclaracaoParametro.getQuantificador() == Quantificador.VETOR && acceptedSymbolTypes.contains(SymbolType.VETOR));
                boolean isMatrix = (noDeclaracaoParametro.getQuantificador() == Quantificador.MATRIZ && acceptedSymbolTypes.contains(SymbolType.MATRIZ));

                return isVariable || isArray || isMatrix;
            }

            return false;
        }

        @Override
        public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
        {
            if ((noDeclaracaoVariavel.constante() && acceptConstants) || (!noDeclaracaoVariavel.constante() && acceptVariables))
            {
                return acceptedSymbolTypes.contains(SymbolType.VARIAVEL);
            }

            return false;
        }

        @Override
        public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
        {
            if ((noDeclaracaoVetor.constante() && acceptConstants) || (!noDeclaracaoVetor.constante() && acceptVariables))
            {
                return acceptedSymbolTypes.contains(SymbolType.VETOR);
            }

            return false;
        }

        @Override
        public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoNao noNao) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoPare noPare) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoReal noReal) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoVaPara noVaPara) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
        {
            return false;
        }

        @Override
        public Object visitar(NoSenao noSenao) throws ExcecaoVisitaASA {
            return false;
        }
    }
}
