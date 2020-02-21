# Becoming a Document Provider


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

