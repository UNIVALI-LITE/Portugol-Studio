package br.univali.ps.ui.coletor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.util.Date;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleText;
import javax.swing.JButton;
import javax.swing.JLabel;

import br.univali.portugol.ajuda.TopicoHtml;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosConstante;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosFuncao;

class Interacao
{

    private final String nomeComponente;
    private final String nomeClasseComponente;
    private final String hierarquia;
    private final String extra; // getText, getAcessibleText
    private final Point posicao;
    private final Date timeStamp;

    public Interacao(Component componente, Point posicao)
    {
    	try {
    		if(componente.getClass().getName().equals("javax.swing.JTree")) {
        		String jTreeVarSelectedPath = "";
        	    Object[] paths = ((javax.swing.JTree)componente).getSelectionPath().getPath();
        	    for (int i=0; i<paths.length; i++) {
        	       
        	    	try {
        	    		if(((javax.swing.tree.DefaultMutableTreeNode)paths[i]).getUserObject() != null) {
        	    			String nomeClasse = ((javax.swing.tree.DefaultMutableTreeNode)paths[i]).getUserObject().getClass().getName();
            	    		if(nomeClasse.equals("java.lang.String")) {
            	    			//System.out.println(((javax.swing.tree.DefaultMutableTreeNode)paths[i]).getUserObject().toString());
            	    			jTreeVarSelectedPath += ((javax.swing.tree.DefaultMutableTreeNode)paths[i]).getUserObject().toString();
            	    		}else if(nomeClasse.equals("br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca")) {
            	    			MetaDadosBiblioteca metaDadosBiblioteca = (MetaDadosBiblioteca)((javax.swing.tree.DefaultMutableTreeNode)paths[i]).getUserObject();
            	    			//System.out.println(metaDadosBiblioteca.getNome());
            	    			jTreeVarSelectedPath += metaDadosBiblioteca.getNome();
            	    		}else if(nomeClasse.equals("br.univali.portugol.nucleo.bibliotecas.base.MetaDadosFuncao")) {
            	    			MetaDadosFuncao metaDadosFuncao = (MetaDadosFuncao)((javax.swing.tree.DefaultMutableTreeNode)paths[i]).getUserObject();
            	    			//System.out.println(metaDadosFuncao.getNome());
            	    			jTreeVarSelectedPath += metaDadosFuncao.getNome();
            	    		}else if(nomeClasse.equals("br.univali.portugol.ajuda.TopicoHtml")) {
            	    			TopicoHtml topicoHtml = (TopicoHtml)((javax.swing.tree.DefaultMutableTreeNode)paths[i]).getUserObject();
            	    			//System.out.println(topicoHtml.getTitulo());
            	    			jTreeVarSelectedPath += topicoHtml.getTitulo();
            	    		}else if(nomeClasse.equals("br.univali.portugol.nucleo.bibliotecas.base.MetaDadosConstante")) {
            	    			MetaDadosConstante metaDadosConstante = (MetaDadosConstante)((javax.swing.tree.DefaultMutableTreeNode)paths[i]).getUserObject();
            	    			//System.out.println(metaDadosConstante.getNome());
            	    			jTreeVarSelectedPath += metaDadosConstante.getNome();
            	    		}else {
            	    			//System.out.println(nomeClasse);  
            	    		}
        	    		}  
        	    	}catch (Exception e) {
    					// TODO: handle exception
    				}
        	        		
        	        if (i+1 <paths.length && !jTreeVarSelectedPath.equals("")) {
        	            jTreeVarSelectedPath += "->";
        	        }
        	    }
        	   System.out.println(jTreeVarSelectedPath);   
        	   if(!jTreeVarSelectedPath.equals("")) {
        		   ColetorInteracao.getInstancia().adicionaAjudaExemplo(jTreeVarSelectedPath);
        	   }
        	}
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
        assert (componente != null);
        assert (posicao != null);

        this.nomeComponente = componente.getName();
        this.hierarquia = getHierarquia(componente);
        this.extra = getExtra(componente);
        this.nomeClasseComponente = componente.getClass().getName();
        this.posicao = posicao;
        this.timeStamp = new Date();
    }

    private static String getExtra(Component componente) 
    {
        assert(componente != null);
        
        boolean possuiInformacaoExtra = componente instanceof JButton || componente instanceof JLabel;
        String nomeComponente = componente.getName();
        
        if ((nomeComponente == null || nomeComponente.isEmpty()) && possuiInformacaoExtra) {
            if (componente instanceof JButton) {
                return getInformacaoExtra((JButton) componente);
            } else if (componente instanceof JLabel) {
                return getInformacaoExtra((JLabel) componente);
            }
        }
        
        return null;
    }
    
    private static String getInformacaoExtra(JLabel label)
    {
        if (!label.getText().isEmpty()) {
            return "texto: " + label.getText();
        }

        return getInformacaoExtra(label.getAccessibleContext());
    }

    private static String getInformacaoExtra(JButton botao)
    {
        if (!botao.getText().isEmpty()) {
            return "texto: " + botao.getText();
        }

        return getInformacaoExtra(botao.getAccessibleContext());
    }

    private static String getInformacaoExtra(AccessibleContext contexto)
    {
        assert (contexto != null) ;

        if (!contexto.getAccessibleName().isEmpty()) {
            return "acessibleName: " + contexto.getAccessibleName();
        }

        AccessibleText accessibleText = contexto.getAccessibleText();
        if (accessibleText != null && !accessibleText.toString().isEmpty()) {
            return "acessibleText: " + accessibleText.toString();
        }

        return null;

    }

    private static String getHierarquia(Component c)
    {
        Container pai = c.getParent();

        if (pai == null) {
            return "";
        }

        String nomePai = pai.getName();
        boolean paiPossuiNome = nomePai == null || nomePai.isEmpty();
        
        String hierarquia = String.format("%s (%s)", pai.getClass().getName(), paiPossuiNome ? nomePai : "Sem nome");

        if (!paiPossuiNome) {
            hierarquia += " => ";
            hierarquia += getHierarquia(pai);
        }

        return hierarquia;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public String getNomeClasseComponente()
    {
        return nomeClasseComponente;
    }

    public String getNomePaiComponente()
    {
        return hierarquia;
    }

    public String getNomeComponente()
    {
        return nomeComponente;
    }

    public Point getPosicao()
    {
        return posicao;
    }

    @Override
    public String toString()
    {
        String string = "{ "
                + "\"componente\": \"" + nomeComponente + "\", ";

        if (extra != null) {
            string += "\"extra\": \"" + extra + "\", ";
        }

        string += "\"hierarquia\": \"" + hierarquia + "\", "
                + "\"classeComponente\": \"" + nomeClasseComponente + "\", "
                + "\"posicao\": { \"x\": \"" + posicao.getX() + "\", \"y\": \"" + posicao.getY() + "\"}, "
                + "\"timeStamp\": \"" + timeStamp + "\""
                + '}';

        return string;
    }

}
