package com.servicios.alternativa.poc.ejie;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.4.5
 * 2022-12-14T16:24:52.321+01:00
 * Generated source version: 3.4.5
 *
 */
@WebServiceClient(name = "SumWSService",
                  wsdlLocation = "file:/C:/ejie/workspace/pocWSSClient/src/main/resources/sumService.wsdl",
                  targetNamespace = "http://servicios.alternativa.poc.ejie.com/")
public class SumWSService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://servicios.alternativa.poc.ejie.com/", "SumWSService");
    public final static QName SumWsPort = new QName("http://servicios.alternativa.poc.ejie.com/", "SumWsPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/ejie/workspace/pocWSSClient/src/main/resources/sumService.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SumWSService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/C:/ejie/workspace/pocWSSClient/src/main/resources/sumService.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SumWSService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SumWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SumWSService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public SumWSService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SumWSService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SumWSService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns SumWs
     */
    @WebEndpoint(name = "SumWsPort")
    public SumWs getSumWsPort() {
        return super.getPort(SumWsPort, SumWs.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SumWs
     */
    @WebEndpoint(name = "SumWsPort")
    public SumWs getSumWsPort(WebServiceFeature... features) {
        return super.getPort(SumWsPort, SumWs.class, features);
    }

}