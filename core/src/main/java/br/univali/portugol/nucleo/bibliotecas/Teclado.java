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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.List;

@PropriedadesBiblioteca(tipo = TipoBiblioteca.RESERVADA)
@DocumentacaoBiblioteca
(
    descricao = "Esta biblioteca contém um conjunto de funções para manipular a entrada de dados através do teclado do computador\n"
            + "<b>IMPORTANTE:</b> Esta biblioteca não funciona no console de entrada e saída de dados do Portugol Studio, ela só funciona com a biblioteca Graficos, se o modo gráfico estiver iniciado.", 
    versao = "1.1"
)
public final class Teclado extends Biblioteca
{
    private boolean[] buffer = new boolean[525];
    private final KeyListener observadorTeclado;
    private int ultimaTecla = -1;
    private boolean temTeclaPressionada = false;
    private boolean aguardandoTecla = false;
    private final WindowListener observadorJanela;

    @DocumentacaoConstante(descricao = "Código numérico da tecla ENTER no teclado")
    public static final int TECLA_ENTER = KeyEvent.VK_ENTER;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla BACKSPACE no teclado")
    public static final int TECLA_BACKSPACE = KeyEvent.VK_BACK_SPACE;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla TAB no teclado")
    public static final int TECLA_TAB = KeyEvent.VK_TAB;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla CANCELAR no teclado")
    public static final int TECLA_CANCELAR = KeyEvent.VK_CANCEL;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla LIMPAR no teclado")
    public static final int TECLA_LIMPAR = KeyEvent.VK_CLEAR;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla SHIFT no teclado")
    public static final int TECLA_SHIFT = KeyEvent.VK_SHIFT;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla CONTROL no teclado")
    public static final int TECLA_CONTROL = KeyEvent.VK_CONTROL;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ALT no teclado")
    public static final int TECLA_ALT = KeyEvent.VK_ALT;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla PAUSE/BREAK no teclado")
    public static final int TECLA_PAUSE = KeyEvent.VK_PAUSE;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla CAPSLOCK  no teclado")
    public static final int TECLA_CAPS_LOCK	= KeyEvent.VK_CAPS_LOCK;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ESC no teclado")
    public static final int TECLA_ESC = KeyEvent.VK_ESCAPE;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ESPAÇO no teclado")
    public static final int TECLA_ESPACO = KeyEvent.VK_SPACE;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla PAGE UP  no teclado")
    public static final int TECLA_PAGE_UP = KeyEvent.VK_PAGE_UP;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla PAGE DOWN no teclado")
    public static final int TECLA_PAGE_DOWN	= KeyEvent.VK_PAGE_DOWN;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla END no teclado")
    public static final int TECLA_END = KeyEvent.VK_END;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla HOME no teclado")
    public static final int TECLA_HOME = KeyEvent.VK_HOME;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla SETA ESQUERDA no teclado")
    public static final int TECLA_SETA_ESQUERDA = KeyEvent.VK_LEFT;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla SETA ACIMA no teclado")
    public static final int TECLA_SETA_ACIMA = KeyEvent.VK_UP;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla SETA DIREITA no teclado")
    public static final int TECLA_SETA_DIREITA = KeyEvent.VK_RIGHT;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla SETA ABAIXO no teclado")
    public static final int TECLA_SETA_ABAIXO = KeyEvent.VK_DOWN;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ',' no teclado")
    public static final int TECLA_VIRGULA = KeyEvent.VK_COMMA;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '-' no teclado")
    public static final int TECLA_MENOS = KeyEvent.VK_MINUS;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '.' no teclado")
    public static final int TECLA_PONTO_FINAL = KeyEvent.VK_PERIOD;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '/' no teclado")
    public static final int TECLA_BARRA = KeyEvent.VK_SLASH;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '0' no teclado")
    public static final int TECLA_0 = KeyEvent.VK_0;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '1' no teclado")
    public static final int TECLA_1 = KeyEvent.VK_1;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '2' no teclado")
    public static final int TECLA_2  = KeyEvent.VK_2;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '3' no teclado")
    public static final int TECLA_3 = KeyEvent.VK_3;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '4' no teclado")
    public static final int TECLA_4 = KeyEvent.VK_4;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '5' no teclado")
    public static final int TECLA_5 = KeyEvent.VK_5;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '6' no teclado")
    public static final int TECLA_6 = KeyEvent.VK_6;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '7' no teclado")
    public static final int TECLA_7 = KeyEvent.VK_7;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '8' no teclado")
    public static final int TECLA_8 = KeyEvent.VK_8;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '9' no teclado")
    public static final int TECLA_9 = KeyEvent.VK_9;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ';' no teclado")
    public static final int TECLA_PONTO_VIRGULA = KeyEvent.VK_SEMICOLON;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '=' no teclado")
    public static final int TECLA_IGUAL = KeyEvent.VK_EQUALS;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'A' no teclado")
    public static final int TECLA_A = KeyEvent.VK_A;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'B' no teclado")
    public static final int TECLA_B = KeyEvent.VK_B;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'C' no teclado")
    public static final int TECLA_C = KeyEvent.VK_C;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'D' no teclado")
    public static final int TECLA_D = KeyEvent.VK_D;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'E' no teclado")
    public static final int TECLA_E = KeyEvent.VK_E;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F' no teclado")
    public static final int TECLA_F = KeyEvent.VK_F;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'G' no teclado")
    public static final int TECLA_G = KeyEvent.VK_G;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'H' no teclado")
    public static final int TECLA_H = KeyEvent.VK_H;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'I' no teclado")
    public static final int TECLA_I = KeyEvent.VK_I;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'J' no teclado")
    public static final int TECLA_J = KeyEvent.VK_J;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'K' no teclado")
    public static final int TECLA_K = KeyEvent.VK_K;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'L' no teclado")
    public static final int TECLA_L = KeyEvent.VK_L;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'M' no teclado")
    public static final int TECLA_M = KeyEvent.VK_M;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'N' no teclado")
    public static final int TECLA_N = KeyEvent.VK_N;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'O' no teclado")
    public static final int TECLA_O = KeyEvent.VK_O;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'P' no teclado")
    public static final int TECLA_P = KeyEvent.VK_P;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'Q' no teclado")
    public static final int TECLA_Q = KeyEvent.VK_Q;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'R' no teclado")
    public static final int TECLA_R = KeyEvent.VK_R;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'S' no teclado")
    public static final int TECLA_S = KeyEvent.VK_S;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'T' no teclado")
    public static final int TECLA_T = KeyEvent.VK_T;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'U' no teclado")
    public static final int TECLA_U = KeyEvent.VK_U;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'V' no teclado")
    public static final int TECLA_V = KeyEvent.VK_V;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'W' no teclado")
    public static final int TECLA_W = KeyEvent.VK_W;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'X' no teclado")
    public static final int TECLA_X = KeyEvent.VK_X;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'Y' no teclado")
    public static final int TECLA_Y = KeyEvent.VK_Y;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'Z' no teclado")
    public static final int TECLA_Z = KeyEvent.VK_Z;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '[' no teclado")
    public static final int TECLA_ABRE_COLCHETES = KeyEvent.VK_OPEN_BRACKET;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '\\' no teclado")
    public static final int TECLA_BARRA_INVERTIDA = KeyEvent.VK_BACK_SLASH;

