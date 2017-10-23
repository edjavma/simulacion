app.factory('importRegService',function($http,$q){
	
	var urlConsulta = '/Econometrico/regresion/upload';
	var fac = {};
	
	fac.consulta = function(data){
	
		var deferred = $q.defer();
	    var config = { headers : {'Content-Type' : undefined}};
		$http.post(urlConsulta,data, config)
		.then(function(response){
			deferred.resolve(response.data);
		},function(error){
			deferred.reject(error);
		});
		return deferred.promise;
	}
	
	return fac;
});
