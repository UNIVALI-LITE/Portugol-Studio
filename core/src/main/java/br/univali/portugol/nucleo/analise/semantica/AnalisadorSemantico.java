package br.univali.portugol.nucleo.analise.semantica;

import br.univali.portugol.nucleo.analise.semantica.avisos.AvisoMatrizPodeSerVariavel;
import br.univali.portugol.nucleo.analise.semantica.avisos.AvisoMatrizPodeSerVetor;
import br.univali.portugol.nucleo.analise.semantica.avisos.AvisoSimboloGlobalOcultado;
import br.univali.portugol.nucleo.analise.semantica.avisos.AvisoValorExpressaoSeraConvertido;
import br.univali.portugol.nucleo.analise.semantica.avisos.AvisoVetorPodeSerVariavel;
import br.univali.portugol.nucleo.analise.semantica.erros.*;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.bibliotecas.base.*;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Percorre a {@link ASA} gerada a partir do código fonte para detectar
 * erros de semântica.
 *
 * @version 2.0
 *
 * @see AnalisadorSintatico
 * @see ObservadorAnaliseSemantica
 */
public final class AnalisadorSemantico implements VisitanteASA
{    
    private static final List<String> FUNCOES_RESERVADAS = getLista();
    
    private String codigoFonte;

    private final Memoria memoria;
    private final List<ObservadorAnaliseSemantica> observadores;
    private final Map<String, MetaDadosBiblioteca> metaDadosBibliotecas;
    private final TabelaCompatibilidadeTipos tabelaCompatibilidadeTipos = TabelaCompatibilidadeTiposPortugol.INSTANCE;

    //private boolean declarandoSimbolosGlobais;
    private ASA asa;
    private Funcao funcaoAtual;
    private NoBloco blocoAtual;
    private Stack<TipoDado> tipoDadoEscolha = new Stack<>();

    private boolean declarandoVetor;
    private boolean declarandoMatriz;
    private boolean passandoReferencia = false;
    private boolean passandoParametro = false;

    public static final String FUNCAO_LIMPA = "limpa";
    public static final String FUNCAO_LEIA = "leia";
    public static final String FUNCAO_ESCREVA = "escreva";
    public static final String FUNCAO_ALEATORIO = "sorteia";

    private int totalVariaveisDeclaradas = 0; // conta variáveis e parâmetros declarados
    private int totalVetoresDeclarados = 0;
    private int totalMatrizesDeclaradas = 0;
    
    public AnalisadorSemantico(String codigoFonte)
    {
        this.codigoFonte = codigoFonte;
        memoria = new Memoria();
        metaDadosBibliotecas = new TreeMap<>();
        observadores = new ArrayList<>();
    }

    /**
     * Permite adicionar um observador à análise semântica. Os observadores
     * serão notificados sobre cada erro semântico encontrado no código fonte e
     * deverão tratá-los apropriadamente, exibindo-os em uma IDE, por exemplo.
     *
     * @param observadorAnaliseSemantica o observador da análise semântica a ser
     * registrado.
     * @since 1.0
     */
    public void adicionarObservador(ObservadorAnaliseSemantica observadorAnaliseSemantica)
    {
        if (!observadores.contains(observadorAnaliseSemantica))
        {
            observadores.add(observadorAnaliseSemantica);
        }
    }

    /**
     * Remove um observador da análise previamente registrado utilizando o
     * método 
     * {@link AnalisadorSemantico#adicionarObservador(br.univali.portugol.nucleo.analise.semantica.ObservadorAnaliseSemantica) }.
     * Uma vez removido, o observador não será mais notificado dos erros
     * semânticos encontrados durante a análise.
     *
     * @param observadorAnaliseSemantica um observador de análise semântica
     * previamente registrado.
     * @since 1.0
     */
    public void removerObservador(ObservadorAnaliseSemantica observadorAnaliseSemantica)
    {
        observadores.remove(observadorAnaliseSemantica);
    }

    private void notificarAviso(AvisoAnalise aviso)
    {
        for (ObservadorAnaliseSemantica observadorAnaliseSemantica : observadores)
        {
            observadorAnaliseSemantica.tratarAviso(aviso);
        }
    }

    private void notificarErroSemantico(ErroSemantico erroSemantico)
    {
        for (ObservadorAnaliseSemantica observadorAnaliseSemantica : observadores)
        {
            observadorAnaliseSemantica.tratarErroSemantico(erroSemantico);
        }
    }

    /**
     * Realiza a análise semântica de uma ASA. Este método não retorna valor e
     * não gera exceções. Para capturar os erros semânticos gerados durante a
     * análise, deve-se registrar um ou mais obsrvadores de análise utilizando o
     * método 
     * {@link AnalisadorSemantico#adicionarObservador(br.univali.portugol.nucleo.analise.semantica.ObservadorAnaliseSemantica) }.
     *
     * @param asa a ASA que será percorrida em busca de erros semânticos.
     * @since 1.0
     */
    public void analisar(ASA asa)
    {
        this.asa = asa;
        if (asa != null)
        {
            try
            {
                asa.aceitar(this);
            }
            catch (Exception excecao)
            {
                notificarErroSemantico(new ErroSemanticoNaoTratado(excecao));
            }
        }
    }
    
    private void setarPaiDoNo(No no)
    {
        if (blocoAtual != null) {
            no.setPai(blocoAtual);
        }
    }
    
    private Funcao criaSimboloFuncao(NoDeclaracaoFuncao declaracaoFuncao) {
        String nome = declaracaoFuncao.getNome();
        TipoDado tipoDado = declaracaoFuncao.getTipoDado();
        Quantificador quantificador = declaracaoFuncao.getQuantificador();
        
        Funcao funcao = new Funcao(nome, tipoDado, quantificador, declaracaoFuncao.getParametros(), declaracaoFuncao);
        funcao.setTrechoCodigoFonteNome(declaracaoFuncao.getTrechoCodigoFonteNome());
        funcao.setTrechoCodigoFonteTipoDado(declaracaoFuncao.getTrechoCodigoFonteTipoDado());
        
        return funcao;
    }
    
    private void registraFuncaoNaTabelaDeSimbolos(NoDeclaracaoFuncao declaracaoFuncao)
    {
        boolean funcaoReservada = FUNCOES_RESERVADAS.contains(declaracaoFuncao.getNome());
        
        if (funcaoReservada) {
            notificarErroSemantico(new ErroSemantico(declaracaoFuncao.getTrechoCodigoFonteNome()) {
                @Override
                protected String construirMensagem() {
                    return "A função " + declaracaoFuncao.getNome() + " é reservada para a linguagem";
                }
            });
            Funcao funcao = criaSimboloFuncao(declaracaoFuncao);
            funcao.setRedeclarado(true);
            memoria.adicionarSimbolo(funcao);
        }
        else {
            Funcao funcao = criaSimboloFuncao(declaracaoFuncao);
            
            Simbolo simbolo = memoria.getSimbolo(declaracaoFuncao.getNome());
            if (simbolo == null) {
                memoria.adicionarSimbolo(funcao);
            } else {
                notificarErroSemantico(new ErroSimboloRedeclarado(funcao, simbolo));
                funcao.setRedeclarado(true);
            }
        }
    }

    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA
    {

        List<NoDeclaracao> declaracoes = asap.getListaDeclaracoesGlobais(true);
        boolean possuiFuncaoInicio = false;
        
        // itera somente nas declarações de funções para colocá-las na tabela de símbolos
        for (NoDeclaracao declaracao : declaracoes) {
            if (declaracao instanceof NoDeclaracaoFuncao) {
                if(((NoDeclaracaoFuncao)declaracao).getNome().equals("inicio"))
                {
                    possuiFuncaoInicio = true;
                }
                registraFuncaoNaTabelaDeSimbolos((NoDeclaracaoFuncao)declaracao);
            }
        }
        
        if(!possuiFuncaoInicio)
        {
            notificarErroSemantico(new ErroFuncaoInicioInexistente(codigoFonte.length()));
        }
        
        for (NoDeclaracao declaracao : declaracoes) {
            declaracao.aceitar(this);
        }

        asap.setTotalVariaveisDeclaradas(totalVariaveisDeclaradas);
        asap.setTotalVetoresDeclarados(totalVetoresDeclarados);
        asap.setTotalMatrizesDeclaradas(totalMatrizesDeclaradas);
        
        return null;
    }

