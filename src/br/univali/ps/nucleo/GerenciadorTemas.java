package br.univali.ps.nucleo;

import br.univali.ps.ui.utils.PackageClassesGetter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.fife.ui.rsyntaxtextarea.Theme;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class GerenciadorTemas {

    private static final Logger LOGGER = Logger.getLogger(GerenciadorTemas.class.getName());
    private static final String PACOTE_TEMA = "br/univali/ps/ui/editor/temas";

    private final Map<String, String> arquivosTema = new HashMap<>();

    GerenciadorTemas() {

    }
    
    public List<String> listarTemas() {
        if (arquivosTema.isEmpty()) {            
            arquivosTema.put("Dark", "dark");
            arquivosTema.put("Eclipse", "eclipse");
            arquivosTema.put("IntelliJ IDEA", "idea");
            arquivosTema.put("Portugol Studio", "default-alt");
            arquivosTema.put("Visual Studio", "vs");
            arquivosTema.put("Dark II", "darkII");
        }

        List<String> listaTemas = new ArrayList<>(arquivosTema.keySet());
        Collections.sort(listaTemas);

        return listaTemas;
    }
    
    public String getNomeTemaPadrao(){
        return "Portugol Studio";
    }
    
    public Theme carregarTema(String nome) throws ExcecaoAplicacao {
        return carregarTema(nome, true);
    }

    private Theme carregarTema(String nome, boolean carregarPadrao) throws ExcecaoAplicacao {
        if (arquivosTema.isEmpty()) {
            listarTemas();
        }

        final String nomeArquivo = arquivosTema.get(nome);
        final String caminho = String.format("%s/%s.xml", PACOTE_TEMA, nomeArquivo);
        final InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(caminho);
        if (resourceStream != null) {
            try {
                return Theme.load(resourceStream);
            } catch (IOException e) {
                throw new ExcecaoAplicacao(e.getMessage(), ExcecaoAplicacao.Tipo.ERRO);
            }
        } else if (carregarPadrao) {
            return carregarTema("Portugol Studio", false);
        }

        return null;
    }
}
