package airTravel;

import airTravel.strategies.IDataStrategy;
import lombok.Data;

import java.util.List;

@Data
public class DataSystem {
    private IDataStrategy iDataStrategy;

    public List<AirTravel> getAirTravels(){
        return iDataStrategy.getAirTravelsList();
    }
}
