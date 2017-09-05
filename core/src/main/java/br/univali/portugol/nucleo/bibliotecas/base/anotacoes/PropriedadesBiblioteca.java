package br.univali.portugol.nucleo.bibliotecas.base.anotacoes;

import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Armazena propriedades adicionais da biblioteca
 * 
 * @author Luiz Fernando Noschang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropriedadesBiblioteca
{
    /**
     * 
     * @return  o tipo desta biblioteca
     */
    TipoBiblioteca tipo();
}
