
# Sistema simples de portaria digital - Desafio AGT

Esse projeto é uma API para uma portaria digital. O intuito principal dessa aplicação é modernizar, facilitar e padronizar uma portaria que realiza o controle de entrada e saída de veículos, além de persistir os dados e gerar históricos sobre as viagens realizadas.
## Stack utilizada

**Back-end:** Java 21, Spring Boot 3.4.7

**Front-end:** Angular 19, PrimeNG 19
 
**Principais Dependências:** Spring Data JPA, Lombok e H2
## Documentação

A documentação do Projeto foi realizada atraves do Swagger, utilizando a dependência do springdoc. Ela está disponível assim que o projeto estiver rodando através da URL [localhost:8080/swagger-ui.html](localhost:8080/swagger-ui.html)


## Rodando localmente

Certifique-se de ter o seguinte software instalado em sua máquina:

- Java Development Kit (JDK) 21 ou superior
- Maven 3.x ou superior
- Git

Clone o projeto

```bash
  git clone https://github.com/sspedroo/desafio_agt.git
```

Entre no diretório do projeto

```bash
  cd desafio_agt
```

Instale as dependências

```bash
  mvn clean install
  mvn spring-boot:run
```

Acesse a aplicação, no caso a documentação:

```bash
  http://localhost:8080/swagger-ui.html
```

Caso prefira e no caso é minha preferência, apenas abra o projeto no Intellij ou IDE de sua escolha e rode o projeto.
## Funcionalidades

- CRUD completo de Veículos
- CRUD completo de Funcionários
- Registro de Viagens (saída e retorno)
- Validações sobre Funcionários, Veículos e Viagens


## Escolhas

### Banco de Dados

Eu escolhi o H2 como banco de dados devido a necessidade de ser um código aberto e não especificamente um deploy realizado, então por uma questão de praticidade e segurança foi escolhido o H2 invés do PostgreSQL do Supabase, porém o projeto já possui a dependência do Postgre sendo apenas necessário a configuração da conexão no `application.yml`

### Validações de Regra de Negócio

Eu utilizei Bean Validations para os DTOs (sintaxe/formato) e um "Validator Pattern"(lógica e regras) para os services. O intuito dessa minha escolha é separar corretamente cada validação e também facilitar no futuro caso seja necessário adicionar, remover ou alterar validações


## Melhorias futuras

Para esse projeto eu enxergo e até desejo com mais tempo adicionar mais algumas funcionalidades e melhorias que irão agregar valor ao projeto, de inicio não apliquei devido a um pensamento de "over-engineering" devido ao escopo proposto no desafio, mas as possiveis melhorias que vi são:
- Autenticação e Autorização: (Implementar segurança com token JWT para login e separar funcionários por role como: ADMIN, PORTEIRO, MOTORISTA(USUARIO_COMUM))
- Geração de relatórios: (Gerar relatórios com dados sobre as viagens, funcionários e os veiculos. EX: Quantas viagens feitas no mês por veículo ou colaborador, média de duração da viagem, dados quantitativos dos veículos e dos colaboradores)
- Notificar um ADMIN ou responsável sobre quando um veículo inicia viagem ou encerra viagem
- Implementar uma comunicação em tempo real com o frontend usando SSE (Server Side Events) ou Websockets invés de um pooling.


## Screenshots

<img width="1491" height="554" alt="image" src="https://github.com/user-attachments/assets/281126f2-dddf-4ea9-9dc1-fd088cd3408f" />
<img width="1629" height="837" alt="image" src="https://github.com/user-attachments/assets/3ef05499-4a37-4d8b-a675-774309987f1a" />
<img width="1556" height="816" alt="image" src="https://github.com/user-attachments/assets/efe87de3-e02f-4cd7-b0a0-95b9cb564b1e" />


## Autores

- [Pedro Oliveira](https://www.github.com/sspedroo)


## Feedback

Se você tiver algum feedback, por favor me informe por meio de 012pedropaulo@gmail.com

