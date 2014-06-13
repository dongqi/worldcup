package cn.eastseven.worldcup.service;

import java.util.Set;

import cn.eastseven.worldcup.domain.WorldCupData;

public interface WorldCupService {

	Set<WorldCupData> getAllData();
	
	WorldCupData getData(String id);
	
	boolean bet(String name, String id, String left, String middle, String right);
	
	boolean save(WorldCupData wcd);
}
