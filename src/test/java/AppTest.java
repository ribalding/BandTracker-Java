import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.sql2o.*;
import org.junit.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();


  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest(){
    goTo("http://localhost:4567");
    assertThat(pageSource()).contains("Band Tracker");
  }

  @Test
  public void addBand(){
    goTo("http://localhost:4567");
    click("a", withText ("View All Bands"));
    fill("#band_name").with("The Beatles");
    submit(".btn");
    assertThat(pageSource()).contains("The Beatles");
  }

  @Test
  public void viewBands(){
    goTo("http://localhost:4567");
    click("a", withText ("View All Bands"));
    assertThat(pageSource()).contains("Bands");
  }

  @Test
  public void viewVenues(){
    goTo("http://localhost:4567");
    click("a", withText ("View All Venues"));
    assertThat(pageSource()).contains("Venues");
  }

  @Test
  public void addVenue(){
    goTo("http://localhost:4567");
    click("a", withText ("View All Venues"));
    fill("#venue_name").with("Club Tundra");
    submit(".btn");
    assertThat(pageSource()).contains("Club Tundra");
  }

  @Test
  public void attachVenue(){
    goTo("http://localhost:4567");
    click("a", withText ("View All Venues"));
    fill("#venue_name").with("Club Tundra");
    submit(".btn");
    goTo("http://localhost:4567");
    click("a", withText ("View All Bands"));
    fill("#band_name").with("The Beatles");
    submit(".btn");
    click("a", withText ("The Beatles"));
    find("#attachVenue").click();
    submit(".btn");
    assertThat(pageSource()).contains("Club Tundra");
  }
}
