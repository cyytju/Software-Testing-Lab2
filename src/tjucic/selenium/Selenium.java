package tjucic.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Selenium {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String[] studentId = new String[144];
  private String[] studentPassword = new String[144];
  private String[] studentGit = new String[144];
  
  @Before
  public void setUp() throws Exception {
	  
	  File f=new File("D:" + File.separator + "MyDownloads"+ File.separator + "软件测试名单.xls");
	  //D:\MyDownloads
	  try {
			Workbook book=Workbook.getWorkbook(f);//
			Sheet sheet=book.getSheet(0);   //获得第一个工作表对象
			for(int i=2;i<sheet.getRows();i++){
				Cell cell=sheet.getCell(1, i);  //获得单元格
				studentId[i-2] = cell.getContents();
				//System.out.print(studentId[i-2]+" ");
				
				int length = studentId[i-2].length();
		        if(length >= 6){
		        	studentPassword[i-2] = studentId[i-2].substring(length-6, length);
		            //System.out.println(str);
		        }else{
		            //System.out.println("学号密码不存在");
		        }
		        //System.out.print(studentPassword[i-2]+" ");
		        
				cell=sheet.getCell(3, i);
				studentGit[i-2] = cell.getContents();
				//System.out.print(studentGit[i-2]+" ");
				//System.out.print("\n");
	    }
	  } catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
	  
	  String driverPath = System.getProperty("user.dir") + "/src/resources/driver/geckodriver.exe";
	  System.setProperty("webdriver.gecko.driver", driverPath);
	  driver = new FirefoxDriver();
	  baseUrl = "http://121.193.130.195:8800/";
	  driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
  }

  @Test
  public void testSelenium() throws Exception {
	driver.get(baseUrl + "/");
	for(int i = 0; i <= 142; i++) { // 共143
		//driver.get(baseUrl + "/");
	    driver.findElement(By.name("id")).sendKeys(studentId[i]);
	    driver.findElement(By.name("password")).sendKeys(studentPassword[i]);
	    driver.findElement(By.id("btn_login")).click();
	    assertEquals(studentGit[i], driver.findElement(By.id("student-git")).getText());
	    driver.findElement(By.id("btn_logout")).click();
	    driver.findElement(By.id("btn_return")).click();
	    
		/*System.out.print(studentId[i]+" ");
	    System.out.print(studentPassword[i]+" ");
	    System.out.print(studentGit[i]+" ");
	    System.out.print("\n");*/
	}
	  
    //WebElement we = driver.findElement(By.id("kw"));
    //we.click();
//  driver.findElement(By.id("kw")).click();
    //driver.findElement(By.id("kw")).clear();
    //driver.findElement(By.id("kw")).sendKeys("天津大学");
    //driver.findElement(By.id("su")).click();
    //assertEquals("天津大学_百度搜索", driver.getTitle());
	
	/*driver.get(baseUrl + "/");
    driver.findElement(By.name("id")).sendKeys("3016218080");
    driver.findElement(By.name("password")).sendKeys("218080");
    driver.findElement(By.id("btn_login")).click();
    assertEquals("https://github.com/cyytju", driver.findElement(By.id("student-git")).getText());
    driver.findElement(By.id("btn_logout")).click();*/
    
  }

  @After
  public void tearDown() throws Exception {
//    driver.quit();
//    String verificationErrorString = verificationErrors.toString();
//    if (!"".equals(verificationErrorString)) {
//      fail(verificationErrorString);
//    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
  
}