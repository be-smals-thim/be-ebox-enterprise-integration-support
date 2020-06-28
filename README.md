***This site is now deprecated in favor of the upcoming documentation site. The new Documentation site is under construction here https://eboxenterprise-documentation-site.vercel.app/***


# Tools & Tricks to integrate with e-Box Enterprise
 
An unofficial set of tools that helps with integration with [https://www.eboxenterprise.be](https://www.eboxenterprise.be) site.

- [Integration as Document Consumer](document_consumer/document_consumer.md)
- [Integration as Document Sender](document_sender/document_sender.md)
- [Integration as Document Provider](document_provider/document_provider.md)
- [Federation Web Service](federation/federation_ws.md)
- [Notifications and the e-Box Enterprise Event Service](federation/enterprise_ebox_event_service.md)

The diagram below shows the high-level technical view of the federated model of the e-Box Enterprise. The federated view indeed integrates several message sources (***DocProviders***), each accessible via a MessageRegistry (web service REST) respecting the same [contract](https://info.eboxenterprise.be/fr/documents/zip/e-Box-Enterprise-swagger-v2.1-AP-Public.zip).
In addition, some institutions (***DocSenders***) can continue to publish their messages to a DocProvider who will make them available for the federated e-Box.
Finally, a company (***DocConsumer***) can directly retrieve the content of its e-Box via a technical integration with each MessageRegistry offering this type of access.
![Diagram: high-level technical viewDiagram: high-level technical view](media/senderProviderConsumerView.png)
