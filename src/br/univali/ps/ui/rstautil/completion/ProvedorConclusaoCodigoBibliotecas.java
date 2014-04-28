package br.univali.ps.ui.rstautil.completion;

import static br.univali.portugol.nucleo.asa.Quantificador.MATRIZ;
import static br.univali.portugol.nucleo.asa.Quantificador.VETOR;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.GerenciadorBibliotecas;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosConstante;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosParametro;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.ParameterizedCompletion;
import org.fife.ui.autocomplete.VariableCompletion;

final class ProvedorConclusaoCodigoBibliotecas extends DefaultCompletionProvider
{
    private final FabricaConclusaoCodigoBibliotecas fabricaConclusoes;
    private final Map<String, List<Completion>> conclusoesBibliotecas;
    private final List<String> bibliotecasIncluidas;
    private final Map<String, String> apelidosBibliotecas;
    private final AtualizadorEstadoBibliotecas atualizador;

    public ProvedorConclusaoCodigoBibliotecas()
    {
        this.fabricaConclusoes = new FabricaConclusaoCodigoBibliotecas();
        this.conclusoesBibliotecas = fabricaConclusoes.criarConclusoes(ProvedorConclusaoCodigoBibliotecas.this);
        this.atualizador = new AtualizadorEstadoBibliotecas();
        this.apelidosBibliotecas = new HashMap<>();
        this.bibliotecasIncluidas = new ArrayList<>();
        this.setParameterizedCompletionParams('(', ", ", ')');
        this.setAutoActivationRules(false, ".");
    }

    public void atualizar(Programa programa)
    {
        atualizador.atualizar(programa);
    }

    @Override
    protected List<Completion> getCompletionsImpl(JTextComponent comp)
    {
        String[] partes = getID(comp).split("\\.");
        String nomeBiblioteca = null;

        if (partes.length == 1 || partes.length == 2)
        {
            nomeBiblioteca = partes[0];
        }

        if (nomeBiblioteca != null)
        {
            if (apelidosBibliotecas.containsKey(nomeBiblioteca))
            {
                nomeBiblioteca = apelidosBibliotecas.get(nomeBiblioteca);
            }

            if (bibliotecasIncluidas.contains(nomeBiblioteca))
            {
                List<Completion> conclusoes = new ArrayList<>(conclusoesBibliotecas.get(nomeBiblioteca));

                if (partes.length == 2)
                {
                    Iterator<Completion> iterador = conclusoes.iterator();

                    while (iterador.hasNext())
                    {
                        if (!iterador.next().getInputText().toLowerCase().startsWith(partes[1].toLowerCase()))
                        {
                            iterador.remove();
                        }
                    }
                }

                //incluirPrefixo(conclusoes, partes[0]);
                return conclusoes;
            }
        }

        return Collections.emptyList();
    }

