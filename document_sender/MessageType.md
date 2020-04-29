The organization, the application and the message types are part of the reference data and are relative to the sender organization that can publish messages

What does the cookbook say?
Principes
The ebox/enterprise RESTful API proposes 3 types of referenceData: messageType; senderOrganization; and senderApplication.
 A senderOrganizationId MUST be the BCE/KBO number of the related organization.
 A senderOrganizationShortName and LongName SHOULD match the official names, except in case of valid business reason.
 senderApplication referenceData is not necessarily required. If provided, it is good practice to refer to data known to the end user (for example, online service names and descriptions).
 MessageType is required to categorize a message in the form of a well-known business category for several documents.
Each referenceDataId used as property of a message MUST correspond to a well-defined referenceData.

Cross-references
In the details of a referenceData, it is planned to list the identifiers of the others referenceData related to the current one. 
For example, for a given senderOrganization, the lists of related senderApplicationId(s) and messageTypeId(s) are provided.

Institution is also the common name used in our e-box vocabulary for organization. Mandatory definitions are Id and short name of the organisation or application.
Organisation and Application are defined in the 3 national languages (fr, nl, de), in the description of those parameters
m organisation and n application: in the current implementation it is ppossible that an organisation has multiple application 
and an application cna have multiple organisation, although this 2nd case is more rare than the 1st case.

Multiple message types can be defined, for the couple of organization and application

Each trio configured will target either the ebox enterprise or the ebox citizen

So basically we work in a structure of 3 levels:
level 1 - Organisation, level 2 - Application and level 3 - Message Type

Example 
organisation: ONVA (FR), RJV (NL), LJU (DE)
ONVA Office national des Vacances annuelles 
RJV Rijksdienst voor Jaarlijkse Vakantie
LJU Landesamt für den Jahresurlaub

Application
"rjv-onva:workerholidayrights:cova2"
This corresponds to Secteur des Vacances Annuelles (FR), Sector Jaarlijkse Vakantie (NL), Sektor des Jahresurlaubs (DE)

Messagetype:
example 1: YearlyVacationAccountExtract (Disponibilité de l’extrait de compte)
example 2: YearlyVacationFiscalCertificate (Disponibilité fiche fiscale)


MessageType: identifies the type of the message. A message type is defined with eBoxIntegration@smals.be when an institution wants to publish a new sort of message to e-Box (by means of an identification form). 
In addition that each message type is linked with an institution and a sender application, it is associated with a validity period, some security checks, and possible metadata, format of metadata included.

There is also the logo support of the organisation
A logo MUST be a PNG or GIF image on a transparent background. The small size proposed for the images (<100 pixels height = width) MUST be respected.
Each logo of a senderOrganization or senderApplication SHOULD match the official one.
As precised in the API, you can provide a different logo for each supported language.
