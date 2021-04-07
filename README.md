#Trade store application

This is trade store application providing API to store and get all trades from trade store. It also has cron scheduler which run everyday midnight to check for expired trades and update their status to expired in trade store

##Install

`mvn clean install`    

##Run the app
   
`java -jar target/tradestore-0.0.1-SNAPSHOT.jar` 

##REST API

####Store trade

Request    
POST <mark>/trade</mark>

```
curl --location --request POST 'http://localhost:8080/trade' \
--header 'Content-Type: application/json' \
--data-raw '{"tradeId":"T1","version":1,"counterPartyId":"CP1","bookId":"B1","maturityDate":"2021-04-07"}' 
```

Response Headers       

```
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 07 Apr 2021 10:17:32 GMT
Keep-Alive: timeout=60
Connection: keep-alive
```

Response Body     

`{"tradeId":"T1","version":1,"counterPartyId":"CP1","bookId":"B1","maturityDate":"2021-04-07","createdDate":"2021-04-07","expired":"N"}`

####Get all trades     

GET <mark>/trades</mark>

Request    
`curl --location --request GET 'http://localhost:8080/trades'`

Response Headers

```
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 06 Apr 2021 14:47:19 GMT
Keep-Alive: timeout=60
Connection: keep-alive
```

Response Body     

```
[
    {
        "tradeId": "T1",
        "version": 1,
        "counterPartyId": "CP1",
        "bookId": "B1",
        "maturityDate": "2021-04-07",
        "createdDate": "2021-04-07",
        "expired": "N"
    }
]
```

## Examples    

### Mature date validation
Request     

```
curl --location --request POST 'http://localhost:8080/trade' \
--header 'Content-Type: application/json' \
--data-raw '{
    "tradeId": "T1",
    "version": 1,
    "counterPartyId": "CP1",
    "bookId": "B1",
    "maturityDate": "2021-04-07"
}'
```

Response     

```
{
    "timestamp": "2021-04-07T18:21:22.642",
    "status": 400,
    "errors": [
        "\"Please provide valid trade maturityDate, maturityDate can not be past date\""
    ]
}
```

### version validation

Request

````
curl --location --request POST 'http://localhost:8080/trade' \
--header 'Content-Type: application/json' \
--data-raw '{
    "tradeId": "T1",
    "version": 0,
    "counterPartyId": "CP1",
    "bookId": "B1",
    "maturityDate": "2021-04-06"
}'
````

Response 

```
{
    "timestamp": "2021-04-07T18:22:01.102",
    "status": 400,
    "errors": [
        "\"Please provide valid trade maturityDate, maturityDate can not be past date\""
    ]
}
```

### Input Validation

Request

```
curl --location --request POST 'http://localhost:8080/trade' \
--header 'Content-Type: application/json' \
--data-raw '{
    "version": 0,
    "counterPartyId": "CP1",
    "bookId": "B1",
    "maturityDate": "2021-04-06"
}'
```

Response

```
{
    "timestamp": "2021-04-07T18:22:01.102",
    "status": 400,
    "errors": [
        "\"Please provide valid trade maturityDate, maturityDate can not be past date\""
    ]
}
```