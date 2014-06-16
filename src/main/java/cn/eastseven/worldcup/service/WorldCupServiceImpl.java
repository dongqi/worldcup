package cn.eastseven.worldcup.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import cn.eastseven.worldcup.domain.WorldCupData;
import cn.eastseven.worldcup.domain.WorldCupDataUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class WorldCupServiceImpl implements WorldCupService {

	private JedisPool pool = null;
	
	private ExecutorService executorService;
	
	public WorldCupServiceImpl() {
		pool = new JedisPool(new JedisPoolConfig(), "localhost");
		executorService = Executors.newFixedThreadPool(100);
	}
	
	public Set<WorldCupData> getAllData() {
		Set<WorldCupData> all = Sets.newHashSet();
		Jedis jedis = pool.getResource();
		for (int index = 0; index < WorldCupDataUtils.NUM; index++) {
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
	
	public List<WorldCupData> getList(Set<WorldCupData> set) {
		List<WorldCupData> list = Lists.newArrayList();
		if(!set.isEmpty()) {
			Object[] array = set.toArray();
			for (Object obj : array) {
				list.add((WorldCupData)obj);
			}
			
			Collections.sort(list, new Comparator<WorldCupData>() {
				public int compare(WorldCupData o1, WorldCupData o2) {
					return Integer.valueOf(o1.getId()) - Integer.valueOf(o2.getId());
				}
				
			});
		}
		
		return list;
	}
	
	public WorldCupData getData(String id) {
		WorldCupData data = null;
		for(Iterator<WorldCupData> iter = WorldCupDataUtils.getMatchList().iterator(); iter.hasNext();) {
			WorldCupData wcd = iter.next();
			if(wcd.getId().equals(id)) {
				data = wcd;
				break;
			}
		}
		
		return data;
	}
	
	public boolean bet(final String name, final String id, final String left, final String middle, final String right) {
		final Date current = Calendar.getInstance().getTime();
		WorldCupData wc = WorldCupDataUtils.getMatchList().get(Integer.valueOf(id));
		if(current.getTime() > wc.getTimes()) {
			return false;
		}
		
		executorService.execute(new Runnable() {
			
			public void run() {
				Jedis jedis = pool.getResource();
				
				String key = name + ":" + id;
				Map<String, String> hash = Maps.newHashMap();
				hash.put("l", left);
				hash.put("m", middle);
				hash.put("r", right);
				hash.put("time", WorldCupData.sdf.format(current));
				String result = jedis.hmset(key, hash);
				jedis.close();
				System.out.println("key="+key+", id="+id+", l="+left+", m="+middle+", r="+right+", result="+result);
			}
		});
		
		return true;//"OK".equalsIgnoreCase(result);
	}
	
	public boolean save(final WorldCupData wc) {
		boolean success = true;
//		Date current = Calendar.getInstance().getTime();
//		if(current.getTime() > wc.getTimes()) {
//			return false;
//		}
		
		executorService.execute(new Runnable() {
			
			public void run() {
				Jedis jedis = pool.getResource();
				
				Map<String, String> map = Maps.newHashMap();
				map.put(WorldCupData.F.resultLeft.toString(),   wc.getResultLeft());
				map.put(WorldCupData.F.resultMiddle.toString(), wc.getResultMiddle());
				map.put(WorldCupData.F.resultRight.toString(),  wc.getResultRight());
				
				String result = jedis.hmset(wc.getKey(), map);
				boolean success = "OK".equalsIgnoreCase(result);
				if(success) {
					load();
				}
				
				jedis.close();
			}
		});
		
		return success;
	}
	
	public void load() {
		if(!WorldCupDataUtils.getMatchList().isEmpty()) {
			WorldCupDataUtils.getMatchList().clear();
		}
		
		List<WorldCupData> list = getList(getAllData());
    	boolean bln = WorldCupDataUtils.getMatchList().addAll(list);
    	System.out.println("load worldcupdata from redis to list : "+bln+", "+list.size());
	}
	
	public List<Map<String, String>> getMyList(String name) {
		Jedis jedis = pool.getResource();
		List<Map<String, String>> list = Lists.newArrayList();
		
		for(WorldCupData wc : WorldCupDataUtils.getMatchList()) {
			String key = name + ":" + wc.getId();
			List<String> result = jedis.hmget(key, "l", "m", "r", "time");
			System.out.println("getMylist : key="+key+", result="+Arrays.toString(list.toArray()));
			Map<String, String> map = Maps.newHashMap();
			map.put("id", wc.getId());
			map.put("l", result.get(0));
			map.put("m", result.get(1));
			map.put("r", result.get(2));
			map.put("time", result.get(3));
			list.add(map);
		}
		
		jedis.close();
		return list;
	}
	
	WorldCupData load(String id) {
		WorldCupData wcd = null;
		Jedis jedis = pool.getResource();
		String key = WorldCupData.key+":"+id;
		List<String> list = jedis.hmget(key, WorldCupData.getFields());
		String teamLeft = list.get(1);
		String teamRight = list.get(2);
		String startTime = list.get(3);
		wcd = new WorldCupData(id, teamLeft, teamRight, startTime);
		wcd.setResultLeft(list.get(6));
		wcd.setResultMiddle(list.get(7));
		wcd.setResultRight(list.get(8));
		jedis.close();
		return wcd;
	}
}
