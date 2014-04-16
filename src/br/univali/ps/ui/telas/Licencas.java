package br.univali.ps.ui.telas;

import static javax.xml.bind.JAXBContext.newInstance;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.FileHandle;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luiz Fernando
 */
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public final class Licencas
{
    private static final Logger LOGGER = Logger.getLogger(Licencas.class.getName());

    @XmlElement(name = "recurso")
    private List<Recurso> recursos;

    private Licencas()
    {

    }
    
    public List<Recurso> getRecursos()
    {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos)
    {
        this.recursos = recursos;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static final class Recurso
    {
        @XmlAttribute
        private String nome;

        @XmlAttribute
        private String versao;

        @XmlAttribute
        private String arquivo;
        
        @XmlAttribute
        private String url;
        
        @XmlElement
        private String descricao;

        @XmlTransient
        private String licenca;
        
        public String getNome()
        {
            return nome;
        }

        public void setNome(String nome)
        {
            this.nome = nome;
        }

        public String getVersao()
        {
            return versao;
        }

        public void setVersao(String versao)
        {
            this.versao = versao;
        }       

        public String getArquivo()
        {
            return arquivo;
        }

        public void setArquivo(String arquivo)
        {
            this.arquivo = arquivo;
        }

        public String getLicenca()
        {
            return licenca;
        }

        public void setLicenca(String licenca)
        {
            this.licenca = licenca;
        }

        public String getDescricao()
        {
            return descricao;
        }

        public void setDescricao(String descricao)
        {
            this.descricao = descricao;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }

    public static Licencas carregar()
    {
        Licencas licencas = carregarXml();

        if (licencas != null)
        {
            for (Recurso recurso : licencas.getRecursos())
            {
                recurso.setLicenca(carregarLicenca(recurso.getArquivo()));
            }
            
            CompardorRecurso comparador = new CompardorRecurso();
            Collections.sort(licencas.getRecursos(), comparador); 
        }           
        
        return licencas;
    }

    private static String carregarLicenca(String arquivo)
    {
        try
        {
            return FileHandle.read(Licencas.class.getClassLoader().getResourceAsStream(String.format("br/univali/ps/licencas/%s", arquivo)), "UTF-8");
        }
        catch (Exception excecao)
        {
            LOGGER.log(Level.SEVERE, String.format("Erro ao carregar a licença '%s'", arquivo), excecao);
        }

        return "Erro ao carregar a licença";
    }

    private static Licencas carregarXml()
    {
        try
        {
            JAXBContext contexto = newInstance(Licencas.class);
            Unmarshaller unmarshaller = contexto.createUnmarshaller();

            return (Licencas) unmarshaller.unmarshal(Licencas.class.getClassLoader().getResourceAsStream("br/univali/ps/licencas/licencas.xml"));
        }
        catch (JAXBException excecao)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
        }

        return null;
    }
    
    /**
     * Coloca a aba do Portugol Studio sempre na primeira posição. Depois coloca a aba do
     * Portugol Núcleo na segunda posição. Ordena as demais abas por ordem alfabética.
     */
    private static final class CompardorRecurso implements Comparator<Recurso>
    {
        @Override
        public int compare(Recurso r1, Recurso r2)
        {
            if (r1.getNome().equals("Portugol Studio"))
            {
                return -1000000;
            }
            else if (r2.getNome().equals("Portugol Studio"))
            {
                return 1000000;
            }
            else if (r1.getNome().equals("Portugol Núcleo"))
            {
                return -999999;
            }
            else if (r2.getNome().equals("Portugol Núcleo"))
            {
                return 999999;
            }
            else
            {
                return r1.getNome().toUpperCase().compareTo(r2.getNome().toUpperCase());
            }
        }
    }
}