    @DocumentacaoConstante(descricao = "Código numérico da tecla ']' no teclado")
    public static final int TECLA_FECHA_COLCHETES = KeyEvent.VK_CLOSE_BRACKET;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '0' no teclado numérico")
    public static final int TECLA_0_NUM = KeyEvent.VK_NUMPAD0;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '1' no teclado numérico")
    public static final int TECLA_1_NUM = KeyEvent.VK_NUMPAD1;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '2' no teclado numérico")
    public static final int TECLA_2_NUM = KeyEvent.VK_NUMPAD2;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '3' no teclado numérico")
    public static final int TECLA_3_NUM = KeyEvent.VK_NUMPAD3;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '4' no teclado numérico")
    public static final int TECLA_4_NUM = KeyEvent.VK_NUMPAD4;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '5' no teclado numérico")
    public static final int TECLA_5_NUM = KeyEvent.VK_NUMPAD5;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '6' no teclado numérico")
    public static final int TECLA_6_NUM = KeyEvent.VK_NUMPAD6;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '7' no teclado numérico")
    public static final int TECLA_7_NUM = KeyEvent.VK_NUMPAD7;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '8' no teclado numérico")
    public static final int TECLA_8_NUM = KeyEvent.VK_NUMPAD8;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '9' no teclado numérico")
    public static final int TECLA_9_NUM = KeyEvent.VK_NUMPAD9;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '*' no teclado numérico")
    public static final int TECLA_MULTIPLICACAO = KeyEvent.VK_MULTIPLY;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '+' no teclado numérico")
    public static final int TECLA_ADICAO = KeyEvent.VK_ADD;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla de separador decimal (',' ou '.') no teclado numérico")
    public static final int TECLA_NUM_SEPARADOR_DECIMAL = KeyEvent.VK_SEPARATOR;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '-' no teclado numérico")
    public static final int TECLA_SUBTRACAO	= KeyEvent.VK_SUBTRACT;
    
