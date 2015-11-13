
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

>>>>>>>>>>> refesh token запрос обновления token c Auth-Server
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

TOKEN=98d065c3-6649-4007-8f52-946ff6947582

>>>>>>>>>>>>>>>>>>>>>>>
curl -H "Accept: application/json" \
-H "Authorization: Bearer $TOKEN" \
-X GET localhost:8081/FrontServer/ds/echo/11
<<<<<<<<<<<<<<<<<<<<<<<
Echo test: id=[11]; CurrentUser=[user_01]

>>>>>>>>>>>>>>>>>>>>>
curl -H "Accept: application/json" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $TOKEN" \
-X POST localhost:8081/FrontServer/ds/echo/req   \
--data '{"request":"ПРИВЕТ ЛОХМАТЫЙ","startTransaction":"20151109122","trID":"555555"}'
<<<<<<<<<<<<<<<<<<<<<
{
"login":"user_01",
"startTransaction":20151109122,
"endTransaction":1447089986777,
"trID":555555,
"trStatus":3,
"trType":"SYNC",
"request":"ПРИВЕТ ЛОХМАТЫЙ",
"response":"ECHO for:ПРИВЕТ ЛОХМАТЫЙ"
}

>>>>>>>>>>>>>>>>>>>>>>>>
curl -H "Accept: application/json" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $TOKEN" \
-X POST localhost:8081/FrontServer/ds/echo/aqr   \
--data '{"request":"ПРИВЕТ ЛОХМАТЫЙ","startTransaction":"20151109122","trID":"555555"}'
<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
{"login":"user_01",
"startTransaction":20151109122,
"endTransaction":1447090219996,
"trID":-1574947049643387450,
"trStatus":0,
"trType":"SYNC",
"request":"ПРИВЕТ ЛОХМАТЫЙ",
"response":
	"MSG:
	{
	Login:[user_01],
	Request:[ПРИВЕТ ЛОХМАТЫЙ],
	StartTransaction:[Sat Aug 22 08:31:49 MSK 1970],
	EndTransaction:[Mon Nov 09 20:30:19 MSK 2015],
	TrID:[-1574947049643387450],
	TrType:[SYNC],
	HDR:
		[ 	prop:CamelBinding=org.apache.camel.component.jms.JmsBinding@15ce4924; 
			prop:CamelMessageHistory=
				[
				DefaultMessageHistory[routeId=route1, node=to1], 
				DefaultMessageHistory[routeId=route1, node=bean1]
				]; 
			prop:CamelExternalRedelivered=false; 
			prop:CamelToEndpoint=log://ECHO-IN-REQUEST?level=INFO; 
			prop:CamelCreatedTimestamp=Mon Nov 09 20:30:19 MSK 2015; 
			hdr:breadcrumbId=null; 
			hdr:JMSCorrelationID=null; 
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
			hdr:JMSXUserID=null;
		]"
	}











<<<<<<<<<<<<<<<<<
Login:[user_01]
Request:[]
Response:[
{"RESP":
	{
		{"login":"user_01","startTransaction":20151109122,"endTransaction":null,"trID":555555,"trStatus":0,"trType":null,"request":"ПРИВЕТ ЛОХМАТЫЙ","response":null}
		{"login":"user_01","startTransaction":20151109122,"endTransaction":null,"trID":555555,"trStatus":0,"trType":null,"request":"ПРИВЕТ ЛОХМАТЫЙ","response":null}
		{"login":"user_01","startTransaction":20151109122,"endTransaction":null,"trID":555555,"trStatus":0,"trType":null,"request":"ПРИВЕТ ЛОХМАТЫЙ","response":null}
		{"login":"user_01","startTransaction":20151109122,"endTransaction":null,"trID":555555,"trStatus":0,"trType":null,"request":"ПРИВЕТ ЛОХМАТЫЙ","response":null}
	}
}
]
startTransaction:[Sat Aug 22 08:31:49 MSK 1970