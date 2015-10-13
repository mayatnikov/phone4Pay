
<================== Содержимое директории/архива: ===================>
|-- projects
|   |-- OAuth2
|   |   |-- AuthServer <-------------- Сервер авторизации
|   `-- p4p-front-back.v3
|       |-- BackServer <-------------- Back сервер (десь основная логика)
|       |-- DataDomain <-------------- классы с форматами запросов (POJO class)
|       `-- FrontServer <------------- фронт сервер обработка всех REST запрсов

=================== Порядок запуска ========================
* создать базу данных^ если без настроек, то mysql/test
* установить ActiveMQ server ( тестировал с apache-activemq-5.9.0)
* maven gradle java 7-я ( с 8-ой есть проблемы, но может это только на MAC OS)
* установить Tomcat (тестировал с apache-tomcat-8.0.15 )
* запустить в любом порядке
** tomcat --> apache-tomcat-8.0.15/bin/catalina.sh run 
** ActiveMQ Server --> apache-activemq-5.9.0/bin/activemq start
** FrontServer bp его директории -->  mvn jetty:run 
** запустить Mysql и создать базу тест ( см. здесь .../OAuth2/AuthServer/src/main/resources/)
** создать authserver, переименовать результат в AuthServer.war и установить на Tomcat
** запустить FrontServer ---> mvn jetty:run
** запустить BackServer ---> gradle bootRun
* потестировать результат командами опаисанными ниже

========================= КОМАНДЫ ДЛЯ ТЕСТИРОВАНИЯ ===============================
>>>>>>>>>>>> ЗАПРОС ТОКЕН с Auth-Server
curl -H "Accept: application/json" \
client1:secret@localhost:8080/AuthServer/oauth/token \
-d grant_type=password \
-d username=user_01 \
-d password=password
<<<<<<<<<< 
{
	"access_token":"9b703aa0-bb0b-4c4b-a3c6-2e82e91f902a",
	"token_type":"bearer",
	"refresh_token":"ad78c6f9-9f25-41b5-855a-d641a9b617da",
	"expires_in":3539,
	"scope":"delete read write"
}

>>>>>>>>>>> запрос обновления token c Auth-Server
curl -H "Accept: application/json" \
client1:secret@localhost:8080/AuthServer/oauth/token \
-d grant_type=refresh_token \
-d refresh_token=a9ddbfa8-7a89-44c4-8a5a-f2e8a495036b
<<<<<<<<<<<<<<<
{
	"access_token":"98d065c3-6649-4007-8f52-946ff6947582",
	"token_type":"bearer",
	"refresh_token":"95d99f13-d296-4fcb-8c52-15e17dddf675",
	"expires_in":3599,"scope":"delete read write"
}

TOKEN=98d065c3-6649-4007-8f52-946ff6947582    <------------ сохраним TOKEN в переменной

Эта команда не ходит дальше фронтальной машины и 
запрос просто заворачивается назад.
входной параметр в URL'е, допустимое значение Long ( в примере 11)
>>>>>>>>>>>>>>>>>>>>>>> 
curl -H "Accept: application/json" \
-H "Authorization: Bearer $TOKEN" \
-X GET localhost:8081/FrontServer/ds/echo/11
<<<<<<<<<<<<<<<<<<<<<<<
Echo test: id=[11]; CurrentUser=[user_01]

Аналогично предыдущей, но POST'ом в запросе JSON / POJO Echo class
>>>>>>>>>>>>>>>>>>>>>
curl -H "Accept: application/json" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $TOKEN" \
-X POST localhost:8081/FrontServer/ds/echo/req \
  --data '{"to":"Sidoroff","from":"Petroff","echo":"this is text in echo","data":"data-contents-string"}'

<<<<<<<<<<<<<<<<<<<<<
{"from":"Login user:user_01; 
	From:Petroff",
	"to":"answer to:Sidoroff",
	"startTransaction":null,
	"trID":null,
	"trStatus":0,
	"echo":"answer note:this is text in echo",
	"data":"answer data:data-contents-string"}

Этот запрос СИНХРОННЫЙ, проходит полный тракт обмена:
curl/POST --> FrontSever --> ActiveMQ --> BackServer --> 
---> CamelContext (разбирает сообщение и к параметру data добавляет содержимое HEADER) 
---> ответ вовращается клиенту
вот содержимое camel/route:
 from("activemq:p4p-echo-in").to("log:ECHO-IN-REQUEST?level=INFO").beanRef("echoConverter");
В результате работы запроса в очереди ActiveMQ видно появление обработанного сообщения

>>>>>>>>>>>>>>>>>>>>>
curl -H "Accept: application/json" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $TOKEN" \
-X POST http://localhost:8081/FrontServer/ds/echo/aqr \
  --data '{"to":"Sidoroff","from":"Petroff","echo":"this is text in echo","data":"data-contents-string"}'
<<<<<<<<<<<<<<<<<<<<<<<<<<<<
{"from":"user_01","to":"Sidoroff","startTransaction":null,
"trID":null,"trStatus":0,"echo":"this is text in echo",
"data":"From:user_01 To:Sidoroff 
Echo:this is text in echo Data:data-contents-string 
HDR: prop:CamelBinding=org.apache.camel.component.jms.JmsBinding@f0ffef6;
 prop:CamelMessageHistory=[DefaultMessageHistory[routeId=route1, node=to1], 
DefaultMessageHistory[routeId=route1, node=bean1]]; 
prop:CamelExternalRedelivered=false;
 prop:CamelToEndpoint=log://ECHO-IN-REQUEST?level=INFO;
 prop:CamelCreatedTimestamp=Sat Oct 03 12:57:19 MSK 2015; 
hdr:breadcrumbId=null; hdr:JMSCorrelationID=null; 
hdr:JMSDeliveryMode=null;
 hdr:JMSDestination=null; 
hdr:JMSExpiration=null; 
hdr:JMSMessageID=null; 
hdr:JMSPriority=null;
 hdr:JMSRedelivered=null; 
hdr:JMSReplyTo=null; 
hdr:JMSTimestamp=null;
 hdr:JMSType=null; 
hdr:JMSXGroupID=null;
 hdr:JMSXUserID=null;"
}
===========================================================================
Что планировал сделать: 
* АСИНХРОННЫЙ ЗАПРОС, то есть в конце camel-route добавить EndPoint .to(("activemq:p4p-echo-out") 
* написать route по регистрации сообщений в redis и
* написать входной запрос по выборке данных из redis по конкретному клиенту.
* написать входной фильтр для web.xml проверка подписи транзакции
* полностью перейти на gradle
* решить проблему с несовместимостью с java 8
* сделать интерфейс и супер класс для сообщения/команды ( то что сейчас class Message+Echo) 
для унификации обмена по всему тракту сообщения
* ошибки должны возвращаться в JSON, сейчас HTML


