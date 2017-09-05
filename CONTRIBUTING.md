# Contribuindo com o Portugol Studio

👍🎉 Primeiramente, obrigado por utilizar seu tempo contribuindo! 🎉👍

Esse projeto segue um código de conduta. Ao participar, é esperado que o código seja seguido. Favor reportar comportamentos inaceitáveis.

O texto a seguir é um conjunto de diretrizes para a contribuir com o Portugol Studio. Estas são apenas diretrizes e não regras, use o seu bom senso e sinta-se livre para propor alterações nesse documento em um pull request.

## Reportando Issues

Você pode criar uma issue [aqui](https://github.com/UNIVALI-LITE/Portugol-Studio/issues/new), mas antes disso, por favor, leia as notas abaixo e inclua o máximo de detalhes possíveis em seu relato. Se puder, favor informar:
* Descrição do Problema:
* Mensagem de Erro (se houver):
* Captura de Tela (pode ser em GIFs animados) / Arquivo.por:
* Especificações do sistema:

## Submetendo Pull Requests

* Inclua capturas de tela e GIFs animados em seu pull request sempre que possível.
* Siga os padrões de código.
* Escreva a documentação em [Markdown](https://daringfireball.net/projects/markdown).
* Use mensagens curtas e com a conjugação verbal no tempo presente. Veja em [Guia de estilo de mensagens de commit](#git-commit-messages).

## Guias de estilo

### Código Geral

* Terminar arquivos com uma linha em branco.
* Evite importar classes que não serão utilizadas.
* Use um simples `return` para retornar explícitamente o fim de uma função.
  * Não usar `return null` ou `null`

### Mensagens de Commit do Git

* Use o tempo presente ("Adicionando função" não "Adicionada função")
* Use o modo imperativo ("Mova o cursor para..." não "O cursor deve ser movido...")
* Limite a primeira linha para 72 caracteres ou menos
* Referencie issues e pull requests livremente
* Considerando iniciar a mensagem do commit com um emoji:
  * :art: `:art:` quando aperfeiçoar o formato/estrutura do código
  * :racehorse: `:racehorse:` quando aperfeiçoar a performance
  * :non-potable_water: `:non-potable_water:` quando previnir vazamento de memória
  * :memo: `:memo:` quando escrever documentação
  * :penguin: `:penguin:` quando corrigir algo no Linux
  * :apple: `:apple:` quando corrigir algo no OSX
  * :checkered_flag: `:checkered_flag:` quando corrigir algo no Windows
  * :bug: `:bug:` quando corrigir um bug
  * :fire: `:fire:` quando remover código ou arquivos
  * :green_heart: `:green_heart:` quando corrigir o build do CI
  * :white_check_mark: `:white_check_mark:` quando adicionar testes
  * :lock: `:lock:` quando estiver lidando com segurança
  * :arrow_up: `:arrow_up:` quando atualizar dependências
  * :arrow_down: `:arrow_down:` quando abaixar as dependências
  * :shirt: `:shirt:` quando remover avisos
