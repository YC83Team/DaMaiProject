package com.yc.damai.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.damai.dao.ProductDao;
import com.yc.damai.dao.UserDao;
import com.yc.damai.po.Result;

import redis.clients.jedis.Jedis;

@WebServlet("/user.do")
public class UserServlet extends BaseServlet {
	private UserDao dao = new UserDao();
	private ProductDao gdao = new ProductDao();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/*
	 * 登录
	 */
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String uname = request.getParameter("ename");
		String upass = request.getParameter("password");
		String check = request.getParameter("check");
		Map<String, Object> users = dao.selectByLogin(uname, uname, upass);// todo 获取登录信息
		// todo 勾选记住密码
		if ((users != null) && (check.equals("true"))) {
			System.out.println(users);
			request.getSession().setAttribute("loginedUser", users);// todo 用户存到session
			// Map<String,Object> user = (Map<String, Object>)
			// request.getSession().getAttribute("loginedUser");
			Cookie username = new Cookie("username", uname);// todo 登录信息存到cookie
			Cookie password = new Cookie("password", upass);
			username.setMaxAge(60 * 60 * 24 * 7);
			password.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(username);
			response.addCookie(password);
			print(response, new Result<>(1, "登录成功"));
		} else if ((users != null) && (check.equals("false"))) {// todo 不记住密码
			request.getSession().setAttribute("loginedUser", users);
			// todo 重新设置一个cookie取代之前保存的cookie
			Cookie password = new Cookie("password", "");
			password.setMaxAge(0);
			response.addCookie(password);
			print(response, new Result<>(1, "登录成功"));
		} else {
			print(response, new Result<>(0, "用户名|密码错误！"));
		}
		response.getWriter().close();

	}

	/*
	 * 手机号登录
	 */
	protected void loginByPhone(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");

		Jedis jedis = new Jedis();
		String yzm = jedis.get(phone);
		if (phone == null || phone.trim().isEmpty()) {
			print(response, new Result<>(0, "请输入手机号码"));
		} else if (code == null || code.trim().isEmpty()) {
			print(response, new Result<>(0, "请输入验证码"));
		} else if (!code.equals(yzm)) {// todo 1234为测试验证码 节约经费！！！&&(!"1234".equals(code)
			System.out.println("yzm:"+yzm);
			print(response, new Result<>(0, "验证码错误|不存在"));
		} else {
			Map<String, Object> user = dao.loginByPhone(phone);
			if (null == user || user.size() == 0) {
				print(response, new Result<>(0, "该手机号未注册"));
			} else {
				request.getSession().setAttribute("loginedUser", user);
				print(response, new Result<>(1, "登录成功"));
			}

		}
		jedis.close();
	}

	/**
	 * 注册
	 */
	protected void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String rcode = (String) request.getSession().getAttribute("Code");// 获取验证码
		String ename = request.getParameter("ename");
		String cname = request.getParameter("cname");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String sex = request.getParameter("sex");
		String reupass = request.getParameter("reupass");
		String code = request.getParameter("code");
		if (ename == null || ename.trim().isEmpty()) {
			response.getWriter().append("请填写用户名");
		} else if (password == null || password.trim().isEmpty()) {
			response.getWriter().append("请填写密码");
		} else if (password.equals(reupass) == false) {
			response.getWriter().append("密码不一致");
		} else if (cname == null || cname.trim().isEmpty()) {// 比对验证码
			response.getWriter().append("验证码不能为空");
		} else if (rcode.equals(code) == false) {
			response.getWriter().append("验证码错误");
		} else {
			dao.register(ename, cname, password, email, phone, sex);
			response.getWriter().append("{\"code\":\"1\"}");
		}
	}

	/*
	 * 获取登录信息
	 */
	protected void getUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
