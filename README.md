# Amâzonia R.C, Inc.

A empresa de representação comercial, Amâzonia Representação Comercial está modernizando seus sistemas legados e você recebeu a missão de remodelar uma das aplicações cores que atualmente é responsável pelo carrinho de compra, checkout, confirmação do pedido por e-mail e realizar a cotação do frete de forma integrada ao sistema do Correios.

Por está razão você como Engenheiro de Software foi designado redesenhar buscando melhorar a manutenibilidade, legibilidade, testabilidade e qualidade da aplicação.

* Caso o cliente informe um cupom de desconto, deve-se aplicar somente para os produtos que estão em promoção
  
* Caso o cliente escolha receber o produto no formato de mídia digital (filmes/séries, livros ou músicas), não deve-se cobrado o frete

* Caso os itens sejam livros, jornais, revistas ou eReaders, o sistema deve idetificar e aplicar a isenção de imposto conforme disposto na Constituição Art. 150, VI, d.

* Para o calculo do frete deve-se utilizar o WebService dos Correios (Collection do Postman de exemplo no repositório)

* Ao final do checkout o cliente deverá receber um e-mail com a confirmação da compra

Tabela com os códigos dos serviços oferecidos pelos Correios.

Código Serviço | Serviço
---------------|---------
04510|PAC sem contrato
41068|PAC com contrato
04669|PAC com contrato
41300|PAC para grandes formatos
04014|SEDEX sem contrato
40045|SEDEX a Cobrar, sem contrato
40126|SEDEX a Cobrar, com contrato
40215|SEDEX 10, sem contrato
40290|SEDEX Hoje, sem contrato
40096|SEDEX com contrato
40436|SEDEX com contrato
40444|SEDEX com contrato
40568|SEDEX com contrato
40606|SEDEX com contrato
04162|SEDEX com contrato
81019|e-SEDEX, com contrato
81027|e-SEDEX Prioritário, com contrato
81035|e-SEDEX Express, com contrato
81868|(Grupo 1) e-SEDEX, com contrato
81833|(Grupo 2) e-SEDEX, com contrato
81850|(Grupo 3) e-SEDEX, com contrato

## O que é necessário fazer?

Você ficou designado a prototipar como poderá ser feita a nova versão deste fluxo de venda e envio do produtos, pois a versão atual é frágil, encadeada em if/else, switch/case, exigindo modificações grandes a cada nova regra de envio/processamento inserida ou removida.

Crie as classes, métodos e suas respectivas chamadas (a seu critério) para que você consiga tratar os cenários acima.

Não é necessário criar as implementações para envio de e-mails. Para este caso crie apenas as chamadas de métodos, para indicar que ali seria o local aonde o envio ocorreria.

Entretanto, levaremos isso como bonus points. É permitido o uso de libs para facilitar a implementação dos testes.

## O que será avaliado?

Sua capacidade de analisar, projetar e codificar uma solução guiando-se com Design Orientado a Objetos, Princípios de Orientação a Objetos, Clean Code e melhores práticas de engenharia e arquitetura de software.

## O que não vale?

* Frameworks

## Qual linguagem?

* Java

## Apresentação

* Código funcional
* Cobertura de testes em pelo menos 90%
* Explicação da solução em Markdown ou PPT

## Avalição

Para nos enviar o seu código, você pode:

* Fazer um fork desse repositório, e nos mandar um pull-request
* Enviar o link do repositório de código fonte (que seja público)