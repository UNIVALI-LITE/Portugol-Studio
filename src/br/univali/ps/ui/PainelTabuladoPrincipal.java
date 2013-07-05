package br.univali.ps.ui;

import br.univali.ps.ui.acoes.AcaoAbrirArquivo;
import br.univali.ps.ui.acoes.AcaoNovoArquivo;

public class PainelTabuladoPrincipal extends PainelTabulado{

    private BotoesControleAba cabecalhoAbaInicial;
    
    
    public PainelTabuladoPrincipal() {
        initComponents();
        Aba aba = new AbaInicial(this);
        cabecalhoAbaInicial = (BotoesControleAba) aba.cabecalho;
    }
    
    public void init(AcaoAbrirArquivo acaoAbrir, AcaoNovoArquivo acaoNovo) {
        cabecalhoAbaInicial.setAcaoAbrirAction(acaoAbrir);
        cabecalhoAbaInicial.setAcaoNovoArquivo(acaoNovo);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setFocusable(false);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    
    
}
