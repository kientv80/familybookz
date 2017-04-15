var zasearch = {};
//
zasearch.searchPages = function(inputId, tbContentId, pagingId, curpage, pageSize){	
	var certify = $("#certify_hidden").val();
	var pageStatus = $("#pageStatus_hidden").val();
	var pageOption = $("#pageOptions_hidden").val();
	var cateId = $('#currentCate_hidden').val();
	if($("#"+inputId).val().trim() != "" || (cateId != undefined && cateId != "10")
		|| (certify != undefined && certify != "0") || (pageStatus != undefined && pageStatus != "0")
        ||  (pageOption != undefined && pageOption != "0")){	
		if(curpage<1){
			curpage = 1;
		}
		var offset = (curpage-1)*pageSize;
		var param ={};
		param.query = $("#"+inputId).val().trim();
		param.from=offset;
		param.num = pageSize;
		param.isHidden = true;
		
		if(cateId != undefined && cateId != "10"){
			param.cateid = parseInt(cateId,10);
		}
		if(certify != undefined && certify != "0"){
			param.cer = (certify == "1")? true : false;
		}
		
		if(pageStatus != undefined && pageStatus != "0"){
			param.status = parseInt(pageStatus,10);
		}
		if(pageOption != undefined && pageOption != "0"){
			if(pageOption == "1"){
				param.searchable = 1;
			}else if(pageOption == "2"){
				param.browsable = 1;
			}else if(pageOption == "3"){
				param.searchable = 2;
			}
		}
                alert("Search");
		$.ajax({
			url : zaglobal.searchDomain+'/pagesearch',  
			type: "get",
			crossDomain: true,
			dataType: 'jsonp',			
			data: param, 
			success: function(rsData){					
				var html = '';
				$("#"+pagingId).css("display",'none');
				
				if (rsData.result == true && rsData.total > 0 ) {
					$("#numberOfPage").html("Number Of Page: " + rsData.total);
					var listId = "";
					for(var i = 0; i < rsData.data.length;i++){
						if(i==0){
							listId += rsData.data[i].id;
						}
						else{
							listId += ","+rsData.data[i].id;
						}
					}
					$.ajax({
						url : zaglobal.domain+'/admin/ajax?option=map_pid',
						type : "post",
						dataType: 'json',
						data:{"data": listId},
						success: function(mapData){
							// call get data getdata(obj.value);
							var inviteFan = '<li style="margin-right:4px;padding:0">\n\
										<a href="{{domain}}/admin/pages/invite_fan?pageid={{pageid_rd}}" title="Invite Fan">\n\
											<i class="splashy-contact_blue_add"></i>\n\
										</a>\n\
									</li>';
							var assign = '<li style="margin-right:4px;padding:0">\n\
										<a href="{{domain}}/admin/pages/assign?pageid={{pageid_rd}}" title="Assign">\n\
											<i class="splashy-group_grey_add"></i>\n\
										</a>\n\
									</li>';
							var moveTo = '<li style="margin-right:4px;padding:0">\n\
										<a title="Move to" data-cateid="{{cateid}}" data-pageid="{{pageid}}" data-pagename="{{pagename}}" data-toggle="modal" href="#modalMoveTo" onclick="updatePagePosition(this);">\n\
											<i class="splashy-arrow_medium_upper_right"></i>\n\
										</a>\n\
									</li>';
							var quota = '<li style="margin-right:4px;padding:0">\n\
										<a title="Quota" href="{{quotaType}}">\n\
											<i class="splashy-box_add"></i>\n\
										</a>\n\
									</li>';
							var apiquota = '{{domain}}/admin/pages/apiquota?pageid={{pageid_rd}}';
							var pagequota = '{{domain}}/admin/pages/pagequota?pageid={{pageid_rd}}';
							var login = '<li style="margin-right:4px;padding:0">\n\
									<a title="Login page" href="{{domain}}/choose?pageid={{pageid}}">\n\
										<img src="{{domain}}/static/images/icon_login.png" width="16px" height="16px">\n\
                                        <!--<i class="splashy-application_windows_edit"></i>-->\n\
									</a>\n\
								</li>';
                            var userlog = '<li style="margin-right:4px;padding:0">\n\
									<a title="User logs" href="{{domain}}/admin/pages/userlog?pageid={{pageid_rd}}">\n\
										<img src="{{domain}}/static/images/icon_logs.png" width="16px" height="16px">\n\
                                        <!--<i class="splashy-pencil_small"></i>-->\n\
									</a>\n\
								</li>'; 
							 var adminfeed = '<li style="margin-right:4px;padding:0">\n\
									<a title="Feed" href="{{domain}}/admin/pages/feeds?pageid={{pageid}}">\n\
										<img src="{{domain}}/static/images/icon_feed.png" width="16px" height="16px">\n\
                                        <!--<i class="splashy-pencil_small"></i>-->\n\
									</a>\n\
								</li>'; 
                            
							var tmp = '<tr>\n\
							<td>{{num}}</td>\n\
							<td>{{pageid_rd}}</td>\n\
							<td>{{pagename}}{{islock}}{{isCer}}</td>\n\\n\
							<td>{{approvedDate}}</td>\n\\n\
							<td>{{updatedDate}}</td>\n\\n\
							<td>{{actionDate}}</td>\n\
							<td>{{searchable}}</td>\n\
							<td>{{browsable}}</td>\n\
                            <td>\n\
                                <ul style="margin:0" class="icon_list_a clearfix">\n\
									<li style="margin-right:4px;padding:0">\n\
									<a title="Edit" href="{{domain}}/admin/pages?option=detail&pageid={{pageid_rd}}">\n\
										<i class="splashy-document_letter_edit"></i>\n\
									</a>\n\
									</li>								\n\
									{{inviteFan}}	\n\
									{{assign}}\n\
									{{moveTo}}\n\
									{{quota}}\n\
									{{login}}\n\
                                    {{userlog}}\n\
                                    {{adminfeed}}\n\
								</ul>\n\
                            </td>\n\
						</tr>';
							var privileges = $("#privileges").val().split(",");
							if(privileges.indexOf("inviteFan") >= 0){
								tmp = tmp.replace(/{{inviteFan}}/g, inviteFan);
							}else{
								tmp = tmp.replace(/{{inviteFan}}/g, ""); 
							}
							if(privileges.indexOf("assign") >= 0){
								tmp = tmp.replace(/{{assign}}/g, assign);
							}else{
								tmp = tmp.replace(/{{assign}}/g, ""); 
							}
							if(privileges.indexOf("moveTo") >= 0){
								tmp = tmp.replace(/{{moveTo}}/g, moveTo);
							}else{
								tmp = tmp.replace(/{{moveTo}}/g, ""); 
							}
							if(privileges.indexOf("pagequota") >= 0 || privileges.indexOf("apiquota") >= 0){
								if(privileges.indexOf("pagequota") >= 0){
									quota = quota.replace(/{{quotaType}}/g, apiquota);
								}else{
									quota = quota.replace(/{{quotaType}}/g, pagequota);
								}
								tmp = tmp.replace(/{{quota}}/g, quota);
							}else{
								tmp = tmp.replace(/{{quota}}/g, ""); 
							}
							if(privileges.indexOf("login") >= 0){
								tmp = tmp.replace(/{{login}}/g, login);
							}else{
								tmp = tmp.replace(/{{login}}/g, ""); 
							}
                            if(privileges.indexOf("userlog") >= 0){
								tmp = tmp.replace(/{{userlog}}/g, userlog);
							}else{
								tmp = tmp.replace(/{{userlog}}/g, ""); 
							}
							if(privileges.indexOf("adminfeed") >= 0){
								tmp = tmp.replace(/{{adminfeed}}/g, adminfeed);
							}else{
								tmp = tmp.replace(/{{adminfeed}}/g, ""); 
							}
							var paging = {};
							paging.curr = curpage;
							paging.pageSize = pageSize;
							paging.totalRecord = rsData.total;
					
							var count = (paging.curr-1) * paging.pageSize;
							for(var index=0;index< rsData.data.length;++index){
								var page = rsData.data[index];	
								var item =tmp;
								item = item.replace(/{{domain}}/g,zaglobal.domain);
								item = item.replace(/{{num}}/,++count);						
								item = item.replace(/{{pageid}}/g, page.id);
								if(mapData != null && mapData["map"][page.id] != null ){
									item = item.replace(/{{pageid_rd}}/g, mapData["map"][page.id]);
								}else{
									item = item.replace(/{{pageid_rd}}/g, page.id);
								}
								item = item.replace(/{{pagename}}/g, zasearch.htmlEncoder(page.pagename));
								var crDate = zasearch.getTimeLineText(page.createdttm);
								item = item.replace(/{{approvedDate}}/g,crDate);
								var upDate = zasearch.getTimeLineText(page.updateddttm);
								item = item.replace(/{{updatedDate}}/g,upDate);
								var actionDate = parseFloat(mapData["lastActionMap"][page.id]);
								if(actionDate <= 0){
									actionDate = page.createdttm;
								}
								actionDate = zasearch.getTimeLineText(actionDate);
								item=item.replace(/{{actionDate}}/g,actionDate);
								if(page.status != 1){							
									item = item.replace(/{{islock}}/, '&nbsp;<i class="splashy-lock_small_locked"></i>');
								}else{
									item = item.replace(/{{islock}}/, '');
								}
								if(page.cer == true){
									item = item.replace(/{{isCer}}/, '&nbsp;<i class="splashy_small_certificate"></i>');
								}
								else{
									item = item.replace(/{{isCer}}/, '');
								}
						
								if(page.searchable == 1){
									item = item.replace(/{{searchable}}/, '<i class="splashy-check"></i>');
								}else{
									item = item.replace(/{{searchable}}/, '<i class="splashy-gem_remove"></i>');
								}
						
								if(page.browsable == 1){
									item = item.replace(/{{browsable}}/, '<i class="splashy-check"></i>');
								}else{
									item = item.replace(/{{browsable}}/, '<i class="splashy-gem_remove"></i>');
								}
			
								var cateIds = page.cateid;
								var cateId = 10;
								for(var id=0;id<cateIds.length;++id){
									cateId = cateIds[id];
									break;
								}						
								item = item.replace(/{{cateid}}/, cateId);
						
								html+= item;
						
							}					
					
							var param = "{'currentPage':"+paging.curr+"\
								,'pageSize':"+paging.pageSize+"\n\
								,'numDisplay':"+5+"\n\
								,'totalRecord':"+paging.totalRecord+"\n\
								,'action':\"zasearch.searchPages('"+inputId+"','"+tbContentId+"','"+pagingId+"',currPage,pageSize)\"}";
							$("#"+pagingId).html(param);
							zacommon.renderPagingSearchAjax(pagingId, "", "", "", "");
							$("#"+tbContentId +" tbody").html(html);
						}
					});					
				}else{
					$("#numberOfPage").html("Number Of Page: 0");
					html = "<tr>\n\
								<td valign='middle' colspan='9'>\n\
									Not found data\n\
								</td>\n\
							</tr>";					
					$("#"+pagingId).css("display","none");
					$("#"+pagingId).html("");
					$("#"+tbContentId +" tbody").html(html);
				}
			}
		});	
	}	
}

