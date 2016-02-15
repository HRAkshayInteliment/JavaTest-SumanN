/**
 * 
 */
package com.rest.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author spalleti
 *
 */
public interface CommonParagraph {

	public abstract String readParagraphFromFile() throws IOException;
	public abstract Map<Integer,List<String>> getWordMapListCount(String data);
	public abstract Map<String, Map<String, String>> getJSONMapData(Map<Integer,List<String>> wordMap,Map<String, List<String>> myMap);
	public abstract String getTopData(Map<Integer,List<String>> wordMap,int topCount);
}
