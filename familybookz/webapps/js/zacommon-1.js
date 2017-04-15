var zacommon = {};
//

zacommon.strTrim = function(str){
	str = str.replace(/^\s+|\s+$/g,"");
	return str;
}

zacommon.selectOptionByValue = function(id, value){	
	if(value!=''){		
		var sl = document.getElementById(id);
		for(var index=0;index<sl.length; ++index){				
			if(sl.options[index].value == value){				
				sl.options[index].selected = 'checked';
				break;
			}
		}	
	}		
}

zacommon.selectOptionByText = function(id, value){	
	if(value!=''){		
		var sl = document.getElementById(id);
		for(var index=0;index<sl.length; ++index){				
			if(sl.options[index].innerHTML == value){				
				sl.options[index].selected = 'checked';
				break;
			}
		}	
	}		
}

zacommon.getTextSelectBox = function(id,value){	
	var rs='';
	if(value!=''){		
		var sl = document.getElementById(id);
		for(var index=0;index<sl.length; ++index){				
			if(sl.options[index].value == value){				
				rs = sl.options[index].innerHTML;
				break;
			}
		}	
	}		
	return rs;
}

zacommon.selectRadio = function(radio_name,value){
	var objselect = document.getElementsByName(radio_name);
	for(var i=0; i<objselect.length;++i){
		if(objselect[i].value==value){
			objselect[i].checked="checked";
		}
	}
}
zacommon.getValueRadioChecked = function(radio_name){
    var value;
	var objselect = document.getElementsByName(radio_name);
	for(var i=0; i<objselect.length;++i){
		if(objselect[i].checked == true){			
            value = objselect[i].value;
            break;
		}
	}
    return value;
}


zacommon.addOptionSelectBox = function(selectId, hiddenId, contentId, callback, ignore){
	var sl = document.getElementById(selectId);
	if(ignore==null || ignore==undefined){
		ignore = 0;
	}
	if(sl.value != ignore){
		var currVal = document.getElementById(hiddenId);
		var arrVal = new Array();
		if(currVal.value != ""){		
			arrVal = currVal.value.split(",");
		}		
		var isHave = false;			
		for(var i = 0 ;i < arrVal.length; ++i){
			if(sl.value == arrVal[i]){
				isHave = true;
				break;
			}
		}	
		if(!isHave){
			arrVal[arrVal.length] = sl.value;
			currVal.value = arrVal;
			if(callback)
				callback(hiddenId, selectId , contentId);
			
		}
	}	
}
zacommon.selectCheckBox = function(id, checked){	
	if(checked == 'true'){		
		var sl = document.getElementById(id);
		if(sl!=undefined){
			sl.checked = true;
		}
	}		
}

zacommon.changeCheckBox = function(obj,inputId, callback){	
	var ch = obj.checked;	
	var currValue = document.getElementById(inputId);	
	var arrVal = new Array();
	
	if(currValue.value != ""){		
		arrVal = currValue.value.split(",");
	}
	
	var isHave = false;
	var index = 0;	
	for(;index < arrVal.length; ++index){
		if(obj.value == arrVal[index]){
			isHave = true;
			break;
		}
	}
	
	if(ch==true){
		if(isHave == false){
			arrVal[arrVal.length] = obj.value;
		}					
	}else{
		if(isHave == true){
			arrVal.splice(index, 1);			
		}
	}
	currValue.value = arrVal;
	if(callback){
		callback(ch, obj.value);
	}	
}

