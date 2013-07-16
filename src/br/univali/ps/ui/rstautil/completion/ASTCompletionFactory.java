package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.NoBitwiseNao;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
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
import br.univali.portugol.nucleo.asa.NoPercorra;
import br.univali.portugol.nucleo.asa.NoReal;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.GerenciadorBibliotecas;
import br.univali.ps.ui.Editor;
import java.util.ArrayList;
import java.util.List;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.ParameterizedCompletion;
import org.fife.ui.autocomplete.VariableCompletion;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ASTCompletionFactory implements VisitanteASA
{
    private List<Completion> completions;
    private CompletionProvider completionProvider;
    private boolean lendoAlias;
    private int nivelASA;
    private boolean declarando;
    private List<ParameterizedCompletion.Parameter> parametros;
    private Editor.EscopoCursor escopoCursor;
    private String escopoASA;

    public List<Completion> createCompletions(ArvoreSintaticaAbstrataPrograma asa, CompletionProvider completionProvider, Editor.EscopoCursor escopoCursor)
    {
        this.completions = new ArrayList<Completion>();

        if (escopoCursor != null && asa != null)
        {
            this.escopoCursor = escopoCursor;
            this.completionProvider = completionProvider;
            this.nivelASA = 1;
            this.declarando = true;
            this.escopoASA = "programa";

            try
            {
                asa.aceitar(this);
            }
            catch (ExcecaoVisitaASA excecaoVisitaASA)
            {
                excecaoVisitaASA.printStackTrace(System.out);
            }
        }

        return this.completions;
    }

    private boolean simboloEstaNoEscopoCursor(NoDeclaracao declaracao)
    {
        return simboloEstaNoEscopoCursor(declaracao.getTrechoCodigoFonteNome());
    }

    private boolean simboloEstaNoEscopoCursor(NoDeclaracaoParametro declaracao)
    {
        return simboloEstaNoEscopoCursor(declaracao.getTrechoCodigoFonteNome());
    }

    private boolean simboloEstaNoEscopoCursor(TrechoCodigoFonte nome)
    {
        // O cursor está fora do escopo do programa

        if (escopoCursor.getProfundidade() == 0 || nome == null)
        {
            return false;
        }
        else // O cursor está dentro de uma função e estamos declarando 
        // os símbolos globais
        if (escopoCursor.getProfundidade() >= 2 && nivelASA == 1)
        {
            return true;
        }
        else
        {
            boolean estaNoMesmoNivel = nivelASA <= escopoCursor.getProfundidade();
            boolean estaAcimaCursor = nome.getLinha() < escopoCursor.getLinha();
            boolean estaNaMesmaLinhaCursor = nome.getLinha() == escopoCursor.getLinha();
            boolean estaAntesCursor = (nome.getColuna() + nome.getTamanhoTexto()) < escopoCursor.getColuna();

            return (estaNoMesmoNivel && (estaAcimaCursor || (estaNaMesmaLinhaCursor && estaAntesCursor)));
        }
    }

    @Override
    public Object visitar(ArvoreSintaticaAbstrataPrograma asap) throws ExcecaoVisitaASA
    {
        if (asap.getListaInclusoesBibliotecas() != null)
        {
            for (NoInclusaoBiblioteca inclusao : asap.getListaInclusoesBibliotecas())
            {
                for (int i = 1; i <= 2; i++)
                {
                    lendoAlias = (i == 2);

                    inclusao.aceitar(this);
                }
            }
        }

        if (asap.getListaDeclaracoesGlobais() != null)
        {
            declarando = true;

            for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais())
            {
                declaracao.aceitar(this);
            }

            declarando = false;

            for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais())
            {
                if (declaracao.getNome().equals(escopoCursor.getNome()))
                {
                    declaracao.aceitar(this);
                }
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
    {
        if (declarando)
        {
            if (simboloEstaNoEscopoCursor(declaracaoFuncao))
            {
                String nome = declaracaoFuncao.getNome();
                String tipo = declaracaoFuncao.getTipoDado().getNome();

                if (declaracaoFuncao.getQuantificador() == Quantificador.VETOR)
                {
                    tipo = tipo.concat("[]");
                }
                else if (declaracaoFuncao.getQuantificador() == Quantificador.MATRIZ)
                {
                    tipo = tipo.concat("[][]");
                }

                parametros = new ArrayList<ParameterizedCompletion.Parameter>();

                for (NoDeclaracaoParametro parametro : declaracaoFuncao.getParametros())
                {
                    parametro.aceitar(this);
                }

                FunctionCompletion functionCompletion = new FunctionCompletion(completionProvider, nome, tipo);
                functionCompletion.setDefinedIn("programa");
                functionCompletion.setRelevance(nivelASA);
                if (!parametros.isEmpty())
                {
                    functionCompletion.setParams(parametros);
                }

                completions.add(functionCompletion);
            }
        }
        else
        {
            int nivelAntigo = nivelASA;

            nivelASA = nivelASA + 1;
            escopoASA = declaracaoFuncao.getNome();

            for (NoDeclaracaoParametro parametro : declaracaoFuncao.getParametros())
            {
                parametro.aceitar(this);
            }

            visitar(declaracaoFuncao.getBlocos());

            nivelASA = nivelAntigo;

            escopoASA = "programa";
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA
    {
        String nome = noDeclaracaoParametro.getNome();
        String tipo = noDeclaracaoParametro.getTipoDado().getNome();

        if (noDeclaracaoParametro.getQuantificador() == Quantificador.VETOR)
        {
            tipo = tipo.concat("[]");
        }
        else if (noDeclaracaoParametro.getQuantificador() == Quantificador.MATRIZ)
        {
            tipo = tipo.concat("[][]");
        }

        if (declarando)
        {
            ParameterizedCompletion.Parameter parameterCompletion = new ParameterizedCompletion.Parameter(tipo, nome);

            if (noDeclaracaoParametro.getModoAcesso() == ModoAcesso.POR_REFERENCIA)
            {
                parameterCompletion.setDescription("passado por referência");
            }
            else
            {
                parameterCompletion.setDescription("passado por valor");
            }

            parametros.add(parameterCompletion);
        }
        else
        {
            if (simboloEstaNoEscopoCursor(noDeclaracaoParametro))
            {
                VariableCompletion variableCompletion = new VariableCompletion(completionProvider, nome, tipo);
                variableCompletion.setRelevance(nivelASA);
                variableCompletion.setDefinedIn(escopoASA);

                completions.add(variableCompletion);
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
    {
        if (simboloEstaNoEscopoCursor(noDeclaracaoMatriz.getTrechoCodigoFonteNome()))
        {
            String nome = noDeclaracaoMatriz.getNome();
            String tipo = noDeclaracaoMatriz.getTipoDado().getNome().concat("[][]");

            VariableCompletion variableCompletion = new VariableCompletion(completionProvider, nome, tipo);
            variableCompletion.setDefinedIn(escopoASA);
            variableCompletion.setRelevance(nivelASA);

            completions.add(variableCompletion);
        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
    {
        if (simboloEstaNoEscopoCursor(noDeclaracaoVariavel.getTrechoCodigoFonteNome()))
        {
            String nome = noDeclaracaoVariavel.getNome();
            String tipo = noDeclaracaoVariavel.getTipoDado().getNome();

            VariableCompletion variableCompletion = new VariableCompletion(completionProvider, nome, tipo);
            variableCompletion.setDefinedIn(escopoASA);
            variableCompletion.setRelevance(nivelASA);

            completions.add(variableCompletion);
        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
    {
        if (simboloEstaNoEscopoCursor(noDeclaracaoVetor.getTrechoCodigoFonteNome()))
        {
            String nome = noDeclaracaoVetor.getNome();
            String tipo = noDeclaracaoVetor.getTipoDado().getNome().concat("[]");

            VariableCompletion variableCompletion = new VariableCompletion(completionProvider, nome, tipo);
            variableCompletion.setDefinedIn(escopoASA);
            variableCompletion.setRelevance(nivelASA);

            completions.add(variableCompletion);
        }
        return null;
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
    {
        try
        {
            TrechoCodigoFonte trecho = (lendoAlias) ? noInclusaoBiblioteca.getTrechoCodigoFonteAlias() : noInclusaoBiblioteca.getTrechoCodigoFonteNome();

            if (simboloEstaNoEscopoCursor(trecho))
            {
                GerenciadorBibliotecas.getInstance().obterMetaDadosBiblioteca(noInclusaoBiblioteca.getNome());

                String nome = (lendoAlias) ? noInclusaoBiblioteca.getAlias() : noInclusaoBiblioteca.getNome();
                String tipo = "Biblioteca";

                if (nome != null)
                {
                    VariableCompletion variableCompletion = new VariableCompletion(completionProvider, nome, tipo);
                    variableCompletion.setDefinedIn("programa");

                    completions.add(variableCompletion);
                }
            }
        }
        catch (ErroCarregamentoBiblioteca erroCarregamentoBiblioteca)
        {
            // Se não conseguiu carregar a biblioteca, nem cria ou autocomplete
        }

        return null;
    }

    private void visitar(List<NoBloco> nos) throws ExcecaoVisitaASA
    {
        if (nos != null)
        {
            for (NoBloco no : nos)
            {
                no.aceitar(this);
            }
        }
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
    {
        int nivelAnterior = nivelASA;

        nivelASA = nivelASA + 1;
        visitar(noEnquanto.getBlocos());
        nivelASA = nivelAnterior;

        return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
    {
        int nivelAnterior = nivelASA;

        nivelASA = nivelASA + 1;

        for (NoCaso caso : noEscolha.getCasos())
        {
            caso.aceitar(this);
        }

        nivelASA = nivelAnterior;

        return null;
    }

    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
    {
        int nivelAnterior = nivelASA;

        nivelASA = nivelASA + 1;

        visitar(noCaso.getBlocos());

        nivelASA = nivelAnterior;

        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
    {
        int nivelAnterior = nivelASA;

        nivelASA = nivelASA + 1;

        visitar(noFacaEnquanto.getBlocos());

        nivelASA = nivelAnterior;

        return null;
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
    {
        int nivelAnterior = nivelASA;

        nivelASA = nivelASA + 1;

        noPara.getInicializacao().aceitar(this);

        visitar(noPara.getBlocos());

        nivelASA = nivelAnterior;

        return null;
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
    {
        int nivelAnterior = nivelASA;

        nivelASA = nivelASA + 1;

        visitar(noSe.getBlocosFalsos());
        visitar(noSe.getBlocosVerdadeiros());

        nivelASA = nivelAnterior;

        return null;
    }

    @Override
    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoPercorra noPercorra) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA
    {
        return null;
    }
}
