# Funds Transfer Service

## What does it do?

Transfers funds between two accounts in the same system

## Usage

#### Start server
Run FundsTransferApplication.java#main method to start the Undertow Server

####Sample requests

#####Funds Transfer

Url: http://localhost:8080/api/fund-transfers\
Method: POST\
ContentType: application/json\
RequestBody:\
{\
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"fromAccountNumber":"REV007",\
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"payeeAccountNumber":"REV008",\
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"amount":{\
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"value":100.00,\
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"currencyCode":"EUR"\
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}\
}

#####Get Account Balance

Url: http://localhost:8080/api/accounts/<accountNumber>\
Method: GET\

#####Data

Five sample accounts are loaded into inmemory DB on server startup\
See <b/>infrastructure/src/main/resources/liquibase/onl-bank-db/changes/changelog-00.00.01.01.json</b>

## Requirements
Java 11\
Gradle 5.6

## Architecture
Hexagonal architecture is enforced with gradle multi-module build.

#### Modules
domain - Encapsulates business domain objects and services
application - Application services
infrastructure - Adapters for database and rest.
bootstrap - Wires the application components

  