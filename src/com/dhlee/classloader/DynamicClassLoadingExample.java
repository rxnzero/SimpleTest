package com.dhlee.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.IOException;
import java.lang.reflect.Method;

public class DynamicClassLoadingExample {
	public static void main(String[] args) {
		URLClassLoader urlClassLoader = null;
		try {
			// 클래스 파일이 위치한 디렉토리 경로 (D:/module)
			URL classUrl = new URL("file:/D:/module/");
			URL[] classUrls = { classUrl };

			// URLClassLoader 생성
			urlClassLoader = new URLClassLoader(classUrls);

			// 로드할 클래스 이름 (패키지명을 포함)
			String className = "com.example.MyClass";

			// 클래스 로드
			Class<?> loadedClass = urlClassLoader.loadClass(className);

			// 클래스 인스턴스 생성
			Object myClassInstance = loadedClass.getDeclaredConstructor().newInstance();

			// myMethod 메서드 가져오기
			Method method = loadedClass.getMethod("myMethod");

			// myMethod 메서드 호출
			method.invoke(myClassInstance);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// URLClassLoader 닫기
			if (urlClassLoader != null) {
				try {
					urlClassLoader.close();
				} catch (IOException e) {
					urlClassLoader = null;
				}
			}
		}
	}
}
