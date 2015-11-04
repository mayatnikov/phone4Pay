
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
curl -H "Accept: application/json" -H "Authorization: Bearer $TOKEN" -X GET localhost:8081/FrontServer/ds/echo/11
<<<<<<<<<<<<<<<<<<<<<<<
Echo test: id=[11]; CurrentUser=[user_01]

>>>>>>>>>>>>>>>>>>>>>
curl -H "Accept: application/json" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $TOKEN" \
-X POST localhost:8081/FrontServer/ds/echo/req \
  --data '{"to":"buffer","from":"?","request":"this is text in echo","response":"?"}'

<<<<<<<<<<<<<<<<<<<<<
{"from":"Login user:user_01; 
	From:Petroff",
	"to":"answer to:Sidoroff",
	"startTransaction":null,
	"trID":null,
	"trStatus":0,
	"echo":"answer note:this is text in echo",
	"data":"answer data:data-contents-string"}

>>>>>>>>>>>>>>>>>>>>>
curl -H "Accept: application/json" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $TOKEN" \
-X POST http://localhost:8081/FrontServer/ds/echo/aqr \
  --data '{"to":"Sidoroff","from":"Petroff","echo":"this is text in echo","data":"data-contents-string"}'
<<<<<<<<<<<<<<<<<<<<<<<<<<<<
curl -H "Accept: application/json" -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -X POST http://localhost:8081/FrontServer/ds/echo/aqr   --data '{"to":"Sidoroff","from":"Petroff","echo":"this is text in echo","data":"data-contents-string"}'
