package br.univali.ps.ui.swing;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.utils.IconFactory;
import java.awt.Color;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author LITE
 */
public final class ColorController {
    private static Color[] getDefaultTheme(){
        return new Color[]{
                new Color(51,51,51),    //cinza escuro
                new Color(210,231,252), //branco azulado
                new Color(250,250,250), //branco
                new Color(49,104,146),  //azul
//                new Color(243,243,243), //branco
                new Color(193,217,245), //azul_medio
                new Color(228,241,254), //branco azulado
                new Color(255,194,0),   //amarelo
                new Color(255,194,0),   //amarelo
                new Color(69,189,255),  //azul claro
                new Color(240,67,59),   //vermelho
                new Color(0,239,192),   //ciano
                new Color(255,255,255), //branco
                new Color(255,255,255), //branco
                new Color(230,230,230), //cinza
                new Color(0,0,0,0)      //transparente
            };
    }
    private static Color[] getASHTheme(){
        return new Color[]{
                new Color(205,205,205), //cinza claro
                new Color(58,70,76),    //cinza azulado
                new Color(38,50,56),    //cinza azulado mais escuro
                new Color(18,30,36),    //cinza azulado muito mais escuro
                new Color(38,50,56),    //cinza azulado mais escuro
                new Color(68,80,86),    //cinza azulado mais claro
                new Color(241,67,60),   //vermelho
                new Color(255,194,0),   //amarelo
                new Color(69,189,255),  //azul claro
                new Color(240,67,59),   //vermelho
                new Color(0,239,192),   //ciano
                new Color(18,30,36),    //cinza azulado muito mais escuro
                new Color(205,205,205), //cinza claro
                new Color(58,70,76),    //cinza azulado
                new Color(0,0,0,0)      //Transparente
            };
    }
    
    private static Color[] getTheme()
    {
        JSONObject json = Configuracoes.getInstancia().getArquivo_temas();
        JSONObject json_temas = json.getJSONObject("temas");
        
        String tema_selecionado = json.getString("tema_selecionado");        
        JSONObject cores_tema = json_temas.getJSONObject(tema_selecionado);
        
        Configuracoes.getInstancia().setIconesCores(cores_tema.getString("icones"));
        IconFactory.verificarTema();
        
        return new Color[]{
                new Color(Integer.parseInt(cores_tema.getString("cor_letra"), 16)),
                new Color(Integer.parseInt(cores_tema.getString("cor_destaque"), 16)),
                new Color(Integer.parseInt(cores_tema.getString("cor_principal"), 16)),
                new Color(Integer.parseInt(cores_tema.getString("fundo_escuro"), 16)),
                new Color(Integer.parseInt(cores_tema.getString("fundo_medio"), 16)),
                new Color(Integer.parseInt(cores_tema.getString("fundo_claro"), 16)),
                new Color(Integer.parseInt(cores_tema.getString("progress_bar"), 16)),
                new Color(255,194,0),   //amarelo
                new Color(69,189,255),  //azul claro
                new Color(240,67,59),   //vermelho
                new Color(0,239,192),   //ciano
                new Color(Integer.parseInt(cores_tema.getString("fundo_botoes_expansiveis"), 16)),    //cinza azulado muito mais escuro
                new Color(Integer.parseInt(cores_tema.getString("cor_letra_titulo"), 16)), //cinza claro
                new Color(Integer.parseInt(cores_tema.getString("cor_console"), 16)),    //cinza azulado
                new Color(0,0,0,0)      //Transparente
            };     
    }
    
    private static final Color[] THEME= getTheme();
    public static final Color COR_LETRA = THEME[0];
    public static final Color COR_DESTAQUE = THEME[1];
    public static final Color COR_PRINCIPAL = THEME[2];
    public static final Color FUNDO_ESCURO = THEME[3];
    public static final Color FUNDO_MEDIO = THEME[4];
    public static final Color FUNDO_CLARO = THEME[5];
    public static final Color PROGRESS_BAR = THEME[6];
    public static final Color AMARELO = THEME[7];
    public static final Color AZUL = THEME[8];
    public static final Color VERMELHO = THEME[9];
    public static final Color VERDE = THEME[10];
    public static final Color FUNDO_BOTOES_EXPANSIVEIS = THEME[11];
    public static final Color COR_LETRA_TITULO = THEME[12];
    public static final Color COR_CONSOLE = THEME[13];
    public static final Color TRANSPARENTE = THEME[14];
}
