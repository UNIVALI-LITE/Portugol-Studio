package br.univali.ps.ui.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author LITE
 */
public class WebConnectionUtils {
    public static void abrirSite(String endereco){
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(endereco));
        } catch (IOException ex) {
            StringSelection stringSelection = new StringSelection(endereco);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            JOptionPane.showMessageDialog(null,"Não foi possível abrir o seu navegador de Internet no endereço "+endereco+". Seu computador está com problemas d econxão com a internet, ou a página encontra-se temporariamente indisponível. O endereço está em sua área de transferência, aperte CTRL+V no navegador para acessá-lo manualmente");
        }
    }
}