zacommon.renderPagingSearch = function(divId, titlePre, titleNext, titleFirst, titleLast, keyword) {
	var divTag = document.getElementById(divId);
	if(divTag == null){
		alert(divId + " is " + divTag);
		return;
	}
		
	var data = eval("("+divTag.innerHTML+")");
	divTag.innerHTML = "";
	if (data.totalRecord == '0'){
		return;
	}
	data.currentPage = parseInt(data.currentPage);
	data.pageSize = parseInt(data.pageSize);
	data.numDisplay = parseInt(data.numDisplay);

	var totalpage = parseInt(data.totalRecord / data.pageSize);
	if (data.totalRecord % data.pageSize > 0)
		totalpage += 1;
	if(data.currentPage < 0 )
		data.currentPage = 1;
	if (data.currentPage > totalpage)
		data.currentPage = totalpage;    

	var paging = "", next = "", pre = "";
	var pos_start, pos_end;

	var url = data.action + "?";
	if(keyword != ''){
		url += keyword;
	}
	url += "&page=";
	var first = "<a href='" + url + 1 + "'>&nbsp" + titleFirst + "&nbsp</a>";
	var last = "<a href='" + url + totalpage + "'>&nbsp" + titleLast + "&nbsp</a>";

	if (data.numDisplay >= totalpage) {
		pos_start = 1;
		pos_end = totalpage;
	}else{
		var half = parseInt(data.numDisplay / 2);	
		if(data.numDisplay % 2 == 1){
			++half;
		}
		if (data.currentPage <= half) {
			pos_start = 1;
		}else {
			if (data.currentPage + half > totalpage) {
				pos_start = totalpage - data.numDisplay + 1;
			} else {
				pos_start = data.currentPage - half + 1;
			}
		}
		pos_end = data.numDisplay;
	}
	if ((data.currentPage - 1) > 0) {
		pre = "<a href='" + url + (data.currentPage - 1) + "' style='cursor: pointer; color: rgb(0, 102, 204);' class='pre' >"+ titlePre +"</a>";
		pre = pre + "&nbsp;";
	} else {
		pre = "<strong class='current'>"+ titlePre +"</strong>";
		pre = pre + "&nbsp;";
		first = "<strong class='current'>"+ titleFirst +"&nbsp</strong>";
	}    
	if ((data.currentPage + 1) <= totalpage) {
		next = "<a href='" + url + (data.currentPage + 1) + "' style='cursor: pointer; color: rgb(0, 102, 204);' class='next' >"+ titleNext +"</a>";
		next = "&nbsp;" + next;
	} else {
		next = "<strong class='current'>"+ titleNext +"</strong>";
		next = "&nbsp;" + next;
		last = "<strong class='current'>&nbsp"+ titleLast +"</strong>";
	}
	for (var i = 0; i < pos_end; i++) {
		if (i != 0) {
			paging += "&nbsp;-&nbsp;";
		}
		if (pos_start == data.currentPage) {
			paging += "<strong class='current'>" + pos_start + "</strong>";
		}else {
			paging += "<a href='" + url + pos_start + "' class='page' style='cursor: pointer; color: rgb(0, 102, 204);' >" + pos_start + "</a>";
		}
		pos_start++;
	}
	var displayTotal = "&nbsp;<strong class='current'>("+ data.currentPage + "/" + totalpage + ")</strong>";

	paging = first + pre + paging + next + last + displayTotal;
	divTag.innerHTML = paging;
	$("#"+divId).css("display","block");
}

