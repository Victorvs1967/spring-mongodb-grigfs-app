require('dotenv').config();

const uploadForm = document.getElementById('fileUploadForm'),
      uploadFormInput = document.getElementById('fileUploadInput'),
      downloadFile = document.getElementById('downloadFileUrl');

const uploadUrl = `http://localhost:${process.env.PORT}/api/files/upload`;
const downloadUrl = `http://localhost:${process.env.PORT}/api/files/download/`;

const uploadFile = file => {
  let formData = new FormData();
  formData.append('file', file);

  fetch(uploadUrl, {
    method: 'POST',
    body: formData,
  }).then(response => response.json())
    .then(data => {
      console.log(data);
      console.log(data.id);
      let response = data.id;

      if (response !== null) {
        const dwnldUrl = downloadUrl.concat(response);
        downloadFile.innerHTML = '<p>File Uploaded Successfullly. <br/> <a href="'.concat(dwnldUrl).concat('" target="_self">Download File</p>');
        downloadFile.style.display = 'block';
      } else {
        alert('Error Occured! No file returned');
      }
    });
};

uploadForm.addEventListener('submit', event => {
  const files = uploadFormInput.files;
  if (files.length !== 0) {
    uploadFile(files[0]);
    event.preventDefault();
  } else {
    alert('Please Select a File');
  }
}, true);
