package cn.eastseven.worldcup.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.eastseven.worldcup.domain.WorldCupData;
import cn.eastseven.worldcup.domain.WorldCupDataUtils;
import cn.eastseven.worldcup.service.WorldCupService;
import cn.eastseven.worldcup.service.WorldCupServiceImpl;

import com.alibaba.fastjson.JSON;

public class WorldCupServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private WorldCupService worldCupService = null;
	
    public WorldCupServlet() {
        super();
        worldCupService = new WorldCupServiceImpl();
    }

    @Override
    public void init() throws ServletException {
    	worldCupService.load();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		if("list".equalsIgnoreCase(m)) {
			List<WorldCupData> list = WorldCupDataUtils.getMatchList();
			String json = JSON.toJSONString(list);
			System.out.println(json);
			pw.print(json);
			
		} else if("mylist".equalsIgnoreCase(m)) {
			String name = request.getParameter("name");
			List<Map<String, String>> list = worldCupService.getMyList(name);
			String json = JSON.toJSONString(list);
			System.out.println(json);
			pw.print(json);
		}
		
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String m = request.getParameter("m");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		
		if("bet".equals(m)) {
			String name = request.getParameter("name");
			String id = request.getParameter("id");
			String L = request.getParameter("L");
			String M = request.getParameter("M");
			String R = request.getParameter("R");
			
			boolean bln = worldCupService.bet(name, id, L, M, R);
			pw.print("{ \"success\" : "+bln+" }");
			
		} else if("save".equalsIgnoreCase(m)) {
			String id = request.getParameter("id");
			String L = request.getParameter("L");
			String M = request.getParameter("M");
			String R = request.getParameter("R");
			WorldCupData wcd = worldCupService.getData(id);
			
			wcd.setResultLeft(L);
			wcd.setResultMiddle(M);
			wcd.setResultRight(R);
			
			boolean isSave = worldCupService.save(wcd);
			if(isSave) {
				WorldCupDataUtils.getMatches().add(wcd);
			}
			
			System.out.println(" save : " + wcd);
			pw.print("{ \"success\" : "+isSave+" }");
		}
		
		pw.close();
	}

	
}
