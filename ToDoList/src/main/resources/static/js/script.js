async function newProject() {

    let token = document.getElementById('token').value;
    let project = document.getElementById('projectName').value;

	let url = new URL('http://localhost:8080/home/add/project');
    let params = new URLSearchParams({
    	_csrf: token,
    	projectName: project
    });

    //console.log(url + '?' + params);
    let response = await fetch(url + '?' + params);

    if (response.ok) {
        //console.log(response);
        addProject(project);
    } else {
        alert('Ошибка HTTP: ' + response.status);
    }
}

function addProject(projectName) {
	let pn = document.createElement('div');
	pn.className = 'content';
	pn_sp = document.createElement('span');
	pn_sp.innerHTML += projectName;
	pn.appendChild(pn_sp);


	let ct = document.createElement('div');
	ct.className = 'content';
	ct_sp = document.createElement('span');
	ct_sp.innerHTML += '0 tasks';
	ct.appendChild(ct_sp);


	let dv = document.createElement('div');
	dv.className = 'content no-padding';
	dv_bt = document.createElement('button');
	dv_bt.innerHTML += 'open';
	dv.appendChild(dv_bt);


	let bc = document.createElement('div');
	bc.className = 'body-content';
	bc.appendChild(pn);
	bc.appendChild(ct);
	bc.appendChild(dv);

	document.getElementById('body').appendChild(bc);
}