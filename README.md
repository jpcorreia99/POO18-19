# UmCarroJa
Projeto desenvolvido no âmbito da cadeira de Programação orientada aos objetos.

Projeto realizado em parceria com [Ivo Baixo](https://github.com/Ivo-Down). 
**Nota final: 17 valores**

## Manual de instruções ## 

- **Avisos Importantes**
- **Menu inicial**
- **Cliente**
- **Proprietário**

### Avisos Importantes 
Dentro da pasta **lib** encontra-se um ficheiro **.jar** que terá de ser adicionado ao **build path** do projeto para que este funcione corretamente.

Este projeto utiliza duas *API's* externas: 
 - [ipinfobd](https://ipinfodb.com/) para reconhecimento do ip do utilizador.
 - [openweathermap](https://openweathermap.org) para a partir do ip do utilizador obter as condições climatéricas na sua localização.

A utilização destas API's depende de uma **key** que é atribuída a cada projeto. 

Como tal, caso alguém pretenda reutilizar o código presente neste projeto, é **CRUCIAL** que obtenha as devidas API keys através dos links abaixo indicados

As keys que se encontram dentro da classe **ControloAPIs** são exclusivas deste projeto e não devem ser utilizadas por mais nenhum projeto, visto que a sua utilizaçao fora deste  terá consequencias graças ao facto de as API's externas terem um número limitado de chamadas que qualquer projeto pode fazer. 

### Menu inicial

Aquando o início da aplicação, é apresentado ao utilizador o menu inicial, onde este pode decidir se pretende **registar-se** na aplicação ou se deseja fazer **login**.

Caso decida registar-se ser-lhe-á pedido que introduza as informações básicas de um utilizador do sistema, como Nif, email, morada, etc. Caso decida fazer login, após introduzir as credenciais corretas, as opções serão diferentes consoante o utilizador seja um Cliente ou um Proprietário.

### Cliente
Ao entrar no menu de cliente, o utilizador poderá escolher uma das seguintes opções:

#### Aluguer de uma viatura:
** Se o utilizador decidir alugar uma viatura, terá várias escolhas acerca de
como ordenar as viaturas: 

- Ordenar as viaturas pela distância a si
- Ordenar as viaturas pelo custo da viagem
- Ordenar as viaturas pela distância a si, mas com um limite da distância a pé
- Alugar por um Id específico, apresentando todas as viaturas disponíveis sem nenhuma ordenação
especial
- Alugar por um Id específico, apresentando todas as viaturas disponíveis sem nenhuma ordenação
especial
- Alugar por uma marca, onde são apresentadas todas as viaturas a marca indicada pelo utilizador

Para além de escolher uma das opções acima mencionadas, o utilizador terá ainda de indicar as suas coordenadas e em que tipo de viatura pretende viajar. O sistema encarregar-se-á então de apenas apresentar ao utilizador as viaturas em que é possível efetuar a viagem, evitando assim que seja escolhida uma viatura que esteja indisponível.

Quando o utilizador faz um pedido de aluguer uma viatura, fica impedido de efetuar outro aluguer até
que o proprietário aceite ou rejeite o pedido que fez atualmente.

**Fatores de aleatoriedade:**
- Caso o utilizador possua uma classificação acima de 95%, terá um desconto automático de 10%. 

- Os preços de aluguer variarão consoante a hora do dia: entre as 8 e as 10 horas e as 17 e 19,(horas de ponta matinais e de tarde) o preço sofrerá um aumento de 25% e entre as 12 e as 14 o
preç sofrerá um aumento de 10%.

- Caso as condições climatéricas sejam desfavoráveis, o proço aumentará por 10%.


#### Consulta do historial 
O cliente pode consultar o seu historial de viagens efetuadas. Para isso terá de indicar o intervalo de tempo em que as viagens foram efetuadas que pretende consultar.

#### Consulta de Informação
 O cliente pode consultar as informações relativas a si, como o nif, morada, etc. Pode ainda consultar o número de alugueres que já efetuou e ainda a distância percorrida.


### Proprietário 


Quando um proprietário entra na aplicação, terá acesso às seguintes opções:

#### Registar uma viatura
O proprietário regista uma viatura. Terá de introduzir os dados relativos à viatura como consumo, marca, tipo de viatura, etc. É efetuado um controlo para que não seja introduzida
uma matrícula já existente.

#### Verificar pedidos de aluguer 
Aqui o proprietário poderá gerir todos os pedidos que foram feitos para alugar uma das suas viaturas. Os pedidos serão apresentados pela sua ordem cronológica e o proprietário
terá de decidir se aceita o pedido ou o rejeita.

#### Consultar o historial 
Após introduzir uma data inicial e uma data final, serão apresentados ao proprietário todas as viagens efetuadas por todas as suas viaturas nesse intervalo de tempo.

#### Gerir viaturas 
Neste menu serão apresentadas ao utilizador todas as suas viaturas, onde o utilizador poderá alterar informação relativamente a elas. Se a viatura o suportar, o proprietário poderá ainda
abastecê-la.

#### Gerir classificações 
Aqui, serão apresentadas ao proprietário todas as viagens que ainda este ainda não classificou. Assim como na mecânica de gerir os pedidos de aluguer, também aqui as viagens serão
apresentadas por ordem cronológica da mais antiga para a mais recente. O utilizador classificará o cliente
então com um inteiro entre 1 e 100.

#### Top 10 clientes por número de alugueres/distância ao cliente 
Nestas duas opções, serão apresentados ao proprietário os 10 clientes mais ativos da aplicação em relação ao número de alugueres ou à distância total percorrida.
