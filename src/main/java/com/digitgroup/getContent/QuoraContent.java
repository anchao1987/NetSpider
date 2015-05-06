/**
 * 
 */
package com.digitgroup.getContent;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

/**
 * @author Moking
 *
 */
public class QuoraContent {
	private final static Logger log = Logger.getLogger(QuoraContent.class);
	public void login()
	{
		try {
			HttpGet httpGet = new HttpGet("http://www.quora.com/");

			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpGet);
			Document doc = Jsoup.parse(EntityUtils.toString(response.getEntity()));
			Elements email = doc.select("input[name=email]");
			email.val("netspider4kindle@126.com");
			Elements password = doc.select("input[name=password]");
			password.val("netspider8302");
			System.out.println("++++++++++++++_content+++++++++++++++");
			System.out.println(doc.toString());


		} catch (ClientProtocolException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		
		
	}
	public static void main(String[] args)
	{
		new QuoraContent().login();
	}
}
