app.controller('importController',         
        function($scope, importService, moment) {
	
       $scope.data = {};
       $scope.resultados = [];
       $scope.dataSource = {};
       $scope.show = true;
       $scope.values = [];
       $scope.chart = {};
       
       $scope.myChartObject = {};
       
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
       
       $scope.fileChange = function(file){
    	   $scope.data.file = file[0];
    	   console.log($scope.data.file);
       }
       
       $scope.consultar = function(){
    	   $scope.show = true;
    	   if($scope.data != null && $scope.data != undefined){
    		   
    		  	var fd = new FormData();
    	        //Take the first selected file
    	        fd.append("file", $scope.data.file, $scope.data.file.name);	   
    	        fd.append("desde", $scope.data.fecha);
    	        fd.append("anios", $scope.data.anios);
    	        fd.append("tipo", $scope.data.tipo);
    	        if($scope.data.mes != null && $scope.data.mes != undefined){
    	        	fd.append("mes", $scope.data.mes);
    	        }
    	        
    		   
    		   importService.consulta(fd)
    		   .then(function(data){
    			   $scope.data = {};
    			   $('#myFile').val('');
    			   $scope.resultados = data.resultados;
    			   $scope.values = data.resultsValues;
    			   $scope.dataSource.chart = {caption: "Resultados"};
    			   $scope.dataSource.data = $scope.resultados;
    			   
    			   $scope.myChartObject = data.barChart;
    		   },function(error){
    			   $scope.show = false;
    			   $scope.data = {};
    			  // $scope.values = [];
    			   $scope.resultados = [];
    			   $scope.dataSource = {};
    			   console.log(error);
    		   });
    	   }
       }
       
       
       $scope.$watch('chart.value', function(NewValue, OldValue) {
    	   if($scope.myChartObject.options != null && $scope.myChartObject.options != undefined){
    		   console.log($scope.chart);
    		   $scope.myChartObject.type = $scope.chart.value;   
    	   }
    	   
       }, true);
           
});