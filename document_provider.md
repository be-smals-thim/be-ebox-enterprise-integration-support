# Becoming a Document Provider

A Document Provider is the strongest eBox integration that can be made as it allows to offer eBox features to Users be it Senders or Consumers. As such it is also the most challenging requiring integration to setup a REST Web Service that will integrate with several other Web Services.

The Document Provider has some responsability toward te overall eBox enterprise fedreation:
- Provide a Service which matches the eBox Enterprise SLA in terms of availability, security and performance
- Store User document with the adequate confidenciality
- Inform the eBox Federation of eBox activities like new messages or messages being read.
- Follow latest guidelines to ensure safeguard the end user experience.

# Message Registry Service

In order to be a Document Provider one MUST implement a Message Registry Service and Register that service on the Provider Registry Service. This service MUST follow the [eBox Message Registry open api Spec](openapi/ebox-rest-2.1.yaml)

## Introspect of an eBox Enterprise Oauth Token

The DP methods are secured by Oauth2 tokens. Instropsecting these token can be tricky since they instrospect endpoint security is quite high using oauth itself to secure the call to the ``/introspect`` endpoint.

- [Java Example of an Introspect](examples/ouath-introspect)


## EES Enterprise integration

In order to notify users of any new, unread or soon to be expire messages, an integration with the EES Enterprise is required.

The EES or Ebox Event Service is a system which allows Document Providers to send Events to HIP's so that the HIP can notify
the user. 

The following events must be sent in the following scenarios:

- newDocument: This event MUST be sent when a new %essage arrives in the eBox
- newDocumentReminder: This event MUST be sent when the Message has not been read 15 days after it has been published
- documentExpirationAlert: This event MUST be sent 7 days prior to the Message expiring only if the document has not been read yet
- readDocument: This event MUST be sent when the main Document is read for the first time

### What are Notifications

Notifications are eMail that are sent once per day with a summary of the event's recieved. The readDocument event is here
to prevent us sending Notifications about Messages that have been read by the User between the time the unread message 
Event was sent and the time the time the Notification is sent to the User.

### Technical Information

- [EEES Open Api 2 Spec](openapi/ebox-enterprise-event-api-1.1.1.yaml)

## Enterprise Federation WS

The Federation WS allows to 
- Know the list of Document Providers
- Know the preferences of a particular eBox (enterprise)

A ``POST /eboxPreferences/search``call allows to know whether the enterprise has opt for recieving his messages exclusivly in eBox or when he visited his eBox for the last time. 

The same call will in the future provide indicative information about the number of messages and unread messages that are available in the eBox.

``PATCH /eboxPreferences`` is not supported for the DP use case.

### What are eBox preferences for?

There are two use cases for eBox preferences

1) You implement the /publishMessage endpoint of a DP. In this case you should call eBox Preferences in order to know whether the user already has an eBox or not. If he does not have an eBox a special status code need to be sent in the reply.
2) You want to know if someone is using his eBox in order to decide on whether to send via messages via eBox or via Paper. This use case mostly applies to Document senders.

### Tehcnical information

- [Federation WS Open Api 2 Spec](openapi/ebox-federation-1.3.yaml)
- ACC URL: https://services-acpt.socialsecurity.be/REST/ebox/enterprise/federation/v1/  
- PRD URL:  https://services.socialsecurity.be/REST/ebox/enterprise/federation/v1

## HTTP Cache headers guidelines

In order to offer the best possible user experience cache control headers MUST be used on some the ``/referneceData/**`` endpoints. These endpoints are heavily used by the eBox Enterprise UI which itself does not use caching so to not impose latency in data updates on the DP. 

The following endpoints are MUST have significant cache control headers. 

- ``/referenceData/messageTypes/*``
- ``/referenceData/senderOrganization/*``
- ``/referenceData/senderApplication/*``

We recommend a 2 day fixed cache with non blocking background refresh, but more advanced options can be chosen.

e.g: ``Cache-Control: public, no-transform, proxy-revalidate, max-age=86400``