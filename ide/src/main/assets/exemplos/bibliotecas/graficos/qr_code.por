programa
{
	// https://www.thonky.com/qr-code-tutorial/introduction

	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u
	inclua biblioteca Texto --> txt
	inclua biblioteca Tipos --> tp
	inclua biblioteca Matematica --> mat

	const cadeia POLINOMIO_GERADOR = "@0x^97 + @17x^96 + @60x^95 + @79x^94 + @50x^93 + @61x^92 + @163x^91 + @26x^90 + @187x^89 + @202x^88 + @180x^87 + @221x^86 + @225x^85 + @83x^84 + @239x^83 + @156x^82 + @164x^81 + @212x^80 + @212x^79 + @188x^78 + @190x^77"

	const inteiro TAMANHO_BIT_QRCODE = 9, MARGEM_QRCODE = 4

	const inteiro ACIMA = 0, ABAIXO = 1

	const inteiro COR_BORDA = 0xEEEEEE, COR_GRADE = 0x444444, COR_BIT_DESLIGADO = 0XEEEEEE, COR_BIT_LIGADO = 0X000000, COR_BIT_INVALIDO = 0xFF6666
	

	const inteiro BIT_DADOS_DESLIGADO = 0, BIT_DADOS_LIGADO = 1, BIT_LOCALIZACAO_DESLIGADO = 2, BIT_LOCALIZACAO_LIGADO = 3
	
	const inteiro BIT_ALINHAMENTO_DESLIGADO = 4, BIT_ALINHAMENTO_LIGADO = 5, BIT_TEMPORIZACAO_H_DESLIGADO = 6, BIT_TEMPORIZACAO_H_LIGADO = 7

	const inteiro BIT_TEMPORIZACAO_V_DESLIGADO = 8, BIT_TEMPORIZACAO_V_LIGADO = 9, BIT_RESERVADO_DESLIGADO = 10, BIT_RESERVADO_LIGADO = 11

	const inteiro BIT_MODULO_FIXO_DESLIGADO = 12, BIT_MODULO_FIXO_LIGADO = 13, BIT_INVALIDO = -1

	
	//cadeia texto_de_teste = "QR CODE gerado com o Portugol Studio"
	//cadeia texto_de_teste = "HELLO WORLD"
	//cadeia texto_de_teste = "http://lite.acad.univali.br/portugol"
	
	cadeia palavra = ""
	cadeia palavra_padrao = "PORTUGOL STUDIO"
	inteiro max_letras = 50

	inteiro codewords_bloco1[78], codewords_bloco2[78], error_codewords_bloco1[20], error_codewords_bloco2[20]

	inteiro matriz_qrcode[45][45]

	cadeia bitstring_dados = ""

	caracter VETOR_DE_CODIFICACAO[] = 
	{
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
		'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '$', '%', '*', 
		'+', '-', '.', '/', ':'
	}

	inteiro VETOR_ALPHA_PARA_BASE[256], VETOR_BASE_PARA_ALPHA[256]

	

	// Modelo 2
	// Versão 7
	// Correção L
	// Data Codewords 156
	// Error correction codewords 20
	// Databits 1248
	// Dados: 2 blocos de 78 codewords
	// Alphanumeric mode indicator 0010
	// Anphanumeric mode character count indicator: 9 bits
	
	funcao inicio()
	{
		inicializar()

		tela_digitar_palavra()
		gerar_qrcode(palavra)		
	}

	funcao gerar_qrcode(cadeia texto)
	{
		// Valor binário que indica que o QR CODE está armazenando apenas caracteres alfanuméricos
		cadeia codigo_binario = "0010"

		texto = txt.caixa_alta(texto)
		
		codigo_binario += codificar_tamanho_texto(texto)
		codigo_binario += codificar_texto(texto)
		codigo_binario += codificar_terminador_binario(codigo_binario)
		codigo_binario += codificar_pad_bytes(codigo_binario)

		extrair_codewords_bloco1(codigo_binario)
		extrair_codewords_bloco2(codigo_binario)

		extrair_error_codewords_bloco1(txt.extrair_subtexto(codigo_binario, 0, 624))
		extrair_error_codewords_bloco2(txt.extrair_subtexto(codigo_binario, 624, 1248))

		bitstring_dados = montar_bitstring_alternado()
		
		gerar_matriz_qrcode()
		
		exibir_qrcode()		
	}

	funcao gerar_matriz_qrcode()
	{
		gerar_padroes_localizadores()
		gerar_padroes_alinhamento()
		gerar_temporizador_vertical()
		gerar_temporizador_horizontal()
		gerar_modulo_fixo()
		reservar_areas_formatacao()
		reservar_areas_versao()
		gerar_dados()
		mascarar_dados()
		gerar_padroes_formato()
		gerar_padroes_versao()		
	}

	funcao mascarar_dados()
	{
		para (inteiro i = 0; i < 45; i++)
		{
			para (inteiro j = 0; j < 45; j++)
			{
				inteiro bit = matriz_qrcode[i][j]

				se (((i + j) % 2 == 0) e (bit == BIT_DADOS_DESLIGADO ou bit == BIT_DADOS_LIGADO))
				{
					se (bit == BIT_DADOS_LIGADO)
					{
						matriz_qrcode[i][j] = BIT_DADOS_DESLIGADO
					}
					senao se (bit == BIT_DADOS_DESLIGADO)
					{
						matriz_qrcode[i][j] = BIT_DADOS_LIGADO
					}
				}
			}
		}
	}

	funcao gerar_padroes_formato()
	{
		cadeia bits = calcular_error_codes_formato()

		para (inteiro i = 0; i < 8; i++)
		{
			cadeia bit = txt.obter_caracter(bits, i) + ""
			inteiro valor = tp.cadeia_para_inteiro(bit, 10)

			se (i >= 6)
			{
				matriz_qrcode[8][i + 1] = valor
			}
			senao
			{
				matriz_qrcode[8][i] = valor
			}

			se (i <= 6)
			{
				matriz_qrcode[45 - i - 1][8] = valor
			}
		}

		para (inteiro i = 7; i < 15; i++)
		{
			cadeia bit = txt.obter_caracter(bits, i) + ""
			inteiro valor = tp.cadeia_para_inteiro(bit, 10)

			se (15 - i <= 6)
			{
				matriz_qrcode[15 - i - 1][8] = valor
			}
			senao
			{
				matriz_qrcode[15 - i][8] = valor
			}

			matriz_qrcode[8][37 + i - 7] = valor
		}
		
	}

	funcao gerar_padroes_versao()
	{
		cadeia versao
		
		versao = "000111110010010100"
		versao = inverter(versao)
		
		inteiro indice_bit = 0

		para (inteiro j = 0; j < 6; j++)
		{
			para (inteiro i = 34; i < 37; i++)
			{
				cadeia bit = txt.extrair_subtexto(versao, indice_bit, indice_bit + 1) 
				inteiro valor = tp.cadeia_para_inteiro(bit, 10)
				
				matriz_qrcode[i][j] = valor
				indice_bit++
			}
		}

		indice_bit = 0

		para (inteiro i = 0; i < 6; i++)
		{
			para (inteiro j = 34; j < 37; j++)
			{
				cadeia bit = txt.extrair_subtexto(versao, indice_bit, indice_bit + 1) 
				inteiro valor = tp.cadeia_para_inteiro(bit, 10)
				
				matriz_qrcode[i][j] = valor
				indice_bit++
			}
		}
	}

	funcao cadeia inverter(cadeia texto)
	{
		cadeia invertido = ""

		para (inteiro i = txt.numero_caracteres(texto) - 1; i >= 0; i--)
		{
			invertido += txt.extrair_subtexto(texto, i, i + 1)
		}

		retorne invertido
	}

	funcao gerar_dados()
	{
		inteiro direcao_vertical = ACIMA
		inteiro linha = 44, coluna = 44
		inteiro bit_existente, indice_bit_dados = -1

		inteiro tamanho_bitstring = txt.numero_caracteres(bitstring_dados)

		//inicializar_modo_grafico()

		enquanto (indice_bit_dados < tamanho_bitstring - 1)
		{
			bit_existente = matriz_qrcode[linha][coluna]

			se (bit_existente == BIT_INVALIDO)
			{
				indice_bit_dados++				
				matriz_qrcode[linha][coluna] = obter_valor_bit(indice_bit_dados, linha, coluna)
			}

			coluna = coluna - 1			

			//desenhar_qrcode()

			bit_existente = matriz_qrcode[linha][coluna]

			se (bit_existente == BIT_INVALIDO)
			{
				indice_bit_dados++				
				matriz_qrcode[linha][coluna] = obter_valor_bit(indice_bit_dados, linha, coluna)
			}

			coluna = coluna + 1

			//desenhar_qrcode()

			se (direcao_vertical == ACIMA)
			{
				linha--

				se (linha == -1)
				{
					linha = 0

					se (coluna != 8)
					{
						coluna = coluna - 2
					}
					senao
					{
						coluna = coluna - 3
					}
					
					direcao_vertical = ABAIXO
				}
			}
			senao se (direcao_vertical == ABAIXO)
			{
				linha++

				se (linha == 45)
				{
					linha = 44
					
					coluna = coluna - 2
					direcao_vertical = ACIMA
				}
			}
		}
	}

	funcao inteiro obter_valor_bit(inteiro indice_bit_dados, inteiro linha, inteiro coluna)
	{
		cadeia bit_qr
		
		bit_qr = txt.extrair_subtexto(bitstring_dados, indice_bit_dados, indice_bit_dados + 1)
		//bit_qr = mascarar_bit(bit_qr, linha, coluna)

		se (bit_qr == "1")
		{
			retorne BIT_DADOS_LIGADO
		}
		senao
		{
			retorne BIT_DADOS_DESLIGADO
		}
	}

	funcao cadeia mascarar_bit(cadeia bit_qr, inteiro linha, inteiro coluna)
	{
		se ((linha + coluna) % 2 == 0)
		{
			se (bit_qr == "1")
			{
				retorne "0"
			}
			senao se (bit_qr == "0")
			{
				retorne "1"
			}
		}

		retorne "?"
	}

	funcao reservar_areas_formatacao()
	{
		para (inteiro linha = 0; linha < 9; linha++)
		{
			se (matriz_qrcode[linha][8] != BIT_TEMPORIZACAO_H_LIGADO)
			{
				matriz_qrcode[linha][8] = BIT_RESERVADO_DESLIGADO
			}
		}

		para (inteiro coluna = 0; coluna < 9; coluna++)
		{
			se (matriz_qrcode[8][coluna] != BIT_TEMPORIZACAO_V_LIGADO)
			{
				matriz_qrcode[8][coluna] = BIT_RESERVADO_DESLIGADO
			}
		}

		para (inteiro coluna = 37; coluna < 45; coluna++)
		{
			se (matriz_qrcode[8][coluna] != BIT_TEMPORIZACAO_V_LIGADO)
			{
				matriz_qrcode[8][coluna] = BIT_RESERVADO_DESLIGADO
			}
		}

		para (inteiro linha = 37; linha < 45; linha++)
		{
			se (matriz_qrcode[linha][8] != BIT_MODULO_FIXO_LIGADO)
			{
				matriz_qrcode[linha][8] = BIT_RESERVADO_DESLIGADO
			}
		}
	}

	funcao reservar_areas_versao()
	{
		para (inteiro linha = 34; linha < 37; linha++)
		{
			para (inteiro coluna = 0; coluna < 6; coluna++)
			{
				matriz_qrcode[linha][coluna] = BIT_RESERVADO_DESLIGADO
			}
		}


		para (inteiro linha = 0; linha < 6; linha++)
		{
			para (inteiro coluna = 34; coluna < 37; coluna++)
			{
				matriz_qrcode[linha][coluna] = BIT_RESERVADO_DESLIGADO
			}
		}
	}

	funcao gerar_modulo_fixo()
	{
		matriz_qrcode[37][8] = BIT_MODULO_FIXO_LIGADO
	}

	funcao gerar_temporizador_vertical()
	{
		inteiro coluna = 6

		para (inteiro linha = 8; linha < 37; linha++)
		{
			se (linha % 2 == 0)
			{
				matriz_qrcode[linha][coluna] = BIT_TEMPORIZACAO_V_LIGADO
			}
			senao
			{
				matriz_qrcode[linha][coluna] = BIT_TEMPORIZACAO_V_DESLIGADO
			}
		}
	}
	
	funcao gerar_temporizador_horizontal()
	{
		inteiro linha = 6

		para (inteiro coluna = 8; coluna < 37; coluna++)
		{
			se (coluna % 2 == 0)
			{
				matriz_qrcode[linha][coluna] = BIT_TEMPORIZACAO_H_LIGADO
			}
			senao
			{
				matriz_qrcode[linha][coluna] = BIT_TEMPORIZACAO_H_DESLIGADO
			}
		}
	}

	funcao gerar_padroes_alinhamento()
	{
		gerar_padrao_alinhamento(6, 22)
		gerar_padrao_alinhamento(22, 6)
		gerar_padrao_alinhamento(22, 22)
		gerar_padrao_alinhamento(22, 38)
		gerar_padrao_alinhamento(38, 22)
		gerar_padrao_alinhamento(38, 38)
	}

	funcao gerar_padrao_alinhamento(inteiro coluna, inteiro linha)
	{
		// Ajusta a linha e a coluna pois o posicionamento do padrão de alinhamento
		// é feito a partir do centro, e não do canto superior esquerdo
		coluna = coluna - 2
		linha = linha - 2
		
		para (inteiro i = 0; i < 5; i++)
		{
			para (inteiro j = 0; j < 5; j++)
			{
				matriz_qrcode[linha + i][coluna + j] = BIT_ALINHAMENTO_LIGADO
				//matriz_qrcode[linha + i][coluna + j] = BIT_INVALIDO
			}
		}

		para (inteiro i = 1; i < 4; i++)
		{
			para (inteiro j = 1; j < 4; j++)
			{
				matriz_qrcode[linha + i][coluna + j] = BIT_ALINHAMENTO_DESLIGADO
				//matriz_qrcode[linha + i][coluna + j] = BIT_INVALIDO
			}
		}

		para (inteiro i = 2; i < 3; i++)
		{
			para (inteiro j = 2; j < 3; j++)
			{
				matriz_qrcode[linha + i][coluna + j] = BIT_ALINHAMENTO_LIGADO
				//matriz_qrcode[linha + i][coluna + j] = BIT_INVALIDO
			}
		}
	}

	funcao gerar_padroes_localizadores()
	{
		gerar_padrao_localizador(0, 0)
		gerar_separador_vertical(7, 0)
		gerar_separador_horizontal(0, 7)
		
		gerar_padrao_localizador(0, 45 - 7)
		gerar_separador_vertical(7, 45 - 8)
		gerar_separador_horizontal(0, 45 - 8)

		
		gerar_padrao_localizador(45 - 7, 0)
		gerar_separador_vertical(45 - 8, 0)
		gerar_separador_horizontal(45 - 8, 7)
	}

	funcao gerar_padrao_localizador(inteiro coluna, inteiro linha)
	{
		para (inteiro i = 0; i < 7; i++)
		{
			para (inteiro j = 0; j < 7; j++)
			{
				matriz_qrcode[linha + i][coluna + j] = BIT_LOCALIZACAO_LIGADO
			}
		}

		para (inteiro i = 1; i < 6; i++)
		{
			para (inteiro j = 1; j < 6; j++)
			{
				matriz_qrcode[linha + i][coluna + j] = BIT_LOCALIZACAO_DESLIGADO
			}
		}

		para (inteiro i = 2; i < 5; i++)
		{
			para (inteiro j = 2; j < 5; j++)
			{
				matriz_qrcode[linha + i][coluna + j] = BIT_LOCALIZACAO_LIGADO
			}
		}
	}

	funcao gerar_separador_vertical(inteiro coluna, inteiro linha)
	{
		para (inteiro i = 0; i < 8; i++)
		{
			matriz_qrcode[linha + i][coluna] = BIT_LOCALIZACAO_DESLIGADO
		}
	}

	funcao gerar_separador_horizontal(inteiro coluna, inteiro linha)
	{
		para (inteiro j = 0; j < 8; j++)
		{
			matriz_qrcode[linha][coluna + j] = BIT_LOCALIZACAO_DESLIGADO
		}
	}

	funcao exibir_qrcode()
	{
		inicializar_modo_grafico()
		
		enquanto (nao t.tecla_pressionada(t.TECLA_ESC))
		{
			desenhar_qrcode()
		}
	}

	funcao inicializar_modo_grafico()
	{
		inteiro tamanho = calcular_tamanho_janela()
		
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(tamanho, tamanho)
		g.definir_titulo_janela("Gerador de QRCODE")
	}

	funcao cadeia calcular_error_codes_formato()
	{
		cadeia binario_correcao = "01" // L
		cadeia binario_mascara = "000" // 0
		//cadeia binario_mascara = "100" // 4
		
		cadeia polinomio_gerador = "10100110111"
		cadeia polinomio_formato = binario_correcao + binario_mascara + "0000000000"

		enquanto (txt.numero_caracteres(polinomio_formato) >= 11)
		{
			polinomio_formato =  remover_zeros_esquerda(polinomio_formato)
			polinomio_gerador = preencher_a_direita('0', txt.numero_caracteres(polinomio_formato), polinomio_gerador)
			
			polinomio_formato = xor_bitstring(polinomio_formato, polinomio_gerador)
			polinomio_formato = remover_zeros_esquerda(polinomio_formato)			
		}

		polinomio_formato = txt.preencher_a_esquerda('0', 10, polinomio_formato)
		polinomio_formato = binario_correcao + binario_mascara + polinomio_formato
		polinomio_formato = xor_bitstring(polinomio_formato, "101010000010010")

		retorne polinomio_formato
	}

	funcao cadeia preencher_a_direita(caracter car, inteiro tamanho, cadeia binario_polinomio_gerador)
	{
		enquanto (txt.numero_caracteres(binario_polinomio_gerador) < tamanho)
		{
			binario_polinomio_gerador += car
		}

		retorne binario_polinomio_gerador
	}

	funcao cadeia remover_zeros_esquerda(cadeia texto)
	{
		enquanto (txt.extrair_subtexto(texto, 0, 1) == "0")
		{
			texto = txt.extrair_subtexto(texto, 1, txt.numero_caracteres(texto))
		}

		retorne texto
	}

	funcao cadeia xor_bitstring(cadeia polinomio_a, cadeia polinomio_b)
	{
		cadeia polinomio_resultante = ""
		
		para (inteiro i = 0; i < txt.numero_caracteres(polinomio_a); i++)
		{
			cadeia bit_formato = txt.extrair_subtexto(polinomio_a, i, i + 1)
			cadeia bit_gerador = txt.extrair_subtexto(polinomio_b, i, i + 1)

			inteiro valor_formato = tp.cadeia_para_inteiro(bit_formato, 10)
			inteiro valor_gerador = tp.cadeia_para_inteiro(bit_gerador, 10)

			inteiro resultado = valor_formato^valor_gerador

			polinomio_resultante += resultado
		}

		retorne polinomio_resultante
	}

	funcao desenhar_qrcode()
	{
		g.definir_cor(COR_BORDA)
		g.limpar()

		desenhar_matriz_qrcode()
		//desenhar_grade()
		
		g.renderizar()
	}

	funcao desenhar_grade()
	{
		g.definir_cor(COR_GRADE)

		inteiro x = (TAMANHO_BIT_QRCODE * MARGEM_QRCODE)
		inteiro y = (TAMANHO_BIT_QRCODE * MARGEM_QRCODE)
		
		para (inteiro i = 0; i <= 45; i++)
		{
			g.desenhar_linha(x, y + (i * TAMANHO_BIT_QRCODE), x + (45 * TAMANHO_BIT_QRCODE), y + (i * TAMANHO_BIT_QRCODE))
			g.desenhar_linha(x + (i * TAMANHO_BIT_QRCODE), y, x + (i * TAMANHO_BIT_QRCODE), y + (45 * TAMANHO_BIT_QRCODE))
		}
	}

	funcao desenhar_matriz_qrcode()
	{
		para (inteiro i = 0; i < 45; i++)
		{
			para (inteiro j = 0; j < 45; j++)
			{
				inteiro bit = matriz_qrcode[i][j]
	
				se (bit == BIT_INVALIDO)
				{
					g.definir_cor(COR_BIT_INVALIDO)
				}
				senao se (bit % 2 == 0)
				{
					g.definir_cor(COR_BIT_DESLIGADO)
				}
				senao
				{
					g.definir_cor(COR_BIT_LIGADO)
				}

				inteiro x = (TAMANHO_BIT_QRCODE * MARGEM_QRCODE) + (j * TAMANHO_BIT_QRCODE)
				inteiro y = (TAMANHO_BIT_QRCODE * MARGEM_QRCODE) + (i * TAMANHO_BIT_QRCODE)

				g.desenhar_retangulo(x, y, TAMANHO_BIT_QRCODE, TAMANHO_BIT_QRCODE, falso, verdadeiro)
			}
		}
	}	

	funcao inteiro calcular_tamanho_janela()
	{
		retorne (TAMANHO_BIT_QRCODE * 45) + (TAMANHO_BIT_QRCODE * MARGEM_QRCODE * 2)
	}

	funcao cadeia montar_bitstring_alternado()
	{
		cadeia bitstring = ""

		para (inteiro i = 0; i < 78; i++)
		{
			inteiro valor_bloco1 = codewords_bloco1[i]
			inteiro valor_bloco2 = codewords_bloco2[i]

			cadeia binario_bloco1, binario_bloco2

			binario_bloco1 = tp.inteiro_para_cadeia(valor_bloco1, 2)
			binario_bloco1 = txt.extrair_subtexto(binario_bloco1, 24, 32)
			
			binario_bloco2 = tp.inteiro_para_cadeia(valor_bloco2, 2)
			binario_bloco2 = txt.extrair_subtexto(binario_bloco2, 24, 32)

			bitstring += binario_bloco1
			bitstring += binario_bloco2
		}


		para (inteiro i = 0; i < 20; i++)
		{
			inteiro valor_bloco1 = error_codewords_bloco1[i]
			inteiro valor_bloco2 = error_codewords_bloco2[i]

			cadeia binario_bloco1, binario_bloco2

			binario_bloco1 = tp.inteiro_para_cadeia(valor_bloco1, 2)
			binario_bloco1 = txt.extrair_subtexto(binario_bloco1, 24, 32)
			
			binario_bloco2 = tp.inteiro_para_cadeia(valor_bloco2, 2)
			binario_bloco2 = txt.extrair_subtexto(binario_bloco2, 24, 32)

			bitstring += binario_bloco1
			bitstring += binario_bloco2
		}

		retorne bitstring
	}

	funcao extrair_error_codewords_bloco1(cadeia binario)
	{
		cadeia polinomio = gerar_polinomio_de_error_codewords(binario)
		inteiro tamanho = contar_compoenentes_polinomio(polinomio)

		para (inteiro i = 1; i <= tamanho; i++)
		{
			cadeia componente = extrair_componente_polinomio(polinomio, i)
			inteiro valor = obter_valor_componente(componente)

			error_codewords_bloco1[i - 1] = valor
		}		
	}

	funcao extrair_error_codewords_bloco2(cadeia binario)
	{
		cadeia polinomio = gerar_polinomio_de_error_codewords(binario)

		inteiro tamanho = contar_compoenentes_polinomio(polinomio)

		para (inteiro i = 1; i <= tamanho; i++)
		{
			cadeia componente = extrair_componente_polinomio(polinomio, i)
			inteiro valor = obter_valor_componente(componente)

			error_codewords_bloco2[i - 1] = valor
		}
	}

	funcao extrair_codewords_bloco1(cadeia binario)
	{
		para (inteiro i = 0; i < 78; i++)
		{
			cadeia bits = txt.extrair_subtexto(binario, i * 8, (i * 8) + 8)
			inteiro valor = tp.cadeia_para_inteiro(bits, 2)

			codewords_bloco1[i] = valor
		}
	}
		
	funcao extrair_codewords_bloco2(cadeia binario)
	{
		para (inteiro i = 78; i < 156; i++)
		{
			cadeia bits = txt.extrair_subtexto(binario, i * 8, (i * 8) + 8)
			inteiro valor = tp.cadeia_para_inteiro(bits, 2)

			codewords_bloco2[i % 78] = valor
		}
	}

	funcao cadeia gerar_polinomio_de_error_codewords(cadeia binario)
	{
		cadeia polinomio_mensagem = criar_polinomio_mensagem(binario)
		cadeia polinomio_gerador = POLINOMIO_GERADOR
		
		cadeia primeiro_componente = ""
		cadeia polinomio_base = ""

		inteiro numero_componentes_mensagem = contar_compoenentes_polinomio(polinomio_mensagem)

		para (inteiro i = 0; i < numero_componentes_mensagem; i++)
		{
			//escreva("STEP " + (i + 1) + "\n")
			se (i == 0)
			{
				polinomio_base = polinomio_mensagem
			}

			//escreva("MULTIPLY: \n")
			//escreva("BASE: " + polinomio_base + "\n")
			
			primeiro_componente = extrair_primeiro_componente_polinomio(polinomio_base)
			//escreva("COMP: " + primeiro_componente + "\n")
			
			cadeia polinomio_multiplicado

			polinomio_multiplicado = multiplicar_polinomio(polinomio_gerador, primeiro_componente)
			//escreva("PRE RESULT: " + polinomio_multiplicado + "\n")
			
			polinomio_multiplicado = converter_para_notacao_normal(polinomio_multiplicado)
			//escreva("RESULT: " + polinomio_multiplicado + "\n")

			polinomio_base = executar_xor_polinomios(polinomio_base, polinomio_multiplicado)

			//escreva("FINAL: " + polinomio_base + "\n\n")
		}

		retorne polinomio_base
	}

	funcao cadeia executar_xor_polinomios(cadeia polinomio_a, cadeia polinomio_b)
	{
		cadeia resultado = ""
		
		inteiro tamanho_polinomio_a = contar_compoenentes_polinomio(polinomio_a)
		inteiro tamanho_polinomio_b = contar_compoenentes_polinomio(polinomio_b)

		cadeia poli = "A"
		inteiro tamanho = tamanho_polinomio_a

		se (tamanho_polinomio_b > tamanho)
		{
			poli = "B"
			tamanho = tamanho_polinomio_b
		}

		para (inteiro i = 1; i <= tamanho; i++)
		{
			cadeia componente_a = extrair_componente_polinomio(polinomio_a, i)
			cadeia componente_b = extrair_componente_polinomio(polinomio_b, i)

			cadeia expoente_a = "INVALID"
			cadeia expoente_b = "INVALID"
			
			se (componente_a != "INVALID")
			{
				expoente_a = obter_expoente_componente(componente_a)
			}

			 se (componente_b != "INVALID")
			 {			
				expoente_b = obter_expoente_componente(componente_b)
			 }
				
			inteiro valor_a = obter_valor_componente(componente_a)
			inteiro valor_b = obter_valor_componente(componente_b)

			inteiro valor_resultante = (valor_a^valor_b)

			se (valor_resultante != 0 ou (valor_resultante == 0 e i > 1))
			{				
				se (poli == "A")
				{
					resultado += valor_resultante + expoente_a + " + "
				}
				senao se (poli == "B")
				{
					resultado += valor_resultante + expoente_b + " + "
				}
			}
		}

		retorne txt.extrair_subtexto(resultado, 0, txt.numero_caracteres(resultado) - 3)
	}

	funcao cadeia obter_expoente_componente(cadeia componente)
	{
		inteiro tamanho_componente = txt.numero_caracteres(componente)
		inteiro posicao_x = txt.posicao_texto("x", componente, 0)

		retorne txt.extrair_subtexto(componente, posicao_x, tamanho_componente)
	}

	funcao inteiro obter_valor_componente(cadeia componente)
	{
		se (componente == "INVALID")
		{
			retorne 0
		}
		
		inteiro posicao_x = txt.posicao_texto("x", componente, 0)
		cadeia valor = txt.extrair_subtexto(componente, 0, posicao_x)

		retorne tp.cadeia_para_inteiro(valor, 10)		
	}

	funcao cadeia extrair_componente_polinomio(cadeia polinomio, inteiro indice)
	{
		inteiro atual = 0
		inteiro tamanho_polinomio = txt.numero_caracteres(polinomio)
		inteiro posicao = 0

		enquanto (posicao < tamanho_polinomio)
		{
			inteiro posicao_espaco = txt.posicao_texto(" + ", polinomio, posicao)

			se (posicao_espaco < 0)
			{
				posicao_espaco = txt.numero_caracteres(polinomio)
			}

			atual++

			se (atual == indice)
			{
				retorne txt.extrair_subtexto(polinomio, posicao, posicao_espaco)
			}
			
			posicao = posicao_espaco + 3
		}

		retorne "INVALID"
	}

	funcao inteiro contar_compoenentes_polinomio(cadeia polinomio)
	{
		inteiro total = 0

		inteiro tamanho_polinomio = txt.numero_caracteres(polinomio)
		inteiro posicao = 0

		enquanto (posicao < tamanho_polinomio)
		{
			inteiro posicao_espaco = txt.posicao_texto(" + ", polinomio, posicao)

			se (posicao_espaco < 0)
			{
				posicao_espaco = txt.numero_caracteres(polinomio)
			}

			posicao = posicao_espaco + 3
			
			total++
		}		
		
		retorne total
	}

	funcao cadeia converter_para_notacao_normal(cadeia polinomio_em_notacao_alpha)
	{
		cadeia polinomio = polinomio_em_notacao_alpha

		cadeia polinomio_transformado = ""
		
		inteiro tamanho_polinomio = txt.numero_caracteres(polinomio)
		inteiro posicao = 0

		enquanto (posicao < tamanho_polinomio)
		{
			inteiro posicao_espaco = txt.posicao_texto(" + ", polinomio, posicao)

			se (posicao_espaco < 0)
			{
				posicao_espaco = txt.numero_caracteres(polinomio)
			}
			
			cadeia componente
			
			componente = txt.extrair_subtexto(polinomio, posicao, posicao_espaco)
			componente = converter_alpha_para_normal(componente)

			polinomio_transformado += componente + " + "

			posicao = posicao_espaco + 3
		}

		retorne txt.extrair_subtexto(polinomio_transformado, 0, txt.numero_caracteres(polinomio_transformado) - 3)
	}

	funcao cadeia multiplicar_polinomio(cadeia polinomio, cadeia componente_multiplicador)
	{
		inteiro alpha = obter_alpha_componente(componente_multiplicador)
		cadeia expoente
		
		expoente = obter_expoente_componente(componente_multiplicador)
		expoente = txt.extrair_subtexto(expoente, 2, txt.numero_caracteres(expoente))
		
		inteiro valor_expoente = tp.cadeia_para_inteiro(expoente, 10)

		cadeia polinomio_transformado = ""
		
		inteiro tamanho_polinomio = txt.numero_caracteres(polinomio)
		inteiro posicao = 0

		enquanto (posicao < tamanho_polinomio)
		{
			inteiro posicao_espaco = txt.posicao_texto(" + ", polinomio, posicao)

			se (posicao_espaco < 0)
			{
				posicao_espaco = txt.numero_caracteres(polinomio)
			}
			
			cadeia componente
			
			componente = txt.extrair_subtexto(polinomio, posicao, posicao_espaco)
			componente = somar_expoente_alpha(componente, alpha)
			
			componente = txt.extrair_subtexto(componente, 0, txt.posicao_texto("^", componente, 0))

			polinomio_transformado += componente + "^" + valor_expoente + " + "
			valor_expoente--

			posicao = posicao_espaco + 3
		}		

		retorne txt.extrair_subtexto(polinomio_transformado, 0, txt.numero_caracteres(polinomio_transformado) - 3)
	}

	funcao inteiro obter_alpha_componente(cadeia componente)
	{
		inteiro posicao_x = txt.posicao_texto("x", componente, 0)
		inteiro base = tp.cadeia_para_inteiro(txt.extrair_subtexto(componente, 0, posicao_x), 10)

		retorne VETOR_BASE_PARA_ALPHA[base]
	}

	funcao cadeia extrair_primeiro_componente_polinomio(cadeia polinomio)
	{
		inteiro posicao_soma = txt.posicao_texto(" + ", polinomio, 0)
		cadeia componente = txt.extrair_subtexto(polinomio, 0, posicao_soma)

		retorne componente
	}

	funcao cadeia preparar_polinomio(cadeia polinomio, inteiro incremento)
	{
		cadeia polinomio_transformado = ""
		
		inteiro tamanho_polinomio = txt.numero_caracteres(polinomio)
		inteiro posicao = 0

		enquanto (posicao < tamanho_polinomio)
		{
			inteiro posicao_espaco = txt.posicao_texto(" + ", polinomio, posicao)

			se (posicao_espaco < 0)
			{
				posicao_espaco = txt.numero_caracteres(polinomio)
			}
			
			cadeia componente
			
			componente = txt.extrair_subtexto(polinomio, posicao, posicao_espaco)
			componente = somar_expoente_x(componente, incremento)

			polinomio_transformado += componente + " + "

			posicao = posicao_espaco + 3
		}		

		retorne txt.extrair_subtexto(polinomio_transformado, 0, txt.numero_caracteres(polinomio_transformado) - 3)
	}

	funcao cadeia somar_expoente_x(cadeia componente, inteiro valor)
	{
		inteiro tamanho_texto = txt.numero_caracteres(componente)
		inteiro posicao_potencia = txt.posicao_texto("^", componente, 0)
		
		cadeia expoente = txt.extrair_subtexto(componente, posicao_potencia + 1, tamanho_texto)
		cadeia base = txt.extrair_subtexto(componente, 0, posicao_potencia)

		retorne base + "^" + (tp.cadeia_para_inteiro(expoente, 10) + valor)
	}

	funcao cadeia somar_expoente_alpha(cadeia componente, inteiro valor)
	{
		inteiro tamanho_texto = txt.numero_caracteres(componente)
		inteiro posicao_x = txt.posicao_texto("x", componente, 0)

		cadeia x = txt.extrair_subtexto(componente, posicao_x, tamanho_texto)
		cadeia alpha = txt.extrair_subtexto(componente, 1, posicao_x)
		inteiro valor_alpha = tp.cadeia_para_inteiro(alpha, 10)

		valor_alpha = (valor_alpha + valor) % 255

		retorne "@" + valor_alpha + x
	}

	funcao cadeia converter_alpha_para_normal(cadeia componente)
	{
		inteiro tamanho_texto = txt.numero_caracteres(componente)
		inteiro posicao_x = txt.posicao_texto("x", componente, 0)

		cadeia x = txt.extrair_subtexto(componente, posicao_x, tamanho_texto)
		cadeia alpha = txt.extrair_subtexto(componente, 1, posicao_x)
		inteiro valor_alpha = tp.cadeia_para_inteiro(alpha, 10)

		valor_alpha = VETOR_ALPHA_PARA_BASE[valor_alpha]

		retorne valor_alpha + x
	}

	funcao cadeia criar_polinomio_mensagem(cadeia binario)
	{
		inteiro tamanho_binario = txt.numero_caracteres(binario)
		inteiro indice_polinomio = (tamanho_binario / 8) - 1
		//inteiro indice_polinomio = 15
		cadeia polinomio = ""
		
		para (inteiro i = 0; i < tamanho_binario; i+=8)
		{
			se (indice_polinomio < 0)
			{
				pare
			}
			
			cadeia data_codeword = txt.extrair_subtexto(binario, i, i + 8)
			inteiro byte = tp.cadeia_para_inteiro(data_codeword, 2)

			polinomio += byte + "x^" + indice_polinomio

			se (indice_polinomio > 0)
			{
				polinomio += " + "
			}
			
			indice_polinomio--
		}

		retorne preparar_polinomio(polinomio, 20)
	}

	funcao inicializar()
	{
		inicializar_vetores()
		inicializar_matriz_qrcode()
	}

	funcao inicializar_vetores()
	{
		para (inteiro i = 0; i <= 255; i++)
		{
			inteiro alpha = calcular_base(i)
			
			VETOR_ALPHA_PARA_BASE[i] = alpha

			se (alpha > 1)
			{
				VETOR_BASE_PARA_ALPHA[alpha] = i
			}
		}
	}

	funcao inicializar_matriz_qrcode()
	{
		para (inteiro i = 0; i < 45; i++)
		{
			para (inteiro j = 0; j < 45; j++)
			{
				matriz_qrcode[i][j] = BIT_INVALIDO			
			}
		}
	}

	funcao inteiro calcular_base(inteiro alpha)
	{
		se (alpha <= 7)
		{
			retorne tp.real_para_inteiro(mat.potencia(2.0, alpha * 1.0))
		}
		senao se (alpha == 8)
		{
			retorne 29
		}
		senao
		{
			inteiro valor = calcular_base(alpha - 1) * 2

			se (valor >= 256)
			{
				valor = valor ^ 285
			}

			retorne valor
		}
	}

	funcao exibir_bitstring(cadeia binario)
	{
		inteiro tamanho_codigo = txt.numero_caracteres(binario)

		escreva("CONTINUOUS: " + binario + " --> " + tamanho_codigo +" bits \n\n")
		escreva("GROUPED 8B: ")

		cadeia buffer = ""
		
		para (inteiro i = 1; i <= tamanho_codigo; i++)
		{
			buffer +=  txt.extrair_subtexto(binario, i - 1, i)
			
			se (i % 8 == 0)
			{
				buffer += " "
			}
		}

		buffer += "--> " + tamanho_codigo +" bits \n"
		
		escreva(buffer)
	}

	// Obtém o tamanho do texto a ser codificado no QR CODE e converte o tamanho em uma representação binária de 9 bits
	// conforme a especificação do QR CODE

	funcao cadeia codificar_tamanho_texto(cadeia texto)
	{
		inteiro tamanho = txt.numero_caracteres(texto)
		cadeia binario = tp.inteiro_para_cadeia(tamanho, 2)

		retorne txt.extrair_subtexto(binario, 23, 32)
	}

	funcao cadeia codificar_texto(cadeia texto)
	{
		inteiro tamanho_texto = txt.numero_caracteres(texto)
		
		cadeia binario = ""

		para (inteiro i = 0; i < tamanho_texto; i += 2)
		{
			caracter primeiro_caracter, segundo_caracter = '?'
			
			 primeiro_caracter = txt.obter_caracter(texto, i)

			// Se o número de caracteres for ímpar, ignorar o segundo caracter na última execução
			
			 se (i < tamanho_texto - 1)
			 {
			 	segundo_caracter = txt.obter_caracter(texto, i + 1)
			 }

			 binario += codificar_par_de_caracteres(primeiro_caracter, segundo_caracter)
		}

		retorne binario
	}

	funcao cadeia codificar_terminador_binario(cadeia binario)
	{
		cadeia terminador = ""
		inteiro tamanho_binario = txt.numero_caracteres(binario)
		inteiro tamanho_minimo = 1248
		inteiro diferenca = tamanho_minimo - tamanho_binario

		se (diferenca >= 4)
		{
			diferenca = 4
		}

		para (inteiro i = 1; i <= diferenca; i++)
		{
			terminador += "0"
		}


		binario = binario + terminador		 
		tamanho_binario = txt.numero_caracteres(binario)

		se (tamanho_binario % 8 != 0)
		{
			inteiro pad_bits = 8 - (tamanho_binario % 8)

			para (inteiro i = 0; i < pad_bits; i++)
			{
				terminador += "0"
			}
		}

		retorne terminador
	}

	funcao cadeia codificar_pad_bytes(cadeia binario)
	{
		cadeia pad_bytes = ""

		cadeia byte_a = "11101100"
		cadeia byte_b = "00010001"

		inteiro tamanho_binario = txt.numero_caracteres(binario)

		se (tamanho_binario < 1248)
		{
			inteiro quantidade_pads = (1248 - tamanho_binario) / 8

			para (inteiro i = 0; i < quantidade_pads; i++)
			{
				se (i % 2 == 0)
				{
					pad_bytes += byte_a
				}
				senao
				{
					pad_bytes += byte_b
				}
			}
		}

		retorne pad_bytes
	}

	funcao cadeia codificar_par_de_caracteres(caracter primeiro_caracter, caracter segundo_caracter)
	{
		cadeia binario = ""

		se (segundo_caracter != '?')
		{
			inteiro codigo_primeiro = obter_codigo_do_caracter(primeiro_caracter)
			inteiro codigo_segundo = obter_codigo_do_caracter(segundo_caracter)
			
			inteiro valor = (codigo_primeiro * 45) + codigo_segundo

			// Converte o valor em uma representação binária de 32 bits
			binario = tp.inteiro_para_cadeia(valor, 2)

			// Extrai os últimos 11 bits, de acordo com a especificação do QR CODE
			binario = txt.extrair_subtexto(binario, 21, 32)
		}
		senao
		{
			inteiro codigo_primeiro = obter_codigo_do_caracter(primeiro_caracter)

			// Converte o valor em uma representação binária de 32 bits
			binario = tp.inteiro_para_cadeia(codigo_primeiro, 2)

			// Extrai os últimos 6 bits, de acordo com a especificação do QR CODE
			binario = txt.extrair_subtexto(binario, 26, 32)
		}

		retorne binario
	}

	funcao inteiro obter_codigo_do_caracter(caracter caract)
	{		
		para (inteiro i = 0; i < u.numero_elementos(VETOR_DE_CODIFICACAO); i++)
		{
			se (VETOR_DE_CODIFICACAO[i] == caract)
			{
				retorne i
			}
		}

		retorne -1
	}

	funcao tela_digitar_palavra()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(800, 600)
		g.definir_titulo_janela("Gerador de QR CODE")

		desenhar_tela_digitar_palavra()
		
		enquanto (verdadeiro)
		{	
			inteiro tecla = t.ler_tecla()
			
			se (tecla == t.TECLA_ENTER)
			{
				se (verificar_palavra())
				{
					
				}
				pare
			}
			se (tecla == t.TECLA_ESC)
			{
				g.fechar_janela()
			}
			senao se(tecla == t.TECLA_CONTROL){
				palavra = ""
			}
			senao se ((tecla >= t.TECLA_A e tecla <= t.TECLA_Z) ou (tecla >= t.TECLA_0 e tecla <= t.TECLA_9) ou tecla == t.TECLA_ESPACO ou tecla == t.TECLA_BACKSPACE)
			{
				atualizar_palavra(tecla)
			}

			desenhar_tela_digitar_palavra()
		}

		g.encerrar_modo_grafico()
	}

	funcao logico verificar_palavra()
	{
		se (palavra == "")
		{
			palavra = palavra_padrao
		}
		
		retorne (txt.numero_caracteres(palavra) > 0)
	}

	funcao desenhar_tela_digitar_palavra()
	{
		desenhar_campo_entrada()
		g.renderizar()
	}

	funcao desenhar_campo_entrada()
	{
		g.definir_tamanho_texto(14.0)
		g.definir_cor(g.COR_PRETO)
		g.definir_estilo_texto(falso, falso, falso)

		cadeia mensagem = "Digite uma frase ou palavra: "
		cadeia texto = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW|"
		
		inteiro espacamento = 10
		inteiro margem_campo = 8
		inteiro largura_campo = g.largura_texto(texto) + (margem_campo * 2)
		inteiro altura_campo = g.altura_texto(texto) + (margem_campo * 2)
		inteiro altura_mensagem = g.altura_texto(mensagem)		

		inteiro borda_vertical = 10
		inteiro borda_horizontal = 10
		inteiro largura_quadro = largura_campo + (borda_horizontal * 2)
		inteiro altura_quadro = altura_mensagem + espacamento + altura_campo + (borda_vertical * 2)
		inteiro x = 400 - (largura_quadro / 2)
		inteiro y = 300 - (altura_quadro / 2)

		g.definir_cor(g.COR_BRANCO)
		g.desenhar_retangulo(x, y, largura_quadro, altura_quadro, verdadeiro, falso)

		x = x + borda_horizontal
		y = y + borda_vertical
		
		g.desenhar_texto(x, y, mensagem)
		y = y + g.altura_texto(mensagem) + espacamento
		g.desenhar_retangulo(x, y, largura_campo, altura_campo, verdadeiro, falso)

		g.desenhar_texto(x + margem_campo, y + margem_campo, palavra + "|")		
	}

	funcao atualizar_palavra(inteiro tecla)
	{
		inteiro tamanho = txt.numero_caracteres(palavra)
		
		se (tecla == t.TECLA_BACKSPACE)
		{			
			se (tamanho >= 1)
			{			
				palavra = txt.extrair_subtexto(palavra, 0, tamanho - 1)
			}
		}
		senao se (tamanho + 1 <= max_letras)
		{
			palavra = palavra + t.caracter_tecla(tecla)
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 564; 
 * @DOBRAMENTO-CODIGO = [66, 74, 99, 114, 137, 180, 216, 228, 299, 316, 333, 368, 388, 393, 410, 427, 437, 472, 488, 515, 523, 531, 541, 550, 575, 585, 595, 615, 626, 640, 669, 674, 716, 730, 745, 756, 767, 807, 862, 870, 883, 911, 935, 966, 1006, 1014, 1022, 1051, 1062, 1076, 1090, 1120, 1126, 1141, 1152, 1175, 1202, 1210, 1235, 1269, 1298, 1329, 1342, 1380, 1390, 1396, 1431];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */