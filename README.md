# java-cwhs

Projeto em java de integração com HubSpot.  

### Requisitos
- Java 17+
- Spring Boot 3+
- Token OAuth 2.0 do HubSpot com escopo: 
`crm.objects.contacts.write oauth crm.objects.contacts.read`

### Paths

Paths da aplicação

###### *Health*
- Health
http://localhost:8080/health
Path para avaliar saúde do serviço(se on), pode ser add versão ou outra referência útil.


###### *Autenticação*
- Authorize
http://localhost:8080/oauth/authorize 
Referente para autenticação da conta. Onde recebe um código e redireciona para o path de callback.
*Onde se o id da conta não estiver parametrizado vai solicitar a seleção pelo operador.
*Mas se estiver parametrizado pula a etapa da seleção de conta. (*user-id)
*Para teste, recomendado executar no browser, e validar se não tem algum bloqueio como de pop.

- Callback
http://localhost:8080/oauth/callback
Responsável pela validação do secret e retorna os dados de autenticação como access_token e refresh_token.

- Token refresh
http://localhost:8080/oauth/refresh?code={code_refresh_token} 
Responsável pela recriação de token por refresh_token.

###### *CRM-Contato*
- Lista de contatos.
GET http://localhost:8080/crm/contacts

```bash
curl --location 'http://localhost:8080/crm/contacts' \
--header 'Authorization: Bearer {token-oauth}' \
--header 'Content-Type: application/json'
```

- Buscar contato por id
GET http://localhost:8080/crm/contact/{id}

```bash
curl --location 'http://localhost:8080/crm/contact/{id}' \
--header 'Authorization: Bearer {token-oauth}' \
--header 'Content-Type: application/json'
```

- Criar contato
POST http://localhost:8080/crm/contact
```bash
curl --location 'http://localhost:8080/crm/contact' \
--header 'Authorization: Bearer {token-oauth}' \
--header 'Content-Type: application/json' \
--data-raw '{"firstName":"Raimundo","lastName":"Neto","email":"netolbv@gmail.com"}'
```

- Atualizar contato
(Em desenvolvimento)

- Delete contato
(Em desenvolvimento)

###### *Webhook*

Paths de exemplo para receber as notificações de webhook, onde o tipo do evento que indica qual fluxo seguir. *Sem comportamento definido.

- POST http://localhost:8080/notification *(recomentado)*
- GET http://localhost:8080/notification?type={type-event}&ref={info-reference}

### Comandos úteis

```bash
mvn clean install
# -- com bash -- na pasta raiz da aplicação
./mvnw spring-boot:run
# -- com windows --
mvnw spring-boot:run
```
Aplicação será iniciada em: http://localhost:8080

### Links úteis
- https://br.developers.hubspot.com/docs/guides/apps/authentication/oauth-quickstart-guide
- https://br.developers.hubspot.com/docs/guides/api/crm/objects/contacts

### Step-by-spep

##### Visão macro
- preencher configuração application.yml
- Startar a aplicação
- No browser acessar o link de authorize, selecionar a conta/user tester se pedir e obter o token, copia access_token.
- Em algum path de contact, por exemplo listar, add no header da consulta: key:Authorization em value: "Bearer {access_token}"
- e voala deve exibir sua consulta


##### Visão micro
- preencher as configurações em application.yml na parte de hubspot.
Os dados são obtidos no hubspot na aplicação e o user-id na conta tester.
> aplication.yml
>> hubspot:
>>>  user-id: 123456 # opcional - user-tester
>>>  client-id: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
>>>  client-secret: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxx
>>>  redirect-uri: {url-redirect-na-aplicacao}
>>>  scopes: {scopo da aplicacao}

- Startar a aplicação
Na pasta raiz da aplicação executar o comando <code>./mvnw spring-boot:run</code>
- No browser acessar o link de authorize
http://localhost:8080/oauth/authorize
- selecionar a conta ou usuario de teste se pedir e confirme 
- Apos a confirmação a aplicação irá redirecionar internamente para o path de callback, que valida com o secret informado
- Após a etapa anterior deve ser exibida os dados de token.
access_token - é o token usado nas consultas
o refresh_token pode ser usado em para pegar um novo token quando expirar http://localhost:8080/oauth/refresh?code={refresh_token} 
- Com o acess_token copiado add no header das consultas da aplicação.

```bash
# Exemplo consulta
curl --location 'http://localhost:8080/crm/contacts' \
--header 'Authorization: Bearer {acess_token}' \
--header 'Content-Type: application/json'
```

### Melhorias possíveis
Considerando um scopo completo.
- Como é um teste deixei em uma arquitetura MVC, mas só pelo tamanho do CRM uma arquitetura hexagonal se encaixa.
- Estrutural - Separação de responsabilidades dos microserviços, com microserviços especializados.
ex.  cadastros ou só crm, autenticação, logs, gateway, segurança da aplicação é não só do hubspot.
- No caso de webhook ou outro fluxo por evento, usar strategy para separar e definir ações por tipo do evento.
- Testes unitários não teve, apessar de ter a dependencia.