package br.univali.ps.plugins.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Luiz Fernando
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
public final class Autor
{
    @XmlAttribute
    private String nome;

    @XmlAttribute
    private String email;

    Autor()
    {

    }

    public String getNome()
    {
        return nome;
    }

    void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getEmail()
    {
        return email;
    }

    void setEmail(String email)
    {
        this.email = email;
    }
}
