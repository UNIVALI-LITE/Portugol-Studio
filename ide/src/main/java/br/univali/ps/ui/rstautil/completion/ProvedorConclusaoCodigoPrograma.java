package br.univali.ps.ui.rstautil.completion;

import static br.univali.ps.ui.rstautil.completion.ParametroConclusaoASA.TipoConclusao.ATRIBUICAO_VALOR;
import static br.univali.ps.ui.rstautil.completion.ParametroConclusaoASA.TipoConclusao.INDICE_VETOR;
import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCaso;
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
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.GerenciadorBibliotecas;
import br.univali.portugol.nucleo.asa.VisitanteNulo;
import br.univali.ps.ui.editor.PSCompletionListCellRenderer;
import br.univali.ps.ui.utils.EscopoCursor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.text.JTextComponent;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ParameterizedCompletion;
import org.fife.ui.autocomplete.TemplateCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ProvedorConclusaoCodigoPrograma extends DefaultCompletionProvider
{
    private final FabricaConclusaoCodigoPrograma fabricaConclusaoCodigo;
    private boolean habilitado = true;

    public ProvedorConclusaoCodigoPrograma()
    {
        this.fabricaConclusaoCodigo = new FabricaConclusaoCodigoPrograma();
        this.setParameterizedCompletionParams('(', ", ", ')');
        
        setListCellRenderer(new PSCompletionListCellRenderer());
    }    

    public void setHabilitado(boolean habilitado)
    {
        this.habilitado = habilitado;
    }

    public void atualizar(Programa programa)
    {
        clear();
        addCompletions(fabricaConclusaoCodigo.criarConclusoes(programa.getArvoreSintaticaAbstrata()));
    }

    @Override
    public List<Completion> getCompletions(JTextComponent textArea)
    {
        if (habilitado)
        {
            EscopoCursor escopoCursor = EscopoCursor.localizar((RSyntaxTextArea) textArea);
            List<Completion> conclusoes = getCompletionsImpl(textArea);
            Iterator<Completion> iterador = conclusoes.iterator();

            while (iterador.hasNext())
            {
                if (!conclusaoNoEscopoCursor((ConclusaoASA) iterador.next(), escopoCursor))
                {
                    iterador.remove();
                }
            }

            return conclusoes;
        }
        
        return Collections.emptyList();
    }

    final class FabricaConclusaoCodigoPrograma extends VisitanteNulo
    {
        private List<Completion> completions;
        private boolean lendoAlias;
        private int nivelASA;
        private boolean declarando;
        private List<ParameterizedCompletion.Parameter> parametros;
        private String local;

        public List<Completion> criarConclusoes(ASAPrograma asa)
        {
            this.completions = new ArrayList<>();

            if (asa != null)
            {
                this.nivelASA = 1;
                this.declarando = true;
                this.local = "programa";

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

        @Override
        public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA
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
                    if (declaracao instanceof NoDeclaracaoFuncao){
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
                TrechoCodigoFonte trecho = declaracaoFuncao.getTrechoCodigoFonteNome();

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

                parametros = new ArrayList<>();

                for (NoDeclaracaoParametro parametro : declaracaoFuncao.getParametros())
                {
                    parametro.aceitar(this);
                }

                ConclusaoFuncaoASA functionCompletion = new ConclusaoFuncaoASA(ProvedorConclusaoCodigoPrograma.this, nome, tipo, nivelASA, trecho, "programa");
                functionCompletion.setDefinedIn("programa");
                functionCompletion.setRelevance(nivelASA);

                if (!parametros.isEmpty())
                {
                    functionCompletion.setParams(parametros);
                }

                completions.add(functionCompletion);
            }
            else
            {
                int nivelAntigo = nivelASA;

                nivelASA = nivelASA + 1;
                local = declaracaoFuncao.getNome();

                 for (NoDeclaracaoParametro parametro : declaracaoFuncao.getParametros())
                    {
                        parametro.aceitar(this);
                    }

                    visitar(declaracaoFuncao.getBlocos());

                    nivelASA = nivelAntigo;
                    local = "programa";
                
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
                TrechoCodigoFonte trecho = noDeclaracaoParametro.getTrechoCodigoFonteNome();

                ConclusaoVariavelASA conclusao = new ConclusaoVariavelASA(ProvedorConclusaoCodigoPrograma.this, nome, tipo, false, nivelASA, trecho, local);
                conclusao.setRelevance(nivelASA);
                conclusao.setDefinedIn(local);

                completions.add(conclusao);
            }

            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
        {
            TrechoCodigoFonte trecho = noDeclaracaoMatriz.getTrechoCodigoFonteNome();

            String nome = noDeclaracaoMatriz.getNome();

            completions.add(new ConclusaoModeloASA(ProvedorConclusaoCodigoPrograma.this, nome, nome, nome + "[${indice}][${indice}]", null, null, nivelASA, trecho, local));
            completions.add(new ConclusaoModeloASA(ProvedorConclusaoCodigoPrograma.this, nome + "[?][?] = ?", nome + "[?][?] = ?", nome + "[${linha}][${coluna}] = ${expressao}", null, null, nivelASA, trecho, local));

            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
        {           
            TrechoCodigoFonte trecho = noDeclaracaoVariavel.getTrechoCodigoFonteNome();

            String nome = noDeclaracaoVariavel.getNome();
            String tipo = noDeclaracaoVariavel.getTipoDado().getNome();

            ConclusaoVariavelASA variableCompletion = new ConclusaoVariavelASA(ProvedorConclusaoCodigoPrograma.this, nome, tipo, noDeclaracaoVariavel.constante(), nivelASA, trecho, local);
            variableCompletion.setDefinedIn(local);
            variableCompletion.setRelevance(nivelASA);

            completions.add(variableCompletion);

            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
        {
            TrechoCodigoFonte trecho = noDeclaracaoVetor.getTrechoCodigoFonteNome();

            String nome = noDeclaracaoVetor.getNome();

            ConclusaoModeloASA conclusao;

            conclusao = new ConclusaoModeloASA(ProvedorConclusaoCodigoPrograma.this, nome, nome + "[&lt;posicao&gt;]", nome + "[${posicao}]${cursor}", null, String.format("Acessa um elemento do vetor '%s'", nome), nivelASA, trecho, local);
            substituirParametro(conclusao, 0, new ParametroConclusaoASA(noDeclaracaoVetor, INDICE_VETOR, conclusao.getParam(0), TipoDado.INTEIRO.toString()));

            completions.add(conclusao);

            conclusao = new ConclusaoModeloASA(ProvedorConclusaoCodigoPrograma.this, nome, nome + "[&lt;posicao&gt;] = &lt;expressao&gt;", nome + "[${posicao}] = ${expressao}${cursor}", null, String.format("Armazena um valor em uma posição do vetor '%s'", nome), nivelASA, trecho, local);
            substituirParametro(conclusao, 0, new ParametroConclusaoASA(noDeclaracaoVetor, INDICE_VETOR, conclusao.getParam(0), TipoDado.INTEIRO.toString()));
            substituirParametro(conclusao, 1, new ParametroConclusaoASA(noDeclaracaoVetor, ATRIBUICAO_VALOR, conclusao.getParam(1), noDeclaracaoVetor.getTipoDado().getNome()));

            completions.add(conclusao);

            return null;
        }

        @Override
        public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
        {
            try
            {
                TrechoCodigoFonte trecho = (lendoAlias) ? noInclusaoBiblioteca.getTrechoCodigoFonteAlias() : noInclusaoBiblioteca.getTrechoCodigoFonteNome();
                GerenciadorBibliotecas.getInstance().obterMetaDadosBiblioteca(noInclusaoBiblioteca.getNome());

                String nome = (lendoAlias) ? noInclusaoBiblioteca.getAlias() : noInclusaoBiblioteca.getNome();
                String tipo = "Biblioteca";

                if (nome != null)
                {
                    ConclusaoVariavelASA conclusao = new ConclusaoVariavelASA(ProvedorConclusaoCodigoPrograma.this, nome, tipo, false, nivelASA, trecho, "programa");
                    conclusao.setDefinedIn("programa");

                    completions.add(conclusao);
                }
            }
            catch (ErroCarregamentoBiblioteca erroCarregamentoBiblioteca)
            {
                // Se não conseguiu carregar a biblioteca, nem cria o autocomplete
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

            if (noPara.getInicializacoes() != null)
            {
            	for (NoBloco inicializacao : noPara.getInicializacoes())
            	{
            		inicializacao.aceitar(this);
            	}
            }

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

        @SuppressWarnings("unchecked")
		private void substituirParametro(TemplateCompletion conclusao, int indice, ParameterizedCompletion.Parameter parametro)
        {
            try
            {
                Class<TemplateCompletion> classe = TemplateCompletion.class;
                Field campoParams = classe.getDeclaredField("params");

                campoParams.setAccessible(true);
                List<ParameterizedCompletion.Parameter> params = (List<ParameterizedCompletion.Parameter>) campoParams.get(conclusao);

                params.set(indice, parametro);
            }
            catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException excecao)
            {

            }
        }
    }

    private boolean conclusaoNoEscopoCursor(ConclusaoASA conclusao, EscopoCursor escopoCursor)
    {
        if (escopoCursor.getProfundidade() == 0)
        {
            // O cursor está fora do escopo do programa
            return false;
        }
        else if (escopoCursor.getProfundidade() >= 2 && conclusao.getNivelASA() == 1)
        {
            // O cursor está dentro de uma função e estamos declarando os símbolos globais
            return true;
        }
        else
        {
            TrechoCodigoFonte nome = conclusao.getTrechoCodigoFonte();

            boolean estaNoMesmoNivel = conclusao.getNivelASA() <= escopoCursor.getProfundidade();
            boolean estaAcimaCursor = nome.getLinha() < escopoCursor.getLinha();
            boolean estaNaMesmaLinhaCursor = nome.getLinha() == escopoCursor.getLinha();
            boolean estaAntesCursor = (nome.getColuna() + nome.getTamanhoTexto()) < escopoCursor.getColuna();
            boolean estaNoMesmoLocal = !(conclusao instanceof ConclusaoFuncaoASA) && escopoCursor.getNome().equals(conclusao.getLocal());

            return (estaNoMesmoNivel && estaNoMesmoLocal && (estaAcimaCursor || (estaNaMesmaLinhaCursor && estaAntesCursor)));
        }
    }
}