    @DocumentacaoConstante(descricao = "???")
    public static final int TECLA_DECIMAL = KeyEvent.VK_DECIMAL;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla '/' no teclado numérico")
    public static final int TECLA_DIVISAO = KeyEvent.VK_DIVIDE;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla DELETE no teclado numérico")
    public static final int TECLA_DELETAR = KeyEvent.VK_DELETE;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla NUM LOCK no teclado numérico")
    public static final int TECLA_NUM_LOCK = KeyEvent.VK_NUM_LOCK;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla SCROLL LOCK no teclado")
    public static final int TECLA_SCROLL_LOCK = KeyEvent.VK_SCROLL_LOCK;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F1' no teclado")
    public static final int TECLA_F1 = KeyEvent.VK_F1;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F2' no teclado")
    public static final int TECLA_F2 = KeyEvent.VK_F2;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F3' no teclado")
    public static final int TECLA_F3 = KeyEvent.VK_F3;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F4' no teclado")
    public static final int TECLA_F4 = KeyEvent.VK_F4;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F5' no teclado")
    public static final int TECLA_F5 = KeyEvent.VK_F5;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F6' no teclado")
    public static final int TECLA_F6 = KeyEvent.VK_F6;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F7' no teclado")
    public static final int TECLA_F7 = KeyEvent.VK_F7;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F8' no teclado")
    public static final int TECLA_F8 = KeyEvent.VK_F8;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F9' no teclado")
    public static final int TECLA_F9 = KeyEvent.VK_F9;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F10' no teclado")
    public static final int TECLA_F10 = KeyEvent.VK_F10;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F11' no teclado")
    public static final int TECLA_F11 = KeyEvent.VK_F11;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F12' no teclado")
    public static final int TECLA_F12 = KeyEvent.VK_F12;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F13' no teclado")
    public static final int TECLA_F13 = KeyEvent.VK_F13;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F14' no teclado")
    public static final int TECLA_F14 = KeyEvent.VK_F14;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F15' no teclado")
    public static final int TECLA_F15 = KeyEvent.VK_F15;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F16' no teclado")
    public static final int TECLA_F16 = KeyEvent.VK_F16;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F17' no teclado")
    public static final int TECLA_F17 = KeyEvent.VK_F17;
   
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F18' no teclado")
    public static final int TECLA_F18 = KeyEvent.VK_F18;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F19' no teclado")
    public static final int TECLA_F19 = KeyEvent.VK_F19;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F20' no teclado")
    public static final int TECLA_F20 = KeyEvent.VK_F20;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F21' no teclado")
    public static final int TECLA_F21 = KeyEvent.VK_F21;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F22' no teclado")
    public static final int TECLA_F22 = KeyEvent.VK_F22;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F23' no teclado")
    public static final int TECLA_F23 = KeyEvent.VK_F23;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla 'F24' no teclado")
    public static final int TECLA_F24 = KeyEvent.VK_F24;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla PRINT SCREEN no teclado")
    public static final int TECLA_PRINTSCREEN = KeyEvent.VK_PRINTSCREEN;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla INSERT no teclado")
    public static final int TECLA_INSERT = KeyEvent.VK_INSERT;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla AJUDA no teclado")
    public static final int TECLA_AJUDA = KeyEvent.VK_HELP;

    /*
    //@DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    //public static final int TECLA_META				= KeyEvent.VK_META;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ??? no teclado")
    public static final int TECLA_BACK_QUOTE = KeyEvent.VK_BACK_QUOTE;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ??? no teclado")
    public static final int TECLA_QUOTE = KeyEvent.VK_QUOTE;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_AMPERSAND				= KeyEvent.VK_AMPERSAND;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_ASTERISK				= KeyEvent.VK_ASTERISK;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_QUOTEDBL				= KeyEvent.VK_QUOTEDBL;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_LESS				= KeyEvent.VK_LESS;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_GREATER				= KeyEvent.VK_GREATER;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_BRACELEFT				= KeyEvent.VK_BRACELEFT;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_BRACERIGHT			= KeyEvent.VK_BRACERIGHT;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_AT				= KeyEvent.VK_AT;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_COLON				= KeyEvent.VK_COLON;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_CIRCUMFLEX			= KeyEvent.VK_CIRCUMFLEX;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_DOLLAR				= KeyEvent.VK_DOLLAR;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_EURO_SIGN				= KeyEvent.VK_EURO_SIGN;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_EXCLAMATION_MARK			= KeyEvent.VK_EXCLAMATION_MARK;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_INVERTED_EXCLAMATION_MARK		= KeyEvent.VK_INVERTED_EXCLAMATION_MARK;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_LEFT_PARENTHESIS			= KeyEvent.VK_LEFT_PARENTHESIS;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_NUMBER_SIGN			= KeyEvent.VK_NUMBER_SIGN;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_PLUS				= KeyEvent.VK_PLUS;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_RIGHT_PARENTHESIS			= KeyEvent.VK_RIGHT_PARENTHESIS;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla ###  no teclado")
    public static final int TECLA_UNDERSCORE			= KeyEvent.VK_UNDERSCORE;
    */
    @DocumentacaoConstante(descricao = "Código numérico da tecla de acesso ao menu Iniciar no teclado")    
    public static final int TECLA_WINDOWS = KeyEvent.VK_WINDOWS;
    
