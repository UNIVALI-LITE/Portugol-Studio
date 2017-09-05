package br.univali.portugol.nucleo.bibliotecas.base.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Armazena as informações do autor da função
 * 
 * @author Luiz Fernando Noschang
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Autor
{
    /**
     * 
     * @return  o nome do autor
     */
    
    public String nome();
    
    /**
     * 
     * @return  o email do autor
     */
    public String email() default "";
}
