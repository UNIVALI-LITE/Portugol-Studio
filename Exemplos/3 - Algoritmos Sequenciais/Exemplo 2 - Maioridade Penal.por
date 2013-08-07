// O exemplo define qual o valor da maior Idade penal usando uma constante chamada maiorIdade
// e mostra ao usuário quantos anos faltam para ele atingir a maioridade

programa 
{
	funcao inicio()
	{
		const inteiro maiorIdade = 18
		inteiro idade, anos

		escreva("Digite sua idade: ")
		leia(idade)

		anos = maiorIdade - idade // Calcula quantos anos faltam para atingir a maioridade
		
		escreva("Faltam ", anos, " anos para voce atingir a maior idade.")
	}
}
