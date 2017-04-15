<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/css/giaphalayout.css">
<script src="/js/jquery-2.1.3.min.js"></script>
<script src="/js/socketjs/sockjs.js"></script>
<script src="/js/stomp-websocket-2.3.4/lib/stomp.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="/js/bootstrap.min.js"></script>
<script src="/js/giapha.js"></script>

<head>
</head>
<body class="body">

	<script>
		  window.fbAsyncInit = function() {
		    FB.init({
		      appId      : '1489327961379891',
		      xfbml      : true,
		      version    : 'v2.4'
		    });
		  };
		
		  (function(d, s, id){
		     var js, fjs = d.getElementsByTagName(s)[0];
		     if (d.getElementById(id)) {return;}
		     js = d.createElement(s); js.id = id;
		     js.src = "//connect.facebook.net/en_US/sdk.js";
		     fjs.parentNode.insertBefore(js, fjs);
		   }(document, 'script', 'facebook-jssdk'));
		  
	</script>

<%-- 	<tiles:insertAttribute name="header" /> --%>
	<div style="margin: auto; margin-top: 60px;">
		<tiles:insertAttribute name="content" />
	</div>
</body>
</html>