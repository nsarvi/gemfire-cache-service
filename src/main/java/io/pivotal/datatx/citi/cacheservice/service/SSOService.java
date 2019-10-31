package io.pivotal.datatx.citi.cacheservice.service;


import com.gemstone.gemfire.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import static io.pivotal.datatx.citi.cacheservice.repository.constant.RegionName.SSO;

@Service
public class SSOService implements GemFireRepositoryService {

	@Autowired
	@Qualifier(SSO)
	private Region<String, String> ssoRegion;

	@Override
	public String get(String key) {
		return ssoRegion.get(key);
	}

	@Override
	public String put(String key, String value) {
		return  ssoRegion.put(key, value);
	}

}
