import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;

class Liker
{
    private String login;
    private String password;
    private ChromeDriver browser;

    private String button_login = "//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[4]/button";
    private String first_pic = "//*[@id=\"react-root\"]/section/main/div/div[3]/article/div/div/div[1]/div[1]";
    private String heart_button = "/html/body/div[4]/div[2]/div/article/div[2]/section[1]/span[1]/button";

    Liker(String login, String password)
    {
        this.login = login;
        this.password = password;
        logger = Logger.getLogger(Liker.class.getName());
    }

    void start(String profile)
    {
        System.out.println("Starting...");
        browser = new ChromeDriver();
        logIn();
        waiting();
        if (findPhoto(profile))
        {
            like();
            while (nextPic())
            {
                like();
            }
        }
        end();
    }

    private void logIn()
    {
        browser.get("https://instagram.com/accounts/login/");
        waiting();
        browser.findElement(new By.ByName("username")).sendKeys(login);
        browser.findElement(new By.ByName("password")).sendKeys(password);
        waiting();
        browser.findElement(new By.ByXPath(button_login)).click();
        waiting();
    }

    public boolean findPhoto(String profile)
    {
        browser.get(String.format("https://www.instagram.com/%s", profile));
        System.out.println("Giving hearts for " + profile);
        try
        {
            browser.findElement(new By.ByXPath(first_pic)).click();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return false;
        }
    }

    private boolean nextPic()
    {
        try
        {
            browser.findElement(new By.ByClassName("coreSpriteRightPaginationArrow")).click();
            waiting();
            return true;
        }
        catch (NoSuchElementException e)
        {
            System.out.println("Pictures are gone");
            return false;
        }
    }

    private void like()
    {
        try
        {
            waiting();
            browser.findElementByXPath("//button/span[@class='glyphsSpriteHeart__filled__24__red_5 u-__7' and @aria-label='Unlike']");
            System.out.println("Already liked");
        }
        catch (NoSuchElementException e)
        {
            browser.findElement(new By.ByXPath(heart_button)).click();
            System.out.println("Done");
            waiting();
        }
    }

    private void end()
    {
        browser.close();
    }

    private void waiting()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}

public class Main
{
    public static void main(String[] argc)
    {
        System.out.println("Hi");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\WINDOWS 10\\Desktop\\chromedriver.exe");
        Liker liker = new Liker("YOUR_LOGIN", "YOUR_PASSWORD");
        liker.start("USERNAME_WHICH_BOT_WILL_LIKE");
    }
}
