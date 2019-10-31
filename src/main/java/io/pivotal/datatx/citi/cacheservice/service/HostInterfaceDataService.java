package io.pivotal.datatx.citi.cacheservice.service;


import com.gemstone.gemfire.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static io.pivotal.datatx.citi.cacheservice.repository.constant.RegionName.HOST_INTERFACE_DATA;

@Service
public class HostInterfaceDataService implements GemFireRepositoryService {

	@Autowired
	@Qualifier(HOST_INTERFACE_DATA)
	private Region<String, String> hostInterfaceDataRegion;

	@Override
	public String get(String key) {
		return  hostInterfaceDataRegion.get(key);
	}

	@Override
	public String put(String key, String value) {
		return  hostInterfaceDataRegion.put(key, value);
	}

}
