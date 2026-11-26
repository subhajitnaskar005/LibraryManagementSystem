/**
 * 
 */

const API_BASE = '/api';

async function handleResponse(res) {
  if (!res.ok) {
    const err = await res.json();
    throw new Error(err.message || 'Something went wrong');
  }
  return res.status === 204 ? null : res.json();
}

async function apiGet(url) {
  return handleResponse(await fetch(API_BASE + url));
}

async function apiPost(url, body) {
  return handleResponse(await fetch(API_BASE + url, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  }));
}

async function apiPostParams(url) {
  return handleResponse(await fetch(API_BASE + url, { method: 'POST' }));
}

async function apiPut(url, body) {
  const options = { method: 'PUT' };
  if (body) {
    options.headers = { 'Content-Type': 'application/json' };
    options.body = JSON.stringify(body);
  }
  return handleResponse(await fetch(API_BASE + url, options));
}

async function apiDelete(url) {
  await handleResponse(await fetch(API_BASE + url, { method: 'DELETE' }));
}

function showMessage(elementId, message, isError = false) {
  const el = document.getElementById(elementId);
  el.textContent = message;
  el.className = isError ? 'message error' : 'message success';
  el.style.display = 'block';
  setTimeout(() => { el.style.display = 'none'; }, 4000);
}