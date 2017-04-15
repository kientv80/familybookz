<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<div id="container" class="w3-container"></div>

<div class="modal fade" id="addParentForm">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Add Parent</h4>
      </div>
      <div class="modal-body">
	      	Create New <input type="radio" name="addFrom" id="addFrom" value="new" onclick="showAddNewForm();"><br>
	      	Facebook <input type="radio" name="addFrom" id="addFrom" value="fb" onclick="showAddNewForm();"><br>
	      	FacebookZ <input type="radio" name="addFrom" id="addFrom" value="fbz" onclick="showSearchForm();"><br>
	      	<div class="dropdown">
			  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			    Select from facebook friends
			    <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" id="fbFriendListId">
			  </ul>
			</div>
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
	      	
	      	<div id="searchForm" >
				<p>Search your parent on FamilyBookz</p>
				<input type="text" placeholder="Seach by name" class="form-control" id="searchText">
				<button value="Search" onclick="return searchPerson();">Search</button>
	      	</div>
	      	<div id="searchResult">
	      		<img alt="" src="" id="avatar">
	      		<label id="name"></label>
	      		<input type="hidden" id="relationId">
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
		var myRadio = $('input[name=addFrom]');
		var checkedValue = myRadio.filter(':checked').val();
		if( "fb"== checkedValue){
			
		}else if( "fbz"==checkedValue){
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
		}else if( "new"==checkedValue){
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