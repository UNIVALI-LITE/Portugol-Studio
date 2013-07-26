package br.univali.ps.ui;

import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosConstante;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosFuncao;



public class PainelSaida extends PainelTabulado {

    private AbaConsole console;
    private AbaMensagemCompilador mensagemCompilador;
    private AbaEnunciado enunciadoAba;
    private AbaDocumentacaoBiblioteca abaDocumentacaoBiblioteca;
    
    public PainelSaida() {
        initComponents();
        console = new AbaConsole(this);
        mensagemCompilador = new AbaMensagemCompilador(this);
        //depurador = new AbaDepurador(this);
        //sett
    }

    public AbaConsole getConsole() {
        return console;
    }

  
    public AbaMensagemCompilador getMensagemCompilador() {
        return mensagemCompilador;
    }

    public AbaEnunciado getEnunciadoAba() {
        return enunciadoAba;
    }
  
    private void criarAbaDocumentacao()
    {
        abaDocumentacaoBiblioteca = new AbaDocumentacaoBiblioteca(this);
    }
      
    public void exibirDocumentacaoFuncaoBiblioteca(MetaDadosBiblioteca metaDadosBiblioteca, MetaDadosFuncao metaDadosFuncao)
    {
        if (abaDocumentacaoBiblioteca == null)
        {
            criarAbaDocumentacao();
        }
        
        abaDocumentacaoBiblioteca.exibirDocumentacao(metaDadosBiblioteca, metaDadosFuncao);
    }

    public void exibirDocumentacaoBiblioteca(MetaDadosBiblioteca metaDadosBiblioteca)
    {
        if (abaDocumentacaoBiblioteca == null)
        {
            criarAbaDocumentacao();
        }
        
        abaDocumentacaoBiblioteca.exibirDocumentacao(metaDadosBiblioteca);
    }

    public void exibirDocumentacaoConstanteBiblioteca(MetaDadosBiblioteca metaDadosBiblioteca, MetaDadosConstante metaDadosConstante)
    {
        if (abaDocumentacaoBiblioteca == null)
        {
            criarAbaDocumentacao();
        }
        
        abaDocumentacaoBiblioteca.exibirDocumentacao(metaDadosBiblioteca, metaDadosConstante);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 8, 8, 8));
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
