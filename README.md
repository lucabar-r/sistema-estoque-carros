# Sistema de Gestão de Estoque Automotivo

Este projeto nasceu da necessidade de organizar o dia a dia de uma concessionária ou revenda de veículos. [cite_start]Em vez de planilhas confusas, estamos construindo uma ferramenta em Java que centraliza o controle de carros, clientes e o histórico de vendas em um só lugar.

### O que o sistema faz?
A ideia é ter um fluxo completo para gerenciar o negócio:
* [cite_start]**Controle de Inventário:** Cadastro detalhado de veículos, permitindo saber exatamente o que está no pátio e o que já saiu[cite: 6, 10].
* [cite_start]**Gestão de Clientes:** Registro de quem está comprando, mantendo o histórico organizado[cite: 14].
* [cite_start]**Registro de Vendas:** Um fluxo que valida o estoque antes de fechar o negócio e atualiza a quantidade automaticamente[cite: 11, 25].
* [cite_start]**Busca Inteligente:** Encontrar modelos ou marcas específicas direto no banco de dados[cite: 8].

### Como estamos construindo
Para garantir que o código seja limpo e fácil de manter, estamos utilizando:
* [cite_start]**Arquitetura MVC (Adaptada):** Dividimos o projeto em camadas (Model, DAO, Service, Controller e View) para que cada parte do código tenha sua responsabilidade bem definida[cite: 31, 34, 35].
* **Persistência Real:** Nada de dados que somem ao fechar o programa. [cite_start]Usaremos um banco de dados relacional (MySQL/PostgreSQL) para guardar tudo com segurança[cite: 73, 74].
* [cite_start]**POO de Verdade:** Aplicamos os pilares da Orientação a Objetos para que o sistema seja robusto e escalável[cite: 33, 66].

###  O Time
[cite_start]Este projeto faz parte da Unidade Curricular de **Programação de Soluções Computacionais (2026/1)** no UniBH, sob orientação do professor **Alexandre "Montanha" de Oliveira**[cite: 43, 44, 45].

* **Integrantes:**
    * Luca Barbosa Rodrigues
    * Luiz Gustavo Soares Moreira
    * Luciano Mendes de Oliveira Gratarolli
    * Nicolas
    * Lucas
    * Filipe
