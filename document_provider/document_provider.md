# Becoming a Document Provider

A Document Provider is the strongest eBox integration that can be made as it allows to offer eBox features to Users be it Senders or Consumers. As such it is also the most challenging, requiring integration to setup a REST Web Service that will integrate with several other Web Services of the federation.

The Document Provider has some responsibilities toward te overall eBox enterprise federation:

- Provide a Service which matches the eBox Enterprise SLA in terms of availability, security and performance
- Store User document with the adequate confidentiality
- Inform the eBox Federation of eBox activities like new messages or messages being read.
- Follow latest guidelines to ensure safeguard the end user experience. 

# Consultation & Publication

There two main level of service that a Document Provider can offer

- [Consultation](consultation_profile.md): Only implement the Consultation parts of the API. This allows to expose messages to the Document Consumers
- Publication: Also implement the Publication parts of the API. Allowing replies to messages and new publication being done through a standardized method.

The Consultation part is required to be considered a Document Provider while the Publication is optional.