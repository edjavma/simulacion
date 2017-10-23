app.controller('regresionController',         
        function($scope, regresionService, moment) {
	
       $scope.data = {};
       $scope.values = [];
       $scope.anios = [];
       $scope.resultados = []; 
       $scope.dataSource = {};
       $scope.meses = [];
       $scope.show = true;
       $scope.chart = {};
       
$scope.getMeses = function(){
	$scope.show = true;
	$scope.meses = [];
    	   $scope.meses.push({
				value: 1,
				label: 'Enero'
		   });
    	   $scope.meses.push({
				value: 2,
				label: 'Febrero'
			});
    	   $scope.meses.push({
				value: 3,
				label: 'Marzo'
			});
    	   $scope.meses.push({
				value: 4,
				label: 'Abril'
			});
    	   $scope.meses.push({
				value: 5,
				label: 'Mayo'
			});
    	   $scope.meses.push({
				value: 6,
				label: 'Junio'
			});
    	   $scope.meses.push({
				value: 7,
				label: 'Julio'
			});
    	   $scope.meses.push({
				value: 8,
				label: 'Agosto'
			});
    	   $scope.meses.push({
				value: 9,
				label: 'Septiembre'
			});
    	   $scope.meses.push({
				value: 10,
				label: 'Octubre'
			});
    	   $scope.meses.push({
				value: 11,
				label: 'Noviembre'
			});
    	   $scope.meses.push({
				value: 12,
				label: 'Diciembre'
			});
       }
       
       $scope.getYears = function(){
    	   $scope.show = true;
    	   regresionService.getAnios()
    	   .then(function(data){
    		   console.log(data);
    		   $scope.anios = data;
    	   }, function(error){
    		  console.log(error);
    	   });
       }
       
       $scope.consultar = function(){
    	   $scope.show = true;
    	   if($scope.data != null && $scope.data != undefined){
    		  
    		   	   
    		   
    		   regresionService.consulta($scope.data)
    		   .then(function(data){
    			   $scope.data = {};
    			   $scope.values = data.values;
    			   $scope.resultados = data.resultados;
    			   
    			   $scope.dataSource.chart = {caption: "Resultados"};
    			   $scope.dataSource.data = $scope.resultados;
    			   
    			   $scope.myChartObject = data.barChart;
    		   },function(error){
    			   $scope.show = false;
    			   $scope.data = {};
    			   $scope.values = [];
    			   $scope.resultados = [];
    			   $scope.dataSource = {};
    			   console.log(error);
    		   });
    	   }
       }
       
       $scope.$watch('chart.value', function(NewValue, OldValue) {
    	   console.log(NewValue);
    	   console.log($scope.chart);
    	   if($scope.myChartObject.options != null && $scope.myChartObject.options != undefined){
    		   console.log($scope.chart);
    		   $scope.myChartObject.type = $scope.chart.value;   
    	   }
    	   
       }, true);
           
});