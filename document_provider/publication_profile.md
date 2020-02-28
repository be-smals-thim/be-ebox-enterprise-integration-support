# Implementing the Document Provider Publication Profile


## Order of the HTTP parts

The publication method needs to support any HTTP part order. One cannot expect for instance that the JSON part ```messageToPublish``` be the first HTTP part to be received. There are two reason for this:
- Several HTTP client do not give control on the order of the HTTP parts
- In order to support end to end streaming for the Document Sender.
 
 The ``messageToPublish`` can contain ``SHA-256`` digests of the published documents. This means that the Document Sender might need to compute it. Computation of the digest requires to fully read the documents streams. 
 
 In an end to end scenarios, the digest computation must occur during HTTP transfer to the Document Provider. The ``messageToPublish`` digests values cannot be known to the Document Sender before the Documents are fully sent to the Document Provider and the ``messageToPublish`` part can only be specified as last part of the exchange.