package com.yc.damai.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.yc.damai.config.Config;
import com.yc.damai.po.Result;
import com.yc.damai.util.MD5Util;

/**
 * 	文件上传
 */
@WebServlet("/uploadmd5.do")
@MultipartConfig

public class UploadMd5Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        Part part = request.getPart("file");
//        String webpath = "/products/upload";
        String diskpath = request.getServletContext().getRealPath(Config.IMAGE_DIR);
        System.out.println(diskpath);
        String fileName = MD5Util.string2MD5(part.getSubmittedFileName().split("\\.")[0])+"."+part.getSubmittedFileName().split("\\.")[1];
        diskpath = diskpath + "/" + fileName;
//        webpath += "/" + part.getSubmittedFileName();
//        // 去掉首部的 /
//        webpath = webpath.substring(1);
        part.write(diskpath);



        Gson gson = new Gson();
        Result res = new Result( 1, "文件上传成功!",Config.IMAGE_URL_PREFIX+"/"+ fileName);
        String json = gson.toJson(res);
        response.getWriter().append(json);
    }
}