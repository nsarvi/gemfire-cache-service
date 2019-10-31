package io.pivotal.datatx.citi.cacheservice.service;

public interface GemFireRepositoryService {

	String get(String key);

	String put(String key, String value);

}
