package br.univali.portugol.nucleo.bibliotecas.base.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Armazena a documentação da função, a qual será exposta às IDEs
 * 
 * @author Luiz Fernando Noschang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DocumentacaoFuncao
{
    /**
     * 
     * @return  a descrição do que a função faz e qual o seu propósito
     */
    public String descricao();
    
    /**
     * 
     * @return  a informacoes de documentação dos parâmetros desta função
     */
    public DocumentacaoParametro[] parametros() default {};
    
    /**
     * 
     * @return  a descrição do que valor que é retornado por esta função
     */
    public String retorno() default "";
    
    /**
     * <string>Opcional<strong>
     *
     * @return  uma URL para uma página Web contendo informações adicionais sobre
     *          a função
     */
    public String referencia() default "";
    
    /**
     * 
     * @return  a lista de autores que desenvolveram esta função
     */
    public Autor[] autores();
}
