

# Message Registry Service

In order to be a Document Provider one MUST implement a Message Registry Service and Register that service on the Provider Registry Service. This service MUST follow the [e-Box Message Registry open API Spec](../openapi/ebox-rest_page.md)

## Introspect of an e-Box Enterprise Oauth Token

The DP methods are secured by Oauth2 tokens. Introspecting these token can be tricky since the introspect endpoint security is quite high using oauth itself to secure the call to the ``/introspect`` endpoint.

The introspect endpoint return several information, the most important being the organization CBE which is the unique identifier of an organization and of it's e-Box.

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
- ``principalAttributes[‘urn:be:fgov:kbo-bce:organization:cbe-number’][0]`` contains the CBE number which identifies the e-Box of the user.
That CBE number is not necessarily in 10 digits format and so you may need to add a prefix with as many 0 as needed to obtain the 10 digits format.
In the e-Box services, CBE numbers must be encoded in 10 digits.

The following resources expand a bit on the subject:

- [Java Example of an Introspect](../examples/ouath-introspect)

### Scopes and endpoints mapping

The e-Box Enterprise Document Providers endpoints are secured by the following scopes:

- ``scope:document:management:consult:ws-eboxrestentreprise:summaryownebox``: Give access to the  ``/ebox`` resource of identified user 
- ``scope:document:management:consult:ws-eboxrestentreprise:summaryallebox``: Give access to the  ``/ebox`` resource of any user. 
- ``scope:document:management:consult:ws-eboxrestentreprise:messagesfull``: Give access to the  ``/ebox/message`` and ``/ebox/message/**`` resource of identified user.
- ``scope:document:management:consult:ws-eboxrestentreprise:referencedata``: Give access to the  ``/referenceData/**`` resources.
 

## Integration with the Portal

The [e-Box Enterprise Portal](https://www.eboxenterprise.be) will be the primary consumer of the Document Provider. Usage by the portal has some particularities that are worth nothing.

### Resources used by the Portal

From all of the API endpoints, only a few are actually used by the portal.

- ``/ebox/messages``: This is the main method being used by the Portal. It provides all the information provided by all of it's sub resources making direct calls to sub resources useless or rare.
- ``/ebox/messages/<id>/attachments/<attachmentId>/content``: This method is used to get the attached documents
- ``/ebox/messages/<id>``: This method is used when displaying a message details and is therefore not used allot
- ``/referenceData/senderApplication/<id>``: This method is called for every single message displayed in e-Box.
- ``/referenceData/senderOrganization/<id>``: This method is called for every single message displayed in e-Box.
- ``/referenceData/messageType/<id>``: This method is called for every single message displayed in e-Box.

**Note:** Integration to portal in ACC is allowed when this minimal set of resources has been implemented.

### Required fields

The fields ``items`` and ``totalItems`` are required in the following Json Schemas described in the [API](../openapi/ebox-rest_page.md):

- ``Attachments``
- ``BusinessDataList``
- ``MessageTypes``
- ``Messages``
- ``MessagesToUpdate``
- ``SenderApplications``
- ``SenderOrganizations``

If there is no item, do not put null as value for the item property but a void list.

### HTTP Cache headers guidelines

In order to offer the best possible user experience cache control headers MUST be used on some the ``/referenceData/**`` endpoints. These endpoints are heavily used by the e-Box Enterprise UI which itself does not use caching so to not impose latency in data updates on the DP. 

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

The Message List endpoint is the single most important endpoint of e-Box.
It gives access to all information of all Messages with the notable exception of the attachments binary content.

### Text search feature

The Text search feature allows substring search in all visible textual information related to a message in the language of the user. Search excludes research in the document or body content themselves.

.. More information coming soon.
