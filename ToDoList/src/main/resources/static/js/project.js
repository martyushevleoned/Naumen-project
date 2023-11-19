async function newTask() {

    let token = document.getElementById('token').value;
    let project = document.getElementById('projectId').value;
    let task = document.getElementById('taskName').value;

	let url = new URL('http://localhost:8080/project/add/task');
    let params = new URLSearchParams({
    	_csrf: token,
    	projectId: project,
    	text: task
    });

    fetch(url + '?' + params).then(
        r => r.text().then(id => addTask(task, id)),
        r => alert('Ошибка HTTP: ' + r.status)
    );
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

async function deleteTask(taskId) {

    let token = document.getElementById('token').value;

	let url = new URL('http://localhost:8080/project/delete/task');
    let params = new URLSearchParams({
    	_csrf: token,
    	id: taskId
    });

    fetch(url + '?' + params).then(
        hideTask(taskId),
        r => alert('Ошибка HTTP: ' + r.status)
    );
}

function hideTask(id) {
    document.getElementById(id).remove();
}