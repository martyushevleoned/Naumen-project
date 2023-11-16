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

   console.log(id)

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