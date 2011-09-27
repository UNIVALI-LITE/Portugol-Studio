package br.univali.ps.controller;

import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.nucleo.TratadorExcecoes;
import br.univali.ps.ui.TelaPrincipal;
import br.univali.ps.ui.swing.ResultadoAnaliseTableModel;
import br.univali.ps.ui.util.FileHandle;
import java.io.File;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class PortugolControlador implements DocumentListener {

    TratadorExcecoes tratadorExcecoes = PortugolStudio.getInstancia().getTratadorExcecoes();
    TelaPrincipal telaPrincipal = new TelaPrincipal(this);
//    InterpretadorRunner interpretadorRunner;
    ResultadoAnaliseTableModel tabelaModel = new ResultadoAnaliseTableModel();

    public PortugolControlador() {
    }

    public void novo() {
        PortugolDocumento portugolDocument = new PortugolDocumento();
        portugolDocument.addDocumentListener(this);
        portugolDocument.setChanged(false);
        telaPrincipal.habilitaCompilar(true);
        telaPrincipal.configurarBotoesEditar();
 //       saida.setModelSaidaErros(tabelaModel);
    }

    public void abrir(File[] arquivos) {
        try {
            for (int i = 0; i < arquivos.length; i++) {
                File arquivo = arquivos[i];
                String codigoFonte = FileHandle.open(arquivo);
                PortugolDocumento portugolDocument = new PortugolDocumento();
                portugolDocument.insertString(0, codigoFonte, null);
        //        editor.novaAba(arquivo.getName(), portugolDocument);
                portugolDocument.setChanged(false);
                portugolDocument.setFile(arquivo);
                portugolDocument.addDocumentListener(this);
                telaPrincipal.habilitaSalvar(false);
                telaPrincipal.habilitaCompilar(true);
                telaPrincipal.configurarBotoesEditar();
                //saida.setModelSaidaErros(tabelaModel);
               //editor.configurarFocusListener(telaPrincipal.getAcaoColar());
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
         //       editor.setTituloAbaSelecionada(documento.getFile().getName());
                documento.setChanged(false);
                telaPrincipal.habilitaSalvar(false);
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
       /// PortugolDocumento documento = (PortugolDocumento) editor.getDocumentAbaSelecionada();
       // documento.setFile(arquivo);
       // salvar(documento);
    }

    public void executar(PortugolDocumento documento) 
    {
        try 
        {
            
                telaPrincipal.habilitaCompilar(false);
                tabelaModel.setResultadoAnalise(null);
                salvar(documento);
//                saida.limpar();
  //              saida.mostrarConsole();

                String codigo = documento.getCodigoFonte();
                ResultadoAnalise resultadoAnalise = Portugol.analisar(codigo);
        
                if (resultadoAnalise.getNumeroTotalErros() > 0)
                {
                    tabelaModel.setResultadoAnalise(resultadoAnalise);
//                    saida.mostrarTabelaMensagem();
                }
                else
                    
                if (resultadoAnalise.getNumeroTotalErros() == 0) 
                {
                    telaPrincipal.habilitarDebug(true);
                    //interpretadorRunner = new InterpretadorRunner();
                    //interpretadorRunner.start();
                } else 
                {
//                    saida.mostrarTabelaMensagem();
                }
            
            
            telaPrincipal.habilitaCompilar(true);
        } catch (Exception ex) 
        {
            tratadorExcecoes.exibirExcecao(ex);
            telaPrincipal.habilitaCompilar(true);
       // } catch (ExcecaoArquivoContemErros ex) {
       //     tratadorExcecoes.exibirExcecao(ex);
       //     telaPrincipal.habilitaCompilar(true);
       // }
        }
    }

 

    public void iniciar() {
      //  telaPrincipal.setEditor(editor);
        //telaPrincipal.setPainelSaida(saida);
        telaPrincipal.setVisible(true);
    }

    public void fecharAplicativo() {
       // editor.fecharTodasAbas();
        System.exit(0);
    }

    public void fecharAbaAtual() {
       // editor.fecharAbaSelecionada();
    }

    public void fecharTodasAbas() {
       // editor.fecharTodasAbas();
    }

    public void interromper(PortugolDocumento documento) {
        //interpretadorRunner.interrupt();
        //interpretadorRunner = null;
    }

    public void insertUpdate(DocumentEvent de) {
        ((PortugolDocumento) de.getDocument()).setChanged(true);
        telaPrincipal.habilitaSalvar(true);
    }

    public void removeUpdate(DocumentEvent de) {
        ((PortugolDocumento) de.getDocument()).setChanged(true);
        telaPrincipal.habilitaSalvar(true);
    }

    public void changedUpdate(DocumentEvent de) {
        ((PortugolDocumento) de.getDocument()).setChanged(true);
        telaPrincipal.habilitaSalvar(true);
    }

    public void nenhumDocumentoAberto(){
        telaPrincipal.habilitaSalvarComo(false);
        telaPrincipal.desabilitarBotoesEditar();
    }

    public void documentoSelecionado(Document document) {
        telaPrincipal.habilitaSalvarComo(true);
        telaPrincipal.habilitaSalvar(((PortugolDocumento) document).isChanged());
        telaPrincipal.configurarBotoesEditar();
      //  editor.configurarFocusListener(telaPrincipal.getAcaoColar());
    }
/*
    private class InterpretadorRunner extends Thread {

        //Interpretador interpretador;

        public InterpretadorRunner(Interpretador interpretador) {
            this.interpretador = interpretador;
        }

        @Override
        public void run() {
            try {
                long horaInicial = System.currentTimeMillis();
                interpretador.setSaida(saida);
                interpretador.setEntrada(saida);
              //  interpretador.interpretar(((PortugolDocumento)editor.getDocumentAbaSelecionada()).getFile(), new String[]{"teste"});
                long tempo = (System.currentTimeMillis() - horaInicial) / 1000;
                saida.imprimir("\n\nPrograma executado com sucesso! Tempo de execução: " + tempo + " segundos");

            } catch (Exception ex) {
                tratadorExcecoes.exibirExcecao(ex);
                telaPrincipal.habilitaCompilar(true);
                telaPrincipal.habilitarDebug(false);
            }
            telaPrincipal.habilitaCompilar(true);
            telaPrincipal.habilitarDebug(false);
        }

        @Override
        public void interrupt() {
            super.interrupt();
            saida.imprimir("\n\nPrograma INTERROMPIDO.");
            telaPrincipal.habilitaCompilar(true);
            telaPrincipal.habilitarDebug(false);
        }
    }*/
}
