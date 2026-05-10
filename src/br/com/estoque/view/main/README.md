## Sistema de Gestão de Estoque de Carros
**Disciplina:** Programação de Soluções Computacionais 
**Professor:** Alexandre de Oliveira

---

## Sobre o Projeto
Este projeto foi criado para ajudar lojas de carros a organizarem melhor o seu inventário. O objetivo é substituir anotações manuais ou planilhas por um sistema robusto que centraliza as informações de forma organizada e segura.

## Status da Entrega: Sprint 1 (11/05/2026)
Nesta primeira etapa, foi implementada a base do sistema seguindo a arquitetura proposta no UML:
- [x] **Estrutura de Pastas:** Organização em camadas (MVC adaptado: model, dao, service, controller, view).
- [x] **Interface Inicial:** Menu interativo via terminal.
- [x] **Requisitos Técnicos:** Uso de laços de repetição (`while`), estruturas de decisão (`switch/case`) e tratamento de exceções (`try/catch`).

##  O que o programa faz? (Funcionalidades Previstas)
- **Controle de carros:** Cadastro, listagem, atualização e remoção.
- **Busca de veículos:** Filtros rápidos por modelo ou marca.
- **Registro de vendas:** Vinculação de clientes a veículos com atualização automática de estoque.
- **Persistência:** Integração futura com banco de dados MySQL.

##  Como Executar
Como o projeto utiliza pacotes (packages), siga estes passos no terminal:

1. **Navegue até a pasta raiz do projeto:**
   ```powershell
   cd sistema-estoque-carros