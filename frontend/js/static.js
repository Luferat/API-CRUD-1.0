import { config } from './config.js';

export function about() {

    $('title').html(config.appName + ' - Sobre');
    let main = `

<h2>Sobre o ${config.appName}</h2>
<p>Este é um aplicativo experimental para consumir a "API CRUD Spring Boot".</p>
<p>Para saber mais, acesse o <a href="https://github.com/Luferat/API-CRUD-1.0.1" target="_blank">repositório GitHub</a>.</p>

    `;

    $('main').html(main);

}