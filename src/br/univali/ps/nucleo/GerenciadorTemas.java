package br.univali.ps.nucleo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fife.ui.rsyntaxtextarea.Theme;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class GerenciadorTemas
{
    private static final Logger LOGGER = Logger.getLogger(GerenciadorTemas.class.getName());
    private static final String PACOTE_TEMA = "";

    private final Map<String, String> arquivosTema = new HashMap<>();

    GerenciadorTemas()
    {

    }

    public List<String> listarTemas()
    {
        if (arquivosTema.isEmpty())
        {
            /*
             * Por enquanto adicionamos manualmente, futuramente podemos
             * carregar dinamicamente do pacote de temas.
             */

            arquivosTema.put("Dark", "dark");
            arquivosTema.put("Eclipse", "eclipse");
            arquivosTema.put("IntelliJ IDEA", "idea");
            arquivosTema.put("Portugol Studio", "default-alt");
            arquivosTema.put("Visual Studio", "vs");
        }

        List<String> listaTemas = new ArrayList<>(arquivosTema.keySet());
        Collections.sort(listaTemas);

        return listaTemas;
    }

    public Theme carregarTema(String nome) throws ExcecaoAplicacao
    {
        return carregarTema(nome, true);
    }
    
    private Theme carregarTema(String nome, boolean carregarPadrao) throws ExcecaoAplicacao
    {
        if (arquivosTema.isEmpty())
        {
            listarTemas();
        }

        try
        {
            final String nomeArquivo = arquivosTema.get(nome);
            final String caminho = String.format("%s/%s.xml", PACOTE_TEMA, nomeArquivo);
            final InputStream resourceStream = getClass().getResourceAsStream(caminho);
            final Theme theme = Theme.load(resourceStream);
            
            return theme;
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.SEVERE, String.format("Erro ao carregar o tema '%s'", nome), excecao);
            
            if (carregarPadrao)
            {
                return carregarTema("Portugol Studio", false);
            }
            else
            {
                throw new ExcecaoAplicacao(String.format("Erro ao carregar o tema '%s'", nome), excecao, ExcecaoAplicacao.Tipo.ERRO);
            }
        }
    }
}
