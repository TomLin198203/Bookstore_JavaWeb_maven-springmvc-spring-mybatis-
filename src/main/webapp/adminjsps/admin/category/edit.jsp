<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加分類</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript">
		function checkForm() {
			if(!$("#cname").val()) {
				alert("分類名稱不能為空！");
				return false;
			}

			return true;
		}
	</script>
<style type="text/css">
	body {background: rgb(254,238,189);}
</style>
  </head>
  
  <body>
    <h3>修改級分類</h3>
    <h1></h1>
    <p style="font-weight: 900; color: red">${adminmsg }</p>
    <form action="<c:url value='/admin/AdminCategoryServlet/editParent'/>" method="post" onsubmit="return checkForm()">
    	<input type="hidden" name="cid" value="${category.cid}"/>
    	<%--<input type="hidden" name="method" value="editParent"/> --%>
    	分類名稱：<input type="text" name="cname" id="cname" value="${category.cname}"/><br/>
    	<input type="submit" value="修改分類"/>
    	<input type="button" value="返回" onclick="history.go(-1)"/>
    </form>
  </body>
</html>
