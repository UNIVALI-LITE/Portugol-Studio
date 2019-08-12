package br.univali.portugol.nucleo.execucao.gerador.helpers;

import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import br.univali.portugol.nucleo.execucao.gerador.GeradorCodigoJava;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Elieser
 */
public class GeradorDeclaracaoMetodo
{
    public void gera(NoDeclaracaoFuncao noFuncao, PrintWriter saida, VisitanteASA visitor, 
            int nivelEscopo, GeradorCodigoJava.Opcoes opcoes, long seed) throws ExcecaoVisitaASA
    {
        saida.println();

        String identacao = Utils.geraIdentacao(nivelEscopo);

        String nome = noFuncao.getNome();
        boolean metodoPrincipal = "inicio".equals(nome);
        if (metodoPrincipal)
        {
            nome = "executar";
            saida.append(identacao);
            saida.append("@Override").println();
        }

        saida.append(identacao)
                .append(metodoPrincipal ? "protected" : "private")
                .append(" ")
                .append(Utils.getNomeTipoJava(noFuncao.getTipoDado()))
                .append(geraQuantificador(noFuncao.getQuantificador()))
                .append(" ")
                .append(nome);

        if (!metodoPrincipal)
        {
            geraParametros(noFuncao, saida);
        }
        else
        {
            saida.append("(String[] parametros)");
        }
        saida.append(" throws ErroExecucao, InterruptedException");
        saida.println(); // pula uma linha depois da declaração da assinatura do método
        saida.append(identacao).append("{").println(); // inicia o escopo do método

        if (opcoes.gerandoCodigoParaInterrupcaoDeThread)
        {
            Utils.geraVerificacaoThreadInterrompida(saida, nivelEscopo);
        }

        if (opcoes.gerandoCodigoParaInspecaoDeSimbolos)
        {
            geraCodigoInicializacaoParametrosInspecionados(noFuncao.getParametros(), saida, nivelEscopo, seed);
        }
        
//        if (opcoes.gerandoCodigoParaPontosDeParada)
//        {
//            Utils.geraParadaPassoAPasso(noFuncao, saida, nivelEscopo);
//        }
        
        Utils.visitarBlocos(noFuncao.getBlocos(), saida, visitor, nivelEscopo, opcoes, seed); // gera o código dentro do método

        saida.println();
        saida.append(identacao).append("}").println(); // finaliza o escopo do método
        saida.println(); // linha em branco depois de cada método
        
        
    }

    private void geraCodigoInicializacaoParametrosInspecionados(List<NoDeclaracaoParametro> parametros,
                     PrintWriter saida, int nivelEscopo, long seed)
    {
        for (NoDeclaracaoParametro parametro : parametros)
        {
            int idInspecao = parametro.getIdParaInspecao();
            if (idInspecao >= 0)
            {
                Quantificador quantificador = parametro.getQuantificador();
                String nomeArrayInspecao = getNomeArrayInspecionavel(quantificador);
                saida.append(Utils.geraIdentacao(nivelEscopo));
                saida.format("if (%s[%d] != null) {", nomeArrayInspecao, idInspecao).println();
                switch (quantificador)
                {
                    case VALOR:
                        geraCodigoInicializacaoVariavel(parametro, saida, nomeArrayInspecao, nivelEscopo, seed);
                        break;
                    case VETOR:
                        geraCodigoInicializacaoVetor(parametro, saida, nomeArrayInspecao, nivelEscopo, seed);
                        break;
                
                    case MATRIZ:
                        geraCodigoInicializacaoMatriz(parametro, saida, nomeArrayInspecao, nivelEscopo, seed);
                        break;
                }
                
                saida.append(Utils.geraIdentacao(nivelEscopo))
                        .append("}") // fecha o IF inicial
                        .println();
            }
        }
    }
    
    private void geraCodigoInicializacaoVetor(NoDeclaracaoParametro parametro,
            PrintWriter saida, String nomeArrayInspecao, int nivelEscopo, long seed) 
    {
        //gera um if verificando se é necessário redimensionar o vetor interno
        
        int idInspecao = parametro.getIdParaInspecao();
        String nomeParametro = parametro.getNome();
        
        saida.append(Utils.geraIdentacao(nivelEscopo + 1));
        saida.format("if (%s[%d].tamanho != %s.length) {", nomeArrayInspecao, idInspecao, nomeParametro);
        saida.println();

        saida.append(Utils.geraIdentacao(nivelEscopo + 2));
        saida.format("inspecionaVetor(%d, %s.length);", idInspecao, nomeParametro);
        saida.println();

        saida.append(Utils.geraIdentacao(nivelEscopo + 1));
        saida.append("}").println(); //fecha IF verificando se é necessário redimencionar array interno

        //gera loop coletando todas as posições do vetor
        saida.append(Utils.geraIdentacao(nivelEscopo + 1));
        saida.format("for(int i_%3$d=0; i_%3$d < %s[%d].tamanho; i_%3$d++){", nomeArrayInspecao, idInspecao, seed); // loop percorrendo todo o array
        saida.println();

        saida.append(Utils.geraIdentacao(nivelEscopo + 2));
        saida.format("%s[%d].setValor(%s[i_%4$d], i_%4$d);", nomeArrayInspecao, idInspecao, nomeParametro, seed);
        saida.println(); //fecha setValor(v[i], i);

        saida.append(Utils.geraIdentacao(nivelEscopo + 1));
        saida.append("}").println(); //fecha loop
        
    }
    