    @Override
    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noCadeia);
        return TipoDado.CADEIA;
    }

    @Override
    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noCaracter);
        return TipoDado.CARACTER;
    }

    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noCaso);
        
        if (noCaso.getExpressao() != null)
        {
            TipoDado tipoDado = (TipoDado) noCaso.getExpressao().aceitar(this);

            if ((tipoDadoEscolha.peek() == TipoDado.INTEIRO) || (tipoDadoEscolha.peek() == TipoDado.CARACTER))
            {
                if (tipoDado != tipoDadoEscolha.peek())
                {
                    notificarErroSemantico(new ErroTiposIncompativeis(noCaso, tipoDado, tipoDadoEscolha.peek()));
                }
            }
            else if ((tipoDado != TipoDado.INTEIRO) && (tipoDado != TipoDado.CARACTER))
            {
                notificarErroSemantico(new ErroTiposIncompativeis(noCaso, tipoDado, TipoDado.INTEIRO, TipoDado.CARACTER));
            }
        }

        analisarListaBlocos(noCaso.getBlocos());

        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(chamadaFuncao);
        
        verificarFuncaoExiste(chamadaFuncao);
        verificarQuantidadeParametros(chamadaFuncao);
        verificarTiposParametros(chamadaFuncao);
        verificarQuantificador(chamadaFuncao);
        verificarModoAcesso(chamadaFuncao);
        verificarParametrosObsoletos(chamadaFuncao);

        return obterTipoRetornoFuncao(chamadaFuncao);
    }

    private void verificarModoAcesso(NoChamadaFuncao chamadaFuncao)
    {
        List<ModoAcesso> modosAcessoEsperados = obterModosAcessoEsperados(chamadaFuncao);
        List<ModoAcesso> modosAcessoPassados = obterModosAcessoPassados(chamadaFuncao);

        int cont = Math.min(modosAcessoEsperados.size(), modosAcessoPassados.size());
        
        if (chamadaFuncao.getNome().equals(FUNCAO_LEIA))
        {
            cont = modosAcessoPassados.size();// a função leia retorna uma lista vazia em modos de acesso esperados
            for (int indice = 0; indice < cont; indice++)
            {
                NoExpressao parametro = chamadaFuncao.getParametros().get(indice);
                boolean parametroValido = parametro instanceof NoReferenciaVariavel || parametro instanceof NoReferenciaVetor || parametro instanceof NoReferenciaMatriz;

                // verifica se o usuário está tentando usar uma constante na função LEIA
                if (parametroValido && parametro instanceof NoReferenciaVariavel) {
                    NoDeclaracaoBase origemDaReferencia = ((NoReferenciaVariavel)parametro).getOrigemDaReferencia();
                    parametroValido = origemDaReferencia != null && !origemDaReferencia.constante();
                        
                }
                if (!parametroValido)
                {
                    notificarErroSemantico(new ErroPassagemParametroInvalida(chamadaFuncao.getParametros().get(indice), obterNomeParametro(chamadaFuncao, indice), chamadaFuncao.getNome(), indice));
                }
            }
        }
        else
        {
            for (int indice = 0; indice < cont; indice++)
            {
                ModoAcesso modoAcessoEsperado = modosAcessoEsperados.get(indice);
                ModoAcesso modoAcessoPassado = modosAcessoPassados.get(indice);

                if (modoAcessoEsperado == ModoAcesso.POR_REFERENCIA && modoAcessoPassado == ModoAcesso.POR_VALOR)
                {


                    notificarErroSemantico(new ErroPassagemParametroInvalida(chamadaFuncao.getParametros().get(indice), obterNomeParametro(chamadaFuncao, indice), chamadaFuncao.getNome(), indice));
                }
            }
        }
    }

    private List<ModoAcesso> obterModosAcessoPassados(NoChamadaFuncao chamadaFuncao)
    {
        List<ModoAcesso> modosAcesso = new ArrayList<>();

        if (chamadaFuncao.getParametros() != null)
        {
            for (NoExpressao parametro : chamadaFuncao.getParametros())
            {
                if (parametro instanceof NoReferenciaVariavel)
                {
                    NoReferenciaVariavel noReferenciaVariavel = (NoReferenciaVariavel) parametro;

                    if (noReferenciaVariavel.getEscopoBiblioteca() == null)
                    {
                        Simbolo simbolo = memoria.getSimbolo(noReferenciaVariavel.getNome());
                        if(simbolo == null)
                        {
                            return modosAcesso;
                        }
                        if (simbolo.constante())
                        {
                            modosAcesso.add(ModoAcesso.POR_VALOR);
                        }
                        else
                        {
                            modosAcesso.add(ModoAcesso.POR_REFERENCIA);
                        }
                    }
                    else
                    {
                        modosAcesso.add(ModoAcesso.POR_VALOR);
                    }
                }
                else
                {
                    modosAcesso.add(ModoAcesso.POR_VALOR);
                }
            }
        }

        return modosAcesso;
    }

    private List<ModoAcesso> obterModosAcessoEsperados(NoChamadaFuncao chamadaFuncao)
    {
        List<ModoAcesso> modosAcesso = new ArrayList<>();

        if (chamadaFuncao.getEscopoBiblioteca() == null)
        {
            if (!FUNCOES_RESERVADAS.contains(chamadaFuncao.getNome()))
            {
                Funcao funcao = (Funcao) memoria.getSimbolo(chamadaFuncao.getNome());

                for (NoDeclaracaoParametro parametro : funcao.getParametros())
                {
                    modosAcesso.add(parametro.getModoAcesso());
                }
            }
        }
        else
        {
            MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(chamadaFuncao.getEscopoBiblioteca());
            MetaDadosFuncao metaDadosFuncao = metaDadosBiblioteca.obterMetaDadosFuncoes().obter(chamadaFuncao.getNome());
            MetaDadosParametros metaDadosParametros = metaDadosFuncao.obterMetaDadosParametros();

            for (MetaDadosParametro metaDadosParametro : metaDadosParametros)
            {
                modosAcesso.add(metaDadosParametro.getModoAcesso());
            }
        }

        return modosAcesso;
    }

    private TipoDado obterTipoRetornoFuncao(NoChamadaFuncao chamadaFuncao)
    {
        if (chamadaFuncao.getEscopoBiblioteca() == null)
        {
            if(chamadaFuncao.getNome().equals(FUNCAO_ALEATORIO)){
                return TipoDado.INTEIRO;
            }else if (FUNCOES_RESERVADAS.contains(chamadaFuncao.getNome()))
            {
                return TipoDado.VAZIO;
            }
            else
            {
                Funcao funcao = (Funcao) memoria.getSimbolo(chamadaFuncao.getNome());

                return funcao.getTipoDado();
            }
        }
        else
        {
            MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(chamadaFuncao.getEscopoBiblioteca());
            MetaDadosFuncao metaDadosFuncao = metaDadosBiblioteca.obterMetaDadosFuncoes().obter(chamadaFuncao.getNome());

            return metaDadosFuncao.getTipoDado();
        }

        //return null;
    }

    private void verificarParametrosObsoletos(final NoChamadaFuncao chamadaFuncao)
    {
        int parametrosEsperados = obterNumeroParametrosEsperados(chamadaFuncao);
        int parametrosPassados = (chamadaFuncao.getParametros() != null) ? chamadaFuncao.getParametros().size() : 0;

        int inicio = Math.min(parametrosEsperados, parametrosPassados);

        if (chamadaFuncao.getParametros() != null)
        {
            for (int indice = inicio; indice < parametrosPassados; indice++)
            {
                NoExpressao parametro = chamadaFuncao.getParametros().get(indice);

                notificarErroSemantico(new ErroParametroExcedente(parametro.getTrechoCodigoFonte(), chamadaFuncao));
            }
        }
    }

    private void verificarQuantificador(NoChamadaFuncao chamadaFuncao)
    {
        List<Quantificador> quantificadoresEsperados = obterQuantificadoresEsperados(chamadaFuncao);
        List<Quantificador> quantificadoresPassados = obterQuantificadoresPassados(chamadaFuncao);

        int cont = Math.min(quantificadoresEsperados.size(), quantificadoresPassados.size());

        for (int indice = 0; indice < cont; indice++)
        {
            Quantificador quantificadorPassado = quantificadoresPassados.get(indice);
            Quantificador quantificadorEsperado = quantificadoresEsperados.get(indice);

            if (quantificadorPassado != quantificadorEsperado)
            {
                notificarErroSemantico(new ErroQuantificadorParametroFuncao(chamadaFuncao, indice, obterNomeParametro(chamadaFuncao, indice), quantificadorEsperado, quantificadorPassado));
            }
        }
    }

    private List<Quantificador> obterQuantificadoresEsperados(NoChamadaFuncao chamadaFuncao)
    {
        List<Quantificador> quantificadores = new ArrayList<>();

        if (chamadaFuncao.getEscopoBiblioteca() == null)
        {
            if (!FUNCOES_RESERVADAS.contains(chamadaFuncao.getNome()))
            {
                Funcao funcao = (Funcao) memoria.getSimbolo(chamadaFuncao.getNome());

                for (NoDeclaracaoParametro declaracaoParametro : funcao.getParametros())
                {
                    quantificadores.add(declaracaoParametro.getQuantificador());
                }
            }
        }
        else
        {
            MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(chamadaFuncao.getEscopoBiblioteca());
            MetaDadosFuncao metaDadosFuncao = metaDadosBiblioteca.obterMetaDadosFuncoes().obter(chamadaFuncao.getNome());
            MetaDadosParametros metaDadosParametros = metaDadosFuncao.obterMetaDadosParametros();

            for (MetaDadosParametro metaDadosParametro : metaDadosParametros)
            {
                quantificadores.add(metaDadosParametro.getQuantificador());
            }
        }

        return quantificadores;
    }

    private List<Quantificador> obterQuantificadoresPassados(NoChamadaFuncao chamadaFuncao)
    {
        List<Quantificador> quantificadores = new ArrayList<>();

        if (chamadaFuncao.getParametros() != null)
        {
            for (NoExpressao parametroPassado : chamadaFuncao.getParametros())
            {
                if (parametroPassado instanceof NoReferenciaVariavel)
                {
                    String nome = ((NoReferenciaVariavel) parametroPassado).getNome();
                    Simbolo simbolo = memoria.getSimbolo(nome);

                    if (simbolo instanceof Variavel)
                    {
                        quantificadores.add(Quantificador.VALOR);
                    }
                    else if (simbolo instanceof Vetor)
                    {
                        quantificadores.add(Quantificador.VETOR);
                    }
                    else if (simbolo instanceof Matriz)
                    {
                        quantificadores.add(Quantificador.MATRIZ);
                    }
                }
                else if (parametroPassado instanceof NoVetor)
                {
                    quantificadores.add(Quantificador.VETOR);
                }
                else if (parametroPassado instanceof NoMatriz)
                {
                    quantificadores.add(Quantificador.MATRIZ);
                }
                else
                {
                    quantificadores.add(Quantificador.VALOR);
                }
            }
        }

        return quantificadores;
    }

    private void verificarTiposParametros(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
    {
        List<ModoAcesso> modosAcesso = obterModosAcessoEsperados(chamadaFuncao);
        List<TipoDado> tiposEsperados = obterTiposParametrosEsperados(chamadaFuncao);
        List<TipoDado> tiposPassado = obterTiposParametrosPassados(chamadaFuncao, modosAcesso);

        int cont = Math.min(tiposEsperados.size(), tiposPassado.size());
        if (chamadaFuncao.getNome().equals(FUNCAO_ALEATORIO))
        {
            for (int indice = 0; indice < 2; indice++)
            {
                TipoDado tipoPassado = tiposPassado.get(indice);
                
                if (!tipoPassado.equals(TipoDado.INTEIRO))
                {
                    notificarErroSemantico(new ErroTipoParametroIncompativel(chamadaFuncao.getNome(), obterNomeParametro(chamadaFuncao, indice), chamadaFuncao.getParametros().get(indice), TipoDado.INTEIRO, tipoPassado));
                }
            }
        }
        if (chamadaFuncao.getNome().equals(FUNCAO_ESCREVA))
        {
            int tamanhoTiposPassado = tiposPassado.size();
            for (int indice = 0; indice < tamanhoTiposPassado; indice++)
            {
                TipoDado tipoPassado = tiposPassado.get(indice);
                
                if (tipoPassado == null)
                {
                    continue;
                }
                
                if (tipoPassado.equals(TipoDado.VAZIO))
                {
                    notificarErroSemantico(new ErroTipoParametroIncompativel(chamadaFuncao.getNome(), obterNomeParametro(chamadaFuncao, indice), chamadaFuncao.getParametros().get(indice), TipoDado.TODOS, tipoPassado));
                }
            }
        }

        for (int indice = 0; indice < cont; indice++)
        {
            TipoDado tipoPassado = tiposPassado.get(indice);
            TipoDado tipoEsperado = tiposEsperados.get(indice);
            ModoAcesso modoAcesso = modosAcesso.get(indice);

            if (tipoPassado != null)
            {
                try
                {
                    tabelaCompatibilidadeTipos.obterTipoRetornoPassagemParametro(tipoEsperado, tipoPassado);
                }
                catch (ExcecaoValorSeraConvertido excecao)
                {
                    if (modoAcesso == ModoAcesso.POR_REFERENCIA)
                    {
                        notificarErroSemantico(new ErroTipoParametroIncompativel(chamadaFuncao.getNome(), obterNomeParametro(chamadaFuncao, indice), chamadaFuncao.getParametros().get(indice), tipoEsperado, tipoPassado));
                    }
                    else
                    {
                        notificarAviso(new AvisoValorExpressaoSeraConvertido(chamadaFuncao.getParametros().get(indice).getTrechoCodigoFonte(), chamadaFuncao, tipoPassado, tipoEsperado, obterNomeParametro(chamadaFuncao, indice)));
                    }
                }
                catch (ExcecaoImpossivelDeterminarTipoDado excecao)
                {
                    notificarErroSemantico(new ErroTipoParametroIncompativel(chamadaFuncao.getNome(), obterNomeParametro(chamadaFuncao, indice), chamadaFuncao.getParametros().get(indice), tipoEsperado, tipoPassado));
                }
            }
        }
    }

    private String obterNomeParametro(NoChamadaFuncao chamadaFuncao, int indice)
    {
        if (chamadaFuncao.getEscopoBiblioteca() == null)
        {
            Funcao funcao = (Funcao) memoria.getSimbolo(chamadaFuncao.getNome());

            return funcao==null?"":funcao.getParametros().get(indice).getNome();
        }
        else
        {
            MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(chamadaFuncao.getEscopoBiblioteca());
            MetaDadosFuncao metaDadosFuncao = metaDadosBiblioteca.obterMetaDadosFuncoes().obter(chamadaFuncao.getNome());
            MetaDadosParametros metaDadosParametros = metaDadosFuncao.obterMetaDadosParametros();

            return metaDadosParametros.obter(indice).getNome();
        }
        //return "";
    }

    private List<TipoDado> obterTiposParametrosEsperados(NoChamadaFuncao chamadaFuncao)
    {
        List<TipoDado> tipos = new ArrayList<>();

        if (chamadaFuncao.getEscopoBiblioteca() == null)
        {
            if (!FUNCOES_RESERVADAS.contains(chamadaFuncao.getNome()))
            {
                Funcao funcao = (Funcao) memoria.getSimbolo(chamadaFuncao.getNome());
                List<NoDeclaracaoParametro> parametros = funcao.getParametros();

                if (parametros != null)
                {
                    for (NoDeclaracaoParametro parametro : parametros)
                    {
                        tipos.add(parametro.getTipoDado());
                    }
                }
            }
        }
        else
        {
            MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(chamadaFuncao.getEscopoBiblioteca());
            MetaDadosFuncao metaDadosFuncao = metaDadosBiblioteca.obterMetaDadosFuncoes().obter(chamadaFuncao.getNome());
            MetaDadosParametros metaDadosParametros = metaDadosFuncao.obterMetaDadosParametros();

            for (MetaDadosParametro metaDadosParametro : metaDadosParametros)
            {
                tipos.add(metaDadosParametro.getTipoDado());
            }
        }

        return tipos;
    }

    private List<TipoDado> obterTiposParametrosPassados(NoChamadaFuncao chamadaFuncao, List<ModoAcesso> modosAcesso) throws ExcecaoVisitaASA
    {
        List<TipoDado> tipos = new ArrayList<>();

        if (chamadaFuncao.getParametros() != null)
        {
            for (int indice = 0; indice < chamadaFuncao.getParametros().size(); indice++)
            {
                NoExpressao parametro = chamadaFuncao.getParametros().get(indice);

                if (chamadaFuncao.getEscopoBiblioteca() == null && FUNCOES_RESERVADAS.contains(chamadaFuncao.getNome()))
                {
                    passandoReferencia = false;
                }
                else if (indice < modosAcesso.size())
                {
                    passandoReferencia = modosAcesso.get(indice) == ModoAcesso.POR_REFERENCIA;
                }
                else
                {
                    passandoReferencia = false;
                }

                try
                {
                    if (parametro instanceof NoReferenciaVariavel && chamadaFuncao.getNome().equals(FUNCAO_LEIA))
                    {
                        String nome = ((NoReferenciaVariavel) parametro).getNome();

                        Simbolo variavel = memoria.getSimbolo(nome);
                        if(variavel == null)
                        {
                            notificarErroSemantico(new ErroSimboloNaoDeclarado((NoReferenciaVariavel)parametro));
                            return tipos;
                        }
                        variavel.setInicializado(true);
                    }
                    passandoParametro = (chamadaFuncao.getEscopoBiblioteca() == null && !FUNCOES_RESERVADAS.contains(chamadaFuncao.getNome()));
                    tipos.add((TipoDado) parametro.aceitar(this));
                    passandoParametro = false;
                }
                catch (ExcecaoVisitaASA ex)
                {
                    if (ex.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado)
                    {
                        tipos.add(null);
                    }
                    else
                    {
                        throw ex;
                    }
                }

                passandoReferencia = false;
            }
        }

        return tipos;
    }

    private void verificarQuantidadeParametros(NoChamadaFuncao chamadaFuncao)
    {
        int esperados = obterNumeroParametrosEsperados(chamadaFuncao);
        int passados = (chamadaFuncao.getParametros() != null) ? chamadaFuncao.getParametros().size() : 0;

        //Funções como leia e escreva aceitam numeros infinitos de parametros, mas não nenhum.
        if ((esperados == Integer.MAX_VALUE && passados == 0) || (esperados != Integer.MAX_VALUE && passados != esperados))
        {
            notificarErroSemantico(new ErroNumeroParametrosFuncao(passados, esperados, chamadaFuncao));
        }
    }

    private int obterNumeroParametrosEsperados(NoChamadaFuncao chamadaFuncao)
    {
        if (chamadaFuncao.getEscopoBiblioteca() == null)
        {
            if (FUNCOES_RESERVADAS.contains(chamadaFuncao.getNome()))
            {
                if (chamadaFuncao.getNome().equals(FUNCAO_LIMPA))
                {
                    return 0;
                }else if(chamadaFuncao.getNome().equals(FUNCAO_ALEATORIO)){
                    return 2;
                }
                else
                {
                    return Integer.MAX_VALUE;
                }
            }

            Funcao funcao = (Funcao) memoria.getSimbolo(chamadaFuncao.getNome());
            List<NoDeclaracaoParametro> parametros = funcao.getParametros();

            if (parametros != null)
            {
                return parametros.size();
            }
            else
            {
                return 0;
            }
        }
        else
        {
            MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(chamadaFuncao.getEscopoBiblioteca());
            MetaDadosFuncao metaDadosFuncao = metaDadosBiblioteca.obterMetaDadosFuncoes().obter(chamadaFuncao.getNome());

            return metaDadosFuncao.obterMetaDadosParametros().quantidade();
        }
    }

    private void verificarFuncaoExiste(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
    {
        if (chamadaFuncao.getEscopoBiblioteca() == null)
        {
            if (!FUNCOES_RESERVADAS.contains(chamadaFuncao.getNome()))
            {
                Simbolo simbolo = memoria.getSimbolo(chamadaFuncao.getNome());
                if (simbolo != null) {
                    if (!(simbolo instanceof Funcao))
                    {
                        notificarErroSemantico(new ErroReferenciaInvalida(chamadaFuncao, simbolo));
                        throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, chamadaFuncao);
                    }
                    else
                    {
                        simbolo.getOrigemDoSimbolo().adicionarReferencia(chamadaFuncao);
                    }
                }
                else // (ExcecaoSimboloNaoDeclarado ex)
                {
                    notificarErroSemantico(new ErroSimboloNaoDeclarado(chamadaFuncao));
                    throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, chamadaFuncao);
                }
            }
        }
        else
        {
            MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(chamadaFuncao.getEscopoBiblioteca());

            if (metaDadosBiblioteca != null)
            {
                MetaDadosFuncao metaDadosFuncao = metaDadosBiblioteca.obterMetaDadosFuncoes().obter(chamadaFuncao.getNome());
                
                if (metaDadosFuncao == null)
                {
                    notificarErroSemantico(new ErroSimboloNaoDeclarado(chamadaFuncao));
                    throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, chamadaFuncao);
                }
                else
                {
                    chamadaFuncao.setFuncaoDeBiblioteca(true);
                    chamadaFuncao.setTipoRetornoBiblioteca(metaDadosFuncao.getTipoDado());
                }
            }
            else
            {
                notificarErroSemantico(new ErroInclusaoBiblioteca(chamadaFuncao.getTrechoCodigoFonteNome(), new Exception(String.format("A biblioteca '%s' não foi incluída no programa", chamadaFuncao.getEscopoBiblioteca()))));
                throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, chamadaFuncao);
            }
        }
    }

    private void verificarRetornoFuncao(NoDeclaracaoFuncao noDeclaracaoFuncao) throws ExcecaoVisitaASA
    {
        if (noDeclaracaoFuncao.getTipoDado() != TipoDado.VAZIO)
        {
            AnalisadorRetornoDeFuncao analisador = new AnalisadorRetornoDeFuncao();

            if (!analisador.possuiRetornoObrigatorio(noDeclaracaoFuncao))
            {
                notificarErroSemantico(new ErroFuncaoSemRetorne(noDeclaracaoFuncao));
            }

        }
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(declaracaoFuncao);
        
        NoBloco blocoAtualAnterior = blocoAtual;
        blocoAtual = declaracaoFuncao;
        
        //if (declarandoSimbolosGlobais)
        //{
//            if (FUNCOES_RESERVADAS.contains(declaracaoFuncao.getNome())){
//                notificarErroSemantico(new ErroSemantico(declaracaoFuncao.getTrechoCodigoFonteNome()) {
//                    @Override
//                    protected String construirMensagem() {
//                        return "A função "+declaracaoFuncao.getNome()+" é reservada para a linguagem";
//                    }
//                });
//                String nome = declaracaoFuncao.getNome();
//                TipoDado tipoDado = declaracaoFuncao.getTipoDado();
//                Quantificador quantificador = declaracaoFuncao.getQuantificador();
//                Funcao funcao = new Funcao(nome, tipoDado, quantificador, declaracaoFuncao.getParametros(), declaracaoFuncao);
//                funcao.setTrechoCodigoFonteNome(declaracaoFuncao.getTrechoCodigoFonteNome());
//                funcao.setTrechoCodigoFonteTipoDado(declaracaoFuncao.getTrechoCodigoFonteTipoDado());
//                funcao.setRedeclarado(true);
//                memoria.adicionarSimbolo(funcao);
//                return null;
//            }
            
//            String nome = declaracaoFuncao.getNome();
//            TipoDado tipoDado = declaracaoFuncao.getTipoDado();
//            Quantificador quantificador = declaracaoFuncao.getQuantificador();
//
//            Funcao funcao = new Funcao(nome, tipoDado, quantificador, declaracaoFuncao.getParametros(), declaracaoFuncao);
//            funcao.setTrechoCodigoFonteNome(declaracaoFuncao.getTrechoCodigoFonteNome());
//            funcao.setTrechoCodigoFonteTipoDado(declaracaoFuncao.getTrechoCodigoFonteTipoDado());
            
//            
//            Simbolo simbolo = memoria.getSimbolo(nome);
//            if (simbolo != null) {
//                notificarErroSemantico(new ErroSimboloRedeclarado(funcao, simbolo));
//                funcao.setRedeclarado(true);
//            }
//            else
//            {
//                memoria.adicionarSimbolo(funcao);
//            }
//            
//            if (funcao.getNome().equals("inicio") && !funcao.getParametros().isEmpty())
//            {
//                notificarErroSemantico(new ErroFuncaoInicioNaoAceitaParametros(declaracaoFuncao));
//            }
//            
//            
//        }
//        else
//        {
//            
            funcaoAtual = (Funcao) memoria.getSimbolo(declaracaoFuncao.getNome());
            if (funcaoAtual != null) {
                memoria.empilharFuncao();
                List<NoDeclaracaoParametro> parametros = declaracaoFuncao.getParametros();
                for (NoDeclaracaoParametro noDeclaracaoParametro : parametros)
                {
                    noDeclaracaoParametro.aceitar(this);
                }
                analisarListaBlocos(declaracaoFuncao.getBlocos());
                verificarRetornoFuncao(declaracaoFuncao);
            }
            else {
                throw new ExcecaoVisitaASA(new ExcecaoSimboloNaoDeclarado(declaracaoFuncao.getNome()), asa, declaracaoFuncao);
            }

            try
            {
                memoria.desempilharFuncao();
            }
            catch (EmptyStackException e)
            {
                // esta excessão ocorre quando a ClassCastException acima também acontece (função com mesmo nome de variável global). 
                // Nesses casos a função não chega a ser empilhada (porque o nome dela coincide com uma variável) e então a pilha
                // está vazia, gerando uma EmptyStackException quando se tenta 'desempilhar'.
                // Estamos 'engolindo' a excessão aqui apenas para evitar que a stack trace seja mostrada na console do PS.
            }
            
//        }
        
        blocoAtual = blocoAtualAnterior;

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noDeclaracaoMatriz);
        
        noDeclaracaoMatriz.setIdParaInspecao(totalMatrizesDeclaradas);
        totalMatrizesDeclaradas++;
        