    private void incluirPrefixo(List<Completion> conclusoes, String prefixo)
    {
        for (Completion conclusao : conclusoes)
        {
            try
            {
                Field campo = BasicCompletion.class.getDeclaredField("replacementText");
                campo.setAccessible(true);
                campo.set(conclusao, prefixo.concat(".").concat(conclusao.getReplacementText()));
            }
            catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException ex)
            {
                Logger.getLogger(ProvedorConclusaoCodigoBibliotecas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private final class AtualizadorEstadoBibliotecas extends VisitanteASABasico
    {
        public void atualizar(Programa programa)
        {
            try
            {
                visitar(programa.getArvoreSintaticaAbstrata());
            }
            catch (ExcecaoVisitaASA excecao)
            {

            }
        }

        @Override
        public Object visitar(ArvoreSintaticaAbstrataPrograma asap) throws ExcecaoVisitaASA
        {
            for (NoInclusaoBiblioteca inclusao : asap.getListaInclusoesBibliotecas())
            {
                inclusao.aceitar(this);
            }

            return null;
        }

        @Override
        public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
        {
            if (!ProvedorConclusaoCodigoBibliotecas.this.bibliotecasIncluidas.contains(noInclusaoBiblioteca.getNome()))
            {
                ProvedorConclusaoCodigoBibliotecas.this.bibliotecasIncluidas.add(noInclusaoBiblioteca.getNome());
            }

            if (noInclusaoBiblioteca.getAlias() != null)
            {
                if (!ProvedorConclusaoCodigoBibliotecas.this.apelidosBibliotecas.containsKey(noInclusaoBiblioteca.getAlias()))
                {
                    ProvedorConclusaoCodigoBibliotecas.this.apelidosBibliotecas.put(noInclusaoBiblioteca.getAlias(), noInclusaoBiblioteca.getNome());
                }
            }

            return null;
        }
    }

    private final class FabricaConclusaoCodigoBibliotecas
    {
        public Map<String, List<Completion>> criarConclusoes(CompletionProvider provedorConclusoes)
        {
            Map<String, List<Completion>> conclusoes = new HashMap<>();
            GerenciadorBibliotecas gerenciadorBibliotecas = GerenciadorBibliotecas.getInstance();

            for (String biblioteca : gerenciadorBibliotecas.listarBibliotecasDisponiveis())
            {
                try
                {
                    List<Completion> conclusoesBiblioteca = new ArrayList<>();
                    MetaDadosBiblioteca metaDadosBiblioteca = gerenciadorBibliotecas.obterMetaDadosBiblioteca(biblioteca);

                    for (MetaDadosConstante constante : metaDadosBiblioteca.getMetaDadosConstantes())
                    {
                        conclusoesBiblioteca.add(criarConclusaoConstante(constante, metaDadosBiblioteca, provedorConclusoes));
                    }

                    for (MetaDadosFuncao funcao : metaDadosBiblioteca.obterMetaDadosFuncoes())
                    {
                        conclusoesBiblioteca.add(criarConclusaoFuncao(funcao, metaDadosBiblioteca, provedorConclusoes));
                    }

                    conclusoes.put(biblioteca, conclusoesBiblioteca);
                }
                catch (ErroCarregamentoBiblioteca ex)
                {
                    Logger.getLogger(FabricaConclusaoCodigoBibliotecas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return conclusoes;
        }

        private Completion criarConclusaoConstante(MetaDadosConstante constante, MetaDadosBiblioteca metaDadosBiblioteca, CompletionProvider provedorConclusoes)
        {
            String nome = constante.getNome();
            String tipo = obterTipo(constante.getTipoDado(), constante.getQuantificador());

            VariableCompletion conclusao = new VariableCompletion(provedorConclusoes, nome, tipo);

            conclusao.setRelevance(1);
            conclusao.setDefinedIn(metaDadosBiblioteca.getNome());
            conclusao.setShortDescription(String.format("Valor: %s", constante.getValor().toString()));
            conclusao.setSummary(constante.getDocumentacao().descricao());

            return conclusao;
        }

        private Completion criarConclusaoFuncao(MetaDadosFuncao funcao, MetaDadosBiblioteca metaDadosBiblioteca, CompletionProvider provedorConclusoes)
        {
            String nomeFuncao = funcao.getNome();
            String tipoFuncao = obterTipo(funcao.getTipoDado(), funcao.getQuantificador());

            FunctionCompletion conclusao = new FunctionCompletion(provedorConclusoes, nomeFuncao, tipoFuncao);

            conclusao.setRelevance(0);
            conclusao.setDefinedIn(metaDadosBiblioteca.getNome());
            conclusao.setReturnValueDescription(funcao.getDocumentacao().retorno());
            conclusao.setSummary(funcao.getDocumentacao().descricao());

            if (funcao.getDocumentacao().parametros() != null && funcao.getDocumentacao().parametros().length > 0)
            {
                ArrayList<ParameterizedCompletion.Parameter> parametros = new ArrayList<>();

                for (MetaDadosParametro parametro : funcao.obterMetaDadosParametros())
                {
                    String nome = parametro.getNome();
                    String tipo = obterTipo(parametro.getTipoDado(), parametro.getQuantificador());

                    ParameterizedCompletion.Parameter conclusaoParametro = new ParameterizedCompletion.Parameter(tipo, nome);
                    conclusaoParametro.setDescription(parametro.getDocumentacaoParametro().descricao());

                    parametros.add(conclusaoParametro);
                }

                conclusao.setParams(parametros);
            }

            return conclusao;
        }

        private String obterTipo(TipoDado tipoDado, Quantificador quantificador)
        {
            StringBuilder sb = new StringBuilder();

            if (tipoDado == TipoDado.TODOS)
            {
                sb.append("*");
            }
            else
            {                   
                sb.append(tipoDado.getNome());
            }
            
            switch (quantificador)
            {
                case VETOR:
                    return sb.append("[]").toString();
                case MATRIZ:
                    return sb.append("[][]").toString();
            }

            return sb.toString();
        }
    }

    private String getID(JTextComponent comp)
    {
        Document doc = comp.getDocument();

        int dot = comp.getCaretPosition();
        Element root = doc.getDefaultRootElement();
        int index = root.getElementIndex(dot);
        Element elem = root.getElement(index);
        int start = elem.getStartOffset();
        int len = dot - start;
        try
        {
            doc.getText(start, len, seg);
        }
        catch (BadLocationException ble)
        {
            ble.printStackTrace();
            return EMPTY_STRING;
        }

        int segEnd = seg.offset + len;
        start = segEnd - 1;
        while (start >= seg.offset && isValidChar(seg.array[start]))
        {
            start--;
        }
        start++;

        len = segEnd - start;
        return len == 0 ? EMPTY_STRING : new String(seg.array, start, len);
    }

    @Override
    protected boolean isValidChar(char ch)
    {
        return Character.isLetterOrDigit(ch) || ch == '_' || ch == '.';
    }
}
