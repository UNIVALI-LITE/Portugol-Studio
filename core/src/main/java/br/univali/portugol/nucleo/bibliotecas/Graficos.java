package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.*;
import br.univali.portugol.nucleo.bibliotecas.graficos.CacheImagens;
import br.univali.portugol.nucleo.bibliotecas.graficos.Imagem;
import br.univali.portugol.nucleo.bibliotecas.graficos.ImagemGenerica;
import br.univali.portugol.nucleo.bibliotecas.graficos.JanelaGrafica;
import br.univali.portugol.nucleo.bibliotecas.graficos.JanelaGraficaImpl;
import br.univali.portugol.nucleo.bibliotecas.graficos.Utils;
import br.univali.portugol.nucleo.bibliotecas.graficos.ImagemGif;
import br.univali.portugol.nucleo.bibliotecas.graficos.SuperficieDesenho;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Luiz Fernando Noschang
 * @author Elieser A. de Jesus
 */
@PropriedadesBiblioteca(tipo = TipoBiblioteca.RESERVADA)
@DocumentacaoBiblioteca(
    descricao = "Esta biblioteca permite inicializar e utilizar um ambiente gráfico com "
    + "suporte ao desenho de primitivas gráficas e de imagens carregadas do "
    + "sistema de arquivos",
    versao = "1.6"
)
public final class Graficos extends Biblioteca implements Teclado.InstaladorTeclado, Mouse.InstaladorMouse
{
    
    private static final Logger LOGGER = Logger.getLogger(Graficos.class.getName());
    
    @DocumentacaoConstante(descricao = "constante que representa a cor 'preto'")
    public static final int COR_PRETO = Color.BLACK.getRGB();

    @DocumentacaoConstante(descricao = "constante que representa a cor 'branca'")
    public static final int COR_BRANCO = Color.WHITE.getRGB();

    @DocumentacaoConstante(descricao = "constante que representa a cor 'azul'")
    public static final int COR_AZUL = Color.BLUE.getRGB();

    @DocumentacaoConstante(descricao = "constante que representa a cor 'vermelho'")
    public static final int COR_VERMELHO = Color.RED.getRGB();

    @DocumentacaoConstante(descricao = "constante que representa a cor 'verde'")
    public static final int COR_VERDE = Color.GREEN.getRGB();

    @DocumentacaoConstante(descricao = "constante que representa a cor 'amarelo'")
    public static final int COR_AMARELO = Color.YELLOW.getRGB();
    
    
    
    @DocumentacaoConstante(descricao = "constante que representa o gradiente na rotação 0")
    public static final int GRADIENTE_DIREITA = 0;
        
    @DocumentacaoConstante(descricao = "constante que representa o gradiente na rotação 0")
    public static final int GRADIENTE_ESQUERDA = 1;
    
    @DocumentacaoConstante(descricao = "constante que representa o gradiente na rotação 0")
    public static final int GRADIENTE_ACIMA = 2;
    
    @DocumentacaoConstante(descricao = "constante que representa o gradiente na rotação 0")
    public static final int GRADIENTE_ABAIXO = 3;
    
    @DocumentacaoConstante(descricao = "constante que representa o gradiente na rotação inferior direito")
    public static final int GRADIENTE_INFERIOR_DIREITO = 4;
    
    @DocumentacaoConstante(descricao = "constante que representa o gradiente na rotação inferior direito")
    public static final int GRADIENTE_INFERIOR_ESQUERDO = 5;
    
    @DocumentacaoConstante(descricao = "constante que representa o gradiente na rotação inferior direito")
    public static final int GRADIENTE_SUPERIOR_DIREITO = 6;
    
    @DocumentacaoConstante(descricao = "constante que representa o gradiente na rotação inferior direito")
    public static final int GRADIENTE_SUPERIOR_ESQUERDO = 7;
    
    
    
    @DocumentacaoConstante(descricao = "constante que representa o canal 'VERMELHO'")
    public static final int CANAL_R = 0;
    
    @DocumentacaoConstante(descricao = "constante que representa o canal 'VERDE'")
    public static final int CANAL_G = 1;
    
    @DocumentacaoConstante(descricao = "constante que representa o canal 'AZUL'")
    public static final int CANAL_B = 2;

    private Programa programa;
    private JanelaGrafica janela;
    private SuperficieDesenho superficieDesenho;
    private CacheImagens cacheImagens;
    private boolean inicializado = false;

    @Override
    public void inicializar(final Programa programa, final List<Biblioteca> bibliotecasReservadas) throws ErroExecucaoBiblioteca, InterruptedException
    {
        this.programa = programa;
        this.janela = JanelaGraficaImpl.criar(programa);
        this.superficieDesenho = this.janela.getSuperficieDesenho();
        this.cacheImagens = CacheImagens.criar();
    }