    private void geraCodigoInicializacaoVariavel(NoDeclaracaoParametro parametro,
                     PrintWriter saida, String nomeArrayInspecao, int nivelEscopo, long seed)
    {
        int idInspecao = parametro.getIdParaInspecao();
        saida.append(Utils.geraIdentacao(nivelEscopo + 1));
        String stringNome = parametro.getNome();
        if (parametro.getModoAcesso() == ModoAcesso.POR_REFERENCIA)
        {
            String nomeTipo = Utils.getNomeTipoJava(parametro.getTipoDado()).toUpperCase();
            stringNome = String.format("REFS_%s[%s]", nomeTipo, parametro.getNome());
        }
        saida.format("%s[%d] = %s;", nomeArrayInspecao, idInspecao, stringNome);
        saida.println();
    }
    
    private void geraCodigoInicializacaoMatriz(NoDeclaracaoParametro parametro,
                    PrintWriter saida, String nomeArrayInspecao, int nivelEscopo, long seed)
    {
        //gera um if verificando se é necessário redimensionar a matriz interna
        
        int idInspecao = parametro.getIdParaInspecao();
        String nomeParametro = parametro.getNome();
        
        saida.append(Utils.geraIdentacao(nivelEscopo + 1));
        saida.format("if (%s[%d].linhas != %s.length) {", nomeArrayInspecao, idInspecao, nomeParametro);
        saida.println();

        saida.append(Utils.geraIdentacao(nivelEscopo + 2));
        saida.format("inspecionaMatriz(%d, %s.length, %s[0].length);", idInspecao, nomeParametro, nomeParametro);
        saida.println();
        
        saida.append(Utils.geraIdentacao(nivelEscopo + 1));
        saida.append("}").println(); //fecha IF verificando se é necessário redimencionar array interno

        //gera loop coletando todas as posições da matriz
        saida.append(Utils.geraIdentacao(nivelEscopo + 1));
        saida.format("for(int i_%3$d=0; i_%3$d < %s[%d].linhas; i_%3$d++){", nomeArrayInspecao, idInspecao, seed);
        saida.println(); 
        
        saida.append(Utils.geraIdentacao(nivelEscopo + 2));
        saida.format("for(int j_%3$d=0; j_%3$d < %s[%d].colunas; j_%3$d++){", nomeArrayInspecao, idInspecao, seed).println(); 

        saida.append(Utils.geraIdentacao(nivelEscopo + 3));
        saida.format("%s[%d].setValor(%s[i_%4$d][j_%4$d], i_%4$d, j_%4$d);", nomeArrayInspecao, idInspecao, nomeParametro, seed);
        saida.println(); 

        saida.append(Utils.geraIdentacao(nivelEscopo + 2));
        saida.append("}").println(); //fecha loop interno
        
        saida.append(Utils.geraIdentacao(nivelEscopo + 1));
        saida.append("}").println(); //fecha loop externo
        
    }
    
   
    
    private String getNomeArrayInspecionavel(Quantificador quantificador)
    {
        switch(quantificador)
        {
            case VALOR: return "variaveisInspecionadas";
            case VETOR: return "vetoresInspecionados";
            case MATRIZ: return "matrizesInspecionadas";
        }
        return "variaveisInspecionadas";
    }
    
    private static String geraQuantificador(Quantificador quantificador)
    {
        switch (quantificador)
        {
            case VETOR:
                return "[]";
            case MATRIZ:
                return "[][]";
            default:
                return ""; 
        }
    }

    private static void geraParametros(NoDeclaracaoFuncao noFuncao, PrintWriter saida)
    {
        List<NoDeclaracaoParametro> parametros = noFuncao.getParametros();

        saida.append("("); // parenteses de início da lista de parâmetros
        int size = parametros.size();
        for (int i = 0; i < size; i++)
        {
            NoDeclaracaoParametro noParametro = parametros.get(i);
            
            // o código gerado para matrizes e vetores passados por referência é o mesmo código usado para passagem de parâmetro por valor (apenas o nome da variável)
            if (noParametro.getModoAcesso() == ModoAcesso.POR_VALOR || noParametro.getQuantificador() != Quantificador.VALOR) 
            {
                geraParametroPorValor(noParametro, saida);
            }
            else{
                geraParametroPorReferencia(noParametro, saida);
            }

            if (i < size - 1)
            {
                saida.append(", ");
            }
        }
        saida.append(")"); // parenteses de fim da lista de parâmetros
    }
    
    private static void geraParametroPorReferencia(NoDeclaracaoParametro noParametro, PrintWriter saida)
    {
        // os parâmetros por referência são apenas índices para o array REFERENCIAS[]
        saida.append("int ")
            .append(noParametro.getNome());
    }
    
    private static void geraParametroPorValor(NoDeclaracaoParametro noParametro, PrintWriter saida)
    {
        saida.append(Utils.getNomeTipoJava(noParametro.getTipoDado()))
                    .append(" ") // espaço entre o tipo e o nome
                    .append(noParametro.getNome())
                    .append(geraQuantificador(noParametro.getQuantificador()));
    }

}
