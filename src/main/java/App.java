import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.ArrayList;
import static spark.Spark.*;
import java.util.Arrays;
import java.util.List;

public class App{
  public static void main(String[] args){
    String layout = "templates/layout.vtl";
    staticFileLocation("/public");

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bandList", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addBand", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String band_name = request.queryParams("band_name");
      boolean band_name_validate = Band.validate(band_name);
      if (band_name_validate == true){
        Band newBand = new Band(band_name);
        newBand.save();
      }
      model.put("template", "templates/index.vtl");
      response.redirect("/bands");
      return null;
    });

    get("/band/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params("id")));
      List<Venue> attachedVenues = newBand.getVenues();
      model.put("attachedVenues", attachedVenues);
      model.put("venueList", Venue.all());
      model.put("band", newBand);
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/band/:id/attachVenue", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params("id")));
      String[] newVenuesArray = request.queryParamsValues("attachVenue");
      for(String venue : newVenuesArray){
        int parsedVenueId = Integer.parseInt(venue);
        if(newBand.containsVenue(parsedVenueId) == false){
          newBand.attachVenue(parsedVenueId);
        }
      }
      response.redirect("/band/" + newBand.getId());
      return null;
    });

    get("/band/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params("id")));
      List<Venue> attachedVenues = newBand.getVenues();
      boolean confirmEditName = true;
      model.put("confirmEditName", confirmEditName);
      model.put("attachedVenues", attachedVenues);
      model.put("venueList", Venue.all());
      model.put("band", newBand);
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/band/:id/submitUpdate", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params("id")));
      String newName = request.queryParams("updateName");
      newBand.update(newName);
      response.redirect("/band/" + newBand.getId());
      return null;
    });

    get("/band/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band newBand = Band.find(Integer.parseInt(request.params("id")));
      newBand.delete();
      response.redirect("/bands");
      return null;
    });

    get("/venues", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("venueList", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addVenue", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String venue_name = request.queryParams("venue_name");
      Venue newVenue = new Venue(venue_name);
      newVenue.save();
      model.put("template", "templates/index.vtl");
      response.redirect("/venues");
      return null;
    });

    get("/venue/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue newVenue = Venue.find(Integer.parseInt(request.params("id")));
      List<Band> bandList = newVenue.getBands();
      model.put("bandList", bandList);
      model.put("venue", newVenue);
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
