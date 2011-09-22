programa
{
	funcao inicio(cadeia args[])
	{
		real temperatura_celsius, tf

		escreva("Digite a temperatura em graus celsius \n")
		leia(temperatura_celsius)

		tf = (1.8*temperatura_celsius)+32

		escreva("A temperatura em Farenheit ", tf)
	}
}
