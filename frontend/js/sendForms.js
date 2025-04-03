import { config } from './config.js';

$(document).on('submit', '#formNewCategory', function (event) {
    event.preventDefault();

    let formData = {
        name: $('#catName').val().trim(),
        description: $('#catDescription').val().trim()
    };

    $.ajax({
        url: `${config.apiBaseURL}/category`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function (response) {
            $('#formNewCategory').trigger('reset');
            alert('Categoria salva com sucesso!');
            location.href='?newcategory';
        },
        error: function (xhr) {
            let errorMessage = xhr.responseJSON?.message || 'Erro ao salvar a categoria.';
            alert(`Oooops!\n\n${errorMessage}`);
            location.href='?newcategory';
        }
    });
});

$(document).on('submit', '#formEditCategory', function(event) {
    event.preventDefault();

    let formData = {
        id: $('#catId').val(),
        name: $('#catName').val().trim(),
        description: $('#catDescription').val().trim()
    };

    $.ajax({
        url: `${config.apiBaseURL}/category/${formData.id}/edit`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function (response) {
            $('#formEditCategory').trigger('reset');
            alert('Categoria atualizada com sucesso!');
            location.href='?categories';
        },
        error: function (xhr) {
            let errorMessage = xhr.responseJSON?.message || 'Erro ao atualizar a categoria.';
            console.log(xhr.responseText);
            alert(`Oooops!\n\n${errorMessage}`);
            location.href='?categories';
        }
    });

});

$(document).on('submit', '#formNewStuff', function(event){
    event.preventDefault();
    console.log('enviando')

    let formData = {
        name: $('#stuffName').val().trim(),
        description: $('#stuffDescription').val().trim(),
        photo: $('#stuffPhoto').val().trim(),
        location: $('#stuffLocation').val().trim(),
        categories: $('#stuffCategories').val()
    };

    $.ajax({
        url: `${config.apiBaseURL}/stuff`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function(response) {
            $('#formNewStuff').trigger('reset');
            alert('Item cadastrado com sucesso!');
            location.href = '?newstuff';
        },
        error: function(xhr) {
            let errorMessage = xhr.responseJSON?.message || 'Erro ao cadastrar o item.';
            alert(`Oooops!\n\n${errorMessage}`);
            location.href = '?newstuff';
        }
    });

});