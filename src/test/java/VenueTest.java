import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class VenueTest{

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void venue_insantiatesCorrectly(){
    Venue newVenue = new Venue("The Know");
    assertEquals(true, newVenue instanceof Venue);
  }

  @Test
  public void all_returnsAllVenues(){
    Venue newVenue = new Venue("The Know");
    newVenue.save();
    Venue otherVenue = new Venue("Crystal Ballroom");
    otherVenue.save();
    assertEquals(2, Venue.all().size());
  }

  @Test
  public void find_findsCorrectVenue(){
    Venue newVenue = new Venue("The Know");
    newVenue.save();
    Venue foundVenue = Venue.find(newVenue.getId());
    assertEquals("The Know", foundVenue.getName());
  }

  @Test
  public void getBands_returnsAllBandsForVenue(){
    Venue newVenue = new Venue("The Know");
    newVenue.save();
    Band newBand = new Band("Tupac");
    newBand.save();
    newBand.attachVenue(newVenue.getId());
    List<Band> bandList = newVenue.getBands();
    assertEquals(1, bandList.size());
  }
}
