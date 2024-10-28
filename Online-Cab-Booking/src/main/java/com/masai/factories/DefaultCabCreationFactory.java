package com.masai.factories;

import com.masai.entities.Cab;
import com.masai.entities.CabDriver;
import com.masai.entities.CabDriverCabDTO;

public class DefaultCabCreationFactory implements CabCreationFactory {

    @Override
    public CabDriver createCabDriver(CabDriverCabDTO cabdto) {
        Cab cab = createCab(cabdto);
        CabDriver cabDriver = new CabDriver(cabdto.getUsername(), cabdto.getPassword(), cabdto.getAddress(),
                cabdto.getMobile(), cabdto.getEmail(), cabdto.getLicenseNumber(), cab);
        cab.setCabDriver(cabDriver);
        return cabDriver;
    }

    @Override
    public Cab createCab(CabDriverCabDTO cabdto) {
        return new Cab(cabdto.getCarType(), cabdto.getNumberPlate(), cabdto.getRatePerKms());
    }
}
