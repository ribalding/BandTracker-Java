import java.util.List;
import org.sql2o.*;

public class Band{
  private int id;
  private String band_name;
  public boolean venueBoolean;

  public Band(String name){
    band_name = name;
  }

  public String getName(){
    return band_name;
  }

  public int getId(){
    return id;
  }

  @Override
  public boolean equals(Object otherBand){
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) &&
             this.getId() == newBand.getId();
    }
  }

  public static Band find(int id){
    String sql = "SELECT * FROM bands WHERE id = :id";
    try(Connection con = DB.sql2o.open()){
      Band thisBand = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Band.class);
      return thisBand;
    }
  }

  public static List<Band> all(){
    String sql = "SELECT * FROM bands";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  public void save(){
    String sql = "INSERT INTO bands (band_name) VALUES (:band_name)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
      .addParameter("band_name", this.band_name)
      .executeUpdate()
      .getKey();
    }
  }

  public void update(String name){
    String sql = "UPDATE bands SET band_name = :band_name WHERE id = :id";
    try (Connection con = DB.sql2o.open()){
      con.createQuery(sql)
      .addParameter("band_name", name)
      .addParameter("id", this.getId())
      .executeUpdate();
    }
  }

  public void delete(){
    try (Connection con = DB.sql2o.open()){
      String deleteBandQuery = "DELETE FROM bands WHERE id = :id";
      con.createQuery(deleteBandQuery)
      .addParameter("id", this.getId())
      .executeUpdate();

      String deleteJoinQuery = "DELETE FROM bands_venues WHERE band_id = :id";
      con.createQuery(deleteJoinQuery)
      .addParameter("id", this.getId())
      .executeUpdate();
    }
  }

  public void attachVenue(int venue_id){
    String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql)
      .addParameter("band_id", this.getId())
      .addParameter("venue_id", venue_id)
      .executeUpdate();
    }
  }

  public List<Venue> getVenues(){
    String sql = "SELECT venues.* FROM bands JOIN bands_venues ON (bands.id = bands_venues.band_id) JOIN venues ON (bands_venues.venue_id = venues.id) WHERE bands.id = :id";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).addParameter("id", this.getId()).executeAndFetch(Venue.class);
    }
  }

  public boolean containsVenue(int venue_id){
    List<Venue> venueList = this.getVenues();
    for(Venue eachVenue : venueList){
      if(venue_id == eachVenue.getId()){
        venueBoolean = true;
      } else {
        venueBoolean = false;
      }
    }
    return venueBoolean;
  }

  public static Boolean validate(String newBandName){
    if(newBandName.equals("")){
      return false;
    } else {
      return true;
    }
  }
}
