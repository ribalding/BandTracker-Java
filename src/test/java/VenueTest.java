import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

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
}
