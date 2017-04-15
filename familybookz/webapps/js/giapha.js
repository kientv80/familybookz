function registerPopOver() {
	$(document).ready(function() {
		$(function() {
			$('[data-toggle="popover"]').popover({
				trigger : 'hover click',
				html : true,
			});
		});
	});
}

function findMyRoot(currentUserId, level) {
	$.ajax({
		url : "/findMyRoot?level=" + level + "&id=" + currentUserId,
		success : function(person) {
			var root = document.getElementById(person.id);
			if (root == null || root == undefined) {
				root = addPersonInfo(person, level, currentUserId,true);
				document.getElementById("container").appendChild(root);
				loadAllChildren(person, level, currentUserId);
				registerPopOver();
			}
		}
	});
}

function loadMyChildren(id, level, currentUserId) {
	$.ajax({
		url : "/loadchildren?id=" + id + "&level=" + level,
		success : function(person) {
			var check = alreadyLoadAllChildren(person, level);
			if (check == null || check == undefined) {
				loadAllChildren(person, level, currentUserId);
				registerPopOver();
			}
		}
	});
}
function loadParents(id, currentUserId, dad, mom) {
	if(mom > 0 || dad > 0){
		if(document.getElementById(mom + "") == undefined && document.getElementById(dad + "") == undefined){
			$.ajax({
				url : "/loadParent?id=" + id,
				success : function(person) {
					if(person.errorCode != undefined && person.errorCode == 1 ){
						alert("No parent found");
					}else{
						parent = addPersonInfo(person, 2, currentUserId, true);
						document.getElementById("container").appendChild(parent);
						loadAllChildren(person, 2, currentUserId);
						registerPopOver();
					}
				}
			});
		}
	}
}
function alreadyLoadAllChildren(person, level) {
	if (person != null && person.children != null
			&& person.children != undefined && person.children.length > 0) {
		var children = person.children;
		var j = 0;
		for (j = 0; j < children.length; j++) {
			var c = children[j];
			if (document.getElementById(c.id) != null) {
				return true;
			} else {
				return alreadyLoadAllChildren(c, level);
			}
		}
	}
}
function loadAllChildren(person, level, currentUserId) {
	loadChildren(person, level, currentUserId);
	level = level - 1;
	if (level > 0) {
		if (person != null && person.children != null
				&& person.children != undefined && person.children.length > 0) {
			var children = person.children;
			var j = 0;
			for (j = 0; j < children.length; j++) {
				var c = children[j];
				loadAllChildren(c, level,currentUserId);
			}
		}
	}
}

