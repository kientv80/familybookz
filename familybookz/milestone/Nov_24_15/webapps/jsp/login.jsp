<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<div style="width: 1024px; margin: auto;">
	<div style="width:50%; float: left;">
		<img alt="" src="/images/icons/family.jpg" style="width: 100%;">
	</div>
	<div style="width: 50%; float: left;text-align: left;padding-left: 10px;">
		<input type="button" value="Login via facebook" id="flogin" onclick="return fblogin();"> 
		<input type="button" value="Sign up" onclick="return showRegisterForm();" id="register">
		<div id="registerForm" style="float: left;">
			<h1>Sign up</h1>
			<p>It is free and always will be</p>
			<form action="/register" method="post"  enctype="multipart/form-data" id = "registerFormInput">
				<input type="text" placeholder="First name" style="float: left;" name = "firstname">
				<input type="text" placeholder="Surname" style="float: left;margin-left: 10px;" name = "surname">
				<input type="text" placeholder="Email or mobile number" style="width: 100%;margin-top: 10px;" name = "email"><br>
				<input type="text" placeholder="Login username" name = "username" id = "username" style="width: 100%;margin-top: 10px;;margin-bottom: 10px;"><br>
				<input type="password" placeholder="Password" name = "password" id = "password" style="width: 100%;margin-top: 10px;;margin-bottom: 10px;">
				<br> 
				<select>
					<option id="0" label="Day">Day</option>
					<option id="1" label="1">1</option>
					<option id="2" label="2">2</option>
					<option id="3" label="3">3</option>
					<option id="4" label="4">4</option>
					<option id="5" label="5">5</option>
					<option id="6" label="6">6</option>
				</select>
				<select>
					<option id="0" label="Month">Month</option>
					<option id="1" label="1">1</option>
					<option id="2" label="2">2</option>
					<option id="3" label="3">3</option>
					<option id="4" label="4">4</option>
					<option id="5" label="5">5</option>
					<option id="6" label="6">6</option>
				</select>
				<select>
					<option id="0" label="Year">Year</option>
					<option id="1" label="1980">1980</option>
					<option id="1" label="1981">1981</option>
					<option id="2" label="1982">1982</option>
					<option id="3" label="1983">1983</option>
					<option id="4" label="1984">1984</option>
					<option id="5" label="1985">1985</option>
					<option id="6" label="1986">1986></option>
				</select> 
				<br> 
				Male <input type="radio" id="1" value="1" name="1">
				Female <input type="radio" id="1" value="2" name="1">
				<br> 
				<input type="file" value="Browse" id="image" name="image" multiple style="float: left;">
				<input type="button" value="Submit" onclick="return register();"> 
			</form>
		</div>
	</div>
	<div id="longinForm" style="float: left;">
		<form action="/login" method="post">
			<input type="text" placeholder="Username" style="float: left;" name="username">
			<input type="text" placeholder="Password" style="float: left;" name = "password">
			<input type="submit" value="Login"> 
		</form>
	</div>
</div>
<script type="text/javascript">
	$("#registerForm").hide();
	function showRegisterForm() {
		$("#flogin").hide();
		$("#longinForm").hide();
		$("#register").hide();
		$("#registerForm").toggle("slow");
	}
	function fblogin(){
		window.location = "/fblogin";
	}
	function register(){
		$("#registerFormInput").submit();
	}
</script>
