package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import java.util.logging.Logger;

/**
 * @author Elieser
 */
public class ComparadorDeNos {

    private static final Logger LOGGER = Logger.getLogger(ComparadorDeNos.class.getName());

    private boolean nosTemMesmoEscopo(NoDeclaracao no1, NoDeclaracao no2) {
        TrechoCodigoFonte trecho1 = no1.getTrechoCodigoFonte();
        TrechoCodigoFonte trecho2 = no2.getTrechoCodigoFonte();
        if (trecho1 != null && trecho2 != null) {
            return trecho1.getLinha() == trecho2.getLinha(); // se as 2 declarações estão na mesma linha então estão no mesmo escopo
        }
        return false;
    }
    
    public boolean mesmoNo(NoDeclaracao no1, NoDeclaracao no2) {
        boolean mesmoEscopo = nosTemMesmoEscopo(no1, no2);
        boolean mesmoNome = no1.getNome().equals(no2.getNome());
        boolean mesmoTipo = no1.getTipoDado() == no2.getTipoDado();
        return mesmoEscopo && mesmoNome && mesmoTipo;
    }

}
