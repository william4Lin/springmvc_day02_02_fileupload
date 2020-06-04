<%--
  Created by IntelliJ IDEA.
  User: pactera
  Date: 2020/6/3
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
        <h2>文件上传</h2>

        <h1>传统方式上传文件到服务器：</h1><br>
        <form action="user/fileload" method="post" enctype="multipart/form-data">
            选择文件:<input type="file" name="upload"/><br>
            <input type="submit" name="提交"/>

        </form>


        <h1>SpringMVC方式上传文件到服务器：</h1><br>
        <form action="user/springmvcfileload" method="post" enctype="multipart/form-data">
            选择文件:<input type="file" name="upload2"/><br>
            <input type="submit" name="提交"/>

        </form>

        <h1>跨服务器上传文件到服务器：</h1><br>
        <form action="user/defserverfileload" method="post" enctype="multipart/form-data">
            选择文件:<input type="file" name="upload3"/><br>
            <input type="submit" name="提交"/>

        </form>


</body>
</html>