//        if (declarandoSimbolosGlobais == memoria.isEscopoGlobal())
//        {
            String nome = noDeclaracaoMatriz.getNome();
            TipoDado tipoDados = noDeclaracaoMatriz.getTipoDado();
            Integer linhas = obterTamanhoVetorMatriz(noDeclaracaoMatriz.getNumeroLinhas(), noDeclaracaoMatriz);
            Integer colunas = obterTamanhoVetorMatriz(noDeclaracaoMatriz.getNumeroColunas(), noDeclaracaoMatriz);

            if (linhas != null && colunas != null)
            {

                BigInteger bigLinhas = new BigInteger(linhas.toString());
                BigInteger bigColunas = new BigInteger(colunas.toString());
                BigInteger bigMax = new BigInteger(Matriz.TAMANHO_MAXIMO.toString());
                BigInteger bigProduto = bigLinhas.multiply(bigColunas);

                if (bigProduto.compareTo(bigMax) > 0)
                {
                    notificarErroSemantico(
                            new ErroTamanhoMaximoMatriz(linhas, colunas, nome, bigProduto,
                            noDeclaracaoMatriz.getTrechoCodigoFonteNome()));
                }
                if(linhas == 1 && colunas == 1)
                {
                    notificarAviso(new AvisoMatrizPodeSerVariavel(noDeclaracaoMatriz, linhas));
                }
                else if(linhas == 1 || colunas == 1)
                {
                    notificarAviso(new AvisoMatrizPodeSerVetor(noDeclaracaoMatriz, linhas>colunas ? linhas : colunas, linhas, colunas));
                }
            }            

            Matriz matriz = new Matriz(nome, tipoDados, noDeclaracaoMatriz, 1, 1);
            matriz.setTrechoCodigoFonteNome(noDeclaracaoMatriz.getTrechoCodigoFonteNome());
            matriz.setTrechoCodigoFonteTipoDado(noDeclaracaoMatriz.getTrechoCodigoFonteTipoDado());

            Simbolo simbolo = memoria.getSimbolo(nome);
            if (simbolo != null) {                
                final boolean global = memoria.isGlobal(simbolo);
                final boolean local = memoria.isLocal(simbolo);
                memoria.empilharEscopo();
                memoria.adicionarSimbolo(matriz);
                final boolean global1 = memoria.isGlobal(matriz);
                final boolean local1 = memoria.isLocal(matriz);
                if ((global && global1) || (local && local1))
                {
                    matriz.setRedeclarado(true);
                    notificarErroSemantico(new ErroSimboloRedeclarado(matriz, simbolo));
                    memoria.desempilharEscopo();
                }
                else
                {
                    memoria.desempilharEscopo();
                    memoria.adicionarSimbolo(matriz);
                    Simbolo simboloGlobal = memoria.isGlobal(simbolo) ? simbolo : matriz;
                    Simbolo simboloLocal = memoria.isGlobal(simbolo) ? matriz : simbolo;

                    notificarAviso(new AvisoSimboloGlobalOcultado(simboloGlobal, simboloLocal, noDeclaracaoMatriz));
                }

            }
            else// (ExcecaoSimboloNaoDeclarado excecaoSimboloNaoDeclarado)
            {
                if (FUNCOES_RESERVADAS.contains(nome))
                {
                    matriz.setRedeclarado(true);
                    Funcao funcaoSistam = new Funcao(nome, TipoDado.VAZIO, Quantificador.VETOR);
                    notificarErroSemantico(new ErroSimboloRedeclarado(matriz, funcaoSistam));
                }
                else
                {
                    memoria.adicionarSimbolo(matriz);
                }
            }

            if (noDeclaracaoMatriz.constante() && noDeclaracaoMatriz.getInicializacao() == null)
            {
                NoReferenciaVariavel referencia = new NoReferenciaVariavel(null, nome);
                referencia.setTrechoCodigoFonteNome(noDeclaracaoMatriz.getTrechoCodigoFonteNome());

                notificarErroSemantico(new ErroSimboloNaoInicializado(referencia, matriz));
            }

            if (noDeclaracaoMatriz.getInicializacao() != null)
            {
                if (noDeclaracaoMatriz.getInicializacao() instanceof NoMatriz)
                {
                    NoExpressao inicializacao = noDeclaracaoMatriz.getInicializacao();
                    NoReferenciaVariavel referencia = new NoReferenciaVariavel(null, nome);
                    referencia.setTrechoCodigoFonteNome(noDeclaracaoMatriz.getTrechoCodigoFonteNome());
                    NoOperacao operacao = new NoOperacaoAtribuicao(referencia, inicializacao);

                    if (linhas != null)
                    {
                        int numeroLinhasDeclaradas = ((NoMatriz) inicializacao).getValores().size();

                        if (linhas != numeroLinhasDeclaradas)
                        {
                            notificarErroSemantico(
                                    new ErroQuantidadeLinhasIncializacaoMatriz(
                                            noDeclaracaoMatriz.getInicializacao().getTrechoCodigoFonte(),
                                            nome, linhas, numeroLinhasDeclaradas));
                        }
                    }

                    if (colunas != null)
                    {
                        for (int linha = 0; linha < ((NoMatriz) inicializacao).getValores().size(); linha++)
                        {
                            List<List<Object>> valores = ((NoMatriz) noDeclaracaoMatriz.getInicializacao()).getValores();

                            if (colunas != valores.get(linha).size())
                            {
                                notificarErroSemantico(
                                        new ErroQuantidadeElementosColunaInicializacaoMatriz(
                                                noDeclaracaoMatriz.getInicializacao().getTrechoCodigoFonte(),
                                                nome, linha, colunas, valores.get(linha).size()));
                            }
                        }
                    }

                    if (noDeclaracaoMatriz.constante() && linhas != null && colunas != null)
                    {
                        List<List<Object>> valores = ((NoMatriz) noDeclaracaoMatriz.getInicializacao()).getValores();

                        for (int linha = 0; linha < valores.size(); linha++)
                        {
                            for (int coluna = 0; coluna < valores.get(linha).size(); coluna++)
                            {
                                if (!(valores.get(linha).get(coluna) instanceof NoExpressaoLiteral))
                                {
                                    notificarErroSemantico(
                                            new ErroInicializacaoConstante(noDeclaracaoMatriz, linha, coluna));
                                }
                            }
                        }
                    }

                    try
                    {
                        this.declarandoMatriz = true;
                        operacao.aceitar(this);
                        this.declarandoMatriz = false;
                    }
                    catch (ExcecaoVisitaASA excecao)
                    {
                        if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
                        {
                            throw excecao;
                        }
                    }
                }
                else
                {
                    if (linhas == null)
                    {
                        linhas = 0;
                    }

                    if (colunas == null)
                    {
                        colunas = 0;
                    }

                    notificarErroSemantico(
                            new ErroAoInicializarMatriz(
                                    matriz, noDeclaracaoMatriz.getInicializacao().getTrechoCodigoFonte(),
                                    linhas, colunas));
                }
            }

            matriz.setConstante(noDeclaracaoMatriz.constante());
