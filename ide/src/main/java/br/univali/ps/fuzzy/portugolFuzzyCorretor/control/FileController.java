/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.fuzzy.portugolFuzzyCorretor.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Augustop
 */
public class FileController {
    private static String currentDirectory = ".";
    
    public static String getCodigoPortugol() throws FileNotFoundException{
        JFileChooser fc = new JFileChooser(currentDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Portugol Code", "por", "txt");
        fc.setFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        File file = null;
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            currentDirectory = file.getParentFile().getPath();
            return formatarArquivoTexto(file);
        } 
        return "";
    }
    
    public static String formatarArquivoTexto(File file) throws FileNotFoundException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        Scanner scanner = new Scanner(file, "ISO-8859-1");
        while (scanner.hasNextLine()){
            text.append(scanner.nextLine() + NL);
        }
        scanner.close();
        return text.toString();
    }
    
    public static File carregarFuzzyRules() throws FileNotFoundException, IOException{
        final String caminho = "Portugol_Corretor_Rules.flc";
        final InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(caminho);
        File PCR = new File("Portugol_Corretor_Rules.flc");
        
        try (OutputStream outputStream = new FileOutputStream(PCR)) {
            IOUtils.copy(resourceStream, outputStream);
        }
        
        return PCR;
    }
    
    public static String carregarArquivoInicial() throws FileNotFoundException{
        File file = new File("errooi.por");
        return formatarArquivoTexto(file);
    }
}
