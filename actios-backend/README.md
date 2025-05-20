# Actios Backend

## 📌 Descrição

O **Actios Backend** é a API responsável por gerenciar eventos acadêmicos, incluindo funcionalidades como emissão de certificados, check-in de participantes e consulta de eventos por categoria. Desenvolvido com foco em escalabilidade e segurança, este projeto utiliza o framework **NestJS** com TypeScript.

## 🚀 Tecnologias Utilizadas

- **Node.js** – Ambiente de execução para JavaScript.
- **MySQL** – Banco de dados relacional utilizado para armazenar dados.
- **React** – Biblioteca JavaScript para construção de interfaces de usuário (frontend).
- **npm** – Gerenciador de pacotes para o Node.js.
- **JWT (JSON Web Token)** – Para autenticação e autorização.
- **Insomnia** – Utilizado para testes manuais de API.
- **IntelliJ IDEA** – IDE recomendada para desenvolvimento e depuração.

## 🎯 Funcionalidades

- 📄 Emissão de certificados digitais.
- ✅ Registro de check-in em eventos.
- 📚 Consulta de eventos por categoria.
- 👤 Autenticação de usuários (JWT).
- 🔐 Diferenciação entre tipos de usuários (admin, organizador, estudante).

## 📁 Estrutura do Projeto
actios-backend/  
├── src/  
│ ├── modules/  
│ │ ├── certificados/  
│ │ ├── checkin/  
│ │ └── eventos/  
│ ├── common/  
│ ├── config/  
│ └── main.ts  
├── test/  
├── .env  
├── tsconfig.json  
└── package.json  

## ⚙️ Como Rodar o Projeto

### ✅ Pré-requisitos

- Node.js (v16+)
- MySQL
- Insomnia (ou outro cliente de API REST)
- IntelliJ IDEA (opcional, mas recomendado)
- React (para a parte frontend)

### 🔧 Passos

### 1. Clone o repositório:

   ```bash
   git clone https://github.com/ArthurFloro/actios-backend.git
   cd actios-backend/actios-backend.
````
### 2. Instale as dependências:
```bash
npm install
```
### 3. Configure seu ambiente:
``` bash
cp .env.example .env
```
Atualize as variáveis de ambiente de acordo com sua configuração de banco de dados e JWT.
### 4. Rode as migrações ou sincronize as entidades (dependendo do uso do ORM).
### 5. Inicie o servidor em modo de desenvolvimento:
```bash
npm run start:dev
```
A aplicação estará disponível em http://localhost:XXXXX
### 6. Teste os endpoints via Insomnia.

# 🧪 Testes
Atualmente os testes estão sendo realizados manualmente via Insomnia. Testes automatizados podem ser adicionados futuramente.

# 🤝 Contribuindo
### 1.Faça um fork do repositório.

### 2.Crie uma branch:
``` bash
git checkout -b minha-feature
```

### 3.Commit suas alterações:
``` bash
git commit -m "feat: nova funcionalidade"
```

### 4.Envie para o seu fork:
``` bash
git push origin minha-feature
```

### 5.Abra um Pull Request para o repositório principal.

# 📝 Licença
Este projeto está sob a licença MIT. Para mais detalhes, veja o arquivo LICENSE.











