package tatocadvancedtest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

//import tatocadvanced.Connectivity;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TatocAdvancedTest {
	WebDriver driver;
	JavascriptExecutor js;
	AdvancedCourse advancedCourse;
	HoverMenu hoverMenu;
	QueryGate queryGate;
	Connectivity connectivity;
	Restful restful;
	FileHandle fileHandle;

	@BeforeClass
	public void launchBrowser() {
		driver = new ChromeDriver();
        js = (JavascriptExecutor)driver;	
		driver.get("http://10.0.1.86/tatoc");
		connectivity = new Connectivity();
		advancedCourse = new AdvancedCourse(js);
		hoverMenu = new HoverMenu(js);
		queryGate = new QueryGate(js, connectivity);
		restful = new Restful(js);
		fileHandle = new FileHandle(js);
    }
	
	@Test(priority = 1)
	public void advanced_Course_Button_Should_Click() {
		advancedCourse.clickButton();
	}
	
	@Test(priority = 2)
	public void menu_2_Item_Click_Should_Display_Query_Gate_Page() {
		hoverMenu.clickGoNext();
	}
	
	@Test(priority = 3)
	public void clicking_Proceed_Without_Credentials_Should_Display_Error_Page() {
		queryGate.clickProceedWithoutEnteringCredentials();
	}
	
	@Test(priority = 4)
	public void clicking_Proceed_On_Entering_Invalid_Credentials_Should_Display_Error_Page() {
		js.executeScript("window.location = 'http://10.0.1.86/tatoc/advanced/query/gate'");
		queryGate.invalidCredentials();
	}
	
	@Test(priority = 5)
	public void valid_Credentials_Should_Go_To_Video_Player_Page() {
		js.executeScript("window.location = 'http://10.0.1.86/tatoc/advanced/query/gate'");
		queryGate.fetchNameAndPasskeyFromCredentials();
	}
	
	@Test(priority = 6)
	public void clicking_Proceed_Without_Generating_Token_Should_Display_Error_Page() {
		js.executeScript("window.location = 'http://10.0.1.86/tatoc/advanced/rest'");
		restful.clickProceedWithoutGeneratingToken();
	}
	
	@Test(priority = 7)
	public void generating_Token_Using_Restful_Service_Should_Be_Successful() {
		js.executeScript("window.location = 'http://10.0.1.86/tatoc/advanced/rest'");
		restful.generateTokenUsingRestfulService();
	}
	
	@Test(priority = 8)
	public void clicking_Proceed_After_Registration_Should_Display_File_Handling_Page() {
		restful.registerForAccess();
	}
	
	@Test(priority = 9)
	public void clicking_Proceed_Without_Signature_Should_Display_Error_Page() {
		fileHandle.clickingProceedWithoutEnteringSignature();
	}
	
	@Test(priority = 10)
	public void clicking_Proceed_With_Invalid_Signature_Should_Display_Error_Page() {
		js.executeScript("window.location = 'http://10.0.1.86/tatoc/advanced/file/handle'");
		fileHandle.invalidSignature();
	}
	
	@Test(priority = 11)
	public void clicking_Download_File_Should_Download_Successfully() {
		js.executeScript("window.location = 'http://10.0.1.86/tatoc/advanced/file/handle'");
		fileHandle.downloadFile();
	}
	
	@Test(priority = 12)
	public void clicked_Proceed_After_Submitting_Signature_Should_Display_Next_Page() {
		fileHandle.readAndSubmitSignature();
	}
	
	@AfterClass
	public void closeBrowser() {
		driver.quit();
	}

}
