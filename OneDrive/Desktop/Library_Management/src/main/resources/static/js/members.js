/**
 * 
 */
editingId = 0;
async function loadMembers() {
  const members = await apiGet('/members');
  const body = document.getElementById('membersBody');
  body.innerHTML = '';
  members.forEach(m => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${m.name}</td>
      <td>${m.email}</td>
      <td>${m.phone || '-'}</td>
      <td>${m.membershipDate || '-'}</td>
      <td>
        <button onclick="editMember(${m.id})" class="success">Edit</button>
        <button onclick="deleteMember(${m.id})" class="danger">Delete</button>
      </td>`;
    body.appendChild(row);
  });
}

document.getElementById('memberForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const member = {
    name: document.getElementById('name').value,
    email: document.getElementById('email').value,
    phone: document.getElementById('phone').value
  };
  try {
    if (editingId) {
      await apiPut('/members/' + editingId, member);
      showMessage('message', 'Member updated successfully');
    } else {
		handleAddMember();
    }
    resetForm();
    loadMembers();
  } catch (err) {
    showMessage('message', err.message, true);
  }
});

async function editMember(id) {
  const members = await apiGet('/members');
  const member = members.find(m => m.id === id);
  editingId = id;
  document.getElementById('name').value = member.name;
  document.getElementById('email').value = member.email;
  document.getElementById('phone').value = member.phone || '';
  document.getElementById('submitBtn').textContent = 'Update Member';
  document.getElementById('cancelEditBtn').style.display = 'inline-block';
}

async function deleteMember(id) {
  if (!confirm('Delete this member?')) return;
  try {
    await apiDelete('/members/' + id);
    showMessage('message', 'Member deleted');
  } catch (err) {
    //showMessage('message', err.message, true);
	showMessage('message', 'Something went wrong !');
  }
   loadMembers();
}



document.getElementById('cancelEditBtn').addEventListener('click', resetForm);

function resetForm() {
  document.getElementById('memberForm').reset();
  document.getElementById('submitBtn').textContent = 'Add Member';
  document.getElementById('cancelEditBtn').style.display = 'none';
}

// Adding memebers .

async function handleAddMember() {
  const member = {
    name: document.getElementById('name').value,
    email: document.getElementById('email').value,
    phone: document.getElementById('phone').value
  };
  await addMembers(member);
}

async function addMembers(member) {
  try {
    const savedMember = await apiPost('/members', member);
    showMessage('message', `"${savedMember.name}" was added successfully`);
	resetForm();
	document.getElementById('name').value = "";
	document.getElementById('email').value = "";
	document.getElementById('phone').value = "";
	loadMembers();
    return savedMember;
  } catch (err) {
    showMessage('message', err.message, true);
  }
}


loadMembers();