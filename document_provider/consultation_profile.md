

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
 

## Integration with the Portal

The [eBox Enterprise Portal](https://www.eboxenterprise.be) will be the primary consumer of the Document Provider. Usage by the portal has some particularities that are worth nothing.

### Resources used by the Portal

From all of the API endpoints, only a few are actually used by the portal.

- ``/ebox/messages``: This is the main method being used by the Portal. It provides all the information provided by all of it's sub resources making direct calls to sub resources useless or rare.
- ``/ebox/messages/<id>/attachments/<attachmentId>/content``: This method is used to get the attached documents
- ``/ebox/messages/<id>``: This method is used when displaying a message details and is therefore not used allot
- ``/referenceData/senderApplication/<id>``: This method is called for every single message displayed in eBox.
- ``/referenceData/senderOrganization/<id>``: This method is called for every single message displayed in eBox.
- ``/referenceData/messageType/<id>``: This method is called for every single message displayed in eBox.

**Note:** Integration to portal in ACC is allowed when this minimal set of resources has been implemented.

### HTTP Cache headers guidelines

In order to offer the best possible user experience cache control headers MUST be used on some the ``/referneceData/**`` endpoints. These endpoints are heavily used by the eBox Enterprise UI which itself does not use caching so to not impose latency in data updates on the DP. 

The following endpoints are MUST have significant cache control headers. 

- ``/referenceData/messageTypes/*``
- ``/referenceData/senderOrganization/*``
- ``/referenceData/senderApplication/*``

We recommend a 2 day fixed cache with non blocking background refresh, but more advanced options can be chosen.

e.g: ``Cache-Control: public, no-transform, proxy-revalidate, max-age=86400``


### TranslatedString

TranslatedString OpenApi contract allows for 0 to 3 language to be specified: nl,fr,de.

However for proper integration with the portal, all 3 values must be provided. If some values are not available they need to be default by the DP, preferably with another language value.

## Message List endpoint

The Message List endpoint is the single most important endpoint of eBox.
It gives access to all information of all Messages with the notable exception of the attachments binary content.

### Text search feature

The Text search feature allows substring search in all visible textual information related to a message in the language of the user. Search excludes reasearch in the document or body content themselves.

.. More information comming soon.
