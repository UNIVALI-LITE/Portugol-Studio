package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.ps.ui.rstautil.BuscadorDeEscopo;
import br.univali.ps.ui.rstautil.ComparadorNos;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elieser
 */
public class ComparadorDeNos {

    private static final Logger LOGGER = Logger.getLogger(ComparadorDeNos.class.getName());
    private static final ComparadorNos COMPARADOR_NOS = new ComparadorNos();

    private final Programa programa;

    public ComparadorDeNos(Programa programa) {
        this.programa = programa;
    }

    public boolean mesmoNo(NoDeclaracao no1, NoDeclaracao no2, boolean consideraEscopo) {
        if (!consideraEscopo) {
            return COMPARADOR_NOS.compare(no1, no2) > 0;
        }

        try {
            Comparator<NoDeclaracao> comparadorDeNos = new ComparadorSimplificado();
            int hashCodeEscopoNo1 = BuscadorDeEscopo.getHashCodeDoObjectDeEscopo(no1, programa, comparadorDeNos);
            int hashCodeEscopoNo2 = BuscadorDeEscopo.getHashCodeDoObjectDeEscopo(no2, programa, comparadorDeNos);
            boolean mesmoEscopo = hashCodeEscopoNo1 == hashCodeEscopoNo2;
            boolean mesmoNome = no1.getNome().equals(no2.getNome());
            boolean mesmoTipo = no1.getTipoDado() == no2.getTipoDado();
            return mesmoEscopo && mesmoNome && mesmoTipo;
        } catch (ExcecaoVisitaASA excecao) {
            LOGGER.log(Level.SEVERE, null, excecao);
        }
        return false;
    }

    //este comparador compara apenas nome e tipo das declarações. Ele é usado somente quando o escopo das declarações também está sendo considerado
    //para determinar se dois nós representam a mesma informação
    private class ComparadorSimplificado implements Comparator<NoDeclaracao> {

        @Override
        public int compare(NoDeclaracao t, NoDeclaracao t1) {
            boolean mesmoNome = t.getNome().equals(t1.getNome());
            boolean mesmoTipoDeDados = t.getTipoDado() == t1.getTipoDado();
            boolean mesmaClasse = t.getClass().equals(t1.getClass());
            return (mesmoNome && mesmoTipoDeDados && mesmaClasse) ? 1 : -1;
        }

    }

}