zasearch.searchAgencyPages = function(inputId, tbContentId, pagingId, curpage, pageSize){
	var cateId = $('#currentCate_hidden').val();
	var cateIdTrue = $('#currentCate_hidden').val();
	if(cateId == undefined || cateId == 0){
	    cateId = 10;
	}
	if($("#"+inputId).val().trim() != "" || (cateId != undefined && cateId != "10")){
		if(curpage<1){
			curpage = 1;
		}
		var offset = (curpage-1)*pageSize;
		var param ={};
		param.query = $("#"+inputId).val().trim();
		param.from=offset;
		param.num = pageSize;
		param.isHidden = true;
		
		if(cateId != undefined && cateId != "10"){
			param.cateid = parseInt(cateId,10);
		}
		$.ajax({
			url : zaglobal.searchDomain+'/pagesearch',  
			type: "get",
			crossDomain: true,
			dataType: 'jsonp',
			data: param, 
			success: function(rsData){
				var html = '';
				$("#"+pagingId).css("display",'none');
				
				if (rsData.result == true && rsData.total > 0 ) {
					var listId = "";
					for(var i = 0; i < rsData.data.length;i++){
						if(i==0){
							listId += rsData.data[i].id;
						}
						else{
							listId += ","+rsData.data[i].id;
						}
					}
				  
					var paramFilter ={};
					paramFilter.data = listId;
					paramFilter.cateId = cateIdTrue;
					$.ajax({
						url : zaglobal.domain+'/admin/ajax?option=map_pid_agency',
						type : "post",
						dataType: 'json',
						data:paramFilter,
						success: function(mapData){
							// call get data getdata(obj.value);
							var tmp = '<tr>\n\
							<td>{{num}}</td>\n\
							<td>{{pageid_rd}}</td>\n\
							<td>{{pagename}}{{islock}}{{isCer}}</td>\n\\n\
							<td>{{assigndate}}</td>\n\
							<td>{{searchable}}</td>\n\
							<td>{{browsable}}</td>\n\
                            <td>\n\
                                <ul style="margin:0" class="icon_list_a clearfix">\n\
									<li style="margin-right:4px;padding:0">\n\
									<a title="Edit" href="{{domain}}/agency/agencymanagement?option=detail&pageid={{pageid_rd}}">\n\
										<i class="splashy-document_letter_edit"></i>\n\
									</a>\n\
									</li>\n\
									<li style="margin-right:4px;padding:0">\n\
										<a href="{{domain}}/agency/agencymanagement?option=assign&pageid={{pageid_rd}}" title="Assign">\n\
											<i class="splashy-group_grey_add"></i>\n\
										</a>\n\
									</li>\n\
									<li style="margin-right:4px;padding:0">\n\
									<a title="Login page" href="{{domain}}/choose?pageid={{pageid}}">\n\
										<i class="splashy-application_windows_edit"></i>\n\
									</a>\n\
									<li style="margin-right:4px;padding:0">\n\
									    <a class="reject-dialog" data-toggle="modal" href="#modalRemove" id="removeIcon" href="#" title="Remove" onclick="removePage({{pageid}}, {{agencyid}})">\n\
										<i class="icon-trash"></i>\n\
									    </a>\n\
									</li>\n\
								</li>\n\
								</ul>\n\
                            </td>\n\
						</tr>';
							var paging = {};
							paging.curr = curpage;
							paging.pageSize = pageSize;
							paging.totalRecord = mapData.totalPages;
							//console.log("mapData.totalPages: " + mapData.totalPages);
							var count = (paging.curr-1) * paging.pageSize;
							
							$("#numberOfPage").html("Number Of Page: " + mapData.totalPages);
							
							if(mapData != null && mapData.map != null){
							      for(var index=0;index< rsData.data.length;++index){
								    var page = rsData.data[index];
								    if(mapData["map"][page.id] != null){
									var item =tmp;
									item = item.replace(/{{domain}}/g,zaglobal.domain);
									item = item.replace(/{{num}}/,++count);
									item = item.replace(/{{pageid}}/g, page.id);
									if(mapData != null && mapData["map"][page.id] != null ){
										item = item.replace(/{{pageid_rd}}/g, mapData["map"][page.id]);
									}else{
										item = item.replace(/{{pageid_rd}}/g, page.id);
									}
									item = item.replace(/{{pagename}}/g, zasearch.htmlEncoder(page.pagename));
									if(page.status != 1){
										item = item.replace(/{{islock}}/, '&nbsp;<i class="splashy-lock_small_locked"></i>');
									}else{
										item = item.replace(/{{islock}}/, '');
									}
									if(page.cer == true){
										item = item.replace(/{{isCer}}/, '&nbsp;<i class="splashy_small_certificate"></i>');
									}
									else{
										item = item.replace(/{{isCer}}/, '');
									}
							
									if(page.searchable == 1){
										item = item.replace(/{{searchable}}/, '<i class="splashy-check"></i>');
									}else{
										item = item.replace(/{{searchable}}/, '<i class="splashy-gem_remove"></i>');
									}
							
									if(page.browsable == 1){
										item = item.replace(/{{browsable}}/, '<i class="splashy-check"></i>');
									}else{
										item = item.replace(/{{browsable}}/, '<i class="splashy-gem_remove"></i>');
									}
									
									if(mapData != null && mapData["mapAssignDate"][page.id] != null ){
										item = item.replace(/{{assigndate}}/g, mapData["mapAssignDate"][page.id]);
									}
									
									if(mapData != null && mapData["mapAgency"][page.id] != null ){
										item = item.replace(/{{agencyid}}/g, mapData["mapAgency"][page.id]);
									}
									
									html+= item;
								    }
							      }
							      
							      var param = "{'currentPage':"+paging.curr+"\
								      ,'pageSize':"+paging.pageSize+"\n\
								      ,'numDisplay':"+5+"\n\
								      ,'totalRecord':"+paging.totalRecord+"\n\
								      ,'action':\"zasearch.searchAgencyPages('"+inputId+"','"+tbContentId+"','"+pagingId+"',currPage,pageSize)\"}";
							      $("#"+pagingId).html(param);
							      zacommon.renderPagingSearchAjax(pagingId, "", "", "", "");
							      //zacommon.renderPagingSearch2('paging','','','','','&cate='+cateIdTrue);
							      $("#"+tbContentId +" tbody").html(html);
							} else{
							      $("#numberOfPage").html("Number Of Page: 0");
							      html = "<tr>\n\
									      <td valign='middle' colspan='9'>\n\
										      Not found data\n\
									      </td>\n\
								      </tr>";
							      $("#"+pagingId).css("display","none");
							      $("#"+pagingId).html("");
							      $("#"+tbContentId +" tbody").html(html);
							}
						}
					});
				}else{
					$("#numberOfPage").html("Number Of Page: 0");
					html = "<tr>\n\
							<td valign='middle' colspan='9'>\n\
								Not found data\n\
							</td>\n\
						</tr>";
					$("#"+pagingId).css("display","none");
					$("#"+pagingId).html("");
					$("#"+tbContentId +" tbody").html(html);
				}
			}
		});
	}	
}

