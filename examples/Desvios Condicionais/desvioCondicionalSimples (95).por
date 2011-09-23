programa { 
	funcao inicio(cadeia args[]) 
	{
		real itensEstoque, itensFornecido, estoqueAtualizado 
		
		escreva("A quantidade de itens no estoque\n") 
		leia(itensEstoque) 
		
		escreva("A quantidade de Pedidos \n") 
		leia(itensFornecido) 
		
		se(itensEstoque < itensFornecido){ 
			escreva("nao ha itens suficientes") 
		}senao{ 
			estoqueAtualizado = itensEstoque - itensFornecido
			escreva("Estoque atualizado:", estoqueAtualizado) 
		} 
	} 
}