zacommon.renderPagingSearch2 = function(divId , titlePre, titleNext, titleFirst, titleLast, keyword) {
	var divTag = document.getElementById(divId);
	if(divTag == null){
		alert(divId + " is " + divTag);
		return;
	}
	
	divTag.innerHTML = zacommon.strTrim(divTag.innerHTML);
	if(divTag.innerHTML==""){
		return;
	}
	
	var data = eval("("+divTag.innerHTML+")");
	divTag.innerHTML = "";
	if (data.totalRecord == '0'){
		return;
	}
	data.currentPage = parseInt(data.currentPage);
	data.pageSize = parseInt(data.pageSize);
	data.numDisplay = parseInt(data.numDisplay);

	var totalpage = parseInt(data.totalRecord / data.pageSize);
	if (data.totalRecord % data.pageSize > 0)
		totalpage += 1;
	if(data.currentPage < 0 )
		data.currentPage = 1;
	if (data.currentPage > totalpage)
		data.currentPage = totalpage;    

	var paging = "", next = "", pre = "";
	var pos_start, pos_end;

	var url = data.action + "?";
	if(keyword != ''){
		url += keyword;
	}
	url += "&page=";	

	var first = '<li class="first"><a href="'+ url + 1 +'">&lt;&lt; '+titleFirst+'</a></li>';
	var last = '<li class="last"><a href="'+ url + totalpage +'">'+titleLast+' &gt;&gt;</a></li>';
	
	if (data.numDisplay >= totalpage) {
		pos_start = 1;
		pos_end = totalpage;
	}else{
		var half = parseInt(data.numDisplay / 2);	
		if(data.numDisplay % 2 == 1){
			++half;
		}
		if (data.currentPage <= half) {
			pos_start = 1;
		}else {
			if (data.currentPage + half > totalpage) {
				pos_start = totalpage - data.numDisplay + 1;
			} else {
				pos_start = data.currentPage - half + 1;
			}
		}
		pos_end = data.numDisplay;
	}
	if ((data.currentPage - 1) > 0) {
		pre = '<li class="prev"><a href="'+ url + (data.currentPage - 1) +'">&lt;'+titlePre+'</a></li>';
	} else {		
		first = "<li class='first disabled'><a href='#'>&lt;&lt; "+titleFirst+"</a></li>";
		pre = '<li class="prev disabled"><a href="#">&lt;'+titlePre+'</a></li>';
	}    
	if ((data.currentPage + 1) <= totalpage) {		
		next =	'<li class="next"><a href="'+ url + (data.currentPage + 1) +'">'+titleNext+'&gt;</a></li>';
	} else {
		last = "<li class='last disabled'><a href='#'>"+titleLast+" &gt;&gt;</a></li>";		
		next =	'<li class="next disabled"><a href="#">'+titleNext+'&gt;</a></li>';
	}
	for (var i = 0; i < pos_end; i++) {
		//		if (i != 0) {
		//			paging += "&nbsp;-&nbsp;";
		//		}
		if (pos_start == data.currentPage) {
			//			paging += "<strong class='current'>" + pos_start + "</strong>";
			paging += '<li class="active"><a href="#">'+ pos_start + '</a></li>';
		}else {
			//			paging += "<a href='" + url + pos_start + "' class='page' style='cursor: pointer; color: rgb(0, 102, 204);' >" + pos_start + "</a>";
			paging += '<li><a href="'+ url + pos_start +'">'+ pos_start + '</a></li>';
		}
		pos_start++;
	}
	//	var displayTotal = "&nbsp;<strong class='current'>("+ data.currentPage + "/" + totalpage + ")</strong>";
	var displayTotal = "<ul><li class='disabled'><a>("+ data.currentPage + "/" + totalpage + ")</a></li></ul>";

	paging = '<ul class="pagination">'+first +pre + paging + next + last+'</ul>'+ displayTotal;
	divTag.innerHTML = paging;
	$("#"+divId).css("display","block");
}

