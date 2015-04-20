package br.univali.ps.ui.abas;

import static br.univali.ps.ui.abas.AbaCodigoFonte.VALOR_INCREMENTO_FONTE;
import br.univali.ps.ui.editor.Editor;
import br.univali.ps.ui.rstautil.tree.PortugolOutlineTree;
import br.univali.ps.ui.util.IconFactory;
import br.univali.ps.ui.weblaf.BarraDeBotoesExpansivel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author elieser
 */
public class FabricaDeAcoesArvoreInspetor {

    
    
    public static BarraDeBotoesExpansivel.Acao criaAcaoAumentarFonte(final AbaCodigoFonte aba) {
        AbstractAction acaoAumentarFonteArvore = new AbstractAction("Aumentar fonte") {
            @Override
            public void actionPerformed(ActionEvent e) {
                float novoTamanho = aba.getTamanhoDaFonteArvoreInspetor() + VALOR_INCREMENTO_FONTE;
                aba.setTamanhoFonteArvoreInspetor(novoTamanho);
            }
        };
        return BarraDeBotoesExpansivel.criaAcao(acaoAumentarFonteArvore, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font_add.png"));
    }
    
    public static BarraDeBotoesExpansivel.Acao criaAcaoDiminuirFonte(final AbaCodigoFonte aba) {
        AbstractAction acaoDiminuirFonteArvore = new AbstractAction("Diminuir fonte") {
            @Override
            public void actionPerformed(ActionEvent e) {
                float novoTamanho = aba.getTamanhoDaFonteArvoreInspetor() - VALOR_INCREMENTO_FONTE;
                aba.setTamanhoFonteArvoreInspetor(novoTamanho);
            }
        };
        return BarraDeBotoesExpansivel.criaAcao(acaoDiminuirFonteArvore, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font_delete.png"));
    }

    
}
