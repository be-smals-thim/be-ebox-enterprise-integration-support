# Becoming a Document Provider

A Document Provider is the strongest eBox integration that can be made as it allows to offer eBox features to Users be it Senders or Consumers. As such it is also the most challenging, requiring integration to setup a REST Web Service that will integrate with several other Web Services of the federation.

The Document Provider has some responsibilities toward te overall eBox enterprise federation:

- Provide a Service which matches the eBox Enterprise SLA in terms of availability, security and performance
- Store User document with the adequate confidentiality
- Inform the eBox Federation of eBox activities like new messages or messages being read.
- Follow latest guidelines to ensure safeguard the end user experience. 

# Message Registry Service

The Message Registry Service is the most important part of the Document Provider. There two main level of service that a Document Provider can offer.

- [Consultation](consultation_profile.md): Only implement the Consultation parts of the API. This allows to expose messages to the Document Consumers
- Publication: Also implement the Publication parts of the API. Allowing replies to messages and new publication being done through a standardized method.

The Consultation part is required to be considered a Document Provider while the Publication is optional.

# Notifications

In order to notify users of any new, unread or soon to be expire messages, an integration with the EES Enterprise is required.

The EES or eBox Event Service is a system which allows Document Providers to send Events to HIP's so that the HIP can notify
the user. 

The following events must be sent in the following scenarios:

- ``newDocument``: This event MUST be sent when a new Message arrives in the eBox
- ``newDocumentReminder``: This event MUST be sent when the Message has not been read 15 days after it has been published
- ``documentExpirationAlert``: This event MUST be sent 7 days prior to the Message expiring only if the document has not been read yet
- ``readDocument``: This event MUST be sent when the **MAIN** Document is read for the first time

## readDocument Event:

Notifications are eMail that are sent once per day with a summary of the event's received. The readDocument event is here
to prevent us sending Notifications about Messages that have been read by the User between the time the unread message 
Event was sent and the time the time the Notification is sent to the User.