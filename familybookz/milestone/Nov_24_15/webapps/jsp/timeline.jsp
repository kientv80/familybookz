<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="w3-container w3-padding-0" style="width: 100%;">
	<div class="w3-row">
		<div class="w3-col s12 m12 l8">
				<div class="w3-row">
					<div class="w3-container w3-padding-0" style="width: 100%;float: left;">
						<form action="/postnewfeed" method="post"  enctype="multipart/form-data" id="postFeedForm">
							<div class="form-group">
								<textarea class="form-control" rows="3" id="status" name="status" placeholder="Write something for your mom ..." style="border-radius:0;"></textarea>
							    <div class="w3-container w3-card w3-light-grey">
							    	<input type="file" value="Browse" id="image" name="image" multiple style="float: left;">
							    	<input type="button" class="btn btn-primary" id="btnPost" value="Post" style="float: right;" onclick="return postFeed();">
							    </div>
							 </div>
						</form>
					</div>
				</div>
				<div class="w3-row">
					<div class="w3-container w3-padding-small">
					
						<c:forEach items="${feeds }" var="f">
							<div class="w3-card-2 w3-white" style="margin: auto 4px 16px 4px;">
								<header class="w3-container w3-light-grey">
									<div class="w3-row">
										<div class="w3-col s1 m1 l1">
											<div class="w3-image"  style="width: 40px;height: 40px;padding: 2px;">
												<img alt="" src="${f.ownerInfo.image }"  class="w3-circle">
											</div>
										</div>
										<div class="w3-col s10 m10 l10 w3-small">
											<span class="title">${f.ownerInfo.name}</span><br>
											<span class="note">${f.postedDate}</span>
										</div>
									</div>
								</header>
								<div class="w3-container">
							    	<p>${f.content } </p>
							  	</div>
							  	<c:if test="${f.type ==1 }">
							  		<c:if test="${f.images != null }">
										<div class="w3-container">
											<div class="w3-row">
												<a href="#" onclick="openLink('${f.url }')">
													<c:forEach items="${f.images }" var="imageUrl">
														<div class="w3-col s12 m12 l12">
															<img src="${imageUrl}" class="w3-padding-8" style="max-width:100%;margin: auto;padding: 4px;">
														</div>
													</c:forEach>
												</a>
											</div>
										</div>
									</c:if>
									<div class="w3-container">
										<div class="w3-row">
											<div class="w3-col s12 m12 l12">
												<p><a href="#" onclick="openLink('${f.url }')" class="title">${f.title}</a></p>
												<p>
													<a href="#" onclick="openLink('${f.url }')">${f.desc}<br><span class="note">${f.website}</span></a>
												</p>
											</div>
										</div>
									</div>
							  	</c:if>
							  	<c:if test="${f.type ==0 }">
								  	<c:if test="${f.images != null && f.getImages().size() == 1}">
										<div class="w3-container">
											<div class="w3-row">
												<c:forEach items="${f.images }" var="imageUrl">
													<div class="w3-col s12 m12 l12">
														<img src="${imageUrl}" class="w3-padding-8" style="max-width:100%;margin: auto;padding: 4px;">
													</div>
												</c:forEach>
											</div>
										</div>
									</c:if>
								  	<c:if test="${f.images != null && f.getImages().size() > 1}">
										<div class="w3-container">
											<div class="w3-row">
												<c:forEach items="${f.images }" var="imageUrl">
													<div class="w3-col s12 m6 l6">
														<img src="${imageUrl}" class="w3-padding-8" style="max-width:100%;margin: auto;padding: 4px;">
													</div>
												</c:forEach>
											</div>
										</div>
									</c:if>
							  	</c:if>
							  	<c:if test="${f.act != null }">
							  		<div class="w3-container">
								  		<span class="note">
									  		<a href="#" onclick="getProfiles('${f.act.likeIds}','like')"><span id="likeCount_${f.id }">${f.act.likeCount}</span> Likes </a>
									  		<a href="#" onclick="getProfiles('${f.act.shareIds}','share')"><span id="shareCount_${f.id }">${f.act.shareCount}</span> Shares  </a>
									  		<a ><span id="commentCount_${f.id }">${f.act.commentCount}</span> Comments</a>
								  		</span> 
							  		</div> 
							  	</c:if>
							  	<c:if test="${f.act == null }">
							  		<div class="w3-container">
								  		<span class="note">
									  		<a href="#" onclick="getProfiles('','like')"><span id="likeCount_${f.id }">0</span> Likes </a>
									  		<a href="#" onclick="getProfiles('','share')"><span id="shareCount_${f.id }">0</span> Shares  </a>
									  		<a href="#" onclick="getProfiles('','comment')"><span id="commentCount_${f.id }">0</span> Comments</a>
								  		</span> 
							  		</div> 
							  	</c:if>
								<header class="w3-container w3-light-grey w3-padding-medium">
									<c:choose>
										 <c:when test="${f.act.likeIds.indexOf(profile.id) >= 0 || f.act.likeIds==profile.id }">
										 	<a style="color: #5890ff;" id = "likeAct" class="title">Liked</a> 
										 </c:when>
										 <c:otherwise>
										 	<a href="#" id = "likeAct_${f.id}" class="title" onclick="return action(${f.id},'${profile.id }','${f.act.likeIds}','l','${f.id}');">Like</a> 
										 </c:otherwise>
									</c:choose>
									<c:choose>
										 <c:when test="${f.act.shareIds.indexOf(profile.id) >= 0 || f.act.shareIds == profile.id }">
										 	<a style="color: #5890ff;" id = "shareAct" class="title">Shared</a>
										 </c:when>
										 <c:otherwise>
										 	<a href="#"  id = "shareAct_${f.id}" class="title" onclick="return action(${f.id},'${profile.id }','${f.act.shareIds}','s','${f.id}');">Share</a>
										 </c:otherwise>
									</c:choose>
								</header>
								<header class="w3-container w3-light-grey" style="border-top: 1px solid #e1e2e3;">
									<div class="w3-container" id="comment_${f.id}">
										<c:forEach items="${f.act.comments }" var="cmt">
											<div class="w3-row" style="padding-bottom: 8px;">
												<div class="w3-col s1 m1 l1">
													<img src="${cmt.profileAvatar }" style="width: 30px;height: 30px" class="w3-circle">
												</div>
												<div class="w3-col s11 m11 l11">
													<span class="profile_comment">${cmt.profileName }</span>
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
													<a href="#" class="name">Like</a>  <a class="name" href="#">Reply</a>
												</div>
											</div>
										</c:forEach>
									</div>
									<div class="w3-row" style="padding-bottom: 8px;">
										<div class="w3-col s1 m1 l1">
											<img src="${profile.image }" style="width: 30px;height: 30px" class="w3-circle">
										</div>
										<div class="w3-col s11 m11 l11">
											<div class="input-group">
										      <input type="text" class="form-control" aria-label="..." id="comment_input_${f.id}" placeholder="Enter your comment">
										      <div class="input-group-btn">
										        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Emoticon<span class="caret"></span></button>
