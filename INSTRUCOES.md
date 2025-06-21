# Sistema de Eventos

## Como Executar

### Opção 1: Usando o arquivo run.bat (Mais Fácil)
1. Clique duas vezes no arquivo `run.bat`
2. Escolha entre:
   - **(1) Interface Gráfica (GUI)** - Abre janelas com botões e formulários
   - **(2) Sistema em Linha de Comando (CMD)** - Menu de texto no terminal

### Opção 2: Usando Maven diretamente
```bash
cd sistema-eventos
mvn exec:java -Dexec.mainClass="com.projeto.Main"
```

### Opção 3: Compilar e executar manualmente
```bash
cd sistema-eventos
mvn compile
mvn exec:java -Dexec.mainClass="com.projeto.Main"
```

## Funcionalidades Disponíveis

### Interface Gráfica (GUI)
- Cadastro de participantes, palestrantes e eventos
- Listagem com tabelas interativas
- Filtros por data, duração e idade
- Consultas com JOIN entre tabelas
- Estatísticas do sistema

### Sistema em Linha de Comando (CMD)
- Menu completo de cadastros
- Exibição de informações
- Busca por intervalos de datas
- Busca de participantes por nome
- Remoção de registros

## Requisitos
- Java 17 ou superior
- Maven 3.6 ou superior
- PostgreSQL (configurado no hibernate.cfg.xml)