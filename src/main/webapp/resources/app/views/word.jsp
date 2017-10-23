<div class="panel panel-default" style="margin-top: 5%;">
	<form name="form">
	<div class="panel-body">
		<!-- <input type="number" class="form-control" placeholder="Cantidad de años a calcular"  ng-model = "data.anios" required/>-->
		<input type="file" name="file" onchange="angular.element(this).scope().fileChange(this.files)" />
	</div>
	<div class="panel-footer text-right">
		<input type="button" class="btn btn-success" ng-click="guardar()" ng-disabled="form.$invalid" value="Guardar"/>
	</div>
	</form>
</div>