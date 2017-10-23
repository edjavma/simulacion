app.factory('resultService',function($http,$q){
	
	var urlConsulta = '/Econometrico/calcular';
	var urlAnios = '/Econometrico/listYear';
	var urlTemp = '/Econometrico/prueba';
	
	var fac = {};
	
	fac.consulta = function(data){
	
		var deferred = $q.defer();
	    var config = { params: data, headers : {'Content-Type' : 'application/json'}};
		$http.get(urlConsulta,config)
		.then(function(response){
			deferred.resolve(response.data);
		},function(error){
			deferred.reject(error);
		});
		return deferred.promise;
	}
	
	fac.getAnios = function(){
		
		var deferred = $q.defer();
	    var config = { headers : {'Content-Type' : 'application/json'}};
		$http.get(urlAnios,config)
		.then(function(response){
			deferred.resolve(response.data);
		},function(error){
			deferred.reject(error);
		});
		return deferred.promise;
	}
	
	fac.getChart = function(){
		var deferred = $q.defer();
		var config = { headers : {'Content-Type' : 'application/json'}};
		$http.get(urlTemp,config)
		.then(function(response){
			deferred.resolve(response.data);
		},function(error){
			deferred.reject(error);
		});
		return deferred.promise;
	}
	
	return fac;
});