//        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel declaracaoVariavel) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(declaracaoVariavel);
        
        declaracaoVariavel.setIdParaInspecao(totalVariaveisDeclaradas);
        //System.out.println(declaracaoVariavel.getNome() + " => " + totalVariaveisDeclaradas);
        totalVariaveisDeclaradas++;
        
//        if (declarandoSimbolosGlobais == memoria.isEscopoGlobal())
//        {
            String nome = declaracaoVariavel.getNome();
            TipoDado tipoDadoVariavel = declaracaoVariavel.getTipoDado();
            
            Variavel variavel = new Variavel(nome, tipoDadoVariavel, declaracaoVariavel);
            variavel.setTrechoCodigoFonteNome(declaracaoVariavel.getTrechoCodigoFonteNome());
            variavel.setTrechoCodigoFonteTipoDado(declaracaoVariavel.getTrechoCodigoFonteTipoDado());

            Simbolo simbolo = memoria.getSimbolo(nome);
            if (simbolo != null) {
                
                final boolean global = memoria.isGlobal(simbolo);
                final boolean local = memoria.isLocal(simbolo);
                memoria.empilharEscopo();
                memoria.adicionarSimbolo(variavel);
                final boolean global1 = memoria.isGlobal(variavel);
                final boolean local1 = memoria.isLocal(variavel);
                if ((global && global1) || (local && local1))
                {
                    variavel.setRedeclarado(true);
                    notificarErroSemantico(new ErroSimboloRedeclarado(variavel, simbolo));
                    memoria.desempilharEscopo();
                }
                else
                {
                    memoria.desempilharEscopo();
                    memoria.adicionarSimbolo(variavel);
                    Simbolo simboloGlobal = memoria.isGlobal(simbolo) ? simbolo : variavel;
                    Simbolo simboloLocal = memoria.isGlobal(simbolo) ? variavel : simbolo;

                    notificarAviso(new AvisoSimboloGlobalOcultado(simboloGlobal, simboloLocal, declaracaoVariavel));
                }
            }
            else// (ExcecaoSimboloNaoDeclarado excecaoSimboloNaoDeclarado)
            {
                if (FUNCOES_RESERVADAS.contains(nome))
                {
                    variavel.setRedeclarado(true);
                    Funcao funcaoSistam = new Funcao(nome, TipoDado.VAZIO, Quantificador.VETOR);
                    notificarErroSemantico(new ErroSimboloRedeclarado(variavel, funcaoSistam));
                }
                else
                {
                    memoria.adicionarSimbolo(variavel);
                }
            }

            if (declaracaoVariavel.constante() && declaracaoVariavel.getInicializacao() == null)
            {
                NoReferenciaVariavel referencia = new NoReferenciaVariavel(null, nome);
                referencia.setTrechoCodigoFonteNome(declaracaoVariavel.getTrechoCodigoFonteNome());

                notificarErroSemantico(new ErroSimboloNaoInicializado(referencia, variavel));
            }

            if (declaracaoVariavel.getInicializacao() != null)
            {
                // Posteriormente restringir na gramática para não permitir atribuir vetor ou matriz a uma variável comum

                if (!(declaracaoVariavel.getInicializacao() instanceof NoVetor) &&
                    !(declaracaoVariavel.getInicializacao() instanceof NoMatriz))
                {
                    NoExpressao inicializacao = declaracaoVariavel.getInicializacao();
                    NoReferenciaVariavel referencia = new NoReferenciaVariavel(null, nome);
                    referencia.setTrechoCodigoFonteNome(declaracaoVariavel.getTrechoCodigoFonteNome());
                    NoOperacao operacao = new NoOperacaoAtribuicao(referencia, inicializacao);

                    memoria.empilharEscopo();
                    memoria.adicionarSimbolo(variavel);

                    if (declaracaoVariavel.constante())
                    {
                        if (inicializacao instanceof NoMenosUnario)
                        {
                            if (!(((NoMenosUnario) inicializacao).getExpressao() instanceof NoExpressaoLiteral))
                            {
                                notificarErroSemantico(new ErroInicializacaoConstante(declaracaoVariavel));
                            }
                        }
                        else if (!(inicializacao instanceof NoExpressaoLiteral))
                        {
                            notificarErroSemantico(new ErroInicializacaoConstante(declaracaoVariavel));
                        }
                    }

                    try
                    {
                        operacao.aceitar(this);
                    }
                    catch (ExcecaoVisitaASA excecao)
                    {
                        if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
                        {
                            throw excecao;
                        }
                    }

                    memoria.desempilharEscopo();
                }
                else
                {
                    notificarErroSemantico(new ErroInicializacaoInvalida(declaracaoVariavel));
                }
            }

            variavel.setConstante(declaracaoVariavel.constante());

