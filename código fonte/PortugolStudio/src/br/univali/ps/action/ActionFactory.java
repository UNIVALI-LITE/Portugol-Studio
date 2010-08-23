/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.action;

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
public class ActionFactory
{

    private static final ActionFactory instance = new ActionFactory();
    private static final String actionPath = "br/univali/ps/action";
    private Document actionXML;

    private ActionFactory()
    {
        loadActionXML();
    }

    public static ActionFactory getInstance()
    {
        return instance;
    }

    public Action createAction(Class ActionClass)
    {
        try
        {
            Action action = (Action) ActionClass.newInstance();
            actionConfig(action, ActionClass.getSimpleName());
            return action;
        } catch (Exception ex)
        {
        }
        return null;
    }

    private void actionConfig(Action action, String name)
    {
        Element element = searchElementConfig(name);
        action.setTitle(readValue(element, "title"));
        action.setLargeIcon(IconFactory.createIcon(IconFactory.LARGE_ICONS_PATH, readValue(element, "icon")));
        action.setIcon(IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, readValue(element, "icon")));
    
    }

    private Element searchElementConfig(String name)
    {
        NodeList elements = actionXML.getDocumentElement().getElementsByTagName("action");

        for (int i = 0; i < elements.getLength(); i++)
        {
            Element element = (Element) elements.item(i);
            String ID = element.getAttribute("id");

            if (ID.equals(name))
            {
                return element;
            }
        }

        return null;
    }

    private String readValue(Element element, String tag)
    {
        String value = null;
        NodeList elements = element.getElementsByTagName(tag);

        if (elements != null && elements.getLength() > 0)
        {
            element = (Element) elements.item(0);
            value = element.getFirstChild().getNodeValue();
        }

        return value;
    }

    private void loadActionXML()
    {
        try
        {
            InputStream XMLInputStream = null;

            try
            {
                XMLInputStream = getClass().getClassLoader().getResourceAsStream(mountXMLFilePath());
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                actionXML = db.parse(XMLInputStream);
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

    private String mountXMLFilePath()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(actionPath);
        builder.append("/actions.xml");

        return builder.toString();
    }
}
