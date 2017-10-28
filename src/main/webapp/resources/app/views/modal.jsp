<div class="modal-header">
            <h3 class="modal-title" id="modal-title">{{titulo}}</h3>
        </div>
        <div class="modal-body" id="modal-body">
           <table class="table table-stripped">
           	<thead>
           		<tr>
           			<th>X</th>
           			<th>Y</th>
           			<th>Y Promedio</th>
           			<th>Y - Y Promedio</th>
           			<th>(Y - Y Promedio)2</th>
           			<th>Y = a + bx</th>
           			<th>YPrima - Y</th>
           			<th>()YPrima - Y)2</th>
           		</tr>
           	</thead>
           	<tbody>
           		<tr ng-repeat="val in items.data">
           			<td>{{val.x}}</td>
           			<td>{{val.y}}</td>
           			<td>{{val.promedio}}</td>
           			<td>{{val.yPromedio}}</td>
           			<td>{{val.yPromedio2}}</td>
           			<td>{{val.yValor}}</td>
           			<td>{{val.yPrima}}</td>
           			<td>{{val.yPrima2}}</td>
           		</tr>
           		<tr>
           			<td></td>
           			<td></td>
           			<td></td>
           			<td></td>
           			<td>{{items.sumatoria.yPromedio2}}</td>
           			<td></td>
           			<td></td>
           			<td>{{items.sumatoria.yPrima2}}</td>
           		</tr>
           	</tbody>
           </table>
           <hr>
           <div  ng-if="tipo == 'C'">
           	<img alt="" src="resources/img/coeficiente_correlacion.png">
           	<label>r = <strong>{{items.correlacion}}</strong></label>
           	</div>
          <div ng-if="tipo == 'E'"> 
          		<img  alt="" src="resources/img/coeficiente_error.png">
          		<label>s = <strong>{{items.errorEstandar}}</strong></label>
          </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" type="button" ng-click="ok()">OK</button>
            <button class="btn btn-warning" type="button" ng-click="cancel()">Cancel</button>
        </div>