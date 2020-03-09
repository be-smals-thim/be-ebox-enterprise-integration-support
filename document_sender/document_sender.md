# Becoming a Document Sender

[Document Sender onboarding process](onboarding_process.md)

Publication happen through the ```/publishMessage``` method of the [e-Box Document Provider API](../openapi/ebox-rest-2.1.yaml)

The method uses a multipart HTTP POST to send up to 6 documents attached to a an e-Box Message. The API fully support [end to end streaming](#EndToEndStreamingConsiderations).

## Minimal publication example

The following is pretty much the simplest publication request that can be made. It is comprised of the following HTTP parts 
1) ``messageToPublish``: This part contains the meta information of the message

```json
{
      "messageToPublish": {
        "recipient": {
          "eboxType": "enterprise",
          "eboxIdValue": "0454079368"
        },
        "subject": {
          "fr": "ik ben"
        },
        "messageTypeId": "TEST_PILOT_5_ENTERPRISE",
        "senderOrganizationId": "0220916609",
        "senderApplicationId": "document:management:consult:ws-eboxrestentreprise:0220916609",
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

## Getting an Oauth Token for publication

The [oauth introspect example](../examples/ouath-introspect) shows how an Oauth token can be retrieved. 

The ``GetAccessTokenV3.getAccessToken()`` method is the one responsible of getting the token. 

Getting a token requires having cleared the oauth part of the onboarding.

## <a id="EndToEndStreamingConsiderations"></a>End to end Streaming Considerations

The order of HTTP parts is arbitrary, each part being linked to its associated meta-data by the ``httpPartName`` property of the publication payload. This allows for end to end streaming on the Document Sender side. See the [Publication Profile Documentation for more information](../document_provider/publication_profile.md#OrderOfTheHttpParts). 