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
<script type="text/javascript" src="/js/mustache/mustache.min.js"></script>
<style>
/* 	.container-fluid{ */
/* 		padding: 0px; */
/* 		margin: 0px; */
/* 	} */
</style>
<head>
</head>
<body style="background-color: #f1f1f1;">
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
<div class="container-fluid" style="padding: 0px;">
	<div class="row" style="padding: 0px;margin: 0px;">
		<div class="hidden-xs hidden-sm col-md-2 col-lg-2" style="padding: 0px;">
			<div class="container-fluid">
				<!-- Reserve column on the left -->
			</div>
		</div>
		
		
		<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8 w3-card-2" style="padding: 0px;">
			<!-- 	header -->
			<div class="container-fluid"  style="padding: 0px;">
				<tiles:insertAttribute name="header" />
			</div>
			<!-- 	Menu -->
			<div class="container-fluid"  style="padding: 10px 0px 10px 0px;">
				<tiles:insertAttribute name="menu" />
			</div>
			<div class="container-fluid"  style="padding: 0px;    background-color: white;">
				<div class="row" style="padding: 0px;">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"  style="padding: 0px;">
						<div class="container-fluid" style="background-color: transparent ;padding: 0px;">
							<!-- 		Content -->
							<tiles:insertAttribute name="content" />
						</div>
					</div>
				</div>
			</div>

		</div>
		<div class="hidden-xs hidden-sm col-md-2 col-lg-2" style="padding: 0px;">
			<div class="container-fluid">
				<!-- Reserve column on the right -->
			</div>
		</div>
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