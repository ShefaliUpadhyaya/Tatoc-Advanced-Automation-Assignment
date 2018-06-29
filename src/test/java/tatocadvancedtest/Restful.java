package tatocadvancedtest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.JavascriptExecutor;

import org.json.JSONObject;

public class Restful {
	
	JavascriptExecutor js;
	URL url;
	HttpURLConnection httpURLConnection;
	String session_id_value = null, token = null;
	
	public Restful(JavascriptExecutor js) {
		this.js = js;
	}
	
	private void clickProceed() {
		js.executeScript("document.getElementsByTagName('a')[0].click();");
	}
	
	public void clickProceedWithoutGeneratingToken() {
		clickProceed();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		String title = js.executeScript("return document.querySelector('body > div > div.page > h1').textContent;").toString();
		assertEquals(title, "Error");
	}
	
	public void generateTokenUsingRestfulService() {
		String session_id = js.executeScript("return document.querySelector('#session_id').textContent;").toString();
		session_id_value = session_id.substring(12);
		String get_url = "http://10.0.1.86/tatoc/advanced/rest/service/token/" + session_id_value;
		try {
			url = new URL(get_url);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
	        httpURLConnection.setRequestProperty("accept", "application/json");
	        int responseCode = httpURLConnection.getResponseCode();
	        if(responseCode == HttpURLConnection.HTTP_OK) {
	        	BufferedReader in = new BufferedReader(
		                new InputStreamReader(httpURLConnection.getInputStream()));
		        String inputLine;
		        StringBuffer response = new StringBuffer();
		        while ((inputLine = in.readLine()) != null) {
		        	response.append(inputLine);
		        }
		        in.close();
		        JSONObject myResponse = new JSONObject(response.toString());
		        token = myResponse.getString("token");
	        }    
		} 
		catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		 catch (IOException e) {
			 System.out.println(e.getMessage());
		}
		assertTrue(token!=null);
	}
	
	public void registerForAccess() {
		String post_url = "http://10.0.1.86/tatoc/advanced/rest/service/register";
	    try {
			url = new URL(post_url);
			httpURLConnection = (HttpURLConnection) url.openConnection(); 
		    httpURLConnection.setDoOutput(true);
		    httpURLConnection.setRequestMethod("POST");
	        httpURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		    OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
		    String str = "id="+ session_id_value + "& signature="+ token + "&allow_access=1";
		    writer.write(str);
		    writer.flush();
		    String line;
		    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		    while ((line = reader.readLine()) != null) {
		      System.out.println(line);
		    }
		    writer.close();
		    reader.close();

		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
	    catch (IOException e) {
	    	System.out.println(e.getMessage());
		}
	    clickProceed();
	    try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	    String title = (String)js.executeScript("return document.querySelector('body > div > div.page > h1').textContent;");
	    assertEquals(title, "File Handle");
	}
	
}
