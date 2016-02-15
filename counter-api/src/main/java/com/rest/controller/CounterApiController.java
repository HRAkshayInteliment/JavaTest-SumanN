package com.rest.controller;
 
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest.client.RestClient;
import com.rest.util.CommonParagraph;
 
/**
 * Handles requests for the application home page.
 */
@RestController
public class CounterApiController {
   
  private static final Logger logger = LoggerFactory.getLogger(CounterApiController.class);
  
  @Autowired
  private CommonParagraph commonParagraphUtil;
  
  @Autowired
  private RestClient restCilent;
  
  public CounterApiController() {
  }
  
  /**
   * 
   * @param myMap
   * @return
   * @throws JsonProcessingException
   * @throws IOException
   */
  @RequestMapping(value="/search", method=RequestMethod.POST)
  public Map<String, Map<String,String>> getSearch(@RequestBody Map<String, List<String>> myMap) throws JsonProcessingException, IOException {
	  if(logger.isInfoEnabled())
		  logger.info("Inside getSearch() method...");
    Map<Integer,List<String>> wordMap = commonParagraphUtil.getWordMapListCount(restCilent.getParagraph());
    Map<String, Map<String, String>> resultMap = commonParagraphUtil.getJSONMapData(wordMap,myMap);
    return resultMap;
  }
  
  /**
   * 
   * @param countValue
   * @return
   * @throws IOException
   */
  @RequestMapping(value="/top/{countValue}", method=RequestMethod.GET)
  public String getTop(@PathVariable("countValue") String countValue) throws IOException {
	  if(logger.isInfoEnabled())
		  logger.info("Inside getTop() method...");
    Map<Integer,List<String>> wordMap = commonParagraphUtil.getWordMapListCount(restCilent.getParagraph());
    return commonParagraphUtil.getTopData(wordMap,Integer.valueOf(countValue).intValue());
  }
  
  /**
   * 
   * @param locale
   * @param model
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/paragraph", method = RequestMethod.GET)
  public String getParagraph(Locale locale, Model model) throws IOException {
	  if(logger.isInfoEnabled())
		  logger.info("Inside getParagraph() method...");
    return commonParagraphUtil.readParagraphFromFile();
  }

/**
 * @return the commonParagraphUtil
 */
public CommonParagraph getCommonParagraphUtil() {
	return commonParagraphUtil;
}

/**
 * @param commonParagraphUtil the commonParagraphUtil to set
 */
public void setCommonParagraphUtil(CommonParagraph commonParagraphUtil) {
	this.commonParagraphUtil = commonParagraphUtil;
}

/**
 * @return the restCilent
 */
public RestClient getRestCilent() {
	return restCilent;
}

/**
 * @param restCilent the restCilent to set
 */
public void setRestCilent(RestClient restCilent) {
	this.restCilent = restCilent;
}

}