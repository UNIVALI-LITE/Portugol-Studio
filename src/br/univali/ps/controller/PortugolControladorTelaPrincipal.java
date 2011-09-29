package br.univali.ps.controller;

import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.nucleo.TratadorExcecoes;
import br.univali.ps.ui.Aba;
import br.univali.ps.ui.AbaAjuda;
import br.univali.ps.ui.AbaCodigoFonte;
import br.univali.ps.ui.AbaConsole;
import br.univali.ps.ui.AbaInicial;
import br.univali.ps.ui.PainelTabulado;
import br.univali.ps.ui.TelaPrincipal;
import br.univali.ps.ui.util.FileHandle;
import java.io.File;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class PortugolControladorTelaPrincipal   {

    private TratadorExcecoes tratadorExcecoes = PortugolStudio.getInstancia().getTratadorExcecoes();
    private TelaPrincipal telaPrincipal;
    
    public PortugolControladorTelaPrincipal() {
        
        telaPrincipal = new TelaPrincipal(this);
    }

    public void novo() {
        AbaCodigoFonte aba = new AbaCodigoFonte(telaPrincipal.getPainelTabulado());
        aba.setPortugolControlador(this);
        telaPrincipal.setAcaoSalvar(aba.getAcaoSalvar());
    }

    public void abrir(File[] arquivos) {
        try {
            for (int i = 0; i < arquivos.length; i++) {
                File arquivo = arquivos[i];
                String codigoFonte = FileHandle.open(arquivo);
                PortugolDocumento portugolDocument = new PortugolDocumento();
                portugolDocument.insertString(0, codigoFonte, null);
                telaPrincipal.getPainelTabulado();
                AbaCodigoFonte abaCodigoFonte = new AbaCodigoFonte(telaPrincipal.getPainelTabulado());
                telaPrincipal.setAcaoSalvar(abaCodigoFonte.getAcaoSalvar());
                abaCodigoFonte.setPortugolControlador(this);
                abaCodigoFonte.setPortugolDocumento(portugolDocument);
                portugolDocument.setChanged(false);
                portugolDocument.setFile(arquivo);
            }
        } catch (Exception ex) {
            tratadorExcecoes.exibirExcecao(ex);
        }
    }

    public void salvar(PortugolDocumento documento) {
        try {
            String texto = documento.getText(0, documento.getLength());
            if (documento.getFile() != null) {
                FileHandle.save(texto, documento.getFile());
                documento.setChanged(false);
            } else {
                telaPrincipal.dialogoSalvar();
            }
        } catch (BadLocationException ex) {
            tratadorExcecoes.exibirExcecao(ex);
        } catch (Exception ex) {
            tratadorExcecoes.exibirExcecao(ex);
        }
    }

    public void salvarComo(File arquivo) {
        AbaCodigoFonte aba = (AbaCodigoFonte) ((PainelTabulado)telaPrincipal.getPainelTabulado()).getAbaSelecionada();
        if (aba != null) {
            aba.getPortugolDocumento().setFile(arquivo);
            salvar(aba.getPortugolDocumento());
        }
    }

    public void executar(PortugolDocumento documento) 
    {
        
    }

    public void iniciar() {
        telaPrincipal.setVisible(true);
    }

    public void fecharAplicativo() 
    {    	
    	PainelTabulado painelTabulado = (PainelTabulado)telaPrincipal.getPainelTabulado(); 
        painelTabulado.fecharTodasAbas(AbaCodigoFonte.class);
        
        //if (!painelTabulado.temAbaAberta(AbaCodigoFonte.class))
        	System.exit(0);
    }

    public void fecharAbaAtual() {
       Aba aba =((PainelTabulado)telaPrincipal.getPainelTabulado()).getAbaSelecionada();
       if (aba != null) {
           aba.fechar();
       }
    }

    public void fecharTodasAbas() 
    {
    	for (Class<? extends Aba> classeAba: Aba.classesFilhas())
    	{
    		if (classeAba != AbaInicial.class)
    			((PainelTabulado)telaPrincipal.getPainelTabulado()).fecharTodasAbas(classeAba);
    	}       
    }    
    
     public void interromper(PortugolDocumento documento) {

    }

    public void nenhumDocumentoAberto(){
        telaPrincipal.habilitaSalvarComo(false);
        telaPrincipal.desabilitarBotoesEditar();
    }

    public void documentoSelecionado(Document document) {
        telaPrincipal.habilitaSalvarComo(true);
        telaPrincipal.habilitaSalvar(((PortugolDocumento) document).isChanged());
//        telaPrincipal.configurarBotoesEditar();
      //  editor.configurarFocusListener(telaPrincipal.getAcaoColar());
    }

}
