<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Equipamento</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="container mt-5">
        <h1>Detalhes do Equipamento</h1>

        <div id="detalhes-equipamento">
            <!-- Informações do equipamento serão exibidas aqui -->
        </div>
        <button id="alugar-button" class="btn btn-primary">Alugar</button>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            
            var urlParams = new URLSearchParams(window.location.search);
            var idEquipamento = urlParams.get('id');

            
            $.ajax({
                url: '/CatiProjeto/equipamentos?id=' + idEquipamento,
                method: 'GET',
                dataType: 'json',
                success: function(data) {
                   
                    var detalhes = $('#detalhes-equipamento');
                    detalhes.empty(); 

                   
                    detalhes.append('<h3>' + data.nome + '</h3>');
                    detalhes.append('<p><strong>Preço:</strong> R$ ' + data.preco.toFixed(2) + '</p>');
                    detalhes.append('<p><strong>Descrição:</strong> ' + data.descricao + '</p>');
                    
                    $.ajax({
                        url: '/CatiProjeto/estoques?idEquipamento=' + idEquipamento, 
                        method: 'GET',
                        dataType: 'json',
                        success: function(estoqueData) {
                            
                            var idEstoque = estoqueData.idEstoque; 

                            
                            $('#alugar-button').click(function() {
                                
                                window.location.href = '/CatiProjeto/reservarEquipamento.jsp?&idEstoque=' + idEstoque;
                            });
                        },
                        error: function(xhr, status, error) {
                            console.error('Erro ao carregar os detalhes do estoque: ' + error);
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.error('Erro ao carregar os detalhes do equipamento: ' + error);
                }
            });
        });
    </script>

</body>
</html>