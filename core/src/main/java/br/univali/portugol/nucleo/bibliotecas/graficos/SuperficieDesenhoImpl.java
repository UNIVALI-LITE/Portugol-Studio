package br.univali.portugol.nucleo.bibliotecas.graficos;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.PoolOperacoesGraficas;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.OperacaoGrafica;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.font.TextAttribute;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luiz Fernando
 */
final class SuperficieDesenhoImpl extends Canvas implements SuperficieDesenho 
{
    private final PoolOperacoesGraficas POOL_OPERACOES_GRAFICAS = new PoolOperacoesGraficas();

    private final OperacaoGrafica[] operacoes = new OperacaoGrafica[PoolOperacoesGraficas.QUANTIDADE_MAXIMA_OPERACOES];
    private int indiceOperacao = 0;

    private Font fonteTexto = null;
    private FontMetrics dimensoesFonte = null;
    
    //             Map<nomeFonte, Map<estilo, Map<sublinhado, Map<tamanho, Font>>> 
    private  final Map<String, Map<Integer, Map<Boolean, Map<Float, Font>>>> cacheFontes = new HashMap<>(); // cache de fontes

    boolean usandoSublinhado = false;
    
    private Map<Integer, Color> cacheCores = new HashMap<>();

    private double rotacao = 0.0;
    private int opacidade = 255;
    private Color cor = new Color(0, 0, 0, opacidade);
    private InformacaoGradiente gradientInfo;

    private BufferStrategy buffer;
    private Rectangle areaGrafica;

   
    
    public SuperficieDesenhoImpl()
    {
        setIgnoreRepaint(true);
        setBackground(cor);
        setFocusable(false);
    }

    private void criarBuffer()
    {
        createBufferStrategy(2);
        buffer = getBufferStrategy();

        if (fonteTexto == null)
        {
            Graphics g = buffer.getDrawGraphics();

            fonteTexto = g.getFont();
            dimensoesFonte = getFontMetrics(fonteTexto);

            g.dispose();
        }
    }

    @Override
    public void renderizar() throws ErroExecucaoBiblioteca
    {
        if(buffer == null)
        {
         throw new ErroExecucaoBiblioteca("O modo gráfico não foi inicializado");
        }
        do
        {
            do
            {
                Graphics2D graficos = (Graphics2D) buffer.getDrawGraphics();
                graficos.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graficos.setColor(Color.BLACK);
                graficos.setFont(fonteTexto);
                graficos.fillRect(0, 0, getWidth(), getHeight());

                for (int i = 0; i < indiceOperacao; ++i)
                {
                    operacoes[i].executar(graficos);
                }

                graficos.dispose();
            }
            while (buffer.contentsRestored());

            buffer.show();
        }
        while (buffer.contentsLost());

        for (int i = 0; i < indiceOperacao; i++)
        {
            operacoes[i].liberar();
            operacoes[i] = null;
        }

        indiceOperacao = 0;
    }

    private Color obterCorTransparente(int cor, int opacidade)
    {
        int chave = (cor << 8) | opacidade; // geram um inteiro com RGBA
        if (!cacheCores.containsKey(chave))
        {
            Color aux = new Color(cor);
            cacheCores.put(chave, new Color(aux.getRed(), aux.getGreen(), aux.getBlue(), opacidade));
        }
        
        return cacheCores.get(chave);
    }

    @Override
    public void redimensionar(int largura, int altura)
    {
        setBounds(0, 0, largura, altura);
        criarBuffer();
        areaGrafica = getBounds();
    }

    @Override
    public void limpar() throws ErroExecucaoBiblioteca
    {
        verificaExcessoOperacoes();
        if(areaGrafica == null)
        {
            throw new ErroExecucaoBiblioteca("O modo gráfico não foi inicializado");
        }
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoLimpar(areaGrafica.width, areaGrafica.height);
        indiceOperacao++;
    }

    @Override
    public void definirCor(int cor)
    {
        verificaExcessoOperacoes();
        this.cor = obterCorTransparente(cor, opacidade);
        this.operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDefinirCor(this, this.cor);
        
        
        indiceOperacao++;
    }

    @Override
    public void definirGradiente(int tipo, int cor, int cor2) {
        
        this.operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDefinirGradiente(this, tipo, cor, cor2);
        
        indiceOperacao++;
    }

    @Override
    public void registrarGradiente(int tipo, int cor1, int cor2) {
        
        gradientInfo = new InformacaoGradiente();
        gradientInfo.cor1 = obterCorTransparente(cor1, opacidade); 
        gradientInfo.cor2 = obterCorTransparente(cor2, opacidade);
        gradientInfo.tipo = tipo;
    }

