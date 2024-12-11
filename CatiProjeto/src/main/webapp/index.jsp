<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Equipamentos para Aluguel</title>

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Estilo básico para a lista de equipamentos */
        #equipamentos-lista div {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 10px;
            background-color: #f9f9f9;
        }
    </style>

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</head>
<body>

    <!-- Cabeçalho -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Equipamentos</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Início</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Contato</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- Conteúdo -->
    <div class="container mt-5">
        <h1 class="mb-4">Equipamentos para Aluguel</h1>

        <!-- Container onde os equipamentos serão listados -->
        <div id="equipamentos-lista">
            <!-- Equipamentos serão exibidos aqui -->
        </div>
    </div>

    <!-- Rodapé -->
    <footer class="footer mt-auto py-3 bg-light">
        <div class="container">
            <span class="text-muted">Equipamentos para Aluguel © 2024</span>
        </div>
    </footer>

    <script>
        $(document).ready(function() {
            // Realizar a requisição para a servlet que retorna os equipamentos em JSON
            $.ajax({
                url: 'equipamentos', // Endereço da servlet
                method: 'GET',
                dataType: 'json',
                success: function(data) {
                    // Verificar se os dados foram recebidos corretamente
                    console.log(data);

                    // Selecionar o elemento onde os equipamentos serão exibidos
                    var listaEquipamentos = $('#equipamentos-lista');
                    listaEquipamentos.empty(); // Limpar conteúdo anterior

                    // Iterar sobre os dados e adicionar as informações ao HTML com jQuery
                    $.each(data, function(index, equipamento) {
                        console.log("Índice: " + index);
                        console.log("Nome: " + equipamento.nome);
                        console.log("Preço: " + equipamento.preco);
                        console.log("Descrição: " + equipamento.descricao);

                        // Criar o HTML do equipamento com jQuery
                        var equipamentoHTML = $('<div>', { class: 'equipamento' })
                            .append($('<h3>').text(equipamento.nome))
                            .append($('<p>').html('<strong>Preço:</strong> R$ ' + equipamento.preco.toFixed(2)))
                            .append($('<p>').html('<strong>Descrição:</strong> ' + equipamento.descricao))
                        .append(
                                $('<button>', {
                                    text: 'Ver Detalhes',
                                    class: 'btn btn-info',
                                    click: function() {
                                        window.location.href = '/CatiProjeto/detalhesequipamento.jsp?id=' + equipamento.idEquipamento;
                                    }
                                })
                            );

                        // Adicionar o HTML ao elemento #equipamentos-lista
                        listaEquipamentos.append(equipamentoHTML);
                    });

                    console.log('Informações adicionadas à página.');
                },
                error: function(xhr, status, error) {
                    console.error('Erro ao carregar equipamentos: ' + error);
                }
            });
        });
    </script>

</body>
</html>
