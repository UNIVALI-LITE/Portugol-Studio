package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.CarregadorBibliotecas;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import java.util.ArrayList;
import java.util.List;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.ParameterizedCompletion;
import org.fife.ui.autocomplete.VariableCompletion;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ASTCompletionFactory extends VisitanteASABasico
{
    private List<Completion> completions;
    private CompletionProvider completionProvider;
    private boolean lendoAlias;

    public List<Completion> createCompletions(ArvoreSintaticaAbstrataPrograma ast, CompletionProvider completionProvider)
    {
        this.completionProvider = completionProvider;
        this.completions = new ArrayList<Completion>();

        try
        {
            ast.aceitar(this);
        }
        catch (ExcecaoVisitaASA excecaoVisitaASA)
        {
            excecaoVisitaASA.printStackTrace(System.out);
        }

        return this.completions;
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
                    
                    Completion completion = (Completion) inclusao.aceitar(this);

                    if (completion != null)
                    {
                        completions.add(completion);
                    }
                }
            }
        }

        if (asap.getListaDeclaracoesGlobais() != null)
        {
            for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais())
            {
                completions.add((Completion) declaracao.aceitar(this));
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
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

        List parametros = new ArrayList();

        for (NoDeclaracaoParametro parametro : declaracaoFuncao.getParametros())
        {
            parametros.add(parametro.aceitar(this));
        }

        FunctionCompletion functionCompletion = new FunctionCompletion(completionProvider, nome, tipo);
        functionCompletion.setDefinedIn("programa");

        functionCompletion.setParams(parametros);

        return functionCompletion;
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

        ParameterizedCompletion.Parameter parameterCompletion = new ParameterizedCompletion.Parameter(tipo, nome);

        if (noDeclaracaoParametro.getModoAcesso() == ModoAcesso.POR_REFERENCIA)
        {
            parameterCompletion.setDescription("passado por referência");
        }
        else
        {
            parameterCompletion.setDescription("passado por valor");
        }

        return parameterCompletion;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
    {
        String nome = noDeclaracaoMatriz.getNome();
        String tipo = noDeclaracaoMatriz.getTipoDado().getNome().concat("[][]");

        VariableCompletion variableCompletion = new VariableCompletion(completionProvider, nome, tipo);
        variableCompletion.setDefinedIn("programa");

        return variableCompletion;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
    {
        String nome = noDeclaracaoVariavel.getNome();
        String tipo = noDeclaracaoVariavel.getTipoDado().getNome();

        VariableCompletion variableCompletion = new VariableCompletion(completionProvider, nome, tipo);
        variableCompletion.setDefinedIn("programa");

        return variableCompletion;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
    {
        String nome = noDeclaracaoVetor.getNome();
        String tipo = noDeclaracaoVetor.getTipoDado().getNome().concat("[]");

        VariableCompletion variableCompletion = new VariableCompletion(completionProvider, nome, tipo);
        variableCompletion.setDefinedIn("programa");

        return variableCompletion;
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
    {
        try
        {
            CarregadorBibliotecas.carregarBiblioteca(noInclusaoBiblioteca.getNome());
            
            String nome = (lendoAlias)? noInclusaoBiblioteca.getAlias() : noInclusaoBiblioteca.getNome();
            String tipo = "Biblioteca";

            if (nome != null)
            {
                VariableCompletion variableCompletion = new VariableCompletion(completionProvider, nome, tipo);
                variableCompletion.setDefinedIn("programa");

                return variableCompletion;
            }
        }
        catch (ErroCarregamentoBiblioteca erroCarregamentoBiblioteca)
        {
            // Se não conseguiu carregar a biblioteca, nem cria ou autocomplete
        }
        
        return null;
    }    
}
