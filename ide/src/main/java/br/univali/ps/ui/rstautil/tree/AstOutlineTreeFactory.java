package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.ASA;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoBitwiseNao;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoContinue;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoEscolha;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoLogico;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoMenosUnario;
import br.univali.portugol.nucleo.asa.NoNao;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseE;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseLeftShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseOu;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseRightShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseXOR;
import br.univali.portugol.nucleo.asa.NoOperacaoDivisao;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaDiferenca;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaE;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaIgualdade;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaior;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaiorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenor;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaOU;
import br.univali.portugol.nucleo.asa.NoOperacaoModulo;
import br.univali.portugol.nucleo.asa.NoOperacaoMultiplicacao;
import br.univali.portugol.nucleo.asa.NoOperacaoSoma;
import br.univali.portugol.nucleo.asa.NoOperacaoSubtracao;
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoParametroFuncao;
import br.univali.portugol.nucleo.asa.NoPare;
import br.univali.portugol.nucleo.asa.NoReal;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoSenao;
import br.univali.portugol.nucleo.asa.NoTitulo;
import br.univali.portugol.nucleo.asa.NoVaPara;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.GerenciadorBibliotecas;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.ps.ui.rstautil.tree.filters.ASTFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class AstOutlineTreeFactory implements VisitanteASA
{
    private SourceTreeNode root;
    private ASTFilter filter;

    public SourceTreeNode createTree(ASA asa)
    {
        this.root = new GenericTreeNode("Remove me!");
        this.filter = new AcceptAllFilter();

        try
        {
            asa.aceitar(this);
        }
        catch (ExcecaoVisitaASA ex)
        {
            ex.printStackTrace(System.err);
        }
        return root;
    }

    public SourceTreeNode createFilteredTree(ASA asa, ASTFilter filter)
    {
        this.root = new GenericTreeNode("Remove me!");
        this.filter = filter;

        try
        {
            asa.aceitar(this);
        }
        catch (ExcecaoVisitaASA ex)
        {
            ex.printStackTrace(System.err);
        }

        return root;
    }

    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA
    {
        ProgramaTreeNode programa = new ProgramaTreeNode();
        List<SourceTreeNode> nos = new ArrayList<>();

        for (NoDeclaracao no : asap.getListaDeclaracoesGlobais())
        {
            SourceTreeNode treeNode = (SourceTreeNode) no.aceitar(this);
            if (treeNode != null && treeNode instanceof PortugolTreeNode)
            {
                nos.add(treeNode);
            }
        }

        Collections.sort(nos, new ComparadorNos());

        for (SourceTreeNode no : nos)
        {
            programa.add(no);
        }

        root.add(programa);

        return null;
    }


    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA
    {
        return new PortugolTreeNode(noDeclaracaoParametro);
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca inclusao) throws ExcecaoVisitaASA
    {
        try
        {
            MetaDadosBiblioteca metaDadosBiblioteca = GerenciadorBibliotecas.getInstance().obterMetaDadosBiblioteca(inclusao.getNome());
            LibraryTreeNode raizBiblioteca = new LibraryTreeNode(inclusao, metaDadosBiblioteca);
            /*
             MetaDadosFuncoes metaDadosFuncoes = metaDadosBiblioteca.obterMetaDadosFuncoes();

             if (metaDadosFuncoes != null && !metaDadosFuncoes.vazio())
             {
             for (MetaDadosFuncao metaDadosFuncao : metaDadosFuncoes)
             {
             raizBiblioteca.add(new LibraryFunctionTreeNode(metaDadosBiblioteca, metaDadosFuncao));
             }
             }

             MetaDadosConstantes metaDadosConstantes = metaDadosBiblioteca.getMetaDadosConstantes();

             if (metaDadosConstantes != null && !metaDadosConstantes.vazio())
             {
             for (MetaDadosConstante metaDadosConstante : metaDadosConstantes)
             {
             raizBiblioteca.add(new LibraryVarTreeNode(metaDadosBiblioteca, metaDadosConstante));
             }
             }*/

            return raizBiblioteca;
        }
        catch (ErroCarregamentoBiblioteca erro)
        {
            return new LibraryTreeNode(erro);
        }
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
    {
        PortugolTreeNode node = new PortugolTreeNode(declaracaoFuncao);

        List<NoDeclaracaoParametro> parametros = declaracaoFuncao.getParametros();

        if (parametros != null)
        {
            for (NoDeclaracaoParametro parametro : parametros)
            {
                if (filter.accepts(parametro))
                {
                    node.add((PortugolTreeNode) parametro.aceitar(this));
                }
            }
        }

        List<NoBloco> body = declaracaoFuncao.getBlocos();

        if (body != null)
        {
            for (int i = 0; i < body.size(); i++)
            {
                Object no = body.get(i).aceitar(this);
                if (no != null)
                {
                    node.add((PortugolTreeNode) no);
                }
            }
        }

        if (filter.accepts(declaracaoFuncao) || node.getChildCount() > 0)
        {
            return node;
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
    {
        if (filter.accepts(noDeclaracaoMatriz))
        {
            return new PortugolTreeNode(noDeclaracaoMatriz);
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
    {
        if (filter.accepts(noDeclaracaoVariavel))
        {
            return new PortugolTreeNode(noDeclaracaoVariavel);
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
    {
        if (filter.accepts(noDeclaracaoVetor))
        {
            return new PortugolTreeNode(noDeclaracaoVetor);
        }

        return null;
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
    {
        PortugolTreeNode node = new PortugolTreeNode(noEnquanto);
        boolean folha = true;
        
        List<NoBloco> blocos = noEnquanto.getBlocos();
        
        if (blocos != null)
        {
            for (NoBloco noBloco : blocos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null)
                {
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)
        {
            return node;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
    {
        PortugolTreeNode node = new PortugolTreeNode(noEscolha);
        boolean folha = true;
        
        List<NoCaso> casos = noEscolha.getCasos();
        
        if (casos != null)
        {
            for (NoCaso noCaso : casos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noCaso.aceitar(this);

                if (ptn != null)
                {
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)
        {
            return node;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
    {
        PortugolTreeNode node = new PortugolTreeNode(noCaso);
        boolean folha = true;
        
        List<NoBloco> blocos = noCaso.getBlocos();
        
        if (blocos != null)
        {
            for (NoBloco noBloco : blocos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null)
                {
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)
        {
            return node;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
    {
        PortugolTreeNode node = new PortugolTreeNode(noFacaEnquanto);
        boolean folha = true;
        
        List<NoBloco> blocos = noFacaEnquanto.getBlocos();
        
        if (blocos != null)
        {
            for (NoBloco noBloco : blocos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null)
                {
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)
        {
            return node;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
    {
        PortugolTreeNode node = new PortugolTreeNode(noPara);
        boolean folha = true;
        
        if (noPara.getInicializacoes() != null)
        {
        	for (NoBloco inicializacao: noPara.getInicializacoes())
        	{
	            Object no = inicializacao.aceitar(this);
	            
	            if (no != null)
	            {
	                node.add((PortugolTreeNode) no);
	                folha = false;
	            }
        	}
        }
        
        List<NoBloco> blocos = noPara.getBlocos();
        
        if (blocos != null)
        {
            for (NoBloco noBloco : blocos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null)
                {
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)
        {
            return node;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
    {
        PortugolTreeNode node = new PortugolTreeNode(noSe);
        boolean folha = true;
        
        List<NoBloco> blocosVerdadeiros = noSe.getBlocosVerdadeiros();
        
        if (blocosVerdadeiros != null)
        {
            GenericTreeNode noverdadeiro = new GenericTreeNode("verdadeiro");
            boolean folhaverdadeiro = true;
            
            for (NoBloco noBloco : blocosVerdadeiros)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null)
                {
                    noverdadeiro.add(ptn);
                    folhaverdadeiro = false;
                }
            }

            if (!folhaverdadeiro)
            {
                node.add(noverdadeiro);
                folha = false;
            }

        }

        List<NoBloco> blocosFalsos = noSe.getBlocosFalsos();
        
        if (blocosFalsos != null)
        {
            GenericTreeNode falsos = new GenericTreeNode("falso");
            boolean folhafalso = true;
            
            for (NoBloco noBloco : blocosFalsos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null)
                {
                    falsos.add(ptn);
                    folhafalso = false;
                }
            }
            if (!folhafalso)
            {
                node.add(falsos);
                folha = false;
            }
        }

        if (!folha)
        {
            return node;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitar(NoVaPara noVaPara) throws ExcecaoVisitaASA
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitar(NoSenao noSenao) throws ExcecaoVisitaASA {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitar(NoParametroFuncao noParametroFuncao) throws ExcecaoVisitaASA {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class ComparadorNos implements Comparator<SourceTreeNode>
    {
        @Override
        public int compare(SourceTreeNode no1, SourceTreeNode no2)
        {
            Object o1 = no1.getUserObject();
            Object o2 = no2.getUserObject();

            if ((o1 instanceof NoDeclaracaoBase) && (o2 instanceof NoDeclaracaoBase))
            {
                NoDeclaracaoBase nd1 = (NoDeclaracaoBase) o1;
                NoDeclaracaoBase nd2 = (NoDeclaracaoBase) o2;

                if ((!(nd1 instanceof NoDeclaracaoFuncao)) && (nd2 instanceof NoDeclaracaoFuncao))
                {
                    return -1;
                }
                else if ((nd1 instanceof NoDeclaracaoFuncao) && (!(nd2 instanceof NoDeclaracaoFuncao)))
                {
                    return 1;
                }
                else
                {
                    return nd1.getNome().compareTo(nd2.getNome());
                }
            }

            return o1.toString().compareTo(o2.toString());
        }
    }

    private final class AcceptAllFilter implements ASTFilter
    {
        @Override
        public boolean accepts(No no)
        {
            return true;
        }
    }
}
