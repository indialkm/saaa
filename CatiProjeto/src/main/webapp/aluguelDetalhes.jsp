<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Atualizar Endereço</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery.mask.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<body>
   <div class="container mt-5">
    <h2>Atualizar Endereço do Aluguel</h2>
    <form id="formEndereco">

        <div class="form-group">
            <label for="entregaCEP">CEP:</label>
            <input type="text" class="form-control cep" id="entregaCEP" name="entregaCEP">
            
        </div>

        <div class="form-group">
            <label for="entregaEndereco">Endereço:</label>
            <input type="text" class="form-control" id="entregaEndereco" name="entregaEndereco">
        </div>

        <div class="form-group">
            <label for="entregaNumero">Número:</label>
            <input type="text" class="form-control" id="entregaNumero" name="entregaNumero">
        </div>

        <div class="form-group">
            <label for="entregaCompl">Complemento:</label>
            <input type="text" class="form-control" id="entregaCompl" name="entregaCompl">
        </div>

        <div class="form-group">
            <label for="entregaBairro">Bairro:</label>
            <input type="text" class="form-control" id="entregaBairro" name="entregaBairro">
        </div>

        <div class="form-group">
            <label for="entregaCidade">Cidade:</label>
            <input type="text" class="form-control" id="entregaCidade" name="entregaCidade">
        </div>

        <div class="form-group">
            <label for="entregaUF">UF:</label>
            <input type="text" class="form-control" id="entregaUF" name="entregaUF">
        </div>


        <div class="form-group">
            <label for="entregaTelefone">Telefone:</label>
            <input type="text" class="form-control phone" id="entregaTelefone" name="entregaTelefone">
        </div>

        <div class="form-group">
            <label for="entregaRefer">Referência:</label>
            <input type="text" class="form-control" id="entregaRefer" name="entregaRefer">
        </div>

        <button type="button" class="btn btn-primary" id="atualizarBtn">Atualizar Endereço</button>
    </form>

    <div id="resultado" class="mt-3" style="display: none;"></div>

   
    <div id="botaoPedido" class="mt-3" style="display: none;">
        <a id="linkPedido" class="btn btn-success" href="#">Ir para Pedido</a>
    </div>
</div>



<script>
    $(document).ready(function () {
    	
    	
    	// http://igorescobar.github.io/jQuery-Mask-Plugin/docs.html
    	$(document).ready(function(){
    	    $('.date').mask('00/00/0000');
    	    $('.time').mask('00:00:00');
    	    $('.cep').mask('00000-000');
    	    $('.phone').mask('(00) 00000-0000');
    	    $('.cpf').mask('000.000.000-00');
    	    $('.money').mask('000.000.000.000,00');
    	});
    	
    	
        function getIdFromURL() {
            const urlParams = new URLSearchParams(window.location.search);
            return urlParams.get('idAluguel');
        }
        
        function limpa_formulário_cep() {
            // Limpa valores do formulário de cep.
            $("#rua").val("");
            $("#bairro").val("");
            $("#cidade").val("");
            $("#uf").val("");
            $("#ibge").val("");
        }
        
        $("#entregaCEP").blur(function() {
        	
                 //Nova variável "cep" somente com dígitos.
                 var cep = $(this).val().replace(/\D/g, '');

                 //Verifica se campo cep possui valor informado.
                 if (cep != "") {

                     //Expressão regular para validar o CEP.
                     var validacep = /^[0-9]{8}$/;

                     //Valida o formato do CEP.
                     if(validacep.test(cep)) {

                         //Preenche os campos com "..." enquanto consulta webservice.
                         $("#entregaEndereco").val("...");
                         $("#entregaBairro").val("...");
                         $("#entregaCidade").val("...");
                         $("#entregaUF").val("...");

                         //Consulta o webservice viacep.com.br/
                         $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

                             if (!("erro" in dados)) {
                                 //Atualiza os campos com os valores da consulta.
                                 $("#entregaEndereco").val(dados.logradouro);
                                 $("#entregaBairro").val(dados.bairro);
                                 $("#entregaCidade$("input.telefone").mask("(99) 9999-9999");").val(dados.localidade);
                                 $("#entregaUF").val(dados.uf);
                             } //end if.
                             else {
                                 //CEP pesquisado não foi encontrado.
                                 limpa_formulário_cep();
                                 alert("CEP não encontrado.");
                             }
                         });
                     } //end if.
                     else {
                         //cep é inválido.
                         limpa_formulário_cep();
                         alert("Formato de CEP inválido.");
                     }
                 } //end if.
                 else {
                     //cep sem valor, limpa formulário.
                     limpa_formulário_cep();
                 }
        });
        
        $("input.telefone").mask("(99) 9999-9999");
        
        $('#botaoPedido').on('click', function () {
            const idAluguel = getIdFromURL(); 

            if (idAluguel) {
                
                window.location.href = '/CatiProjeto/pedido.jsp?idAluguel=' + idAluguel;
            } else {
                $('#resultado')
                    .html('<p class="text-danger">ID do aluguel não encontrado.</p>')
                    .show();
            }
        });

        const idAluguel = getIdFromURL();

        if (!idAluguel) {
            $('#resultado').html('<p class="text-danger">ID do aluguel não encontrado na URL.</p>').show();
            return;
        }

        $('#atualizarBtn').on('click', function () {
            const dadosFormulario = {
                idAluguel: idAluguel, 
                entregaEndereco: $('#entregaEndereco').val(),
                entregaNumero: $('#entregaNumero').val(),
                entregaCompl: $('#entregaCompl').val(),
                entregaBairro: $('#entregaBairro').val(),
                entregaCidade: $('#entregaCidade').val(),
                entregaUF: $('#entregaUF').val(),
                entregaCEP: $('#entregaCEP').val(),
                entregaTelefone: $('#entregaTelefone').val(),
                entregaRefer: $('#entregaRefer').val()
            };
            
            if (!dadosFormulario.entregaEndereco || !dadosFormulario.entregaNumero || !dadosFormulario.entregaCompl || !dadosFormulario.entregaBairro || !dadosFormulario.entregaCidade || !dadosFormulario.entregaUF || !dadosFormulario.entregaCEP || !dadosFormulario.entregaTelefone || !dadosFormulario.entregaRefer) {
                $('#resultado').html('<p class="text-danger">Por favor, preencha todos os campos.</p>').show();
                return;
            }

            $.ajax({
                url: '/CatiProjeto/alugueis', 
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(dadosFormulario),
                success: function (response) {
                    if (response.message) {
                        $('#resultado')
                            .removeClass('alert-danger')
                            .addClass('alert-success')
                            .html('<p><strong>' + response.message + '</strong></p>')
                            .show();

                        // Exibe o botão de pedido sem a necessidade de idPedido
                        $('#botaoPedido').show(); 
                    } else {
                        // Caso o PUT falhe, exibe a mensagem de erro
                        $('#resultado')
                            .removeClass('alert-success')
                            .addClass('alert-danger')
                            .html('<p><strong>Erro!</strong> Não foi possível atualizar o endereço. Tente novamente.</p>')
                            .show();
                    }
                },
                error: function () {
                    // Em caso de erro na requisição AJAX
                    $('#resultado')
                        .removeClass('alert-success alert-danger')
                        .addClass('alert-warning')
                        .html('<p><strong>Erro!</strong> Não foi possível processar a solicitação. Tente novamente.</p>')
                        .show();
                }
            });

        });
    });
</script>

</body>
</html>
