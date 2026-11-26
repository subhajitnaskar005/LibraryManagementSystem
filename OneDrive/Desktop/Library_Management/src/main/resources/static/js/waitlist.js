/**
 * 
 */

async function loadDropdowns() {
  const books = await apiGet('/books');
  const members = await apiGet('/members');

  document.getElementById('bookSelect').innerHTML = '<option value="">-- Select Book --</option>' +
    books.map(b => `<option value="${b.id}">${b.title} (${b.availableCopies} available)</option>`).join('');

  document.getElementById('memberSelect').innerHTML = '<option value="">-- Select Member --</option>' +
    members.map(m => `<option value="${m.id}">${m.name}</option>`).join('');
}

async function loadWaitlist() {
  const reservations = await apiGet('/reservations');
  const body = document.getElementById('waitlistBody');
  body.innerHTML = '';
  reservations.forEach(r => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${r.book.title}</td>
      <td>${r.member.name}</td>
      <td>${r.reservationDate}</td>
      <td><button onclick="cancelReservation(${r.id})" class="danger">Cancel</button></td>`;
    body.appendChild(row);
  });
}

document.getElementById('reservationForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const bookId = document.getElementById('bookSelect').value;
  const memberId = document.getElementById('memberSelect').value;
  if (!bookId || !memberId) return;

  try {
    await apiPostParams(`/reservations?bookId=${bookId}&memberId=${memberId}`);
    showMessage('message', 'Added to waitlist successfully');
    loadWaitlist();
  } catch (err) {
    showMessage('message', err.message, true);
  }
});

async function cancelReservation(id) {
  try {
    await apiPut(`/reservations/cancel/${id}`, null);
    showMessage('message', 'Reservation cancelled');
    loadWaitlist();
  } catch (err) {
    showMessage('message', err.message, true);
  }
}

loadDropdowns();
loadWaitlist();