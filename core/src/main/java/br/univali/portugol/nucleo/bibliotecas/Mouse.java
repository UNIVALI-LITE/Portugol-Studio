package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoConstante;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

@PropriedadesBiblioteca(tipo = TipoBiblioteca.RESERVADA)
@DocumentacaoBiblioteca
(
    descricao = "Esta biblioteca contém um conjunto de funções para manipular a entrada de dados através do mouse do computador\n"
            + "<b>IMPORTANTE:</b> Esta biblioteca não funciona no console de entrada e saída de dados do Portugol Studio, ela só funciona com a biblioteca Graficos, se o modo gráfico estiver iniciado.", 
    versao = "1.1"
)
public final class Mouse extends Biblioteca
{
    private static final Cursor cursorPadrao = Cursor.getDefaultCursor();
    private static final Cursor cursorTransparente = criarCursorTransparente();
            
    private final boolean[] buffer = new boolean[3];
    private final List<InstaladorMouse> instaladores;
    private final MouseAdapter observadorMouse;
    private final FocusAdapter observadorFoco;
    
    private int x;
    private int y;
    private int botoesPressionados = 0;
    private boolean aguardandoBotao = false;
    private int ultimoBotao = -1;    
        
    @DocumentacaoConstante(descricao = "Código numérico do botão esquerdo do mouse")
    public static final int BOTAO_ESQUERDO = 0;
    
    @DocumentacaoConstante(descricao = "Código numérico do botão direito do mouse")
    public static final int BOTAO_DIREITO = 1;
    
    @DocumentacaoConstante(descricao = "Código numérico do botão do meio do mouse")
    public static final int BOTAO_MEIO = 2;

    public Mouse()
    {
        instaladores = new ArrayList<>();
        
        observadorMouse = new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                botoesPressionados = 0;
                
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    buffer[BOTAO_ESQUERDO] = true;
                    botoesPressionados += 1;
                }
                else
                {
                    buffer[BOTAO_ESQUERDO] = false;
                }
                
                if (SwingUtilities.isRightMouseButton(e))
                {
                    buffer[BOTAO_DIREITO] = true;
                    botoesPressionados += 1;
                }
                else
                {
                    buffer[BOTAO_DIREITO] = false;
                }
                
                if (SwingUtilities.isMiddleMouseButton(e))
                {
                    buffer[BOTAO_MEIO] = true;
                    botoesPressionados += 1;
                }
                else
                {
                    buffer[BOTAO_MEIO] = false;
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    buffer[BOTAO_ESQUERDO] = true;
                    botoesPressionados += 1;
                }
                
                if (SwingUtilities.isRightMouseButton(e))
                {
                    buffer[BOTAO_DIREITO] = true;
                    botoesPressionados += 1;
                }
                
                if (SwingUtilities.isMiddleMouseButton(e))
                {
                    buffer[BOTAO_MEIO] = true;
                    botoesPressionados += 1;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    buffer[BOTAO_ESQUERDO] = false;
                    botoesPressionados -= 1;
                    ultimoBotao = BOTAO_ESQUERDO;
                }
                
                if (SwingUtilities.isRightMouseButton(e))
                {
                    buffer[BOTAO_DIREITO] = false;
                    botoesPressionados -= 1;
                    ultimoBotao = BOTAO_DIREITO;
                }
                
                if (SwingUtilities.isMiddleMouseButton(e))
                {
                    buffer[BOTAO_MEIO] = false;
                    botoesPressionados -= 1;
                    ultimoBotao = BOTAO_MEIO;
                }
                
