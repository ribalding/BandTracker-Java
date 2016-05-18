import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BandTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void band_insantiatesCorrectly(){
    Band newBand = new Band("Tragedy");
    assertEquals(true, newBand instanceof Band);
  }

  @Test
  public void all_returnsAllBands(){
    Band newBand = new Band("Nothing");
    newBand.save();
    Band otherBand = new Band("Wrong");
    otherBand.save();
    assertEquals(2, Band.all().size());
  }

  @Test
  public void attachVenue_attachesVenueToBand(){
    Band newBand = new Band("Kvelertak");
    newBand.save();
    Venue newVenue = new Venue("Mohawk Place");
    newVenue.save();
    newBand.attachVenue(newVenue.getId());
    List<Venue> venueList = newBand.getVenues();
    assertEquals(1, venueList.size());
  }

  @Test
  public void update_updatesInformationCorrectly(){
    Band newBand = new Band("The Replacements");
    newBand.save();
    newBand.update("Kylesa");
    assertEquals("Kylesa", Band.find(newBand.getId()).getName());
  }

  @Test
  public void validate_returnsFalseIfPassedAnEmptyString(){
    boolean bandNameTest = Band.validate("");
    assertEquals(false, bandNameTest);
  }

  @Test
  public void containsVenue_returnsTrueIfBandContainsVenue(){
    Band newBand = new Band("The Replacements");
    newBand.save();
    Venue newVenue = new Venue("The Know");
    newVenue.save();
    newBand.attachVenue(newVenue.getId());
    assertEquals(true, newBand.containsVenue(newVenue.getId()));
  }

  @Test
  public void containsVenue_returnsFalseIfBandDoesNotContainVenue(){
    Band newBand = new Band("The Replacements");
    newBand.save();
    Venue newVenue = new Venue("The Know");
    newVenue.save();
    newBand.attachVenue(newVenue.getId());
    Venue otherVenue = new Venue("Showplace Theater");
    otherVenue.save();
    assertEquals(false, newBand.containsVenue(otherVenue.getId()));
  }

}
