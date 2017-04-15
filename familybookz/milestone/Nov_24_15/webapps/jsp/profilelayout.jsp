<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="/css/w3.css">
<link rel="stylesheet" href="/css/giaphalayout.css">
<script src="/js/jquery-2.1.3.min.js"></script>
<script src="/js/socketjs/sockjs.js"></script>
<script src="/js/stomp-websocket-2.3.4/lib/stomp.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="/js/bootstrap.min.js"></script>
<script src="/js/giapha.js"></script>
<script type="text/javascript" src="/js/ejs/ejs_production.js"></script>
<script type="text/javascript" src="/js/ejs/ejs_fulljslint.js"></script>
<head>
</head>
<body style="position: relative; min-height: 100%; top: 0px;">
	<script>
		window.fbAsyncInit = function() {
			FB.init({
				appId : '1489327961379891',
				xfbml : true,
				version : 'v2.4'
			});
		};

		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) {
				return;
			}
			js = d.createElement(s);
			js.id = id;
			js.src = "//connect.facebook.net/en_US/sdk.js";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
		openSocket();
	</script>
	<div id="fb-root"></div>
	<div class="w3-row">
		<div class="w3-col s0 m1 l2">
			<div class="w3-container"></div>
		</div>
		<div class="w3-col s12 m10 l8">
				<div class="w3-container w3-card w3-padding-0 w3-grey" style="width: 100%; margin: auto; overflow-x: auto;">
					<!-- 	header -->
					<tiles:insertAttribute name="header" />
					<!-- 		Left menu -->
					<div class="w3-container w3-light-grey">
						<tiles:insertAttribute name="menu" />
					</div>
					<div class="w3-container" style="padding: 0px;">
						<!-- 		Content -->
						<tiles:insertAttribute name="content" />
					</div>
					<tiles:insertAttribute name="footer" />
				</div>
		</div>
		<div class="w3-col s0 m1 l2">
			<div class="w3-container"></div>
		</div>
	</div>

	<script type="text/javascript">
		getOfflineEvents();
		function getOfflineEvents(){
			$.ajax({
				url : "/offlineevent",
				success : function(offlineEvent) {
					if(offlineEvent != "" && offlineEvent.relationRequestPending != 0)
						addNotifyIcon("availableupdatesNotify",offlineEvent.relationRequestPending);
					if(offlineEvent != "" && offlineEvent.newFeeds != 0)
						addNotifyIcon("tlnotify",offlineEvent.newFeeds);
				}
			});
		}
	</script>
</body>
</html>