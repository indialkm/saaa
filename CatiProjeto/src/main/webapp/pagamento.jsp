<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pedido Processado</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <div id="messageContainer"></div>
    </div>

    <script>
        $(document).ready(function() {
            
            function getIdFromURL() {
                const urlParams = new URLSearchParams(window.location.search);
                return urlParams.get('idPedido');
            }

            const idPedido = getIdFromURL();

            
            if (idPedido) {
               
                $('#messageContainer').html(''); 
                $('#messageContainer').append('<div class="alert alert-success" role="alert">');
                $('#messageContainer').append('<p>Pedido Processado com Sucesso!</p>');
                $('#messageContainer').append('<p><strong>ID do Pedido:</strong> ' + idPedido + '</p>');
                $('#messageContainer').append('<a href="/CatiProjeto/index.jsp" class="btn btn-primary btn-sm">Voltar para o Índice</a>');
                $('#messageContainer').append('</div>');
            } else {
                
                $('#messageContainer').html(''); 
                $('#messageContainer').append('<div class="alert alert-danger" role="alert">');
                $('#messageContainer').append('<p><strong>Erro!</strong> ID do Pedido não encontrado.</p>');
                $('#messageContainer').append('<a href="/CatiProjeto/index.jsp" class="btn btn-primary btn-sm">Voltar para o menu</a>');
                $('#messageContainer').append('</div>');
            }
        });
    </script>
</body>
</html>
