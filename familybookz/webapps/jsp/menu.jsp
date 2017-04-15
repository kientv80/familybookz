<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="container-fluid">
	<div class="row ">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
			<div style="float: left;padding-right: 10px;"><a href="/feeds" id = "feeds" class="title" style="text-align: left;">${profile.firstName }</a></div>
			<div style="float: left;padding-right: 10px;"><a href="/timeline"  id="tlnotify" class="title" onclick="return loadTimeLine();">Home</a></div>
			<div style="float: left;padding-right: 10px;"><a href="/family" id = "fnotify" class="title">Family</a></div>
			<div style="float: left;padding-right: 10px;">
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