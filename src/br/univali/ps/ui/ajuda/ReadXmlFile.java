/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.ajuda;

import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author Carlos
 */
public class ReadXmlFile
{
    String titulo, explicacao, exemplo;
    File fXmlFile = new File("/lol/help.xml");
    Document doc;

    public ReadXmlFile()
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public HashMap captura(String nodeName, String titulo)
    {
        HashMap conteudo = new HashMap();
        NodeList nList = doc.getElementsByTagName(nodeName);
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) nNode;

                if (titulo.equalsIgnoreCase(eElement.getElementsByTagName("titulo").item(0).getTextContent()))
                {
                    conteudo.put("titulo", eElement.getElementsByTagName("titulo").item(0).getTextContent());
                    conteudo.put("explicacao", eElement.getElementsByTagName("explicacao").item(0).getTextContent());

                    if ("".compareToIgnoreCase(eElement.getElementsByTagName("exemplo1").item(0).getTextContent()) != 0)
                    {
                        conteudo.put("exemplo1", eElement.getElementsByTagName("exemplo1").item(0).getTextContent());
                    }
                    else
                    {
                        conteudo.put("exemplo1", "");
                    }

                    if ("".compareToIgnoreCase(eElement.getElementsByTagName("exemplo2").item(0).getTextContent()) != 0)
                    {
                        conteudo.put("exemplo2", eElement.getElementsByTagName("exemplo2").item(0).getTextContent());
                    }
                    else
                    {
                        conteudo.put("exemplo2", "");
                    }

                    if ("".compareToIgnoreCase(eElement.getElementsByTagName("exemplo3").item(0).getTextContent()) != 0)
                    {
                        conteudo.put("exemplo3", eElement.getElementsByTagName("exemplo3").item(0).getTextContent());
                    }
                    else
                    {
                        conteudo.put("exemplo3", "");
                    }
                    return conteudo;
                }
            }
        }


        return null;
    }

    public List capturaSubMenu()
    {
        List<HashMap> lista = new ArrayList();

        NodeList nList = doc.getElementsByTagName("submenu");
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            HashMap conteudo = new HashMap();
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) nNode;
                conteudo.put("titulo", eElement.getElementsByTagName("titulo").item(0).getTextContent());
                conteudo.put("tag", eElement.getElementsByTagName("tag").item(0).getTextContent());
                conteudo.put("explicacao", eElement.getElementsByTagName("explicacao").item(0).getTextContent());

            }
            lista.add(conteudo);
        }


        return lista;
    }

    public String capturaSubMenu(String titulo)
    {
        NodeList nList = doc.getElementsByTagName("submenu");
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) nNode;
                if (titulo.equalsIgnoreCase(eElement.getElementsByTagName("titulo").item(0).getTextContent()))
                {
                    return eElement.getElementsByTagName("tag").item(0).getTextContent();
                }
            }
        }
        return null;
    }

    public Collection capturaTitulos(String tipoConteudo)
    {
        Collection titulos = new LinkedList();
        NodeList nList = doc.getElementsByTagName(tipoConteudo);

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) nNode;
                titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
                titulos.add(titulo);
            }
        }
        return titulos;
    }
}
