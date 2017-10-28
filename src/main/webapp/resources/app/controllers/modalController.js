app.controller('modalController',         
        function($scope, regresionService, $uibModalInstance, items, tipo) {
	
		console.log(items);
	$scope.tipo = tipo;
	$scope.items = items;
		
	  $scope.titulo = tipo == 'C' ? 'Coeficiente de Correlacion' : 'Error Estandar de Estimacion';
	
	  $scope.ok = function () {
	    $uibModalInstance.close('closed');
	  };

	  $scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
});