<!-- 										        <ul class="dropdown-menu dropdown-menu-right"> -->
										        	<div style="width: 450px;" class="dropdown-menu dropdown-menu-right">
										        		<c:forEach items="${emoticons }" var="e">
										        			<div style="float: left;padding: 8px;">
										        				<img alt="" src="${e.url }"  style="width: 40px; height: 40px;" onclick="return postComment('${f.id}','0','comment_input_${f.id}','comment_${f.id}','${e.id }');" >
										        			</div>
										        		</c:forEach>
										        	</div>
										        
<!-- 										        </ul> -->
										      </div><!-- /btn-group -->
										    </div><!-- /input-group -->
										</div>
										<div class="w3-col s1 m1 l1">
											<button onclick="return postComment('${f.id}','0','comment_input_${f.id}','comment_${f.id}')">Post</button>
										</div>
									</div>
								</header>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="w3-col s12 m12 l4 w3-white">
				<div class="w3-container">
				<c:forEach items="${feeds }" var="ff">
					<c:if test="${ff.type == 1 }">
						<div class="w3-card-2">
							<div class="w3-row">
								<a href="#" onclick="openLink('${ff.url }')">
									<c:forEach items="${ff.images }" var="imageUrl2">
										<div class="w3-col s2 m2 l2">
											<img alt="" src="${imageUrl2}" style="width: 50px;height: 40px">
										</div>
									</c:forEach>
									<div class="w3-col s10 m10 l10">
										<span class="title">${ff.title }</span>
										<span class="comment">${ff.desc }</span>
									</div>
								</a>
							</div>
						</div>
					</c:if>
				</c:forEach>
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

