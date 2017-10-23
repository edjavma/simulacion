app.controller('modeloController',         
        function($scope, modeloService, moment, NgTableParams, $filter) {
	
       $scope.data = {};
       $scope.ventas = [];
       $scope.originalData = [];
       $scope.productos = [];
      
       
      
       
       $scope.listar = function(){
    	   modeloService.listarVentas()
    	   .then(function(data){
    		   $scope.ventas = data;
    		   $scope.originalData = angular.copy($scope.ventas);
			   $scope.userTable = new NgTableParams({count:10}, {counts: [] ,dataset: $scope.ventas});
			   $scope.getProductos();
    	   },function(error){
    		   console.log(error);
    	   });
       }
       
       $scope.getProductos = function(){
    	   modeloService.listarProductos()
    	   .then(function(data){
    		   console.log(data);
    		   $scope.productos = data;
    	   },function(error){
    		   console.log(error);
    	   });
       }
       
       
       $scope.guardar = function(){
    	   
    	   if($scope.data != null && $scope.data != undefined){
    		  
    		   var stringFecha = moment($scope.data.fecha).format('DD/MM/YYYY');
    		   
    		   
    		   modeloService.crearVenta($scope.data)
    		   .then(function(data){
    			   alert(data.message);
    			   $scope.data = {};
    			   $scope.listar();
    		   },function(error){
    			   console.log(error);
    		   });
    	   }
       }
       
       
       $scope.cancel = function(venta, rowForm) {
	      var originalRow = resetRow(venta, rowForm);
	      angular.extend(venta, originalRow);
	      $('#productoselect').val('');
	    }

      

	    $scope.save = function(venta, rowForm) {
	    	console.log(venta);
	       modeloService.update(venta)
		    .then(function(data){
		    	var originalRow = resetRow(venta, rowForm);
			    angular.extend(originalRow, venta);
		    },function(error){
		    	$scope.cancel(venta, rowForm);
		    });	     
	    }
		
		function resetRow(venta, rowForm){
		  venta.edit = false;
	      rowForm.$setPristine();
	      var newTemp = $filter("filter")($scope.originalData, {correlativo:venta.correlativo});
	      return newTemp[0];

	      /*$scope.tableTracker.untrack(user);
	      return _.findWhere(originalData, function(r){
	        return r.code === user.code;
	      });*/
	    }
           
           
});
       
