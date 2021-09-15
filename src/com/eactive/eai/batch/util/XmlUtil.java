package com.eactive.eai.batch.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil {
	static boolean debug = false; 
	public static Map<String, Object> xmlToMap(String xml, String encoding) throws Exception {
	    InputStream is = new ByteArrayInputStream(xml.getBytes(encoding));
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document document = db.parse(is);
	    
	    int level = 0;
	    
	    log(getLevelSpace(level) + "] GROUP[0] : " +document.getDocumentElement().getNodeName());
	    return createMap(document.getDocumentElement(), level+1);
	}

	public static Map<String, Object> createMap(Node node, int level) {
	    Map<String, Object> map = new LinkedHashMap<String, Object>();
	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.hasAttributes()) {
	            for (int j = 0; j < currentNode.getAttributes().getLength(); j++) {
	                Node item = currentNode.getAttributes().item(i);
	                map.put(item.getNodeName(), item.getTextContent());
	                log(getLevelSpace(level) + "] ATTR : " +item.getNodeName() +"=" + item.getTextContent());
	            }
	        }
	        if(currentNode.getFirstChild() == null) {
	        	// Empty node
	        	map.put(currentNode.getLocalName(), "");
	            log(getLevelSpace(level) + "] NODE : " +currentNode.getNodeName() +"=NULL");
	        }
	        else {
		        if (currentNode.getFirstChild().getNodeType() == Node.ELEMENT_NODE) {
		        	// check list
		        	if(map.get(currentNode.getNodeName()) == null) {
		        		List<Object> list = new ArrayList<Object>();
		        		log(getLevelSpace(level) + "] GROUP["+list.size()+"] : " +currentNode.getNodeName());
		        		list.add(createMap(currentNode, level+1));
		        		map.put(currentNode.getNodeName(), list);
		        	}
		        	else {
		        		List<Object> list = (List<Object>)map.get(currentNode.getNodeName());
		        		log(getLevelSpace(level) + "] GROUP["+list.size()+"] : " +currentNode.getNodeName());
		        		list.add(createMap(currentNode,  level+1));
		        		map.put(currentNode.getNodeName(), list);
		        	}
		        } else if (currentNode.getFirstChild().getNodeType() == Node.TEXT_NODE) {
		            map.put(currentNode.getLocalName(), currentNode.getTextContent());
		            log(getLevelSpace(level) + "] NODE : " +currentNode.getNodeName() +"=" + currentNode.getTextContent());
		        }
	        }
	    }
	    return map;
	}
	
	public static ArrayList<LayoiutItemRow> xmlToLayout(String xml, String encoding) throws Exception {
		xml = xml.replaceAll(">\\s+<", "><").trim();
//		xml = xml.replaceAll(">\\t+<", "><").trim();
		
	    InputStream is = new ByteArrayInputStream(xml.getBytes(encoding));
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setIgnoringElementContentWhitespace( true );
	    dbf.setIgnoringComments( true );
	    dbf.setNamespaceAware(true);
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document document = db.parse(is);
	    
	    int level = 1;
	    String itemName = document.getDocumentElement().getNodeName();
	    
	    ArrayList<LayoiutItemRow> layout = new ArrayList<LayoiutItemRow>();
	    
	    LayoiutItemRow itemRow = new LayoiutItemRow(itemName, itemName, level, "Group");
	    layout.add(itemRow);
	    log(getLevelSpace(level) + "] GROUP[0] : " +document.getDocumentElement().getNodeName());
	    xmlToLayout(document.getDocumentElement(), level+1, layout, "Group");
	    return layout;
	}

	public static void xmlToLayout(Node node, int level, ArrayList layout, String parentType) throws Exception {
	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.hasAttributes()) {
	            for (int j = 0; j < currentNode.getAttributes().getLength(); j++) {
	                Node item = currentNode.getAttributes().item(i);
	                String itemName = currentNode.getNodeName();
	                String content = item.getTextContent();
	                log(getLevelSpace(level) + "] ATTR : " +item.getNodeName() +"=" + itemName);
	                LayoiutItemRow itemRow = new LayoiutItemRow(itemName, itemName, level, "Attr");
	                itemRow.setContent(content);
	        	    layout.add(itemRow);
	            }
	        }
	        if(currentNode.getFirstChild() == null) {
	        	// Empty node
	        	String itemName = currentNode.getNodeName();
//	        	throw new Exception("Data definition not set : " + itemName);
	        	log(getLevelSpace(level) + "] SKIP : " +itemName);
        	}
	        else {
		        if (currentNode.getFirstChild().getNodeType() == Node.ELEMENT_NODE) {
		        	// check list
	        		String itemName = currentNode.getNodeName();
	        		log(getLevelSpace(level) + "] GROUP : " +itemName);
	        		LayoiutItemRow itemRow = new LayoiutItemRow(itemName, itemName, level, "Group");
	        		int pos = findSibling(layout, itemRow);
	        		if(pos >= 0) {
//	        			printLayout(layout);
	        			int count = layout.size() - pos;
	        			for(int index=0; index<count; index++) {
	        				layout.remove(pos);
	        			}
//	        			printLayout(layout);
	        			itemRow.setItemType("Grid");        			
	        		}
	        		layout.add(itemRow);
	        		xmlToLayout(currentNode,  level+1, layout, itemRow.getItemType());
		        } else if (currentNode.getFirstChild().getNodeType() == Node.TEXT_NODE) {
		        	String itemName = currentNode.getNodeName();
		        	String content = currentNode.getTextContent();
		            log(getLevelSpace(level) + "] NODE : " +itemName +"=" + content);
		            LayoiutItemRow itemRow = new LayoiutItemRow(itemName, itemName, level, "Field");
		            if("Grid".equals(parentType)) {
		            	itemRow.setItemType("Column");
		            }
		            itemRow.setContent(content);
		            layout.add(itemRow);
		        }
	        }
	    }	    
	}
	
	public static int findSibling(ArrayList layout, LayoiutItemRow row) {
		int pos = -1;
		for(int i=0; i< layout.size(); i++) {
			if(row.equals(layout.get(i))) {
				pos = i;
				break;
			}
		}
		return pos;
	}
	
	public static String getLevelSpace(int level) {
		StringBuffer sb = new StringBuffer();
		
		for(int i=0; i<level; i++) {
			sb.append("- ");
		}
		
		if(level >= 0) {
			sb.append(String.format("%03d", level));
		}
		return sb.toString();
	}

	public static void printMap(Map<String, Object> map) {
		printMap(map, 0);
	}
	public static void printMap(Map<String, Object> map, int level) {
		for(String key : map.keySet()) {
			Object o = map.get(key);
			if(o instanceof String) {
				log(getLevelSpace(level) + " - " + key + " = " + (String)o);
			}
			else if(o instanceof Integer) {
				log(getLevelSpace(level) + " - " + key + " = " + (Integer)o);
			}
			else if(o instanceof Long) {
				log(getLevelSpace(level) + " - " + key + " = " + (Long)o);
			}
			else if(o instanceof List) {
				log(getLevelSpace(level) + " - " + key + "-> LIST[" + ((ArrayList)o).size() +"]");
				ArrayList l = (ArrayList)o;
				printList(l, level+1);
			}
			else if(o instanceof Map) {
				log(getLevelSpace(level) + " - " + key + "-> MAP");
				Map m = (Map)o;
				printMap(m, level+1);
			}
			else {
				log(getLevelSpace(level) + " - " + key + " = " + o);
				log(getLevelSpace(level) + " - " + key + " = " + o.getClass());
			}
		}
	}
	
	public static void log(String msg) {
		if(debug) System.out.println(msg);
	}
	public static void printList(List list, int level) {
		for(int index=0; index< list.size(); index++) {
			Object o = list.get(index);
			if(o instanceof List) {
				log(getLevelSpace(level) + " - LIST["+index+"]");
				printList((List<Object>)o, level+1);
			}
			else if(o instanceof Map) {
				log(getLevelSpace(level) + " - MAP["+index+"]");
				printMap((Map<String, Object>)o, level+1);
			}
		}
	}
	
	public static void main(String args[]) {
		try {
//			testKcreditXML();
			testLayoutXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testOpenDartXML() throws Exception {
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result><status>000</status><message>정상</message><corp_code>00126380</corp_code><corp_name>삼성전자(주)</corp_name><corp_name_eng>SAMSUNG ELECTRONICS CO,.LTD</corp_name_eng><stock_name>삼성전자</stock_name><stock_code>005930</stock_code><ceo_nm>김기남, 김현석, 고동진</ceo_nm><corp_cls>Y</corp_cls><jurir_no>1301110006246</jurir_no><bizr_no>1248100998</bizr_no><adres>경기도 수원시 영통구  삼성로 129 (매탄동)</adres><hm_url>www.sec.co.kr</hm_url><ir_url></ir_url><phn_no>031-200-1114</phn_no><fax_no>031-200-7538</fax_no><induty_code>264</induty_code><est_dt>19690113</est_dt><acc_mt>12</acc_mt></result>";
		Map<String, Object> map = xmlToMap(xmlString, "utf-8");
		log("\n>> print Map");  
		printMap(map);
		
		xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><result><status>000</status><message>정상</message><page_no>1</page_no><page_count>10</page_count><total_count>223</total_count><total_page>23</total_page><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000559</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000486</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000375</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000341</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000083</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00120182</corp_code><corp_name>NH투자증권</corp_name><stock_code>005940</stock_code><corp_cls>Y</corp_cls><report_nm>[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)</report_nm><rcept_no>20200117000030</rcept_no><flr_nm>NH투자증권</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00878915</corp_code><corp_name>DGB금융지주</corp_name><stock_code>139130</stock_code><corp_cls>Y</corp_cls><report_nm>소송등의판결ㆍ결정(자회사의 주요경영사항)</report_nm><rcept_no>20200117800593</rcept_no><flr_nm>DGB금융지주</flr_nm><rcept_dt>20200117</rcept_dt><rm>유</rm></list><list><corp_code>00120571</corp_code><corp_name>롯데칠성음료</corp_name><stock_code>005300</stock_code><corp_cls>Y</corp_cls><report_nm>타법인주식및출자증권취득결정</report_nm><rcept_no>20200117800584</rcept_no><flr_nm>롯데칠성음료</flr_nm><rcept_dt>20200117</rcept_dt><rm>유정</rm></list><list><corp_code>00161709</corp_code><corp_name>퍼시스</corp_name><stock_code>016800</stock_code><corp_cls>Y</corp_cls><report_nm>주식등의대량보유상황보고서(약식)</report_nm><rcept_no>20200117000661</rcept_no><flr_nm>피델리티매니지먼트앤리서치컴퍼니엘엘씨</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list><list><corp_code>00188089</corp_code><corp_name>한섬</corp_name><stock_code>020000</stock_code><corp_cls>Y</corp_cls><report_nm>주식등의대량보유상황보고서(약식)</report_nm><rcept_no>20200117000657</rcept_no><flr_nm>피델리티매니지먼트앤리서치컴퍼니엘엘씨</flr_nm><rcept_dt>20200117</rcept_dt><rm></rm></list></result>";
		Map<String, Object> map2 = xmlToMap(xmlString, "utf-8");
		log("\n>> print Map");
		printMap(map2);
	}
	
	public static void testKcreditXML() throws Exception {
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Envelope><HTTP_HEADER><rsp_cd>200</rsp_cd><service_id>MDS0000318</service_id><inq_agr_yn>1</inq_agr_yn></HTTP_HEADER><Header><commonHeader><useSystemCode>P000001000000018</useSystemCode><serviceId>MDS0000318</serviceId><transactionUniqueId/><agreementYn>Y</agreementYn><timeStampToken/><apiKeyHashCode>06f9b9607d60b201c26c0eefc55d111d</apiKeyHashCode></commonHeader></Header><Body><request><userVerifyInfo><userCid>MaAZ8pNBUKEQHw/tw2ay1n+g+iJY9GD2CqL6GISEWT480RBYNLr34MzWCDCU9SdAHDgDx2jjbSDbaaAdYgwigw==</userCid><userName/><userSexdstn>M</userSexdstn><userBrthdy>650420</userBrthdy><userEmail/><userRrn/><userTelno/><ntvfrnrSe>L</ntvfrnrSe></userVerifyInfo><reqstInfo><utlinsttCode>P000001000000018</utlinsttCode><bundleId>MDS0000318</bundleId></reqstInfo><bundleInfo><L048K001/><L048K002>650420</L048K002><L048K003>1288221</L048K003><L048K004/><L048K005/><L048K006/><L048K007/><L048K008/><L048K009>2018</L048K009><L048K010>2019</L048K010><L048K011>B1013</L048K011></bundleInfo><bundleInfo><L048K001/><L048K002>650420</L048K002><L048K003>1288221</L048K003><L048K004/><L048K005/><L048K006/><L048K007/><L048K008/><L048K009>2018</L048K009><L048K010>2019</L048K010><L048K011>B1013</L048K011></bundleInfo></request></Body></Envelope>";
		debug = true;
		Map<String, Object> map = xmlToMap(xmlString, "utf-8");
//		log("\n>> print Map");
//		printMap(map);	
	}
	
	public static void printLayout(ArrayList<LayoiutItemRow> layout) throws Exception {
		for(int i=0; i<layout.size(); i++ ) {
			System.out.println(String.format("%03d",  i+1) + "," +  layout.get(i).toCsvString());
		}
	}
	
	public static void testLayoutXML() throws Exception {
		String xmlString = null;
//		xmlString = "<?xml version=\"1.0\" encoding=\"euc-kr\"?><건강보험자격득실확인서>	<입력항목> <신상정보><성명>NS40</성명><주민번호><앞번호>YS6</앞번호><뒷번호>YS7</뒷번호></주민번호></신상정보><득실정보><코드>NS4</코드><사유>NS100</사유></득실정보><득실정보><코드>NS4</코드><사유>NS100</사유></득실정보><득실정보><코드>NS4</코드><사유>NS100</사유></득실정보><Result><ResultCode>NS10</ResultCode><ResultMsg>NS100</ResultMsg></Result></입력항목></건강보험자격득실확인서>";
		InputStream in = null;
		String fileName = "C:\\Temp\\kcredit-sample.xml";
		ByteArrayOutputStream  baos = null;  
        try {
        	baos = new ByteArrayOutputStream();
        	byte[]  buff = new byte[1024];
            in = new BufferedInputStream(new FileInputStream(fileName));
            int readLength = 0;
            while((readLength = in.read(buff)) > 0) {
            	baos.write(buff, 0, readLength);            	
            }
            byte[] readBytes = baos.toByteArray();;
            xmlString = new String(readBytes, "euc-kr");
            System.out.println("xmlString = [" + xmlString+ "]");
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (in != null) {
                try { in.close(); } catch (Exception e) { }
            }
            if (baos != null) {
            	try { baos.close(); } catch (Exception e) { }
            }
        }
		debug = true;
		ArrayList<LayoiutItemRow> layout = xmlToLayout(xmlString, "euc-kr");
		printLayout(layout);
	}
}
