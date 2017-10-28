<%-- 
    Document   : principal
    Created on : 20/09/2017, 08:35:57 AM
    Author     : ejmorales
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Principal</title>
        
         <!-- jquery -->
        <link rel="stylesheet" type="text/css" href="<c:url value='resources/css/jquery/jquery-ui.min.css'/>" ></link>
    
        <!-- bootstrap -->
        <link rel="stylesheet" type="text/css" href="<c:url value='resources/css/bootstrap/css/bootstrap.min.css' />" ></link>
        <link rel="stylesheet" type="text/css" href="<c:url value='resources/css/bootstrap/css/bootstrap-theme.min.css'/>" ></link>
    
        <!-- Autocomplete -->
        <link rel="stylesheet" type="text/css" href="<c:url value='resources/css/angularstrap/docs.min.css'/>" ></link>
        <link rel="stylesheet" type="text/css" href="<c:url value='resources/css/angularstrap/libs.min.css' />" ></link>	
                
        <!-- Angular material -->
        <link rel="stylesheet" type="text/css" href="<c:url value='resources/css/angular/angular-material.css'/>" ></link>	
        
        <!-- Angular Table -->
        <link rel="stylesheet" type="text/css" href="<c:url value='resources/css/angular/ng-table.min.css'/>" ></link>
        
        <link rel="stylesheet" href="<c:url value='resources/css/font-awesome/css/font-awesome.min.css'/>" ></link>
        
        <!-- Ionicons -->
		  <link rel="stylesheet" href="<c:url value='resources/css/Ionicons/css/ionicons.min.css'/>" ></link>
		  <!-- Theme style -->
		  <link rel="stylesheet" href="<c:url value='resources/css/AdminLTE.min.css'/>" ></link>
		  <!-- AdminLTE Skins. Choose a skin from the css/skins
		       folder instead of downloading all of them to reduce the load. -->
		  <link rel="stylesheet" href="<c:url value='resources/css/skins/_all-skins.min.css'/>" ></link>
		  <style type="text/css">
		  .editable-table > tbody > tr > td {
			  padding: 4px
			}
			.editable-text {
			  padding-left: 4px;
			  padding-top: 4px;
			  padding-bottom: 4px;
			  display: inline-block;
			}
			.editable-table tbody > tr > td > .controls {
			  //width: 100%
			}
			.editable-input {
			  padding-left: 3px;
			}
			.editable-input.input-sm {
			  height: 30px;
			  font-size: 14px;
			  padding-top: 4px;
			  padding-bottom: }
		  </style>
    </head>
    <body ng-app="modelApp" class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">
    	<header class="main-header">
		    <!-- Logo -->
		    <a href="index2.html" class="logo">
		      <!-- mini logo for sidebar mini 50x50 pixels -->
		      <span class="logo-mini"><b>UMG</b></span>
		      <!-- logo for regular state and mobile devices -->
		      <span class="logo-lg"><b>Simulacion</b>UMG</span>
		    </a>
		    <!-- Header Navbar: style can be found in header.less -->
		    <nav class="navbar navbar-static-top">
		      <!-- Sidebar toggle button-->
		      <a style="cursor:pointer;" class="sidebar-toggle" data-toggle="push-menu" role="button">
		        <span class="sr-only">Toggle navigation</span>
		      </a>
		
		    </nav>
		  </header>
		  
		<aside class="main-sidebar">
	    <!-- sidebar: style can be found in sidebar.less -->
	    <section class="sidebar">
	      <!-- Sidebar user panel -->
	      <div class="user-panel">
	      </div>
	     
	      <!-- sidebar menu: : style can be found in sidebar.less -->
	      <ul class="sidebar-menu" data-widget="tree">
	        <li class="header">MAIN NAVIGATION</li>
	           <li>
	          <a href="#!/">
	            <i class="fa fa-files-o"></i> <span>Ingreso</span>
	            <span class="pull-right-container">
	              <small class="label pull-right bg-green">new</small>
	            </span>
	          </a>
	        </li>
	       <li class="treeview">
	          <a href="#">
	            <i class="fa fa-share"></i> <span>Minimos Cuadrados</span>
	            <span class="pull-right-container">
	              <i class="fa fa-angle-left pull-right"></i>
	            </span>
	          </a>
	          <ul class="treeview-menu">
	         
	        <li>
	          <a href="#!/consulta">
	            <i class="fa fa-edit"></i> <span>Consulta</span>
	            <span class="pull-right-container">
	              <small class="label pull-right bg-green">new</small>
	            </span>
	          </a>
	        </li>
	        
	        <li>
	          <a href="#!/upload">
	            <i class="fa fa-folder"></i> <span>Importar Xls</span>
	            <span class="pull-right-container">
	              <small class="label pull-right bg-green">new</small>
	            </span>
	          </a>
	        </li>
	          </ul>
	        </li> 
	       <li class="treeview">
	          <a href="#">
	            <i class="fa fa-share"></i> <span>Regresion Lineal</span>
	            <span class="pull-right-container">
	              <i class="fa fa-angle-left pull-right"></i>
	            </span>
	          </a>
	          <ul class="treeview-menu">
	         
	        <li>
	          <a href="#!/regresion">
	            <i class="fa fa-edit"></i> <span>Consulta</span>
	            <span class="pull-right-container">
	              <small class="label pull-right bg-green">new</small>
	            </span>
	          </a>
	        </li>
	        
	        <li>
	          <a href="#!/importRegresion">
	            <i class="fa fa-folder"></i> <span>Importar Xls</span>
	            <span class="pull-right-container">
	              <small class="label pull-right bg-green">new</small>
	            </span>
	          </a>
	        </li>
	          </ul>
	        </li> 
	      </ul>
	    </section>
	    <!-- /.sidebar -->
	  </aside>
	  
	  <div class="content-wrapper">
	    <!-- Main content -->
	    <section class="content">
	   		 <div ng-view></div>
	    </section>
    <!-- /.content -->
  </div>
    </div>
        
         <!-- Angularjs -->
        <script src="<c:url value='resources/js/angular/angular.min.js'/>" ></script>
        <script src="<c:url value='resources/js/angular/angular-route.min.js'/>" ></script>
        <script src="<c:url value='resources/js/angular/angular-animate.min.js'/>" ></script>
        <script src="<c:url value='resources/js/angular/angular-aria.js'/>" ></script>
        <script src="<c:url value='resources/js/angular/angular-material.js'/>" ></script>
        
        <!-- jquery y bootstrap -->
        <script src="<c:url value='resources/js/jquery/jquery-1.11.3.min.js'/>" ></script>
        <script src="<c:url value='resources/js/jquery/jquery-ui.min.js'/>" ></script>
        <script src="<c:url value='resources/js/bootstrap/bootstrap.min.js'/>" ></script>
        <script src="<c:url value='resources/js/adminlte.min.js'/>" ></script>
        
        <script src="<c:url value='resources/js/angular-strap/angular-strap.js'/>" ></script>
        <script src="<c:url value='resources/js/angular-strap/angular-strap.tpl.js'/>" ></script>
                <!-- Datepicker -->
        <script src="<c:url value='resources/js/angular-strap/datepicker.js'/>" ></script>
        <script src="<c:url value='resources/js/angular-strap/datepicker.tpl.js'/>" ></script>
        
       
        <!-- momentjs -->	
        <script src="<c:url value='resources/js/moment/moment-with-locales.js'/>" ></script>
        <script src="<c:url value='resources/js/moment/moment-precise-range.js'/>" ></script>
         <script src="<c:url value='resources/js/moment/angular-moment.min.js'/>" ></script>
        
         <!-- Componentes de Bootstrap escritos en angularjs -->
        <script src="<c:url value='resources/js/ui-bootstrap/ui-bootstrap-tpls-0.14.3.min.js'/>" ></script>
        
        <script type="text/javascript" src="<c:url value='resources/js/google/ng-google-chart.min.js'/>" ></script>
        
          <!-- Angular Table -->
         <script src="<c:url value='resources/js/angular/ng-table.min.js'/>" ></script>
         <script src="<c:url value='resources/js/lodash.min.js'/>" ></script>
        
        <script type="text/javascript" src="<c:url value='resources/js/angular/fusioncharts.js'/>" ></script>
        <script type="text/javascript" src="<c:url value='resources/js/angular/fusioncharts.charts.js'/>" ></script>
        <script type="text/javascript" src="<c:url value='resources/js/angular/angular-fusioncharts.min.js'/>" ></script>
        
         <!-- Configuracion de angular y ruteos -->
        <script src="<c:url value='resources/app/app.js'/>" ></script>
        
        <!--  Configuracion de controladores -->
        <script src="<c:url value='resources/app/controllers/modeloController.js'/>" ></script>       
        <script src="<c:url value='resources/app/controllers/resultController.js'/>" ></script>
        <script src="<c:url value='resources/app/controllers/importController.js'/>" ></script>
        <script src="<c:url value='resources/app/controllers/wordController.js'/>" ></script>
        <script src="<c:url value='resources/app/controllers/regresionController.js'/>" ></script>
        <script src="<c:url value='resources/app/controllers/importRegController.js'/>" ></script>
        <script src="<c:url value='resources/app/controllers/modalController.js'/>" ></script>
        
        <!--  Configuracion de servicios -->
        <script src="<c:url value='resources/app/services/modeloService.js'/>" ></script>
        <script src="<c:url value='resources/app/services/resultService.js'/>" ></script>
        <script src="<c:url value='resources/app/services/importService.js'/>" ></script>
        <script src="<c:url value='resources/app/services/wordService.js'/>" ></script>
        <script src="<c:url value='resources/app/services/regresionService.js'/>" ></script>
        <script src="<c:url value='resources/app/services/importRegService.js'/>" ></script>
        
        <!--  Directivas  -->
         <script type="text/javascript" src="<c:url value='resources/app/directives/trackedTable.js'/>" ></script>
		<script type="text/javascript" src="<c:url value='resources/app/directives/trackedTableCell.js'/>" ></script>
		<script type="text/javascript" src="<c:url value='resources/app/directives/trackedTableRow.js'/>" ></script>
    </body>
</html>
