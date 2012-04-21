package com.android.a4sp.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;


public class ParsingDataXml {
	private static final String TAG = "ParsingDataXML";
	

	public static Document XMLFromString(String xml) {
		Document document = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			document = db.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			Log.i(TAG, e.getMessage());

		} catch (SAXException e) {
			e.printStackTrace();
			Log.i(TAG, e.getMessage());

		} catch (IOException e) {
			e.printStackTrace();
			Log.i(TAG, e.getMessage());

		}
		return document;
	}

	public static final String getElementValue(Node node) {
		Node kid;
		if (node != null) {
			if (node.hasChildNodes()) {
				for (kid = node.getFirstChild(); kid != null; kid = kid
						.getNextSibling()) {
					if (kid.getNodeType() == Node.TEXT_NODE) {
						return kid.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	public static String getXML(String param) {
		String line = null;
		String url = "http://search.4shared.com/network/searchXml.jsp?q="
				+ param + "&searchExtention=music";
		
		String urlParsed = url.replaceAll(" ","%20");
		Log.i("urlnya adalah", urlParsed);
		
//		split ekstensi mp3 saja 
//	 	String filename = urlParsed;
//        String filenameArray[] = filename.split("\\.");
//        String extension = filenameArray[filenameArray.length-1];
//        Log.i("ekstensinya >> ", extension);
		
		
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(urlParsed);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			line = EntityUtils.toString(httpEntity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Log.i(TAG, e.getMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.i(TAG, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.i(TAG, e.getMessage());
		}		
		
		return line;
	}

	public static int numResult(Document document) {
		Node result = document.getDocumentElement();
		int count = -1;
		try {
			count = Integer.valueOf(result.getAttributes()
					.getNamedItem("count").getNodeValue());
		} catch (Exception e) {
			count = -1;
			e.printStackTrace();
			Log.i(TAG, e.getMessage());
		}
		return count;
	}

	public static String getValue(Element item, String tag) {
		NodeList nodeList = item.getElementsByTagName(tag);
		return ParsingDataXml.getElementValue(nodeList.item(0));
	}
}