zacommon.renderPagingSearchAjax = function(divId, titlePre, titleNext, titleFirst, titleLast) {
	var divTag = document.getElementById(divId);
	if(divTag == null){
		alert(divId + " is " + divTag);
		return;
	}
	if(divTag.innerHTML==""){
		return;
	}
	var data = eval("("+divTag.innerHTML+")");
	divTag.innerHTML = "";
	if (data.totalRecord == undefined || data.totalRecord == '0'){
		return;
	}
	data.currentPage = parseInt(data.currentPage);
	data.pageSize = parseInt(data.pageSize);
	data.numDisplay = parseInt(data.numDisplay);

	var totalpage = parseInt(data.totalRecord / data.pageSize);
	if (data.totalRecord % data.pageSize > 0)
		totalpage += 1;
	if(data.currentPage < 0 )
		data.currentPage = 1;
	if (data.currentPage > totalpage)
		data.currentPage = totalpage;    

	var paging = "", next = "", pre = "";
	var pos_start, pos_end;

	var func = data.action;
	func = func.replace('currPage',1);
	func = func.replace('pageSize',data.pageSize);
	
	//	var first = "<a href='javascript:void(0)' onclick=\""+func+"\">&nbsp" + titleFirst + "&nbsp</a>";
	var first = '<li class="first"><a href="javascript:void(0);" onclick="'+func+'">&lt;&lt; '+titleFirst+'</a></li>';
	func = data.action;
	func = func.replace('currPage',totalpage);
	func = func.replace('pageSize',data.pageSize);
	//	var last = "<a href='javascript:void(0)' onclick=\""+func+"\">&nbsp" + titleLast + "&nbsp</a>";
	var last = '<li class="last"><a href="javascript:void(0)" onclick="'+func+'">'+titleLast+' &gt;&gt;</a></li>';

	if (data.numDisplay >= totalpage) {
		pos_start = 1;
		pos_end = totalpage;
	}else{
		var half = parseInt(data.numDisplay / 2);	
		if(data.numDisplay % 2 == 1){
			++half;
		}
		if (data.currentPage <= half) {
			pos_start = 1;
		}else {
			if (data.currentPage + half > totalpage) {
				pos_start = totalpage - data.numDisplay + 1;
			} else {
				pos_start = data.currentPage - half + 1;
			}
		}
		pos_end = data.numDisplay;
	}
	if ((data.currentPage - 1) > 0) {
		func = data.action;
		func = func.replace('currPage',(data.currentPage - 1));
		func = func.replace('pageSize',data.pageSize);
		//		pre = "<a href='javascript:void(0);' onclick=\""+func+"\" style='cursor: pointer; color: rgb(0, 102, 204);' class='pre' >"+ titlePre +"</a>";
		//		pre = pre + "&nbsp;";
		pre = '<li class="prev"><a href="javascript:void(0);" onclick="'+func+'">&lt;'+titlePre+'</a></li>';
	} else {		
		//		pre = "<strong class='current'>"+ titlePre +"</strong>";
		//		pre = pre + "&nbsp;";
		//		first = "<strong class='current'>"+ titleFirst +"&nbsp</strong>";
		first = "<li class='first disabled'><a href='javascript:void(0);'>&lt;&lt; "+titleFirst+"</a></li>";
		pre = '<li class="prev disabled"><a href="#">&lt;'+titlePre+'</a></li>';
	}    
	if ((data.currentPage + 1) <= totalpage) {
		func = data.action;
		func = func.replace('currPage',(data.currentPage + 1));
		func = func.replace('pageSize',data.pageSize);
		//		next = "<a href='javascript:void(0);' onclick=\""+func+"\" style='cursor: pointer; color: rgb(0, 102, 204);' class='next' >"+ titleNext +"</a>";
		//		next = "&nbsp;" + next;
		next =	'<li class="next"><a href="javascript:void(0);" onclick="'+func+'">'+titleNext+'&gt;</a></li>';
	} else {
		//		next = "<strong class='current'>"+ titleNext +"</strong>";
		//		next = "&nbsp;" + next;
		//		last = "<strong class='current'>&nbsp"+ titleLast +"</strong>";
		last = "<li class='last disabled'><a href='#'>"+titleLast+" &gt;&gt;</a></li>";		
		next =	'<li class="next disabled"><a href="#">'+titleNext+'&gt;</a></li>';
	}
	for (var i = 0; i < pos_end; i++) {
		//		if (i != 0) {
		//			paging += "&nbsp;-&nbsp;";
		//		}
		if (pos_start == data.currentPage) {
			//			paging += "<strong class='current'>" + pos_start + "</strong>";
			paging += '<li class="active"><a href="javascript:void(0);">'+ pos_start + '</a></li>';
		}else {
			func = data.action;
			func = func.replace('currPage',pos_start);
			func = func.replace('pageSize',data.pageSize);
			//			paging += "<a href='javascript:void(0);' onclick=\""+func+"\" class='page' style='cursor: pointer; color: rgb(0, 102, 204);' >" + pos_start + "</a>";
			paging += '<li><a href="javascript:void(0);" onclick="'+func+'">'+ pos_start + '</a></li>';
		}
		pos_start++;
	}
	//	var displayTotal = "&nbsp;<strong class='current'>("+ data.currentPage + "/" + totalpage + ")</strong>";
	//	paging = first + pre + paging + next + last + displayTotal;

	var displayTotal = "<ul><li class='disabled'><a>("+ data.currentPage + "/" + totalpage + ")</a></li></ul>";
	paging = '<ul>'+first +pre + paging + next + last+'</ul>'+ displayTotal;
	
	divTag.innerHTML = paging;
	$("#"+divId).css("display","block");
}

