package com.aut.watering.server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aut.watering.server.data.DeleteSprinklerRequest;
import com.aut.watering.server.data.SprinklerRequest;
import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.Patch;
import com.aut.watering.server.enums.AvailableSprinklerStatus;
import com.google.gson.JsonObject;

@Service
public class SprinklerService {
	
	@Autowired
	private SprinklerDao dao;
	
	@Autowired
	private GardenDao gardenDao;
	
	@Autowired
	private SprinklerActivationService activationService;
	
	@Autowired
	private DynamicPropertiesService propertyService;
		
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Patch getSprinkler(String sprinklerCode){
		return dao.getSprinkler(sprinklerCode);
	}

	public boolean validate (SprinklerRequest request){
		boolean result = true;
		result = validateHumidityCritical(request);
		result = validateHumidityThreshold(request);
		result = validateWateringTime(request);
		return result;
	}
	
	public boolean validateHumidityCritical(SprinklerRequest request){
		return  request.getHumidityCritical() == null || (request.getHumidityCritical()< 100 && request.getHumidityCritical() > 0);
	}
	
	public boolean validateHumidityThreshold(SprinklerRequest request){
		return request.getHumidityThreshold()< 100 && request.getHumidityThreshold() > 0;
	}
	
	public boolean validateWateringTime(SprinklerRequest request){
		int maxWateringTime = propertyService.getPropertyAsInteger("max.watering.time");
		return request.getWateringSeconds() > 0 && 
				request.getWateringSeconds() < maxWateringTime;
	}
	
	public void save(SprinklerRequest request){
		Patch patch = new Patch();
		patch.setCriticalHumidity(request .getHumidityCritical());
		patch.setHumidityThreshold(request.getHumidityThreshold());
		patch.setPatchCode(request.getPatchCode());
		patch.setWateringTime(request.getWateringSeconds());
		patch.setType(request.getType());
		dao.saveSprinkler(patch);
	}
	
	public boolean validateDeleteRequest(DeleteSprinklerRequest request, int gardenId){
		boolean result = true;
		Garden garden = gardenDao.getGarden(gardenId);
		validatePatchId (garden.getPatches(), request.getPatchId());
		return result;
	}
	
	private boolean validatePatchId(List<Patch> patches, String  patchId){
		boolean found = false;
		for (Patch patch : patches){
			if ( patch.getPatchCode().equals(patchId)){
				found = true;
			}
		}
		return found;
	}
	
	public void delete (Patch sprinkler){
		dao.deleteSprinkler(sprinkler);
	}
	
	public void modify (SprinklerRequest request, Patch patch){
		
		if(request.getHumidityCritical()!= -1){
			patch.setCriticalHumidity(request.getHumidityCritical());
		}
		
		if(request.getHumidityThreshold()!= -1){
			patch.setHumidityThreshold(request.getHumidityThreshold());
		}
		
		if(request.getWateringSeconds()!= -1){
			patch.setWateringTime(request.getWateringSeconds());
		}
		dao.savePatch(patch);
	}
	
	public boolean validateModify(SprinklerRequest request){
		boolean result = false;

		if(request.getHumidityCritical()!= -1){
			result = validateHumidityCritical(request);
		}
		log.error ("Result: " + result);
		if(request.getHumidityThreshold()!= -1){
			result = validateHumidityThreshold(request);
		}
		log.error ("Result: " + result);
		if(request.getWateringSeconds()!= -1){
			result = validateWateringTime(request);
		}
		log.error ("Result: " + result);
		return result;
	}
	
	public String getSprinklerStatusResponse(AvailableSprinklerStatus sprinklerStatus){
		JsonObject response = new JsonObject();
		response.addProperty("response_code", sprinklerStatus.getId());
		response.addProperty("response_status", sprinklerStatus.toString());
		return response.getAsString();
	}
	
	public boolean shouldActivateSprinkler (Patch sprinkler, float currentHumidity, Integer nextCheck){
		return activationService.checkActivation(sprinkler, currentHumidity, nextCheck);
	}
}
