import { config } from './config.js';

export function listAllIdName() {
    $('title').html(config.appName + ' - Todos os Itens');
    $.getJSON(`${config.apiBaseURL}/stuff`, function(data) {

    console.log(data)

       let main = '<h2>Todos os Itens</h2><ul>';

        for (let key in data) {
            let item = data[key];
            main += `<li><a href="?stuff&${item.id}">${item.name}</a></li>`;
        }
        main += '</ul>';
        $('main').html(main);
    });
}

export function getOne(id){
    $.getJSON(`${config.apiBaseURL}/stuff/${id}`, function(data){
        $('title').html(config.appName + ' - ' + data.name);
        let main = `

<h2>${data.name}</h2>
<img src="${data.photo}" alt="${data.name}">
<ul>
  <li><strong>ID: </strong>${data.id}</li>
  <li><strong>Descrição: </strong>${data.description}</li>
  <li><strong>Localização: </strong>${data.location}</li>
  <li><strong>Categorias: </strong>
  <ul>
        `;
        for (let k in data.categories) {
            main += `
    <li>ID ${data.categories[k].id}: ${data.categories[k].name} - ${data.categories[k].description}</li>
                    `;
        }
        main += `
</ul></ul>
<a href="?stuffedit&${data.id}">Editar</a>
<a href="?stuffdelete&${data.id}" onclick="return confirm('Tem certeza que deseja apagar ${data.name}?')">Apagar</a>
        `;

        $('main').html(main);
    });
}

export function listAll() {
    $('title').html(config.appName + ' - Todos os Itens');
    $.getJSON(`${config.apiBaseURL}/stuff/list`, function(data) {
        let main = '<h2>Todos os Itens</h2>';
        for (let key in data) {
            let item = data[key];
            main += `
<div class="item">
    <h3>${item.name}</h3>
    <img src="${item.photo}" alt="${item.name}">
    <ul>
      <li><strong>ID: </strong>${item.id}</li>
      <li><strong>Descrição: </strong>${item.description}</li>
      <li><strong>Localização: </strong>${item.location}</li>
      <li><strong>Categorias: </strong>
          <ul>`;
            for (let k in item.categories) {
                main += `
              <li><strong>ID: </strong>${item.categories[k].id}</li>
              <li><strong>Nome: </strong>${item.categories[k].name}</li>
              <li><strong>Descrição: </strong>${item.categories[k].description}</li>
              &nbsp;
                `;
            }
            main += `
            </ul></ul>
                <a href="index.html?stuff&${item.id}">Editar</a>
        </div>
            `;
        }
        $('main').html(main);
    });
}

export function deleteItem(id){
    $('title').html(config.appName + ' - Apagar Item');
    $('main').html(`<h2>Apagando Item</h2>`);
    $.ajax({
        url: `${config.apiBaseURL}/stuff/${id}/delete`,
        type: 'PATCH',
        contentType: 'application/json',
        success: function (response) {
            alert(`Item apagado com sucesso!`);
            location.href='?';
        },
        error: function (xhr) {
            let errorMessage = xhr.responseJSON?.message || 'Erro ao deletar o item.';
            console.error('Erro:', errorMessage);
            $('main').append(`<p style="color:red;">${errorMessage}</p>`);
        }
    });
}

export function newStuff() {
    $('title').html(config.appName + ' - Novo Item');

    $.getJSON(`${config.apiBaseURL}/category`, function(data) {
        let selectCat = '';
        for (let key in data) {
            selectCat += `<option value="${data[key].id}">${data[key].name}</option>`;
        }

        let form = `

<form method="post" name="newstuff" id="formNewStuff">
    <p>
        <label for="stuffName">Nome:</label>
        <input type="text" name="name" id="stuffName" required minlength="3">
    </p>
    <p>
        <label for="stuffDescription">Descrição:</label>
        <input type="text" name="description" id="stuffDescription" required minlength="3">
    </p>
    <p>
        <label for="stuffPhoto">URL da Foto:</label>
        <input type="text" name="photo" id="stuffPhoto">
    </p>
    <p>
        <label for="stuffLocation">Localização:</label>
        <input type="text" name="location" id="stuffLocation" required minlength="3">
    </p>
    <p>
        <label for="stuffCategories">Categorias:</label>
        <select name="categories" id="stuffCategories" multiple size="5">
            ${selectCat}
        </select>
    </p>
    <p>
        <button type="submit">Salvar</button>
        <button type="button" onclick="location.href='?categories'">Listar Categorias</button>
    </p>
</form>

        `;

        $('main').html(form)

    });
}

export function editItem(id) {
    $.getJSON(`${config.apiBaseURL}/stuff/${id}`, function(stuff) {
        console.log(stuff);

        $.getJSON(`${config.apiBaseURL}/category`, function(data) {
        let selectedCategories = stuff.categories.map(cat => cat.id); // Extraindo os IDs
        console.log('IDs das categorias selecionadas:', selectedCategories);

        let selectCat = '';
        for (let key in data) {
            let categoryId = data[key].id;
            let isSelected = selectedCategories.includes(categoryId) ? 'selected' : '';
            selectCat += `<option value="${categoryId}" ${isSelected}>${data[key].name}</option>`;
        }

            let form = `
<form method="post" name="editstuff" id="formEditStuff">
    <input type="hidden" name="id" id="StuffId" value="${stuff.id}">
    <p>
        <label for="stuffName">Nome:</label>
        <input type="text" name="name" id="stuffName" required minlength="3" value="${stuff.name}">
    </p>
    <p>
        <label for="stuffDescription">Descrição:</label>
        <input type="text" name="description" id="stuffDescription" required minlength="3" value="${stuff.description}">
    </p>
    <p>
        <label for="stuffPhoto">URL da Foto:</label>
        <input type="text" name="photo" id="stuffPhoto" value="${stuff.photo}">
    </p>
    <p>
        <label for="stuffLocation">Localização:</label>
        <input type="text" name="location" id="stuffLocation" required minlength="3" value="${stuff.location}">
    </p>
    <p>
        <label for="stuffCategories">Categorias:</label>
        <select name="categories" id="stuffCategories" multiple size="5">
            ${selectCat}
        </select>
    </p>
    <p>
        <button type="submit">Salvar</button>
        <button type="button" onclick="location.href='?categories'">Listar Categorias</button>
    </p>
</form>
            `;

            $('main').html(form);
        });
    });
}
