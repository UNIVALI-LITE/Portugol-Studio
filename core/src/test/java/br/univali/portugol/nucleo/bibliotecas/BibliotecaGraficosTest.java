package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.ProgramaVazio;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.PoolOperacoesGraficas;
import java.util.Collections;
import org.junit.Test;

/**
 * @author Elieser
 */
public class BibliotecaGraficosTest
{
   private static final Programa programa = ProgramaVazio.novaInstancia();

   @Test(expected = ErroExecucaoBiblioteca.class)
   public void testMuitasOperacoesSemInvocarRenderizar() throws ErroExecucaoBiblioteca, InterruptedException
   {
       final Graficos g = new Graficos();
       g.inicializar(programa, Collections.EMPTY_LIST);
       g.iniciar_modo_grafico(false);

       // gera mais operações do que o permitido
       for (int i = 0; i < PoolOperacoesGraficas.QUANTIDADE_MAXIMA_OPERACOES + 1; i++)
       {
           g.definir_cor(0);
           g.definir_fonte_texto("Arial");
           g.definir_tamanho_texto(12);
           g.definir_estilo_texto(true, true, true);
           g.definir_rotacao(0);
       }
   }
   
   @Test
   public void janela() throws Exception
   {
       final Graficos g = new Graficos();
       final Util u = new Util();

       final long tempoTeste = 10000; // 10 segundos de teste

       final int LARGURA_TELA = 800;
       final int ALTURA_TELA = 600;

       g.inicializar(programa, Collections.EMPTY_LIST);

       g.iniciar_modo_grafico(true);
       g.definir_dimensoes_janela(LARGURA_TELA, ALTURA_TELA);
       g.definir_titulo_janela("Título da janela");

       long tempoExecucao = 0;
       int x = 0;
       int y = 0;
       int tamanho = 100;

       Runtime runtime = Runtime.getRuntime();

       String imagem = "../ide/src/main/assets/exemplos/jogos/corrida/imagens/veiculos.png";
       int idImagem = g.carregar_imagem(imagem);

       int larguraFrame = 43; // cada imagem dos carros tem 43 pixels de largura

       String caminhoFonte = "../ide/src/main/assets/exemplos/jogos/fontes/Starjhol.ttf";
       g.carregar_fonte(caminhoFonte);

       while (tempoExecucao < tempoTeste)
       {
           long inicio = System.currentTimeMillis();
           g.definir_cor(Graficos.COR_PRETO);
           g.limpar();

           g.definir_cor(Graficos.COR_AZUL);
           g.desenhar_retangulo(LARGURA_TELA - tamanho - x, ALTURA_TELA - tamanho - y, tamanho, tamanho, false, true);

           g.definir_cor(Graficos.COR_AMARELO);
           g.desenhar_elipse(x, ALTURA_TELA - tamanho / 2 - y, tamanho / 2, tamanho / 2, true);

           //desenha uma cruz/mira no centro da janela usando pontos e uma linha
           g.definir_cor(Graficos.COR_VERMELHO);
           final int tamanhoMira = 21;
           for (int i = 0; i < tamanhoMira; i++)
           {
               g.desenhar_ponto(LARGURA_TELA / 2 - tamanhoMira / 2 + i, ALTURA_TELA / 2); // linha horizontal
           }
           g.desenhar_linha(LARGURA_TELA / 2, ALTURA_TELA / 2 - tamanhoMira / 2, LARGURA_TELA / 2, ALTURA_TELA / 2 + tamanhoMira / 2);

           g.definir_cor(Graficos.COR_BRANCO);
           g.definir_tamanho_texto(36);

           String nomeFonte = "Verdana";
           g.definir_fonte_texto(nomeFonte);
           g.definir_estilo_texto(false, true, true);
           int larguraTexto = g.largura_texto(nomeFonte);
           int textoY = ALTURA_TELA / 2 + tamanhoMira * 2;
           g.desenhar_texto(LARGURA_TELA / 2 - larguraTexto / 2, textoY, nomeFonte);

           int alturaTexto = g.altura_texto(nomeFonte);
           nomeFonte = "Times new Roman";
           g.definir_fonte_texto(nomeFonte);
           g.definir_tamanho_texto(60);
           g.definir_cor(0xFF0000);
           g.definir_estilo_texto(false, true, false);
           larguraTexto = g.largura_texto(nomeFonte);
           textoY += alturaTexto;
           g.desenhar_texto(LARGURA_TELA / 2 - larguraTexto / 2, textoY, nomeFonte);

           nomeFonte = "Star Jedi Hollow";
           g.definir_cor(Graficos.COR_BRANCO);
           g.definir_fonte_texto(nomeFonte);
           larguraTexto = g.largura_texto(nomeFonte);
           textoY += alturaTexto;
           g.desenhar_texto(LARGURA_TELA / 2 - larguraTexto / 2, textoY, nomeFonte);

           long memoriaUsada = runtime.totalMemory() - runtime.freeMemory();
           g.definir_cor(Graficos.COR_BRANCO);
           g.definir_tamanho_texto(14);
           g.definir_fonte_texto("Verdana");
           g.desenhar_texto(5, 50, "RAM: " + String.valueOf(memoriaUsada / 1024 / 1024) + " MB");

           final int carros = 5;
           for (int i = 0; i < carros; i++)
           {
               int pos = (int) (x * Math.pow(i, 1.1));
               g.desenhar_porcao_imagem(pos, i * larguraFrame + 60, larguraFrame * i, 0, larguraFrame, 96, idImagem);

               pos = LARGURA_TELA - pos;
               g.desenhar_porcao_imagem(pos, ALTURA_TELA - 96 - i * larguraFrame, larguraFrame * i, 0, larguraFrame, 96, idImagem);
           }

           g.definir_opacidade(64);
           g.definir_cor(Graficos.COR_VERDE);
           g.definir_rotacao(45);
           g.desenhar_retangulo(x, y, 100, 100, true, true);
           g.definir_opacidade(255);
           g.definir_rotacao(0);

           x += 2;
           y += 2;

           if (x > LARGURA_TELA)
           {
               x = 0;
           }

           if (y > ALTURA_TELA)
           {
               y = 0;
           }

           g.renderizar();
           u.aguarde(16);
           tempoExecucao += System.currentTimeMillis() - inicio;

       }

       g.finalizar();
   }
}
