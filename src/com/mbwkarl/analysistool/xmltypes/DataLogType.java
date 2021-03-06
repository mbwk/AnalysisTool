//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.21 at 12:00:48 AM BST 
//


package com.mbwkarl.analysistool.xmltypes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataLogType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataLogType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Corruptions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="Meta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;choice maxOccurs="unbounded">
 *             &lt;element name="DataEntry" type="{}DataEntryType"/>
 *           &lt;/choice>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataLogType", propOrder = {
    "corruptions",
    "meta",
    "dataEntry"
})
public class DataLogType {

    @XmlElement(name = "Corruptions")
    protected int corruptions;
    @XmlElement(name = "Meta")
    protected List<String> meta;
    @XmlElement(name = "DataEntry")
    protected List<DataEntryType> dataEntry;

    /**
     * Gets the value of the corruptions property.
     * 
     */
    public int getCorruptions() {
        return corruptions;
    }

    /**
     * Sets the value of the corruptions property.
     * 
     */
    public void setCorruptions(int value) {
        this.corruptions = value;
    }

    /**
     * Gets the value of the meta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMeta() {
        if (meta == null) {
            meta = new ArrayList<String>();
        }
        return this.meta;
    }

    /**
     * Gets the value of the dataEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataEntryType }
     * 
     * 
     */
    public List<DataEntryType> getDataEntry() {
        if (dataEntry == null) {
            dataEntry = new ArrayList<DataEntryType>();
        }
        return this.dataEntry;
    }

}
