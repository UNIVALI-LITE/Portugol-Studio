package br.univali.ps.ui.paineis;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Elieser
 */
public class ImagePanel extends JComponent {
    
    private Image imagem;
    private Rectangle origem;
    private Rectangle destino;

    public ImagePanel() {
        inicializa();
    }

    private void inicializa() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                atualizaDimensoes();
                repaint();
            }
        });
    }

    public void setImagem(Image imagem) {
        this.imagem = imagem;
        atualizaDimensoes();
        repaint();
    }

    private void atualizaDimensoes() {
        int larguraComponente = getWidth();
        int alturaComponente = getHeight();
        
        if (imagem == null || larguraComponente <= 0 || alturaComponente <= 0) {
            return;
        }
        int larguraImagem = imagem.getWidth(null);
        int alturaImagem = imagem.getHeight(null);
        
        double fatorEscalaHorizontal = (double)larguraComponente/larguraImagem;
        double fatorEscalaVertical = (double)alturaComponente/alturaImagem; 
        double fatorEscala = Math.min(fatorEscalaVertical, fatorEscalaHorizontal);
        
        origem = new Rectangle(0, 0, larguraImagem, alturaImagem);
        
        int novaLargura = (int)(larguraImagem * fatorEscala);
        int novaAltura = (int)(alturaImagem * fatorEscala);
        int novaMargemEsquerda = (larguraComponente-novaLargura)/2;
        int novaMargemSuperior = (alturaComponente - novaAltura)/2;
        destino = new Rectangle(novaMargemEsquerda, novaMargemSuperior, novaMargemEsquerda + novaLargura , novaMargemSuperior + novaAltura);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (imagem != null && destino != null && origem != null) {
        
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            
            g2d.drawImage(imagem, 
                    destino.x, destino.y, destino.width, destino.height, 
                    origem.x, origem.y, origem.width, origem.height, null);
        }
    }
    
    private static int indiceImagemAtual = 0;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(300, 500));
        
        final ImagePanel imagePanel = new ImagePanel();
        
        System.out.println(new File(".").getAbsolutePath());
        
        String imagens[] = {
            "../Portugol-Studio-Recursos/exemplos/jogos/corrida.png",
            "../Portugol-Studio-Recursos/exemplos/jogos/pong_game.png",
            "../Portugol-Studio-Recursos/exemplos/jogos/serpente.png"
        };
        
        JButton botao = new JButton("Próxima imagem");
        botao.addActionListener((ev) -> {
            try 
            {
                indiceImagemAtual = (indiceImagemAtual + 1) % imagens.length;
                BufferedImage imagem = ImageIO.read(new File(imagens[indiceImagemAtual]));
                imagePanel.setImagem(imagem);
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(imagePanel, BorderLayout.CENTER);
        contentPane.add(botao, BorderLayout.SOUTH);
        
        botao.doClick();
        
        frame.setVisible(true);
    }
    
}
