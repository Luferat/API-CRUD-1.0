import * as stuffHandle from './stuff.js';
import * as categoryHandle from './category.js';
import * as staticHandle from './static.js';

$(document).ready(runApp);

function runApp () {
    let params = new URLSearchParams(window.location.search);
    let keys = [...params.keys()];
    let page = keys[0];

    switch(keys[0]){
        case 'stuff': stuffHandle.getOne(keys[1]); break;
        case 'completelist': stuffHandle.listAll(); break;
        case 'newstuff': stuffHandle.newStuff(); break;
        case 'stuffdelete': stuffHandle.deleteItem(keys[1]); break;
        case 'stuffedit': stuffHandle.editItem(keys[1]); break;
        case 'categories': categoryHandle.listAll(); break;
        case 'catedit': categoryHandle.editItem(keys[1]); break;
        case 'catdelete': categoryHandle.deleteItem(keys[1]); break;
        case 'newcategory': categoryHandle.addNew(); break;
        case 'about': staticHandle.about(); break;
        default: stuffHandle.listAllIdName(); break;
    }
}
