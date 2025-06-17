package com.dhlee.gzip;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GzipTest {
	private static ObjectMapper mapper = new ObjectMapper();
	public static void main(String[] args) {
		String rootFilePath = "d:/tmp";
		ObjectNode json = mapper.createObjectNode();
		json.put("encData", "H4sIAAAAAAAAAAEtANL/7JWV7LaV7ZWY6rOgIOyduOy9lOuUqe2VoCDrrLjsnpDsl7TsnoXri4jri6QuX4WeTS0AAAA=");
		String jsonString = json.toString();
		System.out.println(jsonString);
		try {
			System.out.println(unzipAndSave(jsonString, rootFilePath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getGzipValue(String jsonString, String path) {
		try {
			JsonNode rootNode = mapper.readTree(jsonString);
			JsonNode childNode = rootNode.get(path);
			String gzippedValue = childNode.asText();
			return gzippedValue;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String unzipAndSave(String jsonString, String rootFilePath) throws Exception {
	    String filePath = "";
	    String fileName = "";

	    // gzippedValue 추출
	    String gzippedValue = getGzipValue(jsonString, "encData");
	    System.out.println("gzippedValue[" + gzippedValue + "]");

	    // 압축 해제
	    String unziped = GzipBase64Example.decompress(gzippedValue);

	    // 현재 처리 시각을 기반으로 파일명 생성
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
	    fileName = LocalDateTime.now().format(formatter) + ".txt";
	    
	    // 파일 경로 설정
	    filePath = Paths.get(rootFilePath, fileName).toString();
	    
	    // unziped 데이터를 파일에 저장
	    Files.write(Paths.get(filePath), unziped.getBytes(), StandardOpenOption.CREATE);

	    // 결과 JSON 구성
	    ObjectNode replacedJson = mapper.createObjectNode();
	    replacedJson.put("filePath", filePath);
	    replacedJson.put("fileName", fileName);
	    
	    return replacedJson.toString();
	}
	
}
