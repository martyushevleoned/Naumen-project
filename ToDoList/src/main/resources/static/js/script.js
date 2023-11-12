async function newProject() {

    //token = (document.getElementById('token').value;
	//console.log(token);

	addProject(document.getElementById('projectName').value)
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