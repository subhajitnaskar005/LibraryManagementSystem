/**
 * 
 */


async function loadDashboard() {
  try {
    const stats = await apiGet('/dashboard/summary');
    document.getElementById('totalBooks').textContent = stats.totalBooks;
    document.getElementById('totalMembers').textContent = stats.totalMembers;
    document.getElementById('totalIssued').textContent = stats.totalIssuedBooks;
    document.getElementById('totalOverdue').textContent = stats.totalOverdueBooks;
    document.getElementById('totalWaitlisted').textContent = stats.totalWaitlisted;
    document.getElementById('totalFine').textContent = stats.totalFineCollected.toFixed(2);
  } catch (e) { console.error(e); }
}
loadDashboard();