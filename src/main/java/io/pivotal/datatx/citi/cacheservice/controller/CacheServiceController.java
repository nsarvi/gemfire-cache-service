package io.pivotal.datatx.citi.cacheservice.controller;

import io.pivotal.datatx.citi.cacheservice.exception.NotFoundStatusException;
import io.pivotal.datatx.citi.cacheservice.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.pivotal.datatx.citi.cacheservice.service.HostInterfaceDataService;
import static io.pivotal.datatx.citi.cacheservice.repository.constant.RegionName.*;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@AllArgsConstructor
@RestController("/cache")
public class CacheServiceController {

	@Autowired
	private  HostInterfaceDataService hostInterfaceDataService;

	@Autowired
	private  SessionService sessionService;

	@Autowired
	private  SharedContextService sharedContextService;

	@Autowired
	private  SSOService ssoService;


	@GetMapping
	@ResponseStatus(OK)
	public String read(@RequestHeader("region") String regionName,
			@RequestHeader("key") String key) {
		GemFireRepositoryService gemfireRepositoryService = getGemFireRepositoryService(
				regionName);
		return gemfireRepositoryService.get(key);

	}

	@PutMapping
	@ResponseStatus(OK)
	public String update(@RequestHeader("region") String regionName,
			@RequestHeader("key") String key, @RequestBody String requestBody) {
		GemFireRepositoryService gemfireRepositoryService = getGemFireRepositoryService(
				regionName);
		return gemfireRepositoryService.put(key, requestBody);
	}

	private GemFireRepositoryService getGemFireRepositoryService(String regionName) {
		switch (regionName) {
		case HOST_INTERFACE_DATA:
			return hostInterfaceDataService;
		case SESSIONS:
			return sessionService;
		case SHARED_CONTEXT:
			return sharedContextService;
		case SSO:
			return ssoService;
		default:
			throw new RuntimeException(regionName + " not found!!");
		}
	}

}
