package br.univali.ps.nucleo;

import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.ps.ParserDeQuestao;
import br.univali.ps.exception.CarregamentoDeExercicioException;
import br.univali.ps.ui.telas.TelaSobre;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 22/08/2011
 *
 */
public final class PortugolStudio {

    private static PortugolStudio instancia = null;
    private boolean depurando = false;
    private TratadorExcecoes tratadorExcecoes = null;

    private GerenciadorTemas gerenciadorTemas = null;
    private TelaSobre telaSobre = null;

    private PortugolStudio() {
    }

    public static PortugolStudio getInstancia() {
        if (instancia == null) {
            instancia = new PortugolStudio();
        }

        return instancia;
    }

    public boolean isDepurando() {
        return depurando;
    }

    public void setDepurando(boolean depurando) {
        this.depurando = depurando;
    }

    public void iniciar() {

    }

    private void registrar_fontes() {
        final String path = "br/univali/ps/ui/fontes/";

        final String[] fontes
                = {
                    "dejavu_sans_mono.ttf",
                    "dejavu_sans_mono_bold.ttf",
                    "dejavu_sans_mono_bold_oblique.ttf",
                    "dejavu_sans_mono_oblique.ttf"
                };

        for (String nome : fontes) {
            try {
                Font fonte = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream(path + nome));
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonte);
            } catch (FontFormatException | IOException excecao) {
                excecao.printStackTrace(System.err);
            }
        }
    }

    public TratadorExcecoes getTratadorExcecoes() {
        if (tratadorExcecoes == null) {
            tratadorExcecoes = new TratadorExcecoes();
        }

        return tratadorExcecoes;
    }

    public GerenciadorTemas getGerenciadorTemas() {
        if (gerenciadorTemas == null) {
            gerenciadorTemas = new GerenciadorTemas();
        }

        return gerenciadorTemas;
    }

    public TelaSobre getTelaSobre() {
        if (telaSobre == null) {
            telaSobre = new TelaSobre();
        }

        telaSobre.setLocationRelativeTo(null);

        return telaSobre;
    }

    public Questao abrirQuestao(String pathDoArquivoPex, ParserDeQuestao parserDeQuestao) throws CarregamentoDeExercicioException {
        String conteudoDoXmlDoExercicio = CarregadorDeArquivo.getConteudoDoArquivo(pathDoArquivoPex);

        try {
            return parserDeQuestao.getQuestao(conteudoDoXmlDoExercicio);
        } catch (Exception e) {
            throw new CarregamentoDeExercicioException(pathDoArquivoPex, e);
        }
    }

    public boolean rodandoApplet() {
        return System.getSecurityManager() != null;
    }

    private static class CarregadorDeArquivo {

        public static String getConteudoDoArquivo(String urlDoArquivo) throws CarregamentoDeExercicioException {
            try {
                InputStream is = null;
                BufferedOutputStream bos = null;
                String conteudoDoArquivo = "";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    is = new BufferedInputStream(new URL(urlDoArquivo).openStream());

                    bos = new BufferedOutputStream(baos);
                    int byteLido = -1;
                    while ((byteLido = is.read()) != -1) {
                        bos.write(byteLido);
                    }
                } finally {
                    bos.flush();
                    conteudoDoArquivo = new String(baos.toByteArray());
                    is.close();
                    bos.close();
                    baos.close();//por precaução :)
                    return conteudoDoArquivo;
                }
            } catch (Exception e) {
                throw new CarregamentoDeExercicioException(urlDoArquivo);
            }
        }
    }

}
