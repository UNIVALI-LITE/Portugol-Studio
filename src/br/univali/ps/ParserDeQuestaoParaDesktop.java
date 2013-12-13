package br.univali.ps;

import br.univali.portugol.corretor.dinamico.model.Questao;

public class ParserDeQuestaoParaDesktop implements ParserDeQuestao{

    @Override
    public Questao getQuestao(String conteudoDoArquivoPex) throws Exception{
        return Questao.geraQuestao(conteudoDoArquivoPex);
    }

}
