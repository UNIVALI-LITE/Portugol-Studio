package br.univali.ps.ui.acoes;

import br.univali.ps.ui.util.IconFactory;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Fillipi Pelz
 */
public class FabricaAcao
{

    private static final FabricaAcao instancia = new FabricaAcao();
    private static final String CAMINHO_ACOES = "br/univali/ps/ui/acoes";
    private Document XMLacoes;

    private FabricaAcao()
    {
        carregarActionXML();
    }

    public static FabricaAcao getInstancia()
    {
        return instancia;
    }

    public Acao criarAcao(Class classeAcao)
    {
        try
        {
            Acao acao = (Acao) classeAcao.newInstance();          
            configurarAcao(acao, classeAcao.getSimpleName());
            return acao;
        } catch (Exception ex)
        {
        }
        return null;
    }

    private void configurarAcao(Acao acao, String nome)
    {
        Element elemento = configurarPesquisaElemento(nome);
        acao.setTitulo(lerValor(elemento, "title"));
        acao.setIconeGrande(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, lerValor(elemento, "icon")));
        acao.setIcone(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, lerValor(elemento, "icon")));
    
    }

    private Element configurarPesquisaElemento(String nome)
    {
        NodeList elementos = XMLacoes.getDocumentElement().getElementsByTagName("action");

        for (int i = 0; i < elementos.getLength(); i++)
        {
            Element elemento = (Element) elementos.item(i);
            String ID = elemento.getAttribute("id");

            if (ID.equals(nome))
            {
                return elemento;
            }
        }

        return null;
    }

    private String lerValor(Element elemento, String tag)
    {
        String valor = null;
        NodeList elements = elemento.getElementsByTagName(tag);

        if (elements != null && elements.getLength() > 0)
        {
            elemento = (Element) elements.item(0);
            valor = elemento.getFirstChild().getNodeValue();
        }

        return valor;
    }

    private void carregarActionXML()
    {
        try
        {
            InputStream XMLInputStream = null;

            try
            {
                XMLInputStream = getClass().getClassLoader().getResourceAsStream(montarCaminhoArquivoXML());
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                XMLacoes = db.parse(XMLInputStream);
            } finally
            {
                try
                {
                    XMLInputStream.close();
                } catch (Exception ex)
                {
                }
            }
        } catch (Exception ex)
        {
        }
    }

    private String montarCaminhoArquivoXML()
    {
        StringBuilder construtor = new StringBuilder();

        construtor.append(CAMINHO_ACOES);
        construtor.append("/actions.xml");

        return construtor.toString();
    }
}
