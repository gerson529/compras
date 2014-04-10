function AtualizarTotalInicial() {
	$('#cotacoes table tr').each(function(k) {
		var total = 0;
		var cotacao_id = $('#cotacoes table tr:eq(' + k + ')').attr('class');

		$('#cotacoes table tr.' + cotacao_id + ' .hidden_cotmat').each(function(key, value) {
			var span = '#cotacoes table tr.' + cotacao_id + ' .hidden_cotmat:eq(' + key + ')';
			var quantidade = 'div#materiais table .' + $(span).attr('rel') + 'quantidade';
			var itemq = 0;
			$(quantidade).each(function(i) {
				itemq = itemq + (1 * $(quantidade + ':eq(' + i + ')').text());
			});
			total = total + (
					($(span).val() * 1)
					* itemq
					);
		});

		$('span.' + cotacao_id + 'total').text(total);
	})
}
function AtualizarTotal(cotacao_id) {
	var total = 0;


	$('div#modalCotacao span.' + cotacao_id + 'valor_unitario').each(function(key, value) {
		var span = 'div#modalCotacao span.' + cotacao_id + 'valor_unitario:eq(' + key + ')';
		var quantidade = 'div#materiais table .' + $(span).attr('rel') + 'quantidade';
		var itemq = 0;
		$(quantidade).each(function(i) {
			itemq = itemq + (1 * $(quantidade + ':eq(' + i + ')').text());
		});
		total = total + (
				($(span).text() * 1)
				* itemq
				);
	});

	$('span.' + cotacao_id + 'total').text(total);
}
$(function() {
	var DATE_PICKER_CONFIG = {
		dateFormat: "dd/mm/yy",
		dayNamesMin: ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"],
		monthNames: ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"],
		monthNamesShort: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
		changeMonth: true,
		changeYear: true
	};
	$('#compra_data').mask('99/99/9999').datepicker(DATE_PICKER_CONFIG);

	$('#frmCompraMaterial').submit(function(e) {
		e.preventDefault();
		var data = $(this).serialize();
		data = data + '&compra_id=' + $('#id_compra').val();
		$.post('/compras/compra_material/servletController', data
				, function(response) {
					$('div#materiais table').append(response);
				}, 'html');
	});

	$('#frmCotacao').submit(function(e) {
		e.preventDefault();
		var data = $(this).serialize();
		data = data + '&compra_id=' + $('#id_compra').val();
		$.post('/compras/cotacao/servletController', data
				, function(response) {
					$('div#cotacoes table').append(response);
				}, 'html');
	});


	$('#frmSalvar').click(function(e) {
		e.preventDefault();
		$('form#frmCompra').submit();
	});
	$(document).on('submit', 'form#frmCotacaoMaterial', function(e) {
		e.preventDefault();
		var data = $('form#frmCotacaoMaterial').serialize(),
		cotacao_id = $('form#frmCotacaoMaterial #cotacao_id').val();

		$.post('/compras/cotacao_material/servletController',
				data
				, function(response) {
					if ((response * 1) !== 0) {
						$('div#modalCotacao table').append(response);
						AtualizarTotal(cotacao_id);
					}
				});
	});
	$(document).on('click', 'a.deleteCotacaoMaterial', function(e) {
		e.preventDefault();
		var id = $(this).attr('rel');
		cotacao_id = $('form#frmCotacaoMaterial #cotacao_id').val();

		$.post('/compras/cotacao_material/servletController', {
			encaminhar: "ActionCotacaoMaterial",
			metodo: 'excluir',
			indice: id
		}, function(response) {
			if ((response * 1) !== 0) {
				$('div#modalCotacao table tr.' + id).slideUp().remove();
				AtualizarTotal(cotacao_id);
			}
		});
	});
	$(document).on('click', 'a.deleteCompraMaterial', function(e) {
		e.preventDefault();
		var id = $(this).attr('rel');
		$.post('/compras/compra_material/servletController', {
			encaminhar: "ActionCompraMaterial",
			metodo: 'excluir',
			indice: id
		}, function(response) {
			if ((response * 1) !== 0) {
				$('div#materiais table tr.' + id).slideUp().remove();
			}
		});
	});
	$(document).on('click', 'a.deleteCotacao', function(e) {
		e.preventDefault();
		var cotacao_id = $(this).attr('rel');
		$.post('/compras/cotacao/servletController', {
			encaminhar: "ActionCotacao",
			metodo: 'excluir',
			cotacao_id: cotacao_id
		}, function(response) {
			if ((response * 1) !== 0) {
				$('div#cotacoes table tr.' + cotacao_id).slideUp().remove();
			}
		});
	});
	$(document).on('click', 'a.editCotacao', function(e) {
		e.preventDefault();
		var cotacao_id = $(this).attr('rel');
		$.post('/compras/cotacao/servletController', {
			encaminhar: "ActionCotacao",
			metodo: 'edit',
			cotacao_id: cotacao_id
		}, function(resp) {
			$('div#modalCotacao div.modal-body').html(resp);


			$('div#modalCotacao div.modal-body #frmCotacaoMaterial select option').each(function(i, val) {
				if (i > 0 && $('table .' + val.value + 'quantidade').length === 0) {
					//alert(val.value);
					$('form#frmCotacaoMaterial select option[value=' + val.value + ']').html('').remove();

				}
			});
			$('div#modalCotacao').modal('show');
		});
	});


	AtualizarTotalInicial();

})
        