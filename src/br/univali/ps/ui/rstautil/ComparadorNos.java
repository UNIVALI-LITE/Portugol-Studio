package br.univali.ps.ui.rstautil;

import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import java.util.Comparator;

/**
 *
 * @author elieser
 */
public class ComparadorNos implements Comparator<No> {

    @Override
    public int compare(No o1, No o2) {
        boolean linha = false;
        boolean coluna = false;
        boolean tamanho = false;
        boolean nome = false;
        if ((o1 instanceof NoDeclaracao) && (o2 instanceof NoDeclaracao)) {
            NoDeclaracao dec1 = (NoDeclaracao) o1;
            NoDeclaracao dec2 = (NoDeclaracao) o2;
            if (dec1.getTrechoCodigoFonteNome() == null) {
                return 0;
            }
            linha = dec1.getTrechoCodigoFonteNome().getLinha() == dec2.getTrechoCodigoFonteNome().getLinha();
            coluna = dec1.getTrechoCodigoFonteNome().getColuna() == dec2.getTrechoCodigoFonteNome().getColuna();
            tamanho = dec1.getTrechoCodigoFonteNome().getTamanhoTexto() == dec2.getTrechoCodigoFonteNome().getTamanhoTexto();
            nome = dec1.getNome().equals(dec2.getNome());
        }
        if (linha && coluna && tamanho && nome) {
            return 1;
        } else {
            return 0;
        }
    }
    
}