zasearch.searchPageRegister = function(status, inputId, tbContentId, pagingId, curpage, pageSize){	
	if($("#"+inputId).val().trim() != ""){	
		if(curpage<1){
			curpage = 1;
		}
		var offset = (curpage-1)*pageSize;		
		var param ={};
		param.page_name = $("#"+inputId).val().trim();
		param.from=offset;
		param.num = pageSize;
		param.status = status;
		param.option = 'search';
		$.ajax({
			url : zaglobal.domain+'/admin/register/action',  
			type: "get",			
			dataType: 'json',			
			data: param, 
			success: function(rsData){					
				var html = '';
				$("#"+pagingId).css("display",'none');
				
				if (rsData.result == true && rsData.total > 0 ) {
					// call get data getdata(obj.value);
					var tmp = '<tr>\n\
							<td><input type="checkbox" value="{{registerID}}" onchange="removeCheckAll()"/></td>\n\
							<td>{{No.}}</td>\n\
							<td>{{createTime}}</td>\n\
							<td>{{updateTime}}</td>\n\
                            <td>{{pageName}}</td>\n\
							<td>{{contactName}}</td>\n\
                            <td>{{description}}</td>\n\\n\
							<td>{{action}}</td>\n\
						</tr>';
					var paging = {};
					paging.curr = curpage;
					paging.pageSize = pageSize;
					paging.totalRecord = rsData.total;
					
					var count = (paging.curr-1) * paging.pageSize;
					for(var index=0;index< rsData.data.length;++index){
						var page = rsData.data[index];	
						var item =tmp;
						var statusSpending = '<a class="reject-dialog" data-toggle="modal" href="#modalReject" id="rejectIcon_{{registerID}}" href="#" rejectid="{{registerID}}" title="Reject" onclick="showNumber({{registerID}})">\n\
												<i class="icon-trash"></i><span>Reject</span></a>';
						statusSpending = statusSpending.replace(/{{registerID}}/g, page.id);
						
						var action = '<a class="approve-dialog" data-toggle="modal" href="#myModal" id="approveIcon_{{registerID}}" approveid="{{registerID}}" title="Approve" onclick="showNumber({{registerID}})">\n\
										<i class="icon-pencil"></i>\n\
										<span>Approve</span>\n\
									</a>\n\
									{{statusSpending}}\n\
									<a href="{{domain}}/admin/register?option=detail&regid={{registerID}}" class="detail-dialog-icon" title="View Detail" detailid="{{registerID}}">\n\
										<i class="icon-eye-open"></i>\n\
										<span>View Details</span>\n\
									</a>';
						var actionTMPDontRemove = '{{statusSpending}}\n\
									<a href="{{domain}}/admin/register?option=detail&regid={{registerID}}" class="detail-dialog-icon" title="View Detail" detailid="{{registerID}}">\n\
										<i class="icon-eye-open"></i>\n\
										<span>View Details</span>\n\
									</a>';
						action = action.replace(/{{registerID}}/g, page.id);
						action = action.replace(/{{domain}}/g,zaglobal.domain);
						if(page.status == 0){							
							action = action.replace(/{{statusSpending}}/, statusSpending);
						}else if(page.status == 2){
							action = action.replace(/{{statusSpending}}/, "");
						}	
						
						item = item.replace(/{{No.}}/,++count);
						item = item.replace(/{{registerID}}/g, page.id);
						item = item.replace(/{{createTime}}/g, page.create_time);
						item = item.replace(/{{updateTime}}/g, page.update_time);
						item = item.replace(/{{pageName}}/g, zasearch.htmlEncoder(page.page_name));
						item = item.replace(/{{contactName}}/,page.contact_name);
						item = item.replace(/{{description}}/,page.description);
						item = item.replace(/{{action}}/,action);
						
						html+= item;
						
					}					
					
					var param = "{'currentPage':"+paging.curr+"\
								,'pageSize':"+paging.pageSize+"\n\
								,'numDisplay':"+5+"\n\
								,'totalRecord':"+paging.totalRecord+"\n\
								,'action':\"zasearch.searchPageRegister('"+status+"','"+inputId+"','"+tbContentId+"','"+pagingId+"',currPage,pageSize)\"}";
					$("#"+pagingId).html(param);
					zacommon.renderPagingSearchAjax(pagingId, "", "", "", "");
										
				}else{
					html = "<tr>\n\
								<td valign='middle' colspan='9'>\n\
									Not found data\n\
								</td>\n\
							</tr>";					
					$("#"+pagingId).css("display","none");
					$("#"+pagingId).html("");
				}
				$("#"+tbContentId +" tbody").html(html);
			}
		});	
	}	
}

