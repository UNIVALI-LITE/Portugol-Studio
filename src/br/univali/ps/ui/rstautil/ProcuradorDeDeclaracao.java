package br.univali.ps.ui.rstautil;

import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.ps.ui.rstautil.lista.VisitanteNulo;

//classe usada para procurar por um determinado símbolo dentro da ASA

public class ProcuradorDeDeclaracao extends VisitanteNulo {
    private NoDeclaracao noProcurado;
    private final String nomeDoSimbolo;
    private final int colunaDoSimbolo;
    private final int linhaDoSimbolo;
    private final int tamanhoDoTexto;

    public ProcuradorDeDeclaracao(String nomeDoSimbolo, int linhaDoSimbolo, int colunaDoSimbolo, int tamanhoDoTexto) {
        this.nomeDoSimbolo = nomeDoSimbolo;
        this.linhaDoSimbolo = linhaDoSimbolo;
        this.colunaDoSimbolo = colunaDoSimbolo;
        this.tamanhoDoTexto = tamanhoDoTexto;
    }

    public boolean encontrou() {
        return noProcurado != null;
    }

    public NoDeclaracao getNoDeclaracao() {
        return noProcurado;
    }

    private void verificarNoDeclaracao(NoDeclaracao no) {
        boolean mesmoNome = no.getNome().equals(nomeDoSimbolo);
        boolean mesmaLinha = no.getTrechoCodigoFonteNome().getLinha() == linhaDoSimbolo;
        boolean mesmaColuna = no.getTrechoCodigoFonteNome().getColuna() == colunaDoSimbolo;
        boolean mesmoTamanho = no.getTrechoCodigoFonteNome().getTamanhoTexto() == tamanhoDoTexto;
        boolean encontrouSimbolo = mesmoNome && mesmaLinha && mesmaColuna && mesmoTamanho;
        if (encontrouSimbolo) {
            this.noProcurado = no;
        }
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
        verificarNoDeclaracao(noDeclaracaoVariavel);
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
        verificarNoDeclaracao(noDeclaracaoVetor);
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
        verificarNoDeclaracao(noDeclaracaoMatriz);
        return null;
    }
    
}