//        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noDeclaracaoVetor);
        
        noDeclaracaoVetor.setIdParaInspecao(totalVetoresDeclarados);
        totalVetoresDeclarados++;
        
//        if (declarandoSimbolosGlobais == memoria.isEscopoGlobal()) {
            String nome = noDeclaracaoVetor.getNome();
            TipoDado tipoDados = noDeclaracaoVetor.getTipoDado();
            NoExpressao expTamanho = noDeclaracaoVetor.getTamanho();

            Integer tamanho = obterTamanhoVetorMatriz(expTamanho, noDeclaracaoVetor);

            if (tamanho != null)
            {
                if (tamanho > Vetor.TAMANHO_MAXIMO)
                {
                    notificarErroSemantico(
                            new ErroTamanhoMaximoVetor(tamanho, nome, noDeclaracaoVetor.getTrechoCodigoFonteNome()));
                }
                if(tamanho == 1)
                {
                    notificarAviso(new AvisoVetorPodeSerVariavel(noDeclaracaoVetor, tamanho));
                }
            }
            Vetor vetor = new Vetor(nome, tipoDados, noDeclaracaoVetor, 1);
            vetor.setTrechoCodigoFonteNome(noDeclaracaoVetor.getTrechoCodigoFonteNome());
            vetor.setTrechoCodigoFonteTipoDado(noDeclaracaoVetor.getTrechoCodigoFonteTipoDado());

            
            Simbolo simbolo = memoria.getSimbolo(nome);
            if (simbolo != null) {
                
                final boolean global = memoria.isGlobal(simbolo);
                final boolean local = memoria.isLocal(simbolo);
                memoria.empilharEscopo();
                memoria.adicionarSimbolo(vetor);
                final boolean global1 = memoria.isGlobal(vetor);
                final boolean local1 = memoria.isLocal(vetor);
                if ((global && global1) || (local && local1))
                {
                    vetor.setRedeclarado(true);
                    notificarErroSemantico(new ErroSimboloRedeclarado(vetor, simbolo));
                    memoria.desempilharEscopo();
                }
                else
                {
                    memoria.desempilharEscopo();
                    memoria.adicionarSimbolo(vetor);
                    Simbolo simboloGlobal = memoria.isGlobal(simbolo) ? simbolo : vetor;
                    Simbolo simboloLocal = memoria.isGlobal(simbolo) ? vetor : simbolo;

                    notificarAviso(new AvisoSimboloGlobalOcultado(simboloGlobal, simboloLocal, noDeclaracaoVetor));
                }
            }
            else // (ExcecaoSimboloNaoDeclarado excecaoSimboloNaoDeclarado)
            {
                if (FUNCOES_RESERVADAS.contains(nome))
                {
                    vetor.setRedeclarado(true);
                    Funcao funcaoSistam = new Funcao(nome, TipoDado.VAZIO, Quantificador.VETOR);
                    notificarErroSemantico(new ErroSimboloRedeclarado(vetor, funcaoSistam));
                }
                else
                {
                    memoria.adicionarSimbolo(vetor);
                }
            }

            if (noDeclaracaoVetor.constante() && noDeclaracaoVetor.getInicializacao() == null)
            {
                NoReferenciaVariavel referencia = new NoReferenciaVariavel(null, nome);
                referencia.setTrechoCodigoFonteNome(noDeclaracaoVetor.getTrechoCodigoFonteNome());

                notificarErroSemantico(new ErroSimboloNaoInicializado(referencia, vetor));
            }

            if (noDeclaracaoVetor.getInicializacao() != null)
            {
                if (noDeclaracaoVetor.getInicializacao() instanceof NoVetor)
                {
                    NoExpressao inicializacao = noDeclaracaoVetor.getInicializacao();
                    NoReferenciaVariavel referencia = new NoReferenciaVariavel(null, nome);
                    referencia.setTrechoCodigoFonteNome(noDeclaracaoVetor.getTrechoCodigoFonteNome());
                    NoOperacao operacao = new NoOperacaoAtribuicao(referencia, inicializacao);

                    if (tamanho != null)
                    {
                        int numeroElementosDeclarados = ((NoVetor) inicializacao).getValores().size();

                        if (tamanho != numeroElementosDeclarados)
                        {
                            notificarErroSemantico(new ErroQuantidadeElementosInicializacaoVetor(noDeclaracaoVetor.getInicializacao().getTrechoCodigoFonte(), nome, tamanho, numeroElementosDeclarados));
                        }

                        if (noDeclaracaoVetor.constante())
                        {
                            NoVetor noVetor = (NoVetor) inicializacao;

                            for (int indice = 0; indice < noVetor.getValores().size(); indice++)
                            {
                                if (!(noVetor.getValores().get(indice) instanceof NoExpressaoLiteral))
                                {
                                    notificarErroSemantico(new ErroInicializacaoConstante(noDeclaracaoVetor, indice));
                                }
                            }
                        }
                    }

                    try
                    {
                        this.declarandoVetor = true;
                        operacao.aceitar(this);
                        this.declarandoVetor = false;
                    }
                    catch (ExcecaoVisitaASA excecao)
                    {
                        if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
                        {
                            throw excecao;
                        }
                    }
                }
                else
                {
                    if (tamanho == null)
                    {
                        tamanho = 0;
                    }

                    notificarErroSemantico(new ErroAoInicializarVetor(vetor, noDeclaracaoVetor.getInicializacao().getTrechoCodigoFonte(), tamanho));
                }
            }

            vetor.setConstante(noDeclaracaoVetor.constante());