zasearch.searchPageAgencyRegister = function(status, inputId, tbContentId, pagingId, curpage, pageSize){	
	if($("#"+inputId).val().trim() != ""){	
		if(curpage<1){
			curpage = 1;
		}
		var offset = (curpage-1)*pageSize;		
		var param ={};
		param.page_name = $("#"+inputId).val().trim();
		param.from=offset;
		param.num = pageSize;
		param.status = status;
		param.option = 'search';
		$.ajax({
			url : zaglobal.domain+'/agency/register/action',  
			type: "get",
			dataType: 'json',
			data: param,
			success: function(rsData){
				var html = '';
				$("#"+pagingId).css("display",'none');
				
				if (rsData.result == true && rsData.total > 0 ) {
					// call get data getdata(obj.value);
					var tmp = '<tr>\n\
							<td><input type="checkbox" value="{{registerID}}" onchange="removeCheckAll()"/></td>\n\
							<td>{{No.}}</td>\n\
							<td>{{pageName}}</td>\n\
							<td>{{contactName}}</td>\n\
							<td>{{description}}</td>\n\
							<td>{{statusPage}}</td>\n\\n\
							<td>{{action}}</td>\n\
						</tr>';
					var paging = {};
					paging.curr = curpage;
					paging.pageSize = pageSize;
					paging.totalRecord = rsData.total;
					
					var count = (paging.curr-1) * paging.pageSize;
					for(var index=0;index< rsData.data.length;++index){
						var page = rsData.data[index];	
						var item =tmp;
						var statusSpending = '<a class="approve-dialog" data-toggle="modal" href="#myModal" id="approveIcon_{{registerID}}" approveid="{{registerID}}" title="Approve" onclick="showNumber({{registerID}})">\n\
										<i class="icon-pencil"></i>\n\
										<span>Approve</span>\n\
									</a>';
						statusSpending = statusSpending.replace(/{{registerID}}/g, page.id);
						
						var action = '{{statusSpending}}\n\
									<a class="reject-dialog" data-toggle="modal" href="#modalReject" id="rejectIcon_{{registerID}}" href="#" rejectid="{{registerID}}" title="Reject" onclick="showNumber({{registerID}})">\n\
										<i class="icon-trash"></i><span>Reject</span></a>\n\
									<a href="{{domain}}/agency/register?option=detail&regid={{registerID}}" class="detail-dialog-icon" title="View Detail" detailid="{{registerID}}">\n\
										<i class="icon-eye-open"></i>\n\
										<span>View Details</span>\n\
									</a>';
						var actionTMPDontRemove = '{{statusSpending}}\n\
									<a href="{{domain}}/agency/register?option=detail&regid={{registerID}}" class="detail-dialog-icon" title="View Detail" detailid="{{registerID}}">\n\
										<i class="icon-eye-open"></i>\n\
										<span>View Details</span>\n\
									</a>';
						action = action.replace(/{{registerID}}/g, page.id);
						action = action.replace(/{{domain}}/g,zaglobal.domain);
						if(page.status == 0){
							action = action.replace(/{{statusSpending}}/, statusSpending);
						}else if(page.status == 2){
							action = action.replace(/{{statusSpending}}/, "");
						}
						
						item = item.replace(/{{No.}}/,++count);
						item = item.replace(/{{registerID}}/g, page.id);
						item = item.replace(/{{pageName}}/g, zasearch.htmlEncoder(page.page_name));
						item = item.replace(/{{contactName}}/,page.contact_name);
						item = item.replace(/{{description}}/,page.description);
						if(page.status == 0){
							item = item.replace(/{{statusPage}}/,"Pending");
						}else if(page.status == 2){
							item = item.replace(/{{statusPage}}/,"Rejected");
						}
						item = item.replace(/{{action}}/,action);
						
						html+= item;
						
					}
					
					var param = "{'currentPage':"+paging.curr+"\
								,'pageSize':"+paging.pageSize+"\n\
								,'numDisplay':"+5+"\n\
								,'totalRecord':"+paging.totalRecord+"\n\
								,'action':\"zasearch.searchPageAgencyRegister('"+status+"','"+inputId+"','"+tbContentId+"','"+pagingId+"',currPage,pageSize)\"}";
					$("#"+pagingId).html(param);
					zacommon.renderPagingSearchAjax(pagingId, "", "", "", "");
				  
				  
				/*
					var listId = "";
					for(var i = 0; i < rsData.data.length;i++){
						if(i==0){
							listId += rsData.data[i].id;
						}
						else{
							listId += ","+rsData.data[i].id;
						}
					}
				  
					var paramFilter ={};
					paramFilter.data = listId;
					paramFilter.status = status;
					$.ajax({
						url : zaglobal.domain+'/admin/ajax?option=map_reg_agency',
						type : "post",
						dataType: 'json',
						data:paramFilter,
						success: function(mapData){
							// call get data getdata(obj.value);
							var tmp = '<tr>\n\
									    <td><input type="checkbox" value="{{registerID}}" onchange="removeCheckAll()"/></td>\n\
									    <td>{{No.}}</td>\n\
									    <td>{{pageName}}</td>\n\
									    <td>{{contactName}}</td>\n\
									    <td>{{description}}</td>\n\
									    <td>{{statusPage}}</td>\n\\n\
									    <td>{{action}}</td>\n\
								    </tr>';
							
							
							var paging = {};
							paging.curr = curpage;
							paging.pageSize = pageSize;
							paging.totalRecord = mapData.totalPages;
							//console.log("mapData.totalPages: " + mapData.totalPages);
							var count = (paging.curr-1) * paging.pageSize;
							
							//$("#numberOfPage").html("Number Of Page: " + mapData.totalPages);
							
							if(mapData != null && mapData.map != null){
							      for(var index=0;index< rsData.data.length;++index){
								    var page = rsData.data[index];
								    if(mapData["map"][page.id] != null){
									var item =tmp;
									var statusSpending = '<a class="reject-dialog" data-toggle="modal" href="#modalReject" id="rejectIcon_{{registerID}}" href="#" rejectid="{{registerID}}" title="Reject" onclick="showNumber({{registerID}})">\n\
											      <i class="icon-trash"></i><span>Reject</span></a>';
									statusSpending = statusSpending.replace(/{{registerID}}/g, page.id);
									
									var action = '<a class="approve-dialog" data-toggle="modal" href="#myModal" id="approveIcon_{{registerID}}" approveid="{{registerID}}" title="Approve" onclick="showNumber({{registerID}})">\n\
												<i class="icon-pencil"></i>\n\
												<span>Approve</span>\n\
											</a>\n\
											{{statusSpending}}\n\
											<a href="{{domain}}/agency/register?option=detail&regid={{registerID}}" class="detail-dialog-icon" title="View Detail" detailid="{{registerID}}">\n\
												<i class="icon-eye-open"></i>\n\
												<span>View Details</span>\n\
											</a>';
									var actionTMPDontRemove = '{{statusSpending}}\n\
												<a href="{{domain}}/agency/register?option=detail&regid={{registerID}}" class="detail-dialog-icon" title="View Detail" detailid="{{registerID}}">\n\
													<i class="icon-eye-open"></i>\n\
													<span>View Details</span>\n\
												</a>';
									action = action.replace(/{{registerID}}/g, page.id);
									action = action.replace(/{{domain}}/g,zaglobal.domain);
									if(page.status == 0){
										action = action.replace(/{{statusSpending}}/, statusSpending);
									}else if(page.status == 2){
										action = action.replace(/{{statusSpending}}/, "");
									}
									
									item = item.replace(/{{No.}}/,++count);
									item = item.replace(/{{registerID}}/g, page.id);
									item = item.replace(/{{pageName}}/g, zasearch.htmlEncoder(page.page_name));
									item = item.replace(/{{contactName}}/,page.contact_name);
									item = item.replace(/{{description}}/,page.description);
									if(page.status == 0){
										item = item.replace(/{{statusPage}}/,"Pending");
									}else if(page.status == 2){
										item = item.replace(/{{statusPage}}/,"Rejected");
									}
									item = item.replace(/{{action}}/,action);
									
									html+= item;
								    }
							      }
							      
							      var param = "{'currentPage':"+paging.curr+"\
								      ,'pageSize':"+paging.pageSize+"\n\
								      ,'numDisplay':"+5+"\n\
								      ,'totalRecord':"+paging.totalRecord+"\n\
								      ,'action':\"zasearch.searchPageAgencyRegister('"+inputId+"','"+tbContentId+"','"+pagingId+"',currPage,pageSize)\"}";
							      $("#"+pagingId).html(param);
							      zacommon.renderPagingSearchAjax(pagingId, "", "", "", "");
							      $("#"+tbContentId +" tbody").html(html);
							} else{
							      $("#numberOfPage").html("Number Of Page: 0");
							      html = "<tr>\n\
									      <td valign='middle' colspan='9'>\n\
										      Not found data\n\
									      </td>\n\
								      </tr>";
							      $("#"+pagingId).css("display","none");
							      $("#"+pagingId).html("");
							      $("#"+tbContentId +" tbody").html(html);
							}
						}
					});
				*/
				}else{
					html = "<tr>\n\
							<td valign='middle' colspan='9'>\n\
								Not found data\n\
							</td>\n\
						</tr>";
					$("#"+pagingId).css("display","none");
					$("#"+pagingId).html("");
				}
				$("#"+tbContentId +" tbody").html(html);
			}
		});	
	}	
}

