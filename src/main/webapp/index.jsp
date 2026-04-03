<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Basic Struts Application - Welcome</title>
    </head>
    <body>
        <h1>Welcome To Struts!</h1>
        <p><a href="<s:url action='hello'/>">Hello World</a></p>

        <s:url action="hello" var="helloLink">
  <s:param name="userName">Bruce Phillips</s:param>
</s:url>

<p><a href="${helloLink}">Hello Bruce Phillips</a></p>

<a href='<s:url action="index" namespace="config-browser" />'>Launch the configuration browser</a>

    </body>
</html>
