as-none-before
==============

ASN.1 java compiler using ANTLR to parse ASN.1 notation and BouncyCastle for runtime decode and encode.

Status: *pre-alpha*

**Aim**: To generate java classes that can decode and encode P1 files which are defined by the x420 specification from the ITU.

Found an ANTLR grammar [here](from https://github.com/ttsiodras/asn1scc/blob/master/Antlr/asn1.g) that fails on half of the x420 asn.1 definition files so I'm working on fixing the grammar.

Apparently ANTLR is not a great fit for the full ASN.1 spec (see [here](https://sites.google.com/site/ramaswamyr/article/parsing-asn-1)) but to generate java classes for decoding and encoding P1 files it does not appear to be necessary to support all the particulars of the ASN.1 format (noticed this by looking at java code generated by asn1c from Objective Systems).

I doubt this project will go anywhere much as I'm not familiar enough with ASN.1. Still fun to have a look at.

My fifty cents about x420 is that it's pretty over the top compared to say SMTP but is pretty thorough. Given how long it's been around you'd think that the full gamut of open source tools (many languages and platforms)  would be out there to implement it. Certainly would have been an obstacle to adoption. Old thinking eh. 


