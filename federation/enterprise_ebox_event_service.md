
## EES Enterprise integration

### Integration tips

We strongly recommend an asynchronous implementation with retry so that the DP  is not directly impacted by errors of the EES. Event are important so we cannot simply ignore them because they cannot be send.

### Example input

´´´:json
[
    {
      "eventType": "documentExpirationAlert",
      "eventCorrelationId": "c1493fcf-601b-453d-80b8-9db1b226199f",
      "eboxId": "0206731645",
      "messageId": "3b06011b-071d-4e9c-b879-b260523247cb",
      "expirationDate": "2020-04-29T10:25:53.723Z",
      "receiptDate": "2020-04-29T10:25:53.723Z",
      "registeredMail": false
    }
  ]
´´´

### Technical Information

- [EEES Open Api 2 Spec](../openapi/ebox-enterprise-event-api-1.1.1.yaml)
- [EEES Endpoint ACC](https://public.int.fedservices.be/EventServices/Ebox/enterprise/document/events)
- [EEES Endpoint PRD](https://public.fedservices.be/EventServices/Ebox/enterprise/document/events)