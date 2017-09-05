package br.univali.portugol.nucleo.bibliotecas.base.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Luiz Fernando Noschang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DocumentacaoParametro
{
    /**
     * 
     * @return  o nome do parâmetro da função
     */
    public String nome();
    
    /**
     * 
     * @return  a descrição do propósito deste parãmetro na função
     */
    public String descricao();
}
