package br.univali.ps.plugins.base;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;
import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luiz Fernando Noschang
 */
@XmlAccessorType(FIELD)
@XmlRootElement(name = "plugin")
public final class MetaDadosPlugin
{
    @XmlTransient
    private Class classe;
    
    @XmlAttribute(name = "classe")
    private String nomeClasse;
    
    @XmlAttribute
    private String nome;
    
    @XmlAttribute
    private String versao;
    
    @XmlElement
    private String descricao;
    
    @XmlTransient
    private String licenca;
    
    @XmlTransient
    private Image icone16x16;
    
    @XmlTransient
    private Image icone32x32;
    
    @XmlElementWrapper
    @XmlElement(name = "autor")
    private List<Autor> autores;
    
    @XmlTransient
    private File arquivoJar;

    MetaDadosPlugin()
    {

    }

    public Class getClasse()
    {
        return classe;
    }

    void setClasse(Class classe)
    {
        this.classe = classe;
    }

    String getNomeClasse()
    {
        return nomeClasse;
    }

    void setNomeClasse(String nomeClasse)
    {
        this.nomeClasse = nomeClasse;
    }
    
    public String getNome()
    {
        return nome;
    }

    void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public String getDescricao()
    {
        return descricao;
    }

    void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public String getLicenca()
    {
        return licenca;
    }

    void setLicenca(String licenca)
    {
        this.licenca = licenca;
    }

    public String getVersao()
    {
        return versao;
    }

    void setVersao(String versao)
    {
        this.versao = versao;
    }

    public Image getIcone16x16()
    {
        return icone16x16;
    }

    void setIcone16x16(Image icone16x16)
    {
        this.icone16x16 = icone16x16;
    }

    public Image getIcone32x32()
    {
        return icone32x32;
    }

    void setIcone32x32(Image icone32x32)
    {
        this.icone32x32 = icone32x32;
    }

    public List<Autor> getAutores()
    {
        return autores;
    }

    void setAutores(List<Autor> autores)
    {
        this.autores = autores;
    }

    public File getArquivoJar()
    {
        return arquivoJar;
    }

    void setArquivoJar(File arquivoJar)
    {
        this.arquivoJar = arquivoJar;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof MetaDadosPlugin)
        {
            return ((MetaDadosPlugin) obj).getClass().equals(this.getClass());
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return 2339
                + (this.getClass() != null ? this.getClass().hashCode() : 0)
                + (this.getVersao() != null ? this.getVersao().hashCode() : 0);
    }
}
