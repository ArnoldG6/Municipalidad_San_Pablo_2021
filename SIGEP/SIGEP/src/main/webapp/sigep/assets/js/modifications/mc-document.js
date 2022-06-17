const days = {
    0: "Domingo ",
    1: "Lunes ",
    2: "Martes ",
    3: "Miércoles ",
    4: "Jueves ",
    5: "Viernes ",
    6: "Sábado ",
}
const months = {
    0: "Enero ",
    1: "Febrero ",
    2: "Marzo ",
    3: "Abril ",
    4: "Mayo ",
    5: "Junio ",
    6: "Julio ",
    7: "Agosto ",
    8: "Setiembre ",
    9: "Octubre ",
    10: "Noviembre ",
    11: "Diciembre "

}

const ta = document.form.ta.value;
const ta1 = document.form1.ta1.value;
const ta2 = document.form2.ta2.value;
const ta3 = document.form3.ta3.value;
const ta4 = document.form4.ta4.value;
const ta5 = document.form5.ta5.value;

const ptxt = $('#ptxt').text(ta);
const ptxt1 = $('#ptxt1').text(ta1);
const ptxt2 = $('#ptxt2').text(ta2);
const ptxt3 = $('#ptxt3').text(ta3);
const ptxt4 = $('#ptxt4').text(ta4);
const ptxt5 = $('#ptxt5').text(ta5);

var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
})


$(document).ready(function () {
    let date = new Date();
    let t = $('#date').text();
    let fullDate = days[date.getDay()] + date.getDate() + " de " + months[date.getMonth()] + "del " + date.getFullYear();
    $('#date').html(t + fullDate);

    document.getElementById("idRequest").value = window.localStorage.getItem('mcResolution');
    console.log(document.getElementById("idRequest").value);
});

function retrieveMovementsData() {
    document.getElementById("idRequest").value = window.localStorage.getItem('mcResolution');
    var idModification = document.getElementById("idRequest").value;
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: `http://localhost:8080/home/ModificationMovementsListService?idModification=${idModification}`,
        success: loadTableData,
        error: function () {}
    });
}

function loadTableData(data) {
    let table = $("#movements-list").DataTable({
        "language": {
            url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"
        },
        data: data,
        columns: [
            {title: "Código", data: "id"},
            {title: "Partida decreciente", data: "budgetA.description"},
            {title: "Partida creciente", data: "budgetB.description"},
            {title: "Monto", data: "amount"}
        ]
    });
}


function addSummary() {
    $('#doc1').change(function (event) {
        var tmppath = URL.createObjectURL(event.target.files[0]);
        console.log(tmppath);

        let ruta = tmppath;
        var img = document.createElement('img');
        img.src = ruta;
        img.setAttribute("id", "img1");
        img.style = "height:300px; width: 90%;";
        document.getElementById('imgDiv').appendChild(img);
        $('#btnDeleteSummary').toggle("hide");
        event.target.value = "";

    });
}
function addDetails() {

    $('#doc2').change(function (event) {

        var tmppath2 = URL.createObjectURL(event.target.files[0]);

        let ruta = tmppath2;
        var img2 = document.createElement('img');
        img2.src = ruta;
        img2.setAttribute("id", "img2");
        img2.style = "height:300px; width: 90%;";

        document.getElementById('imgDiv2').appendChild(img2);
        $('#btnDeleteDetails').toggle("hide");

        event.target.value = "";
    });
}

function addAnnexes() {

    $('#doc4').change(function (event) {

        var tmppath3 = URL.createObjectURL(event.target.files[0]);

        let ruta = tmppath3;
        var img3 = document.createElement('img');
        img3.src = ruta;
        img3.setAttribute("id", "img3");
        img3.style = "height:400px; width: 90%;";

        document.getElementById('imgDiv3').appendChild(img3);
        $('#btnDeleteAnnexes').toggle("hide");

        event.target.value = "";
    });
}

function deleteSummary() {
    $('#btnDeleteSummary').toggle("hide");
    $('#imgDiv').empty();
}

function deleteDetails() {
    $('#btnDeleteDetails').toggle("hide");
    $('#imgDiv2').empty();
}

function deleteAnnexes() {
    $('#btnDeleteAnnexes').toggle("hide");
    $('#imgDiv3').empty();
}

function genPDF() {

    const textareas = document.querySelectorAll('.t-area');
    const parrafos = document.querySelectorAll('.pt');
    var pdf = document.getElementById('content-area');

    for (var e of textareas) {
        e.classList.toggle('hide');
    }
    for (var p of parrafos) {
        p.classList.toggle('hide');
    }
    html2pdf().set({
        margin: [0, 1, 0, 1],
        filename: 'resolucion.pdf',
        image: {
            type: 'jpeg',
            quality: 0.98
        },
        html2canvas: {
            scale: 3,
            letterRendering: true
        },
        jsPDF: {
            unit: "in",
            format: "a3",
            orientation: 'portrait'
        }
    })
            .from(pdf)
            .save()
            .catch(err => console.log(err))
            .finally()
            .then(() => {
                console.log("¡Guardado!")

            });

    for (var e of textareas) {
        e.classList.toggle('hide');
    }
    for (var p of parrafos) {
        p.classList.toggle('hide');
    }
}

function sendPDF() {
    var form = $('#formMc')[0];
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/home/NewBudgetModificationResolutionService',
        data: new FormData(form),
        processData: false,
        dataType: 'text',
        contentType: false,
        cache: false,
        success: function () {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'El envío de la resolución ha sido exitoso.',
                icon: 'success'
            });
        },
        error: function (xhr, status, error) {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Error!',
                text: xhr.responseText,
                //text: 'Error al enviar documento',
                icon: 'error'
            });
        }
    });
}
