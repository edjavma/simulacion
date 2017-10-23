<div class="panel panel-default" style="margin-top: 5%;">
	<form name="form" class="form-horizontal"> 
	<div class="panel-body">
	<div class="alert alert-warning" ng-if="!show">
		<span style="color:black;"><strong>Info!</strong> No hay data Disponible.</span>	  
	</div>
	<div class="form-group">
	    <label class="control-label col-sm-2" for="email">Tipo:</label>
	    <div class="col-sm-10">
	      <select class="form-control" ng-model = "data.tipo"  required>
			<option value="M">Mensual</option>
			<option value="Y">Anual</option>
		</select>
	    </div>
	  </div>
	
	  <div class="form-group" ng-if="data.tipo != null && data.tipo != undefined">
	    <label class="control-label col-sm-2" for="email">{{data.tipo == 'Y' ? 'Cantidad de años: ' : 'Cantidad de años: '}}</label>
	    <div class="col-sm-10">
	      <input type="number" class="form-control" placeholder="Cantidad de años a calcular"  ng-model = "data.anios" required/>
	    </div>
	  </div>
	  		
	   <div class="form-group" ng-if="data.tipo != null && data.tipo != undefined">
	    <label class="control-label col-sm-2" for="email">{{data.tipo == 'Y' ? 'Datos desde: ' : 'Seleccione Año'}}</label>
	    <div class="col-sm-10">
	      <select class="form-control" ng-model = "data.desde" ng-init="getYears()" required>
			<option ng-repeat="anio in anios" value="{{anio}}">{{anio}}</option>
		</select>
	    </div>
	  </div>
	  
	   <div class="form-group" ng-if="data.tipo != null && data.tipo != undefined && data.tipo == 'M'">
	    <label class="control-label col-sm-2" for="email">Seleccione Mes</label>
	    <div class="col-sm-10">
	      <select class="form-control" ng-model = "data.mes" ng-init="getMeses()" required>
			<option ng-repeat="m in meses" value="{{m.value}}">{{m.label}}</option>
		</select>
	    </div>
	  </div>
	  
	</div>
	<div class="panel-footer text-right">
		<input type="button" class="btn btn-success" ng-click="consultar()" ng-disabled="form.$invalid" value="Consultar"/>
	</div>
	</form>
</div>
<hr>
<div class="panel panel-default" ng-if="resultados.length > 0 && show">
	<div class="panel-body">
		 <div class="form-group" >
			    <label class="control-label col-sm-2" for="email">Cambiar Graficos</label>
			    <div class="col-sm-7">
			      <select class="form-control" ng-model = "chart.value" >
					<option value="BarChart">BarChart</option>
					<option value="LineChart">LineChart</option>
					<option value="PieChart">PieChart</option>
					<option value="ColumnChart">ColumnChart</option>
					<option value="AreaChart">AreaChart</option>
				</select>
			    </div>
			    <div class="col-sm-3">
			    	<label class="checkbox-inline"><input type="checkbox" ng-model="product.disabled"  ng-change="summaryChecked()">Productos {{product.disabled}}</label>
			    </div>
		</div>
	</div>
</div>
<div class="panel panel-default" ng-if="resultados.length > 0 && show">
	<div class="panel-body">
	<h1 class="text-center">Tabla de valores</h1>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>X</th>
					<th>Y</th>
					<th>X2</th>
					<th>Y2</th>
					<th>XY</th>
					<th ng-if="val.anio != null && val.anio != undefined">Anio</th>
					<th ng-if="val.mes != null && val.mes != undefined">mes</th>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="val in values">
					<td>{{val.x}}</td>
					<td>{{val.y}}</td>
					<td>{{val.x2}}</td>
					<td>{{val.y2}}</td>
					<td>{{val.xy}}</td>
					<td ng-if="val.anio != null && val.anio != undefined">{{val.anio}}</td>
					<td ng-if="val.mes != null && val.mes != undefined">{{val.mes}}</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<hr/>
<div ng-switch="product.disabled" ng-if="resultados.length > 0 && show">
 <div ng-switch-when="false">
	<div     google-chart chart="principalChartObject" style="height:600px; width:100%;"></div>
</div>
<div ng-switch-when="true">	
	<div    google-chart chart="objMultipleChart" style="height:600px; width:100%;"></div>
</div>
</div>
<hr>
<div class="panel panel-default" ng-if="resultados.length > 0 && show">
	<div class="panel-body">
	<h1 class="text-center">Resultados</h1>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Anio</th>
					<th>Ventas</th>					
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="res in resultados">
					<td>{{res.label}}</td>
					<td>{{res.value}}</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<!-- <div class="panel panel-default" ng-if="resultados.length > 0 && show">
	<div class="panel-body text-center">
		<div fusioncharts
		    width="600" 
		    height="400"
		    type="column2d"
		    datasource="{{dataSource}}"
		    ></div>
	</div>
</div>-->
<hr>
<div  ng-if="resultados.length > 0 && show" google-chart chart="myChartObject" style="height:600px; width:100%;"></div>
<br>