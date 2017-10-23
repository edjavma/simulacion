<div class="panel panel-default" style="margin-top: 5%;">
    <div class="panel-body">
        <form name="form" class="form-horizontal">
  
  <div class="form-group">
  <label class="control-label col-sm-2" for="fechaPermiso">Fecha Venta</label>  
   <div class="has-feedback col-sm-10">
	    <input type="text" class="form-control" ng-model="data.fecha" name="fechaPermiso" id = "fechaPermiso" data-autoclose="1" bs-datepicker  required>
	    <span class="glyphicon glyphicon-calendar form-control-feedback"></span>
	  </div>
	</div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="total">Precio Producto:</label>
    <div class="col-sm-10">
      <input type="number" ng-model="data.total" class="form-control" id="total" placeholder="" required>
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="total">Cantidad:</label>
    <div class="col-sm-10">
      <input type="number" ng-model="data.cantidad" class="form-control" id="total" placeholder="" required>
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="total">Producto:</label>
    <div class="col-sm-10">
      <select ng-options="producto.descripcion for producto in productos" ng-model="data.producto" class="editable-input form-control input-sm" required>
      </select>
    </div>
  </div>
  <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <button type="button" ng-click="guardar()" class="btn btn-default" ng-disabled="form.$invalid">Guardar</button>
    </div>
  </div>
</form>
    </div>
</div>
<hr/>
<div class="panel panel-default" ng-init="listar()">
	<div class="panel-body">
	<!-- <table class="table table-striped">
			<thead>
			<tr>
				<th>Fecha</th>
				<th>Total</th>
			</tr>
			</thead>
			<tbody>
			<tr ng-repeat="venta in ventas">
				<td>{{venta.fecha | date:'dd/MM/yyyy'}}</td>
				<td>{{venta.total | currency :'Q.' :2}}</td>
			</tr>
			</tbody>
		</table>-->
		<table ng-table="userTable" class="table table-striped editable-table demoTable" show-filter="true" ng-form="tableForm"
		demo-tracked-table="tableTracker">
			 <colgroup>
	          <col width="10%" />
	          <col width="10%" />
	          <col width="10%" />
	          <col width="60%" />
	          <col width="10%" />
	  
	        </colgroup>
			<tr ng-repeat="venta in $data"  ng-form="rowForm" demo-tracked-table-row="venta" edit="venta.edit">
				<td class="text-left" title="'Fecha'">
					<label>{{venta.fecha | date:'dd/MM/yyyy'}}</label></td>
				<td class="text-center" title="'Precio'" ng-switch="venta.edit" ng-class="price.$dirty ? 'bg-warning' : ''" ng-form="price" demo-tracked-table-cell>
					<label ng-switch-default class="editable-text" >{{venta.total | currency :'Q.' :2}}</label>
					<div class="controls" ng-class="price.$invalid && price.$dirty ? 'has-error' : ''" ng-switch-when="true">
			          <input type="number" name="price" ng-model="venta.total" class="editable-input form-control input-sm" required/>
			        </div>
					</td>				
				<td class="text-center" title="'Cantidad'" ng-switch="venta.edit" ng-class="count.$dirty ? 'bg-warning' : ''" ng-form="count" demo-tracked-table-cell>
					<label ng-switch-default class="editable-text" >{{venta.cantidad}}</label>
					<div class="controls" ng-class="count.$invalid && count.$dirty ? 'has-error' : ''" ng-switch-when="true">
			          <input type="number" name="price" ng-model="venta.cantidad" class="editable-input form-control input-sm" required/>
			        </div>
				</td>
				<td class="text-center" title="'Producto'" ng-switch="venta.edit" ng-class="product.$dirty ? 'bg-warning' : ''" ng-form="product" demo-tracked-table-cell>
					<label ng-switch-default class="editable-text" >{{venta.producto.descripcion}}</label>
					<div class="controls" ng-class="product.$invalid && product.$dirty ? 'has-error' : ''" ng-switch-when="true">
<!-- 				<select id="productoselect" ng-model="venta.producto" class="editable-input form-control input-sm" required>
						<option ng-repeat="producto in productos" value="{{producto}}">{{producto.descripcion}}</option>
					</select>	-->		
					<select ng-options="producto.descripcion for producto in productos" ng-model="venta.producto" class="editable-input form-control input-sm" required>
			         
			   </select>          
			        </div>
					</td>		
				<td class="text-center" title="'Editar'">
					<a href="" ><span ng-click="save(venta,rowForm)" ng-if="venta.edit" ng-hide="rowForm.$pristine || rowForm.$invalid" class="glyphicon glyphicon-ok"></span></a>
					<a href="" ><span ng-click="cancel(venta,rowForm)" ng-if="venta.edit" class="glyphicon glyphicon-remove"></span></a>
					<a href="" ><span ng-click="venta.edit = true" ng-if="!venta.edit" class="glyphicon glyphicon-pencil"></span></a>
				</td>
				
			</tr>
		</table>
	</div>
</div>
        
