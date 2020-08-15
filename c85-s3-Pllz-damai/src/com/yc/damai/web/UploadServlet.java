package com.yc.damai.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.yc.damai.po.Result;
/**
 * 文件上传
 * @author 李玲芝
 *
 */
@MultipartConfig
@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		response.setCharacterEncoding("utf-8");

		response.setContentType("text/html;charset=utf-8");

		

		Part part = request.getPart("file");
		//获取工程更目录  tomcat服务器/webapps/工程名
		String webpath = "/";
		String diskpath = request.getServletContext().getRealPath(webpath);
		//将路径转为file对象
		File proDir=new File(diskpath);
		//获取上级的上级目录  tomcat
		File tomcatDir=proDir.getParentFile().getParentFile();
		//获取上级的上级目录  /webapps/ROOT/upload
		File uploadDir=new File(tomcatDir,"/webapps/ROOT/upload");
		
		
		diskpath = uploadDir.getAbsolutePath();
		//重新赋值磁盘路径
		diskpath = diskpath + "/" + part.getSubmittedFileName();
		webpath += "/" + part.getSubmittedFileName();
		// 去掉首部的 /
		webpath = webpath.substring(1);
		System.out.println("webpath:"+webpath);
		part.write(diskpath);

		Gson gson = new Gson();
		Result res = new Result( 1, "文件上传成功!", "/upload/"+part.getSubmittedFileName());
		String json = gson.toJson(res);
		response.getWriter().append(json);
	}

}