zasearch.searchPageRequestAgency = function(status, inputId, tbContentId, pagingId, curpage, pageSize){	
	if($("#"+inputId).val().trim() != ""){	
		if(curpage<1){
			curpage = 1;
		}
		var offset = (curpage-1)*pageSize;		
		var param ={};
		param.page_name = $("#"+inputId).val().trim();
		param.from=offset;
		param.num = pageSize;
		param.status = status;
		param.option = 'search';
		$.ajax({
			url : zaglobal.domain+'/agency/requestmanagement/action',  
			type: "get",
			dataType: 'json',
			data: param,
			success: function(rsData){
				var html = '';
				$("#"+pagingId).css("display",'none');
				
				if (rsData.result == true && rsData.total > 0 ) {
					// call get data getdata(obj.value);
					var tmp = '<tr>\n\
							<td>{{No.}}</td>\n\
							<td>{{pageName}}</td>\n\
							<td>{{cateName}}</td>\n\
							<td>{{contactName}}</td>\n\
							<td>{{description}}</td>\n\
							<td>{{statusPage}}</td>\n\\n\
							<td>{{action}}</td>\n\
						</tr>';
					var paging = {};
					paging.curr = curpage;
					paging.pageSize = pageSize;
					paging.totalRecord = rsData.total;
					
					var count = (paging.curr-1) * paging.pageSize;
					for(var index=0;index< rsData.data.length;++index){
						var page = rsData.data[index];	
						var item =tmp;
						var statusSpending = '<a class="approve-dialog" data-toggle="modal" href="#myModal" id="approveIcon_{{regid}}" approveid="{{regid}}" title="Approve" onclick="approvePage({{regid}})">\n\
									  <i class="icon-pencil"></i>\n\
									  <span>Approve</span>\n\
								      </a>\n\
								      <a class="reject-dialog" data-toggle="modal" href="#modalReject" id="rejectIcon_{{regid}}" href="#" rejectid="{{regid}}" title="Reject" onclick="rejectPage({{regid}})">\n\
									  <i class="icon-trash"></i>\n\
									  <span>Reject</span>\n\
								      </a>';
						statusSpending = statusSpending.replace(/{{regid}}/g, page.id);
						
						var action = '{{statusSpending}}\n\
							      <a href="{{domain}}/agency/requestmanagement?option=info&pageid={{regid}}" class="detail-dialog-icon" title="View Detail" detailid="{{regid}}">\n\
								  <i class="icon-eye-open"></i>\n\
								  <span>View Details</span>\n\
							      </a>';
						action = action.replace(/{{regid}}/g, page.id);
						action = action.replace(/{{domain}}/g,zaglobal.domain);
						if(page.status == 0){
							action = action.replace(/{{statusSpending}}/, statusSpending);
						}else if(page.status == 2){
							action = action.replace(/{{statusSpending}}/, "");
						}
						
						item = item.replace(/{{No.}}/,++count);
						item = item.replace(/{{regid}}/g, page.id);
						item = item.replace(/{{pageName}}/g, zasearch.htmlEncoder(page.page_name));
						item = item.replace(/{{cateName}}/g, zasearch.htmlEncoder(page.cate_name));
						item = item.replace(/{{contactName}}/,page.contact_name);
						item = item.replace(/{{description}}/,page.description);
						
						var statusPending = '<i class="splashy-refresh_backwards"></i>';
						var statusRejected = '<i class="splashy-remove"></i>';
						if(page.status == 0){
							item = item.replace(/{{statusPage}}/,statusPending);
						}else if(page.status == 2){
							item = item.replace(/{{statusPage}}/,statusRejected);
						}
						item = item.replace(/{{action}}/,action);
						
						html+= item;
						
					}
					
					var param = "{'currentPage':"+paging.curr+"\
								,'pageSize':"+paging.pageSize+"\n\
								,'numDisplay':"+5+"\n\
								,'totalRecord':"+paging.totalRecord+"\n\
								,'action':\"zasearch.searchPageRequestAgency('"+status+"','"+inputId+"','"+tbContentId+"','"+pagingId+"',currPage,pageSize)\"}";
					$("#"+pagingId).html(param);
					zacommon.renderPagingSearchAjax(pagingId, "", "", "", "");
				}else{
					html = "<tr>\n\
							<td valign='middle' colspan='9'>\n\
								Not found data\n\
							</td>\n\
						</tr>";
					$("#"+pagingId).css("display","none");
					$("#"+pagingId).html("");
				}
				$("#"+tbContentId +" tbody").html(html);
			}
		});	
	}	
}

