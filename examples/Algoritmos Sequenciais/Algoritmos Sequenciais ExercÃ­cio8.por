programa
{
	funcao inicio(cadeia args[]) 
	{
		inteiro horaInicio, horaFim, duracao

		escreva("Digite a hora de inicio e de Fim da Festa")
		leia(horaInicio, horaFim)

		duracao = horaFim - horaInicio

		escreva("A festa durou:  ", duracao, "  horas")
	}
}