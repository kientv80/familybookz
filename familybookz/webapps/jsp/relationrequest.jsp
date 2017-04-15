<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="w3-container w3-padding-0" style="width: 100%;">
	<c:if test="${ requests != null}">
		<c:forEach items="${requests }" var="r">
			<div class="w3-row">
				<div class="w3-col s10 m10 l10">
					<img alt="" src="${r.requesterImage }" style="width:50px;height:50px;">${r.requesterName } want to add you as ${r.relation}
				</div>
				<div class="w3-col s1 m1 l1">
					<a href="/rconfirm?id=${r.id }">Confirm</a>
				</div>
				<div class="w3-col s1 m1 l1">
					<a href="/rdelete?id=${r.id }">Delete</a>
				</div>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${ requests != null}">
		<div class="w3-row">
			<div class="w3-col s10 m10 l10">
				There is no request
			</div>
		</div>
	</c:if>
</div>
