package io.pivotal.datatx.citi.cacheservice.service;

import com.gemstone.gemfire.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static io.pivotal.datatx.citi.cacheservice.repository.constant.RegionName.SHARED_CONTEXT;

@Service
public class SharedContextService implements GemFireRepositoryService {

	@Autowired
	@Qualifier(SHARED_CONTEXT)
	private Region<String, String> sharedContextRegion;

	@Override
	public String get(String key) {
		return sharedContextRegion.get(key);
	}

	@Override
	public String put(String key, String value) {
		return sharedContextRegion.put(key, value);
	}

}
