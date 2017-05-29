import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.util.List;

public class Main {

    protected static WebDriver driver;

    protected static void init() throws Exception {
        System.out.println("init...");
        if(new File("drivers\\IEDriverServer.exe").exists())
            System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");
        else throw new Exception("IE driver not found");
        driver = new InternetExplorerDriver();
    }

    public static void main(String[] args) throws Exception {
        try {
            init();
//        driver.get("https://sfi.ru/science/nauchnyj-zhurnal/vypusk-7-maj-2013@user-slead80gmailcom;language=ru.html#SubSectionMenu");
//        driver.get("https://sfi.ru/science/nauchnyj-zhurnal/vypusk-6-dekabr-2012@user-slead80gmailcom;language=ru.html");
//        driver.get("https://sfi.ru/science/nauchnyj-zhurnal/vypusk-5-maj-2012@user-slead80gmailcom;language=ru.html");
            driver.get("https://sfi.ru/science/nauchnyj-zhurnal/vypusk-4-dekabr-2011@user-slead80gmailcom;language=ru.html");

            WebElement elUsername = driver.findElement(By.id("username"));
            elUsername.sendKeys("xxx");
            WebElement elPassword = driver.findElement(By.id("password"));
            elPassword.sendKeys("xxx");
            elPassword.submit();

            handleIssue();

            //publish
            driver.findElement(By.id("ember755")).click();
        } catch (Exception e) {
            driver.quit();
        }
    }

    private static void handleIssue() throws InterruptedException {
        String keywords = "КЛЮЧЕВЫЕ СЛОВА:";
//        String keywords = "Ключевые слова:";
        List<WebElement> annotations = driver.findElements(By.xpath("//div[span[text()[contains(.,'" + keywords + "')]]]"));
        for (WebElement annotation : annotations) {
            String text = annotation.getText();
            int i = text.indexOf(keywords);
            CharSequence anText = text.substring(0, i).trim();
            WebElement anEl = annotation.findElement(By.xpath(".//span"));
            anEl.clear();
            anEl.sendKeys(anText);
            CharSequence kwText = text.substring(i +15).trim();
            WebElement kwEl = annotation.findElement(By.xpath("..//..//div[contains(@class, 'AlmanacEntry-keywords')]//span"));
            kwEl.clear();
            kwEl.sendKeys(kwText);
        }
    }
}