//        }

        return null;

    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noEnquanto);
        
        NoBloco blocoAtualAnterior = blocoAtual;
        blocoAtual = noEnquanto;
        
        TipoDado tipoDadoCondicao = (TipoDado) noEnquanto.getCondicao().aceitar(this);

        if (tipoDadoCondicao != TipoDado.LOGICO)
        {
            notificarErroSemantico(new ErroTiposIncompativeis(noEnquanto, tipoDadoCondicao));
        }

        analisarListaBlocos(noEnquanto.getBlocos());

        blocoAtual = blocoAtualAnterior;
        
        return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noEscolha);
        
        NoBloco blocoAtualAnterior = blocoAtual;
        blocoAtual = noEscolha;
        
        tipoDadoEscolha.push((TipoDado) noEscolha.getExpressao().aceitar(this));

        if ((tipoDadoEscolha.peek() != TipoDado.INTEIRO) && (tipoDadoEscolha.peek() != TipoDado.CARACTER))
        {
            notificarErroSemantico(new ErroTiposIncompativeis(noEscolha, tipoDadoEscolha.peek(), TipoDado.INTEIRO, TipoDado.CARACTER));
        }
        List<NoCaso> casos = noEscolha.getCasos();
        for (NoCaso noCaso : casos)
        {
            noCaso.aceitar(this);
        }
        
        tipoDadoEscolha.pop();
        
        blocoAtual = blocoAtualAnterior;
        
        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noFacaEnquanto);
        
        NoBloco blocoAtualAnterior = blocoAtual;
        blocoAtual = noFacaEnquanto;
        
        analisarListaBlocos(noFacaEnquanto.getBlocos());

        TipoDado tipoDadoCondicao = (TipoDado) noFacaEnquanto.getCondicao().aceitar(this);

        if (tipoDadoCondicao != TipoDado.LOGICO)
        {
            notificarErroSemantico(new ErroTiposIncompativeis(noFacaEnquanto, tipoDadoCondicao));
        }

        blocoAtual = blocoAtualAnterior;
        
        return null;
    }

    @Override
    public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noInteiro);
        return TipoDado.INTEIRO;
    }

    @Override
    public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noLogico);
        return TipoDado.LOGICO;
    }

    @Override
    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noMatriz);
        List<List<Object>> valores = noMatriz.getValores();

        if (valores != null && !valores.isEmpty())
        {

            try
            {
                TipoDado tipoMatriz = (TipoDado) ((NoExpressao) valores.get(0).get(0)).aceitar(this);
                for (List<Object> valList : valores)
                {
                    for (int i = 0; i < valList.size(); i++)
                    {
                        TipoDado tipoDadoElemento = (TipoDado) ((NoExpressao) valList.get(i)).aceitar(this);

                        if (tipoMatriz != tipoDadoElemento)
                        {
                            notificarErroSemantico(new ErroDefinirTipoDadoMatrizLiteral(noMatriz.getTrechoCodigoFonte()));

                            throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noMatriz);
                        }
                    }
                }
                return tipoMatriz;
            }
            catch (Exception excecao)
            {
                //excecao.printStackTrace(System.out);
                throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noMatriz);
            }

        }
        else
        {

            notificarErroSemantico(new ErroInicializacaoMatrizEmBranco(noMatriz.getTrechoCodigoFonte()));

            throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noMatriz);
        }
    }

    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noMenosUnario);
        
        TipoDado tipo = (TipoDado) noMenosUnario.getExpressao().aceitar(this);
        if (!tipo.equals(TipoDado.INTEIRO) && !tipo.equals(TipoDado.REAL))
        {
            notificarErroSemantico(new ErroTiposIncompativeis(noMenosUnario, tipo, TipoDado.INTEIRO, TipoDado.REAL));
            throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noMenosUnario);
        }

        return tipo;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noNao);
        
        TipoDado tipo = (TipoDado) noNao.getExpressao().aceitar(this);
        if (tipo != TipoDado.LOGICO)
        {
            notificarErroSemantico(new ErroTiposIncompativeis(noNao, tipo, TipoDado.LOGICO));
            throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noNao);
        }
        return tipo;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(final NoOperacaoAtribuicao noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        
        TipoDado tipoDadoRetorno;

        TipoDado operandoEsquerdo = null;
        TipoDado operandoDireito = null;

        Simbolo simbolo = null;
        boolean inicializadoAnterior = false;
        if (!(noOperacao.getOperandoEsquerdo() instanceof NoReferencia))
        {
            notificarErroSemantico(new ErroAtribuirEmExpressao(noOperacao, noOperacao.getOperandoEsquerdo()));
        }
        else
        {
            if (noOperacao.getOperandoEsquerdo() instanceof NoReferenciaVariavel)
            {
                final NoReferenciaVariavel referencia = (NoReferenciaVariavel) noOperacao.getOperandoEsquerdo();

                if (referencia.getEscopoBiblioteca() == null)
                {
                    simbolo = memoria.getSimbolo(referencia.getNome());
                    
                    if (simbolo != null) {
                        inicializadoAnterior = simbolo.inicializado();
                        simbolo.setInicializado(true);
                    }
                    
                    if (simbolo instanceof Variavel)
                    {

                        if (simbolo.constante())
                        {
                            final Simbolo pSimbolo = simbolo;
                            notificarErroSemantico(new ErroAtribuirEmConstante(noOperacao.getOperandoEsquerdo().getTrechoCodigoFonte(), pSimbolo));
                        }

                        if ((noOperacao.getOperandoDireito() instanceof NoMatriz)
                                || (noOperacao.getOperandoDireito() instanceof NoVetor))
                        {
                            notificarErroSemantico(new ErroAtribuirMatrizVetorEmVariavel(noOperacao.getOperandoDireito().getTrechoCodigoFonte()));
                        }
                    }
                    else if (simbolo instanceof Vetor)
                    {
                        if (!(noOperacao.getOperandoDireito() instanceof NoVetor))
                        {
                            if (declarandoVetor)
                            {
                                notificarErroSemantico(new ErroAoInicializarVetor((Vetor) simbolo, noOperacao.getOperandoDireito().getTrechoCodigoFonte(), ((Vetor) simbolo).getTamanho()));
                            }
                        }
                    }
                    else if (simbolo instanceof Matriz)
                    {
                        if (!simbolo.inicializado() && !(noOperacao.getOperandoDireito() instanceof NoMatriz))
                        {
                            notificarErroSemantico(new ErroAoInicializarMatriz((Matriz) simbolo, noOperacao.getOperandoDireito().getTrechoCodigoFonte(), ((Matriz) simbolo).getNumeroLinhas(), ((Matriz) simbolo).getNumeroColunas()));
                        }
                    }
                }
                else
                {
                    /* O escopo pode retornar o nome real da biblioteca ou o alias que o usuário definiu.
                     *                          * 
                     * Como o alias é dinâmico, o gerenciador de bibliotecas não consegue recuperar a
                     * biblioteca a partir dele. Por isso, o método obterMetaDadosBiblioteca() só pode
                     * ser utilizado com o nome real da biblioteca.
                     * 
                     * Para resolver isto, o semântico faz um mapeamento interno das biblitecas. Ao incluir 
                     * a biblioteca ele cria uma chave no mapa, tanto para o nome real da biblioteca, quanto
                     * para o alias do usuário.
                     * 
                     * Por isso, ao trabalhar com as bibliotecas dentro do semântico, deve-se sempre utilizar
                     * o mapa interno, caso contrário vai dar NullPointerException.
                     */
                    final MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(referencia.getEscopoBiblioteca());
                    if(metaDadosBiblioteca == null)
                    {
                        notificarErroSemantico(new ErroAliasInexistente(noOperacao.getOperandoEsquerdo().getTrechoCodigoFonte(), referencia.getEscopoBiblioteca()));
                    }
                    else
                    {
                        final MetaDadosConstante metaDadosConstante = metaDadosBiblioteca.getMetaDadosConstantes().obter(referencia.getNome());
                        if(metaDadosConstante == null)
                        {
                            notificarErroSemantico(new ErroAtribuirFuncaoBiblioteca(noOperacao.getOperandoEsquerdo().getTrechoCodigoFonte(), metaDadosBiblioteca));
                        }
                        else
                        {
                            notificarErroSemantico(new ErroAtribuirConstanteBiblioteca(noOperacao.getOperandoEsquerdo().getTrechoCodigoFonte(), metaDadosConstante, metaDadosBiblioteca));                                
                        }
                    }
                }
            }
            else if (noOperacao.getOperandoEsquerdo() instanceof NoReferenciaMatriz
                    || noOperacao.getOperandoEsquerdo() instanceof NoReferenciaVetor)
            {
                simbolo = memoria.getSimbolo(((NoReferencia) noOperacao.getOperandoEsquerdo()).getNome());
                
                if(simbolo==null)
                {
                    notificarErroSemantico(new ErroSimboloNaoDeclarado((NoReferencia) noOperacao.getOperandoEsquerdo()));
                }
                else if (simbolo.constante())
                {
                    final Simbolo pSimbolo = simbolo;
                    notificarErroSemantico(new ErroAtribuirEmConstante(noOperacao.getTrechoCodigoFonte(), pSimbolo));
                }

                if (noOperacao.getOperandoDireito() instanceof NoVetor)
                {
                    notificarErroSemantico(new ErroAoAtribuirEmVetor(noOperacao.getTrechoCodigoFonte()));
                }
                else if (noOperacao.getOperandoDireito() instanceof NoMatriz)
                {
                    notificarErroSemantico(new ErroAoAtribuirEmMatriz(noOperacao.getOperandoDireito().getTrechoCodigoFonte()));
                }
            }
            else if (noOperacao.getOperandoEsquerdo() instanceof NoChamadaFuncao)
            {
                notificarErroSemantico(new ErroAtribuirEmChamadaFuncao(noOperacao.getTrechoCodigoFonte()));
            }

        }

        try
        {
            operandoEsquerdo = (TipoDado) noOperacao.getOperandoEsquerdo().aceitar(this);
        }
        catch (ExcecaoVisitaASA excecao)
        {
            if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
            {
                throw excecao;
            }
        }

        if (simbolo != null)
        {
            simbolo.setInicializado(inicializadoAnterior);
        }

        try
        {
            operandoDireito = (TipoDado) noOperacao.getOperandoDireito().aceitar(this);
        }
        catch (ExcecaoVisitaASA excecao)
        {
            if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
            {
                throw excecao;
            }
        }

        if (operandoEsquerdo != null && operandoDireito != null)
        {
            try
            {
                tipoDadoRetorno = tabelaCompatibilidadeTipos.obterTipoRetornoOperacao(noOperacao.getClass(), operandoEsquerdo, operandoDireito);
            }
            catch (ExcecaoValorSeraConvertido excecao)
            {
                notificarAviso(new AvisoValorExpressaoSeraConvertido(noOperacao.getOperandoDireito().getTrechoCodigoFonte(), noOperacao, excecao.getTipoEntrada(), excecao.getTipoSaida()));

                tipoDadoRetorno = excecao.getTipoSaida();
            }
            catch (ExcecaoImpossivelDeterminarTipoDado excecao)
            {
                notificarErroSemantico(new ErroTiposIncompativeis(noOperacao, operandoEsquerdo, operandoDireito));

                throw new ExcecaoVisitaASA(excecao, asa, noOperacao);
            }
        }
        else
        {
            throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noOperacao);
        }

        if (simbolo != null)
        {
            simbolo.setInicializado(true);
        }

        return tipoDadoRetorno;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacao);
        return recuperaTipoNoOperacao(noOperacao);
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noOperacaoBitwiseNao);
        
        TipoDado tipo = (TipoDado) noOperacaoBitwiseNao.getExpressao().aceitar(this);
        if (tipo != TipoDado.INTEIRO)
        {
            notificarErroSemantico(new ErroTiposIncompativeis(noOperacaoBitwiseNao, tipo, TipoDado.LOGICO));
            throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noOperacaoBitwiseNao);
        }
        return tipo;
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noPara);
        
        NoBloco blocoAtualAnterior = blocoAtual;
        blocoAtual = noPara;
        
        memoria.empilharEscopo();

        try
        {
            if (noPara.getInicializacoes() != null)
            {
                for (NoBloco inicializacao : noPara.getInicializacoes())
                {
                    if(inicializacao instanceof NoOperacaoAtribuicao || inicializacao instanceof NoDeclaracaoVariavel || inicializacao instanceof NoReferenciaVariavel)
                    {
                        inicializacao.aceitar(this);                        
                    }
                    else{
                        notificarErroSemantico(new ErroInicializacaoErrada(noPara));
                    }
                }
            }
        }
        catch (ExcecaoVisitaASA excecao)
        {
            if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
            {
                throw excecao;
            }
        }

        try
        {
            if (noPara.getCondicao() == null)
            {
                notificarErroSemantico(new ErroParaSemExpressaoComparacao(noPara.getTrechoCodigoFonte()));
            }
            else
            {
                TipoDado tipoDadoCondicao = (TipoDado) noPara.getCondicao().aceitar(this);

                if (tipoDadoCondicao != TipoDado.LOGICO)
                {
                    notificarErroSemantico(new ErroTiposIncompativeis(noPara, tipoDadoCondicao));
                }
            }
        }
        catch (ExcecaoVisitaASA excecao)
        {
            if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
            {
                throw excecao;
            }
        }

        try
        {
            if (noPara.getIncremento() instanceof NoOperacaoAtribuicao)
            {
                noPara.getIncremento().aceitar(this);
            }
            else if(noPara.getIncremento() != null)
            {
                notificarErroSemantico(new ErroParaSemExpressaoAtribuicao(noPara.getTrechoCodigoFonte()));
            }
        }
        catch (ExcecaoVisitaASA excecao)
        {
            if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
            {
                throw excecao;
            }
        }

        analisarListaBlocos(noPara.getBlocos());

        memoria.desempilharEscopo();

        blocoAtual = blocoAtualAnterior;
        
        return null;
    }

    @Override
    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noPare);
        return null;
    }

    @Override
    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noReal);
        return TipoDado.REAL;
    }

    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noReferenciaMatriz);
        
        try
        {
            TipoDado tipoLinha = (TipoDado) noReferenciaMatriz.getLinha().aceitar(this);
            TipoDado tipoColuna = (TipoDado) noReferenciaMatriz.getColuna().aceitar(this);

            if (tipoLinha != TipoDado.INTEIRO || tipoColuna != TipoDado.INTEIRO)
            {
                notificarErroSemantico(new ErroTiposIncompativeis(noReferenciaMatriz, tipoLinha, TipoDado.INTEIRO, tipoColuna, TipoDado.INTEIRO));
            }
        }
        catch (ExcecaoVisitaASA excecao)
        {
            if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
            {
                throw excecao;
            }
        }

        
        Simbolo simbolo = memoria.getSimbolo(noReferenciaMatriz.getNome());
        if (simbolo != null) {

            if (!(simbolo instanceof Matriz))
            {
                notificarErroSemantico(new ErroReferenciaInvalida(noReferenciaMatriz, simbolo));
            }
            else
            {
                ((Matriz) simbolo).getOrigemDoSimbolo().adicionarReferencia(noReferenciaMatriz);
            }

            return simbolo.getTipoDado();
        }
        else // (ExcecaoSimboloNaoDeclarado excecaoSimboloNaoDeclarado)
        {
            notificarErroSemantico(new ErroSimboloNaoDeclarado(noReferenciaMatriz));
        }

        throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noReferenciaMatriz);
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noReferenciaVariavel);
        
        if (noReferenciaVariavel.getEscopoBiblioteca() == null)
        {
            try
            {
                return analisarReferenciaVariavelPrograma(noReferenciaVariavel);
            }
            catch (ExcecaoImpossivelDeterminarTipoDado ex)
            {
                throw new ExcecaoVisitaASA(ex, asa, noReferenciaVariavel);
            }
        }
        else
        {
            return analisarReferenciaVariavelBiblioteca(noReferenciaVariavel);
        }
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noReferenciaVetor);
        
        try
        {
            TipoDado tipoIndice = (TipoDado) noReferenciaVetor.getIndice().aceitar(this);

            if (tipoIndice != TipoDado.INTEIRO)
            {
                notificarErroSemantico(new ErroTiposIncompativeis(noReferenciaVetor, tipoIndice, TipoDado.INTEIRO));
            }
        }
        catch (ExcecaoVisitaASA excecao)
        {
            if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
            {
                throw excecao;
            }
        }

        
        Simbolo simbolo = memoria.getSimbolo(noReferenciaVetor.getNome());
        if (simbolo != null) {

            if (!(simbolo instanceof Vetor))
            {
                notificarErroSemantico(new ErroReferenciaInvalida(noReferenciaVetor, simbolo));
            }
            else
            {
                ((Vetor) simbolo).getOrigemDoSimbolo().adicionarReferencia(noReferenciaVetor);
            }

            return simbolo.getTipoDado();
        }
        else // (ExcecaoSimboloNaoDeclarado excecaoSimboloNaoDeclarado)
        {
            notificarErroSemantico(new ErroSimboloNaoDeclarado(noReferenciaVetor));
        }

        throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noReferenciaVetor);
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
    {
        TipoDado tipoRetornoFuncao = TipoDado.VAZIO;

        if (noRetorne.getExpressao() != null)
        {
            TipoDado tipoExpressaoRetorno = TipoDado.VAZIO;

            try
            {
                noRetorne.setPai(funcaoAtual.getOrigemDoSimbolo());
                tipoExpressaoRetorno = (TipoDado) noRetorne.getExpressao().aceitar(this);
                tipoRetornoFuncao = tabelaCompatibilidadeTipos.obterTipoRetornoFuncao(funcaoAtual.getTipoDado(), tipoExpressaoRetorno);
            }
            catch (ExcecaoValorSeraConvertido e)
            {
                notificarAviso(new AvisoValorExpressaoSeraConvertido(noRetorne, e.getTipoEntrada(), e.getTipoSaida(), funcaoAtual.getNome()));
                tipoRetornoFuncao = e.getTipoSaida();
            }
            catch (ExcecaoImpossivelDeterminarTipoDado ex)
            {
                notificarErroSemantico(new ErroTiposIncompativeis(noRetorne, new String[]
                {
                    funcaoAtual.getNome()
                }, funcaoAtual.getTipoDado(), tipoExpressaoRetorno));
                throw new ExcecaoVisitaASA(ex, asa, noRetorne);
            }
        }else{
            if(tipoRetornoFuncao != funcaoAtual.getOrigemDoSimbolo().getTipoDado()){
                notificarErroSemantico(new ErroTiposIncompativeis(noRetorne, new String[]
                {
                    funcaoAtual.getNome()
                }, funcaoAtual.getTipoDado(), TipoDado.VAZIO));
            }
        }

        return tipoRetornoFuncao;
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noSe);
        
        NoBloco blocoAtualAnterior = blocoAtual;
        blocoAtual = noSe;
        
        TipoDado tipoDadoCondicao = (TipoDado) noSe.getCondicao().aceitar(this);

        if (tipoDadoCondicao != TipoDado.LOGICO)
        {
            notificarErroSemantico(new ErroTiposIncompativeis(noSe, tipoDadoCondicao));
        }

        analisarListaBlocos(noSe.getBlocosVerdadeiros());
        analisarListaBlocos(noSe.getBlocosFalsos());

        blocoAtual = blocoAtualAnterior;
        
        return null;
    }

    @Override
    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noVetor);
        
        List<NoExpressao> valores = (List) noVetor.getValores();

        if (valores != null && !valores.isEmpty())
        {
            try
            {
                TipoDado tipoDadoVetor = (TipoDado) ((NoExpressao) valores.get(0)).aceitar(this);

                for (int i = 1; i < valores.size(); i++)
                {
                    TipoDado tipoDadoElemento = (TipoDado) ((NoExpressao) valores.get(i)).aceitar(this);

                    if (tipoDadoElemento != tipoDadoVetor)
                    {
                        notificarErroSemantico(new ErroDefinirTipoDadoVetorLiteral(noVetor.getTrechoCodigoFonte()));

                        throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noVetor);
                    }
                }
                return tipoDadoVetor;
            }
            catch (Exception excecao)
            {
                //excecao.printStackTrace(System.out);
                throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noVetor);
            }
        }