zacommon.charCount = function(obj){ //obj: input field to be counted  
    var objId = $(obj).attr('id') != undefined ? $(obj).attr('id') : "";
    var countDiv = $('#' + objId + "_count");
    var countMax = Number($(obj).attr('countmax'));
    
    if(obj != null && countDiv != null && countDiv != undefined && countMax != undefined && countMax > 0){
        var textTemp = $(obj).val();        
        var text = textTemp.replace(/\n/g, "\n\r");
        $(countDiv).html(text.length + "/" + countMax);
        
        if(text.length > countMax){
            $("#alert_err").html("Tối đa " + countMax + " ký tự");
            $("#alert_err").show();
        }
        else{
            $("#alert_err").hide();
        }
    }
}


zacommon.getFileSize = function(fileid) {
    var fileSize = 0;
    if($("#" + fileid) != null && $("#" + fileid) != undefined){
        try {        
            //for IE
            if ($.browser.msie) {
                var objFSO = new ActiveXObject("Scripting.FileSystemObject");
                var sPath = $("#" + fileid)[0].value;
                var objFile = objFSO.getFile(sPath);
                fileSize = objFile.size;
            }
            //for FF, Safari, Opeara and Others
            else {
                var fileObj = document.getElementById(fileid);
                fileSize = fileObj.files[0].size;            
            }
        }
        catch (e) {        
        }
    }
    return fileSize;
}

zacommon.sortSelect = function(selectId) {
    if(selectId != null && selectId != undefined){
        $("#"+selectId).html($("#"+selectId+" option").sort(zacommon.alphabetASC)); 
    }
} 

zacommon.alphabetASC = function(A, B){        
     var a = zacommon.unicodeToASCII(A.text.toLowerCase());
     var b = zacommon.unicodeToASCII(B.text.toLowerCase());
     return a == b ? 0 : a < b ? -1 : 1     
}

zacommon.alphabetDESC = function(A, B){
     var a = A.text.toLowerCase();
     var b = B.text.toLowerCase();
     return a == a ? 0 : a > b ? -1 : 1     
}

zacommon.unicodeToASCII = function(str) {
    var rs = "";    
    if(str != null && str != undefined){
        var marTViet = new Array("à", "á", "ạ", "ả", "ã", "â", "ầ", "ấ", "ậ", "ẩ", "ẫ", "ă", "ằ", "ắ", "ặ", "ẳ", "ẵ", "è", "é", "ẹ", "ẻ", "ẽ", "ê", "ề", "ế", "ệ", "ể", "ễ", "ì", "í", "ị", "ỉ", "ĩ", "ò", "ó", "ọ", "ỏ", "õ", "ô", "ồ", "ố", "ộ", "ổ", "ỗ", "ơ", "ờ", "ớ", "ợ", "ở", "ỡ", "ù", "ú", "ụ", "ủ", "ũ", "ư", "ừ", "ứ", "ự", "ử", "ữ", "ỳ", "ý", "ỵ", "ỷ", "ỹ", "đ", "À", "Á", "Ạ", "Ả", "Ã", "Â", "Ầ", "Ấ", "Ậ", "Ẩ", "Ẫ", "Ă", "Ằ", "Ắ", "Ặ", "Ẳ", "Ẵ", "È", "É", "Ẹ", "Ẻ", "Ẽ", "Ê", "Ề", "Ế", "Ệ", "Ể", "Ễ", "Ì", "Í", "Ị", "Ỉ", "Ĩ", "Ò", "Ó", "Ọ", "Ỏ", "Õ", "Ô", "Ồ", "Ố", "Ộ", "Ổ", "Ỗ", "Ơ", "Ờ", "Ớ", "Ợ", "Ở", "Ỡ", "Ù", "Ú", "Ụ", "Ủ", "Ũ", "Ư", "Ừ", "Ứ", "Ự", "Ử", "Ữ", "Ỳ", "Ý", "Ỵ", "Ỷ", "Ỹ", "Đ");
        var marKoDau = new Array("a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e", "i", "i", "i", "i", "i", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "y", "y", "y", "y", "y", "d", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "I", "I", "I", "I", "I", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "U", "U", "U", "U", "U", "U", "U", "U", "U", "U", "U", "Y", "Y", "Y", "Y", "Y", "D");
        rs = str;
        for (var index = 0; index < marTViet.length; ++index) {
            rs = rs.replace(marTViet[index], marKoDau[index]);
        }
    }
    return rs;
}