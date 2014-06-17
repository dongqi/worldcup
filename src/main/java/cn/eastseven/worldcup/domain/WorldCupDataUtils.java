package cn.eastseven.worldcup.domain;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class WorldCupDataUtils {

	private static Set<WorldCupData> matches = Sets.newConcurrentHashSet();
	private static List<WorldCupData> matches4redis = Lists.newCopyOnWriteArrayList();
	
	public static Set<WorldCupData> getMatches() {
		return matches;
	}

	public static List<WorldCupData> getMatchList() {
		return matches4redis;
	}
	
	static {
		//loadData();
	}

	public static final int NUM = 48;
	
	private static void loadData() {
		String[][] data = { 
				{ "0", "2014年06月13日 星期五", "04:00", "巴西", "克罗地亚" },
				{ "1", "2014年06月14日 星期六", "00:00", "墨西哥", "喀麦隆" },
				{ "2", "2014年06月14日 星期六", "03:00", "西班牙", "荷兰" },
				{ "3", "2014年06月14日 星期六", "06:00", "智利", "澳大利亚" },
				{ "4", "2014年06月15日 星期日", "00:00", "哥伦比亚", "希腊" },
				{ "5", "2014年06月15日 星期日", "03:00", "乌拉圭", "哥斯达黎加" },
				{ "6", "2014年06月15日 星期日", "06:00", "英格兰", "意大利" },
				{ "7", "2014年06月15日 星期日", "09:00", "科特迪瓦", "日本" },
				{ "8", "2014年06月16日 星期一", "00:00", "瑞士", "厄瓜多尔" },
				{ "9", "2014年06月16日 星期一", "03:00", "法国", "洪都拉斯" },
				{ "10", "2014年06月16日 星期一", "06:00", "阿根廷", "波黑" },
				{ "11", "2014年06月17日 星期二", "00:00", "德国", "葡萄牙" },
				{ "12", "2014年06月17日 星期二", "03:00", "伊朗", "尼日利亚" },
				{ "13", "2014年06月17日 星期二", "06:00", "加纳", "美国" },
				{ "14", "2014年06月18日 星期三", "00:00", "比利时", "阿尔及利亚" },
				{ "15", "2014年06月18日 星期三", "03:00", "巴西", "墨西哥" },
				{ "16", "2014年06月18日 星期三", "06:00", "俄罗斯", "韩国" },
				{ "17", "2014年06月19日 星期四", "00:00", "澳大利亚", "荷兰" },
				{ "18", "2014年06月19日 星期四", "03:00", "西班牙", "智利" },
				{ "19", "2014年06月19日 星期四", "06:00", "喀麦隆", "克罗地亚" },
				{ "20", "2014年06月20日 星期五", "00:00", "哥伦比亚", "科特迪瓦" },
				{ "21", "2014年06月20日 星期五", "03:00", "乌拉圭", "英格兰" },
				{ "22", "2014年06月20日 星期五", "06:00", "日本", "希腊" },
				{ "23", "2014年06月21日 星期六", "00:00", "意大利", "哥斯达黎加" },
				{ "24", "2014年06月21日 星期六", "03:00", "瑞士", "法国" },
				{ "25", "2014年06月21日 星期六", "06:00", "洪都拉斯", "厄瓜多尔" },
				{ "26", "2014年06月22日 星期日", "00:00", "阿根廷", "伊朗" },
				{ "27", "2014年06月22日 星期日", "03:00", "德国", "加纳" },
				{ "28", "2014年06月22日 星期日", "06:00", "尼日利亚", "波黑" },
				{ "29", "2014年06月23日 星期一", "00:00", "比利时", "俄罗斯" },
				{ "30", "2014年06月23日 星期一", "03:00", "韩国", "阿尔及利亚" },
				{ "31", "2014年06月23日 星期一", "06:00", "美国", "葡萄牙" },
				{ "32", "2014年06月24日 星期二", "00:00", "澳大利亚", "西班牙" },
				{ "33", "2014年06月24日 星期二", "00:00", "荷兰", "智利" },
				{ "34", "2014年06月24日 星期二", "04:00", "喀麦隆", "巴西" },
				{ "35", "2014年06月24日 星期二", "04:00", "克罗地亚", "墨西哥" },
				{ "36", "2014年06月25日 星期三", "00:00", "意大利", "乌拉圭" },
				{ "37", "2014年06月25日 星期三", "00:00", "哥斯达黎加", "英格兰" },
				{ "38", "2014年06月25日 星期三", "04:00", "日本", "哥伦比亚" },
				{ "39", "2014年06月25日 星期三", "04:00", "希腊", "科特迪瓦" },
				{ "40", "2014年06月26日 星期四", "00:00", "尼日利亚", "阿根廷" },
				{ "41", "2014年06月26日 星期四", "00:00", "波黑", "伊朗" },
				{ "42", "2014年06月26日 星期四", "04:00", "洪都拉斯", "瑞士" },
				{ "43", "2014年06月26日 星期四", "04:00", "厄瓜多尔", "法国" },
				{ "44", "2014年06月27日 星期五", "00:00", "美国", "德国" },
				{ "45", "2014年06月27日 星期五", "00:00", "葡萄牙", "加纳" },
				{ "46", "2014年06月27日 星期五", "04:00", "韩国", "比利时" },
				{ "47", "2014年06月27日 星期五", "04:00", "阿尔及利亚", "俄罗斯" }
				/*
				,
				{ "48", "2014年06月29日 星期日", "00:00", "1A", "2B" },
				{ "49", "2014年06月29日 星期日", "04:00", "1C", "2D" },
				{ "50", "2014年06月30日 星期一", "00:00", "1B", "2A" },
				{ "51", "2014年06月30日 星期一", "04:00", "1D", "2C" },
				{ "52", "2014年07月01日 星期二", "00:00", "1E", "2F" },
				{ "53", "2014年07月01日 星期二", "04:00", "1G", "2H" },
				{ "54", "2014年07月02日 星期三", "00:00", "1F", "2E" },
				{ "55", "2014年07月02日 星期三", "04:00", "1H", "2G" },
				{ "56", "2014年07月05日 星期六", "00:00", "W53", "W54" },
				{ "57", "2014年07月05日 星期六", "04:00", "W49", "W50" },
				{ "58", "2014年07月06日 星期日", "00:00", "W55", "W56" },
				{ "59", "2014年07月06日 星期日", "04:00", "W51", "W52" },
				{ "60", "2014年07月09日 星期三", "04:00", "W57", "W58" },
				{ "61", "2014年07月10日 星期四", "04:00", "W59", "W60" },
				{ "62", "2014年07月13日 星期日", "04:00", "L61", "L62" },
				{ "63", "2014年07月14日 星期一", "03:00", "W61", "W62" }
				*/
				 };

		for (int index = 0; index < data.length; index++) {
			String[] item = data[index];
			String date = item[1].split(" ")[0].trim();
			String id = item[0], startTime = date + " " + item[2], teamLeft = item[3], teamRight = item[4];
			matches.add(new WorldCupData(id, teamLeft, teamRight, startTime));
		}
	}

	public static void cleanData() {
		matches.clear();
		
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
		Jedis jedis = pool.getResource();
		
		for (Iterator<WorldCupData> iter = WorldCupDataUtils.matches.iterator(); iter.hasNext();) {
			WorldCupData wc = iter.next();
			long result = jedis.hdel(wc.getKey(), WorldCupData.getFields());
			System.out.println(wc.getKey() + " delete " + result);
		}
		
		jedis.close();
	}

	private static void import2redis() {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
		Jedis jedis = pool.getResource();
		for (Iterator<WorldCupData> iter = WorldCupDataUtils.matches.iterator(); iter.hasNext();) {
			WorldCupData wc = iter.next();
			System.out.println(wc.getKey());

			Map<String, String> map = Maps.newHashMap();
			map.put(WorldCupData.F.id.toString(),             wc.getId());
			map.put(WorldCupData.F.resultLeft.toString(),     wc.getResultLeft());
			map.put(WorldCupData.F.resultMiddle.toString(),   wc.getResultMiddle());
			map.put(WorldCupData.F.resultRight.toString(),    wc.getResultRight());
			map.put(WorldCupData.F.startTime.toString(),      wc.getStartTime());
			map.put(WorldCupData.F.teamLeft.toString(),       wc.getTeamLeft());
			map.put(WorldCupData.F.teamLeftGoals.toString(),  wc.getTeamLeftGoals());
			map.put(WorldCupData.F.teamRight.toString(),      wc.getTeamRight());
			map.put(WorldCupData.F.teamRightGoals.toString(), wc.getTeamRightGoals());
			String result = jedis.hmset(wc.getKey(), map);

			System.out.println("ret=" + result);
		}

		for (int index = 0; index < matches.size(); index++) {
			String key = WorldCupData.key + index;
			String value = jedis.hget(key, "id");
			System.out.println("redis data: key=" + key + ", val=" + value);
		}

		jedis.close();
	}

	private static void fetchDataFromUrl() throws IOException, MalformedURLException {
		final String url = "http://2014.sina.com.cn/fixtures/timeline.html";
		Document doc = Jsoup.parse(new URL(url), 10000);

		Elements table = doc.select(".tb_01");
		// System.out.println(table);

		Elements trs = table.select("tr");
		// System.out.println(trs);
		int index = 0;
		for (Iterator<Element> iter = trs.iterator(); iter.hasNext();) {
			Element tr = iter.next();
			if (tr.child(0).hasAttr("scope"))
				continue;

			// System.out.println(tr);
			String date = tr.child(0).text().trim();
			String time = tr.child(1).text();
			String teamL = "", teamR = "";
			Elements teams = tr.child(2).select("span");
			if (teams.get(0).select("a").isEmpty()) {
				teamL = teams.get(0).text();
				teamR = teams.get(2).text();
			} else {
				teamL = teams.get(0).select("a").text();
				teamR = teams.get(2).select("a").text();
			}

			System.out.println("{\"" + index + "\",\"" + date + "\",\"" + time
					+ "\",\"" + teamL + "\",\"" + teamR + "\"},");
			index++;
		}
	}
	
	public static void main(String[] args) throws Exception {
		// fetchDataFromUrl();
		//cleanData();
		//loadData();
		//import2redis();
		
		
	}
}