//        else
//        {
//            //TODO Fazer essa verificaçao no Sintatico (Portugol.g)
//            notificarErroSemantico(new ErroVetorSemElementos(noVetor.getTrechoCodigoFonte()));
//
//            throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noVetor);
//        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noDeclaracaoParametro);
        
        switch (noDeclaracaoParametro.getQuantificador())
        {
            case VALOR:
                noDeclaracaoParametro.setIdParaInspecao(totalVariaveisDeclaradas);
                totalVariaveisDeclaradas++;
                break;
            case VETOR:
                noDeclaracaoParametro.setIdParaInspecao(totalVetoresDeclarados);
                totalVetoresDeclarados++;
                break;
            case MATRIZ:
                noDeclaracaoParametro.setIdParaInspecao(totalMatrizesDeclaradas);
                totalMatrizesDeclaradas++;
                break;
        }
        
        String nome = noDeclaracaoParametro.getNome();
        TipoDado tipoDado = noDeclaracaoParametro.getTipoDado();
        Quantificador quantificador = noDeclaracaoParametro.getQuantificador();
        Simbolo simbolo = null;

        if (quantificador == Quantificador.VALOR)
        {
            simbolo = new Variavel(nome, tipoDado, noDeclaracaoParametro);
        }
        else if (quantificador == Quantificador.VETOR)
        {
            simbolo = new Vetor(nome, tipoDado, noDeclaracaoParametro);
        }
        else if (quantificador == Quantificador.MATRIZ)
        {
            simbolo = new Matriz(nome, tipoDado, noDeclaracaoParametro, 0, 0, new ArrayList<List<Object>>());
        }

        
        Simbolo simboloExistente = memoria.getSimbolo(nome);
        if (simboloExistente != null)
        {
            

            final boolean global = memoria.isGlobal(simboloExistente);
            final boolean local = memoria.isLocal(simboloExistente);

            memoria.empilharEscopo();
            memoria.adicionarSimbolo(simbolo);

            final boolean global1 = memoria.isGlobal(simbolo);
            final boolean local1 = memoria.isLocal(simbolo);

            if ((global && global1) || (local && local1))
            {
                simbolo.setRedeclarado(true);
                notificarErroSemantico(new ErroParametroRedeclarado(noDeclaracaoParametro, funcaoAtual));
                memoria.desempilharEscopo();
            }
            else
            {
                memoria.desempilharEscopo();
                memoria.adicionarSimbolo(simbolo);
                simbolo.setInicializado(true);

                Simbolo simboloGlobal = memoria.isGlobal(simboloExistente) ? simboloExistente : simbolo;
                Simbolo simboloLocal = memoria.isGlobal(simboloExistente) ? simbolo : simboloExistente;

                notificarAviso(new AvisoSimboloGlobalOcultado(simboloGlobal, simboloLocal, noDeclaracaoParametro));
            }
        }
        else// (ExcecaoSimboloNaoDeclarado excecaoSimboloNaoDeclarado)
        {
            simbolo.setInicializado(true);
            memoria.adicionarSimbolo(simbolo);
        }

        return null;
    }

    private static List<String> getLista()
    {
        List<String> funcoes = new ArrayList<>();

        funcoes.add(FUNCAO_LEIA);
        funcoes.add(FUNCAO_ESCREVA);
        funcoes.add(FUNCAO_ALEATORIO);
        funcoes.add(FUNCAO_LIMPA);

        return funcoes;
    }

    private TipoDado recuperaTipoNoOperacao(NoOperacao noOperacao) throws ExcecaoVisitaASA
    {
        TipoDado operandoEsquerdo = null;
        TipoDado operandoDireito = null;

        try
        {
            operandoEsquerdo = (TipoDado) noOperacao.getOperandoEsquerdo().aceitar(this);
            //noOperacao.getOperandoEsquerdo().setTipoResultante(operandoEsquerdo);
        }
        catch (ExcecaoVisitaASA excecao)
        {
            if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
            {
                throw excecao;
            }
        }

        try
        {
            operandoDireito = (TipoDado) noOperacao.getOperandoDireito().aceitar(this);
            //noOperacao.getOperandoDireito().setTipoResultante(operandoDireito);
        }
        catch (ExcecaoVisitaASA excecao)
        {
            if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
            {
                throw excecao;
            }
        }

        if (operandoEsquerdo != null && operandoDireito != null)
        {
            try
            {
                return tabelaCompatibilidadeTipos.obterTipoRetornoOperacao(noOperacao.getClass(), operandoEsquerdo, operandoDireito);
            }
            catch (ExcecaoValorSeraConvertido excecao)
            {
                notificarAviso(new AvisoValorExpressaoSeraConvertido(noOperacao, excecao.getTipoEntrada(), excecao.getTipoSaida()));

                return excecao.getTipoSaida();
            }
            catch (ExcecaoImpossivelDeterminarTipoDado excecao)
            {
                notificarErroSemantico(new ErroTiposIncompativeis(noOperacao, operandoEsquerdo, operandoDireito));

                throw new ExcecaoVisitaASA(excecao, asa, noOperacao);
            }
        }
        else
        {
            throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noOperacao);
        }
    }

    private void analisarListaBlocos(List<NoBloco> blocos) throws ExcecaoVisitaASA
    {
        if (blocos == null)
        {
            return;
        }

        memoria.empilharEscopo();

        for (NoBloco noBloco : blocos)
        {
            try
            {
                if (!blocoValido(noBloco))
                {
                    notificarErroSemantico(new ErroBlocoInvalido(noBloco));
                }

                noBloco.aceitar(this);
            }
            catch (ExcecaoVisitaASA excecao)
            {
                if (!(excecao.getCause() instanceof ExcecaoImpossivelDeterminarTipoDado))
                {
                    throw excecao;
                }
            }
        }

        memoria.desempilharEscopo();
    }

    private boolean blocoValido(NoBloco bloco)
    {
        Class classeBloco = bloco.getClass();
        Class<? extends NoBloco>[] classesPermitidas = new Class[]
        {
            NoDeclaracaoVariavel.class, NoDeclaracaoVetor.class, NoDeclaracaoMatriz.class, NoListaDeclaracoes.class,
            NoCaso.class, NoEnquanto.class, NoEscolha.class, NoFacaEnquanto.class, NoPara.class, NoSe.class,
            NoPare.class, NoRetorne.class, NoTitulo.class, NoVaPara.class,
            NoOperacaoAtribuicao.class, NoChamadaFuncao.class
        };

        for (Class classe : classesPermitidas)
        {
            if (classe.isAssignableFrom(classeBloco))
            {
                return true;
            }
        }

        return false;
    }

    // notificarErroSemantico(new ErroInclusaoBiblioteca(noInclusaoBiblioteca.getTrechoCodigoFonteNome(), new Exception(String.format("o identificador \"%s\" já está sendo utilizado", nome))));
    //notificarErroSemantico(new ErroInclusaoBiblioteca(noInclusaoBiblioteca.getTrechoCodigoFonteNome(), new Exception(String.format("A biblioteca \"%s\" já foi incluída", nome))));
    
    // notificarErroSemantico(new ErroInclusaoBiblioteca(noInclusaoBiblioteca.getTrechoCodigoFonteNome(), new Exception(String.format("o idenficador \"%s\" já está sendo utilizado como alias", nome))));
    //notificarErroSemantico(new ErroInclusaoBiblioteca(noInclusaoBiblioteca.getTrechoCodigoFonteAlias(), new Exception(String.format("O alias \"%s\" já está sendo utilizado pela biblioteca \"%s\"", alias, metaDadosBibliotecas.get(alias).getNome()))));
    
    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
    {
        String nome = noInclusaoBiblioteca.getNome();
        String alias = noInclusaoBiblioteca.getAlias();

        try
        {
            MetaDadosBiblioteca metaDadosBiblioteca = GerenciadorBibliotecas.getInstance().obterMetaDadosBiblioteca(nome);

            if (metaDadosBibliotecas.containsKey(nome)) {
                notificarErroSemantico(new ErroInclusaoBiblioteca(noInclusaoBiblioteca.getTrechoCodigoFonteNome(), new Exception(String.format("A biblioteca \"%s\" já foi incluída", nome))));
            }
            else {
                metaDadosBibliotecas.put(nome, metaDadosBiblioteca);
            }

            if (alias != null) {
//                Simbolo simbolo = memoria.getSimbolo(nome);
//                if (simbolo != null) {
//                    
//                    notificarErroSemantico(new ErroInclusaoBiblioteca(noInclusaoBiblioteca.getTrechoCodigoFonteNome(), new Exception(String.format("o identificador \"%s\" já está sendo utilizado", nome))));
//                }
                //else { // (ExcecaoSimboloNaoDeclarado excecao2) {
                    if (metaDadosBibliotecas.containsKey(alias)) {
                        notificarErroSemantico(new ErroInclusaoBiblioteca(noInclusaoBiblioteca.getTrechoCodigoFonteAlias(), new Exception(String.format("O alias \"%s\" já está sendo utilizado pela biblioteca \"%s\"", alias, metaDadosBibliotecas.get(alias).getNome()))));
                    }
                    else {
                        metaDadosBibliotecas.put(alias, metaDadosBiblioteca);
                    }
                //}
            }
        }
        catch (ErroCarregamentoBiblioteca erro) {
            notificarErroSemantico(new ErroInclusaoBiblioteca(noInclusaoBiblioteca.getTrechoCodigoFonteNome(), erro));
        }

        return null;
    }

    private TipoDado analisarReferenciaVariavelPrograma(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoImpossivelDeterminarTipoDado
    {
        Simbolo simbolo = memoria.getSimbolo(noReferenciaVariavel.getNome());
        if (simbolo != null) {

            if (!simbolo.inicializado())
            {
                notificarErroSemantico(new ErroSimboloNaoInicializado(noReferenciaVariavel, simbolo));
            }

            if (!(simbolo instanceof Variavel) && !declarandoVetor && !declarandoMatriz && !passandoReferencia && !passandoParametro)
            {
                notificarErroSemantico(new ErroReferenciaInvalida(noReferenciaVariavel, simbolo));
            }
            else if (simbolo instanceof Variavel)
            {
                ((Variavel) simbolo).getOrigemDoSimbolo().adicionarReferencia(noReferenciaVariavel);
            }
            else if (simbolo instanceof Vetor)
            {
                simbolo.getOrigemDoSimbolo().adicionarReferencia(noReferenciaVariavel);
            }
            else if (simbolo instanceof Matriz)
            {
                simbolo.getOrigemDoSimbolo().adicionarReferencia(noReferenciaVariavel);
            }
            
            return simbolo.getTipoDado();
        }
        else// (ExcecaoSimboloNaoDeclarado excecaoSimboloNaoDeclarado)
        {
            notificarErroSemantico(new ErroSimboloNaoDeclarado(noReferenciaVariavel));
        }

        throw new ExcecaoImpossivelDeterminarTipoDado();
    }

    private TipoDado analisarReferenciaVariavelBiblioteca(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
    {
        final String escopo = noReferenciaVariavel.getEscopoBiblioteca();
        final String nome = noReferenciaVariavel.getNome();
        final MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(escopo);

        if (metaDadosBiblioteca != null)
        {
            MetaDadosConstante metaDadosConstante = metaDadosBiblioteca.getMetaDadosConstantes().obter(nome);

            if (metaDadosConstante != null)
            {
                noReferenciaVariavel.setVariavelDeBiblioteca(true);
                noReferenciaVariavel.setTipoBiblioteca(metaDadosConstante.getTipoDado());
                
                return metaDadosConstante.getTipoDado();
            }

            notificarErroSemantico(new ErroConstanteNaoEncontradaNaBiblioteca(noReferenciaVariavel.getTrechoCodigoFonteNome(), nome, metaDadosBiblioteca));
        }
        else
        {
            notificarErroSemantico(new ErroBibliotecaNaoInserida(noReferenciaVariavel.getTrechoCodigoFonteNome(), escopo));
        }

        throw new ExcecaoVisitaASA(new ExcecaoImpossivelDeterminarTipoDado(), asa, noReferenciaVariavel);
    }

    private Integer obterTamanhoVetorMatriz(final NoExpressao expTamanho, NoDeclaracaoBase noDeclaracao) throws ExcecaoVisitaASA
    {
        if (expTamanho != null)
        {
            TipoDado tipoTamanho = (TipoDado) expTamanho.aceitar(this);
            AnalisadorDeclaracaoTamanhoVetorMatriz adtvm = new AnalisadorDeclaracaoTamanhoVetorMatriz();            
            
            if (tipoTamanho == TipoDado.INTEIRO)
            {
                if (!(expTamanho instanceof NoInteiro) && !(expTamanho instanceof NoReferenciaVariavel) && !(expTamanho instanceof NoOperacao))
                {
                    notificarErroSemantico(new ErroTamanhoVetorMatriz(noDeclaracao, expTamanho));
                }
                else if (expTamanho instanceof NoReferenciaVariavel)
                {
                    NoReferenciaVariavel ref = (NoReferenciaVariavel) expTamanho;

                    if (ref.getEscopoBiblioteca() == null)
                    {
                        Variavel variavel = (Variavel) memoria.getSimbolo(ref.getNome());
                        NoDeclaracaoVariavel decl = (NoDeclaracaoVariavel) variavel.getOrigemDoSimbolo();

                        if (variavel.constante())
                        {
                            Integer intg = ((NoInteiro) decl.getInicializacao()).getValor();
                            if(intg<=0)
                            {
                                notificarErroSemantico(new ErroTamanhoVetorMatriz(noDeclaracao, expTamanho));
                            }
                            return intg;
                        }
                        else
                        {
                            notificarErroSemantico(new ErroTamanhoVetorMatriz(noDeclaracao, expTamanho));
                        }
                    }
                    else
                    {
                        MetaDadosBiblioteca metaDadosBiblioteca = metaDadosBibliotecas.get(ref.getEscopoBiblioteca());
                        MetaDadosConstantes metaDadosConstantes = metaDadosBiblioteca.getMetaDadosConstantes();
                        MetaDadosConstante metaDadosConstante = metaDadosConstantes.obter(ref.getNome());

                        if (metaDadosConstante.getTipoDado() == TipoDado.INTEIRO)
                        {
                            return (Integer) metaDadosConstante.getValor();
                        }
                        else
                        {
                            notificarErroSemantico(new ErroTamanhoVetorMatriz(noDeclaracao, expTamanho));
                        }
                    }
                }
                else if(expTamanho instanceof NoOperacao)
                {                            
                    try {
                        return adtvm.possuiExpressaoDeTamanhoValida(noDeclaracao, expTamanho);
                    } catch (ErroExpressaoTamanhoVetorMatriz ex) {
                        notificarErroSemantico(ex);
                    }
                }
                else
                {
                    Integer intg = ((NoInteiro) expTamanho).getValor();
                    if(intg<=0)
                    {
                        notificarErroSemantico(new ErroTamanhoVetorMatriz(noDeclaracao, expTamanho));
                    }
                    return intg;
                }
            }
            else
            {
                notificarErroSemantico(new ErroTamanhoVetorMatriz(noDeclaracao, expTamanho));
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noContinue);
        throw new ExcecaoVisitaASA("Erro", new ErroComandoNaoSuportado(noContinue.getTrechoCodigoFonte()), asa, noContinue);
    }

    @Override
    public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noTitulo);
        throw new ExcecaoVisitaASA("Erro", new ErroComandoNaoSuportado(noTitulo.getTrechoCodigoFonte()), asa, noTitulo);
    }

    @Override
    public Object visitar(NoVaPara noVaPara) throws ExcecaoVisitaASA
    {
        setarPaiDoNo(noVaPara);
        throw new ExcecaoVisitaASA("Erro", new ErroComandoNaoSuportado(noVaPara.getTrechoCodigoFonte()), asa, noVaPara);
    }

    @Override
    public Object visitar(NoSenao noSenao) throws ExcecaoVisitaASA {
        setarPaiDoNo(noSenao);
        throw new ExcecaoVisitaASA("Erro", new ErroComandoNaoSuportado(noSenao.getTrechoCodigoFonte()), asa, noSenao);
    }

    @Override
    public Object visitar(NoParametroFuncao noParametroFuncao) throws ExcecaoVisitaASA {
        setarPaiDoNo(noParametroFuncao);
        throw new ExcecaoVisitaASA("Erro", new ErroComandoNaoSuportado(noParametroFuncao.getTrechoCodigoFonte()), asa, noParametroFuncao);
    }
}
