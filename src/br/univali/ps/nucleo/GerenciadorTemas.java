package br.univali.ps.nucleo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.fife.ui.rsyntaxtextarea.Theme;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class GerenciadorTemas
{
    private static final String PACOTE_TEMA = "br/univali/ps/ui/temas/";
    
    private Map<String, String> arquivosTema;

    GerenciadorTemas()
    {
        arquivosTema = new HashMap<String, String>();
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
            arquivosTema.put("Padrão", "default-alt");
            arquivosTema.put("Visual Studio", "vs");
        }

        List<String> listaTemas = new ArrayList<String>(arquivosTema.keySet());
        Collections.sort(listaTemas);
        
        return listaTemas;
    }

    public Theme carregarTema(String nome) throws ExcecaoAplicacao
    {
        if (arquivosTema.isEmpty())
        {
            listarTemas();
        }
            
        try
        {
            String nomeArquivo = arquivosTema.get(nome);        

            String caminho = String.format("%s/%s.xml", PACOTE_TEMA, nomeArquivo);
            InputStream arquivoTema = getClass().getClassLoader().getResourceAsStream(caminho);
            
            return Theme.load(arquivoTema);
        }
        catch (IOException excecao)
        {
            throw new ExcecaoAplicacao(String.format("Erro ao carregar o tema '%s'", nome), excecao, ExcecaoAplicacao.Tipo.ERRO);
        }        
    }
}