    @DocumentacaoConstante(descricao = "Código numérico da tecla MENU DE CONTEXTO no teclado")
    public static final int TECLA_MENU_CONTEXTO = KeyEvent.VK_CONTEXT_MENU;
    
    public Teclado()
    {
        observadorTeclado = new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                buffer[e.getKeyCode()] = true;
                temTeclaPressionada = true;
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                buffer[e.getKeyCode()] = false;
                ultimaTecla = e.getKeyCode();
                temTeclaPressionada = false;
                
                if (aguardandoTecla)
                {
                    acordarThread();
                }
            }
        };
        
        observadorJanela = new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent we) {
                limparEstadoTeclado();
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
                limparEstadoTeclado();
            }

            @Override
            public void windowIconified(WindowEvent we) {
                limparEstadoTeclado();
            }
            
            private void limparEstadoTeclado()
            {
//                if (aguardandoTecla)
//                {
//                    acordarThread();
//                }
                for (int i = 0; i < buffer.length; i++)
                {
                    buffer[i] = false;
                }
                
                temTeclaPressionada = false;
                ultimaTecla = -1;
                
            }
        };
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Testa se uma determinada <param>tecla</param> está pressionada neste instante",
        parametros = 
        {
            @DocumentacaoParametro(nome = "tecla", descricao = "o código da tecla que será testada")
        },
        autores = 
        {
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br"),
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        },
        retorno = "o resultado do teste. <tipo>Verdadeiro</tipo> se a <param>tecla</param> estiver pressionada no momento do teste. Caso contrário, retorna <tipo>falso</tipo>"
    )
    public boolean tecla_pressionada(int tecla) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return buffer[tecla];
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Testa se existe alguma tecla pressionada neste instante",
        autores = 
        {
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br"),
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        },
        retorno = "o resultado do teste. <tipo>Verdadeiro</tipo> se houver uma tecla pressionada no momento do teste. Caso contrário, retorna <tipo>falso</tipo>"
    )    
    public boolean alguma_tecla_pressionada() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return temTeclaPressionada;
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Aguarda até que uma tecla seja digitada, isto é, foi pressionada e depois solta, e captura o seu código",
        autores = 
        {
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br"),
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        },
        retorno = "o código da tecla lida"
    )
    public int ler_tecla() throws ErroExecucaoBiblioteca, InterruptedException, InterruptedException
    {
        synchronized (Teclado.this)
        {
            aguardandoTecla = true;
            wait();
            aguardandoTecla = false;
        }
        
        return ultimaTecla;
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Obtém o caracter correspondente a uma determinada <param>tecla</param>",
            
        parametros = 
        {
            @DocumentacaoParametro(nome = "tecla", descricao = "a tecla para a qual se quer obter o caracter")
        },
        
        retorno = "o caracter correspondente à tecla",
        
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }            
    )
    public char caracter_tecla(int tecla) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return (char) (int) tecla;
    }
    
    private synchronized void acordarThread()
    {
        notifyAll();
    }
    
    @Override
    public void inicializar(Programa programa, List<Biblioteca> bibliotecasReservadas) throws ErroExecucaoBiblioteca, InterruptedException
    {
        for (Biblioteca biblioteca : bibliotecasReservadas)
        {
            instalarTeclado(biblioteca);
        }
        Arrays.fill(buffer, false);
    }
    
    @Override
    public void bibliotecaRegistrada(Biblioteca biblioteca) throws ErroExecucaoBiblioteca, InterruptedException
    {
        instalarTeclado(biblioteca);
    }
    
    private void instalarTeclado(Biblioteca biblioteca) throws ErroExecucaoBiblioteca, InterruptedException
    {
        if (biblioteca instanceof InstaladorTeclado)
        {
            ((InstaladorTeclado) biblioteca).instalarTeclado(observadorTeclado, observadorJanela);
        }
    }
    
    public interface InstaladorTeclado
    {
        public void instalarTeclado(KeyListener observadorTeclado, WindowListener obervadorJanela) throws ErroExecucaoBiblioteca, InterruptedException;
    }
}
