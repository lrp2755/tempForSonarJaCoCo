package com.masai.factories;

import com.masai.entities.Cab;
import com.masai.entities.CabDriver;
import com.masai.entities.CabDriverCabDTO;

public interface CabCreationFactory {
    CabDriver createCabDriver(CabDriverCabDTO cabdto);

    Cab createCab(CabDriverCabDTO cabdto);
}
