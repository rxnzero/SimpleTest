package com.dhlee.nio.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ListFileTest {

	public ListFileTest() {

	}

	public static void main(String[] args) throws IOException {
		int maxFiles = 10;
		String fileParh = "D:\\tmp\\zookeeper\\version-2";

		System.out.println(">> Test big size dir... too many files");
		ioTest(fileParh, maxFiles);
		nioTest(fileParh, maxFiles);
	}

	private static void ioTest(String filePath, int maxFiles) throws IOException {
		int i = 1;
		System.out.println("use old io method.");
		long start = System.currentTimeMillis();
		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();
		// System.out.println("Total : " + listOfFiles.length);
		for (File file : listOfFiles) {
			System.out.println("" + i + ": " + file.getName());
			if (++i > maxFiles)
				break;
		}

		long stop = System.currentTimeMillis();
		System.out.println("Elapsed: " + (stop - start) + " ms");
	}

	private static void nioTest(String filePath, int maxFiles) throws IOException {
		int i = 1;
		System.out.println("use nio method.");
		long start = System.currentTimeMillis();
		Path dir = FileSystems.getDefault().getPath(filePath);
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);

		for (Path path : stream) {
			System.out.println("" + i + ": " + path.getFileName());
			if (++i > maxFiles)
				break;
		}
		stream.close();
		long stop = System.currentTimeMillis();
		System.out.println("Elapsed: " + (stop - start) + " ms");
	}
}
