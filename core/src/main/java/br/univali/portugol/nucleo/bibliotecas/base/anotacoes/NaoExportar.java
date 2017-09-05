package br.univali.portugol.nucleo.bibliotecas.base.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Esta anotação define que um método público da biblioteca não será exportado como uma função portugol.
 * 
 * @author Luiz Fernando Noschang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NaoExportar
{

}
