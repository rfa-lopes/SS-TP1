# Software Security - Authentication (TP1)
Authentication is the mechanism that allows a system to convince itself that some actor is acting under the authority (and responsibility) of a given principal, that the given actor is the “authentic” principal.
The authenticator is the component of the application security infrastructure responsible by authentication.

---

## Quick start

### Clonar repositório
```bash
cd webapps
git clone https://github.com/rfa-lopes/SS-TP1.git
```

### Instalar configurações TLS
```bash
cd webapps
cp -f ../conf/server.xml /SS-TP1/TLS/server.xml
```

### Instalar MariaDB-Windows10 (opcional)

**Nota**: SQLite está pronto a testar sem instalações apriori. Caso não se queira perder tempo com instalações do MariaDB o servidor está pronto a ser utilizado. Para configurar o servidor para que use MariaDB, troque a variavel USE_MARIA para *true* [aqui](src/Config/Configs.java).

1) Download do zip: https://downloads.mariadb.org/mariadb/10.5.2/
2) Copiar e descomprimir para webapp/WEB-INF/database2/
3) Instalar:
```bash
mysql_install_db.exe --password=mariadb
mysqld.exe
```

**Nota**: Credênciais root:mariadb, configurável [aqui](src/Config/Configs.java)

4) Testar instalação: 
```bash
mysql.exe -uroot -pmariadb
```
### Compilar código

**Nota:** A compilação foi feita utilizando o Intelliji.

### Correr servidor
```bash
cd bin
catalina.bat run
chrome.exe https://localhost:8443/SS-TP1
```

### Credênciais do Admin
* username: root
* password: toor

**Nota:** Configurável [aqui](src/Config/Configs.java)

---

## Diretorias
* src - Código fonte.
* TLS - Ficheiros para configurações TLS
* WEB-INF - Compilado.
* WEB-INF/database - Base de dados (SQLite).
* WEB-INF/database2 - Base de dados (MariaDB).

---

## TLS - ficheiros [aqui](/TLS/)
* Algorithm: RSA
* Key alias: ServerCert
* Keystore: ServerKeyStore.jks
* Keystore password: changeit
* Key size: 4096
* Store type: pkcs12

Comando para criar a keystore [aqui](/TLS/commands.txt)
Ficheiro de configuração para o tomcat (server.xml)[/TLS/server.xml]

---

## Arquitectura
### Filtros
Para uma maior segurança adicionámos três filtros ao nosso sistema, um que fica encarregue de autenticar o utilizador de modo a saber quem este é (Authenticator Filter); outro cuja sua função é a verificação de privilégios para que cada utilizador tenha acesso apenas às funcionalidades que lhe são dirigidas (Roles Filter); e por fim, e para mitigar por completo ataques como SQL Injection e XSS, adicionamos um filtro que fica encarregue de monitorizar todo o input vindo dos utilizadores (Input Filter). Para facilitar em seguida temos uma visualização geral de como estes são usados de acordo com o tipo de pedido que o sistema recebe.

![Tokens](Documentation/Filters.png)

### Sistema de tokens (JWT)
Entre as duas opções que ponderamos (JWT ou Session Cookie), escolhemos a JWT por ser uma implementação mais desafiante e que nos obrigou a resolver alguns problemas. Também entendemos desde logo que utilizando JWT não precisariamos proteger a memória do sistema, sendo que cada cliente guarda as suas cookies e não o servidor (como acontece utilizando Sessions Cookies). Estes JWT são assinados pelo servidor e contém informação útil para o servidor autenticar o cliente. O nosso JWT foi implementado com o aspecto seguinte:

![Tokens](Documentation/JWT.png)

Como se pode ver na figura anterior, o JWT é contituido em 3 componentes:

#### HEADER
Onde contém informação sobre o token, o seu tipo (JWT) e o algoritmo utilizado na assinatura deste. Pensamos que esta informação poderia ser secreta de modo a não fornecer ao atacante qualquer tipo de informação sobre o token de modo a mitigar ainda mais possiveis ataques.