    @Override
    public void definirOpacidade(int opacidade)
    {
        verificaExcessoOperacoes();
        
        this.opacidade = opacidade;
        this.cor = obterCorTransparente(this.cor.getRGB(), opacidade);
        this.operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDefinirCor(this, this.cor);

        indiceOperacao++;
    }

    @Override
    public void desenharRetangulo(int x, int y, int largura, int altura, boolean arredondarCantos, boolean preencher)
    {
        verificaExcessoOperacoes();
        
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDesenhoRetangulo(this, x, y, largura, altura, arredondarCantos, preencher, rotacao, opacidade);
        indiceOperacao++;
    }

    @Override
    public void desenharElipse(int x, int y, int largura, int altura, boolean preencher)
    {
        verificaExcessoOperacoes();
        
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDesenhoElipse(this, x, y, largura, altura, preencher, rotacao, opacidade);
        indiceOperacao++;
    }

    @Override
    public void desenharLinha(int x1, int y1, int x2, int y2)
    {
        verificaExcessoOperacoes();
        
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDesenhoLinha(this, x1, y1, x2, y2, rotacao, opacidade);
        indiceOperacao++;
    }

    @Override
    public void desenharTexto(String texto, int x, int y)
    {
        verificaExcessoOperacoes();
        
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDesenhoTexto(x, y, texto, dimensoesFonte, rotacao, opacidade);
        indiceOperacao++;
    }

    @Override
    public void definirFonteTexto(String nome) throws ErroExecucaoBiblioteca
    {
        verificaExcessoOperacoes();
        if(fonteTexto == null)
        {
            throw new ErroExecucaoBiblioteca("O modo gráfico não foi inicializado");
        }
        fonteTexto = getFonte(nome, fonteTexto.getStyle(), usandoSublinhado, fonteTexto.getSize2D());

        dimensoesFonte = getFontMetrics(fonteTexto);
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDefinirFonte(fonteTexto);
        indiceOperacao++;
    }

    @Override
    public void definirEstiloTexto(boolean italico, boolean negrito, boolean sublinhado) throws ErroExecucaoBiblioteca{
        verificaExcessoOperacoes();
    
        if(fonteTexto == null)
        {
            throw new ErroExecucaoBiblioteca("O modo gráfico não foi inicializado");
        }
        this.usandoSublinhado = sublinhado;
        String nomeFonte = fonteTexto.getName();
        int estilo = getEstilo(negrito, italico);
        float tamanho = fonteTexto.getSize2D();

        fonteTexto = getFonte(nomeFonte, estilo, sublinhado, tamanho);

        dimensoesFonte = getFontMetrics(fonteTexto);
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDefinirFonte(fonteTexto);

        indiceOperacao++;
    }

    @Override
    public void registrarFonteCarregada(Font fonte)
    {
        cacheFontes.putIfAbsent(fonte.getName(), new HashMap<Integer, Map<Boolean, Map<Float, Font>>>());
        Map<Integer, Map<Boolean, Map<Float, Font>>> fonteName = cacheFontes.get(fonte.getName());
        fonteName.putIfAbsent(fonte.getStyle(), new HashMap<Boolean, Map<Float, Font>>());
        Map<Boolean, Map<Float, Font>> style = fonteName.get(fonte.getStyle());
        style.putIfAbsent(Boolean.FALSE, new HashMap<Float, Font>());
        style.get(Boolean.FALSE).putIfAbsent(fonte.getSize2D(), fonte);
    }

    private Font getFonte(String nomeFonte, int estilo, boolean sublinhado, float tamanho)
    {
        if (!cacheFontes.containsKey(nomeFonte))
        {
            cacheFontes.put(nomeFonte, new HashMap<Integer, Map<Boolean, Map<Float, Font>>>());
        }
        Map<Integer, Map<Boolean, Map<Float, Font>>> fontesNome = cacheFontes.get(nomeFonte);

        if (!fontesNome.containsKey(estilo))
        {
            fontesNome.put(estilo, new HashMap<Boolean, Map<Float, Font>>());
        }
        Map<Boolean, Map<Float, Font>> fontesEstilo = fontesNome.get(estilo);

        if (!fontesEstilo.containsKey(sublinhado))
        {
            fontesEstilo.put(sublinhado, new HashMap<Float, Font>());
        }

        Map<Float, Font> fontesTamanho = fontesEstilo.get(sublinhado);
        if (!fontesTamanho.containsKey(tamanho))
        {
            //System.out.println("Criando fonte");
            Font novaFonte = new Font(nomeFonte, estilo, (int)tamanho); 
            if (sublinhado)
            {
                Map<TextAttribute, Integer> atributos = (Map<TextAttribute, Integer>) fonteTexto.getAttributes();
                atributos.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                novaFonte = novaFonte.deriveFont(atributos);
            }
            cacheFontes.get(nomeFonte).get(estilo).get(sublinhado).put(tamanho, novaFonte);
        }

        return cacheFontes.get(nomeFonte).get(estilo).get(sublinhado).get(tamanho);
    }