//        @SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		if (user == null) {
			print(response, new Result<>(0, "请先登录"));
		} else {
//            print(response, user);
			// todo 更新用户实时信息/
			Map<String, Object> user1 = dao.selectByLogin((String) user.get("ename"), (String) user.get("ename"),
					(String) user.get("password"));
			if (user1 == null) {
				print(response, new Result<>(0, "登陆失败"));
			} else {
				print(response, user1);
			}
		}

	}

	/*
	 * 修改用户信息
	 */
	protected void flash(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		String uid = "" + user.get("id");
		String cname = request.getParameter("cname");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String image = request.getParameter("image");
		if (user == null) {
			response.getWriter().append("{\"msg\":\"请先登录再进行操作\"}");
		}
		// 后期加上其他字段的判断
		dao.flash(cname, sex, email, phone, image, uid);
		print(response, new Result(1, "修改成功"));
	}

	/*
	 * 修改密码
	 */
	protected void alterPass(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		String uid = "" + user.get("id");
		String password = (String) user.get("password");
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		if (user == null) {
			response.getWriter().append("{\"msg\":\"请先登录再进行操作\"}");
		} else if (oldPass.equals(password) == false) {
			print(response, new Result(0, "旧密码错误"));
		} else {
			// 后期加上其他字段的判断
			dao.alterPass(newPass, uid);
			print(response, new Result(1, "修改密码成功"));
		}
	}

	/*
	 * 修改邮箱
	 */
	protected void alterEmail(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		String uid = "" + user.get("id");
		String rcode = (String) request.getSession().getAttribute("Code");// 获取验证码
		String code = request.getParameter("code");
		String email = request.getParameter("email");
		if (code == null || code.trim().isEmpty()) {
			print(response, new Result(0, "请填写邮箱验证码"));
		} else if (rcode.equals(code) == false) {
			print(response, new Result(0, "验证码不一致"));
		} else {
			dao.alterEamil(email, uid);
			print(response, new Result(1, "修改成功"));
		}
	}

	/*
	 * 修改手机号码
	 */
	protected void alterPhone(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Jedis jedis = new Jedis();
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		String uid = "" + user.get("id");
		String yzm = jedis.get("yzm");
		String phone = request.getParameter("phone");
		String yzCode = request.getParameter("phoneCode");
		if (yzCode == null || yzCode.trim().isEmpty()) {
			print(response, new Result<>(0, "请填写手机验证码"));
		} else if (yzm == null | yzm.trim().isEmpty()) {
			print(response, new Result<>(0, "验证码不存在|已过期"));
		} else if (yzCode.equals(yzm) == false) {
			print(response, new Result(0, "验证码不一致"));
		} else {
			dao.alterPhone(phone, uid);
			print(response, new Result<>(1, "修改成功"));
		}
		jedis.close();
	}

	/*
	 * 注销 退出功能
	 */
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		if (user != null) {
			HttpSession session = request.getSession();
			session.removeAttribute("loginedUser");
			// 防止缓存
			// Forces caches to obtain a new copy of the page from the origin server
			response.setHeader("Cache-Control", "no-cache");
			// Directs caches not to store the page under any circumstance
			response.setHeader("Cache-Control", "no-store");
			// HTTP 1.0 backward compatibility
			response.setHeader("Pragma", "no-cache");
			// Causes the proxy cache to see the page as "stale"
			response.setDateHeader("Expires", 0);
			
			print(response, new Result<>(1, "退出成功"));
		} else {
			print(response, new Result<>(0, "尚未登陆"));
		}

	}

	/*
	 * 我的足迹
	 */
	protected void footprint(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Jedis jedis = new Jedis();
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		String uid = "" + user.get("id");
		List<Map<String, Object>> list = new ArrayList<>();
//        HashMap<String,Object> list =new HashMap<>();
		Set<String> set = jedis.smembers("step" + uid);
		for (String s : set) {
			List<Map<String, Object>> product = gdao.querySingle(s);
			list.addAll(product);// todo 将多个list 合并成一个
		}
		System.out.println(Arrays.toString(set.toArray()));
		System.out.println(set);
		System.out.println(list);
		print(response, list);
		jedis.close();
	}

	/*
	 * 我的收藏
	 */
	protected void myCollection(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Jedis jedis = new Jedis();
		@SuppressWarnings("unchecked")
		Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		String uid = "" + user.get("id");
		List<Map<String, Object>> list = new ArrayList<>();
//        HashMap<String,Object> list =new HashMap<>();
		Set<String> set = jedis.smembers("collection" + uid);
		for (String s : set) {
			List<Map<String, Object>> product = gdao.querySingle(s);
			list.addAll(product);
		}
		System.out.println(set);
		System.out.println(list);
		print(response, list);
		jedis.close();
	}

	/*
	 * 找回密码
	 */
	protected void checkphone(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		Jedis jedis = new Jedis();
		String yzm = jedis.get(phone);
		if (phone == null || phone.trim().isEmpty()) {
			print(response, new Result<>(0, "请输入手机号码"));
		} else if (code == null || code.trim().isEmpty()) {
			print(response, new Result<>(0, "请填写验证码"));
		} else if ((!code.equals(yzm)) && (!"1234".equals(code))) {
			print(response, new Result<>(0, "验证码不一致"));
		} else {
			print(response, new Result<>(1, "验证通过"));
		}

	}

	protected void findPass(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String phone = request.getParameter("phone");
		String reupass = request.getParameter("reuPass");
		String newPass = request.getParameter("newPass");
		if (newPass == null || newPass.trim().isEmpty()) {
			print(response, new Result<>(0, "请输入密码"));
		} else if (reupass == null || reupass.trim().isEmpty()) {
			print(response, new Result<>(0, "请确认密码"));
		} else if (!newPass.equals(reupass)) {
			print(response, new Result<>(0, "密码不一致"));
		} else {
			dao.repass(newPass, phone);
			print(response, new Result<>(1, "重置成功"));
		}

	}
}
