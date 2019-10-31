package io.pivotal.datatx.citi.cacheservice.config;


import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Properties;

import static io.pivotal.datatx.citi.cacheservice.repository.constant.RegionName.*;

@Configuration
@ConfigurationProperties("vcap.services.gemfire-config.credentials")
@EnableConfigurationProperties
public class GemFireConfig {

	private String locator1;
	private String port1;

	private String locator2;
	private String port2;

	private String locator3;
	private String port3;

	private String securityusername;
	private String securitypassword;
	private String securityclientauthinit;


	@Bean
	ClientRegionFactory clientRegionFactory() {

		System.out.println(" ---- > Using Locators : "+locator1 +", "+locator2 +", "+locator3 );

		Properties props = new Properties();
		ClientCacheFactory ccf = new ClientCacheFactory(props);

		ccf.set("mcast-port", "0");
		ccf.set("log-level", "trace");
		ccf.setPoolSubscriptionEnabled(true);

		if (!StringUtils.isEmpty(securityusername)) {
			ccf.set("security-username", securityusername);
			ccf.set("security-password", securitypassword);
			ccf.set("security-client-auth-init", securityclientauthinit);

		}
		ccf.addPoolLocator(locator1, Integer.valueOf(port1));
		if (!StringUtils.isEmpty(locator2)){
			ccf.addPoolLocator(locator2, Integer.valueOf(port2));
		}
		if (!StringUtils.isEmpty(locator3)){
			ccf.addPoolLocator(locator3, Integer.valueOf(port3));
		}
		ClientCache clientCache=ccf.create();
		return clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
	}

	@Bean(HOST_INTERFACE_DATA)
	Region<String, String> hostInterfaceDataRegion(
			ClientRegionFactory clientRegionFactory) {
		return clientRegionFactory.create(HOST_INTERFACE_DATA);
	}

	@Bean(SESSIONS)
	Region<String, String> sessionRegion(ClientRegionFactory clientRegionFactory) {
		return clientRegionFactory.create(SESSIONS);
	}

	@Bean(SHARED_CONTEXT)
	Region<String, String> sharedContextRegion(ClientRegionFactory clientRegionFactory) {
		return clientRegionFactory.create(SHARED_CONTEXT);
	}

	@Bean(SSO)
	Region<String, String> ssoRegion(ClientRegionFactory clientRegionFactory) {
		return clientRegionFactory.create(SSO);
	}


	public String getLocator1() {
		return locator1;
	}

	public void setLocator1(String locator1) {
		this.locator1 = locator1;
	}

	public String getPort1() {
		return port1;
	}

	public void setPort1(String port1) {
		this.port1 = port1;
	}

	public String getLocator2() {
		return locator2;
	}

	public void setLocator2(String locator2) {
		this.locator2 = locator2;
	}

	public String getPort2() {
		return port2;
	}

	public void setPort2(String port2) {
		this.port2 = port2;
	}

	public String getLocator3() {
		return locator3;
	}

	public void setLocator3(String locator3) {
		this.locator3 = locator3;
	}

	public String getPort3() {
		return port3;
	}

	public void setPort3(String port3) {
		this.port3 = port3;
	}

	public String getSecurityusername() {
		return securityusername;
	}

	public void setSecurityusername(String securityusername) {
		this.securityusername = securityusername;
	}

	public String getSecuritypassword() {
		return securitypassword;
	}

	public void setSecuritypassword(String securitypassword) {
		this.securitypassword = securitypassword;
	}

	public String getSecurityclientauthinit() {
		return securityclientauthinit;
	}

	public void setSecurityclientauthinit(String securityclientauthinit) {
		this.securityclientauthinit = securityclientauthinit;
	}
}
