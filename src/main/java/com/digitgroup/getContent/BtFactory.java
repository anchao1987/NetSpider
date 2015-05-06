package com.digitgroup.getContent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class BtFactory {
	private final static Logger log = Logger.getLogger(BtFactory.class);

	public  static void getBtorrent (String id,String name) throws ClientProtocolException, IOException
	{
		log.info("Get Torrent id " + id + ",name " + name);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("type", "torrent"));
		formParams.add(new BasicNameValuePair("id", id));
		formParams.add(new BasicNameValuePair("name",name));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
		HttpPost httpPost = new HttpPost("http://down.happytogether2015.com/freeone/down.php");
		httpPost.setEntity(entity);
		File file = new File("D://btfactory//" + name + ".torrent");
		FileOutputStream outputStream = null;

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);
		HttpEntity httpEntity = response.getEntity();		
		log.info("begin download " + file.getName());
		outputStream = FileUtils.openOutputStream(file);
		httpEntity.writeTo(outputStream);
		outputStream.flush();
		outputStream.close();
		log.info("file download complete");


	}
	
	/**
	 * ∂¡»°ƒ⁄»›
	 * @param path
	 * @return
	 */
	public static List<String> readContent(String path)
	{
		List<String> contents = new ArrayList<String>();
		File srcDir = new File(path);
		String[] extensions = {"html","HTML"};
		Iterator<File> srcFileIter = FileUtils.iterateFiles(srcDir, extensions, false);
		try
		{
			while (srcFileIter.hasNext())
			{
				File srcFile = srcFileIter.next();
				FileInputStream fileInput = FileUtils.openInputStream(srcFile);
				String content = IOUtils.toString(fileInput, "UTF-8");
				contents.add(content);
			}
		}
		catch (IOException err)
		{
			log.error(err.toString());
		}

		return contents;
	}
	
	/**
	 * Ω‚ŒˆURL
	 * @param content
	 * @return
	 */
	public static List<String> parseContent(List<String> contents)
	{
		List<String> urls = new ArrayList<String>();
		for (String content : contents)
		{
			Document doc = Jsoup.parse(content);
			Elements elements = doc.select("a[href*=happytogether2015");		
			for (Element element : elements)
			{
				urls.add(element.ownText());
			}
		}
		return urls;
	}
	
	
	public static void main (String[] args)
	{
		List<String> contents = readContent("D://btfactory//source");
		List<String> urls = parseContent(contents);
		
		int j = 3;
		while (j < urls.size())
		{
			try
			{
				log.info("Now tackle :" + urls.get(j));
				HttpGet torrentGet = new HttpGet(urls.get(j));
				j++;			
				CloseableHttpClient torrentClient = HttpClients.createDefault();
				CloseableHttpResponse torrentResp = torrentClient.execute(torrentGet);
				Document doc = Jsoup.parse(EntityUtils.toString(torrentResp.getEntity(),"UTF-8"));
				Elements idElem = doc.select("#id");
				Elements nameElem = doc.select("#name");
				getBtorrent(idElem.val(), nameElem.val());
			}
			catch (Exception err)
			{
				log.error("Tackle Fial,Repeat " + err.toString());
			}
		}

	}

}
