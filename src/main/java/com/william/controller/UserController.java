package com.william.controller;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 传统方式上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/fileload")
    public String fileload(HttpServletRequest request) throws Exception {
        System.out.println("传统文件上传");

        //使用fileupload组件完成文件上传
        //1.创建上传文件夹
        //文件上传位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        System.out.println("文件上传路径为："+path);
        //判断该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            //如果不存在，创建文件夹
            file.mkdir();
        }
        //2.解析request对象，获取上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        //解析request
        List<FileItem> fileItems = upload.parseRequest(request);

        for (FileItem item : fileItems) {
            //判断当前的item是否为上传文件项
            if(item.isFormField()){
                //item为普通表单项
            }else{
                //item为上传文件项
                //获取上传文件的名称
                String name = item.getName();
                //设置文件名字唯一值
                String uuid = UUID.randomUUID().toString().replace("-", "");
                name = uuid + "_" + name ;
                //完成文件上传
                item.write(new File(path,name));
                //删除临时文件
                item.delete();
            }
        }
        return "success";
    }


    /**
     * springmvc方式上传文件到服务器，配置文件解析器后，springmvc自动帮忙解析，不需要我们手动代码解析
     *
     * @param request
     * @param upload2
     * @return
     * @throws Exception
     */
    @RequestMapping("/springmvcfileload")
    public String springmvcfileload(HttpServletRequest request, MultipartFile upload2) throws Exception {
        //注意：形参MultipartFile upload2中的upload2必须与jsp文件中，选择文件的那个input的name一致，否则不会自动封装
        System.out.println("springmvc文件上传");

        //使用fileupload组件完成文件上传
        //1.创建上传文件夹
        //文件上传位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        System.out.println("文件上传路径为："+path);
        //判断该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            //如果不存在，创建文件夹
            file.mkdir();
        }

        //获取上传文件的名称
        String name =upload2.getOriginalFilename();
        //设置文件名字唯一值
        String uuid = UUID.randomUUID().toString().replace("-", "");
        name = uuid + "_" + name ;
        //完成文件上传
        upload2.transferTo(new File(path,name));

        return "success";
    }


    /**
     * 夸服务器文件上传
     * @param upload3
     * @return
     * @throws Exception
     */
    @RequestMapping("/defserverfileload")
    public String defserverfileload(MultipartFile upload3) throws Exception {
        //注意：形参MultipartFile upload2中的upload2必须与jsp文件中，选择文件的那个input的name一致，否则不会自动封装
        System.out.println("跨服务器文件上传");
        //定义文件上传位置
        String path = "http://localhost:9090/fileuploadserver_war_exploded/uploads/";

        //获取上传文件的名称
        String name =upload3.getOriginalFilename();
        name = URLEncoder.encode(name,"UTF-8");
        //设置文件名字唯一值
        String uuid = UUID.randomUUID().toString().replace("-", "");
        name = uuid + "_" + name ;
        //完成文件上传

        //创建客户端对象，用于向文件服务器发起连接的客户端
        Client client = Client.create();
        //连接文件服务器
        WebResource resource = client.resource(path + name);
        //上传文件
        resource.put(upload3.getBytes());

        return "success";
    }
}
