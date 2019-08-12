<%--
  Created by IntelliJ IDEA.
  User: 15741
  Date: 2019/7/26
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录界面</title>
</head>
<body>
<h2 align="center">欢迎登录</h2>
<form action="/param" role="form" method=post>
<table align="center">
    <tr>
        <td>用户名：</td><td><input type=text name=username /></td>
    </tr>
    <tr>
        <td>密码：</td><td><input type=password name=password /></td>
    </tr>

    <tr>
        <td colspan="2",align="center">
            <input type="submit" value="submit" />
            <input type="reset" value="reset" />
        </td>
    </tr>
</table>
</form>

</body>
</html>
