# Becoming a Document Consumer

Message consultation happen via a HTTP GET through all the methods that start with ```/ebox``` defined in the [e-Box RESTful API](../openapi/ebox-rest_page.md).
This specification is available in the .yaml format.
So a Document Consumer can not only consult his messages but also reference data.
The ```/publishMessage``` method is available only for [Document Sender](../document_sender/document_sender.md) and [Document Provider](../document_provider/document_provider.md).

## Getting an Oauth Token for consultation
The [oauth introspect example](../examples/ouath-introspect) shows how an Oauth token can be retrieved.
You have to request your AccessToken to the Authorization Server.
The ``GetAccessTokenV3.getAccessToken()`` method is the one responsible of getting the token.

<table>
<tr><td>OAuth Authorization Server URL (ACC)</td><td>https://services-acpt.socialsecurity.be/REST/oauth/v3/token</td></tr>
<tr><td>Audience (ACC)</td><td>https://oauthacc.socialsecurity.be</td></tr>
<tr><td>OAuth Authorization Server URL (PRD)</td><td>https://services.socialsecurity.be/REST/oauth/v3/token</td></tr>
<tr><td>Audience (PRD)</td><td>https://oauth.socialsecurity.be</td></tr>
</table>

Getting a token requires having cleared the OAuth part of the onboarding. If it is not done yet, see the [Document Consumer onboarding process](onboarding_process.md).

You will get the scopes:
- ``scope:document:management:consult:ws-eboxrestentreprise:summaryownebox`` to get the summary of your e-Box;
- ``scope:document:management:consult:ws-eboxrestentreprise:messagesfull`` to get and perform authorized actions on all messages in your e-Box;  
- ``scope:document:management:consult:ws-eboxrestentreprise:referencedata`` to retrieve the details of the messageTypes, senderOrganizations, and senderApplications.
- ``scope:documentmanagement:ebox:enterprise:federation-rest:registry`` to get the list of Document Providers

## Endpoints
Once you have got your token, you can find the list of Document Providers to call in the result of a GET request to the *Provider Registry*:

| Environment| URL Provider Registry                                                                     |
|------------|------------------------------------------------------------------------------------------------|
| Acceptance | ``https://services-acpt.socialsecurity.be/REST/ebox/enterprise/federation/v1/messageProviders``|
| Production | ``https://services.socialsecurity.be/REST/ebox/enterprise/federation/v1/messageProviders``     |

Among them, you can find our Document Provider:

| Environment| Endpoint e-Box enterprise                                                           |
|------------|-------------------------------------------------------------------------------------|
| Acceptance | ``https://services-acpt.socialsecurity.be/REST/ebox/enterprise/messageRegistry/v2/``|
| Production | ``https://services.socialsecurity.be/REST/ebox/enterprise/messageRegistry/v2/``      |

## Implementation choices
- The counters you get in the summary of your e-Box (that you can get with a GET on <endpoint>/ebox) take also into account the message set to not visible.
- If you request to get a reference data, the lists messageTypeIds, senderOrganizationIds and senderApplicationIds in response will be empty.