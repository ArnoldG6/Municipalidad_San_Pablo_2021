var url = "http://localhost:8080/Web/";

function loadLogin() {
    let request = new Request(url + 'prueba.html', {method: 'GET'});
    (async () => {
        const response = await fetch(request);
        console.log(response);
        if (!response.ok) {
            //errorMessage(response.status, $("#loginDialog #errorDiv"));
            return;
        }
        content = await response.text();
        console.log(content);
        $('body').append(content);
    })();
}

$(loadLogin); 