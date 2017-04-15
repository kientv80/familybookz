<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header class="container-fluid w3-white" style="width:100%;">
	<div class="row">
		<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
			<div class="container-fluid">
				<div class="w3-image">
					<img alt="" src="${family.me.image }"  class="w3-circle">
				</div>
			</div>
		</div>
		<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
			<div class="container-fluid">
				<c:forEach items="${family.wife }" var="wife">
					<div class="w3-image" style="width:60px;height:60px;float: left;background-image: url('${wife.image }');background-repeat: no-repeat;background-size: 100% 100%">
						<img alt="" src="/images/icons/starnhon50.png" style="width: 60px;height: 60px;">
					</div>
				</c:forEach>
				<c:forEach items="${family.kids }" var="child">
					<div class="w3-image" style="width:50px;height:50px;float: left;background-image: url('${child.image }');background-repeat: no-repeat;background-size: 100% 100%">
						<img alt="" src="/images/icons/starnhon50.png">
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</header>