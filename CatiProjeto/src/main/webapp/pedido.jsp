<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Pedido</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h2>Detalhes do Pedido</h2>
        <div id="pedidoDetalhes" class="mt-4">
            <!-- O ID do Pedido será exibido aqui -->
        </div>
         <button id="btnPagamento" class="btn btn-primary">Pagar</button>
    </div>

    <script>
    $(document).ready(function() {
        // Função para buscar os detalhes do pedido
        function buscarDetalhesPedido(idAluguel) {
            $.ajax({
                url: '/CatiProjeto/pedidos',
                method: 'GET',
                data: { action: 'detalhes', idAluguel: idAluguel },
                dataType: 'json',
                success: function(data) {
                    const idPedido = data.idPedido;
                    console.log('ID do Pedido:', idPedido);
                    console.log('Detalhes do Pedido:', data);

                    // Configurar o evento do botão de pagamento
                    $('#btnPagamento').off('click').on('click', function() {
                        $.ajax({
                            url: '/CatiProjeto/pagamentos',
                            method: 'PUT',
                            contentType: 'application/json', 
                            data: JSON.stringify({ 
                                idPedido: idPedido 
                            }),
                            success: function(response) {
                                window.location.href = '/CatiProjeto/pagamento.jsp?idPedido=' + idPedido;
                            },
                            error: function() {
                                alert('Erro ao processar o pagamento.');
                            }
                        });
                    });
                
                    // Limpar a área de detalhes
                    $('#pedidoDetalhes').html('');

                    // Exibir os detalhes do pedido
                    if (data.idPedido) {
                        $('#pedidoDetalhes').append('<h4><strong>ID do Pedido:</strong> ' + data.idPedido + '</h4>');
                    }

                    // Exibir os detalhes de aluguel, se existirem
                    if (data.aluguels && data.aluguels.length > 0) {
                        $.each(data.aluguels, function(index, aluguel) {
                            var aluguelHTML = 
                                '<div class="card mb-3">' +
                                    '<div class="card-body">' +
                                        '<h5 class="card-title">' + aluguel.equipamento.nome + '</h5>' +
                                        '<p class="card-text">' + aluguel.equipamento.descricao + '</p>' +
                                        '<p><strong>Data de Reserva:</strong> ' + aluguel.reserva.dataInicio + ' a ' + aluguel.reserva.dataFim + '</p>' +
                                        '<p><strong>Valor Total:</strong> R$ ' + aluguel.valorTotal.toFixed(2) + '</p>' +
                                    '</div>' +
                                '</div>';
                            
                            $('#pedidoDetalhes').append(aluguelHTML);
                        });
                    } else {
                        $('#pedidoDetalhes').append('<p>Nenhum aluguel encontrado para este pedido.</p>');
                    }
                },
                error: function() {
                    $('#pedidoDetalhes').append('<p>Erro ao carregar os detalhes do pedido.</p>');
                }
            });
        }

        // Função para pegar o ID do aluguel da URL
        function getIdFromURL() {
            const urlParams = new URLSearchParams(window.location.search);
            return urlParams.get('idAluguel');
        }

        const idAluguel = getIdFromURL();

        // Verifica se o ID do aluguel foi encontrado na URL
        if (!idAluguel) {
            $('#pedidoDetalhes').html('<p class="text-danger">ID do aluguel não encontrado na URL.</p>').show();
            return;
        }

        // Chama a função para buscar os detalhes do pedido com o ID do aluguel
        buscarDetalhesPedido(idAluguel);
    });
    </script>

</body>
</html>
