package br.univali.ps.ui.ajuda;

import java.io.File;
import java.net.URL;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class NavegadorAjuda extends javax.swing.JFrame implements HyperlinkListener {

    public NavegadorAjuda(String path) {
        initComponents();
        setTitle("Ajuda");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jTextPane1.getDocument().putProperty("IgnoreCharsetDirective", Boolean.TRUE);

        this.jTextPane1.addHyperlinkListener(this);
       
        displayPage(path);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextPane1.setContentType("text/html");
        jTextPane1.setEditable(false);
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void hyperlinkUpdate(HyperlinkEvent he) {
        if (he.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                // Loads the new page represented by link clicked
                URL url = he.getURL();               
                jTextPane1.setPage(url);                
            } catch (Exception exc) {
            }
        }
    }

    public void displayPage(String page) {

        // Check if user has specified any command line parameter
        if (page != null && page.trim().length() > 0) {

            /* User may specify one of the following
            1. A relative path for a local file
            2. An absolute path for a local file
            3. A URL
            Check for a valid user input
             */

            File localFile = new File(page);

            // Chgeck if the file exists on the dist
            if (localFile.exists() && localFile.isFile()) {
                /* Check if user specified the absolute path
                Add the file protocol in front of file name */

                page = "file:///" + localFile.getAbsolutePath();
                try {
                    jTextPane1.setPage(page);
                } catch (Exception e1) {
                    // Not a valid URL
                    jTextPane1.setText("Could not load page:" + page + "\n"
                            + "Error:" + e1.getMessage());
                }
            }
        }
    }
}
