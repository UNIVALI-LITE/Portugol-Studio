package br.univali.ps.nucleo.biblioteca;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import br.univali.ps.ui.Lancador;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@PropriedadesBiblioteca(tipo = TipoBiblioteca.RESERVADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca permite executar algumas ações dentro do Portugol Studio a partir dos programas ",
        versao = "1.0"
)
public final class PortugolStudio extends Biblioteca
{
    private static int ultimoEstado = JFrame.MAXIMIZED_BOTH;

    private static synchronized  void setUltimoEstado(int ultimoEstado)
    {
        PortugolStudio.ultimoEstado = ultimoEstado;
    }

    private static synchronized int getUltimoEstado() 
    {
        return ultimoEstado;
    }
    
        @DocumentacaoFuncao(
            descricao = "Minimiza a janela principal do Portugol Studio",
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public void minimizar() throws ErroExecucaoBiblioteca
    {
        try {
            SwingUtilities.invokeAndWait(() -> {
                setUltimoEstado(Lancador.getJFrame().getExtendedState());
                Lancador.getJFrame().setExtendedState(JFrame.ICONIFIED);
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            throw new ErroExecucaoBiblioteca(ex);
        }
        
    }

    @Override
    protected void finalizar() throws ErroExecucaoBiblioteca
    {
        try {
            SwingUtilities.invokeAndWait(() -> {
                if (Lancador.getJFrame().getExtendedState() == JFrame.ICONIFIED)
                {
                    Lancador.getJFrame().setExtendedState(getUltimoEstado());
                    Lancador.getJFrame().toFront();
                    Lancador.getJFrame().requestFocusInWindow();
                }
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            throw new ErroExecucaoBiblioteca(ex);
        }
        
    }
}
