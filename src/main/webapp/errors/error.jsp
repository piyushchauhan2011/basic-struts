<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:text name="error.page.title"/></title>
</head>
<body>
<h1><s:text name="error.page.title"/></h1>
<p><s:text name="error.page.message"/></p>
<p><a href="<s:url action='index'/>">Home</a></p>
</body>
</html>
