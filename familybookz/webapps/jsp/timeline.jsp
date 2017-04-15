<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<!-- column 1 -->
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8" style="padding: 0px;z-index: 2;">
		<div class="container-fluid" style="padding-right: 0px;">
			<!-- Post feed action -->
			<div class="row" style="padding: 0px;">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="container-fluid">
						<form action="/postnewfeed" method="post"  enctype="multipart/form-data" id="postFeedForm">
							<div class="form-group">
								<textarea class="form-control" rows="3" id="status" name="status" placeholder="Write something for your mom ..." style="border-radius:0;"></textarea>
							    <div class="container-fluid w3-card w3-light-grey">
							    	<input type="file" value="Browse" id="image" name="image" multiple style="float: left;">
							    	<input type="button" class="btn btn-primary" id="btnPost" value="Post" style="float: right;" onclick="return postFeed();">
							    </div>
							 </div>
						</form>
					</div>
				</div>
			</div>
			<!-- feeds container -->
			<div class="row" style="padding: 0px;">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="container-fluid">
						<c:forEach items="${feeds }" var="f">
							<div class="w3-card-2 w3-white" style="margin-bottom: 15px;">
								<header class="container-fluid w3-light-grey">
									<div class="row">
										<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
											<div class="w3-image"  style="width: 40px;height: 40px;padding: 2px;">
												<a href="/${ f.ownerInfo.domainName}">
													<img alt="" src="${f.ownerInfo.image }"  class="w3-circle">
												</a>
											</div>
										</div>
										<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11">
											<span class="title"><a href="/${ f.ownerInfo.domainName}">${f.ownerInfo.name}</a></span><br>
											<span class="note">${f.postedDate}</span>
										</div>
									</div>
								</header>
								<div class="container-fluid">
							    	<p>${f.content } </p>
							  	</div>
							  	<c:if test="${f.type ==1 }">
							  		<c:if test="${f.images != null }">
										<div class="container-fluid">
											<div class="row">
												<a  onclick="openLink('${f.url }')">
													<c:forEach items="${f.images }" var="imageUrl">
														<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
															<img src="${imageUrl}" class="w3-padding-8" style="max-width:100%;margin: auto;padding: 4px;">
														</div>
													</c:forEach>
												</a>
											</div>
										</div>
									</c:if>
									<div class="container-fluid">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
												<p><a  onclick="openLink('${f.url }')" class="title">${f.title}</a></p>
												<p>
													<a  onclick="openLink('${f.url }')">${f.desc}<br><span class="note">${f.website}</span></a>
												</p>
											</div>
										</div>
									</div>
							  	</c:if>
							  	<c:if test="${f.type ==0 }">
								  	<c:if test="${f.images != null && f.getImages().size() == 1}">
										<div class="container-fluid">
											<div class="row">
												<c:forEach items="${f.images }" var="imageUrl">
													<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
														<img src="${imageUrl}" class="w3-padding-8" style="max-width:100%;margin: auto;padding: 4px;">
													</div>
												</c:forEach>
											</div>
										</div>
									</c:if>
								  	<c:if test="${f.images != null && f.getImages().size() > 1}">
										<div class="container-fluid">
											<div class="row">
												<c:forEach items="${f.images }" var="imageUrl">
													<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
														<img src="${imageUrl}" class="w3-padding-8" style="max-width:100%;margin: auto;padding: 4px;">
													</div>
												</c:forEach>
											</div>
										</div>
									</c:if>
							  	</c:if>
							  	<c:if test="${f.act != null }">
							  		<div class="container-fluid">
								  		<span class="note">
									  		<a onclick="getProfiles('${f.act.likeIds}','like')"><span id="likeCount_${f.id }">${f.act.likeCount}</span> Likes </a>
									  		<a  onclick="getProfiles('${f.act.shareIds}','share')"><span id="shareCount_${f.id }">${f.act.shareCount}</span> Shares  </a>
									  		<a ><span id="commentCount_${f.id }">${f.act.commentCount}</span> Comments</a>
								  		</span> 
							  		</div> 
							  	</c:if>
							  	<c:if test="${f.act == null }">
							  		<div class="container-fluid">
								  		<span class="note">
									  		<a  onclick="getProfiles('','like')"><span id="likeCount_${f.id }">0</span> Likes </a>
									  		<a  onclick="getProfiles('','share')"><span id="shareCount_${f.id }">0</span> Shares  </a>
									  		<a  onclick="getProfiles('','comment')"><span id="commentCount_${f.id }">0</span> Comments</a>
								  		</span> 
							  		</div> 
							  	</c:if>
								<header class="container-fluid w3-light-grey w3-padding-medium">
									<c:choose>
										 <c:when test="${f.act.likeIds.indexOf(profile.id) >= 0 || f.act.likeIds==profile.id }">
										 	<a style="color: #5890ff;" id = "likeAct" class="title">Liked</a> 
										 </c:when>
										 <c:otherwise>
										 	<a  id = "likeAct_${f.id}" class="title" onclick="return action(${f.id},'${profile.id }','${f.act.likeIds}','l','${f.id}');">Like</a> 
										 </c:otherwise>
									</c:choose>
									<c:choose>
										 <c:when test="${f.act.shareIds.indexOf(profile.id) >= 0 || f.act.shareIds == profile.id }">
										 	<a style="color: #5890ff;" id = "shareAct" class="title">Shared</a>
										 </c:when>
										 <c:otherwise>
										 	<a   id = "shareAct_${f.id}" class="title" onclick="return action(${f.id},'${profile.id }','${f.act.shareIds}','s','${f.id}');">Share</a>
										 </c:otherwise>
									</c:choose>
								</header>
								<header class="container-fluid w3-light-grey" style="border-top: 1px solid #e1e2e3;">
									<div class="container-fluid" id="comment_${f.id}">
										<c:forEach items="${f.act.comments }" var="cmt">
											<div class="row" style="padding-bottom: 8px;">
												<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
													<a href="/${ cmt.commenterDomainName}">
														<img src="${cmt.profileAvatar }" style="width: 30px;height: 30px" class="w3-circle">
													</a>
												</div>
												<div class="col-xs-11 col-sm-11 col-md-11 col-lg-11">
													<span class="profile_comment"><a href="/${ cmt.commenterDomainName}">${cmt.profileName }</a></span>
													<span class="comment">${cmt.comment }</span>
													<c:if test="${cmt.type == 3 }">
														<a href="${cmt.url }">
															<img src="${cmt.image }" style="width: 60px; height: 50px;padding: 4px;">
															<span class="title">${cmt.title }</span>
															<span class="comment">${cmt.desc }</span>
														</a>
													</c:if>
													<c:if test="${cmt.type == 4 }">
														<img src="${cmt.image }" style="width: 40px; height: 40px;" class="w3-circle" >
													</c:if>
													<br>
													<a  class="name">Like</a>  <a class="name" >Reply</a>
												</div>
											</div>
										</c:forEach>
									</div>
									<div class="row" style="padding-bottom: 8px;">
										<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
											<img src="${profile.image }" style="width: 30px;height: 30px" class="w3-circle">
										</div>
										<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9">
											<div class="input-group">
										      <input type="text" class="form-control" aria-label="..." id="comment_input_${f.id}" placeholder="Enter your comment">
										      <div class="input-group-btn">
										        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Emoticon<span class="caret"></span></button>
										        	<div style="width: 450px;" class="dropdown-menu dropdown-menu-right">
										        		<c:forEach items="${emoticons }" var="e">
										        			<div style="float: left;padding: 8px;">
										        				<img alt="" src="${e.url }"  style="width: 40px; height: 40px;" onclick="return postComment('${f.id}','0','comment_input_${f.id}','comment_${f.id}','${e.id }');" >
										        			</div>
										        		</c:forEach>
										        	</div>
										      </div>
										    </div>
										</div>
										<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
											<button class="btn btn-primary" onclick="return postComment('${f.id}','0','comment_input_${f.id}','comment_${f.id}')">Post</button>
										</div>
									</div>
								</header>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- column 2 -->
	<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4  hidden-xs hidden-sm" style="padding-left: 0px;">
		<div class="container-fluid" style="padding-left: 0px;" data-spy="affix" id="myAffix">
			<div class="w3-card-2 w3-white" style="margin-bottom: 8px;">
				Latest activities from relative<br>
				Latest activities from relative<br>
				Latest activities from relative<br>
				Latest activities from relative<br>
				Latest activities from relative<br>
				Latest activities from relative<br>
				Latest activities from relative<br>
			</div>
			
			<div class="w3-card-2 w3-white" style="margin-bottom: 8px;">
				Birthday activities from relative<br>
				Birthday activities from relative<br>
				Birthday activities from relative<br>
				Birthday activities from relative<br>
				Birthday activities from relative<br>
				Birthday activities from relative<br>
				Birthday activities from relative<br>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="profileListContainer">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><span id="title"></span></h4>
      </div>
      <div class="modal-body" id="popupContent">
		<!-- 		Content -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
	function getPosition(element) {
	    var xPosition = 0;
	    var yPosition = 0;
	  
	    while(element) {
	        xPosition += (element.offsetLeft - element.scrollLeft + element.clientLeft);
	        yPosition += (element.offsetTop - element.scrollTop + element.clientTop);
	        element = element.offsetParent;
	    }
	    return { x: xPosition, y: yPosition };
	}
	$('#myAffix').on('affixed.bs.affix',function(){
		$('#myAffix').css("top","0");
// 		var po = getPosition(document.getElementById('myAffix'));
// 		$('#myAffix').css("right",(po.x+15)+"px");
	});
	$('#myAffix').on('affix.bs.affix',function(){
		var width = document.getElementById('myAffix').offsetWidth;
		$('#myAffix').css("width",width+"px");
	});
	
	$(document).ready(function() {
		var po = getPosition(document.getElementById('myAffix'));
		$('#myAffix').attr("data-offset-top",po.y);
	});
</script>