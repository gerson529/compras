<%@page import="dominio.CotacaoMaterial"%>
<%@page import="dominio.Material"%>
<%@page import="dominio.Cotacao"%>
<%@page import="dominio.CompraMaterial"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Set"%>
<%@page import="dominio.Compra"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="dominio.Categoria"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Compras</title>

		<!-- Bootstrap -->
		<link href="css/jquery-ui.css" rel="stylesheet">
		<link href="css/bootstrap.min.css" rel="stylesheet">

		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="js/jquery.js"></script>
		<script src="js/jquery-ui.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="js/bootstrap.min.js"></script>
	</head>
	<body>
		<div class="container">
			<%
				Compra compra = (Compra) request.getAttribute("compra");
				Set<CompraMaterial> compras_materiais = compra.getCompraMaterials();
				Set<Cotacao> compra_cotacoes = compra.getCotacaos();
				String dataString = "";
			%>
			<%
				List materiais = (List) request.getAttribute("materiais");
				String options = "";
				for (Iterator iteratorM = materiais.iterator(); iteratorM.hasNext();) {
					Material material = (Material) iteratorM.next();
					options = options + "\t<option value='" + material.getId() + "'>" + material.getNome() + "</option>";
				}

			%>
			<% String fornecedores = request.getAttribute("fornecedores").toString();%>
			<%
				if (compra.getDataPrazo() != null) {
					dataString = new java.text.SimpleDateFormat("dd/MM/yyyy").format(compra.getDataPrazo());
				}
			%>
			<script type="text/javascript" src="js/jmask.js"></script>
			<script type="text/javascript" src="js/compras_form.js"></script>
			<p class="lead">
				<a href="../index.jsp">Home</a> :: <a href="./compra/index.jsp">Compras</a> :: Cadastro
			</p>

			<form role="form" id='frmCompra'  method='post' action='ServletCompra'>
				<input type='hidden' name='encaminhar' value='ActionCompra'/>
				<input type='hidden' name='cmd' value='ATUALIZAR'/>
				Cod. Compra: <input class="form-control" type='text' id='id_compra' name='compra_id' value='<%=compra.getId()%>' readonly='readonly' /> 
				<input type='submit' value='s' style='width:1px;height:1px;float:right;border:#fff;'><br/>

				Prazo de Compra: <input class="form-control" type='text' id='compra_data' name='data' value='<%=dataString%>' /><br/>
			</form>
			<div class='row'>
				<div id='materiais'  class='col-md-6'><h4>Itens/Materiais</h4>
					<form class='form-inline' id='frmCompraMaterial' method='post' action='servletController'>
						<input  type='hidden' name='encaminhar' value='ActionCompraMaterial'/>
						<input  type='hidden' name='metodo' value='adicionar'/>
						<select class="form-control" name='material_id'>
							<option value="">[ Selecione um Material ]</option>
							<%=options%>
						</select>
						<input class="form-control" type='text' name='quantidade'>
						<input class='btn btn-info' type='submit' value='Inserir'>
					</form>
					<table class='table table-bordered table-striped'>

						<%
							try {
								//session.setAttribute("compra_materiais", compras_materiais);
								int i = 0;
								for (Iterator iterator2 = compras_materiais.iterator(); iterator2.hasNext();) {
									CompraMaterial compra_material = (CompraMaterial) iterator2.next();
						%>
						<tr class='<%=compra_material.getId()%>'>
							<td><a rel='<%=compra_material.getId()%>' href='#' class='deleteCompraMaterial'><i class='glyphicon glyphicon-trash'></i></a></td>
							<td><%=compra_material.getMaterial().getNome()%></td>
							<td class='<%=compra_material.getMaterial().getId()%>quantidade'><%=compra_material.getQuantidade()%> </td>
						</tr>
						<%
									i++;
								}
							} catch (Exception e) {
							}
						%>
					</table></div>
				<div id='cotacoes' class='col-md-6 well'><h4>Cotações</h4>
					<form class='form-inline' id='frmCotacao' method='post' action='ServletCompra'>
						<input type='hidden' name='encaminhar' value='ActionCotacao'/>
						<input type='hidden' name='metodo' value='adicionar'/>
						<select class="form-control" name='fornecedor_id'>
							<option value="">[ Selecione um Fornecedor ]</option>
							<%=fornecedores%>
						</select>
						<input class='btn btn-success' type='submit' value='Inserir'>
					</form>
					<table class='table table-bordered table-striped'>
						<%
							try {
								//session.setAttribute("compra_cotacoes", compra_cotacoes);
								int i = 0;
								Float total;
								for (Iterator iterator2 = compra_cotacoes.iterator(); iterator2.hasNext();) {
									Cotacao cotacao = (Cotacao) iterator2.next();
									total = 0f;
									String hiddens = "";
									for (Iterator tota = cotacao.getCotacaoMaterials().iterator(); tota.hasNext();) {
										CotacaoMaterial cotacao_material = (CotacaoMaterial) tota.next();
										hiddens += "<input type='hidden' rel='" + cotacao_material.getMaterial().getId() + "' class='hidden_cotmat' value='" + cotacao_material.getValorUnitario() + "'>";
									}
						%>
						<tr class='<%=cotacao.getId()%>'>
							<td><a rel='<%=cotacao.getId()%>' href='#' class='deleteCotacao'><i class='glyphicon glyphicon-trash'></i></a></td>
							<td><a rel='<%=cotacao.getId()%>' href='#' class='editCotacao'><%=cotacao.getFornecedorId()%></a></td>
							<td><span class='<%=cotacao.getId()%>total'><%=total%></span><%=hiddens%></td>
						</tr>
						<%
									i++;
								}
							} catch (Exception e) {
							}
						%>
					</table></div>
				<div class='row'>
					<input class='btn btn-large btn-block btn-primary' type='button' id='frmSalvar' value='Atualizar'/>
					</form>
					<br/><a class='btn btn-default' href='compra/index.jsp'>Voltar</a>
					<div class="modal fade" id="modalCotacao" tabindex="-1" role="dialog" aria-labelledby="modalCotacaoLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="modalCotacaoLabel">Modal title</h4>
								</div>
								<div class="modal-body">
									...
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>