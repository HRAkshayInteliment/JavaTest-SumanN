package com.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.rest.util.ApplicationConstants;

public class RestClient {
	
	private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
	private String loclaHostUrl;
	private String portNo;
	
	/**
	 * 
	 * @return the String
	 */
	public String getParagraph() {
		if(logger.isInfoEnabled())
			logger.info("Inside getParagraph() method...");
		StringBuilder output = new StringBuilder();
		try {
			StringBuffer paragraph = new StringBuffer();
			paragraph.append("http://");
			paragraph.append(loclaHostUrl);
			paragraph.append(":");
			paragraph.append(portNo);
			paragraph.append("/counter-api/paragraph");
			
			// create HTTP Client
			HttpClient httpClient = HttpClientBuilder.create().build();
			// Create new getRequest with below mentioned URL
			HttpGet getRequest = new HttpGet(paragraph.toString());
			// Add additional header to getRequest which accepts application/xml data
			BasicHeader basicHeader = new BasicHeader(HTTP.CONTENT_TYPE,MediaType.APPLICATION_XML_VALUE);
			getRequest.addHeader(basicHeader);
			getRequest.addHeader(ApplicationConstants.AUTHORIZATION_STRING,ApplicationConstants.AUTHORIZATION_VALUE);
			// Execute your request and catch response
			HttpResponse response = httpClient.execute(getRequest);
			if(logger.isInfoEnabled())
				logger.info("response created successfully for the request "+ApplicationConstants.PARAGAPH_URI);

			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				if(logger.isErrorEnabled()){
					logger.error("Failed : HTTP error code : "+ response.getStatusLine().getStatusCode());
					throw new RuntimeException("Failed : HTTP error code : "+ response.getStatusLine().getStatusCode());
				}
			}
			// Get-Capture Complete application/xml body response
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String line;
			// Simply iterate through XML response and show on console.
			while ((line = br.readLine()) != null) {
				output.append(line);
			}
			logger.debug("Paragraph : ["+output.toString()+"]");
		} catch (ClientProtocolException e) {
			logger.error("Error occured in ClientProtocolException",e);
		} catch (IOException e) {
			logger.error("Error occured in IOException",e);
		}
		return output.toString();
	}

	/**
	 * @return the loclaHostUrl
	 */
	public String getLoclaHostUrl() {
		return loclaHostUrl;
	}

	/**
	 * @param loclaHostUrl the loclaHostUrl to set
	 */
	public void setLoclaHostUrl(String loclaHostUrl) {
		this.loclaHostUrl = loclaHostUrl;
	}

	/**
	 * @return the portNo
	 */
	public String getPortNo() {
		return portNo;
	}

	/**
	 * @param portNo the portNo to set
	 */
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}
}
