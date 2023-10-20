function sendRequest() {
  let text = document.getElementById("text").value;

  const checkboxes = document.querySelectorAll('input[type="checkbox"]');
  const requiredGenres = [];

  checkboxes.forEach(checkbox => {
    if (checkbox.checked) {
        requiredGenres.push(checkbox.value);
    }
  });

  let xhr = new XMLHttpRequest();
  xhr.open("POST", "/filter");

  xhr.setRequestHeader("Content-Type", "application/json");

  xhr.onload = function() {
     if (xhr.status != 200) {
        const resultElement = document.getElementById('result');

        resultElement.innerText = 'Ошибка ' + xhr.status + ': ' + xhr.responseText;

        return;
     }

    const responseData = JSON.parse(xhr.response);

    const result = responseData.join('\n');

    const resultElement = document.getElementById('result');
    resultElement.innerText = result;
  };

  let data = JSON.stringify({
    text: text,
    requiredGenres: requiredGenres
  });

  xhr.send(data);
}