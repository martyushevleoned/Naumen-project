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

}