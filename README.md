# Stock System

This system lets you post stock to a queue and this stock gets stored in a database via a queued channel.
It also lets you retrieve the last stock which exists in the database.

How-to
Post stock to the database using rest web services.

    /setStock (METHOD = POST) Posts the stock to the database.

Sample JSON Stock request

    {"value":10,"type":"APPL"}

Sample JSON Response

    "true"

Retrieving the last stock in the database.

    /getLastStock Gets the last stock from the database.


Logs are written into the /logs/stock/ directory under the name stock.log all configured in logback.xml