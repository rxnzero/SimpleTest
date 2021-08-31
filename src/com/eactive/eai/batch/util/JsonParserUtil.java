package com.eactive.eai.batch.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JsonParserUtil {

	public static void main(String[] args) {
		testSingle();
//		testGrid();
//		testSingleList();
//		testGridList();
		
//		testApisGridList();
		testApisGrid();
	}
	
	public static void testSingle() {	
		System.out.println("\n>> testSingle");
		String jsonString = 
				"{\"status\":\"000\",\"message\":\"����\",\"corp_code\":\"01204843\",\"corp_name\":\"�ֽ�ȸ�� ������ó��Ʈ�ʽ�\",\"corp_name_eng\":\"Sejong Venture Partners Co., Ltd.\",\"stock_name\":\"������ó��Ʈ�ʽ�\",\"stock_code\":\"\",\"ceo_nm\":\"���ذ�\",\"corp_cls\":\"E\",\"jurir_no\":\"1101115088665\",\"bizr_no\":\"2648112657\",\"adres\":\"����Ư���� ������ �������38�� 5 ��Ÿ�ú��� 6��\",\"hm_url\":\"\",\"ir_url\":\"\",\"phn_no\":\"070-4667-0760\",\"fax_no\":\"02-595-0760\",\"induty_code\":\"64\",\"est_dt\":\"20130314\",\"acc_mt\":\"12\"}";
		Map<String, Object> map = jsonToMap(jsonString);
		printMap(map);
	}
	
	public static void testGrid() {
		System.out.println("\n>> testGrid");
		String jsonString = 
		"{\"status\":\"000\",\"message\":\"����\",\"list\":[{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1986.09.01\",\"isu_dcrs_stle\":\"��������\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"40,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2003.04.18\",\"isu_dcrs_stle\":\"�ֽĹ��\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"156,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2004.03.15\",\"isu_dcrs_stle\":\"��������\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"5,356,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2010.03.30\",\"isu_dcrs_stle\":\"�ֽĹ��\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"2,142,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2014.08.01\",\"isu_dcrs_stle\":\"��������(��3�ڹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"3,504,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2018.01.26\",\"isu_dcrs_stle\":\"��ȯ�����\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"1,517,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1990.04.07\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"20,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1990.08.10\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"20,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1992.11.14\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"40,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1999.12.30\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"96,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2000.03.31\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"24,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2000.03.31\",\"isu_dcrs_stle\":\"��������\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"120,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2000.06.02\",\"isu_dcrs_stle\":\"�ֽĺ���\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"3,240,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2000.10.11\",\"isu_dcrs_stle\":\"��������(�Ϲݰ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"1,600,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"}]}";
		Map<String, Object> map = jsonToMap(jsonString);
		printMap(map);
	}
	
	public static void testSingleList() {	
		System.out.println("\n>> testSingle");
		String jsonString = 
				"{\"status\":\"000\",\"message\":\"����\",\"corp_code\":\"01204843\",\"corp_name\":\"�ֽ�ȸ�� ������ó��Ʈ�ʽ�\",\"corp_name_eng\":\"Sejong Venture Partners Co., Ltd.\",\"stock_name\":\"������ó��Ʈ�ʽ�\",\"stock_code\":\"\",\"ceo_nm\":\"���ذ�\",\"corp_cls\":\"E\",\"jurir_no\":\"1101115088665\",\"bizr_no\":\"2648112657\",\"adres\":\"����Ư���� ������ �������38�� 5 ��Ÿ�ú��� 6��\",\"hm_url\":\"\",\"ir_url\":\"\",\"phn_no\":\"070-4667-0760\",\"fax_no\":\"02-595-0760\",\"induty_code\":\"64\",\"est_dt\":\"20130314\",\"acc_mt\":\"12\"}";
		List<Map<String, Object>> list = jsonToMapList(jsonString);
		printList(list);
	}
	
	public static void testGridList() {
		System.out.println("\n>> testGrid");
		String jsonString = 
		"{\"status\":\"000\",\"message\":\"����\",\"list\":[{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1986.09.01\",\"isu_dcrs_stle\":\"��������\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"40,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2003.04.18\",\"isu_dcrs_stle\":\"�ֽĹ��\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"156,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2004.03.15\",\"isu_dcrs_stle\":\"��������\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"5,356,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2010.03.30\",\"isu_dcrs_stle\":\"�ֽĹ��\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"2,142,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2014.08.01\",\"isu_dcrs_stle\":\"��������(��3�ڹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"3,504,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2018.01.26\",\"isu_dcrs_stle\":\"��ȯ�����\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"1,517,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1990.04.07\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"20,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1990.08.10\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"20,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1992.11.14\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"40,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"1999.12.30\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"96,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2000.03.31\",\"isu_dcrs_stle\":\"��������(���ֹ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"24,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2000.03.31\",\"isu_dcrs_stle\":\"��������\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"120,000\",\"isu_dcrs_mstvdv_fval_amount\":\"5,000\",\"isu_dcrs_mstvdv_amount\":\"5,000\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2000.06.02\",\"isu_dcrs_stle\":\"�ֽĺ���\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"3,240,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"},{\"rcept_no\":\"20190820000266\",\"corp_cls\":\"K\",\"corp_code\":\"00293886\",\"corp_name\":\"���н�\",\"isu_dcrs_de\":\"2000.10.11\",\"isu_dcrs_stle\":\"��������(�Ϲݰ���)\",\"isu_dcrs_stock_knd\":\"������\",\"isu_dcrs_qy\":\"1,600,000\",\"isu_dcrs_mstvdv_fval_amount\":\"500\",\"isu_dcrs_mstvdv_amount\":\"500\"}]}";
		List<Map<String, Object>> list = jsonToMapList(jsonString);
		printList(list);
	}
	
	public static void testApisGridList() {
		System.out.println("\n>> testApisGridList");
		String jsonString = 
		"{\"response\":{\"body\":{\"items\":{\"item\":[{\"basDt\":\"20210825\",\"crno\":\"0000000000000\",\"corpNm\":\"SAMPO FUND MANAGEMENT LTD/MANDATUM EMERGING\",\"corpEnsnNm\":\"SAMPO FUND MANAGEMENT LTD/MANDATUM EMERGING\",\"enpPbanCmpyNm\":\"SAMPOFUNDMANAGEMENTLTD/MANDATUMEMERGING\",\"enpRprFnm\":\"KIMMO LAAKSONEN\",\"corpRegMrktDcd\":\"E\",\"corpRegMrktDcdNm\":\"��Ÿ\",\"corpDcd\":\"\",\"corpDcdNm\":\"\",\"bzno\":\"013646\",\"enpOzpno\":\"110762\",\"enpBsadr\":\"����Ư���� ���α� �Ź���2�� ��Ƽ����\",\"enpDtadr\":\"����Ư���� ���α� �Ź���2�� ��Ƽ����\",\"enpHmpgUrl\":\"\",\"enpTlno\":\"0220041331\",\"enpFxno\":\"0220041987\",\"sicNm\":\"99009\",\"enpEstbDt\":\"19990901\",\"enpStacMm\":\"12\",\"enpXchgLstgDt\":\"\",\"enpXchgLstgAbolDt\":\"\",\"enpKosdaqLstgDt\":\"\",\"enpKosdaqLstgAbolDt\":\"\",\"enpKrxLstgDt\":\"\",\"enpKrxLstgAbolDt\":\"\",\"smenpYn\":\"\",\"enpMntrBnkNm\":\"\",\"enpEmpeCnt\":\"0\",\"empeAvgCnwkTermCtt\":\"\",\"enpPn1AvgSlryAmt\":\"0\",\"actnAudpnNm\":\"\",\"audtRptOpnnCtt\":\"\",\"enpMainBizNm\":\"\",\"fssCorpUnqNo\":\"00507691\",\"fssCorpChgDtm\":\"2017/06/30\"},{\"basDt\":\"20210825\",\"crno\":\"0000000000000\",\"corpNm\":\"ARMOR QUALIFIED, LP\",\"corpEnsnNm\":\"ARMOR QUALIFIED, LP\",\"enpPbanCmpyNm\":\"ARMORQUALIFIED,LP\",\"enpRprFnm\":\"KRISTINE GUARNERI\",\"corpRegMrktDcd\":\"E\",\"corpRegMrktDcdNm\":\"��Ÿ\",\"corpDcd\":\"\",\"corpDcdNm\":\"\",\"bzno\":\"18792\",\"enpOzpno\":\"100180\",\"enpBsadr\":\"����Ư���� �߱� �ٵ�39����\",\"enpDtadr\":\"����Ư���� �߱� �ٵ�39����\",\"enpHmpgUrl\":\"\",\"enpTlno\":\"212-808-3721\",\"enpFxno\":\"\",\"sicNm\":\"649\",\"enpEstbDt\":\"20060120\",\"enpStacMm\":\"12\",\"enpXchgLstgDt\":\"\",\"enpXchgLstgAbolDt\":\"\",\"enpKosdaqLstgDt\":\"\",\"enpKosdaqLstgAbolDt\":\"\",\"enpKrxLstgDt\":\"\",\"enpKrxLstgAbolDt\":\"\",\"smenpYn\":\"\",\"enpMntrBnkNm\":\"\",\"enpEmpeCnt\":\"0\",\"empeAvgCnwkTermCtt\":\"\",\"enpPn1AvgSlryAmt\":\"0\",\"actnAudpnNm\":\"\",\"audtRptOpnnCtt\":\"\",\"enpMainBizNm\":\"\",\"fssCorpUnqNo\":\"00695437\",\"fssCorpChgDtm\":\"2017/06/30\"}]},\"numOfRows\":100,\"pageNo\":1,\"totalCount\":152708},\"header\":{\"resultCode\":\"00\",\"resultMsg\":\"NORMAL SERVICE.\"}}}";
		List<Map<String, Object>> list = jsonToMapList(jsonString);
		printList(list);
	}
	
	public static void testApisGrid() {
		System.out.println("\n>> testApisGridList");
		String jsonString = 
		"{\"response\":{\"body\":{\"items\":{\"item\":[{\"basDt\":\"20210825\",\"crno\":\"0000000000000\",\"corpNm\":\"SAMPO FUND MANAGEMENT LTD/MANDATUM EMERGING\",\"corpEnsnNm\":\"SAMPO FUND MANAGEMENT LTD/MANDATUM EMERGING\",\"enpPbanCmpyNm\":\"SAMPOFUNDMANAGEMENTLTD/MANDATUMEMERGING\",\"enpRprFnm\":\"KIMMO LAAKSONEN\",\"corpRegMrktDcd\":\"E\",\"corpRegMrktDcdNm\":\"��Ÿ\",\"corpDcd\":\"\",\"corpDcdNm\":\"\",\"bzno\":\"013646\",\"enpOzpno\":\"110762\",\"enpBsadr\":\"����Ư���� ���α� �Ź���2�� ��Ƽ����\",\"enpDtadr\":\"����Ư���� ���α� �Ź���2�� ��Ƽ����\",\"enpHmpgUrl\":\"\",\"enpTlno\":\"0220041331\",\"enpFxno\":\"0220041987\",\"sicNm\":\"99009\",\"enpEstbDt\":\"19990901\",\"enpStacMm\":\"12\",\"enpXchgLstgDt\":\"\",\"enpXchgLstgAbolDt\":\"\",\"enpKosdaqLstgDt\":\"\",\"enpKosdaqLstgAbolDt\":\"\",\"enpKrxLstgDt\":\"\",\"enpKrxLstgAbolDt\":\"\",\"smenpYn\":\"\",\"enpMntrBnkNm\":\"\",\"enpEmpeCnt\":\"0\",\"empeAvgCnwkTermCtt\":\"\",\"enpPn1AvgSlryAmt\":\"0\",\"actnAudpnNm\":\"\",\"audtRptOpnnCtt\":\"\",\"enpMainBizNm\":\"\",\"fssCorpUnqNo\":\"00507691\",\"fssCorpChgDtm\":\"2017/06/30\"},{\"basDt\":\"20210825\",\"crno\":\"0000000000000\",\"corpNm\":\"ARMOR QUALIFIED, LP\",\"corpEnsnNm\":\"ARMOR QUALIFIED, LP\",\"enpPbanCmpyNm\":\"ARMORQUALIFIED,LP\",\"enpRprFnm\":\"KRISTINE GUARNERI\",\"corpRegMrktDcd\":\"E\",\"corpRegMrktDcdNm\":\"��Ÿ\",\"corpDcd\":\"\",\"corpDcdNm\":\"\",\"bzno\":\"18792\",\"enpOzpno\":\"100180\",\"enpBsadr\":\"����Ư���� �߱� �ٵ�39����\",\"enpDtadr\":\"����Ư���� �߱� �ٵ�39����\",\"enpHmpgUrl\":\"\",\"enpTlno\":\"212-808-3721\",\"enpFxno\":\"\",\"sicNm\":\"649\",\"enpEstbDt\":\"20060120\",\"enpStacMm\":\"12\",\"enpXchgLstgDt\":\"\",\"enpXchgLstgAbolDt\":\"\",\"enpKosdaqLstgDt\":\"\",\"enpKosdaqLstgAbolDt\":\"\",\"enpKrxLstgDt\":\"\",\"enpKrxLstgAbolDt\":\"\",\"smenpYn\":\"\",\"enpMntrBnkNm\":\"\",\"enpEmpeCnt\":\"0\",\"empeAvgCnwkTermCtt\":\"\",\"enpPn1AvgSlryAmt\":\"0\",\"actnAudpnNm\":\"\",\"audtRptOpnnCtt\":\"\",\"enpMainBizNm\":\"\",\"fssCorpUnqNo\":\"00695437\",\"fssCorpChgDtm\":\"2017/06/30\"}]},\"numOfRows\":100,\"pageNo\":1,\"totalCount\":152708},\"header\":{\"resultCode\":\"00\",\"resultMsg\":\"NORMAL SERVICE.\"}}}";
		Map<String, Object> map = jsonToMap(jsonString);
		printMap(map);
	}
	
	public static void printMap(Map<String, Object> map) {
		for(String key : map.keySet()) {
			Object o = map.get(key);
			if(o instanceof String) {
				System.out.println(key + " = " + (String)o);
			}
			else if(o instanceof Integer) {
				System.out.println(key + " = " + (Integer)o);
			}
			else if(o instanceof Long) {
				System.out.println(key + " = " + (Long)o);
			}
			else if(o instanceof List) {
				System.out.println(key + "-> LIST");
				ArrayList l = (ArrayList)o;
				printList(l);
			}
			else if(o instanceof Map) {
				System.out.println(key + "-> MAP");
				Map m = (Map)o;
				printMap(m);
			}
			else {
				System.out.println(key + " = " + o);
				System.out.println(key + " = " + o.getClass());
			}
		}
	}
	
	public static void printList(List list) {
		for(int index=0; index< list.size(); index++) {
			Object o = list.get(index);
			if(o instanceof List) {
				System.out.println("LIST["+index+"]");
				printList((List<Object>)o);
			}
			else if(o instanceof Map) {
				System.out.println("MAP["+index+"]");
				printMap((Map<String, Object>)o);
			}
		}
	}

	public static List<Map<String, Object>> jsonToMapList(String jsonString) {
		JSONObject json = (JSONObject) JSONValue.parse(jsonString);
		Map<String, Object> map = jsonToMap(json);
		return mapToMapList(map);
	}
	
	// Use OpenDart response ONLY!
	public static List<Map<String, Object>> mapToMapList(Map<String, Object> map) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>(); 
		
		Object oList = map.get("list");
		// has inner Grid
		if(oList instanceof List) {
			Map<String, Object> baseMap = createBaseMap(map);
			ArrayList list = (ArrayList)oList;
			for(int index=0; index< list.size(); index++) {
				Object o = list.get(index);
				if(o instanceof Map) {
					Map<String, Object> innerMap = (Map<String, Object>)o;
					innerMap.putAll(baseMap);
					mapList.add(innerMap);
				}
			}
		}
		// Single map
		else {
			mapList.add(map);
		}
		return mapList;
	}
	
	public static Map<String, Object> createBaseMap(Map<String, Object> map) {
		Map<String, Object> baseMap = new LinkedHashMap<String, Object>();
		for(String key : map.keySet()) {
			Object o = map.get(key);
			if(o instanceof String) {
				String value = (String)o;
				baseMap.put(key, value);
			}
		}
		return baseMap;
	}
	
	public static Map<String, Object> jsonToMap(String jsonString) {
		JSONObject json = (JSONObject) JSONValue.parse(jsonString);
		return jsonToMap(json);
	}
	public static Map<String, Object> jsonToMap(JSONObject json) {
        Map<String, Object> retMap = new LinkedHashMap<String, Object>();

        if(json != null) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        Iterator<String> keysItr = object.keySet().iterator();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key.toLowerCase(), value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}

