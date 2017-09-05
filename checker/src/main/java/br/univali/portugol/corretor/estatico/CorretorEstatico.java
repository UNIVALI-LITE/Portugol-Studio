package br.univali.portugol.corretor.estatico;

import br.univali.portugol.corretor.dinamico.model.*;
import br.univali.portugol.corretor.estatico.rules.Dica;
import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CorretorEstatico {

    private final Questao questao;

    private List<Object> visitors = new ArrayList<Object>();
    
    public CorretorEstatico(Questao questao) {
        this.questao = questao;
    }
    
    
    public List<Mensagem> executar(String codigoFonte, String[] args) throws ErroCompilacao, ExcecaoVisitaASA{
        
        
        Solucao solucaoSimilar = null;
        
        Serializador serializador = new Serializador();
        Programa programaAluno = Portugol.compilarParaAnalise(codigoFonte);
        serializador.visitar(programaAluno.getArvoreSintaticaAbstrata());
        String codigoAlunoSerializado = serializador.getAstSerializada();
        
        int distance = Integer.MAX_VALUE;
        System.out.println(questao.getSolucoes().get(0).getValor());
        for (Solucao solucao : questao.getSolucoes()){
            Programa programaSolucao = Portugol.compilarParaAnalise(solucao.getValor());
            serializador = new Serializador();
            serializador.visitar(programaSolucao.getArvoreSintaticaAbstrata());
            String solucaoSerializado = serializador.getAstSerializada();
            
            int newDistance = LevenshteinDistance.execute(codigoAlunoSerializado, solucaoSerializado);
            
            if (newDistance < distance) {
                distance = newDistance;
                solucaoSimilar = solucao;
            }
        }
        System.out.println("distance"+distance);
        List<Visitor> visitorNames = solucaoSimilar.getVisitors();
        
        for (Visitor visitorName : visitorNames) {
            String className = "br.univali.portugol.corretor.estatico.rules."+visitorName.getValor();
            Class c = null;
            try {
                c = Class.forName(className);
                try {
                    if (visitorName.getParametros() != null && !visitorName.getParametros().isEmpty()){
                
                        List<Parametro> parametros = visitorName.getParametros();
                        Class[] tipos = new Class[parametros.size()];
                        Object[] valores = new Object[parametros.size()];

                        for (int i = 0; i < parametros.size(); i++) {
                            Parametro p = parametros.get(i);
                            
                            Class tipo = Class.forName(p.getTipo());

                            Object valor = null;

                            if (p instanceof ParametroValor) {
                                if (tipo instanceof java.lang.Class) {
                                    valor = Class.forName(((ParametroValor)p).getValor());
                                } else {
                                //outros tipos virão aqui.
                                }
                            } else {
                                
                                List<Object> itensListaValoresParametro = new ArrayList<Object>();
                                ParametroList pl = (ParametroList) p;
                                for (Valor v : pl.getValores()){
                                    Class t = Class.forName(v.getTipo());
                                    if (t instanceof java.lang.Class) {
                                        itensListaValoresParametro.add(Class.forName(v.getValor()));
                                    } else {
                                    //outros tipos virão aqui. 
                                    }
                                }
                                valor = itensListaValoresParametro;
                            }

                            tipos[i] = tipo;
                            valores[i] = valor;
                        }
                        try {
                            Constructor constructor = c.getConstructor(tipos);
                            visitors.add(constructor.newInstance(valores));
                        } catch (InstantiationException ex) {
                            Logger.getLogger(CorretorEstatico.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(CorretorEstatico.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(CorretorEstatico.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(CorretorEstatico.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchMethodException ex) {
                            Logger.getLogger(CorretorEstatico.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SecurityException ex) {
                            Logger.getLogger(CorretorEstatico.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else {
                        visitors.add(c.newInstance());
                    }
                } catch (InstantiationException ex) {
                    Logger.getLogger(CorretorEstatico.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(CorretorEstatico.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CorretorEstatico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        for (Object o : visitors) {
        
            if (o instanceof VisitanteASA) {
                programaAluno.getArvoreSintaticaAbstrata().aceitar((VisitanteASA)o);        
            }
            
        }
        
        List<Mensagem> dicas = new ArrayList<Mensagem>();
        
        for (Object o : visitors) {
        
            if (o instanceof Dica) {
                List<Mensagem> msgs = ((Dica) o).recuperar();
                
                for (Mensagem m : msgs) {
                
                    dicas.add(m);
                    
                }
            }
            
        }

       return dicas;
    }
    
}
