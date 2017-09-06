/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.fuzzy.portugolFuzzyCorretor.control;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.univali.ps.fuzzy.portugolFuzzyCorretor.core.PortugolFuzzyCorretor;
import br.univali.ps.fuzzy.portugolFuzzyCorretor.view.MainWindow;
import java.io.IOException;

/**
 *
 * @author Augustop
 */
public class MainWindowController {
    private MainWindow mainWindow;
    private PortugolFuzzyCorretor portugolCorretor;
    
    public MainWindowController() {
        this.mainWindow = new MainWindow(this);
        try {
            this.mainWindow.getTextAreaCodigo().setText(FileController.carregarArquivoInicial());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.mainWindow.setLocationRelativeTo(null);
        this.mainWindow.setVisible(true);
    }
    
    public void prepararCarregamentoCodigo(){
        String codigo = "";
        try {
            codigo = FileController.getCodigoPortugol();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.mainWindow.getTextAreaCodigo().setText(codigo);
    }
    
    public void prepararInterpretacaoCodigo(){
        this.portugolCorretor =PortugolFuzzyCorretor.getInstance();
        try {
            String mensagens = this.portugolCorretor.interpretarCodigo();
            this.mainWindow.setMensagens(mensagens);
            this.portugolCorretor.calcularFuzzy(FileController.carregarFuzzyRules());
        } catch (ExcecaoVisitaASA ex) {
//            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
