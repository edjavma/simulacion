var app = angular.module("modelApp",['ngRoute', 'ui.bootstrap','mgcrea.ngStrap.datepicker','angularMoment','ng-fusioncharts','googlechart','ngTable']);

app.config(function ($routeProvider,  $datepickerProvider) {
    $routeProvider        
        .when('/', {
            templateUrl: 'resources/app/views/modelo.jsp',
            controller: 'modeloController'
        })
        .when('/consulta', {
            templateUrl: 'resources/app/views/resultado.jsp',
            controller: 'resultController'
        })
        .when('/upload', {
            templateUrl: 'resources/app/views/import.jsp',
            controller: 'importController'
        })
        .when('/import', {
            templateUrl: 'resources/app/views/word.jsp',
            controller: 'wordController'
        })
        .when('/regresion', {
            templateUrl: 'resources/app/views/regresion.jsp',
            controller: 'regresionController'
        })
        .when('/importRegresion', {
            templateUrl: 'resources/app/views/importRegresion.jsp',
            controller: 'importRegController'
        })
        .otherwise({redirectTo:'/'});
    
    angular.extend($datepickerProvider.defaults, {
        dateFormat: 'dd/MM/yyyy',
        startWeek: 1,
      //  startView: 1,  
        hasToday: true,
       // startDate: 'today',
     //   daysOfWeekDisabled: '0,6'
    });
});
