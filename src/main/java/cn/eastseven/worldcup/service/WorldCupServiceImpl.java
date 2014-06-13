package cn.eastseven.worldcup.service;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import cn.eastseven.worldcup.domain.WorldCupData;
import cn.eastseven.worldcup.domain.WorldCupDataUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class WorldCupServiceImpl implements WorldCupService {

	private JedisPool pool = null;
	
	public WorldCupServiceImpl() {
		pool = new JedisPool(new JedisPoolConfig(), "localhost");
	}
	
	public Set<WorldCupData> getAllData() {
		Set<WorldCupData> all = Sets.newHashSet();
		Jedis jedis = pool.getResource();
		for (int index = 0; index < WorldCupDataUtils.getMatches().size(); index++) {
			String key = WorldCupData.key + index;
			List<String> v = jedis.hmget(key, WorldCupData.getFields());
			WorldCupData wcd = new WorldCupData(v.get(0), v.get(1), v.get(2), v.get(3));
			wcd.setTeamLeftGoals(v.get(4));
			wcd.setTeamRightGoals(v.get(5));
			wcd.setResultLeft(v.get(6));
			wcd.setResultMiddle(v.get(7));
			wcd.setResultRight(v.get(8));
			
			all.add(wcd);
		}
		return all;
	}
	
	public WorldCupData getData(String id) {
		WorldCupData data = null;
		for(Iterator<WorldCupData> iter = WorldCupDataUtils.getMatches().iterator(); iter.hasNext();) {
			WorldCupData wcd = iter.next();
			if(wcd.getId().equals(id)) {
				data = wcd;
				break;
			}
		}
		
		return data;
	}
	
	public boolean bet(String name, String id, String left, String middle, String right) {
		Jedis jedis = pool.getResource();
		String key = name + ":" + id;
		Map<String, String> hash = Maps.newHashMap();
		hash.put("l", left);
		hash.put("m", middle);
		hash.put("r", right);
		hash.put("time", WorldCupData.sdf.format(Calendar.getInstance().getTime()));
		System.out.println("name="+name+", id="+id+", l="+left+", m="+middle+", r="+right);
		String result = jedis.hmset(key, hash);
		jedis.close();
		return "OK".equalsIgnoreCase(result);
	}
	
	public boolean save(WorldCupData wc) {
		Jedis jedis = pool.getResource();
		
		Map<String, String> map = Maps.newHashMap();
		map.put(WorldCupData.F.resultLeft.toString(),   wc.getResultLeft());
		map.put(WorldCupData.F.resultMiddle.toString(), wc.getResultMiddle());
		map.put(WorldCupData.F.resultRight.toString(),  wc.getResultRight());
		String result = jedis.hmset(wc.getKey(), map);
		jedis.close();
		return "OK".equalsIgnoreCase(result);
	}
}