    @Override
    public void finalizar() throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela.ocultar();
        cacheImagens.liberar();
        inicializado = false;

    }

    @NaoExportar
    @Override
    public void instalarTeclado(KeyListener observadorTeclado, WindowListener observadorJanela) throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela.instalarTeclado(observadorTeclado, observadorJanela);
    }

    @NaoExportar
    @Override
    public void instalarMouse(MouseAdapter observadorMouse, FocusListener observadorFoco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela.instalarMouse(observadorMouse, observadorFoco);
    }

    @NaoExportar
    @Override
    public void definirCursor(Cursor cursor) throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela.definirCursor(cursor);
    }

    @DocumentacaoFuncao(
        descricao
        = "Inicia o modo gráfico e exibe uma janela com as configurações padrão (tamanho 640x480 e fundo preto). "
        + "Se o modo gráfico já estiver iniciado, nada acontecerá",
        parametros =
        {
            @DocumentacaoParametro(
                    nome = "manter_visivel",
                    descricao = "define se a janela do ambiente gráfico deve permanecer sempre visível sobre as outras janelas (útil durante a depuração)"
            )
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void iniciar_modo_grafico(final boolean manter_visivel) throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela.exibir(true);
        inicializado = true;
    }

    @DocumentacaoFuncao(
        descricao = "Encerra o programa como se o usuário tivesse clicado no botão 'Fechar' da janela",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void fechar_janela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().fechar();
    }

    @DocumentacaoFuncao(
        descricao = "Minimiza a janela do ambiente gráfico, como se o usuário tivesse clicado no botão 'Minimizar' da janela",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void minimizar_janela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().minimizar();
    }

    @DocumentacaoFuncao(
        descricao = "Restaura a janela do ambiente gráfico, como se o usuário tivesse clicado no ícone do programa na barra de tarefas do Sistema Operacional",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void restaurar_janela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().restaurar();
    }

    @DocumentacaoFuncao(
        descricao = "Oculta a borda da janela do modo gráfico, fazendo com que somente o conteúdo da janela seja exibido",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void ocultar_borda_janela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().ocultarBorda();
    }

    @DocumentacaoFuncao(
        descricao = "Exibe novamente a borda da janela do modo gráfico, caso ela esteja oculta",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void exibir_borda_janela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().exibirBorda();
    }

    @DocumentacaoFuncao(
        descricao = "Encerra o modo gráfico e fecha a janela criada com a função 'iniciar_modo_grafico'",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void encerrar_modo_grafico() throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().ocultar();
        inicializado = false;
    }

    @DocumentacaoFuncao(
        descricao = "altera as dimensões da janela do ambiente gráfico",
        parametros =
        {
            @DocumentacaoParametro(nome = "largura", descricao = "a nova largura da janela"),
            @DocumentacaoParametro(nome = "altura", descricao = "a nova altura da janela")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_dimensoes_janela(final int largura, final int altura) throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().definirDimensoes(largura, altura);
    }

    @DocumentacaoFuncao(
        descricao = "define o texto da janela do ambiente gráfico",
        parametros =
        {
            @DocumentacaoParametro(nome = "titulo", descricao = "o novo título da janela")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_titulo_janela(final String titulo) throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().definirTitulo(titulo);
    }

    @DocumentacaoFuncao(
        descricao = "limpa o desenho do ambiente e gráfico e preenche o fundo com a cor atual",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void limpar() throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.limpar();
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }
    }

    @DocumentacaoFuncao(
        descricao
        = "Quando uma função de desenho da biblioteca é chamada, o desenho não é realizado imediatamente na tela, "
        + "mas sim, em uma área reservada da memória. Isto é feito com o objetivo de aumentar o desempenho do "
        + "programa e minimizar outros problemas. Esta técnica é chamada de <b>Back Buffer</b> ou <b>Double Buffer</b>.<br><br>"
        + "A função renderizar, faz com que os desenhos existentes no <b>Back Buffer</b> sejam desenhados na tela.<br><br>"
        + "Esta função deve ser chamada sempre após todas as outras funções de desenho, para garantir que todos os"
        + " desenhos sejam exibidos",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        },
        referencia = "http://en.wikipedia.org/wiki/Multiple_buffering#Double_buffering_in_computer_graphics"
    )
    public void renderizar() throws ErroExecucaoBiblioteca, InterruptedException
    {
        superficieDesenho.renderizar();
    }

    @DocumentacaoFuncao(
        descricao
        = "Esta função cria uma nova imagem em memória e renderiza todos os desenhos do ambiente gráfico nesta nova imagem ao invés de "
        + "renderizá-los na tela",
        parametros =
        {
            @DocumentacaoParametro(nome = "largura", descricao = "a largura da nova imagem"),
            @DocumentacaoParametro(nome = "altura", descricao = "a altura da nova imagem")
        },
        retorno = "o endereço de memória da nova imagem",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int renderizar_imagem(int largura, int altura) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            BufferedImage imagem = superficieDesenho.renderizarImagem(largura, altura);

            return cacheImagens.adicionarImagem(new ImagemGenerica(imagem));
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }
    }

    @DocumentacaoFuncao(
        descricao
        = "Desenha um retângulo na posição definida pelos parâmetros <param>x</param> e <param>y</param> "
        + "e com as dimensões especificadas pelos parâmetros <param>largura</param> e <param>altura</param>. <br><br>"
        + "O retângulo é desenhado na tela a partir do seu canto superior esquerdo ",
        parametros =
        {
            @DocumentacaoParametro(nome = "x", descricao = "a posição (distância) do retângulo no eixo horizontal, em relação ao lado esquerdo da janela"),
            @DocumentacaoParametro(nome = "y", descricao = "a posição (distância) do retângulo no eixo vertical, em relação ao topo da janela"),
            @DocumentacaoParametro(nome = "largura", descricao = "a largura do retângulo em pixels"),
            @DocumentacaoParametro(nome = "altura", descricao = "a altura do retângulo em pixels"),
            @DocumentacaoParametro(nome = "arredondar_cantos", descricao = "define se o retângulo deverá ter cantos arredondados"),
            @DocumentacaoParametro(
                    nome = "preencher",
                    descricao
                    = "define se o retângulo será preenchido com a cor do ambiente gráfico. "
                    + "Se o valor for <tipo>verdadeiro</tipo>, o retângulo será preenchido. Se o valor for "
                    + "<tipo>falso</tipo>, somente o contorno do retângulo será desenhado"
            )
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        }
    )
    public void desenhar_retangulo(final int x, final int y, final int largura, final int altura, final boolean arredondar_cantos, final boolean preencher) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.desenharRetangulo(x, y, largura, altura, arredondar_cantos, preencher);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    }
    
    @DocumentacaoFuncao(
        descricao
        = "Desenha poligono",
        parametros =
        {
            @DocumentacaoParametro(nome = "pontos", descricao = "a largura do retângulo em pixels"),
            
            @DocumentacaoParametro(
                    nome = "preencher",
                    descricao
                    = "define se o retângulo será preenchido com a cor do ambiente gráfico. "
                    + "Se o valor for <tipo>verdadeiro</tipo>, o retângulo será preenchido. Se o valor for "
                    + "<tipo>falso</tipo>, somente o contorno do retângulo será desenhado"
            )
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        }
    )
    public void desenhar_poligono(final int[][] pontos, final boolean preencher) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.desenharPoligono(pontos, preencher);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    };
    
    @DocumentacaoFuncao(
        descricao
        = "Desenha uma elipse na posição definida pelos parâmetros <param>x</param> e <param>y</param> "
        + "e com as dimensões especificadas pelos parâmetros <param>largura</param> e <param>altura</param>, .<br><br>"
        + "A elipse é desenhada na tela a partir do seu canto superior esquerdo",
        parametros =
        {
            @DocumentacaoParametro(nome = "x", descricao = "a posição (distância) do círculo no eixo horizontal, em relação ao lado esquerdo da janela"),
            @DocumentacaoParametro(nome = "y", descricao = "a posição (distância) do círculo no eixo vertical, em relação ao topo da janela"),
            @DocumentacaoParametro(nome = "largura", descricao = "a largura da elipse em pixels"),
            @DocumentacaoParametro(nome = "altura", descricao = "a altura da elipse em pixels"),
            @DocumentacaoParametro(
                    nome = "preencher",
                    descricao
                    = "define se a elipse será preenchida com a cor do ambiente gráfico. "
                    + "Se o valor for <tipo>verdadeiro</tipo>, a elipse será preenchida. Se o valor for "
                    + "<tipo>falso</tipo>, somente o contorno da elipse será desenhado"
            )
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        }
    )
    public void desenhar_elipse(final int x, final int y, final int largura, final int altura, final boolean preencher) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.desenharElipse(x, y, largura, altura, preencher);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }                        
    }

    @DocumentacaoFuncao(
        descricao
        = "Desenha um ponto na posição definida pelos parâmetros <param>x</param> e <param>y</param>.<br><br>"
        + "O ponto desenhado ocupa um único pixel na tela",
        parametros =
        {
            @DocumentacaoParametro(nome = "x", descricao = "a coordenada (distância) do ponto no eixo horizontal, em relação ao lado esquerdo da janela"),
            @DocumentacaoParametro(nome = "y", descricao = "a coordenada (distância) do ponto no eixo vertical, em relação ao topo da janela"),
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        }
    )
    public void desenhar_ponto(final int x, final int y) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.desenharPonto(x, y);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    }    

    @DocumentacaoFuncao(
        descricao
        = "Desenha uma linha de um ponto 'A' (definido pelos parâmetros <param>x1</param> e <param>y1</param>) "
        + "até um ponto 'B' (definido pelos parâmetros <param>x2</param> e <param>y2</param>)",
        parametros =
        {
            @DocumentacaoParametro(nome = "x1", descricao = "a coordenada (distância) do ponto 'A' no eixo horizontal, em relação ao lado esquerdo da janela"),
            @DocumentacaoParametro(nome = "y1", descricao = "a coordenada (distância) do ponto 'A' no eixo vertical, em relação ao topo da janela"),
            @DocumentacaoParametro(nome = "x2", descricao = "a coordenada (distância) do ponto 'B' no eixo horizontal, em relação ao lado esquerdo da janela"),
            @DocumentacaoParametro(nome = "y2", descricao = "a coordenada (distância) do ponto 'B' no eixo vertical, em relação ao topo da janela"),
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        }
    )
    public void desenhar_linha(final int x1, final int y1, final int x2, final int y2) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.desenharLinha(x1, y1, x2, y2);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }
    }

    @DocumentacaoFuncao(
        descricao
        = "Carrega uma imagem na memória para ser utilizada mais tarde. Os formatos de imagem suportados "
        + "são: JPEG, PNG, BITMAP e GIF",
        parametros =
        {
            @DocumentacaoParametro(nome = "caminho", descricao = "o caminho do arquivo de imagem no computador")
        },
        retorno = "o endereço de memória no qual a imagem foi carregada",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int carregar_imagem(String caminho) throws ErroExecucaoBiblioteca, InterruptedException
    {
        File arquivo = programa.resolverCaminho(new File(caminho));

        if (arquivo.exists())
        {
            try
            {
                int endereco = cacheImagens.adicionarImagem(Imagem.carregar(arquivo));
                //System.gc();
                
                return endereco;
            }
            catch (IOException excecao)
            {
                throw new ErroExecucaoBiblioteca(String.format("Ocorreu um erro ao carregar a imagem '%s'", caminho));
            }
        }
        else
        {
            throw new ErroExecucaoBiblioteca(String.format("A imagem '%s' não foi encontrada", caminho));
        }
    }
    
    @DocumentacaoFuncao(
        descricao
        = "Define a imagem a ser desenhada do gif como o próximo frame",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória no qual o gif foi carregado")
        },
        autores =
        {
            @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br")
        }
    )
    public void proximo_frame_gif(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        cacheImagens.obterGif(endereco).avancarQuadro();
    }

    
    @DocumentacaoFuncao(
        descricao = "Esta função permite transformar uma imagem previamente carregada no ambiente gráfico com a função carregar_imagem(). "
        + "As transformações possíveis são: espelhamento, rotação e remoção de cor.<br><br>O espelhamento permite inverter a imagem tanto na "
        + "direção horizontal quanto na direção vertical.<br><br>A rotação, permite girar e inclinar a imagem em um ângulo de 360 graus.<br><br> "
        + "A remoção de cor, permite escolher uma cor da imagem e torná-la transparente.<br><br>Esta função cria uma cópia da imagem original "
        + "antes de aplicar as transformações, portanto, a imagem original não é perdida ao realizar a transformação e a nova imagem é alocada em "
        + "outro endereço de memória",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da imagem que será transformada"),
            @DocumentacaoParametro(nome = "espelhamento_horizontal", descricao = "define se a imagem será invertida (espelhada) na direção horizontal"),
            @DocumentacaoParametro(nome = "espelhamento_vertical", descricao = "define se a imagem será invertida (espelhada) na direção vertical"),
            @DocumentacaoParametro(nome = "rotacao",
                    descricao = "define em quantos graus a imagem será rotacionada. Se o valor 0 for informado, a imagem não será rotacionada. "
                    + "É importante notar que, ao rotacionar a imagem, as suas dimensões (largura e altura) poderão se alterar"
            ),
            @DocumentacaoParametro(nome = "cor_transparente", descricao = "define a cor que será removida da imagem, ou seja, que irá se tornar transparente. Se o valor 0 for informado, nenhuma cor será removida")
        },
        retorno = "o endereço de memória da nova imagem",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    
    
    public int transformar_imagem(int endereco, boolean espelhamento_horizontal, boolean espelhamento_vertical, int rotacao, int cor_ignorada) throws ErroExecucaoBiblioteca, InterruptedException
    {
        BufferedImage imagemTransformada = (BufferedImage) cacheImagens.obterImagem(endereco).getImagem();

        imagemTransformada = aplicarChromaKey(imagemTransformada, cor_ignorada);
        imagemTransformada = espelharImagem(imagemTransformada, espelhamento_horizontal, espelhamento_vertical);
        imagemTransformada = rotacionarImagem(imagemTransformada, rotacao);

        imagemTransformada = Utils.criarImagemCompativel(imagemTransformada, true);
        
        return cacheImagens.adicionarImagem(new ImagemGenerica(imagemTransformada));
    }
    
    @DocumentacaoFuncao(
        descricao = "Esta função permite redimensionar uma imagem previamente carregada no ambiente gráfico com a função carregar_imagem(). "
            +" Caso um dos parametros de dimensão seja 0, o outro parametro será proporcional ao dado",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da imagem que será transformada"),
            @DocumentacaoParametro(nome = "largura", descricao = "a largura desejada da imagem"),
            @DocumentacaoParametro(nome = "altura", descricao = "a altura desejada da imagem"),
            @DocumentacaoParametro(nome = "manter_qualidade", descricao = "define se a qualidade da imagem deve ser mantida ao redimensionar"),
        },
        retorno = "o endereço de memória da nova imagem",
        autores =
        {
            @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br"),
            @Autor(nome = "Alisson Steffens Henrique", email = "ali.steffens@gmail.com")
        }
    )
    public int redimensionar_imagem(int endereco, int largura, int altura, boolean manter_qualidade) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Imagem imagem = cacheImagens.obterImagem(endereco);
        
        if (!(imagem instanceof ImagemGif))
        {
            BufferedImage original = (BufferedImage) imagem.getImagem();
        
            if(largura<=0 && altura<=0){
                throw new ErroExecucaoBiblioteca("Impossível transformar imagem para estas dimensões");
            }

            if(altura == 0 && largura !=0){
                double l = largura;
                double lo = original.getWidth();
                double ao = original.getHeight();
                altura = (int) (ao*(l/lo));
            }else if(altura != 0 && largura ==0){
                double a = altura;
                double lo = original.getWidth();
                double ao = original.getHeight();
                largura = (int) (lo*(a/ao));
            }
            // Usar o GraphicsConfiguration faz com a imagem seja criada e desenhada com performance
            // melhor do que criando uma nova BufferedImage na mão

            return cacheImagens.adicionarImagem(new ImagemGenerica(Utils.criarImagemCompativel(original, largura, altura, manter_qualidade)));
        }
        else
        {
            try
            {   
                ImagemGif imagemGif = (ImagemGif) imagem;
                ImagemGif nova = imagemGif.clonar();
                
                 if(largura<=0 && altura<=0){
                throw new ErroExecucaoBiblioteca("Impossível transformar imagem para estas dimensões");
                }

                if(altura == 0 && largura !=0){
                    double l = largura;
                    double lo = imagemGif.getLargura();
                    double ao = imagemGif.getAltura();
                    altura = (int) (ao*(l/lo));
                }else if(altura != 0 && largura ==0){
                    double a = altura;
                    double lo = imagemGif.getLargura();
                    double ao = imagemGif.getAltura();
                    largura = (int) (lo*(a/ao));
                }
                
                nova.setDimensoes(largura, altura, manter_qualidade);
                
                return cacheImagens.adicionarImagem(nova);
            }
            catch (IOException e)
            {
                throw new ErroExecucaoBiblioteca("Erro ao redimensionar a imagem GIF");
            }
        }        
    }
    
    
    @DocumentacaoFuncao(
        descricao = "Esta função permite obter uma cor em um pixel expecifico de uma imagem previamente carregada no ambiente gráfico com a função carregar_imagem(). ",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da imagem que será transformada"),
            @DocumentacaoParametro(nome = "x", descricao = "coluna do pixel no bitmap"),
            @DocumentacaoParametro(nome = "y", descricao = "linha do pixel no bitmap")
        },
        retorno = "cor RGB do pixel",
        autores =
        {
            @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br"),
            @Autor(nome = "Alisson Steffens Henrique", email = "ali.steffens@gmail.com")
        }
    )
    public int obter_cor_pixel(int endereco, int x, int y) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheImagens.obterImagem(endereco).getImagem().getRGB(x,y);
    }
    
    @DocumentacaoFuncao(
        descricao = "Esta função permite obter um canal de uma cor.",
        parametros =
        {
            @DocumentacaoParametro(nome = "cor", descricao = "cor que será extraido o canal"),
            @DocumentacaoParametro(nome = "canal", descricao = "canal R, G ou B")
        },
        retorno = "cor RGB do pixel",
        autores =
        {
            @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br"),
            @Autor(nome = "Alisson Steffens Henrique", email = "ali.steffens@gmail.com")
        }
    )
    public int obter_RGB(int cor, int canal) throws ErroExecucaoBiblioteca, InterruptedException
    {
        
        switch(canal){
            case CANAL_R : return (cor & 0xff0000) >> 16;
            case CANAL_G : return (cor & 0x00ff00) >> 8;
            case CANAL_B : return cor & 0x0000ff;
        }
        
        return 0;
    }

    @DocumentacaoFuncao(
        descricao = "Esta função permite transformar uma porção de uma imagem previamente carregada no ambiente gráfico com a função carregar_imagem(). "
        + "As transformações possíveis são: espelhamento, rotação e remoção de cor.<br><br>O espelhamento permite inverter uma porção da imagem tanto na "
        + "direção horizontal quanto na direção vertical.<br><br>A rotação, permite girar e inclinar uma porção da imagem em um ângulo de 360 graus.<br><br> "
        + "A remoção de cor, permite escolher uma cor da imagem e torná-la transparente.<br><br>Esta função copia uma porção da imagem original "
        + "antes de aplicar as transformações, portanto, a imagem original não é perdida ao realizar a transformação e a nova imagem é alocada em "
        + "outro endereço de memória",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da imagem que será transformada"),
            @DocumentacaoParametro(nome = "x", descricao = "a posição (distância) no eixo horizontal a partir da qual a imagem será transformada"),
            @DocumentacaoParametro(nome = "y", descricao = "a posição (distância) no eixo vertical a partir da qual a imagem será transformada"),
            @DocumentacaoParametro(nome = "largura", descricao = "a largura da porção da imagem a ser transformada"),
            @DocumentacaoParametro(nome = "altura", descricao = "a altura da porção da imagem a ser transformada"),
            @DocumentacaoParametro(nome = "espelhamento_horizontal", descricao = "define se a imagem será invertida (espelhada) na direção horizontal"),
            @DocumentacaoParametro(nome = "espelhamento_vertical", descricao = "define se a imagem será invertida (espelhada) na direção vertical"),
            @DocumentacaoParametro(nome = "rotacao",
                    descricao = "define em quantos graus a imagem será rotacionada. Se o valor 0 for informado, a imagem não será rotacionada. "
                    + "É importante notar que, ao rotacionar a imagem, as suas dimensões (largura e altura) poderão se alterar"
            ),
            @DocumentacaoParametro(nome = "cor_transparente", descricao = "define a cor que será removida da imagem, ou seja, que irá se tornar transparente. Se o valor 0 for informado, nenhuma cor será removida")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int transformar_porcao_imagem(int endereco, int x, int y, int largura, int altura, boolean espelhamento_horizontal, boolean espelhamento_vertical, int rotacao, int cor_ignorada) throws ErroExecucaoBiblioteca, InterruptedException
    {
        BufferedImage original = (BufferedImage) cacheImagens.obterImagem(endereco).getImagem();
        
        // Usar o GraphicsConfiguration faz com a imagem seja criada e desenhada com performance
        // melhor do que criando uma nova BufferedImage na mão
        
        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
        
        BufferedImage imagemTransformada = graphicsConfiguration.createCompatibleImage(largura, altura, original.getTransparency());

        Graphics2D g = (Graphics2D) imagemTransformada.getGraphics();
        
        g.drawImage(original, 0, 0, largura, altura, x, y, x + largura, y + altura, null);
        g.dispose();

        imagemTransformada = aplicarChromaKey(imagemTransformada, cor_ignorada);
        imagemTransformada = espelharImagem(imagemTransformada, espelhamento_horizontal, espelhamento_vertical);
        imagemTransformada = rotacionarImagem(imagemTransformada, rotacao);

        return cacheImagens.adicionarImagem(new ImagemGenerica(imagemTransformada));
    }

//    private BufferedImage copiarImagem(BufferedImage original)
//    {
//        BufferedImage copia = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
//        Graphics graficos = copia.getGraphics();
//
//        graficos.drawImage(original, 0, 0, null);
//        graficos.dispose();
//
//        return copia;
//    }

//    private BufferedImage copiarPorcaoImagem(BufferedImage original, int x, int y, int largura, int altura)
//    {
//        BufferedImage copia = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
//        Graphics graficos = copia.getGraphics();
//
//        graficos.drawImage(original, 0, 0, largura, altura, x, y, x + largura, y + altura, null);
//        graficos.dispose();
//
//        return copia;
//    }
    
    private BufferedImage espelharImagem(BufferedImage imagem, boolean espelhamento_horizontal, boolean espelhamento_vertical)
    {
        if (!espelhamento_horizontal && !espelhamento_vertical)
            return imagem;
        
        int escalaX = (espelhamento_horizontal) ? -1 : 1;
        int escalaY = (espelhamento_vertical) ? -1 : 1;

        int translacaoX = (espelhamento_horizontal) ? -imagem.getWidth() : 0;
        int translacaoY = (espelhamento_vertical) ? -imagem.getHeight() : 0;

        AffineTransform transformacao = AffineTransform.getScaleInstance(escalaX, escalaY);
        transformacao.translate(translacaoX, translacaoY);

        AffineTransformOp operacao = new AffineTransformOp(transformacao, AffineTransformOp.TYPE_BICUBIC);

        return operacao.filter(imagem, null);
    }

    private BufferedImage rotacionarImagem(BufferedImage imagem, int graus)
    {
        if ((graus % 360) != 0)
        {
            double angulo = Math.toRadians(graus % 360);

            AffineTransform rotacao = AffineTransform.getRotateInstance(angulo);
            rotacao.preConcatenate(calcularTranslacao(rotacao, imagem));

            AffineTransformOp operacao = new AffineTransformOp(rotacao, AffineTransformOp.TYPE_BICUBIC);

            return operacao.filter(imagem, null);
        }

        return imagem;
    }

    private AffineTransform calcularTranslacao(AffineTransform rotacao, BufferedImage imagem)
    {
        double translacaoX = 0;
        double translacaoY = 0;

        Point2D[] vertices = new Point2D[]
        {
            new Point2D.Double(0.0, 0.0), // Vértice superior esquerdo
            new Point2D.Double(imagem.getWidth(), 0.0), // Vértice superior direito
            new Point2D.Double(imagem.getWidth(), imagem.getHeight()), // Vértice inferior direito
            new Point2D.Double(0.0, imagem.getHeight())                     // Vértice inferior esquerdo
        };

        for (Point2D vertice : vertices)
        {
            vertice = rotacao.transform(vertice, null); // Calcula a nova posição de cada vértice de acordo com o ângulo de rotação

            if (vertice.getX() < translacaoX)
            {
                translacaoX = vertice.getX();
            }

            if (vertice.getY() < translacaoY)
            {
                translacaoY = vertice.getY();
            }
        }

        AffineTransform translacao = new AffineTransform();
        translacao.translate(-translacaoX, -translacaoY);

        return translacao;
    }

    private BufferedImage aplicarChromaKey(BufferedImage imagem, int cor)
    {
        if (cor != 0)
        {
            Color chroma = new Color(cor);

            int chromaRed = chroma.getRed();
            int chromaGreen = chroma.getGreen();
            int chromaBlue = chroma.getBlue();

            int[] pixel = new int[4];

            WritableRaster rgb = imagem.getRaster();
            WritableRaster alpha = imagem.getAlphaRaster();

            int r, g, b;

            for (int x = 0; x < rgb.getWidth(); x++)
            {
                for (int y = 0; y < rgb.getHeight(); y++)
                {
                    rgb.getPixel(x, y, pixel);

                    r = pixel[0];
                    g = pixel[1];
                    b = pixel[2];

                    if (r == chromaRed && g == chromaGreen && b == chromaBlue)
                    {
                        pixel[0] = 0;
                        pixel[1] = 0;
                        pixel[2] = 0;
                        pixel[3] = 0;

                        alpha.setPixel(x, y, pixel);
                    }
                }
            }
        }

        return imagem;
    }
    
    // private static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

    //     int original_width = imgSize.width;
    //     int original_height = imgSize.height;
    //     int bound_width = boundary.width;
    //     int bound_height = boundary.height;
    //     int new_width = original_width;
    //     int new_height = original_height;

    //     // first check if we need to scale width
    //     if (original_width > bound_width) {
    //         //scale width to fit
    //         new_width = bound_width;
    //         //scale height to maintain aspect ratio
    //         new_height = (new_width * original_height) / original_width;
    //     }

    //     // then check if we need to scale even with the new height
    //     if (new_height > bound_height) {
    //         //scale height to fit instead
    //         new_height = bound_height;
    //         //scale width to maintain aspect ratio
    //         new_width = (new_height * original_width) / original_height;
    //     }

    //     return new Dimension(new_width, new_height);
    // }

    @DocumentacaoFuncao(
        descricao
        = "Desenha uma imagem previamente carregada, na posição especificada pelos "
        + "parâmetros <param>x</param> e <param>y</param>",
        parametros =
        {
            @DocumentacaoParametro(nome = "x", descricao = "a posição (distância) da imagem no eixo horizontal, em relação ao lado esquerdo da janela"),
            @DocumentacaoParametro(nome = "y", descricao = "a posição (distância) da imagem no eixo vertical, em relação ao topo da janela"),
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da imagem a ser desenhada")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        }
    )
    public void desenhar_imagem(final int x, final int y, int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.desenharImagem(x, y, cacheImagens.obterImagem(endereco).getImagem());
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    }
    
    @DocumentacaoFuncao(
        descricao
        = "Salva uma imagem",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da imagem a ser desenhada"),
            @DocumentacaoParametro(nome = "caminho", descricao = "lugar onde a imagem deverá sre salva")
        },
        autores =
        {
            @Autor(nome = "Alisson Steffens Henrique", email = "ali.steffens@gmail.com")
        }
    )
    public void salvar_imagem(int endereco, String caminho) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            File arquivo = programa.resolverCaminho(new File(caminho));
            
            if (arquivo.mkdirs())
            {            
                ImageIO.write(cacheImagens.obterImagem(endereco).getImagem(), "PNG", arquivo);
            }
            else
            {
                throw new ErroExecucaoBiblioteca("Erro ao salvar a imagem: o arquivo " + arquivo.getAbsolutePath() + " não pode ser criado");
            }
        }
        catch (IOException ex) {
            throw new ErroExecucaoBiblioteca("Erro ao salvar a imagem: "+ex.getMessage());
        }            
    }
    
    @DocumentacaoFuncao(
        descricao
        = "Desenha um frame de um gif previamente carregado, na posição especificada pelos "
        + "parâmetros <param>x</param> e <param>y</param>",
        parametros =
        {
            @DocumentacaoParametro(nome = "x", descricao = "a posição (distância) da imagem no eixo horizontal, em relação ao lado esquerdo da janela"),
            @DocumentacaoParametro(nome = "y", descricao = "a posição (distância) da imagem no eixo vertical, em relação ao topo da janela"),
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da imagem a ser desenhada")
        },
        autores =
        {
            @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br")
        }
    )
    public void desenhar_quadro_atual_gif(final int x, final int y, int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.desenharImagem(x, y, cacheImagens.obterGif(endereco).getQuadroAtual());
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }
    }   

    @DocumentacaoFuncao(
        descricao
        = "Desenha uma porção de uma imagem previamente carregada, na posição especificada pelos "
        + "parâmetros <param>x</param> e <param>y</param>",
        parametros =
        {
            @DocumentacaoParametro(nome = "x", descricao = "a posição (distância) da imagem no eixo horizontal, em relação ao lado esquerdo da janela"),
            @DocumentacaoParametro(nome = "y", descricao = "a posição (distância) da imagem no eixo vertical, em relação ao topo da janela"),
            @DocumentacaoParametro(nome = "xi", descricao = "a posição (distância) no eixo horizontal a partir da qual a imagem começará a ser desenhada"),
            @DocumentacaoParametro(nome = "yi", descricao = "a posição (distância) no eixo vertical a partir da qual a imagem começará a ser desenhada"),
            @DocumentacaoParametro(nome = "largura", descricao = "a largura da porção da imagem a ser desenhada"),
            @DocumentacaoParametro(nome = "altura", descricao = "a altura da porção da imagem a ser desenhada"),
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da imagem a ser desenhada")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        }
    )
    public void desenhar_porcao_imagem(final int x, final int y, final int xi, final int yi, final int largura, final int altura, int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.desenharPorcaoImagem(x, y, xi, yi, largura, altura, cacheImagens.obterImagem(endereco).getImagem());
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    }
    
    @DocumentacaoFuncao(
        descricao = "Obtêm o intervalo entre os quadros de um gif",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do gif")
        },
        retorno = "intervalo em milissegundos do quadro atual",
        autores =
        {
            @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br")
        }
    )    
    public int obter_intervalo_gif(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheImagens.obterGif(endereco).getIntervalo();
    }
    
    @DocumentacaoFuncao(
        descricao = "Obtêm o numero de quadros de um gif",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do gif")
        },
        retorno = "o número de quadros que o gif possui",
        autores =
        {
            @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br")
        }
    )
    public int obter_numero_quadros_gif(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheImagens.obterGif(endereco).getNumeroFrames();
    }
    
    @DocumentacaoFuncao(
        descricao = "Obtêm o numero do quadro atual de um gif",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do gif")
        },
        retorno = "o número do quadro atual que o gif está",
        autores =
        {
            @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br")
        }
    )
    public int obter_numero_quadro_atual_gif(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheImagens.obterGif(endereco).getIndiceQuadroAtual();
    }
    
    @DocumentacaoFuncao(
        descricao = "Obtêm determinado quadro de um gif",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do gif"),
            @DocumentacaoParametro(nome = "quadro", descricao = "o quadro que deseja obter")
        },
        retorno = "imagem do quadro pedido por parâmetro",
        autores =
        {
            @Autor(nome = "Alisson Steffens Henrique", email = "ali.steffens@gmail.com")
        }
    )    
    public int obter_quadro_gif(int endereco, int quadro) throws ErroExecucaoBiblioteca, InterruptedException
    {
        BufferedImage imagemQuadro = cacheImagens.obterGif(endereco).getQuadro(quadro);
        
        return cacheImagens.adicionarImagem(new ImagemGenerica(imagemQuadro));
    }
    
    @DocumentacaoFuncao(
        descricao = "Define determinado quadro a um gif",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do gif"),
            @DocumentacaoParametro(nome = "quadro", descricao = "o quadro que deseja ser definido")
        },
        autores =
        {
            @Autor(nome = "Alisson Steffens Henrique", email = "ali.steffens@gmail.com")
        }
    )    
    public void definir_quadro_gif(int endereco, int quadro) throws ErroExecucaoBiblioteca, InterruptedException
    {
        cacheImagens.obterGif(endereco).irParaQuadro(quadro);
    }
    
    @DocumentacaoFuncao(
        descricao = "Libera a memória utilizada por uma imagem que tenha sido previamente carregada",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da imagem")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void liberar_imagem(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        cacheImagens.liberarImagem(endereco);
        //System.gc();
    }

    @DocumentacaoFuncao(
        descricao
        = "Desenha um texto (<tipo>cadeia</tipo>) na posição especificada pelos "
        + "parâmetros <param>x</param> e <param>y</param>",
        parametros =
        {
            @DocumentacaoParametro(nome = "x", descricao = "a posição (distância) do texto no eixo horizontal, em relação ao lado esquerdo da janela"),
            @DocumentacaoParametro(nome = "y", descricao = "a posição (distância) do ponto no eixo vertical, em relação ao topo da janela"),
            @DocumentacaoParametro(nome = "texto", descricao = "o texto (<tipo>cadeia</tipo>) a ser desenhado")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        }
    )
    public void desenhar_texto(final int x, final int y, final String texto) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.desenharTexto(texto, x, y);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    }

    @DocumentacaoFuncao(
        descricao
        = "Define a cor atual do ambiente gráfico. Esta cor será utilizada para desenhar e preencher "
        + "as primitivas gráficas (ponto, linha, retângulo, etc.) e, como cor de fundo ao limpar "
        + "o ambiente gráfico ou desenhar um texto",
        parametros =
        {
            @DocumentacaoParametro(nome = "cor", descricao = "a nova cor do ambiente gráfico")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_cor(final int cor) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.definirCor(cor);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }
    }
    
    @DocumentacaoFuncao(
        descricao
        = "Define a cor atual do ambiente gráfico como um gradiente de 2 cores. Esta cor será utilizada para desenhar e preencher "
        + "as primitivas gráficas (ponto, linha, retângulo, etc.) e, como cor de fundo ao limpar "
        + "o ambiente gráfico",
        parametros =
        {
            @DocumentacaoParametro(nome = "tipo", descricao = "o tipo de rotação de gradiente que será utilizado"),
            @DocumentacaoParametro(nome = "cor1", descricao = "a primeira cor do novo gradiente do ambiente gráfico"),
            @DocumentacaoParametro(nome = "cor2", descricao = "a segunda cor do novo gradiente do ambiente gráfico")
            
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_gradiente(final int tipo, final int cor1,final int cor2) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.definirGradiente(tipo, cor1, cor2);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }
    }

    @DocumentacaoFuncao(
        descricao = "Define a fonte que será utilizada para desenhar um texto no ambiente gráfico",
        parametros =
        {
            @DocumentacaoParametro(
                    nome = "nome",
                    descricao
                    = "o nome da fonte a ser utilizada (Ex.: Arial, Times New Roman, Tahoma). Se a fonte informada "
                    + "não existir no sistema operacional do computador, será utilizada a fonte padrão"
            )
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_fonte_texto(final String nome) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.definirFonteTexto(nome);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }
    }

    @DocumentacaoFuncao(
        descricao = "Define o tamanho da fonte que será utilizada para desenhar um texto no ambiente gráfico",
        parametros =
        {
            @DocumentacaoParametro(
                    nome = "tamanho",
                    descricao
                    = "o tamanho da fonte a ser utilizada"
            )
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_tamanho_texto(final double tamanho) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.definirTamanhoTexto(tamanho);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    }

    @DocumentacaoFuncao(
        descricao = "Define o estilo da fonte que será utilizada para desenhar um texto no ambiente gráfico",
        parametros =
        {
            @DocumentacaoParametro(nome = "italico", descricao = "define se a fonte terá o estilo itálico"),
            @DocumentacaoParametro(nome = "negrito", descricao = "define se a fonte terá o estilo negrito"),
            @DocumentacaoParametro(nome = "sublinhado", descricao = "define se a fonte terá o estilo sublinhado")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_estilo_texto(final boolean italico, final boolean negrito, final boolean sublinhado) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.definirEstiloTexto(italico, negrito, sublinhado);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }
    }

    @DocumentacaoFuncao(
        descricao = "Obtém a largura em pixels que um texto ocupa para ser desenhado na tela",
        parametros =
        {
            @DocumentacaoParametro(nome = "texto", descricao = "o texto que será mensurado")
        },
        retorno = "a largura do texto",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int largura_texto(String texto) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            return superficieDesenho.larguraTexto(texto);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    }

    @DocumentacaoFuncao(
        descricao = "Obtém a altura em pixels que um texto ocupa para ser desenhado na tela",
        parametros =
        {
            @DocumentacaoParametro(nome = "texto", descricao = "o texto que será mensurado")
        },
        retorno = "a altura do texto",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int altura_texto(String texto) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            return superficieDesenho.alturaTexto(texto);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    }

    @DocumentacaoFuncao(
        descricao = "Obtém a altura de uma imagem previamente carregada no ambiente gráfico",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço da imagem para a qual se quer obter a largura")
        },
        retorno = "a largura da imagem",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int largura_imagem(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheImagens.obterImagem(endereco).getImagem().getWidth(null);
    }

    @DocumentacaoFuncao(
        descricao = "Obtém a altura de uma imagem previamente carregada no ambiente gráfico",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço da imagem para a qual se quer obter a altura")
        },
        retorno = "a altura da imagem",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int altura_imagem(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheImagens.obterImagem(endereco).getImagem().getHeight(null);
    }

    @DocumentacaoFuncao(
        descricao = "cria uma nova cor a partir da combinação de tons de vermelho, verde e azul",
        parametros =
        {
            @DocumentacaoParametro(nome = "vermelho", descricao = "o tom de vermelho (0 a 255)"),
            @DocumentacaoParametro(nome = "verde", descricao = "o tom de verde (0 a 255)"),
            @DocumentacaoParametro(nome = "azul", descricao = "o tom de azul (0 a 255)")
        },
        retorno = "a nova cor criada pela combinação dos tons de vermelho, verde e azul",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        },
        referencia = "http://pt.wikipedia.org/wiki/RGB"
    )
    public int criar_cor(int vermelho, int verde, int azul) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            return new Color(vermelho, verde, azul).getRGB();
        }
        catch (IllegalArgumentException excecao)
        {
            throw new ErroExecucaoBiblioteca("Erro ao criar a cor, os valor dos tons deve estar entre 0 e 255");
        }
    }

    @DocumentacaoFuncao(
        descricao = "Carrega uma fonte no ambiente gráfico a partir de um arquivo de fonte presente no sistema de arquivos",
        parametros =
        {
            @DocumentacaoParametro(nome = "caminho_fonte", descricao = "o caminho do arquivo de fonte no sistema de arquivos")
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void carregar_fonte(String caminho_fonte) throws ErroExecucaoBiblioteca, InterruptedException
    {
        File arquivo = programa.resolverCaminho(new File(caminho_fonte));

        if (arquivo.exists())
        {
            try
            {
                Font fonte = Font.createFont(Font.TRUETYPE_FONT, arquivo);
                boolean fonteRegistrada = GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonte);
                if (fonteRegistrada)
                {
                    superficieDesenho.registrarFonteCarregada(fonte);
                }
                else
                {
                    LOGGER.log(Level.SEVERE, "A fonte {0} não foi carregada!", caminho_fonte);
                }
            }
            catch (IOException | FontFormatException excecao)
            {
                throw new ErroExecucaoBiblioteca(String.format("Não foi possível carregar a fonte '%s'", arquivo.getAbsolutePath()));
            }
        }
        else
        {
            throw new ErroExecucaoBiblioteca(String.format("A fonte '%s' não foi encontrada", caminho_fonte));
        }
    }

    @DocumentacaoFuncao(
        descricao = "Altera o ícone que é exibido na janela do ambiente gráfico. Este ícone aparece ao lado do título "
        + "da janela e na barra de tarefas do sistema operacional",
        parametros =
        {
            @DocumentacaoParametro(nome = "endereco", descricao = "o endereço da imagem que será usada como ícone"),
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_icone_janela(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().definirIcone(cacheImagens.obterImagem(endereco).getImagem());
    }

    @DocumentacaoFuncao(
        descricao = "Esta função define o nível de opacidade dos desenhos no ambiente gráfico. Quanto menor for a opacidade, "
        + "mais transparente será o desenho e quanto maior for a opacidade mais opaco será o desenho. Com esta função, é "
        + "possível desenhar imagens, textos e primitivas gráficas semi-transparentes, o que permite \"enxergar\" através dos "
        + "desenhos.<br><br>É importante notar que, após ser chamada, esta função afeta todos os desenhos realizados. Isto significa "
        + "que se foram desenhados um retângulo e uma elipse após a chamada desta função, ambos terão seu nível de "
        + "opacidade alterados.<br><br>Caso fosse desejável modificar apenas a opacidade do retângulo, então seria necessário "
        + "chamar novamente esta função definido a opacidade para o valor máximo antes de desenhar a elipse",
        parametros =
        {
            @DocumentacaoParametro(nome = "opacidade",
                    descricao = "o nível de opacidade dos desenhos. O valor deve estar entre 0 e 255, sendo que, 0 indica um "
                    + "desenho totalmente transparente e 255 indica um desenho totalmente opaco"
            ),
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_opacidade(int opacidade) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.definirOpacidade(opacidade);
        }
        catch (IllegalArgumentException excecao)
        {
            throw new ErroExecucaoBiblioteca("O valor da opacidade deve esta entre 0 e 255");
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }        
    }

    @DocumentacaoFuncao(
        descricao = "Esta função define o grau de rotação dos desenhos no ambiente gráfico. Com esta função, é possível rotacionar "
        + "imagens, textos e todas as primitivas gráficas, incluindo linhas, retângulos e elipses.<br><br>A rotação é realizada sempre "
        + "a partir do centro do desenho.<br><br>É importante notar que, após ser chamada, esta função afeta todos os desenhos realizados. "
        + "Isto significa que se foram desenhados um retângulo e uma elipse após a chamada desta função, ambos serão rotacionados no mesmo "
        + "grau de inclinação.<br><br>Caso fosse desejável rotacionar apenas o retângulo, então seria necessário chamar novamente esta "
        + "função definido a rotação para 0 antes de desenhar a elipse",
        parametros =
        {
            @DocumentacaoParametro(nome = "rotacao",
                    descricao = "o grau de rotação dos desenhos. Pode ser qualquer valor real, incluindo 0, positivos e negativos. "
                    + "Os valores múltiplos de 360.0 e o valor 0.0 indicam que não haverá rotação. Valores positivos, indicam uma "
                    + "rotação no sentido horário enquanto que valores negativos indicam uma rotação no sentido anti-horário"
            ),
        },
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void definir_rotacao(int rotacao) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            superficieDesenho.definirRotacao(rotacao);
        }
        catch(IllegalStateException e)
        {
            throw new ErroExcessoOperacoes();
        }            
    }

    private JanelaGrafica janela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        if (inicializado)
        {
            return janela;
        }
        else
        {
            throw new ErroExecucaoBiblioteca("O modo gráfico não foi inicializado");
        }
    }

    @DocumentacaoFuncao(
        descricao = "obtém a largura atual da janela do ambiente gráfico",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int largura_janela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return janela().getLargura();
    }

    @DocumentacaoFuncao(
        descricao = "obtém a altura atual da janela do ambiente gráfico",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int altura_janela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return janela().getAltura();
    }

    @DocumentacaoFuncao(
        descricao = "obtém a largura da tela do computador",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int largura_tela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        return gd.getDisplayMode().getWidth();
    }

    @DocumentacaoFuncao(
        descricao = "obtém a altura da tela do computador",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int altura_tela() throws ErroExecucaoBiblioteca, InterruptedException
    {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        return gd.getDisplayMode().getHeight();
    }

    @DocumentacaoFuncao(
        descricao = "Faz com que a janela gráfica seja redimensionada para ocupar o tamanho total da tela",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void entrar_modo_tela_cheia() throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().entrarModoTelaCheia();
    }

    @DocumentacaoFuncao(
        descricao = "Faz com que a janela gráfica seja redimensionada para o tamanho que possuía antes de entrar no modo de tela cheia",
        autores =
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public void sair_modo_tela_cheia() throws ErroExecucaoBiblioteca, InterruptedException
    {
        janela().sairModoTelaCheia();
    }
    
    private class ErroExcessoOperacoes extends ErroExecucaoBiblioteca
    {
        public ErroExcessoOperacoes()
        {
            super("A função Graficos.renderizar() não foi chamada nenhuma vez em seu código ou o número de operações de desenho que você chamou antes do Graficos.renderizar() foi muito grande");
        }
        
    }
}

