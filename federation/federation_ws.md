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