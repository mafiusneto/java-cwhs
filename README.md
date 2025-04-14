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
mvnw spring-boot:run
```
Aplicação será iniciada em: http://localhost:8080

### Links úteis
- https://br.developers.hubspot.com/docs/guides/apps/authentication/oauth-quickstart-guide
- https://br.developers.hubspot.com/docs/guides/api/crm/objects/contacts

