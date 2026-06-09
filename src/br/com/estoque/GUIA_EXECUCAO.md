# ⚙️ Guia de Execução e Configuração do Banco de Dados

Siga os passos abaixo para preparar o ambiente, criar o banco de dados e executar a aplicação Java pelo terminal.

## Passo 1: Preparação do Banco de Dados (MySQL)
O projeto requer um banco de dados MySQL ativo. O script de criação das tabelas e população inicial de dados já está incluso no projeto.

1. Abra o seu SGBD preferido (ex: MySQL Workbench, DBeaver).
2. Execute integralmente o script SQL fornecido na raiz do projeto (criação do banco `estoque_carros` e tabelas `carro`, `cliente`, `venda`).
3. **Importante - Credenciais de Acesso:** O sistema está configurado para conectar ao banco local (`localhost:3306`) com as seguintes credenciais na classe `br.com.estoque.util.Conexao`:
   - **Usuário:** `root`
   - **Senha:** `montanha`
   *(Caso o seu MySQL utilize outra senha, altere a constante `SENHA` na classe `Conexao.java` antes de compilar).*

## Passo 2: Compilação do Sistema
Como o projeto utiliza a estrutura de pacotes rigorosa (`br.com.estoque...`), a compilação deve ser feita a partir da pasta raiz do projeto.

Abra o terminal na pasta raiz e execute:
```powershell
javac -d bin src/br/com/estoque/*/*.java
