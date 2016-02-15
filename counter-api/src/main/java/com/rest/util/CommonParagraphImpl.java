package com.rest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class CommonParagraphImpl implements CommonParagraph{
	
	private static final Logger logger = LoggerFactory.getLogger(CommonParagraphImpl.class);

	public CommonParagraphImpl() {
		super();
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public String readParagraphFromFile() throws IOException{
		logger.info("Inside readParagraphFromFile() method...");
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("paragraph.txt").getFile());
		FileInputStream fileInputStream = new FileInputStream(file);
		StringBuilder builder = new StringBuilder();
		int ch;
		while((ch = fileInputStream.read()) != -1){
		    builder.append((char)ch);
		}
		return builder.toString().replaceAll("\r", "").replaceAll("\n", "");
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public Map<Integer,List<String>> getWordMapListCount(String data) {
		logger.info("Inside getWordMapListCount() method...");
		String word[] = data.split(" ");
		List<String> wordList = Arrays.asList(word);
		TreeMap<Integer,List<String>> wordsCountMap = new TreeMap<Integer,List<String>>();
		Set<String> uniqueWords = new HashSet<String>(wordList);
		for (String wordValue : uniqueWords) {
			Integer wordfrequency = Collections.frequency(wordList, wordValue);
			if(wordsCountMap.containsKey(wordfrequency)){
				wordsCountMap.get(wordfrequency).add(wordValue);
			}else{
				List<String> wordValueList = new ArrayList<String>();
				wordValueList.add(wordValue);
				wordsCountMap.put(wordfrequency, wordValueList);
			}
		}
		return wordsCountMap.descendingMap();
	  }
	
	/**
	 * 
	 * @param wordMap
	 * @param myMap
	 * @return
	 */
	public Map<String, Map<String, String>> getJSONMapData(Map<Integer,List<String>> wordMap,Map<String, List<String>> myMap){
		logger.info("Inside getJSONMapData() method...");
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		Map<String, String> resultMap = new HashMap<String, String>();
		for (String keyElement : myMap.keySet()) {
			List<String> listOfSearchText = myMap.get(keyElement);
			Outer:
			for (String searchText : listOfSearchText) {
				for (Integer wordKeyElementCount : wordMap.keySet()) {
					boolean checkWordExist = false;
					List<String> paragraphWords = wordMap.get(wordKeyElementCount);
					for (String paragraphWord : paragraphWords) {
						if(StringUtils.pathEquals(paragraphWord, searchText)){
							resultMap.put(searchText, String.valueOf(wordKeyElementCount));
							checkWordExist = true;
							continue Outer;
						}
					}
					if(!checkWordExist){
						resultMap.put(searchText, "0");
					}
				}
			}
		}
		map.put("counts", resultMap);
		return map;
	}
	
	/**
	 * 
	 * @param wordMap
	 * @param topCount
	 * @return
	 */
	public String getTopData(Map<Integer,List<String>> wordMap,int topCount){
		logger.info("Inside getTopData() method...");
		StringBuilder topWord = new StringBuilder();
		int index=1;
			for (Integer wordCountValue : wordMap.keySet()) {
				List<String> topWordList = wordMap.get(wordCountValue);
				for (String wordValue : topWordList) {
					topWord.append(wordValue).append("|").append(wordCountValue).append("\r\n");
				}
				if(index==topCount){
					break;
				}
				index=index+1;
		}
		return topWord.toString();
	}
}
