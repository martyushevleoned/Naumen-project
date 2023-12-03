async function newTask() {

    let token = document.getElementById('token').value;
    let project = document.getElementById('projectId').value;
    let task = document.getElementById('taskName').value;

    if (task == '')
        return;

	let url = new URL('http://localhost:8080/project/add/task');
    let params = new URLSearchParams({
    	_csrf: token,
    	projectId: project,
    	text: task
    });

    console.log(url + '?' + params);
    fetch(url + '?' + params).then(
        response => {
            if (response.ok)
                response.text().then(id => addTask(task, id))
        }
    );

    document.getElementById('taskName').value = '';
}

function addTask(taskText, id) {

    let task = document.createElement('div');
    task.className = 'task no-padding';
    task.id = id;

    let text = document.createElement('div');
    text.className = 'content';
    let text_sp = document.createElement('span');
    text_sp.innerHTML = taskText;
    text.appendChild(text_sp);
    task.appendChild(text);

    let del = document.createElement('div');
    del.className = 'content no-padding';
    let del_bt = document.createElement('button');
    del_bt.innerHTML = 'delete';
    del_bt.onclick = function(){ deleteTask(id); };
    del.appendChild(del_bt);
    task.appendChild(del);

    document.getElementById('container').appendChild(task);
}

async function deleteTask(task) {

    let token = document.getElementById('token').value;

	let url = new URL('http://localhost:8080/project/delete/task');
    let params = new URLSearchParams({
    	_csrf: token,
    	taskId: task
    });

    console.log(url + '?' + params);
    fetch(url + '?' + params).then(
        response => {
            if (response.ok)
                hideTask(task)
        }
    );
}

function hideTask(id) {
    document.getElementById(id).remove();
}

//====================================================================

async function newMessage() {

    let token = document.getElementById('token').value;
    let project = document.getElementById('projectId').value;
    let message = document.getElementById('message').value;

    if (message == '')
        return;

	let url = new URL('http://localhost:8080/project/add/message');
    let params = new URLSearchParams({
    	_csrf: token,
    	projectId: project,
    	text: message
    });

    console.log(url + '?' + params);
    fetch(url + '?' + params).then(
        response => {
            if (response.ok)
                response.text().then(id => addMessage(message, id))
        }
    );

    document.getElementById('message').value = '';
}

function addMessage(message, id) {

    let block = document.createElement('block');
    block.id = id;

    let block_div = document.createElement('div');
    block_div.className = 'content no-padding';
    block.appendChild(block_div);

    let block_span = document.createElement('span');
    block_span.innerHTML = message;
    block.appendChild(block_span);

    document.getElementById('chat').appendChild(block);
}

// ================================================

async function newMember(){

    let token = document.getElementById('token').value;
    let project = document.getElementById('projectId').value;
    let username = document.getElementById('username').value;

    if (username == '')
        return;

    let url = new URL('http://localhost:8080/project/add/member');
    let params = new URLSearchParams({
    	_csrf: token,
    	projectId: project,
    	username: username
    });

    console.log(url + '?' + params);
    fetch(url + '?' + params).then(
        response => {
            if (response.ok)
                response.text().then(id => addMember(username, id))
        }
    );

    document.getElementById('username').value = '';
}

function addMember(username, id){

    let block = document.createElement('block');
    block.id = id;

    let block_div = document.createElement('div');
    block_div.className = 'content no-padding';
    block.appendChild(block_div);

    let block_span = document.createElement('span');
    block_span.innerHTML = username;
    block.appendChild(block_span);

    document.getElementById('memberList').appendChild(block);
}
