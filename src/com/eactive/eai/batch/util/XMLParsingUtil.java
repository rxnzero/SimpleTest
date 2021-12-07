package com.eactive.eai.batch.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParsingUtil {

	public static void main(String[] args) {
		testParsing();
	}
	public static void testParsing() {
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result><status>000</status><message>정상</message><corp_code>00126380</corp_code><corp_name>삼성전자(주)</corp_name><corp_name_eng>SAMSUNG ELECTRONICS CO,.LTD</corp_name_eng><stock_name>삼성전자</stock_name><stock_code>005930</stock_code><ceo_nm>김기남, 김현석, 고동진</ceo_nm><corp_cls>Y</corp_cls><jurir_no>1301110006246</jurir_no><bizr_no>1248100998</bizr_no><adres>경기도 수원시 영통구  삼성로 129 (매탄동)</adres><hm_url>www.sec.co.kr</hm_url><ir_url></ir_url><phn_no>031-200-1114</phn_no><fax_no>031-200-7538</fax_no><induty_code>264</induty_code><est_dt>19690113</est_dt><acc_mt>12</acc_mt></result>";
		
		try {
			Map<String, Object> map = xmlToMap(xmlString, "utf-8");
			System.out.println("\n>> print Map");
			JsonParserUtil.printMap(map);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result><status>000</status><message>정상</message><page_no>1</page_no><page_count>10</page_count><total_count>223</total_count><total_page>23</total_page><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000559</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000486</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000375</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000341</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000083</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000030</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00878915</corp_code><corp_name>DGB금융지주</corp_name><stock_code>139130</stock_code><corp_cls>Y</corp_cls><report_nm>소송등의판결ㆍ결정(자회사의 주요경영사항)</report_nm><rcept_no>20200117800593</rcept_no><flr_nm>DGB금융지주</flr_nm><rcept_dt>20200117</rcept_dt><rm>유</rm></list><list><corp_code>00120571</corp_code><corp_name>롯데칠성음료</corp_name><stock_code>005300</stock_code><corp_cls>Y</corp_cls><report_nm>타법인주식및출자증권취득결정</report_nm><rcept_no>20200117800584</rcept_no><flr_nm>롯데칠성음료</flr_nm><rcept_dt>20200117</rcept_dt><rm>유정</rm></list><list><corp_code>00161709</corp_code><corp_name>퍼시스</corp_name><stock_code>016800</stock_code><corp_cls>Y</corp_cls><report_nm>주식등의대량보유상황보고서(약식)</report_nm><rcept_no>20200117000661</rcept_no><flr_nm>피델리티매니지먼트앤리서치컴퍼니엘엘씨</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00188089</corp_code><corp_name>한섬</corp_name><stock_code>020000</stock_code><corp_cls>Y</corp_cls><report_nm>주식등의대량보유상황보고서(약식)</report_nm><rcept_no>20200117000657</rcept_no><flr_nm>피델리티매니지먼트앤리서치컴퍼니엘엘씨</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list></result>";
		
		try {
			Map<String, Object> map2 = xmlToMap(xmlString, "utf-8");
			System.out.println("\n>> print Map");
			JsonParserUtil.printMap(map2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void testPickChild() {
		String xmlMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result><status>010</status><message>등록되지 않은 인증키입니다.</message></result>";
//		String xmlMessage = "rawerasereresraerwe";
		try {
			System.out.println("getXmlFld status = " + getXmlFld(xmlMessage, "status"));
			System.out.println("getXmlFld message = " + getXmlFld(xmlMessage, "message"));
	
			System.out.println("getDiretChlid status = " + getDirectChlidData(xmlMessage, "status"));
			System.out.println("getDiretChlid message = " + getDirectChlidData(xmlMessage, "message"));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Map<String, Object> xmlToMap(String xml) throws Exception {
		return xmlToMap(xml, "utf-8");
	}
	
	public static Map<String, Object> xmlToMap(String xml, String encoding) throws Exception {
	    InputStream is = new ByteArrayInputStream(xml.getBytes(encoding));
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document document = db.parse(is);
	    return createMap(document.getDocumentElement());
	}

	public static Map<String, Object> createMap(Node node) {
	    Map<String, Object> map = new LinkedHashMap<String, Object>();
	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.hasAttributes()) {
	            for (int j = 0; j < currentNode.getAttributes().getLength(); j++) {
	                Node item = currentNode.getAttributes().item(i);
	                map.put(item.getNodeName(), item.getTextContent());
//	                System.out.println(item.getNodeName() +"=A=>" + item.getTextContent());
	            }
	        }
	        if(currentNode.getFirstChild() == null) {
	        	// Empty node
	        	map.put(currentNode.getLocalName(), "");
//	            System.out.println(currentNode.getNodeName() +"=Empty =>");
	        }
	        else {
		        if (currentNode.getFirstChild().getNodeType() == Node.ELEMENT_NODE) {
//		        	System.out.println(currentNode.getNodeName() +"=EL=");
		        	// check list
		        	if(map.get(currentNode.getNodeName()) == null) {
		        		List<Object> list = new ArrayList<Object>();
		        		list.add(createMap(currentNode));
		        		map.put(currentNode.getNodeName(), list);
		        	}
		        	else {
		        		List<Object> list = (List<Object>)map.get(currentNode.getNodeName());
		        		list.add(createMap(currentNode));
		        		map.put(currentNode.getNodeName(), list);
		        	}
		        } else if (currentNode.getFirstChild().getNodeType() == Node.TEXT_NODE) {
		            map.put(currentNode.getLocalName(), currentNode.getTextContent());
//		            System.out.println(currentNode.getNodeName() +"=N=>" + currentNode.getTextContent());
		        }
	        }
	    }
	    return map;
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
	
	public static String getDirectChlidData(String xmlString, String childName) throws Exception {
		Element e = getDirectChlid(xmlString, childName);
		if(e != null) {
			if(e.getFirstChild() == null) {
				// empty node return zero-space
				return "";
			}
			else {
				return e.getFirstChild().getNodeValue();
			}
		}
		return null;
	}
	
	public static org.w3c.dom.Element getDirectChlid(String xmlString, String childName) throws Exception {
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
