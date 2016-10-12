package br.univali.ps.nucleo.biblioteca;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@PropriedadesBiblioteca(tipo = TipoBiblioteca.RESERVADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca permite executar algumas ações dentro do Portugol Studio a partir dos programas ",
        versao = "1.0"
)
public final class PortugolStudio extends Biblioteca {

    private static int ultimoEstado = JFrame.MAXIMIZED_BOTH;

    private static synchronized void setUltimoEstado(int ultimoEstado) {
        PortugolStudio.ultimoEstado = ultimoEstado;
    }

    private static synchronized int getUltimoEstado() {
        return ultimoEstado;
    }

    @DocumentacaoFuncao(
            descricao = "Minimiza a janela principal do Portugol Studio",
            autores
            = {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public void minimizar() throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            SwingUtilities.invokeAndWait(() -> {
                setUltimoEstado(br.univali.ps.nucleo.PortugolStudio.getInstancia().getTelaPrincipal().getExtendedState());
                br.univali.ps.nucleo.PortugolStudio.getInstancia().getTelaPrincipal().setExtendedState(JFrame.ICONIFIED);
            });
        } catch (InvocationTargetException ex) {
            throw new ErroExecucaoBiblioteca(ex);
        }

    }

    @Override
    public void finalizar() throws ErroExecucaoBiblioteca {
        try {
            SwingUtilities.invokeAndWait(() -> {
                if (br.univali.ps.nucleo.PortugolStudio.getInstancia().getTelaPrincipal().getExtendedState() == JFrame.ICONIFIED) {
                    br.univali.ps.nucleo.PortugolStudio.getInstancia().getTelaPrincipal().setExtendedState(getUltimoEstado());
                    br.univali.ps.nucleo.PortugolStudio.getInstancia().getTelaPrincipal().toFront();
                    br.univali.ps.nucleo.PortugolStudio.getInstancia().getTelaPrincipal().requestFocusInWindow();
                }
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            throw new ErroExecucaoBiblioteca(ex);
        }

    }
}
