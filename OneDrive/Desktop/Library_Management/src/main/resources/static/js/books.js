/**
 * 
 */

let editingId = null;

async function loadBooks() {
  renderBooks(await apiGet('/books'));
}

function renderBooks(books) {
  const body = document.getElementById('booksBody');
  body.innerHTML = '';
  books.forEach(b => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${b.title}</td>
      <td>${b.author}</td>
      <td>${b.isbn}</td>
      <td>${b.category || '-'}</td>
      <td>${b.availableCopies} / ${b.totalCopies}</td>
      <td>
        <button onclick="editBook(${b.id})">Edit</button>
        <button onclick="deleteBook(${b.id})" class="danger">Delete</button>
      </td>`;
    body.appendChild(row);
  });
}

async function searchBooks() {
  const keyword = document.getElementById('searchInput').value.trim();
  if (!keyword) { loadBooks(); return; }
  renderBooks(await apiGet('/books/search?keyword=' + encodeURIComponent(keyword)));
}

document.getElementById('bookForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const book = {
    title: document.getElementById('title').value,
    author: document.getElementById('author').value,
    isbn: document.getElementById('isbn').value,
    category: document.getElementById('category').value,
    totalCopies: parseInt(document.getElementById('totalCopies').value)
  };
  try {
    if (editingId) {
      await apiPut('/books/' + editingId, book);
      showMessage('message', 'Book updated successfully');
    } else {
      await apiPost('/books', book);
      showMessage('message', 'Book added successfully');
    }
    resetForm();
    loadBooks();
  } catch (err) {
    showMessage('message', err.message, true);
  }
});

async function editBook(id) {
  const books = await apiGet('/books');
  const book = books.find(b => b.id === id);
  editingId = id;
  document.getElementById('title').value = book.title;
  document.getElementById('author').value = book.author;
  document.getElementById('isbn').value = book.isbn;
  document.getElementById('category').value = book.category || '';
  document.getElementById('totalCopies').value = book.totalCopies;
  document.getElementById('submitBtn').textContent = 'Update Book';
  document.getElementById('cancelEditBtn').style.display = 'inline-block';
}

async function deleteBook(id) {
  if (!confirm('Delete this book?')) return;
  try {
    await apiDelete('/books/' + id);
    showMessage('message', 'Book deleted');
    loadBooks();
  } catch (err) {
    showMessage('message', err.message, true);
  }
}

document.getElementById('cancelEditBtn').addEventListener('click', resetForm);

function resetForm() {
  editingId = null;
  document.getElementById('bookForm').reset();
  document.getElementById('submitBtn').textContent = 'Add Book';
  document.getElementById('cancelEditBtn').style.display = 'none';
}


async function addBooks(book) {
  try {
    const savedBook = await apiPost('/books', book);
    showMessage('message', `"${savedBook.title}" was added successfully`);
    return savedBook;
  } catch (err) {
    showMessage('message', err.message, true);
  }
}

loadBooks();