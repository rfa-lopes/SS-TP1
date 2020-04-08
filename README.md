# Software Security - Authentication (TP1)
Authentication is the mechanism that allows a system to convince itself that some actor is acting under the authority (and responsibility) of a given principal, that the given actor is the “authentic” principal.
The authenticator is the component of the application security infrastructure responsible by authentication.

---

## Quick start
```bash
cd webapps
git clone https://github.com/rfa-lopes/SS-TP1.git
cd ../bin
catalina.bat run
chrome.exe http://localhost:8080/SS-TP1/
```
---
## Diretorias
* /WEB-INF - Compilado.
* /WEB-INF/database - Base de dados.
* /src - Código fonte.

---

## Arquitectura
### Sistema

//TODO

### Base de dados

#### Accounts table
username  | passwordHash            | isloggedin | islocked |  usertype
  --------| ------------------------|------------|----------|-------------
  root    | Af3ddsIjq...Lfg41Hg==   |     1      |    0     | ADMIN
  Rodrigo | qgUS75wu2...OX2yZuA==   |     0      |    0     | ACCOUNT
  Miguel  | 4fQDlIjq3...uyOX1Hg==   |     0      |    1     | ACCOUNT

---

## Funcionalidades
* Login
* Logout
* Criar conta (admin)
* Apagar conta (admin)
* Obter dados de uma conta (admin)
* Mudar password de uma conta

---

## Ferramentas e Tecnologias
* [Intelliji](https://www.jetbrains.com/idea/)
* [SQLite](https://www.sqlite.org/download.html)
* [Tomcat 8.5.53](https://tomcat.apache.org/)

---

## Informação adicional

### Comandos Git
```bash
https://github.com/rfa-lopes/SS-TP1.git
git pull origin master
git add .
git commit -m "Initial commit"
git push
git rm -r --cached Path/to/directories
```

### Autores
* Rodrigo Lopes - rfa.lopes@campus.fct.unl.pt
* Miguel Teodoro - mt.moreira@campus.fct.unl.pt
