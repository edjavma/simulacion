app.controller('wordController',         
        function($scope, wordService, moment) {
	
       $scope.data = {};
       
       $scope.fileChange = function(file){
    	   $scope.data.file = file[0];
    	   console.log($scope.data.file);
       }
       
       $scope.guardar = function(){
    	   
    	   if($scope.data != null && $scope.data != undefined){
    		   
    		  	var fd = new FormData();
    	        //Take the first selected file
    	        fd.append("file", $scope.data.file, $scope.data.file.name);	   
    		   
    		   wordService.consulta(fd)
    		   .then(function(data){
    			   $scope.data = {};    			   
    			   console.log(data);
    		   },function(error){
    			   console.log(error);
    		   });
    	   }
       }
           
});