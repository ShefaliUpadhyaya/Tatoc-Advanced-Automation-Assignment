package tatocadvancedtest;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.JavascriptExecutor;

public class HoverMenu {
	JavascriptExecutor js;
	
	public HoverMenu(JavascriptExecutor js) {
		this.js = js;
	}
	
	public void clickGoNext() {
		js.executeScript("document.querySelector('body > div > div.page > div.menutop.m2 > span:nth-child(5)').click();");
		String title = js.executeScript("return document.querySelector('body > div > div.page > h1').textContent;").toString();
		assertEquals(title, "Query Gate");
	}
}