function loadChildren(person, level,currentUserId) {
	if (person != null && person.children != null
			&& person.children != undefined && person.children.length > 0) {
		var children = person.children;
		var table = document.createElement("table");
		var tr1 = document.createElement("tr");
		var td1 = document.createElement("td");
		td1.setAttribute("colspan", person.children.length);
		td1.setAttribute("id", "td_"+person.id);
		td1.appendChild(document.createTextNode("|"));
		tr1.appendChild(td1);
		table.appendChild(tr1);

		var tr = document.createElement("tr");
		tr.setAttribute("id", "tr_"+person.id);
		table.appendChild(tr);
		for (i = 0; i < children.length; i++) {
			var td = document.createElement("td");
			td.setAttribute("class", "personContainer");
			var seperator = document.createElement("div");
			if (children.length > 1) {
				if (i == 0) {
					seperator.setAttribute("class", "seperator_right");
				} else if (i == (children.length - 1)) {
					seperator.setAttribute("class", "seperator_left");
				} else {
					seperator.setAttribute("class", "seperator");
				}
				td.appendChild(seperator);
			} else if (children.length == 1) {
				seperator.setAttribute("class", "seperator");
				td.appendChild(seperator);
			}

			td.appendChild(addPersonInfo(children[i], level,currentUserId,false));
			tr.appendChild(td);
		}
		var parentContainer = document.getElementById(person.id);
		if (document.getElementById("table_" + person.id) != undefined) {
			$("#table_" + person.id).remove();
		}
		table.setAttribute("id", "table_" + person.id);
		parentContainer.appendChild(table);
	}
}
function addPersonInfo(personInfo, level, loginUserId, showParentAction) {
	var personContainer = document.getElementById(personInfo.id);
	if (personContainer == null || personContainer == undefined) {
		personContainer = document.createElement("div");
		personContainer.id = personInfo.id;
		personContainer.setAttribute("class", "person");

		var personInfoContentDiv = document.createElement("div");
		personInfoContentDiv.setAttribute("class", "personContent");

		var personInfoDiv = document.createElement("div");
		personInfoDiv.setAttribute("class", "personInfo");

		var actionDiv = document.createElement("div");
		actionDiv.setAttribute("class", "personAction");

		personContainer.appendChild(personInfoContentDiv);
		personInfoContentDiv.appendChild(personInfoDiv);
		personInfoContentDiv.appendChild(actionDiv);

		var image = document.createElement("img");
		image.src = personInfo.image;
		image.setAttribute("id", "image_"+personInfo.id);
		image.setAttribute("class", "image");
		image.setAttribute("onclick", "openTimeline("+personInfo.id+")");
		
		
		// Build notify
		var notify = document.createElement("div");
		notify.setAttribute("id", "notify_"+personInfo.id);
		notify.setAttribute("data-toggle", "popover");
//		notify.setAttribute("title", "New feed");
		notify.setAttribute("style", "background-color: yellow;");
		// End building notify
		
		var link = document.createElement("a");
		link.href = "#";
		link.text = personInfo.firstName;
		$(link).attr("onclick", "openTimeline(" + personInfo.id + ")");
		$(link).attr("class", "name");
		personInfoDiv.appendChild(notify);
		personInfoDiv.appendChild(image);
		personInfoDiv.appendChild(document.createElement("br"));
		personInfoDiv.appendChild(link);

		if((personInfo.dad != 0 || personInfo.mom != 0) && showParentAction == true){
			var loadParentAction = document.createElement("a");
			loadParentAction.href = "#";
			loadParentAction.text = "Parent";
			$(loadParentAction).attr("onclick","loadParents(" + personInfo.id + "," + loginUserId + "," + personInfo.dad +"," + personInfo.mom +")");
			actionDiv.appendChild(loadParentAction);
			actionDiv.appendChild(document.createTextNode(" | "));
		}

		var loadChildren = document.createElement("a");
		loadChildren.href = "#";
		loadChildren.text = "Children";
		$(loadChildren).attr("onclick","loadMyChildren(" + personInfo.id + "," + level + ")");
		actionDiv.appendChild(loadChildren);
		
		if(loginUserId == personInfo.id || loginUserId == personInfo.ownerId){
			if(personInfo.mom == null || personInfo.mom == 0){
				var addMomAction = document.createElement("a");
				addMomAction.href = "#";
				addMomAction.text = "Add Mom";
				$(addMomAction).attr("onclick","addRelation(" + personInfo.id + ",'mom')");
				actionDiv.appendChild(document.createTextNode(" | "));
				actionDiv.appendChild(addMomAction);
			}
			if(personInfo.dad == null || personInfo.dad == 0){
				var addDadAction = document.createElement("a");
				addDadAction.href = "#";
				addDadAction.text = "Add Dad";
				$(addDadAction).attr("onclick","addRelation(" + personInfo.id + ",'dad')");
				actionDiv.appendChild(document.createTextNode(" | "));
				actionDiv.appendChild(addDadAction);
			}
			
			var addWifeHusbandAction = document.createElement("a");
			addWifeHusbandAction.href = "#";
			if(personInfo.gender == 'MALE'){
				addWifeHusbandAction.text = "+Wife";
				$(addWifeHusbandAction).attr("onclick","addRelation(" + personInfo.id + ",'wife')");
			}else{
				addWifeHusbandAction.text = "+Husband";
				$(addWifeHusbandAction).attr("onclick","addRelation(" + personInfo.id + ",'husband')");
			}
			actionDiv.appendChild(document.createTextNode(" | "));
			actionDiv.appendChild(addWifeHusbandAction);
			
			var addChildrenAction = document.createElement("a");
			addChildrenAction.href = "#";
			addChildrenAction.text = "+Kid";
			$(addChildrenAction).attr("onclick","addRelation(" + personInfo.id + ",'kid')");
			actionDiv.appendChild(document.createTextNode(" | "));
			actionDiv.appendChild(addChildrenAction);
		}

	}
	return personContainer;
}
function addRelation(id,type){//type is mom or dad
	$('#addParentForm').on('hidden.bs.modal', function (e) {
		//alert("ok");
	});
	$('#addParentForm').on('show.bs.modal', function (event) {
		//Fill data if needed
		$("#type").val(type);
		$("#personId").val(id);
		getFBFriends();
	});
	$('#addParentForm').modal("show");
}
function showProfile(id){
	$(id).popover('show');
}

