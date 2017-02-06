/**
 * 
 */

var user;
var sentCompany;
var ajaxCompanies;
var app = angular.module('myApp',['ui.bootstrap']);

app.controller("CouponSystemController", function($scope, $http){
	
	/******************* Utilities functions ******************************/
	$scope.getCompanyForm = function(){
		return "id=" + $scope.newCompany.id +
				"&companyName=" + $scope.newCompany.companyName +
				"&email=" + $scope.newCompany.email +
				"&password=" + $scope.newCompany.password;
	} 	
	
	$scope.resetLogin = function(){
		$scope.login_panel = true;
		$scope.panel='none';
		$scope.welcome_panel = false;
		$scope.admin_panel = false;
		$scope.company_panel = false;
		$scope.customer_panel = false;
	};
	
	$scope.resetContent = function(){
		$scope.company_display = false;
		$scope.customer_display = false;
		$scope.coupons_display = false;
		$scope.coupons_display_designed = false;
		$scope.savedProperty = false;
		$scope.submitCation = "Create";
	};
	
	$scope.errorHandler = function(response){
		$scope.resetLogin ();
		$scope.resetContent();
		alert("You have encounter error:\n" + JSON.stringify(response));
	}
	
	
	
	$scope.submitLogin = function(){
		var loginUrl="http://localhost:8080/CouponsProj/rest/jaxb/login";
		var formData = "username="+ $scope.login.username +
						"&password=" + $scope.login.password +
						"&role="+ $scope.login.role;
		
		$http({
			 method  : 'POST',
	          url     : loginUrl,
	          data    : formData, //forms user object
	          headers : {'responseType': 'json',
	        	  		 'Content-Type': 'application/x-www-form-urlencoded'} 
	          
		})
		.then(function(response) {
            if (response.data && response.data.username ) {
            	alert("Success");
            	$scope.user = response.data;
            	$scope.login_panel = false;
            	$scope.welcome_panel = true;

            	$scope.panel = response.data.role;
              }else {
            	  $scope.errorHandler(response);
              }
            },$scope.errorHandler);
            
          
      
	};
	
	$scope.logout = function(){
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/login/logout").then(function(response){
			$scope.resetContent();
			$scope.resetLogin();
		});
	}
	
	
	
	/******************* Admin functions ******************************/
	
	/******************* manage company  ******************************/
	
	$scope.getCompanies = function(){
		$scope.resetContent();
		$scope.company_display = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/admin/getAllCompanies").then(function(response){
			$scope.companies = response.data.company;
		}, $scope.errorHandler);
	};
	
	

	
	$scope.updateCompany = function(index){
		
		$scope.newCompany = angular.copy($scope.companies[index]);
		$scope.submitCation = "Update";
		$scope.savedProperty = true;
		$scope.submitCompany = "updateCompany";
		
		
	};
	
	$scope.deleteCompany = function(index){
		if (confirm('Are you sure you want to delete this company from the database?')) {
			$http.post("http://localhost:8080/CouponsProj/rest/jaxb/admin/removeCompany", $scope.companies[index])
			.then(function(response){
				alert("Company deleted");
				$scope.getCompanies();
			}, $scope.errorHandler);
		} 
	};

	
	$scope.sendCompany = function(){
		
		$http.post("http://localhost:8080/CouponsProj/rest/jaxb/admin/"+$scope.submitCompany, $scope.newCompany)
				.then(function(response){
					$scope.getCompanies();
					$scope.newCompany= '';
				},  $scope.errorHandler)
	};
	
	$scope.resetCompany = function(){
		$scope.newCompany = "";
		$scope.submitCation = "Create";
		$scope.savedProperty = false;
		$scope.submitCompany = "createCompany";
	};
	
	$scope.submitCompany = "createCompany";
	
	/******************* manage customers  ******************************/
	$scope.getCustomers = function(){
		$scope.resetContent();
		$scope.customer_display = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/admin/getAllCustomer").then(function(response){
			$scope.customers = response.data.customer;
		},$scope.errorHandler);
	};
	
	$scope.updateCustomer = function(index){
		
		$scope.newCustomer = angular.copy($scope.customers[index]);
		$scope.submitCation = "Update";
		$scope.savedProperty = true;
		$scope.submitCustomer = "updateCustomer";
		
		
	};
	
	$scope.deleteCustomer = function(index){
		if (confirm('Are you sure you want to delete this Customer from the database?')) {
			$http.post("http://localhost:8080/CouponsProj/rest/jaxb/admin/removeCustomer", $scope.customers[index])
			.then(function(response){
				alert("Customer deleted");
				$scope.getCustomers();
			}, $scope.errorHandler)
		} 
	};

	
	$scope.sendCustomer = function(){
		$http.post("http://localhost:8080/CouponsProj/rest/jaxb/admin/"+$scope.submitCustomer, $scope.newCustomer)
				.then(function(response){
					$scope.getCustomers();
					$scope.newCustomer= '';
				},$scope.errorHandler)
	};
	
	$scope.resetCustomer = function(){
		$scope.newCustomer = "";
		$scope.submitCation = "Create";
		$scope.savedProperty = false;
		$scope.submitCustomer = "createCustomer";
	};
	
	$scope.submitCustomer = "createCustomer";
	
	/******************* Company functions ******************************/
	
	$scope.getCoupons = function(){
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/company/getAllCoupon").then(function(response){
			if(response.data){
				if(response.data.coupon.length){
					$scope.coupons = response.data.coupon;
				}
				else {
					$scope.coupons = [];
					$scope.coupons.push(response.data.coupon);
				}
			}
		}, $scope.errorHandler);
		
	};
	
	
	$scope.updateCoupon = function(index){
		
		$scope.newCoupon = angular.copy($scope.coupons[index]);
		$scope.newCoupon.startDate =  new Date($scope.coupons[index].startDate);
		$scope.newCoupon.endDate =  new Date($scope.coupons[index].endDate);
		$scope.submitCation = "Update";
		$scope.savedProperty = true;
		$scope.submitCoupon = "updateCoupon";
		
		
	};
	
	$scope.deleteCoupon = function(index){
		if (confirm('Are you sure you want to delete this Coupon from the database?')) {
			$http.post("http://localhost:8080/CouponsProj/rest/jaxb/company/removeCoupon", $scope.coupons[index])
			.then(function(response){
				alert("Coupon deleted");
				$scope.getCoupons();
			}, $scope.errorHandler)
		} 
	};

	
	$scope.sendCoupon = function(){
		$http.post("http://localhost:8080/CouponsProj/rest/jaxb/company/"+$scope.submitCoupon, $scope.newCoupon)
				.then(function(response){
					$scope.getCoupons();
					$scope.newCoupon= '';
				}, $scope.errorHandler)
	};
	
	$scope.filterByType = function(){
		$scope.resetContent();
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/company/getCouponByType?type="+$scope.filterByTypeSelect).then(function(response){
			if(response.data){
				if(response.data.coupon.length){
					$scope.coupons = response.data.coupon;
				}
				else if(response.data.coupon != null){
					$scope.coupons = [];
					$scope.coupons.push(response.data.coupon);
				}
			}
		},$scope.errorHandler);
	};
	
	$scope.filterByPrice = function(){
		$scope.resetContent();
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/company/getCouponUntilPrice?price="+$scope.filterByPriceText).then(function(response){
			if(response.data){
				if(response.data.coupon.length){
					$scope.coupons = response.data.coupon;
				}
				else if(response.data.coupon != null){
					$scope.coupons = [];
					$scope.coupons.push(response.data.coupon);
				}
			}
		}, $scope.errorHandler);
	};
	
	$scope.filterByDate = function(){
		$scope.resetContent();
		$scope.coupons_display = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/company/getCouponUntilDate?date="+$scope.filterByDateDate.toDateString()).then(function(response){
			if(response.data){
				if(response.data.coupon.length){
					$scope.coupons = response.data.coupon;
				}
				else if(response.data.coupon != null){
					$scope.coupons = [];
					$scope.coupons.push(response.data.coupon);
				}
			}
		}, $scope.errorHandler);
	}
	
	
	$scope.resetCoupon = function(){
		$scope.newCoupon = "";
		$scope.submitCation = "Create";
		$scope.savedProperty = false;
		$scope.submitCoupon = "createCoupon";
	};
	
	$scope.submitCoupon = "createCoupon";
	
	/******************* Customer functions ******************************/
	
	$scope.getAvailableCoupons = function(){
		$scope.resetContent();
		$scope.coupons_display_designed = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/customer/getAvailableCoupons").then(function(response){
			$scope.buyCoupons=true;
			if(response.data){
				if(response.data.coupon.length){
					$scope.coupons = response.data.coupon;
				}
				else if(response.data.coupon != null){
					$scope.coupons = [];
					$scope.coupons.push(response.data.coupon);
				}
			}else{
				$scope.coupons ='';
			}
		}, $scope.errorHandler);
	};
	
	$scope.getPurchasedCoupons = function(){
		$scope.resetContent();
		$scope.coupons_display_designed = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/customer/getCoupons").then(function(response){
			$scope.buyCoupons=false;
			if(response.data){
				if(response.data.coupon.length){
					$scope.coupons = response.data.coupon;
				}
				else{
					$scope.coupons = [];
					$scope.coupons.push(response.data.coupon);
				}
			}else{
				$scope.coupons ='';
			}
			
		}, $scope.errorHandler);
	};
	
	$scope.custFilterByPrice = function(){
		$scope.resetContent();
		$scope.coupons_display_designed = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/customer/getCouponsByPrice?price="+ $scope.custFilterByPriceText).then(function(response){
			$scope.buyCoupons=false;
			if(response.data){
				if(response.data.coupon.length){
					$scope.coupons = response.data.coupon;
				}
				else{
					$scope.coupons = [];
					$scope.coupons.push(response.data.coupon);
				}
			}else{
				$scope.coupons ='';
			}
			
		}, $scope.errorHandler);
	};
	
	$scope.custFilterByType = function(){
		$scope.resetContent();
		$scope.coupons_display_designed = true;
		$http.get("http://localhost:8080/CouponsProj/rest/jaxb/customer/getCouponsByType?type="+ $scope.custFilterByTypeSelect).then(function(response){
			$scope.buyCoupons=false;
			if(response.data){
				if(response.data.coupon.length){
					$scope.coupons = response.data.coupon;
				}
				else{
					$scope.coupons = [];
					$scope.coupons.push(response.data.coupon);
				}
			}else{
				$scope.coupons ='';
			}
			
		}, $scope.errorHandler);
	};
	
	$scope.purchaseCoupon= function(index){
		$http.post("http://localhost:8080/CouponsProj/rest/jaxb/customer/purchaseCoupon",$scope.coupons[index]).then(function(response){
			$scope.buyCoupons=false;
			$scope.getPurchasedCoupons();
		}, $scope.errorHandler);
		
	};
	
	
	
	/******************* Startup functions ******************************/
	
	
	$scope.companies;
	$scope.customers;
	$scope.coupons =[];
	$scope.user;
	$scope.resetLogin();
	$scope.resetContent();
	
});
