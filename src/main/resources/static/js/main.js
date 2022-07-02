const PORT = '8080';
const uploadForm = document.getElementById('fileUploadForm'),
      uploadFormInput = document.getElementById('fileUploadInput'),
      downloadFileUrl = document.getElementById('downloadFileUrl'),
      listBtn = document.getElementById('listBtn'),
      listItems = document.querySelector('.list-wrapper'),
      apiUrl = `http://localhost:${PORT}/api/files/`;

const uploadFile = file => {
  let formData = new FormData();
  formData.append('file', file);
  fetch(apiUrl.concat('upload'), {
    method: 'POST',
    body: formData,
  })
  .then(response => response.json())
  .then(data => {
    let response = data.id;
    if (response !== null) {
      downloadFileUrl.innerHTML = `
        <p>
          File Uploaded Successfullly. <br/>
          <a href="${apiUrl.concat(response)}" target="_blank">
            Download File
          </a>
        </p>
      `;
      downloadFileUrl.style.display = 'block';
    } else {
      alert('Error Occured! No file returned');
    }
  });
};

const downloadFile = event => {
  const files = uploadFormInput.files;
  if (files.length !== 0) {
    uploadFile(files[0]);
    event.preventDefault();
  } else {
    alert('Please Select a File');
  }
};

const listFiles = () => {
  listItems.innerHTML = '';
  fetch(apiUrl)
    .then(data => data.json())
    .then(items => items.forEach(item => Object.entries(item).forEach( ([ id, filename ]) => {
      console.log(`${id} --> ${filename}`);
      const linkImg = apiUrl.concat(id);
      listItems.insertAdjacentHTML('beforeend', `
        <li>
          <a href="${linkImg}" target="_blank">
            <img src="${linkImg}" />
          </a>
          <div class="img-footer">
            <a href="${linkImg}" target="_blank">${filename}</a>
            <span class="delete-link" id="${id}">delete</span>
          </div>
        </li>
      `);
      document.getElementById(id).addEventListener('click', () => deleteFile(id));
    }))
  );
};

const deleteFile = id => {
  fetch(apiUrl.concat(id), {
    method: 'DELETE',
  }).then(res => {
      if (id !== null) {
        alert('Deleted file with ID: '.concat(id));
        listFiles();
      } else {
        alert('Error Occured! No file deleted');
      }
    });
};

uploadForm.addEventListener('submit', event => downloadFile(event), true);
listBtn.addEventListener('click', () => listFiles());
