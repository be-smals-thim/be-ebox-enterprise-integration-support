

# Message Registry Service

In order to be a Document Provider one MUST implement a Message Registry Service and Register that service on the Provider Registry Service. This service MUST follow the [eBox Message Registry open api Spec](../openapi/ebox-rest-2.1.yaml)

## Introspect of an eBox Enterprise Oauth Token

The DP methods are secured by Oauth2 tokens. Instropsecting these token can be tricky since they instrospect endpoint security is quite high using oauth itself to secure the call to the ``/introspect`` endpoint.

The introspect endpoint return serveral information the most important being the organization Cbe which is the unique identifier of an organization and of it's eBox.

Here is an example introspect payload.

```json
{
    "active": true,
    "client_id": "my:ebox:enterprise:dp:client",
    "token_type": "access_token",
    "sub": "79072300048",
    "aud": "oauth:sanitycheck:public:client",
    "iss": "oauth-acpt.socialsecurity.be",
    "jti": "i1v18l327gktaad04vuetqnrso",
    "principalAttributes": {
        ...
        "urn:be:fgov:kbo-bce:organization:cbe-number": [
                        "0406798006"
                        ],
        ...

    },
    "iat": 1563882772,
    "exp": 1571658772,
    "nbf": 1563882712,
    "scope": "openid profile"
}
```

Proper Oauth2 treatment of the token will not be described here as it is done out of the box by most Oauth client and is documented in the Oauth Specification. However here are some pointers

- ``active`` needs to be checked, if false the token is not acceptable
- ``scope`` need to be checked based on the endpoint 
- ``principalAttributes[‘urn:be:fgov:kbo-bce:organization:cbe-number’][0]`` contains the CBE number which identifies the eBox of the user.

The following resources expand a bit on the subject:

- [Java Example of an Introspect](../examples/ouath-introspect)

### Scopes and endpoints mapping

The eBox Enterprise Document Providers endpoints are secured by the following scopes:

- ``scope:document:management:consult:ws-eboxrestentreprise:summaryownebox``: Give access to the  ``/ebox`` resource of identified user 
- ``scope:document:management:consult:ws-eboxrestentreprise:summaryallebox``: Give access to the  ``/ebox`` resource of any user. 
- ``scope:document:management:consult:ws-eboxrestentreprise:messagesfull``: Give access to the  ``/ebox/message`` and ``/ebox/message/**`` resource of identified user.
- ``scope:document:management:consult:ws-eboxrestentreprise:referencedata``: Give access to the  ``/referenceData/**`` resources.
 



## EES Enterprise integration

In order to notify users of any new, unread or soon to be expire messages, an integration with the EES Enterprise is required.

The EES or eBox Event Service is a system which allows Document Providers to send Events to HIP's so that the HIP can notify
the user. 

The following events must be sent in the following scenarios:

- newDocument: This event MUST be sent when a new Message arrives in the eBox
- newDocumentReminder: This event MUST be sent when the Message has not been read 15 days after it has been published
- documentExpirationAlert: This event MUST be sent 7 days prior to the Message expiring only if the document has not been read yet
- readDocument: This event MUST be sent when the main Document is read for the first time

### What are Notifications

Notifications are eMail that are sent once per day with a summary of the event's received. The readDocument event is here
to prevent us sending Notifications about Messages that have been read by the User between the time the unread message 
Event was sent and the time the time the Notification is sent to the User.

### Technical Information

- [EEES Open Api 2 Spec](../openapi/ebox-enterprise-event-api-1.1.1.yaml)

## Enterprise Federation WS

The Federation WS allows to 
- Know the list of Document Providers
- Know the preferences of a particular eBox (enterprise)

A ``POST /eboxPreferences/search``call allows to know whether the enterprise has opt for receiving his messages exclusively in eBox or when he visited his eBox for the last time. 

The same call will in the future provide indicative information about the number of messages and unread messages that are available in the eBox.

``PATCH /eboxPreferences`` is not supported for the DP use case.

### What are eBox preferences for?

There are two use cases for eBox preferences

1) You implement the /publishMessage endpoint of a DP. In this case you should call eBox Preferences in order to know whether the user already has an eBox or not. If he does not have an eBox a special status code need to be sent in the reply.
2) You want to know if someone is using his eBox in order to decide on whether to send via messages via eBox or via Paper. This use case mostly applies to Document senders.

### Technical information

- [Federation WS Open Api 2 Spec](../openapi/ebox-federation-1.3.yaml)
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