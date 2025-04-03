import { config } from './config.js';

export function listAll() {
    $('title').html(config.appName + ' - Todas as Categorias');
    $.getJSON(`${config.apiBaseURL}/category`, function(data) {

    let main = `
<h2>Lista de Categorias</h2>
<a href="?newcategory">Nova categoria</a>
    `;

        for (let key in data) {
            let item = data[key];

            main += `
            <div class="item">
                <p>
                    <strong>ID ${item.id}: ${item.name}</strong><br>
                    <small>${item.description}</small><br>
                    <a href="?catedit&${item.id}">Editar</a>
                    <a href="?catdelete&${item.id}" onclick="return confirm('Tem certeza que deseja apagar esta categoria?')">Apagar</a>
                </p>
            </div>
            `;

        }

        $('main').html(main);

    });
}

export function addNew() {

    $('title').html(config.appName + ' - Nova Categoria')
    let main = `
<h2>Nova Categoria</h2>
<form method="post" name="newcategory" id="formNewCategory">
    <p>
        <label for="catName">Nome:</label>
        <input type="text" name="name" id="catName" required minlength="3">
    </p>
    <p>
        <label for="catDescription">Descrição:</label>
        <input type="text" name="description" id="catDescription" required minlength="3">
    </p>
    <p>
        <button type="submit">Salvar</button>
        <button type="button" onclick="location.href='?categories'">Listar Categorias</button>
    </p>
</form>
    `;
    $('main').html(main);
}

export function deleteItem(id) {
    $('title').html(config.appName + ' - Apagar Categoria');
    $('main').html(`<h2>Apagando Categoria</h2>`);
    $.ajax({
        url: `${config.apiBaseURL}/category/${id}/delete`,
        type: 'PATCH',
        contentType: 'application/json',
        success: function (response) {
            alert(`Categoria apagada com sucesso!`);
            location.href='?categories';
        },
        error: function (xhr) {
            let errorMessage = xhr.responseJSON?.message || 'Erro ao apagar a categoria.';
            console.error('Erro:', errorMessage);
            $('main').append(`<p style="color:red;">${errorMessage}</p>`);
        }
    });
}

export function editItem(id) {
    $('title').html(config.appName + ' - Editar Categoria')
    $.getJSON(`${config.apiBaseURL}/category/${id}`, function(data) {
        let main = `

<h2>Editar Categoria</h2>
<form method="post" name="editcategory" id="formEditCategory">
    <input type="hidden" name="id" id="catId" value="${data.id}">
    <p><strong>ID: </strong><code>${data.id}</code>
    <p>
        <label for="catName">Nome:</label>
        <input type="text" name="name" id="catName" required minlength="3" value="${data.name}">
    </p>
    <p>
        <label for="catDescription">Descrição:</label>
        <input type="text" name="description" id="catDescription" required minlength="3" value="${data.description}">
    </p>
    <p>
        <button type="submit">Salvar</button>
        <button type="button" onclick="location.href='?categories'">Listar Categorias</button>
    </p>
</form>

        `;

        $('main').html(main);
    });
}