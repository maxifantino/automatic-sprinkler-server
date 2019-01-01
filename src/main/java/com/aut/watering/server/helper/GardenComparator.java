package com.aut.watering.server.helper;

import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.Location;

public class GardenComparator{
	
	public static boolean compare (Garden gardenA, Garden gardenB) {
		boolean result = true;
		
		if ((gardenA.getName() != null && !gardenA.getName().equals(gardenB.getName())) || (gardenA.getName() == null && gardenB.getName() != null)){
			result = false;
		}
		
		if ((gardenA.getUser().getId() != null && !gardenA.getUser().getId().equals(gardenB.getUser().getId())) ||( gardenA.getUser().getId() == null && gardenB.getUser().getId() != null)){
			result = false;
		}

		if ((gardenA.getWorkingDays() != null && !gardenA.getWorkingDays().equals(gardenB.getWorkingDays())) || (gardenA.getWorkingDays() == null && gardenB.getWorkingDays() == null)){
			result = false;
		}

		if ((gardenA.getWorkingTimeWindow() != null && !gardenA.getWorkingTimeWindow().equals(gardenB.getWorkingTimeWindow())) || (gardenA.getWorkingTimeWindow() == null && gardenB.getWorkingTimeWindow() != null)){
			result = false;
		}
		if ((gardenA.getPatches() != null && gardenA.getPatches().equals(gardenB.getPatches())) || (gardenA.getPatches() == null && gardenB.getPatches() != null)){
			result = false;
		}
		return result && compareLocation(gardenA.getLocation(), gardenB.getLocation());
	}

	private static boolean compareLocation (Location locA, Location locB) {
		boolean result = true;
		if((locA !=null && locB == null) || (locA ==null && locB != null)) {
			return false;
		}
		if (locA == null && locB == null) {
			return true;
		}
		if ((locA.getAddress() != null && !locA.getAddress().equals(locB.getAddress())) || locA.getAddress() == null) {
			result = false;
		}
		if ((locA.getCity() != null && !locA.getCity().equals(locB.getCity())) || locA.getCity() == null) {
			result = false;
		}
		if ((locA.getLatitude() != null && !locA.getLatitude().equals(locB.getLatitude())) || locA.getLatitude() == null) {
			result = false;
		}
		if ((locA.getLongitude() != null && !locA.getLongitude().equals(locB.getLongitude())) || locA.getLongitude() == null) {
			result = false;
		}		
		return result;
	}
}