package br.univali.ps.ui.utils;

import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

public class FabricaDeFileChooser {

    //private static JFileChooser chooserAbertura;
    private static JFileChooser chooserSalvamento;

    public static void inicializar() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //chooserAbertura = new JFileChooser();

            chooserSalvamento = new JFileChooser() {
                @Override
                public File getSelectedFile() {
                    File arquivo = super.getSelectedFile();

                    if (arquivo != null) {
                        if (!arquivo.getName().toLowerCase().endsWith(".por")) {
                            arquivo = new File(arquivo.getPath().concat(".por"));
                        }
                    }

                    return arquivo;
                }

                @Override
                public void approveSelection() {
                    if (getDialogType() == JFileChooser.SAVE_DIALOG) {
                        File selectedFile = getSelectedFile();

                        if ((selectedFile != null) && selectedFile.exists()) {
                            int response = JOptionPane.showConfirmDialog(this, "O arquivo informado já existe.\n Deseja substituí-lo?", "Portugol Studio", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                            if (response != JOptionPane.YES_OPTION) {
                                return;
                            }
                        }
                    }

                    super.approveSelection();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void limpaChooser(JFileChooser chooser) {
        chooser.setCurrentDirectory(new File("."));
        chooser.setSelectedFile(null);
        chooser.setFileFilter(null);
        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setMultiSelectionEnabled(false);
        FileFilter filtros[] = chooser.getChoosableFileFilters();
        for (FileFilter filtro : filtros) {
            chooser.removeChoosableFileFilter(filtro);
        }
    }

    public static JFileChooser getFileChooserAbertura() {
        LookAndFeel previousLF = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFileChooser dialogo = new JFileChooser();
            limpaChooser(dialogo);
            UIManager.setLookAndFeel(previousLF);
            return dialogo;
        }
        catch(Exception e) {
            
        }
        return null;
    }
    

    public static JFileChooser getFileChooserSalvamento() {
        if (chooserSalvamento == null) {
            throw new IllegalStateException("A fabrica de FileChoosers ainda não foi inicializada!");
        }
        limpaChooser(chooserSalvamento);
        return chooserSalvamento;
    }

}
