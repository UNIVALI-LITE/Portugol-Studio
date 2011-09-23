programa
{
	funcao inicio()
	{
		real valorCompra, descontoCompra
		cadeia cliente

		escreva("Para clientes Especiais 10% desconto\n")
		escreva("Para clientes funcionários 5% desconto\n")
		
		escreva("Digite o total da compra \n")
		leia(valorCompra)

		escreva("Digite o tipo de cliente que você é: especial, funcionario ou comum \n")
		leia(cliente)

		se(cliente == "especial")
		{
			descontoCompra = valorCompra - (10/100.00)
		}
		
		se(cliente == "funcionario")
		{
			descontoCompra = valorCompra - (5/100.00)
		}
		
		se(cliente == "comum")
		{
			descontoCompra = valorCompra
		}
		
		escreva("O valor da compra : ", descontoCompra)
	}
}
