package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.ps.ui.utils.IconFactory;
import javax.swing.Icon;
import br.univali.portugol.nucleo.asa.NoDeclaracaoInspecionavel;

/**
 *
 * @author elieser
 */
public abstract class ItemDaLista {

    protected final NoDeclaracao noDeclaracao;
    
    protected final int ID;

    protected boolean desenhaDestaques = true; //quando o programa está em execução o desenho dos destaques é desativado.
    //Não adiatanda destacar a última variável atualizada com o programa em execução já que o destaque fica "pulando" freneticamente
    //de uma variável para outra
    
    protected boolean estaNoEscopoAtual = true; // os itens só ficam fora de escopo na execução passo a passo

    public ItemDaLista(NoDeclaracao no) {
        this.noDeclaracao = no;
        ID = ((NoDeclaracaoInspecionavel) no).getIdParaInspecao();
    }

    public int getIdParaInspecao() {
        return ID;
    }

    public void setDesenhaDestaques(boolean statusDosDestaques) {
        desenhaDestaques = statusDosDestaques;
    }

    public void setEscopoAtual(boolean estaNoEscopoAtual)
    {
        this.estaNoEscopoAtual = estaNoEscopoAtual;
    }
    
    public boolean estaNoEscopoAtual()
    {
        return estaNoEscopoAtual;
    }
    
    abstract RenderizadorBase getRendererComponent();

    Icon getIcone() {
        String icone = "unknown";
        TipoDado tipo = getTipo();
        if (tipo != null) {
            if (ehVariavel()) {
                icone = tipo.getNome();
            }
            else if(ehVetor()) { // vetores e matrizes usam o mesmo ícone
                icone = "vetor_" + tipo.getNome();
            }
            else {
                icone = "matriz_" + tipo.getNome();
            }
        }
        return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, icone + ".png");
    }

    boolean ehVetor() {
        return noDeclaracao instanceof NoDeclaracaoVetor || (noDeclaracao instanceof NoDeclaracaoParametro && ((NoDeclaracaoParametro) noDeclaracao).getQuantificador() == Quantificador.VETOR);
    }

    boolean ehMatriz() {
        return noDeclaracao instanceof NoDeclaracaoMatriz || (noDeclaracao instanceof NoDeclaracaoParametro && ((NoDeclaracaoParametro) noDeclaracao).getQuantificador() == Quantificador.MATRIZ);
    }

    boolean ehVariavel() {
        return noDeclaracao instanceof NoDeclaracaoVariavel || (noDeclaracao instanceof NoDeclaracaoParametro && ((NoDeclaracaoParametro) noDeclaracao).getQuantificador() == Quantificador.VALOR);
    }

    public boolean podeDesenharDestaque() {
        return this == InspetorDeSimbolos.ultimoItemModificado && desenhaDestaques && estaNoEscopoAtual();
    }

    public String getNome() {
        return noDeclaracao.getNome();
    }

    public TipoDado getTipo() {
        return noDeclaracao.getTipoDado();
    }

    public NoDeclaracao getNoDeclaracao() {
        return noDeclaracao;
    }

    public void limpa() {
        estaNoEscopoAtual = true;
    }
    
    public abstract void atualiza(Programa programa);

}