#### PAYLOAD
Onde está a informação do cliente útil para a sua autenticação. Pesquisamos sobre que tipo de informação poderia conter o payload no RFC 7519 que pode ser visto [aqui](https://tools.ietf.org/html/rfc7519#page-8).
* JTI - Username do utilizador.
* SUB - Assunto do token.
* EXP - Data de expiração do token.
* IAT - Data em que foi criado.

#### SIGNATURE
A assinatura de todo o token (Header + Payload) serve para o servidor poder verificar a sua integridade, de modo a mitigar possiveis ataques de adulteração.

Após a implementação deste tipo de autenticação e depois de alguma pesquisa, fomos confrontados com o facto de que meter a password de X em X tempos (sendo X a diferença entre EXP e IAT) é algo inconveniente para um utilizador que poderá utilizar este sistema várias vezes por dia. Para isso decidimos implementar uma extenção ao nosso sistema de tokens, adicionado ao JWT um Refresh Token, que tem como objetivo servir como token de autenticação para uma revalidação de um token JWT sem que o utilizador se apreceba e continua a sua intereção com o sistema de modo transparente e seguro, visto que este fica guardado de forma segura (Utilizando a flag httpOnly de modo a mitigar ataques XSS) em local storage no browser do cliente e só é usado para revalidar novos JWT token. O Refresh Token tem o aspecto seguinte:

![Tokens](Documentation/RefreshToken.png)

É possivel verificar que a estrutura é semelhante à do JWT utilizado nos pedidos normais, com a diferença que este têm um tempo de expiração muito maior que poderá rondar os 14 a 60 dias, sendo que na nossa aplicação preferimos reduzir esse tempo a 20 horas, sendo este facilmente modificado no ficheiro de configurações e segredos [aqui](src/Config/Configs.java).

A implementação deste sistema pode ser visto na figura seguinte, onde simulamos alguns pedidos do cliente ao sistema.

![Tokens](Documentation/TP1.png)

**Nota**: Para a mitigação de eventuais ataques aos sistema de tokens, decidimos utilizar encriptação simétrica de forma a esconder a informação deste token e eventuais informações relevantes a possiveis atacantes. A chave utilizada para este feito tem um tamanho de 128 bits (16 bytes) para que haja uma maior agilização e rapidez na encriptação e decriptação destes tokens de modo não perder mais velocidade na resposta ao cliente.

### Base de dados

#### Accounts table
username  | passwordHash            | isloggedin | islocked |  usertype
  --------| ------------------------|------------|----------|-------------
  root    | Af3ddsIjq...Lfg41Hg==   |     1      |    0     | ADMIN
  Rodrigo | qgUS75wu2...OX2yZuA==   |     0      |    0     | ACCOUNT
  Miguel  | 4fQDlIjq3...uyOX1Hg==   |     0      |    1     | ACCOUNT
  
**Nota:** [passwordHash] = password do utilizador mais 'salt', de forma a que passwords iguais entre utilizadores diferentes não apareção iguais na base de dados.

Exemplo:

username  | passwordHash            |passwordText| 
  --------| ------------------------|------------|
  user1   | Af3ddsIjq...Lfg41Hg==   |     123456 |
  user2   | qgUS75wu2...OX2yZuA==   |     123456 |
  
#### Loggers table

  id  | username               | operation  | date                |
  ----|------------------------|------------|---------------------|
  0   | root                   | LOGIN      | 04-05-2020 09:45:43 |
  1   | root                   | CREATE     | 04-05-2020 09:46:03 |
  2   | root                   | LOGOUT     | 04-05-2020 09:47:17 |
  3   | rodrigo                | LOGIN      | 04-05-2020 09:47:53 |
  4   | rodrigo                | CHANGE     | 04-05-2020 10:55:22 |
  
A Logger Table é um tabela que contém todos os movimentos de todos os utilizadores, sendo as operações as seguintes:
* LOGIN: Login
* LOGOUT: Logout
* CREATE: Create account
* REMOVE: Remove account
* LOCK_UNLOCK: Lock or unlock account
* LIST: List all users
* CHANGE: Change password
* LOGGERS: Get Loggers

---
## Estrutura de Confidencialidade

![Tokens](Documentation/ConfLattice.png)

Dividimos a estrutura de confidencialidade em 4 entidades: 
* *Public*, onde qualquer entidade tem acesso. Aqui a única funcionalidade permitida é o Login.

* *Account*, que são as contas dos utilizadores que estão autenticados. São permitidas interações com: Logout, Change-password e todas de dominio público.

* *Admin*, sendo este o administrador do sistema, tem poder epara operar em qualquer funcionalidade oferecida pelo sistema, que são: Create-account, remove-account, get-account, lock-account e todas as anteriormente mencionadas.

* *Secrets*. Achamos por bem acrescentar esta "entidade" sendo este o sistema em si, e o único que têm acesso às chaves de encriptação, assinaturas, etc.

---

## Funcionalidades
* Login
* Logout
* Mudar password de uma conta
* Criar conta (admin)
* Apagar conta (admin)
* Obter dados de uma conta (admin)
* Bloquear um utilizador (admin)
* Listar todos os utilizadores (admin)
* Listar Loggers (admin)

---

## EXTRAS
* Número máximo de tentativas de passwords incorretas utilizando remote address.
* Implementação com Refresh token.
* Encriptação de tokens (configurável).
* Ficheiros JSP.
* Mecanisco de Anti-SQLInjection / XSS (inputFilter).
* MariaDB.

---

## Ferramentas e Tecnologias
* [Intelliji](https://www.jetbrains.com/idea/)
* [SQLite](https://www.sqlite.org/download.html)
* [MariaDB](https://mariadb.org/)
* [Tomcat 8.5.53](https://tomcat.apache.org/)
* [BurpSuit](https://portswigger.net/burp)

**Notas:**
* SQLite: Utilizado para testes rápidos durante o desenvolvimento.
* MariaDB: Utilizado no deploy da aplicação.
* Burpsuit: Utilizado para testes da aplicação e testes de intrusão ao sistema.

---

## Utils

### SQL Queries
```sql
show databases;
use authenticator;
show tables;
select * from accounts;
select * from loggers;
```

### Comandos Git
```bash
https://github.com/rfa-lopes/SS-TP1.git
git pull origin master
git add .
git commit -m "Initial commit"
git push
git rm -r --cached Path/to/directories
```

## Informação adicional

### Autores
* Rodrigo Lopes - rfa.lopes@campus.fct.unl.pt
* Miguel Teodoro - mt.moreira@campus.fct.unl.pt
