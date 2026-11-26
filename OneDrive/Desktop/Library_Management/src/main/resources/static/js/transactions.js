/**
 * 
 */

async function loadDropdowns() {
  const books = await apiGet('/books');
  const members = await apiGet('/members');

  document.getElementById('bookSelect').innerHTML = '<option value="">-- Select Book --</option>' +
    books.map(b => `<option value="${b.id}">${b.title} (${b.availableCopies} available)</option>`).join('');

  document.getElementById('memberSelect').innerHTML = '<option value="">-- Select Member --</option>' +
    members.map(m => `<option value="${m.id}">${m.name} (${m.email})</option>`).join('');
}

async function loadIssuedBooks() {
  const transactions = await apiGet('/transactions/issued');
  const body = document.getElementById('issuedBody');
  body.innerHTML = '';
  const today = new Date().toISOString().split('T')[0];

  transactions.forEach(t => {
    const isOverdue = t.dueDate < today;
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${t.book.title}</td>
      <td>${t.member.name}</td>
      <td>${t.issueDate}</td>
      <td>${t.dueDate}</td>
      <td class="${isOverdue ? 'overdue-text' : ''}">${isOverdue ? 'Overdue' : 'On Time'}</td>
      <td><button onclick="returnBook(${t.id})">Return</button></td>`;
    body.appendChild(row);
  });
}

document.getElementById('issueForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const bookId = document.getElementById('bookSelect').value;
  const memberId = document.getElementById('memberSelect').value;
  if (!bookId || !memberId) return;

  try {
    await apiPostParams(`/transactions/issue?bookId=${bookId}&memberId=${memberId}`);
    showMessage('message', 'Book issued successfully');
    loadDropdowns();
    loadIssuedBooks();
  } catch (err) {
    showMessage('message', err.message, true);
  }
});

async function returnBook(transactionId) {
  try {
    const result = await apiPut(`/transactions/return/${transactionId}`, null);
    const fineMsg = result.fineAmount > 0 ? ` Fine charged: ₹${result.fineAmount}` : ' No fine.';
    showMessage('message', 'Book returned successfully.' + fineMsg);
    loadDropdowns();
    loadIssuedBooks();
  } catch (err) {
    showMessage('message', err.message, true);
  }
}

loadDropdowns();
loadIssuedBooks();