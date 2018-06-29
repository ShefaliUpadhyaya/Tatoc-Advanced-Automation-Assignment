package tatocadvancedtest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.openqa.selenium.JavascriptExecutor;

public class FileHandle {
	
	JavascriptExecutor js;
	private static String downloadPath = "C:\\Users\\Shefaliupadhyaya\\Downloads";
	
	public FileHandle(JavascriptExecutor js) {
		this.js = js;
	}
	
	private void clickProceed() {
		js.executeScript("document.getElementsByClassName('submit')[0].click();");
	}
	
	public void clickingProceedWithoutEnteringSignature() {
		clickProceed();
		String title = js.executeScript("return document.querySelector('body > div > div.page > h1').textContent;").toString();
		assertEquals(title, "Error");
	}
	
	public void invalidSignature() {
		js.executeScript("document.querySelector('#signature').value = 'Hello';");
		clickProceed();
		String title = (String)js.executeScript("return document.querySelector('body > div > div.page > h1').textContent;");
	    assertEquals(title, "Error");
	}
	
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		 boolean flag = false;
		    File dir = new File(downloadPath);
		    File[] dir_contents = dir.listFiles();
		    for (int i = 0; i < dir_contents.length; i++) {
		        if (dir_contents[i].getName().equals(fileName))
		            return flag=true;
		            }

		    return flag;
	}
	
	public void downloadFile() {
		js.executeScript("document.getElementsByTagName('a')[0].click();");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		assertTrue(isFileDownloaded(downloadPath, "file_handle_test.dat"));
	}
	
	public void readAndSubmitSignature() {
		File file = new File(downloadPath+"\\file_handle_test.dat");
		Scanner s;
		String str = null;
		try {
			s = new Scanner(file);
			while (s.hasNextLine()) {
				str = s.nextLine();
			    if(str.contains("Signature")) {
			    	str = str.substring(11); 
			    	break;
			    }
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}	 
		js.executeScript("document.querySelector('#signature').value = '" + str + "';");
		clickProceed();
		String title = (String)js.executeScript("return document.querySelector('body > div > div.page > h1').textContent;");
	    assertEquals(title, "End");
	}
}
