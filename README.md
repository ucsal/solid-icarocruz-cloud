# Refatoração com princípios SOLID

Este projeto foi refatorado aplicando conceitos de organização de código inspirados nos princípios do SOLID, mantendo todas as funcionalidades originais do sistema.

## O que foi feito

- Organização e melhoria da estrutura do código existente
- Separação lógica de responsabilidades dentro da própria classe principal
- Melhoria na legibilidade e manutenção do código

## Princípios aplicados

### S - Single Responsibility Principle (SRP)
As responsabilidades foram melhor organizadas dentro da classe principal, separando melhor:
- Entrada de dados
- Processamento
- Exibição

### O - Open/Closed Principle (OCP)
O código foi estruturado para facilitar futuras alterações sem impactar diretamente o funcionamento existente.

## Funcionalidades mantidas

- Cadastro de participante
- Cadastro de prova
- Cadastro de questão
- Aplicação de prova
- Cálculo de nota
- Impressão do tabuleiro (FEN)

## Estrutura atual

O sistema utiliza as seguintes classes:

- App.java → controle principal e fluxo do sistema
- Participante.java → dados do participante
- Prova.java → dados da prova
- Questao.java → dados da questão e validação de resposta
- Resposta.java → registro das respostas do participante
- Tentativa.java → controle da tentativa da prova

## Benefícios

- Código mais organizado
- Melhor legibilidade
- Facilidade de manutenção
- Preservação total das funcionalidades originais
