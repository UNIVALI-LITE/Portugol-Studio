programa
{
    funcao inicio()
    {
		inteiro idade
		real salario, nota1, nota2, nota3
		cadeia nome, sobrenome
		
		escreva("Informe a sua idade: ")
		leia (idade)				//lê o valor digitado para "idade"
		
		escreva("Informe seu salario: ")
		leia (salario)				//lê o valor digitado para "salario"
		
		escreva("Informe o seu nome e sobrenome: ")
		leia (nome, sobrenome)		//lê o valor digitado para "nome" e "sobrenome"
		
		escreva("Informe as suas três notas: ")
		leia (nota1, nota2, nota3)	//lê o valor digitado para "nota1", "nota2" e "nota3"

		escreva("Seu nome é:"+nome+" "+sobrenome+"\n")
		escreva("Você tem "+idade+" anos e ganha de salario "+salario+"\n")
		escreva("Suas três notas foram:\n")
		escreva("Nota 1: "+nota1+"\n")
		escreva("Nota 2: "+nota2+"\n")
		escreva("Nota 3: "+nota3+"\n")
    }
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 815; 
 */