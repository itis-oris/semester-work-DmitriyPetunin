function removeInput(iconElement) {
    const inputContainer = iconElement.parentElement;
    inputContainer.remove();
}
document.getElementById("addLocation").addEventListener("click", function() {

    const inputContainer = document.createElement('div');
    inputContainer.className = 'input-container';


    const newInput = document.createElement('input');
    newInput.name = 'location';
    newInput.className = 'form-control';
    newInput.type = 'text';
    newInput.required = true;


    inputContainer.appendChild(newInput);

    const deleteIcon = document.createElement('i');
    deleteIcon.className = 'bi bi-trash3-fill remove-icon';
    deleteIcon.onclick = function() {
        const inputContainerToRemove = this.parentElement;
        inputContainerToRemove.remove();
    };

    inputContainer.appendChild(deleteIcon);
    const locationsContainer = document.getElementById('locationsContainer');
    console.log('Before appending, locationsContainer:', locationsContainer);
    console.log('New input container:', inputContainer);
    locationsContainer.appendChild(inputContainer);
    console.log('After appending, locationsContainer:', locationsContainer);
    console.log('Children of locationsContainer:', locationsContainer.children);
});


document.getElementById('getCountryByLocation').addEventListener('click', async function () {
    const locationInputs = document.querySelectorAll('#locationsContainer .form-control');
    const responseMessagesContainer = document.getElementById('responseMessages');
    responseMessagesContainer.innerHTML = ''

    const hiddenFieldContainer = document.getElementById('hidden-container');
    hiddenFieldContainer.innerHTML = ''

    for (const input of locationInputs) {
        const location = input.value;
        if (location) {
            const country = await getCountryByLocation(location);
            const message = document.createElement('label');
            message.innerText = `Локация: ${location}, Страна: ${country}`;
            responseMessagesContainer.appendChild(message);

            const hiddenField = document.createElement('input');
            hiddenField.type = 'hidden';
            hiddenField.name = 'country[]';
            hiddenField.value = `${location}:${country}`;
            hiddenFieldContainer.appendChild(hiddenField);

        } else {
            const message = document.createElement('div');
            message.innerText = 'Пожалуйста, введите корректный пункт.';
            responseMessagesContainer.appendChild(message);
        }
    }
});
async function getCountryByLocation(location) {
    const apiKey = '1a2e16060a57430284d97d95281dc317';
    const response = await fetch(`https://api.opencagedata.com/geocode/v1/json?q=${location}&key=${apiKey}`);
    if (!response.ok) {
        document.getElementById('responseMessage').innerText = 'Ошибка при получении данных';
        return;
    }
    const data = await response.json();

    if (data.results && data.results.length > 0) {
        const country = data.results[0].components.country;
        return country.toString();
    } else {
        return 'Страна не найдена'
    }
}