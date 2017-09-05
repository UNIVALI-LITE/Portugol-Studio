package br.univali.portugol.nucleo.bibliotecas.base.anotacoes;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Armazena a documentação da {@link Biblioteca}, a qual será exposta às IDEs
 * 
 * @author Luiz Fernando Noschang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DocumentacaoBiblioteca
{
    /**
     * 
     * @return  a descrição geral do propósito da biblioteca e das funções e 
     *          constantes contidas nela
     */
    String descricao();    
    
    /**
     * 
     * @return  a versão atual da biblioteca. Deve ser uma String no
     *          formato "versaoMaior.versaoMenor". Exemplo: 1.3
     */
    String versao();
}