                if (aguardandoBotao)
                {
                    acordarThread();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                x = e.getX();
                y = e.getY();
            }
        };
        
        observadorFoco = new FocusAdapter()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                buffer[0] = false;
                buffer[1] = false;
                buffer[2] = false;
                
                ultimoBotao = -1;
                botoesPressionados = 0;
                
                if (aguardandoBotao)
                {
                    aguardandoBotao = false;
                    acordarThread();
                }                
            }
        };
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Testa se um determinado <param>botão</param> do mouse está pressionado neste instante",
        parametros = 
        {
            @DocumentacaoParametro(nome = "botao", descricao = "o código do botão que será testado")
        },
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        },
        retorno = "o resultado do teste. <tipo>Verdadeiro</tipo> se o <param>botão</param> estiver pressionado no momento do teste. Caso contrário, retorna <tipo>falso</tipo>"
    )
    public boolean botao_pressionado(int botao) throws ErroExecucaoBiblioteca, InterruptedException
    {
        if ((botao >= 0) && (botao < buffer.length))
        {
            return buffer[botao];
        }
        else throw new ErroExecucaoBiblioteca(String.format("O código '%d' não é um código de botão válido", botao));
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Testa se existe algum botão do mouse pressionado neste instante",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        },
        retorno = "o resultado do teste. <tipo>Verdadeiro</tipo> se houver um botão do mouse pressionado no momento do teste. Caso contrário, retorna <tipo>falso</tipo>"
    )    
    public boolean algum_botao_pressionado() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return botoesPressionados > 0;
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Aguarda até que um botão seja clicado (isto é, foi pressionado e depois solto), e captura o seu código",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        },
        retorno = "o código do botão lido"
    )
    public int ler_botao() throws ErroExecucaoBiblioteca, InterruptedException, InterruptedException
    {
        synchronized (Mouse.this)
        {
            aguardandoBotao = true;
            wait();
            aguardandoBotao = false;
        }
        
        return ultimoBotao;
    }

    @DocumentacaoFuncao
    (
        descricao = "Obtém a coordenada X do mouse neste instante",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        },
        retorno = "a coordenada X do mouse neste instante"            
    )
    public int posicao_x() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return x;
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Obtém a coordenada Y do mouse neste instante",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        },
        retorno = "a coordenada Y do mouse neste instante"
    )    
    public int posicao_y() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return y;
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Oculta o cursor do mouse",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    ) 
    public void ocultar_cursor() throws ErroExecucaoBiblioteca, InterruptedException
    {
        for (InstaladorMouse instaladorMouse : instaladores)
        {
            instaladorMouse.definirCursor(cursorTransparente);
        }
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Exibe novamente o cursor do mouse caso ele esteja oculto",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )     
    public void exibir_cursor() throws ErroExecucaoBiblioteca, InterruptedException
    {
        for (InstaladorMouse instaladorMouse : instaladores)
        {
            instaladorMouse.definirCursor(cursorPadrao);
        }
    }    
    
    private synchronized void acordarThread()
    {
        notifyAll();
    }
    
    private static Cursor criarCursorTransparente()
    {
        try
        {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dim = toolkit.getBestCursorSize(1, 1);

            BufferedImage cursorImg = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = cursorImg.createGraphics();

            g2d.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
            g2d.clearRect(0, 0, dim.width, dim.height);

            g2d.dispose();

            return toolkit.createCustomCursor(cursorImg, new Point(0,0), "hiddenCursor");
        }
        catch (HeadlessException | IndexOutOfBoundsException ex)
        {
            return Cursor.getDefaultCursor();
        }
    }
    
    @Override
    public void inicializar(Programa programa, List<Biblioteca> bibliotecasReservadas) throws ErroExecucaoBiblioteca, InterruptedException
    {
        for (Biblioteca biblioteca : bibliotecasReservadas)
        {
            instalarMouse(biblioteca);
        }
    }
    
    @Override
    public void bibliotecaRegistrada(Biblioteca biblioteca) throws ErroExecucaoBiblioteca, InterruptedException
    {
        instalarMouse(biblioteca);
    }
    
    private void instalarMouse(Biblioteca biblioteca) throws ErroExecucaoBiblioteca, InterruptedException
    {
        if (biblioteca instanceof InstaladorMouse)
        {
            ((InstaladorMouse) biblioteca).instalarMouse(observadorMouse, observadorFoco);
            instaladores.add((InstaladorMouse) biblioteca);
        }
    }
    
    public interface InstaladorMouse
    {
        public void instalarMouse(MouseAdapter observadorMouse, FocusListener observadorFoco) throws ErroExecucaoBiblioteca, InterruptedException;
        public void definirCursor(Cursor cursor) throws ErroExecucaoBiblioteca, InterruptedException;
    }
}
