package br.univali.portugol.nucleo;

public enum TipoDado
{
    INTEIRO		("inteiro",         "inteiro",		0	),
    REAL		("real",            "real", 		0.0	),
    LOGICO		("logico",          "lógico", 		false	),
    CADEIA		("cadeia",          "cadeia", 		null	),
    CARACTER            ("caracter",        "caracter", 	'\0'	),
    VAZIO		("vazio",           "vazio", 		null	);

    private String nome;
    private String descricao;
    private Object valorPadrao;

    private TipoDado(String nome, String descricao, Object valorPadrao)
    {
        this.nome = nome;
        this.descricao = descricao;
        this.valorPadrao = valorPadrao;
    }

    public Object getValorPadrao()
    {
        return valorPadrao;
    }

    @Override
    public String toString()
    {
        return descricao;
    }

    public static TipoDado obterTipoDadoPeloNome(String nome)
    {
        TipoDado[] tiposDado = values();

        for (TipoDado tipoDado: tiposDado)
            if (nome.equals(tipoDado.nome))
                return tipoDado;

        return TipoDado.VAZIO;
    }
}
