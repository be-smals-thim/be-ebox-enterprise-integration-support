
## EES Enterprise integration

### Integration tips

We strongly recommend an asynchronous implementation with retry so that the DP  is not directly impacted by errors of the EES. Event are important so we cannot simply ignore them because they cannot be send

### Technical Information

- [EEES Open Api 2 Spec](../openapi/ebox-enterprise-event-api-1.1.1.yaml)
