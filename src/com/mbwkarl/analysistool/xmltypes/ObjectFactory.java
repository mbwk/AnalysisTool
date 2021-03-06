//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.21 at 12:00:48 AM BST 
//


package com.mbwkarl.analysistool.xmltypes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mbwkarl.analysistool.newxmltypes package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DataLog_QNAME = new QName("", "DataLog");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mbwkarl.analysistool.newxmltypes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link KeyValueMapModeller }
     * 
     */
    public KeyValueMapModeller createKeyValueMapModeller() {
        return new KeyValueMapModeller();
    }

    /**
     * Create an instance of {@link DataLogType }
     * 
     */
    public DataLogType createDataLogType() {
        return new DataLogType();
    }

    /**
     * Create an instance of {@link DataEntryType }
     * 
     */
    public DataEntryType createDataEntryType() {
        return new DataEntryType();
    }

    /**
     * Create an instance of {@link KeyValueMapModeller.Field }
     * 
     */
    public KeyValueMapModeller.Field createKeyValueMapModellerField() {
        return new KeyValueMapModeller.Field();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataLogType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DataLog")
    public JAXBElement<DataLogType> createDataLog(DataLogType value) {
        return new JAXBElement<DataLogType>(_DataLog_QNAME, DataLogType.class, null, value);
    }

}
