package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrata;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoBitwiseNao;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
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
import br.univali.portugol.nucleo.asa.NoPare;
import br.univali.portugol.nucleo.asa.NoPercorra;
import br.univali.portugol.nucleo.asa.NoReal;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.CarregadorBibliotecas;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.ps.ui.rstautil.IconFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

class AstOutlineTreeFactory implements VisitanteASA
{
    private PortugolTreeNode root;

    public PortugolTreeNode createTree(ArvoreSintaticaAbstrata asa)
    {
        this.root = new PortugolTreeNode("Remove me!", IconFactory.SOURCE_FILE_ICON);
            
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
    public Object visitar(ArvoreSintaticaAbstrataPrograma asap) throws ExcecaoVisitaASA
    {
        MemberTreeNode bibliotecas = new MemberTreeNode("Bibliotecas");

        for (NoInclusaoBiblioteca inclusao : asap.getListaInclusoesBibliotecas())
        {
            bibliotecas.add((MemberTreeNode)inclusao.aceitar(this));
        }

        MemberTreeNode programa = new MemberTreeNode("Programa");

        for (Iterator<NoDeclaracao> i = asap.getListaDeclaracoesGlobais().iterator(); i.hasNext();)
        {
            NoDeclaracao td = i.next();
            MemberTreeNode dmtn = (MemberTreeNode) td.aceitar(this);
            programa.add(dmtn);
        }


        root.add(bibliotecas);
        root.add(programa);  
        
        return null;
    }
    
    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA
    {
        return new FuncParamTreeNode(noDeclaracaoParametro);
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca inclusao) throws ExcecaoVisitaASA
    {
        try
        {
            Biblioteca biblioteca = CarregadorBibliotecas.carregarBiblioteca(inclusao.getNome());
            MemberTreeNode raizBiblioteca = new LibraryTreeNode(inclusao, biblioteca);

            List<Method> funcoes = biblioteca.getFuncoes();

            if (funcoes != null)
            {
                for (Method funcao : funcoes)
                {
                    raizBiblioteca.add(new LibraryFunctionTreeNode(biblioteca, funcao));
                }
            }

            List<Field> variaveis = biblioteca.getVariaveis();

            if (variaveis != null)
            {
                for (Field variavel : variaveis)
                {
                    raizBiblioteca.add(new LibraryVarTreeNode(biblioteca, variavel));
                }
            }

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
        MemberTreeNode node = new MemberTreeNode(declaracaoFuncao);
        
        List<NoDeclaracaoParametro> parametros = declaracaoFuncao.getParametros();

        if (parametros != null)
        {
            for (NoDeclaracaoParametro parametro : parametros)
            {
                node.add((PortugolTreeNode)parametro.aceitar(this));
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
                    node.add((PortugolTreeNode)no);
                }
            }
        }

        return node;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
    {
        return new LocalVarTreeNode(noDeclaracaoMatriz);
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
    {
        return new LocalVarTreeNode(noDeclaracaoVariavel);
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
    {
        return new LocalVarTreeNode(noDeclaracaoVetor);
    }
    
    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
    {
        CommandTreeNode node = new CommandTreeNode(noEnquanto);
        boolean folha = true;
        List<NoBloco> blocos = noEnquanto.getBlocos();
        if (blocos != null){
            for (NoBloco noBloco : blocos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null){
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)        
            return node;
        else
            return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
    {
        CommandTreeNode node = new CommandTreeNode(noEscolha);
        boolean folha = true;
        List<NoCaso> casos = noEscolha.getCasos();
        if (casos != null){
            for (NoCaso noCaso : casos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noCaso.aceitar(this);

                if (ptn != null){
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)        
            return node;
        else
            return null;
    }

    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
    {
        CommandTreeNode node = new CommandTreeNode(noCaso);
        boolean folha = true;
        List<NoBloco> blocos = noCaso.getBlocos();
        if (blocos != null){
            for (NoBloco noBloco : blocos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null){
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)        
            return node;
        else
            return null;
    }
    
    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
    {
        CommandTreeNode node = new CommandTreeNode(noFacaEnquanto);
        boolean folha = true;
        List<NoBloco> blocos = noFacaEnquanto.getBlocos();
        if (blocos != null){
            for (NoBloco noBloco : blocos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null){
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)        
            return node;
        else
            return null;
    }
    
    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
    {
        CommandTreeNode node = new CommandTreeNode(noPara);
        boolean folha = true;
        if (noPara.getInicializacao() != null){
            Object no = noPara.getInicializacao().aceitar(this);
            if (no != null) {
                node.add((PortugolTreeNode)no);
                folha = false;
            }
        }
        List<NoBloco> blocos = noPara.getBlocos();
        if (blocos != null){
            for (NoBloco noBloco : blocos)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null){
                    node.add(ptn);
                    folha = false;
                }
            }
        }
        if (!folha)        
            return node;
        else
            return null;
    }
    
    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
    {
        CommandTreeNode node = new CommandTreeNode(noSe);
        boolean folha = true;
        List<NoBloco> blocosVerdadeiros = noSe.getBlocosVerdadeiros();
        if (blocosVerdadeiros != null){
            PortugolTreeNode noverdadeiro = new PortugolTreeNode("veradeiro", IconFactory.INTERFACE_ICON);
            boolean folhaverdadeiro = true;
            for (NoBloco noBloco : blocosVerdadeiros)
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);

                if (ptn != null){
                    noverdadeiro.add(ptn);
                    folhaverdadeiro = false;
                }
            }
        
            if (!folhaverdadeiro) {
                node.add(noverdadeiro); 
                folha = false;
            }    
            
        }
        
        List<NoBloco> blocosFalsos = noSe.getBlocosFalsos();
        if (blocosFalsos != null){  
             PortugolTreeNode falsos = new PortugolTreeNode("falso", IconFactory.INTERFACE_ICON);
             boolean folhafalso = true;
            for (NoBloco noBloco : blocosFalsos )
            {
                PortugolTreeNode ptn = (PortugolTreeNode) noBloco.aceitar(this);
                
                if (ptn != null){
                    falsos.add(ptn);
                    folhafalso = false;
                }
            }
            if (!folhafalso){
                node.add(falsos);
                folha = false;
            }
        }
        
        if (!folha)        
            return node;
        else
            return null;
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
    public Object visitar(NoPercorra noPercorra) throws ExcecaoVisitaASA
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

    
}
