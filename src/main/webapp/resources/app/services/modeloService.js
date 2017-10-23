app.factory('modeloService',function($http,$q){
	
	var urlVenta = '/Econometrico/crear';
	var urlUpdate = '/Econometrico/modificar';
	var urlListar = '/Econometrico/listar';
	var urlProductos = '/Econometrico/productos';
	var fac = {};
	
	fac.crearVenta = function(data){
		console.log(data);
		var deferred = $q.defer();
	    //var config = { params: data, headers : {'Content-Type' : 'application/json'}};
		$http.post(urlVenta,data)
		.then(function(response){
			deferred.resolve(response.data);
		},function(error){
			deferred.reject(error);
		});
		return deferred.promise;
	}
	
	fac.listarVentas = function(){
		var deferred = $q.defer();
		$http.get(urlListar)
		.then(function(response){
			deferred.resolve(response.data);
		},function(error){
			deferred.reject(error);
		})
		return deferred.promise;
	}
	
	fac.listarProductos = function(){
		var deferred = $q.defer();
		$http.get(urlProductos)
		.then(function(response){
			deferred.resolve(response.data);
		},function(error){
			deferred.reject(error);
		})
		return deferred.promise;
	}
	
	fac.update = function(data){
		var deferred = $q.defer();
		$http.post(urlUpdate,data)
		.then(function(response){
			deferred.resolve(response.data);
		},function(error){
			deferred.reject(error);
		});
		return deferred.promise;
	}
	
	return fac;
});
