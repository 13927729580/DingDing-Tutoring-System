package edu.gzhu.its.qa.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import edu.gzhu.its.qa.model.Entity;
import edu.gzhu.its.qa.model.QaIntent;
import edu.gzhu.its.qa.service.ITDBReadService;
import edu.gzhu.its.qa.utils.QaUtils;
import edu.gzhu.its.system.entity.User;

/**
 * 前端控制器
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/qa")
public class QaController {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpSession session;

	@Autowired
	private ITDBReadService tdbReadService;

	/**
	 * 增加意图
	 * 
	 * @return
	 */
	@GetMapping("/addIntent")
	public String addIntent() {
		return "qa/addIntent";
	}

	@GetMapping({ "", "/", "/index" })
	public String index() {

		return "qa/index";
	}

	/**
	 * key - value
	 * 
	 * @return
	 */
	@GetMapping("/init")
	@ResponseBody
	public Map<String, Object> init() {

		User user = (User) session.getAttribute("currentUser");
		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);
		map.put("msg", "");

		// 定义我的信息
		JSONObject mine = new JSONObject();
		mine.put("username", user.getUsername());
		mine.put("id", String.valueOf(user.getId()));
		mine.put("status", "online");
		mine.put("sign", "论码农的修养");
		mine.put("avatar", "/static/its/images/boy.png");

		JSONObject data = new JSONObject();
		data.put("mine", mine);

		// 定义好友信息
		// 好友列表
		/*
		 * ,"friend": [{ "groupname": "前端码屌" //好友分组名 ,"id": 1 //分组ID ,"list": [{
		 * //分组下的好友列表 "username": "贤心" //好友昵称 ,"id": "100001" //好友ID ,"avatar":
		 * "a.jpg" //好友头像 ,"sign": "这些都是测试数据，实际使用请严格按照该格式返回" //好友签名 ,"status":
		 * "online" //若值为offline代表离线，online或者不填为在线 }, …… ] }, …… ]
		 */

		JSONArray array = new JSONArray();

		JSONObject friend1 = new JSONObject();
		friend1.put("username", "公路病害录入");
		friend1.put("id", "10000");
		friend1.put("status", "online");
		friend1.put("sign", "自然语音交互");
		friend1.put("avatar", "/cultrue/images/firends_Morcha_01.png");

		JSONObject friend2 = new JSONObject();
		friend2.put("username", "诗词助手");
		friend2.put("id", "10001");
		friend2.put("status", "online");
		friend2.put("sign", "俺认识71894首古诗！");
		friend2.put("avatar", "/cultrue/images/firends_Morcha_02.png");

		array.add(friend1);
		array.add(friend2);

		JSONObject friend = new JSONObject();
		friend.put("groupname", "中国传统文化");
		friend.put("id", "2000");
		friend.put("list", array);

		JSONArray arrayFriend = new JSONArray();
		arrayFriend.add(friend);

		data.put("friend", arrayFriend);

		map.put("data", data);

		/*
		 * ,"data": {
		 * 
		 * //我的信息 "mine": { "username": "纸飞机" //我的昵称 ,"id": "100000" //我的ID
		 * ,"status": "online" //在线状态 online：在线、hide：隐身 ,"sign":
		 * "在深邃的编码世界，做一枚轻盈的纸飞机" //我的签名 ,"avatar": "a.jpg" //我的头像 }
		 */

		return map;

	}

	@GetMapping("/answer")
	@ResponseBody
	public Map<String, Object> answer(String toId, String sentId, String content)
			throws ClientProtocolException, IOException {
		String subject = "";
		String predicate = "";
		String result = "";
		Map<String, Object> map = new HashMap<>();
		// 处理汉字
		if (toId.equals("10000")) {

			QaIntent intent = QaUtils.getResult(content);

			String intentInfo = intent.getIntent();

			String filePath = "E:\\Intelligent Tutoring System\\Intelligent-Tutoring-System\\src\\main\\resources\\tdb";
			Dataset dataset = TDBFactory.createDataset(filePath);
			Model model = dataset.getDefaultModel();
			List<Entity> entities = intent.getEntities();
			String queryInfo = "";

			switch (intentInfo) {
			case "greet":
				int hellosrandom = (int) (Math.random() * hellos.length);
				map.put("content", hellos[hellosrandom]);
				map.put("timestamp", new Date().getTime());
				break;
			case "goodbye":
				int byesrandom = (int) (Math.random() * byes.length);
				map.put("content", byes[byesrandom]);
				map.put("timestamp", new Date().getTime());
				break;
			}
			
		}
		// 诗词
		else if (toId.equals("10001")) {
			QaIntent intent = QaUtils.getResult(content);

			String intentInfo = intent.getIntent();

			String filePath = "E:\\Intelligent Tutoring System\\Intelligent-Tutoring-System\\src\\main\\resources\\tdb";
			Dataset dataset = TDBFactory.createDataset(filePath);
			Model model = dataset.getDefaultModel();
			List<Entity> entities = intent.getEntities();
			String queryInfo = "";

			switch (intentInfo) {
			case "greet":
				int hellosrandom = (int) (Math.random() * hellos.length);
				map.put("content", hellos[hellosrandom]);
				map.put("timestamp", new Date().getTime());
				break;
			case "goodbye":
				int byesrandom = (int) (Math.random() * byes.length);
				map.put("content", byes[byesrandom]);
				map.put("timestamp", new Date().getTime());
				break;
			// 问诗人
			case "poet":
				// 获取诗人实体
				if (entities != null && entities.size() > 0) {
					for (int i = 0; i < entities.size(); i++) {
						Entity entity = entities.get(i);
						if (entity.getEntity().equals("poet")) {
							String sparql = "SELECT  ?o WHERE { <http://lcell.bnu.edu.cn/ontologies/chinese/poetry#"
									+ entity.getValue() + "> <http://lcell.bnu.edu.cn/owl/property#description> ?o }";
							ResultSet resultSet = selectQuery(model, sparql);
							String returnContent = "";
							while (resultSet.hasNext()) {
								QuerySolution querySolution = resultSet.next();
								result = querySolution.get("?o").toString();
								returnContent = result + "\n" + returnContent;
							}
							map.put("content", returnContent);
							map.put("timestamp", new Date().getTime());
							dataset.close();
							return map;
						}
					}
				}

				break;
			case "poetry":
				// 问诗
				if (entities != null && entities.size() > 0) {
					for (int i = 0; i < entities.size(); i++) {
						Entity entity = entities.get(i);
						if (entity.getEntity().equals("poet")) {
							String sparql = "SELECT  ?o WHERE { <http://lcell.bnu.edu.cn/ontologies/chinese/poetry#"
									+ entity.getValue()
									+ "> <http://lcell.bnu.edu.cn/ontologies/chinese/poetry#创作> ?o }";
							ResultSet resultSet = selectQuery(model, sparql);
							String returnContent = entity.getValue() + "的作品有：";

							while (resultSet.hasNext()) {
								QuerySolution querySolution = resultSet.next();
								result = querySolution.get("?o").toString();
								returnContent = returnContent + getIRIName(result) + "\n";
							}
							map.put("content", returnContent);
							map.put("timestamp", new Date().getTime());
							dataset.close();
							return map;
						}
					}
				}
				break;
			case "ask":
				// 问诗
				if (entities != null && entities.size() > 0) {
					String subject1 = "";
					String object1 = "";
					for (int i = 0; i < entities.size(); i++) {
						Entity entity = entities.get(i);

						if (entity.getEntity().equals("poet")) {
							subject1 = "<http://lcell.bnu.edu.cn/ontologies/chinese/poetry#" + entity.getValue() + ">";
						}
						if (entity.getEntity().equals("poetry")) {
							object1 = "<http://lcell.bnu.edu.cn/ontologies/chinese/poetry#" + entity.getValue() + ">";

						}
					}
					if (subject1.length() > 0 && object1.length() > 0) {
						String sparql = "select (COUNT(*) AS ?count) {" + subject1
								+ " <http://lcell.bnu.edu.cn/ontologies/chinese/poetry#创作> " + object1 + "}";
						ResultSet resultSet = selectQuery(model, sparql);
						int count = resultSet.next().getLiteral("?count").getInt();
						if (count > 0) {
							map.put("content", "是的");
							map.put("timestamp", new Date().getTime());
						} else {
							map.put("content", "不是");
							map.put("timestamp", new Date().getTime());
						}
					} else {
						map.put("content", "知识储备不足，暂时无法判断！");
						map.put("timestamp", new Date().getTime());
					}
					dataset.close();
					return map;

				}

				break;
			case "who":
				// 问诗
				if (entities != null && entities.size() > 0) {
					String subject1 = "";
					String object1 = "";
					for (int i = 0; i < entities.size(); i++) {
						Entity entity = entities.get(i);
						if (entity.getEntity().equals("poetry")) {
							object1 = "<http://lcell.bnu.edu.cn/ontologies/chinese/poetry#" + entity.getValue() + ">";
						}
					}
					if (object1.length() > 0) {
						String sparql = "select ?s WHERE {?s <http://lcell.bnu.edu.cn/ontologies/chinese/poetry#创作> " + object1
								+ "}";
						ResultSet resultSet = selectQuery(model, sparql);
						String returnContent = "";

						while (resultSet.hasNext()) {
							QuerySolution querySolution = resultSet.next();
							result = querySolution.get("?s").toString();
							returnContent = returnContent + getIRIName(result) + "\n";
						}

						map.put("content", returnContent);
						map.put("timestamp", new Date().getTime());

					} else {
						map.put("content", "知识储备不足，暂时无法判断！");
						map.put("timestamp", new Date().getTime());
					}
					dataset.close();
					return map;
				}
				break;
				// 导航【 模块，哦
			case "navigation":
				if (entities != null && entities.size() > 0) {
					
					map.put("content", "导航信息不齐！");
					map.put("timestamp", new Date().getTime());
					return map;
				}
				else{
					map.put("content", "导航信息不齐！");
					map.put("timestamp", new Date().getTime());
				}
				break;
			default:
				break;
			}
		
		}

		return map;
	}

	/**
	 * 根据sparql返回查询结果
	 * 
	 * @param model
	 * @param queryString
	 * @return
	 */
	public static ResultSet selectQuery(Model model, String queryString) {
		QueryExecution qexec = QueryExecutionFactory.create(queryString, model);
		ResultSet resultSet = qexec.execSelect();
		return resultSet;
	}

	public String[] hellos = { "你好呀！", "你好！为诚泰公司大佬们演示！", "Hello!", "嗯，你问我答", "哈罗，我努力处理各种疑难杂症" };
	public String[] byes = { "再见！", "88", "下次再聊哈" };

	/**
	 * 根据iri截取name
	 * 
	 * @author : 丁国柱
	 * @date : 2014-10-8 下午9:04:52
	 */
	public String getIRIName(String iri) {
		return iri.substring(iri.lastIndexOf("#") + 1, iri.length());
	}
}
