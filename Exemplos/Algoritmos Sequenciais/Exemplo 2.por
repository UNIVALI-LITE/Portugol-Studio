// O exemplo requer três valores reias, que representam a altura de três pessoas ,e ao fim exibe a media da altura.
programa
{
	funcao inicio(cadeia args[])
	{
		real altura1, altura2, altura3, mediaAltura

		escreva("Digite a Altura de três pessoas: \n")
		leia(altura1, altura2, altura3)

		mediaAltura = (altura1 + altura2 + altura3)/3

		escreva("A média da altura das três pessoas é:  ", mediaAltura," m")
	}
}
