package tatocadvancedtest;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.JavascriptExecutor;

public class AdvancedCourse {
	
	JavascriptExecutor js;
	
	public AdvancedCourse(JavascriptExecutor js) {
		this.js = js;
	}
	
	public void clickButton() {
		js.executeScript("document.getElementsByTagName('a')[1].click();");
		String title = js.executeScript("return document.querySelector('body > div > div.page > h1').textContent;").toString();
		assertEquals(title, "Hover Menu");
	}
}
