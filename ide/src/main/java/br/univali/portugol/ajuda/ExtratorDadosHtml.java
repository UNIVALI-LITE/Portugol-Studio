package br.univali.portugol.ajuda;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Luiz Fernando Noschang
 */
final class ExtratorDadosHtml
{
    private String titulo = null;
    private String icone = null;

    public ExtratorDadosHtml()
    {
        
    }

    public String getTitulo()
    {
        return titulo;
    }

    public String getIcone()
    {
        return icone;
    }
    
    public void extrair(String html, String nomeArquivo) throws ErroCarregamentoAjuda
    {
        Document documento = Jsoup.parse(html);
        
        for (Element tag : documento.head().getElementsByTag("title"))
        {
            titulo = tag.text();
        }
        
        for (Element tag : documento.head().getElementsByTag("link"))
        {
            String rel = tag.attributes().get("rel");
            
            if (rel != null && rel.toLowerCase().equals("shortcut icon"))
            {
                icone = tag.attributes().get("href");
                break;
            }
        }
        
        if (titulo == null)
        {
            throw new ErroCarregamentoAjuda(String.format("Erro ao carregar a ajuda: o arquivo '%s' não possui a tag 'title'", nomeArquivo));
        }
		
    }
}