zasearch.searchCertificatePage = function(status, inputId, tbContentId, pagingId, curpage, pageSize){	
	if($("#"+inputId).val() != ""){	
		if(curpage<1){
			curpage = 1;
		}
		var offset = (curpage-1)*pageSize;		
		var param ={};
		param.page_name = $("#"+inputId).val();
		param.from=offset;
		param.num = pageSize;
		param.status = status;
		param.option = 'search_certificate';
		$.ajax({
			url : zaglobal.domain+'/admin/pages/certificate/action',  
			type: "get",			
			dataType: 'json',			
			data: param, 
			success: function(rsData){					
				var html = '';
				$("#"+pagingId).css("display",'none');
				
				if (rsData.result == true && rsData.total > 0 ) {
					var listId = "";
					for(var i = 0; i < rsData.data.length;i++){
						if(i==0){
							listId += rsData.data[i].id;
						}
						else{
							listId += ","+rsData.data[i].id;
						}
					}
					$.ajax({
						url : zaglobal.domain+'/admin/ajax?option=map_pid',
						type : "post",
						dataType: 'json',
						data:{"data": listId},
						success: function(mapData){
							// call get data getdata(obj.value);
							var inviteFan = '<li style="margin-right:4px;padding:0">\n\
											<a href="{{domain}}/admin/pages/invite_fan?pageid={{pageid_rd}}" title="Invite Fan">\n\
												<i class="splashy-contact_blue_add"></i>\n\
											</a>\n\
										</li>';
							var assign = '<li style="margin-right:4px;padding:0">\n\
											<a href="{{domain}}/admin/pages/assign?pageid={{pageid_rd}}" title="Assign">\n\
												<i class="splashy-group_grey_add"></i>\n\
											</a>\n\
										</li>';
							var login = '<li style="margin-right:4px;padding:0">\n\
										<a title="Login page" href="{{domain}}/choose?pageid={{pageid}}">\n\
											<i class="splashy-application_windows_edit"></i>\n\
										</a>\n\
									</li>';
							var tmp = '<tr>\n\
							<td>{{num}}</td>\n\
							<td>{{pageid_rd}}</td>\n\
							<td>{{pagename}}</td>\n\
                            <td>\n\
								<ul style="margin:0" class="icon_list_a clearfix">\n\
									<li style="margin-right:4px;padding:0">\n\
										<a title="Edit" href="{{domain}}/admin/pages?option=detail&pageid={{pageid_rd}}">\n\
											<i class="splashy-document_letter_edit"></i>\n\
										</a>\n\
										</li>\n\
										{{inviteFan}}				\n\
										{{assign}}\n\
										{{login}}\n\
								</ul>\n\
							</td>\n\
						</tr>';
							var privileges = $("#privileges").val().split(",");
							if(privileges.indexOf("inviteFan") >= 0){
								tmp = tmp.replace(/{{inviteFan}}/g, inviteFan);
							}else{
								tmp = tmp.replace(/{{inviteFan}}/g, ""); 
							}
							if(privileges.indexOf("assign") >= 0){
								tmp = tmp.replace(/{{assign}}/g, assign);
							}else{
								tmp = tmp.replace(/{{assign}}/g, ""); 
							}
							if(privileges.indexOf("login") >= 0){
								tmp = tmp.replace(/{{login}}/g, login);
							}else{
								tmp = tmp.replace(/{{login}}/g, ""); 
							}
							var paging = {};
							paging.curr = curpage;
							paging.pageSize = pageSize;
							paging.totalRecord = rsData.total;
					
							var count = (paging.curr-1) * paging.pageSize;
							for(var index=0;index< rsData.data.length;++index){
								var page = rsData.data[index];	
								var item =tmp;
								item = item.replace(/{{domain}}/g,zaglobal.domain);
								item = item.replace(/{{num}}/,++count);
								item = item.replace(/{{pageid}}/g, page.id);
								if(mapData != null && mapData["map"][page.id] != null ){
									item = item.replace(/{{pageid_rd}}/g, mapData["map"][page.id]);
								}else{
									item = item.replace(/{{pageid_rd}}/g, page.id);
								}
								item = item.replace(/{{pagename}}/g, zasearch.htmlEncoder(page.page_name));
						
								html+= item;
						
							}					
					
							var param = "{'currentPage':"+paging.curr+"\
								,'pageSize':"+paging.pageSize+"\n\
								,'numDisplay':"+5+"\n\
								,'totalRecord':"+paging.totalRecord+"\n\
								,'action':\"zasearch.searchCertificatePage('"+status+"','"+inputId+"','"+tbContentId+"','"+pagingId+"',currPage,pageSize)\"}";
							$("#"+pagingId).html(param);
							zacommon.renderPagingSearchAjax(pagingId, "", "", "", "");
							$("#"+tbContentId +" tbody").html(html);
						}
					});					
				}else{
					html = "<tr>\n\
								<td valign='middle' colspan='9'>\n\
									Not found data\n\
								</td>\n\
							</tr>";					
					$("#"+pagingId).css("display","none");
					$("#"+pagingId).html("");
					$("#"+tbContentId +" tbody").html(html);
				}
			}
		});	
	}	
}

zasearch.getTimeLineText = function(time) {
		var d = time;
		if (typeof d === 'number') {
			d = new Date(d);
		}
		var monthNames = new Array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
		var day = d.getDate();
		if(day < 10){
			day = "0" + day;
		}
		return day + "/" + monthNames[d.getMonth()] + "/" + d.getFullYear();
}

 zasearch.htmlEncoder = function(str) {
        if (str == null) {
            return "";
        }
        var rs = str;
        rs = rs.replace(/&/g, "&#38");
        rs = rs.replace(/</g, "&#60");
        return rs;
}