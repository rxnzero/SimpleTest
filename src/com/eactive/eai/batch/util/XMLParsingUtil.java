package com.eactive.eai.batch.util;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLParsingUtil {

	public static void main(String[] args) {
		String xmlMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result><status>010</status><message>등록되지 않은 인증키입니다.</message></result>";
//		String xmlMessage = "rawerasereresraerwe";
		try {
			System.out.println("getXmlFld status = " + getXmlFld(xmlMessage, "status"));
			System.out.println("getXmlFld message = " + getXmlFld(xmlMessage, "message"));
	
			System.out.println("getDiretChlid status = " + getDiretChlidData(xmlMessage, "status"));
			System.out.println("getDiretChlid message = " + getDiretChlidData(xmlMessage, "message"));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getXmlFld(String xmlMessage, String tagName) {
        try {
            String sTag = "<" +tagName +">";
            String eTag = "</" +tagName +">";
            int s_idx = xmlMessage.indexOf(sTag);
            int e_idx = xmlMessage.indexOf(eTag);
            if(s_idx == -1 || e_idx == -1) {
                return "";
            }
            else {
                return xmlMessage.substring(s_idx+sTag.length(), e_idx);
            }
        } catch(Exception e) {
            return "";
        }
    }
	
	public static String getDiretChlidData(String xmlString, String childName) throws Exception {
		Element e = getDiretChlid(xmlString, childName);
		if(e != null) {
			return e.getFirstChild().getNodeValue();
		}
		return null;
	}
	
	public static org.w3c.dom.Element getDiretChlid(String xmlString, String childName) throws Exception {
		DocumentBuilder builder = null;
		org.w3c.dom.Document doc = null;
		builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		ByteArrayInputStream bis = new ByteArrayInputStream(xmlString.getBytes("utf-8"));
        doc = builder.parse(bis);
		org.w3c.dom.Element parent = doc.getDocumentElement();
		for(Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child instanceof org.w3c.dom.Element &&
					childName.equals(child.getNodeName())) {
				return (org.w3c.dom.Element)child;
			}
		}
		return null;
	}
}
