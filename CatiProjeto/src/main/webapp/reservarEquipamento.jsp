<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Verificar e Reservar</title>

<!-- Bootstrap CSS -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	rel="stylesheet">

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="container mt-5">
		<h1 class="text-center mb-4">Verificar e Reservar</h1>

		<!-- Formulário para entrada do usuário -->
		<form id="verificarDisponibilidadeForm">
			<div class="form-group">
				<label for="dataInicio">Data de Início:</label> <input type="date"
					class="form-control" id="dataInicio" name="dataInicio" required>
			</div>

			<div class="form-group">
				<label for="dataFim">Data de Fim:</label> <input type="date"
					class="form-control" id="dataFim" name="dataFim" required>
			</div>

			<button type="button" id="verificarBtn"
				class="btn btn-primary btn-block">Verificar Disponibilidade</button>
			<button type="button" id="reservarBtn"
				class="btn btn-success btn-block mt-3" style="display: none;">Reservar</button>
		</form>

		<h2 class="mt-4">Resultado</h2>
		<div id="resultado" class="alert" style="display: none;"></div>
		<button id="proximaTelaBtn" class="btn btn-primary"
			style="display: none;">
			<i class="fas fa-arrow-right"></i> Ir para Aluguel
		</button>

	</div>

	<script>
        $(document).ready(function () {
          
            function getIdFromURL() {
		    const urlParams = new URLSearchParams(window.location.search);
		    return urlParams.get('idEstoque'); 
		}
		
		const idEstoque = getIdFromURL();
		
		
		$('#verificarBtn').on('click', function () {
		    const dataInicio = $('#dataInicio').val();
		    const dataFim = $('#dataFim').val();
		
		    console.log("ESSE É O ID:" + idEstoque);
		    console.log(dataInicio);
		    console.log(dataFim);
		
		   
		    if (!idEstoque || !dataInicio || !dataFim) {
		        $('#resultado').html('<p class="text-danger">Por favor, preencha todos os campos.</p>').show();
		        return;
		    }
		
		    
		    $.ajax({
		        url: '/CatiProjeto/reservas?action=verificar&idEstoque=' + idEstoque,
		        method: 'GET',
		        data: {
		            dataInicio: dataInicio,
		            dataFim: dataFim
		        },
		        success: function (response) {
		        	 //console.log("Resposta do servidor:", response);
		            if (response.disponivel) {
		                $('#resultado')
		                    .removeClass('alert-danger')
		                    .addClass('alert-success')
		                    .html('') 
			                .append('<p><strong>Disponível!</strong> O período está livre para reserva.</p>')
			                .append('<p><strong>Valor Total:</strong> R$ ' + response.valorTotal.toFixed(2) + '</p>')
			                .append('<p><strong>Quantidade de Dias:</strong> ' + response.quantidadeDias + ' dia(s)</p>')
			                .show();
		
		                $('#reservarBtn').show();
		            } else {
		                $('#resultado')
		                    .removeClass('alert-success')
		                    .addClass('alert-danger')
		                    .html('<p><strong>Indisponível!</strong> O período já está reservado.</p>')
		                    .show();
		                
		                
	
		                $('#reservarBtn').hide();
		            }
		        },
		        error: function () {
		            $('#resultado')
		                .removeClass('alert-success alert-danger')
		                .addClass('alert-warning')
		                .html('<p><strong>Erro!</strong> Não foi possível verificar a disponibilidade. Tente novamente.</p>')
		                .show();
		        }
		    });
		});


          
            $('#reservarBtn').on('click', function () {
                const idEstoque = getIdFromURL();
                const dataInicio = $('#dataInicio').val();
                const dataFim = $('#dataFim').val();

              
                if (!idEstoque || !dataInicio || !dataFim) {
                    $('#resultado').html('<p class="text-danger">Por favor, preencha todos os campos.</p>').show();
                    return;
                }

                
                $.ajax({
                    url: 'reservas', 
                    method: 'POST',
                    contentType: 'application/json', 
                    data: JSON.stringify({
                        idEstoque: idEstoque,
                        dataInicio: dataInicio,
                        dataFim: dataFim
                    }),
                    success: function (response) {
                    	//console.log("Aqui " + response);
                        if (response) {
                            $('#resultado')
                                .removeClass('alert-sucess alert-warning')
                                .addClass('alert-success')
                                .html('<p><strong>Reservado com sucesso, mas é necesssário confirmar o pagamento para bloquear a reserva da data!</strong> O período ainda pode ser escolhido por outro cliente.</p>')
                                .show();
                            
                            console.log(response);
                            $('#proximaTelaBtn').show();
                            
                            $('#proximaTelaBtn').on('click', function() {
                                var idAluguel = response.idAluguel;
                                if (idAluguel) {
                                 
                                    window.location.href = '/CatiProjeto/aluguelDetalhes.jsp?idAluguel=' + idAluguel;
                                    console.log(idAluguel);
                                } else {
                                    alert('Erro: Não foi possível obter os detalhes do aluguel.');
                                }
                            });
                       
                        } else {
                            $('#resultado')
                                .removeClass('alert-danger alert-warning')
                                .addClass('alert-danger')
                                .html('<p><strong>Erro!</strong> Não foi possível realizar a reserva. Tente novamente.</p>')
                                .show();
                        }
                    },            
                    
                }); 

            });
        });
    </script>

	<!-- Bootstrap JS (opcional para interações) -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
