import java.util.List;
import org.sql2o.*;

public class Venue{
  private int id;
  private String venue_name;

  public Venue(String name){
    venue_name = name;
  }

  public String getName(){
    return venue_name;
  }

  public int getId(){
    return id;
  }

  @Override
  public boolean equals(Object otherVenue){
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName()) &&
             this.getId() == newVenue.getId();
    }
  }

  public static Venue find(int id){
    String sql = "SELECT * FROM venues WHERE id = :id";
    try(Connection con = DB.sql2o.open()){
      Venue thisVenue = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Venue.class);
      return thisVenue;
    }
  }

  public static List<Venue> all(){
    String sql = "SELECT * FROM venues";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  public void save(){
    String sql = "INSERT INTO venues (venue_name) VALUES (:venue_name)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
      .addParameter("venue_name", this.venue_name)
      .executeUpdate()
      .getKey();
    }
  }

  public List<Band> getBands(){
    String sql = "SELECT bands.* FROM venues JOIN bands_venues ON (venues.id = bands_venues.venue_id) JOIN bands ON (bands_venues.band_id = bands.id) WHERE venues.id = :id";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).addParameter("id", this.getId()).executeAndFetch(Band.class);
    }
  }
}
