package toll.fee.cal;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CostOfTrip {

	static String FILE_PATH = "interchanges.json";
	public static void main(String[] args) throws Exception{
		if (args.length < 3) {
			System.out.println("Must run with Start and End points!");
		}

		List<InterChange> interChangeList = CostOfTrip.loadJsonFromFile(FILE_PATH);
		int interChange1 = getInterChangeByNameFromList(args[1], interChangeList);
		int interChange2 = getInterChangeByNameFromList(args[2], interChangeList);
	
		if(interChange1 < 0 || interChange2 < 0) {
			throw new Exception("Interchange(s) not found.");
		}
		double totalDistance = 0d;
		if(interChange1 > interChange2) {
			for (int i = interChange1; i >= interChange2; i-- ) {
				Set<Adjacent> adjacents = interChangeList.get(i).getRoutes();
				Adjacent adjacent = (Adjacent)adjacents.toArray()[0];
				totalDistance += adjacent.getDistance();
			}
		} else {
			for (int i = interChange1; i <= interChange2; i++) {
				Set<Adjacent> adjacents = interChangeList.get(i).getRoutes();
				Adjacent adjacent = null;
				if(adjacents.size() > 1) {
					adjacent = (Adjacent)adjacents.toArray()[1];
				} else {
					adjacent = (Adjacent)adjacents.toArray()[0];
				}
				totalDistance += adjacent.getDistance();
			}
		}
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.HALF_UP);
		System.out.println("Distance: " + df.format(totalDistance));

		df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		System.out.println("Cost: $" + df.format(totalDistance*.25));
	}

	@SuppressWarnings("unchecked")
	public static List<InterChange> loadJsonFromFile(String filePath) throws Exception{
		JSONParser jsonParser = new JSONParser();

	    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
	    InputStreamReader reader = new InputStreamReader(is);
	    JSONObject result = (JSONObject) jsonParser.parse(reader);
	    Map<String, JSONObject> locations = (Map<String, JSONObject>) result.get("locations");
	    List<InterChange> interChangeList = new ArrayList<>();
	    locations.forEach((k, v) -> {
	    	InterChange interChange = new InterChange();
	    	interChange.setId(Integer.parseInt(k));
	    	interChange.setName((String)v.get("name"));
	    	interChange.setLat(((Number)v.get("lat")).doubleValue());
	    	interChange.setLng(((Number)v.get("lng")).doubleValue());

	    	Set<Adjacent> adjacentList = new TreeSet<>();

	    	((JSONArray)v.get("routes")).forEach(a -> {
	    		Adjacent adjacent = new Adjacent();
	    		Long adjacentId = (Long) ((JSONObject)a).get("toId");
	    		Number n = (Number) ((JSONObject)a).get("distance");
	    		Double distance = ((Number) ((JSONObject)a).get("distance")).doubleValue();
	    		adjacent.setId(adjacentId.intValue());
	    		adjacent.setDistance(distance);
	    		adjacentList.add(adjacent);
	    	});
	    	interChange.setRoutes(adjacentList);
	    	interChangeList.add(interChange);
	    });
	    
	    // We may assume the integer info in Json file is not sorted
	    Collections.sort(interChangeList);
	    
	    return interChangeList;
	}

	public static int getInterChangeByNameFromList(String name, List<InterChange> interChangeList) {
		int retVal = -1;
		if (name != null) {
			InterChange interChange = interChangeList.stream()
					  .filter(e -> name.equals(e.getName()))
					  .findAny()
					  .orElse(null);
			if(interChange !=null) {
				return interChangeList.indexOf(interChange);
			}
		}
		return retVal;
	}
	
}

