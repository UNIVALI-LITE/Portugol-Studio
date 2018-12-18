/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.utils;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Adson Esteves
 */
public class EncodingDetector {

    public EncodingDetector() {
    }
    
    public String detect(InputStream fin, byte[] fileContent) throws IOException
    {        
        
        String charset = "ISO-8859-1";
        fin.read(fileContent);

        byte[] data =  fileContent;

        CharsetDetector detector = new CharsetDetector();
        detector.setText(data);
        CharsetMatch cm = detector.detect();

        if (cm != null) {
            int confidence = cm.getConfidence();
            //System.out.println("Encoding: " + cm.getName() + " - Confidence: " + confidence + "%");
            if (confidence > 50) {
                charset = cm.getName();
            }
        }        
        return charset;
    }
}
