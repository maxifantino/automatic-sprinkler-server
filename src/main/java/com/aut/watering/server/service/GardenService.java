package com.aut.watering.server.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aut.watering.server.builder.LocationBuilder;
import com.aut.watering.server.constants.SprinklerConstants;
import com.aut.watering.server.data.CreateGardenRequest;
import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.Location;
import com.aut.watering.server.dto.Patch;
import com.aut.watering.server.dto.User;
import com.aut.watering.server.helper.GsonHelper;

@Service
public class GardenService {

	@Autowired
	private GardenDao gardenDao;
	
	@Autowired
	private LocationDao locationDao;
		
	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	public Garden getGarden(Integer gardenId){
		return gardenDao.getGarden(gardenId);
	}

	public boolean validateGardenDelete(Garden garden, Integer userId){
		boolean result = true;
		if (garden == null || garden.getUser().getId() != userId){
			result = false;
		}
		return result;
	}

	public boolean validateCreateRequest (CreateGardenRequest request){
		boolean valid = true;
		if (StringUtils.isBlank(request.getGardenName())||
			StringUtils.isBlank(request.getCity()) ||
			StringUtils.isBlank(request.getCountry())){
			valid = false;
		}
		log.error("request: " + request.toString());
		log.error("1erValidate : " + valid);
		checkDefaultValues(request);
		if (!validateTimeWindow(request.getWateringTimeWindow())){
			valid = false;
		}
		log.error("2d0Validate : " + valid);
	
		if (!validateWeekDays(request.getWateringWorkingDays())){
			valid = false;
		}
	
		log.error("3erValidate : " + valid);
		return valid;
	}
	
	private void checkDefaultValues(CreateGardenRequest request) {
		if (StringUtils.isBlank(request.getWateringWorkingDays())) {
			request.setWateringWorkingDays(SprinklerConstants.DEFAULT_WORKING_DAYS);
		}
		if (StringUtils.isBlank(request.getWateringTimeWindow())) {
			request.setWateringTimeWindow(SprinklerConstants.DEFAULT_WORKING_HOURS);
		}
	}
	
	private boolean validateWeekDays(String weekDays){
		return StringUtils.isBlank(weekDays) || weekDays.matches("(([0-7](,)?){1,7})*");
	}
	
	private boolean validateTimeWindow (String timewindow){
		return StringUtils.isBlank(timewindow) || timewindow.matches("from:[0-9]*,\\s*to:[0-9]*");
	}
	
	public void deleteGarden(Garden garden){
		gardenDao.deleteGarden(garden);
	}	
	
	private List<Patch> buildPatchs(String json){
		GsonHelper gsonHelper = new GsonHelper();
		return gsonHelper.toClassList(json, Patch.class);
	}
	
	public Garden createGarden (CreateGardenRequest request, User user){
		Garden garden = new Garden();
		garden.setName(request.getGardenName());
		garden.setUser(user);
		garden.setWorkingDays(request.getWateringWorkingDays());
		garden.setWorkingTimeWindow(request.getWateringTimeWindow());
		LocationBuilder builder = new LocationBuilder();
		Location location = builder.withCity(request.getCity())
		.withCountry(request.getCountry())
		.withLatitude(request.getLatitude())
		.withLongitude(request.getLongitude())
		.withAddress(request.getAddress()).build();
		log.error ("Hooolis");
		locationDao.saveLocation(location);
		garden.setLocation(location);
		garden.setPatches(buildPatchs(request.getPatchList()));
		log.error("Llegue 5");
		
		gardenDao.saveGarden(garden);
		// TODO: manejar errores.
		return garden;
	}

}