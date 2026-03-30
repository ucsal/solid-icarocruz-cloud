# Refatoração SOLID - Sistema Olimpíadas

## Objetivo
Refatorar o sistema aplicando os princípios SOLID sem mudar o funcionamento original.

## O que foi feito

- Separei a lógica do sistema em classes diferentes
- Tirei responsabilidades da classe App
- Organizei melhor o código

## Aplicação do SOLID

### SRP (Responsabilidade Única)
A classe App foi dividida em:
- ParticipanteService
- ProvaService
- NotaService

Cada classe agora faz apenas uma coisa.

### OCP (Aberto/Fechado)
O código agora pode ser expandido sem precisar modificar o que já existe.

### LSP (Substituição de Liskov)
As classes mantêm comportamento consistente e podem ser usadas sem quebrar o sistema.


### ISP (Segregação de Interface)
As responsabilidades foram separadas em classes menores.

### DIP (Inversão de Dependência)
A classe App passou a usar serviços ao invés de conter toda a lógica.

## Estrutura
