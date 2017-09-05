package br.univali.portugol.nucleo.asa;

/**
 * Classe de suporte para permitir o disparo de exceções durante o caminhamento da árvore utilizando o padrão visitor.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * @see VisitanteASA
 */
public final class ExcecaoVisitaASA extends Exception
{
    private No no;
    private ASA arvoreSintaticaAbstrata;

    /**
     * 
     * @param mensagem                    a descrição da exceção ocorrida
     * @param arvoreSintaticaAbstrata     a ASA que estava sendo percorrida quando a exceção ocorreu
     * @param no                          o nó da ASA que estava sendo visitado quando a exceção ocorreu
     * @since 1.0
     */
    public ExcecaoVisitaASA(String mensagem, ASA arvoreSintaticaAbstrata, No no)
    {
        super(mensagem);
        this.arvoreSintaticaAbstrata = arvoreSintaticaAbstrata;
        this.no = no;
    }

    /**
     * 
     * @param causa                       o erro que originou a exceção
     * @param arvoreSintaticaAbstrata     a ASA que estava sendo percorrida quando a exceção ocorreu
     * @param no                          o nó da ASA que estava sendo visitado quando a exceção ocorreu
     * @since 1.0
     */
    public ExcecaoVisitaASA(Throwable causa, ASA arvoreSintaticaAbstrata, No no)
    {
        super(causa);
        this.no = no;
        this.arvoreSintaticaAbstrata = arvoreSintaticaAbstrata;
    }


    /**
     * @param mensagem                    a descrição da exceção ocorrida
     * @param causa                       o erro que originou a exceção
     * @param arvoreSintaticaAbstrata     a ASA que estava sendo percorrida quando a exceção ocorreu
     * @param no                          o nó da ASA que estava sendo visitado quando a exceção ocorreu
     * @since 1.0
     */
    public ExcecaoVisitaASA(String mensagem, Throwable causa, ASA arvoreSintaticaAbstrata, No no)
    {
        super(mensagem, causa);
        this.no = no;
        this.arvoreSintaticaAbstrata = arvoreSintaticaAbstrata;
    }

    /**
     * Obtém a ASA que estava sendo percorrida quando a exceção ocorreu
     * 
     * @return     a ASA
     * @since 1.0
     */
    public ASA getArvoreSintaticaAbstrata()
    {
        return arvoreSintaticaAbstrata;
    }

    /**
     * Obtém o nó da ASA que estava sendo visitado quando a exceção ocorreu
     * 
     * @return     o nó da ASA
     * @since 1.0
     */
    public No getNo()
    {
        return no;
    }
}
