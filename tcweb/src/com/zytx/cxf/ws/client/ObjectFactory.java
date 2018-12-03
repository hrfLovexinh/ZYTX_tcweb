
package com.zytx.cxf.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zytx.cxf.ws.client package. 
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

    private final static QName _JyDataUpdate_QNAME = new QName("http://service.ws.cxf.zytx.com/", "jyDataUpdate");
    private final static QName _JyDataUpdateResponse_QNAME = new QName("http://service.ws.cxf.zytx.com/", "jyDataUpdateResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zytx.cxf.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JyDataUpdateResponse }
     * 
     */
    public JyDataUpdateResponse createJyDataUpdateResponse() {
        return new JyDataUpdateResponse();
    }

    /**
     * Create an instance of {@link JyDataUpdate }
     * 
     */
    public JyDataUpdate createJyDataUpdate() {
        return new JyDataUpdate();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JyDataUpdate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cxf.zytx.com/", name = "jyDataUpdate")
    public JAXBElement<JyDataUpdate> createJyDataUpdate(JyDataUpdate value) {
        return new JAXBElement<JyDataUpdate>(_JyDataUpdate_QNAME, JyDataUpdate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JyDataUpdateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.cxf.zytx.com/", name = "jyDataUpdateResponse")
    public JAXBElement<JyDataUpdateResponse> createJyDataUpdateResponse(JyDataUpdateResponse value) {
        return new JAXBElement<JyDataUpdateResponse>(_JyDataUpdateResponse_QNAME, JyDataUpdateResponse.class, null, value);
    }

}
