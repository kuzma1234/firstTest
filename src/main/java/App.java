import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.NoSuchElementException;
import java.util.function.Function;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class App
{
    static WebDriver driver = new ChromeDriver();
    public static void main(String[] args) throws InterruptedException
    {
        try {
            driver.get("https://www.airportparkingreservations.com/");
            driver.findElement(By.id("blended_searchbox")).findElement(By.name("airport")).sendKeys("LAX");
            //Thread.sleep(5000);
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);
            wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.id("blended_searchbox")).findElement(By.className("name"));
                }
            });
            List<String> names = driver.findElement(By.id("blended_searchbox")).findElements(By.className("name"))
                    .stream().map(WebElement::getText).collect(Collectors.toList());

            if (!names.contains("Los Angeles (LAX)")) {
                throw new NoSuchElementException("Don't work");
            }
        } finally {
            driver.quit();
        }
    }
}