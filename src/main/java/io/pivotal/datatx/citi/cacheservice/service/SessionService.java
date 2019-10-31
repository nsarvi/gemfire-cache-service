package io.pivotal.datatx.citi.cacheservice.service;


import com.gemstone.gemfire.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static io.pivotal.datatx.citi.cacheservice.repository.constant.RegionName.SESSIONS;

@Service
public class SessionService implements GemFireRepositoryService {

	@Autowired
	@Qualifier(SESSIONS)
	private Region<String, String> sessionRegion;

	@Override
	public String get(String key) {
		return  sessionRegion.get(key);
	}

	@Override
	public String put(String key, String value) {
		return sessionRegion.put(key, value);
	}

}
