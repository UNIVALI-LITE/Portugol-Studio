package br.univali.ps.ui.rstautil;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoEscolha;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.ps.ui.inspetor.VisitanteNulo;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author elieser
 */
public class BuscadorDeEscopo {

    public static int getHashCodeDoObjectDeEscopo(NoDeclaracao noProcurado, Programa programa, Comparator<NoDeclaracao> comparadorDeNos) throws ExcecaoVisitaASA {
        VisitorDoBuscador buscador = new VisitorDoBuscador(noProcurado, comparadorDeNos);
        programa.getArvoreSintaticaAbstrata().aceitar(buscador);
        return buscador.getHashCodeDoObjectDeEscopo();
    }

    //++++++++++++++
    private static class VisitorDoBuscador extends VisitanteNulo {

        private final NoDeclaracao noProcurado;
        private final Comparator<NoDeclaracao> comparador;
        private int hashCodeDoObjetoDeEscopo = -1;
        private boolean encontrouEscopo = false;//usado para podar

        public VisitorDoBuscador(NoDeclaracao noProcurado, Comparator<NoDeclaracao> comparador) {
            this.noProcurado = noProcurado;
            this.comparador = comparador;
        }

        int getHashCodeDoObjectDeEscopo() {
            return hashCodeDoObjetoDeEscopo;
        }

        @Override
        public Object visitar(ArvoreSintaticaAbstrataPrograma asap) throws ExcecaoVisitaASA {
            hashCodeDoObjetoDeEscopo = asap.hashCode();
            for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais()) {
                declaracao.aceitar(this);
                if (encontrouEscopo) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA {
            hashCodeDoObjetoDeEscopo = noCaso.hashCode();
            if (noCaso.getBlocos() != null) {
                for (NoBloco filho : noCaso.getBlocos()) {
                    filho.aceitar(this);
                    if (encontrouEscopo) {
                        return null;
                    }
                }
            }
            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
            hashCodeDoObjetoDeEscopo = declaracaoFuncao.hashCode();
            List<NoDeclaracaoParametro> parametros = declaracaoFuncao.getParametros();
            for (NoDeclaracaoParametro parametro : parametros) {
                parametro.aceitar(this);
                if(encontrouEscopo){
                    return null;
                }
            }
            for (NoBloco filho : declaracaoFuncao.getBlocos()) {
                filho.aceitar(this);
                if (encontrouEscopo) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
            if (comparador.compare(noProcurado, noDeclaracaoMatriz) > 0) {
                encontrouEscopo = true;
            }
            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
            if (comparador.compare(noProcurado, noDeclaracaoVariavel) > 0) {
                encontrouEscopo = true;
            }
            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA {
            if (comparador.compare(noProcurado, noDeclaracaoParametro) > 0) {
                encontrouEscopo = true;
            }
            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
            if (comparador.compare(noProcurado, noDeclaracaoVetor) > 0) {
                encontrouEscopo = true;
            }
            return null;
        }

        @Override
        public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA {
            hashCodeDoObjetoDeEscopo = noEnquanto.hashCode();
            for (NoBloco bloco : noEnquanto.getBlocos()) {
                bloco.aceitar(this);
                if (encontrouEscopo) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA {
            hashCodeDoObjetoDeEscopo = noEscolha.hashCode();
            for (NoCaso caso : noEscolha.getCasos()) {
                caso.aceitar(this);
                if (encontrouEscopo) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA {
            hashCodeDoObjetoDeEscopo = noFacaEnquanto.hashCode();
            for (NoBloco no : noFacaEnquanto.getBlocos()) {
                no.aceitar(this);
                if (encontrouEscopo) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public Object visitar(NoPara noPara) throws ExcecaoVisitaASA {
            hashCodeDoObjetoDeEscopo = noPara.hashCode();
            for (NoBloco no : noPara.getBlocos()) {
                no.aceitar(this);
                if (encontrouEscopo) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public Object visitar(NoSe noSe) throws ExcecaoVisitaASA {
            hashCodeDoObjetoDeEscopo = noSe.hashCode();
            for (NoBloco no : noSe.getBlocosVerdadeiros()) {
                no.aceitar(this);
                if (encontrouEscopo) {
                    return null;
                }
            }

            if (noSe.getBlocosFalsos() != null) {
                for (NoBloco no : noSe.getBlocosFalsos()) {
                    no.aceitar(this);
                    if (encontrouEscopo) {
                        return null;
                    }
                }
            }
            return null;
        }

    }
}
