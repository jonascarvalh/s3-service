
# S3 Service 🚀

Serviço backend desenvolvido com **Spring Boot + Java 17**, focado no gerenciamento de uploads de arquivos para o Amazon S3. Esta aplicação fornece uma API REST robusta para operações de armazenamento em nuvem, seguindo boas práticas de arquitetura e segurança.

---

## 📚 Descrição

Este repositório implementa um serviço completo para gerenciar arquivos no Amazon S3, demonstrando:
- Arquitetura em camadas com Spring Boot
- Integração segura com AWS S3
- API REST bem estruturada
- Configuração flexível através de variáveis de ambiente
- Tratamento robusto de erros

---

## 🧱 Arquitetura do Projeto

A aplicação segue uma arquitetura em camadas bem definida:

```
src/main/java/br/com/s3/
 ┣ adapter/         # Adaptadores para serviços externos (S3)
 ┣ config/          # Configurações de segurança e beans
 ┣ controller/      # Controladores REST com DTOs
 ┣ service/         # Lógica de negócio
 ┗ S3ServiceApplication.java  # Classe principal da aplicação
```

### Estrutura de Pacotes:
- **Controller**: Endpoints REST para upload de arquivos
- **Service**: Lógica de negócio para gerenciamento de anexos
- **Adapter**: Integração com AWS S3
- **Config**: Configurações de segurança e propriedades
- **DTO**: Objetos de transferência de dados

---

## ✅ Funcionalidades

- [x] Upload de arquivos para Amazon S3
- [x] API REST para gerenciamento de anexos
- [x] Configuração flexível via variáveis de ambiente
- [x] Segurança implementada com Spring Security
- [x] Tratamento robusto de erros
- [x] Suporte a diferentes regiões AWS

---

## 🧠 Boas Práticas Adotadas

- ✅ Arquitetura em camadas bem definida
- ✅ Injeção de dependências com Spring
- ✅ DTOs para transferência de dados
- ✅ Tratamento adequado de exceções
- ✅ Configuração externalizada
- ✅ Segurança implementada
- ✅ Código limpo e bem documentado

---

## 📦 Principais Dependências

- `spring-boot-starter-web` - Framework web
- `spring-boot-starter-security` - Segurança da aplicação
- `spring-boot-starter-data-jpa` - Persistência de dados
- `software.amazon.awssdk:s3` - Cliente AWS S3
- `lombok` - Redução de boilerplate
- `h2database` - Banco de dados em memória para testes

---

## ⚙️ Configuração

### Variáveis de Ambiente

| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `S3_BUCKET` | Nome do bucket S3 | `local-bucket` |
| `S3_REGION` | Região AWS do bucket S3 | `sa-east-1` |

### Exemplo de Configuração

```bash
# Linux/Mac
export S3_BUCKET=meu-bucket-producao
export S3_REGION=us-east-1

# Windows PowerShell
$env:S3_BUCKET="meu-bucket-producao"
$env:S3_REGION="us-east-1"

# Windows CMD
set S3_BUCKET=meu-bucket-producao
set S3_REGION=us-east-1
```

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- Credenciais AWS configuradas

### Execução Local
```bash
# Compilar e executar
mvn spring-boot:run

# Ou compilar separadamente
mvn clean compile
mvn spring-boot:run
```

### Execução com JAR
```bash
mvn clean package
java -jar target/s3-service-0.0.1-SNAPSHOT.jar
```

---

## 📡 Endpoints da API

### Upload de Arquivo
```
POST /api/attachments/upload
Content-Type: multipart/form-data

Parâmetros:
- file: Arquivo a ser enviado
- bucket: Nome do bucket (opcional, usa padrão se não informado)
```

### Exemplo de Uso
```bash
curl -X POST \
  http://localhost:8080/api/attachments/upload \
  -H 'Content-Type: multipart/form-data' \
  -F 'file=@documento.pdf'
```

---

## 🔒 Segurança

- Spring Security configurado
- Endpoints protegidos por padrão
- Configuração customizável de autenticação

---

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Executar testes específicos
mvn test -Dtest=S3ServiceApplicationTests
```

---

## 📊 Status do Projeto

🔧 Em desenvolvimento  
📤 Deploy: Configurável para diferentes ambientes  
🌐 AWS S3: Integração completa  
🔒 Segurança: Implementada  

---

## 🤝 Contribuições

Para contribuir com o projeto:

1. Faça um fork do repositório
2. Crie uma branch para sua feature
3. Implemente as mudanças
4. Adicione testes se necessário
5. Faça commit das alterações
6. Abra um Pull Request

---

## 📝 Licença

Este projeto está sob licença MIT. Veja o arquivo LICENSE para mais detalhes.
