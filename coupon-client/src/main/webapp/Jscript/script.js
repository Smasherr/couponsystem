/**
 * 
 */


var user;
var sentCompany;

function sendLoginForm(btn){
		var ajax=new XMLHttpRequest();
		var url="http://localhost:8080/CouponsProj/rest/jaxb/login";
		ajax.onreadystatechange=function(){
			if(ajax.readyState==4)
				showFeedback(ajax.response); 
		};
		ajax.responseType = "json";
		ajax.open("POST",url,true);
		ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		ajax.send(getParameters());
		document.getElementById("loadingGif").hidden=false;
}

function getParameters(){
	var formElements = document.getElementById('login_form').elements;
	var paramString = '';
	for (var i=0; i<formElements.length; i++) {
		paramString += formElements[i].name + '=' + formElements[i].value+'&';
	}
	alert(paramString);
	return paramString;
}

function showFeedback(result){
	
	if(result != null && result.username != null){
		user = result;
		
		var str_login="<p> hello " + user.username + "</p>"+
				"<input type='button' id='logout' value='logout' onclick='logout()' />";
		document.getElementById("login_div").innerHTML= str_login;
		switch(result.role){
		case "admin":
			document.getElementById("admin_control_panel").hidden=false;
		}
		
	}else{
		alert("WRONG!!");
	}


} 

function getCompanies(){
	var ajax=new XMLHttpRequest();
	var url="http://localhost:8080/CouponsProj/rest/jaxb/admin/getAllCompanies";
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4)
			displayCompanies(ajax.response); 
	};
	ajax.responseType = "json";
	ajax.open("GET",url,true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.send();
	document.getElementById("loadingGif").hidden=false;
}

function displayCompanies(ajaxJson){
	var str = "";
	//alert(JSON.stringify(ajaxJson.company[1]));
	sentCompany = ajaxJson.company[0];
	for(var i=0; i< ajaxJson.company.length; i++){
		var company = ajaxJson.company[i];
		str+= "<tr>"+
					"<td class='company_id'>" + company.id  +"</td>"+
					"<td class='company_id'>"+ company.companyName +"</td>"+
					"<td>"+ company.email +"</td>"+
					"<td>"+ company.password +"</td>"+
					"<td><input type='button' value='update' onclick='updateCompany(" + company.id  + ")' /></td>"+
					"<td><input type='button' value='update' onclick='deleteCompany(" + company.id  + ")' /></td>"+
				"</tr>";
	}
	document.getElementById("company_table_body").innerHTML=str;
	document.getElementById("loadingGif").hidden=true;
}

function createCompany(){
	var newCompanyName = document.getElementById("company_name").value;
	var newCompanyEmail = document.getElementById("company_email").value;
	var newCompanyPassword = document.getElementById("company_password").value;
	var newCompany = {
			companyName : newCompanyName,
			email : newCompanyEmail,
			password : newCompanyPassword
	};
	var item = {
			company: newCompany
	}
	
	var test = {
			"company": {
				"companyName":"cool",
				"email":"cool",
				"password":"cool"
			}
	}
	
	var ajax=new XMLHttpRequest();
	var url="http://localhost:8080/CouponsProj/rest/jaxb/admin/createCompany";
	ajax.onreadystatechange=function(){
		if(ajax.readyState==4)
			createCompanyCallback(ajax.response); 
	};


	ajax.open("POST",url,true);
	ajax.setRequestHeader("Content-Type", "application/json");
	alert(JSON.stringify(sentCompany))
	ajax.send(JSON.stringify(sentCompany));
	
}

function createCompanyCallback(json){
	
}


