# Becoming a Document Sender

Publication happen through the ```/publishMessage``` method of the [e-Box RESTful API](https://info.eboxenterprise.be/fr/documents/zip/e-Box-Enterprise-swagger-v2.1-AP-Public.zip)
The method uses a multipart HTTP POST to send up to 6 documents attached to a an e-Box Message. The API fully support [end to end streaming](#EndToEndStreamingConsiderations).

The authentication has to be done via a [OAuth2 token request](#getToken). See the [Document Sender onboarding process](onboarding_process.md) to configure your enterprise as a new OAuth client.

## Minimal publication example

The following is pretty much the simplest publication request that can be made. It is comprised of the following HTTP parts 
1) ``messageToPublish``: This part contains the meta information of the message.
Example:
```json
messageToPublish: {
    "recipient": {
      "eboxType": "enterprise",
      "eboxIdValue": "0123456789"
    },
    "subject": {
      "fr": "Message de test"
    },
    "senderApplicationId": "db2b.SPFETCS-FODWASO.fgov.be",
    "messageTypeId": "SpfFodSocialElections",
    "senderOrganizationId": "0123456789",
    "attachments": [
        {
          "httpPartName": "upfile1",
          "mainContent": true,
          "attachmentSigned": false
        }
    ],
    "bodyMainContent": false,
    "replyAuthorized": false
}
```

2) ``upfile1``: This part MUST contain the binary and basic meta information of the document. It is a standard HTTP binary file upload part which needs to specify the following information:
    - data stream: the raw data of the content
    - filename: the file name of the document
    - Content-Type: the [MIME type](https://www.iana.org/assignments/media-types/media-types.xhtml) of the document
```
Content-Disposition: form-data; name="upfile1
"; filename="MyTestDocument.pdf"
Content-Type: application/pdf

(data)
``` 

**Note**: The ``upfile1`` part and the extra meta information of the document are linked together through ``"httpPartName": "upfile1"`` in the ``messageToPublish`` part,

### Response

Provided that the request is correct one can expect a ``201`` status code to be issued with our without an additional information ``code`` in the JSON response body.

### NO_DIGITAL_USER response code

This code allows the Document Sender to know that the publication he just made was to a recipient that never visited his e-Box. 

This is the preferred method for a Sender to determine if the User uses his e-Box or not. An alternative to this is to use the [e-Box Federation WS](../federation/federation_ws.md) but this requires to integrate with another web service and does not fit the "e-Box First" philosophy we are trying to push. 

### For the attention of

If the publication is for the attention of a particular person, a ``ForTheAttentionOf`` object has to be added in the ``messageToPublish``.
Example:
```json
        "forTheAttentionOf": {
          "type": "person",
          "id": "84022711080",
          "name": "John Doe"
        }
```
The ``type`` is used to determine if the attention is for a person or a group of person. For the moment, only ``"person"`` is suported. The ``id`` property is the National Register Number.

## <a id="getToken"></a>Getting an Oauth Token for publication

The [oauth introspect example](../examples/ouath-introspect) shows how an Oauth token can be retrieved.
You have to request your AccessToken to the Authorization Server.
The ``GetAccessTokenV3.getAccessToken()`` method is the one responsible of getting the token.

<table>
<tr><td>OAuth Authorization Server URL (ACC)</td><td>https://services-acpt.socialsecurity.be/REST/oauth/v3/token</td></tr>
<tr><td>Audience (ACC)</td><td>https://oauthacc.socialsecurity.be</td></tr>
<tr><td>OAuth Authorization Server URL (PRD)</td><td>https://services.socialsecurity.be/REST/oauth/v3/token</td></tr>
<tr><td>Audience (PRD)</td><td>https://oauth.socialsecurity.be</td></tr>
</table>

Getting a token requires having cleared the OAuth part of the onboarding. If it is not done yet, see the [Document Sender onboarding process](onboarding_process.md).


## <a id="EndToEndStreamingConsiderations"></a>End to end Streaming Considerations

The order of HTTP parts is arbitrary, each part being linked to its associated meta-data by the ``httpPartName`` property of the publication payload. This allows for end to end streaming on the Document Sender side. See the [Publication Profile Documentation for more information](../document_provider/publication_profile.md#OrderOfTheHttpParts).

## Our implementation choices

There are some restrictions in our implementation of the service:
- We do not support publication with several languages. Only one among ``fr``, ``nl`` and ``de`` has to be selected in a publication request for the subject, attachment title, body content and business data values.
- We do not support the ``attachmentTitle`` field in the ``AttachmentToPublish`` object. The attachment title will be the file name of the uploaded file.
- ``/linkEboxMessage`` feature is not implemented but the broadcast feature still available by asking the procedure to [eBoxIntegration@smals.be](mailto:eBoxIntegration@smals.be).
- We do not support dynamic expiration date. That is to say, in the API about the ``messageToPublish`` object, the ``expirationDate`` field is not supported. The expiration date will be calculated from the current date plus the validity period defined for the message type. You can see the ``validityPeriod`` by doing a GET on ``<endpoint>/referenceData/messageTypes/<messageType-ID>``
- The business data put in a ``messageToPublish`` can only be those defined for the message type created during the Onboarding process.
- The ``originalMessageId`` in the ``messageToPublish`` object is only supported in the case of a reply. The value to put is the ID of the message to reply.