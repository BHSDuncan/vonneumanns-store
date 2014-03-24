<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The Mad Grapher's Mad Gaming Bonanza</title>
</head>
<body>
Welcome!
<div>
	<form action="/store/" method="POST">
		<div>
	        <form:select name="lstGames1" path="selectGames1" id="selectGames1">
	        	<form:option value="0" label="- Choose Game 1 -" />
	        	<form:options items="${games}" itemValue="gameId" itemLabel="title" />
	        </form:select>
	    </div>
		<div>
	        <form:select name="lstGames2" path="selectGames2" id="selectGames2">
	        	<form:option value="0" label="- Choose Game 2 -" />
	        	<form:options items="${games}" itemValue="gameId" itemLabel="title" />
	        </form:select>
	    </div>
	    
        <input type="submit" value="Search"/>
    </form>
</div>


</body>
</html>