function showNotify(id, msg){
	$(id).attr("data-content", msg);
	$(id).popover('show');
}
function openTimeline(id){
	window.location = "/timeline?id="+id;
}
function addChildNode(person, relationId){
	var personData = person;
	var personHTML = addPersonInfo(personData);
	var tr = document.getElementById("tr_" + relationId);
	if(tr == null || tr == undefined){
		var table = document.createElement("table");
		tr1 = document.createElement("tr");
		var td1 = document.createElement("td");
		td1.setAttribute("colspan", 1);
		td1.setAttribute("id", "td_" + relationId);
		td1.appendChild(document.createTextNode("|"));
		tr1.appendChild(td1);
		table.appendChild(tr1);
		tr = document.createElement("tr");
		tr.setAttribute("id", "tr_" + relationId);
		table.appendChild(tr);
		var parentContainer = document.getElementById(relationId);
		table.setAttribute("id", "table_" + relationId);
		parentContainer.appendChild(table);
	}
	var td = document.createElement("td");
	var seperator = document.createElement("div");
	seperator.setAttribute("class", "seperator");
	td.appendChild(seperator);
	td.appendChild(personHTML);
	td.setAttribute("class", "personContainer");
	tr.appendChild(td);
	var childNum = tr.childNodes.length;
	$("#td_" + relationId).attr("colspan",childNum);
	 for(i = 0 ;i < childNum; i++){
		if (childNum > 1) {
			if (i == 0) {
				tr.childNodes[i].childNodes[0].setAttribute("class", "seperator_right");
			} else if (i == (childNum - 1)) {
				tr.childNodes[i].childNodes[0].setAttribute("class", "seperator_left");
			} else {
				tr.childNodes[i].childNodes[0].setAttribute("class", "seperator");
			}
		} else if (tr.childNodes.length == 1) {
			tr.childNodes[i].childNodes[0].setAttribute("class", "seperator");
		}
	} 
}

function openSocket() {
	var socket = new SockJS("/feedNotifyEndpoint");
	var stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		stompClient.subscribe('/topic/newFeedNotify', function(newfeed) {
			data = jQuery.parseJSON(newfeed.body);
			if("feed" == data.type){
				addNotifyIcon("tlnotify","1");
				addNotifyMessage(data.ownerId,data.content);
			}
		});
	});
}
function addNotifyIcon(containerId, notifyContent){
	var span = document.getElementById("span_" + containerId);
	if(span == null || span == undefined){
		span = document.createElement("span");
		span.setAttribute("class", "badge");
		span.setAttribute("id", "span_" + containerId);
		span.setAttribute("style", "background-color: #F44336;");
		$("#"+containerId).append(span);
	}else{
		span.removeChild(span.firstChild);
	}
	span.appendChild(document.createTextNode(notifyContent));
	
}

