package br.univali.ps.ui;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.KeyStroke;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TradutorAtalhosTeclado
{
    private static final Map<String, String> mapeamento = criarMapeamento();
            
    private static Map<String, String> criarMapeamento()
    {
        Map<String, String> mapa = new HashMap<>();
        
        mapa.put("Escape", "Esc");
        mapa.put("Pausar", "Pause/Brake");
        mapa.put("Início", "Home");
        
        return mapa;
    }
    
    public static String traduzir(KeyStroke atalho)
    {
        String codigo = KeyEvent.getKeyText(atalho.getKeyCode());
        String modificadores = KeyEvent.getKeyModifiersText(atalho.getModifiers());

        for (String valor : mapeamento.keySet())
        {
            if (codigo.equals(valor))
                codigo = mapeamento.get(valor);
        }
        
        return modificadores.concat("+").concat(codigo);
    }
}
