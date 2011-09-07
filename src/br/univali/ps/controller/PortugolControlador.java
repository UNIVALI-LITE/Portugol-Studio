package br.univali.ps.controller;

import br.univali.portugol.nucleo.Interpretador;
import br.univali.portugol.nucleo.excecoes.Mensagem;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.nucleo.TratadorExcecoes;
import br.univali.ps.ui.Editor;
import br.univali.ps.ui.TelaPrincipal;
import br.univali.ps.ui.PainelSaida;
import br.univali.ps.ui.util.FileHandle;
import java.io.File;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class PortugolControlador implements DocumentListener{

    TratadorExcecoes tratadorExcecoes = PortugolStudio.getInstancia().getTratadorExcecoes();
    Editor editor = new Editor(this);
    PainelSaida saida = new PainelSaida(this);
    TelaPrincipal telaPrincipal = new TelaPrincipal(this);

    public PortugolControlador() {
    }

    public void novo(){
        PortugolDocumento portugolDocument = new PortugolDocumento();
        editor.novaAba("Sem título", portugolDocument);
        portugolDocument.addDocumentListener(this);
        portugolDocument.setChanged(false);
    }

    public void abrir(File arquivo) {
        try {
            String codigoFonte = FileHandle.open(arquivo);
            PortugolDocumento portugolDocument = new PortugolDocumento();
            portugolDocument.insertString(0, codigoFonte, null);
            editor.novaAba(arquivo.getName(), portugolDocument);
            portugolDocument.setChanged(false);
            portugolDocument.setFile(arquivo);
            portugolDocument.addDocumentListener(this);
            telaPrincipal.habilitaSalvar(false);
        } catch (Exception ex) {
            tratadorExcecoes.exibirExcecao(ex);
        }
    }

    public void salvar() {
        try {
            PortugolDocumento documento = (PortugolDocumento) editor.getDocumentAbaSelecionada();
            String texto = documento.getText(0, documento.getLength());
            if (documento.getFile() != null) {
                FileHandle.save(texto, documento.getFile());
                editor.setTituloAbaSelecionada(documento.getFile().getName());
                //TODO fazer um listener na ABA para ela saber quando é pra destacar modificado ou não. substituir documentListener por um especifico do Portugol
                documento.setChanged(false);
                telaPrincipal.habilitaSalvar(false);
            } else {
                telaPrincipal.dialogoSalvar();
            }
        } catch (BadLocationException ex) {
            tratadorExcecoes.exibirExcecao(ex);
        } catch (Exception ex){
            tratadorExcecoes.exibirExcecao(ex);
        }
    }

    public void salvarComo(File arquivo) {
        PortugolDocumento documento = (PortugolDocumento) editor.getDocumentAbaSelecionada();
        documento.setFile(arquivo);
        salvar();
    }

    public void executar(){
//        ListaMensagens listaMensagens = new ListaMensagens();
//        try {
//            if (saveFileAction.getFile() != null) {
//                btnCompile.setEnabled(false);
//                saveFileAction.actionPerformed(null);
//                console.setText(null);
//                painelSaida.setSelectedIndex(0);
//
//                AnalizadorSemantico analizadorSemantico = new AnalizadorSemantico(saveFileAction.getFile());
//                listaMensagens = analizadorSemantico.analizar();
//
//
//
//                //ModeloExemplo1 modelo = new ModeloExemplo1();
//
//                ModeloExemplo2 modelo = new ModeloExemplo2();
//                tabelaMensagens.setDefaultRenderer(Mensagem.class, new RenderizadorMensagem());
//
//
//                tabelaMensagens.setModel(modelo);
//                modelo.adicionar(listaMensagens);
//
//
//                if (listaMensagens.getNumeroErros() == 0) {
//                    btnDebug.setEnabled(true);
//                    interpretadorRunner = new InterpretadorRunner(new Interpretador());
//                    interpretadorRunner.start();
//                } else {
//                    painelSaida.setSelectedIndex(1);
//                }
//
//
//            } else {
//                saveFileAction.actionPerformed(null);
//            }
//            btnCompile.setEnabled(true);
//        } catch (ExcecaoArquivoContemErros ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//            showError("O arquivo contém erros", "Portugol Studio");
//            btnCompile.setEnabled(true);
//        }
    }

    public void setCursor(Mensagem m) {
        editor.selecionarAbaArquivo(m.getArquivo());
        editor.posicionaCursor(m.getLinha(), m.getColuna());
    }

    public void iniciar() {
        telaPrincipal.setEditor(editor);
        telaPrincipal.setPainelSaida(saida);
        telaPrincipal.setVisible(true);
    }

    public void fecharAplicativo() {
        editor.fecharTodasAbas();
        System.exit(0);
    }

    public void fecharAbaAtual() {
        editor.fecharAbaSelecionada();
    }

    public void fecharTodasAbas() {
        editor.fecharTodasAbas();
    }

    public void interromper() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void insertUpdate(DocumentEvent de) {
        ((PortugolDocumento)de.getDocument()).setChanged(true);
        telaPrincipal.habilitaSalvar(true);
    }

    public void removeUpdate(DocumentEvent de) {
        ((PortugolDocumento)de.getDocument()).setChanged(true);
        telaPrincipal.habilitaSalvar(true);
    }

    public void changedUpdate(DocumentEvent de) {
        ((PortugolDocumento)de.getDocument()).setChanged(true);
        telaPrincipal.habilitaSalvar(true);
    }

    public void documentoSelecionado(Document document) {
        telaPrincipal.habilitaSalvar(((PortugolDocumento)document).isChanged());
    }

    private class InterpretadorRunner extends Thread {

        Interpretador interpretador;

        public InterpretadorRunner(Interpretador interpretador) {
            this.interpretador = interpretador;
        }

        @Override
        public void run() {
//            try {
//                long horaInicial = System.currentTimeMillis();
//                interpretador.setSaida(MainFrame.this);
//                interpretador.setEntrada(MainFrame.this);
//                interpretador.interpretar(saveFileAction.getFile(), new String[]{"teste"});
//                long tempo = (System.currentTimeMillis() - horaInicial) / 1000;
//                console.append("\n\nPrograma executado com sucesso! Tempo de execução: " + tempo + " segundos");
//
//            } catch (Exception ex) {
//                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                btnCompile.setEnabled(true);
//                btnDebug.setEnabled(false);
//            }
//             btnCompile.setEnabled(true);
//             btnDebug.setEnabled(false);
        }

        @Override
        public void interrupt() {
//            super.interrupt();
//            console.append("\n\nPrograma INTERROMPIDO.");
//            btnCompile.setEnabled(true);
//            btnDebug.setEnabled(false);
        }
    }



}
