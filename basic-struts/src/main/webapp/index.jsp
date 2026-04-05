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

<p><a href="<s:url action='hello'/>"><s:text name="app.link.hello"/></a></p>

<s:url action="hello" var="helloLink">
    <s:param name="userName">Bruce Phillips</s:param>
</s:url>
<p><a href="${helloLink}"><s:text name="app.link.bruce.example"/></a></p>

<h2><s:text name="app.form.greeting.label"/></h2>
<s:form action="helloSubmit" method="post">
    <s:textfield name="userName" label="%{getText('app.form.userName.label')}"/>
    <s:token/>
    <s:submit key="app.form.submit"/>
</s:form>
</body>
</html>
