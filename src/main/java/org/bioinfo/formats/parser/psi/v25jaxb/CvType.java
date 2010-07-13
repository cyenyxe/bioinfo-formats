//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.15 at 03:58:18 PM CET 
//


package org.bioinfo.formats.parser.psi.v25jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Reference to an external controlled vocabulary.
 * 
 * <p>Java class for cvType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cvType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cvType", propOrder = {
    "names",
    "xref"
})
@XmlSeeAlso({
    org.bioinfo.formats.parser.psi.v25jaxb.ParticipantType.ParticipantIdentificationMethodList.ParticipantIdentificationMethod.class,
    org.bioinfo.formats.parser.psi.v25jaxb.ParticipantType.ExperimentalRoleList.ExperimentalRole.class,
    org.bioinfo.formats.parser.psi.v25jaxb.ParticipantType.ExperimentalPreparationList.ExperimentalPreparation.class
})
public class CvType {

    @XmlElement(required = true)
    protected NamesType names;
    @XmlElement(required = true)
    protected XrefType xref;

    /**
     * Gets the value of the names property.
     * 
     * @return
     *     possible object is
     *     {@link NamesType }
     *     
     */
    public NamesType getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamesType }
     *     
     */
    public void setNames(NamesType value) {
        this.names = value;
    }

    /**
     * Gets the value of the xref property.
     * 
     * @return
     *     possible object is
     *     {@link XrefType }
     *     
     */
    public XrefType getXref() {
        return xref;
    }

    /**
     * Sets the value of the xref property.
     * 
     * @param value
     *     allowed object is
     *     {@link XrefType }
     *     
     */
    public void setXref(XrefType value) {
        this.xref = value;
    }

}