    private int getEstilo(boolean negrito, boolean italico)
    {
        int estilo = Font.PLAIN;
        if (italico)
        {
            estilo = estilo | Font.ITALIC;
        }

        if (negrito)
        {
            estilo = estilo | Font.BOLD;
        }
        return estilo;
    }

    @Override
    public void definirTamanhoTexto(double tamanho)
    {
        verificaExcessoOperacoes();
        
        fonteTexto = fonteTexto.deriveFont((float) tamanho);

        dimensoesFonte = getFontMetrics(fonteTexto);
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDefinirFonte(fonteTexto);
        indiceOperacao++;
    }

    @Override
    public void desenharImagem(int x, int y, BufferedImage imagem)
    {
        // evita adicionar operações de pintura que estão fora das dimensões do canvas
        if (y + imagem.getHeight() > 0 && y < getHeight() && x < getWidth() && x + imagem.getWidth() > 0)
        {
            verificaExcessoOperacoes();
            
            operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDesenhoImagem(x, y, imagem, opacidade, rotacao);
            indiceOperacao++;
        }
    }

    @Override
    public void desenharPorcaoImagem(int x, int y, int xi, int yi, int largura, int altura, BufferedImage imagem)
    {
        verificaExcessoOperacoes();
        
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDesenhoPorcaoImagem(x, y, xi, yi, largura, altura, imagem, opacidade, rotacao);
        indiceOperacao++;
    }

    @Override
    public int alturaTexto(String texto) throws ErroExecucaoBiblioteca
    {
        if(dimensoesFonte == null)
        {
            throw new ErroExecucaoBiblioteca("O modo gráfico não foi inicializado");
        }
        return dimensoesFonte.getAscent() + dimensoesFonte.getLeading();
    }

    @Override
    public int larguraTexto(String texto) throws ErroExecucaoBiblioteca
    {
        if(dimensoesFonte == null)
        {
            throw new ErroExecucaoBiblioteca("O modo gráfico não foi inicializado");
        }
        return dimensoesFonte.stringWidth(texto);
    }

    public String nomeFonte()
    {
        return fonteTexto.getName();
    }

    @Override
    public void definirRotacao(int graus)
    {
        rotacao = Math.toRadians(graus % 360);
    }

    @Override
    public void desenharPonto(int x, int y)
    {
        verificaExcessoOperacoes();
        
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDesenhoPonto(x, y, opacidade);
        indiceOperacao++;
    }

    @Override
    public BufferedImage renderizarImagem(int largura, int altura)
    {
        BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graficos = (Graphics2D) imagem.getGraphics();

        graficos.setColor(cor);
        graficos.setFont(fonteTexto);
        graficos.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < indiceOperacao; i++)
        {
            operacoes[i].executar(graficos);
            operacoes[i] = null;
        }

        indiceOperacao = 0;

        return imagem;
    }

    @Override
    public void desenharPoligono(int[][] pontos, boolean preencher)
    {
        verificaExcessoOperacoes();
        
        operacoes[indiceOperacao] = POOL_OPERACOES_GRAFICAS.obterOperacaoDesenhoPoligono(this, pontos, preencher, rotacao, opacidade);
        indiceOperacao++;
    }

    @Override
    public void instalarMouse(MouseAdapter observadorMouse)
    {
        addMouseListener(observadorMouse);
        addMouseMotionListener(observadorMouse);
    }
    
    private void verificaExcessoOperacoes()
    {
        if (indiceOperacao >= PoolOperacoesGraficas.QUANTIDADE_MAXIMA_OPERACOES)
        {
            throw new IllegalStateException("A quantidade máxima de operações foi excedida!");
        }
    }

    @Override
    public InformacaoGradiente getInformacaoGradiente() {
        return gradientInfo;
    }

    @Override
    public void removerGradiente() {
        gradientInfo = null;
    }
    
    
}
