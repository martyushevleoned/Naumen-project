async function newProject() {

    let token = document.getElementById('token').value;
    let project = document.getElementById('projectName').value;

	let url = new URL('http://localhost:8080/projects/add');
    let params = new URLSearchParams({
    	_csrf: token,
    	projectName: project
    });

    fetch(url + '?' + params).then(
        r => r.text().then(id => addProject(project, id)),
        r => alert('Ошибка HTTP: ' + r.status)
    );
}

function addProject(projectName, id) {

   let pn = document.createElement('div');
   pn.className = 'content';
   pn_sp = document.createElement('span');
   pn_sp.innerHTML += projectName;
   pn.appendChild(pn_sp);

   let dp = document.createElement('div');
   dp.className = 'content no-padding';
   dp_bt = document.createElement('button');
   dp_bt.innerHTML = 'delete';
   dp_bt.onclick = function(){ deleteProject(id); };
   dp.appendChild(dp_bt);

   let ct = document.createElement('div');
   ct.className = 'content';
   ct_sp = document.createElement('span');
   ct_sp.innerHTML += '0 tasks';
   ct.appendChild(ct_sp);

   let form = document.createElement('form');
   form.action = '/project/' + id;
   let form_dv = document.createElement('div');
   form_dv.className = 'content no-padding';
   let form_dv_bt = document.createElement('button');
   form_dv_bt.innerHTML = 'open';
   form_dv_bt.type = 'submit';
   form_dv.appendChild(form_dv_bt);
   form.appendChild(form_dv);

   let bc = document.createElement('div');
   bc.className = 'body-content';
   bc.id = id;
   bc.appendChild(pn);
   bc.appendChild(dp);
   bc.appendChild(ct);
   bc.appendChild(form);

   document.getElementById('body').appendChild(bc);
}


async function deleteProject(projectId) {

    let token = document.getElementById('token').value;

	let url = new URL('http://localhost:8080/projects/delete');
    let params = new URLSearchParams({
    	_csrf: token,
    	id: projectId
    });

    fetch(url + '?' + params).then(
        hideProject(projectId),
        r => alert('Ошибка HTTP: ' + r.status)
    );
}

function hideProject(id) {
    document.getElementById(id).remove();
}