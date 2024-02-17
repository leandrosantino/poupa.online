# Poupa.online

[![Licença](https://img.shields.io/badge/license-MIT-green.svg?style=flat)](https://choosealicense.com/licenses/MIT/)

[![Minhas Habilidades](https://skillicons.dev/icons?i=java,spring,angular,docker,postgresql,nginx,aws
)](https://skillicons.dev)

O **Poupa.online** é uma plataforma desenvolvida para facilitar o gerenciamento de contribuições financeiras entre grupos específicos, como amigos ou familiares. Semelhante a uma vaquinha online, o Poupa.online permite que os membros do grupo definam uma meta financeira e contribuam com pagamentos predeterminados para alcançá-la coletivamente.

A plataforma facilita a gestão dos pagamentos e gera o QR code para pagamento via Pix. Para implementar essa funcionalidade, utilizei a API de pagamentos do MercadoPago. No momento, o site está vinculado apenas à minha conta do MercadoPago. Seria desejável criar um sistema de autenticação para que cada usuário recebesse os pagamentos em sua própria conta. No entanto, por se tratar de um projeto de estudo, optei por não desenvolver essa parte agora.

## Acesse o Poupa.Online

Desenvolver o **Poupa.online** foi uma experiência valiosa para mim, proporcionando aprendizado em todas as tecnologias utilizadas no desenvolvimento. Exceto pelo Docker e pelo PostgreSQL, nunca havia trabalhado de forma prática com essas tecnologias antes.

Você pode acessar o projeto através do seguinte link: [http://poupa.online](http://poupa.online/). Para visualizar os pagamentos, utilize o CPF: 123.456.789-09.

## Stack Utilizada

O back-end da aplicação foi desenvolvido utilizando Java com Spring Boot, enquanto o front-end foi construído com Angular.js. O banco de dados escolhido foi o PostgreSQL. Para o deployment, utilizei a AWS e criei contêineres Docker para rodar a aplicação. Ela foi dividida em quatro contêineres: Database, API (back-end), APP (front-end) e Proxy. No contêiner do Proxy, configurei o Nginx como proxy reverso para gerenciar as requisições e definir um subdomínio para o contêiner do back-end.

**Back-end:**
- Java
- Spring Boot
- PostgreSQL
- MercadoPago API

**Front-end:**
- Angular.js

**Infraestrutura:**
- AWS (Amazon Web Services)
- Docker
- Nginx (Proxy reverso)


## Aprendizados

Durante o desenvolvimento deste projeto, adquiri conhecimentos valiosos em diversas áreas, incluindo:

- Implementação de pagamentos via API com MercadoPago
- Desenvolvimento de aplicações full-stack com Java, Spring Boot e Angular.js
- Utilização de contêineres Docker para deployment de aplicações
- Configuração de proxy reverso com Nginx para gerenciamento de requisições
- Integração de sistemas com PostgreSQL para armazenamento de dados

O desenvolvimento do Poupa.online foi uma experiência valiosa, proporcionando aprendizado em diversas tecnologias e práticas de desenvolvimento web.


## Licença

Este software é licenciado sob a [MIT](https://choosealicense.com/licenses/mit/).
