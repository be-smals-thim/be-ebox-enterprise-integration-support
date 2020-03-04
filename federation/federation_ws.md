## Enterprise Federation WS

The Federation WS allows to 
- Know the list of Document Providers
- Know the preferences of a particular e-Box (enterprise)

A ``POST /eboxPreferences/search``call allows to know whether the enterprise has opt for receiving his messages exclusively in e-Box or when he visited his e-Box for the last time. 

The same call will in the future provide indicative information about the number of messages and unread messages that are available in the e-Box.

``PATCH /eboxPreferences`` is not supported for the Document Provider use case.

### What are e-Box preferences for?

There are two use cases for e-Box preferences

1) You implement the ``/publishMessage`` endpoint of a Document Provider. In this case you should call e-Box Preferences in order to know whether the user already has an e-Box or not. If he does not have an e-Box, a special status code needs to be sent in the reply.
2) You want to know if someone is using his e-Box in order to decide on whether to send via messages via e-Box or via Paper. This use case mostly applies to Document senders.


### Technical information

- [Federation WS Open Api 2 Spec](../openapi/ebox-federation-1.3.yaml)
- ACC URL: https://services-acpt.socialsecurity.be/REST/ebox/enterprise/federation/v1/  
- PRD URL:  https://services.socialsecurity.be/REST/ebox/enterprise/federation/v1