const sortButton = document.querySelector('#sort-button')
const sortInput = document.querySelector('#sort-input')
const fileName = sortInput.getAttribute('value')
sortButton.addEventListener('click', e => fetchSort(fileName))

function fetchSort(fileName) {
  const options = {
    url: '/app/sort?fileName=' + fileName,
    method: 'GET'
  }

  axios(options).then(response => {
    console.log(response.status)
  })
}
