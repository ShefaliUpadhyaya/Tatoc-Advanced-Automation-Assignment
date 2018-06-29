package tatocadvancedtest;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.JavascriptExecutor;

//import tatocadvanced.Connectivity;


public class QueryGate {
	JavascriptExecutor js;
	Connection connection;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public QueryGate(JavascriptExecutor js, Connectivity connectivity) {
		this.js = js;
		this.connection = connectivity.connection;
	}
	
	private void clickProceed() {
		js.executeScript("document.getElementById('submit').click();");
	}
	
	public void clickProceedWithoutEnteringCredentials() {
		clickProceed();
	}
	
	public void invalidCredentials() {
		js.executeScript("document.getElementById('name').value = 'Hello';");
		js.executeScript("document.getElementById('passkey').value = 'Hello';");
		clickProceed();
		String title = js.executeScript("return document.querySelector('body > div > div.page > h1').textContent;").toString();
		assertEquals(title, "Error");
	}
	
	private int fetchIdFromIdentity() {
		String symbol = js.executeScript("return document.getElementById('symboldisplay').textContent;").toString();
		try {
			ps = connection.prepareStatement("select id from identity where symbol = '" + symbol + "'");
			rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}
	
	public void fetchNameAndPasskeyFromCredentials() {
		String name = null, passkey = null;
		int id = fetchIdFromIdentity();
		try {
			ps = connection.prepareStatement("select name,passkey from credentials where id = " + id);
			rs = ps.executeQuery();
			if(rs.next()) {
				name = rs.getString(1);
				passkey = rs.getString(2);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		js.executeScript("document.getElementById('name').value = '" + name + "';");
		js.executeScript("document.getElementById('passkey').value = '" + passkey + "';");
		clickProceed();
		String title = js.executeScript("return document.querySelector('body > div > div.page > h1').textContent;").toString();
		assertEquals(title, "Ooyala Video Player");
	}
 }
