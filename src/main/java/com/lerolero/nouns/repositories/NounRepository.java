package com.lerolero.nouns.repositories;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.DefaultResourceLoader;

public class NounRepository {

	private ResourceLoader loader = new DefaultResourceLoader();

	private String fileName = "nouns.dat";

	public String pullRandom() {
		Resource resource = loader.getResource(fileName);
		try {
			String[] words = resource.getContentAsString(Charset.defaultCharset()).split("\n");
			Integer randindex = (int)(Math.random() * words.length);
			return words[randindex];
		} catch (IOException e) {
			return null;
		}
	}
	
}
