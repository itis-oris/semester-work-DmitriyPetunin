document.getElementById('addLocation').addEventListener('click', function() {
    const newInput = document.createElement('input');
    newInput.type = 'text';
    newInput.className = 'locationInput';
    newInput.name = 'locations[]';
    newInput.placeholder = 'Введите пункт';
    newInput.required = true;

    document.getElementById('locationsContainer').appendChild(newInput);
});
document.getElementById('getCountryByLocation').addEventListener('click', async function () {
    const locationInputs = document.getElementsByClassName('locationInput');
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
    document.getElementById('locationForm').appendChild(hiddenFieldContainer);
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