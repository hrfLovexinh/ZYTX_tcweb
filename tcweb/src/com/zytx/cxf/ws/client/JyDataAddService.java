package com.zytx.cxf.ws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.1.3
 * Mon Jul 03 15:40:08 CST 2017
 * Generated source version: 2.1.3
 * 
 */
 
@WebService(targetNamespace = "http://service.ws.cxf.zytx.com/", name = "JyDataAddService")
@XmlSeeAlso({ObjectFactory.class})
public interface JyDataAddService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "jyDataAdd", targetNamespace = "http://service.ws.cxf.zytx.com/", className = "com.zytx.cxf.ws.client.JyDataAdd")
    @ResponseWrapper(localName = "jyDataAddResponse", targetNamespace = "http://service.ws.cxf.zytx.com/", className = "com.zytx.cxf.ws.client.JyDataAddResponse")
    @WebMethod
    public java.lang.String jyDataAdd(
        @WebParam(name = "xmlStr", targetNamespace = "")
        java.lang.String xmlStr
    );
}
