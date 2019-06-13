package br.univali.portugol.nucleo.compilador;

import br.univali.portugol.nucleo.asa.*;

import java.util.ArrayList;
import java.util.List;

final class LocalizadorFuncoes extends VisitanteASABasico {
    private final List<String> funcoes = new ArrayList<>();

    public List<String> getFuncoes(ASAPrograma asa) {
        try {
            asa.aceitar(this);
        } catch (ExcecaoVisitaASA excecaoVisitaASA) {
            excecaoVisitaASA.printStackTrace(System.out);
        }

        return funcoes;
    }

    public String getFuncaoInicial() {
        for (String funcao : funcoes) {
            if (funcao.equals(Compilador.FUNCAO_INICIAL_PADRAO)) {
                return Compilador.FUNCAO_INICIAL_PADRAO;
            }
        }

        if (!funcoes.isEmpty()) {
            return funcoes.get(0);
        }

        return Compilador.FUNCAO_INICIAL_PADRAO;
    }

    private boolean estaNaListaNegra(String funcao) {
        for (String func : Compilador.FUNCOES_ESPECIAIS) {
            if (funcao.equals(func)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA {
        List<NoDeclaracao> declaracoes = asap.getListaDeclaracoesGlobais();

        if (declaracoes != null) {
            for (NoDeclaracao declaracao : declaracoes) {
                if (!estaNaListaNegra(declaracao.getNome())) {
                    declaracao.aceitar(this);
                }
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
        funcoes.add(declaracaoFuncao.getNome());

        return null;
    }
}
