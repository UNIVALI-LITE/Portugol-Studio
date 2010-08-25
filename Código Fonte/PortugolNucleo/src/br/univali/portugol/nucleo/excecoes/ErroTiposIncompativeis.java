package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.Operacao;
import br.univali.portugol.nucleo.TipoDado;
import br.univali.portugol.nucleo.asa.NoOperacao;

public class ErroTiposIncompativeis extends Erro
{
    private static final long serialVersionUID = -7376765028186940378L;

    private Operacao operacao;
    private TipoDado tipoDadoOperandoDireito;
    private TipoDado tipoDadoOperandoEsquerdo;

    public ErroTiposIncompativeis(NoOperacao operacao, TipoDado tipoDadoOperandoEsquerdo, TipoDado tipoDadoOperandoDireito)
    {
        this.operacao = operacao.getOperacao();

        setLinha(operacao.getToken().getLinha());
        setColuna(operacao.getToken().getColuna());

        this.tipoDadoOperandoDireito = tipoDadoOperandoDireito;
        this.tipoDadoOperandoEsquerdo = tipoDadoOperandoEsquerdo;
    }

    public Operacao getOperacao()
    {
        return operacao;
    }

    public TipoDado getTipoDadoOperandoDireito()
    {
        return tipoDadoOperandoDireito;
    }

    public TipoDado getTipoDadoOperandoEsquerdo()
    {
        return tipoDadoOperandoEsquerdo;
    }

    @Override
    protected String construirMensagem()
    {
        switch (operacao)
        {
            case ATRIBUICAO:                    return construirMensagemAtribuicao();
            case DIFERENCA:                     return construirMensagemDiferenca();
            case DIVISAO:                       return construirMensagemDivisao();
            case DIVISAO_ATRIBUITIVA:           return construirMensagemDivisaoAtribuitiva();
            case E:                             return construirMensagemE();
            case IGUALDADE:                     return construirMensagemIgualdade();
            case MAIOR:                         return construirMensagemMaior();
            case MAIOR_IGUAL:                   return construirMensagemMaiorIgual();
            case MENOR:                         return construirMensagemMenor();
            case MENOR_IGUAL:                   return construirMensagemMenorIgual();
            case MODULO:                        return construirMensagemModulo();
            case MODULO_ATRIBUITIVO:            return construirMensagemModuloAtribuitivo();
            case MULTIPLICACAO:                 return construirMensagemMultiplicacao();
            case MULTIPLICACAO_ATRIBUITIVA:     return construirMensagemMultiplicacaoAtribuitiva();
            case OU:                            return construirMensagemOu();
            case SOMA:                          return construirMensagemSoma();
            case SOMA_ATRIBUITIVA:              return construirMensagemSomaAtribuitiva();
            case SUBTRACAO:                     return construirMensagemSubtracao();
            case SUBTRACAO_ATRIBUITIVA:         return construirMensagemSubtracaoAtribuitiva();
        }

        return "A mensagem para a operação " + operacao + " ainda não foi implementada!";
    }

    private String construirMensagemAtribuicao()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível atribuir uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\" à uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemDiferenca()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" com uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemDivisao()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível dividir uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" por uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemDivisaoAtribuitiva()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível executar uma operação de divisão atribuitiva entre uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" e uma expressao do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemE()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível executar a operação lógica E entre uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" e uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemIgualdade()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" com uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemMaior()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" com uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemMaiorIgual()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" com uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemMenor()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" com uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemMenorIgual()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível comparar uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" com uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemModulo()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível obter o módulo entre uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" e uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemModuloAtribuitivo()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível executar uma operação de módulo atribuitivo entre uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" e uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemMultiplicacao()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível multiplicar uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" com uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemMultiplicacaoAtribuitiva()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível executar uma operação de multiplicação atribuitiva entre uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" e uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemOu()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível executar a operação lógica OU entre uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" e uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemSoma()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível somar uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" com uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemSomaAtribuitiva()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível executar uma operação de soma atribuitiva entre uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" e uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemSubtracao()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível subtrair uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\" de uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\".");

        return construtorString.toString();
    }

    private String construirMensagemSubtracaoAtribuitiva()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! Não é possível executar uma operação de subtração atribuitiva entre uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoEsquerdo);
        construtorString.append("\" e uma expressão do tipo \"");
        construtorString.append(tipoDadoOperandoDireito);
        construtorString.append("\".");

        return construtorString.toString();
    }
}
