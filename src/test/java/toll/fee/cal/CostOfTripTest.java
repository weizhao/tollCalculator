package toll.fee.cal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CostOfTripTest {
	static String FILE_PATH_TEST = "interchangesTest.json";

	@Test
    public void whenValidFilePathIsProvide_thenAListIsReturned() throws Exception{
		List<InterChange> interChangeList = CostOfTrip.loadJsonFromFile(FILE_PATH_TEST);
		assertNotNull(interChangeList);
    }
	
	@Test
	public void whenWrongNamePassedInToLookUpTheList_thenNullReturned() throws Exception{
	    
		List<InterChange> interChangeList = CostOfTrip.loadJsonFromFile(FILE_PATH_TEST);
		
		assertTrue(CostOfTrip.getInterChangeByNameFromList("NonExistingName", interChangeList) < 0);
		
	}
	
	@Test
	public void whenNamePassedInToLookUpTheList_thenInterChangeReturned() throws Exception{
	    
		List<InterChange> interChangeList = CostOfTrip.loadJsonFromFile(FILE_PATH_TEST);
		
		assertTrue(CostOfTrip.getInterChangeByNameFromList("Salem Road", interChangeList) >= 0);
		
	}
}
