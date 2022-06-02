function retrieveUserRoles() {
    $.ajax({
        type: 'GET',
        data: {},
        dataType: 'json',
        url: 'http://localhost:8086/home/UserListService',
        success: loadTableData,
        error: function (xhr, status, error) {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Error!',
                text: xhr.responseText,
                icon: 'error'
            });
        }
    });
}

function loadTableData(data) {
    var table = $('#user-roles-list').DataTable(
            {
                "destroy": true,
                "language": {

                    url: "https://cdn.datatables.net/plug-ins/1.11.3/i18n/es_es.json"

                },
                data: data,
                columns: [

                    {title: "Identitificación", data: "idUser"},
                    {title: "Nombre", "render": function (data, tyoe, row, meta) {
                            return row.official.name + " " + row.official.surname;
                        }
                    },
                    {title: "Departamento", data: "official.department.description"},
                    {title: "Permiso administrador de presupuesto", "render": function (data, type, row, meta) {
                            var id = row.idUser + "-4";
                            if (findRol(row.roles, 4)) {
                                return `<div class="form-check form-switch"><input onclick="onChangeCheckbox('${id}')" class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked></div>`;
                            }
                            return `<div class="form-check form-switch"><input onclick="onChangeCheckbox('${id}')" class="form-check-input" type="checkbox" id="flexSwitchCheckDefault"></div>`;
                        }
                    },
                    {title: "Permiso administrador de proveduria", "render": function (data, type, row, meta) {
                            var id = row.idUser + "-5";
                            if (findRol(row.roles, 5)) {
                                return `<div class="form-check form-switch"><input onclick="onChangeCheckbox('${id}')" class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked></div>`;
                            }
                            return `<div class="form-check form-switch"><input onclick="onChangeCheckbox('${id}')" class="form-check-input" type="checkbox" id="flexSwitchCheckDefault"></div>`;
                        }
                    },
                    {title: "Permiso funcionario", "render": function (data, type, row, meta) {
                            var id = row.idUser + "-3";
                            if (findRol(row.roles, 3)) {
                                return `<div class="form-check form-switch"><input onclick="onChangeCheckbox('${id}')" class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked></div>`;
                            }
                            return `<div class="form-check form-switch"><input onclick="onChangeCheckbox('${id}')" class="form-check-input" type="checkbox" id="flexSwitchCheckDefault"></div>`;
                        }
                    }

                ]
            });
}

function findRol(data, idRol) {
    for (let i = 0; i < data.length; i++) {
        if (data[i].idRol == idRol) {
            return true;
        }
    }
    return false;

}

function onChangeCheckbox(id) {
    id = id.split("-");
    document.getElementById("id-user").value = id[0];
    document.getElementById("id-rol").value = id[1];
    frm = $("#form-rol");
    $.ajax({
        type: frm.attr("method"),
        url: frm.attr("action"),
        data: frm.serialize(),
        success: function () {
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Exitoso!',
                text: 'Rol cambiado con éxito.',
                icon: 'success'
            });
        },
        error: function (xhr, status, error) {
            console.log(status);
            Swal.fire({
                confirmButtonColor: '#3085d6',
                title: '¡Error!',
                text: xhr.responseText,
                icon: 'error'
            });
        }
    });

}