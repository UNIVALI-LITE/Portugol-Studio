// O exemplo requer dois valores inteiros, sendo eles a hora de inicio e fim de uma festa. Ao fim é exibido o tempo de duração da festa.
programa
{
	funcao inicio(cadeia args[]) 
	{
		inteiro horaInicio, horaFim, duracao

		escreva("Digite a hora de inicio e de fim da festa")
		leia(horaInicio, horaFim)

		duracao = horaFim - horaInicio

		escreva("A festa durou:  ", duracao, "  horas")
	}
}
