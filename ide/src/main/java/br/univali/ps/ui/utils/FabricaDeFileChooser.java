package br.univali.ps.ui.utils;

import br.univali.ps.nucleo.Caminhos;
import br.univali.ps.ui.swing.weblaf.jOptionPane.QuestionDialog;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

public class FabricaDeFileChooser {

    private static final Logger LOGGER = Logger.getLogger(FabricaDeFileChooser.class.getName());

    private static JFileChooser chooserAbertura;
    private static JFileChooser chooserSalvamento;

    public static void inicializar() {
        LookAndFeel lafAtual = UIManager.getLookAndFeel();
        try {
            
            if (!Caminhos.rodandoNoMac()) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            
            chooserAbertura = new JFileChooser();
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
                            int response = QuestionDialog.getInstance().showConfirmMessage("O arquivo informado já existe.\n Deseja substituí-lo?", JOptionPane.WARNING_MESSAGE);

                            if (response != JOptionPane.YES_OPTION) {
                                return;
                            }
                        }
                        if((selectedFile != null) && (selectedFile.getName().matches(".*[><\"/|?\\*].*") || FileHandle.isFilenameValid(selectedFile.getPath())))
                        {
                            QuestionDialog.getInstance().showMessage("O nome do arquivo não é permitido. \n O nome não pode conter os caracteres \n > < : \" / | ? *,", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }

                    super.approveSelection();
                }
            };
        } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        finally 
        {
            try {
                UIManager.setLookAndFeel(lafAtual); // restaura o LAF atual
            } catch (UnsupportedLookAndFeelException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
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
        if (chooserAbertura == null) {
            throw new IllegalStateException("A fabrica de FileChoosers ainda não foi inicializada!");
        }
        limpaChooser(chooserAbertura);
        return chooserAbertura;
    }

    public static JFileChooser getFileChooserSalvamento() {
        if (chooserAbertura == null) {
            throw new IllegalStateException("A fabrica de FileChoosers ainda não foi inicializada!");
        }
        limpaChooser(chooserSalvamento);
        return chooserSalvamento;
    }

}
