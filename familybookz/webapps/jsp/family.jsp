<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<div id="container" class="container-fluid" style="width: 100%;overflow: auto;padding: 0px;margin: 0px;">
</div>

<div class="modal fade" id="addParentForm">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Add Parent</h4>
      </div>
      <div class="modal-body">
      	<div class="container-fluid">
      		<div class="row">
      			<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10" style="padding: 0px;">
      				<input type="text" placeholder="Seach by name" class="form-control" id="searchText">
      			</div>
      			<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2" style="padding: 0px;">
      				<button value="Search" onclick="return searchPerson();" class="btn btn-primary">Search</button>
      			</div>
      		</div>
      		<div class="row">
      			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="padding: 0px;">
	      			<div class="dropdown">
						  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
						    Select from facebook friends
						    <span class="caret"></span>
						  </button>
						  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" id="fbFriendListId">
						  </ul>
					</div>
      			</div>
      		</div>
      		<div class="row">
      			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="padding: 0px;">
      				Create New(only use if the person was passed away or can NOT create account by themselves) 
      				<input type="checkbox" name="addFrom" id="addFrom" value="new" onclick="showAddNewForm();">
      			</div>
      		</div>
      		<div class="row">
      			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="padding: 0px;">
      				<input type="hidden" id="type" name="type">
			      	<div id="addNewForm" >
				      	<form  method="post" enctype="multipart/form-data" action="/addparent">
							<p>Person info</p>
							<input type="text" placeholder="First name" class="form-control" name="firstname" id = "firstname">
							<input type="text" placeholder="Surname" class="form-control" name="surname" id = "surname" >
							<textarea class="form-control" rows="3" placeholder="Short description about your mom or dad" name="desc" id="desc"></textarea>
							<input type="file" name="image" id = "image">
							<input type="hidden" id="personId">
						</form>
			      	</div>
      			</div>
      		</div>
	      	<div class="row">
      			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="padding: 0px;">
      				<div id="searchResult">
			      		<img alt="" src="" id="avatar">
			      		<label id="name"></label>
			      		<input type="hidden" id="relationId">
			      	</div>
      			</div>
      		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" onclick="submitForm();">Add</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
	function findMe(){
		findMyRoot(${person.id}, 2);
	}
	findMyRoot(${person.id}, 2);
	
	function postFeed() {
		$.ajax({
			method : "POST",
			url : "/postfeed",
			data : {
				ownerId : $("#id").val(),
				feedId : "1",
				content : $("#msg").val()
			}
		}).done(function(msg) {
		});
	}
	
	
	$("#addNewForm").hide();
	$("#searchForm").hide();
	
	function showAddNewForm() {
		$("#addNewForm").toggle("slow");
	}
	function showSearchForm(){
		$("#searchForm").toggle("slow");
	}
	function submitForm(){
		
		if(document.getElementById("addFrom").checked==true){
			var formdata = new FormData();
			formdata.append("image", $("#image")[0].files[0]);
			formdata.append('firstname',$("#firstname").val());
			formdata.append('surname', $("#surname").val());
			formdata.append('desc', $("#desc").val());
			formdata.append('type', $("#type").val());
			formdata.append('personId', $("#personId").val());
			$.ajax({
				method : "POST",
				url : "/addrelation",
				contentType: false,
				processData: false,
				data : formdata
			}).done(function(result) {
				if(result.errorCode == 1){
					alert(result.errorCode + result.msg);
				} else if ("kid" == result.type) {
					addChildNode(result.relation, result.monOrDadId);
					$('#addParentForm').modal("hide");
				} else {//no thing to do with add wife or husband
					$('#addParentForm').modal("hide");
				}
			});
		}else if($("#relationId").val() != "" && $("#relationId").val() != undefined){
			$.ajax({
				method : "GET",
				url : "/addrelation",
				data : {
					'personId' : $("#personId").val(),
					'relationId' : $("#relationId").val(),
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

	}
	function searchPerson(){
		$.ajax({
			method : "POST",
			url : "/search",
			data : {
				'searchText' : $("#searchText").val()
			}
		}).done(function(result) {
			if(result.errorCode != null){
				alert(result.msg);
			}else{
				$("#avatar").attr('src',result.image);
				$("#name").text(result.name);
				$("#relationId").val(result.id);
			}
			
		});
	}
	function getFBFriends(){
		$.ajax({
			method : "GET",
			url : "/fbfriends"
		}).done(function(result) {
			if(result.errorCode != null){
				alert(result.msg);
			}else{
				loadFBFriends($("#personId").val(),"fbFriendListId",result);
			}
			
		});
	}
	
</script>