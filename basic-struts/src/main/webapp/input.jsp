<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:text name="app.title.welcome"/></title>
</head>
<body>
<h1><s:text name="app.heading.welcome"/></h1>
<s:actionerror/>
<s:fielderror fieldName="userName"/>
<p><a href="<s:url action='index'/>"><s:text name="app.link.hello"/></a></p>
</body>
</html>