function addNotifyMessage(containerId, notifyContent){
	if($("#image_" + containerId) == null || $("#image_" + containerId) == undefined)
		return;
	$("#image_" + containerId).attr("data-toggle", "popover");
	$("#image_" + containerId).attr("title", "New Feed");
	$("#image_" + containerId).attr("data-content", notifyContent);
	$("#image_" + containerId).popover('show');
}
function isUrl(text){
	var regex = new RegExp("^((https{0,1}|ftp|rtsp|mms){0,1}://){0,1}(([0-9a-z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]{1,}:\\ ){0,1}[0-9a-z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]{1,}@){0,1}(([0-9]{1,3}\\.){3,3}[0-9]{1,3}|([0-9a-z_!~\\*'\\(\\)\\-]{1,}\\.){0,}([0-9a-z][0-9a-z\\-]{0,61}){0,1}[0-9a-z]\\.[a-z]{2,6}|localhost)(:[0-9]{1,4}){0,1}((/{0,1})|(/[0-9a-z_!~\\*'\\(\\)\\.;\\?:@&=\\+\\$,%#\\-]{1,}){1,}/{0,1})$", "gi");
	var testString = ""; // fill this in
	var match = regex.exec(text)
	if(match != undefined && match.length > 0){
		return true;
	}
}
function getUrl(text){
	url = "";
	tmp = "";
	if(text != undefined && text.indexOf("https") >= 0){
		lastIndex = text.indexOf(" ",text.indexOf("https"));
		if(lastIndex <= 0)
			lastIndex = text.length;
		
		url = text.substring(text.indexOf("https"), lastIndex);
	}else if(text != undefined && text.indexOf("http") >= 0){
		lastIndex = text.indexOf(" ",text.indexOf("http"));
		if(lastIndex <= 0)
			lastIndex = text.length;
		url = text.substring(text.indexOf("http"),lastIndex);
	}
	if(url != "" && isUrl(url))
		return url;
	else
		return "";
}
function openLink(url){
	window.open(url);
}
function postFeed(){
	var url = getUrl($("#status").val());
	$("#postFeedForm").submit();
}
function loadFBFriends(profileId, dropdownlistId, fbfriends){
	var list = document.getElementById(dropdownlistId);
	if(list.getAttribute("loaded") != undefined)
		return;
	list.setAttribute("loaded","true");
	if (list != null || list != undefined) {
		for(i=0;i<fbfriends.length;i++){
			friend = fbfriends[i];
			li = document.createElement("li");
			a = document.createElement("a");
			text = document.createTextNode(friend.name);
			a.setAttribute("onclick","addRelationA("+profileId+", "+friend.facebookId+")");
			img = document.createElement("img");
			img.setAttribute("src", friend.image);
			img.setAttribute("class", "avatar");
			a.appendChild(img);
			a.appendChild(text);
			li.appendChild(a);
			list.appendChild(li);
		}
	}
}
function addRelationA(requestingId, requestedId){
	$.ajax({
		method : "GET",
		url : "/addrelation",
		data : {
			'personId' : $("#personId").val(),
			'relationId' :requestedId,
			'type' : $("#type").val()
		}
	}).done(function(result) {
		if(result.errorCode == 1){
			alert(result.errorCode + result.msg);
		} else if(result.errorCode == 0){
			alert(result.errorCode + result.msg);
			$('#addParentForm').modal("hide");
		}else if ("kid" == result.type) {
			addChildNode(result.relation, result.monOrDadId);
			$('#addParentForm').modal("hide");
		} else {//no thing to do with add wife or husband
			$('#addParentForm').modal("hide");
		}
	});
}
function action(id, actionUserId, userIds,type,controlId){
	if(userIds.indexOf(","+actionUserId) > 0 || userIds == actionUserId){
		alert("already like");
		return;
	}
	$.ajax({
		method : "GET",
		url : "/action",
		data : {
			'itemId' : id,
			'type' :type
		}
	}).done(function(result) {
		if(result.errorCode == 1){
			alert(result.errorCode + result.msg);
		} else if(result.errorCode == 0){
			if("l" == type){
				var count = parseInt($("#likeCount_"+id).html())+1;
				$("#likeCount_"+id).html(count);
				document.getElementById("likeAct_"+id).removeAttribute("href");
				document.getElementById("likeAct_"+id).removeAttribute("onclick");
				document.getElementById("likeAct_"+id).setAttribute("style","color: #5890ff;");
			}else if ("s" == type){
				var count = parseInt($("#shareCount_"+id).html())+1;
				$("#shareCount_"+id).html(count);
				document.getElementById("shareAct_"+id).removeAttribute("href");
				document.getElementById("shareAct_"+id).removeAttribute("onclick");
				document.getElementById("shareAct_"+id).setAttribute("style","color: #5890ff;");
			}

		}
	});
}
function getProfiles(profileIds, type){
	if(profileIds == "" && profileIds == undefined){
		return;
	}
	$.ajax({
		method : "GET",
		url : "/profiles",
		data : {
			'profileIds' : profileIds
		}
	}).done(function(result) {
		if(result.errorCode == null || result.errorCode == undefined ){
			var data ={supplies:result};
			var html = new EJS({url: '/js/ejs/templates/listprofiles.ejs'}).render(data);
			if('like' == type)
				$("#title").html("People who like this");
			if('share' == type)
				$("#title").html("People who share this");
			if('comment' == type)
				$("#title").html("People who comment on this");
			
			$("#popupContent").html(html);
			$('#profileListContainer').modal('toggle');
		}
	});
}
function postComment(feedId, replyCommentId, commentInputId, commentContainterId, emoticonId){
	$.ajax({
		method : "POST",
		url : "/comment",
		data : {
			'feedId' : feedId,
			'replyCommentId' : 0,
			'content' : $("#"+commentInputId).val(),
			'emoticon' : emoticonId
		}
	}).done(function(result) {
		var div =document.createElement("div");
		div.setAttribute("class", "w3-row");
		container = document.getElementById(commentContainterId);
		container.appendChild(div);
		var data ={cmt:result};
		var html = new EJS({url: '/js/ejs/templates/comment.ejs'}).render(data);
		div.innerHTML  = html;
		var count = parseInt($("#commentCount_"+feedId).html())+1;
		 $("#"+commentInputId).val("");
		$("#commentCount_"+feedId).html(count);
	});
}

function postCommentM(feedId, replyCommentId, commentInputId, commentContainterId, emoticonId){
	$.ajax({
		method : "POST",
		url : "/comment",
		data : {
			'feedId' : feedId,
			'replyCommentId' : 0,
			'content' : $("#"+commentInputId).val(),
			'emoticon' : emoticonId
		}
	}).done(function(result) {
		var div =document.createElement("div");
		div.setAttribute("class", "w3-row");
		container = document.getElementById(commentContainterId);
		container.appendChild(div);
		var data ={cmt:result};
		var template = "<span class='profile_comment'>{{cmt.profileName }}</span>";
		var html = Mustache.to_html(template, data);
		div.innerHTML  = html;
		var count = parseInt($("#commentCount_"+feedId).html())+1;
		 $("#"+commentInputId).val("");
		$("#commentCount_"+feedId).html(count);
	});
}
