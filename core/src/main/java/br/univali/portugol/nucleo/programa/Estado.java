package br.univali.portugol.nucleo.programa;

public enum Estado {
    BREAK_POINT, // usuário clicou no botão que executa o programa até
    // atingir um ponto de parada, caso exista algum
    STEP_INTO, // não utilizado no momento
    STEP_OVER, // executa passo a passo, para em todos os nós que são
    // paráveis (nem todos são)
    PARADO// esperando o usuário iniciar a execução
}
