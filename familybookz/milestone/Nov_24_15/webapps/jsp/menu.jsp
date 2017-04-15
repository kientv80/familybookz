<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="w3-topnav">
	<div class="w3-row ">
		<div class="w3-col s3 m2 l2 "><a href="/feeds" id = "feeds" class="title" style="text-align: left;">${profile.firstName }</a></div>
		<div class="w3-col s3 m2 l2 "><a href="/timeline"  id="tlnotify" class="title" onclick="return loadTimeLine();">Home</a></div>
		<div class="w3-col s3 m2 l2 "><a href="/family" id = "fnotify" class="title">Family</a></div>
		<div class="w3-col s3 m2 l2 ">
		<div class="dropdown">
			<img src="/images/icons/available-updates.png" class="dropdown-toggle" id="availableupdates" data-toggle="dropdown" />
			<span id="availableupdatesNotify"></span>
			<ul class="dropdown-menu" aria-labelledby="availableupdates">
			    <li><a href="/relationrequest" class="title" id = "rnotify">Request</a></li>
			    <li><a href="/logout" id = "logout" class="title">Logout</a></li>
			</ul>
		</div>
		</div>
	</div>
</nav>
<script type="text/javascript">
	function loadTimeLine(){
		$.ajax({
			url : "/clearofflineFeeds",
			success : function(offlineEvent) {
			}
		});
		window.location = "/timeline";
	}
</script>