package br.univali.portugol.nucleo.bibliotecas.base.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Armazena a documentação da constante, a qual será exposta às IDEs.
 * 
 * @author Luiz Fernando Noschang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DocumentacaoConstante
{
    /**
     * 
     * @return  uma descrição do valor contido na constante e o seu propósito
     */
    public String descricao();

    /**
     * <string>Opcional<strong>
     *
     * @return  uma URL para uma página Web contendo informações adicionais sobre
     *          a constante
     */    
    public String referencia() default "";
}
