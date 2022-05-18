import React, { Component } from 'react';
import axios from 'axios';
import '../Usuarios.css'
import { Modal, Button, Form, FormGroup } from "react-bootstrap";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Cookies from 'universal-cookie';
import { sha256 } from 'js-sha256';
const cookies = new Cookies();

class AddUserModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: null,
            usuarioLogeado: cookies.get('username', { path: process.env.REACT_APP_AUTH })
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.checkPermissions = this.checkPermissions.bind(this);
        this.validatePassword = this.validatePassword.bind(this);
    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

    validatePassword(password) {
        if (password.length < 8) {
            toast.error("La constraseña debe ser de al menos 8 caracteres.", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 5000
            });
            return false;
        }
        if (!/\d/.test(password)) {
            toast.error("La constraseña debe tener al menos 1 número.", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 5000
            });
            return false;
        }
        if (!/[A-Z]/.test(password)) {
            toast.error("La constraseña debe tener al menos 1 letra mayúscula.", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 5000
            });
            return false;
        }
        this.setState({ pwValid: true })
        return true;
    }

    handleSubmit = (event) => {
        const form = event.currentTarget;
        console.log(form)
        if (form.checkValidity() === false || this.validatePassword(event.target.password.value) === false) {
            event.preventDefault();
            event.stopPropagation();
            console.log(form)
        }
        else {
            event.preventDefault();
            let options = {
                url: process.env.REACT_APP_AUTH_API_PATH + `/User/add`,
                method: 'PUT',
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    "username": parseInt(event.target.username.value),
                    "email": event.target.email.value,
                    "name": event.target.name.value,
                    "surname": event.target.surname.value,
                    "department": event.target.department.value,
                    "role": event.target.roles.value,
                    "password": sha256(event.target.password.value)
                }
            }
            axios(options)
                .then(response => {
                    toast.success("El usuario ha sido agregado con exito.", {
                        position: toast.POSITION.TOP_RIGHT,
                        pauseOnHover: true,
                        theme: 'colored',
                        autoClose: 5000
                    });
                    this.setState({ validated: false })
                    this.props.refreshPage();
                    this.props.closeModal();
                }).catch(error => {
                    var msj = "";
                    if (error.response) {
                        //Server responded with an error
                        switch (error.response.status) {
                            case 400:
                                msj = "El nombre de usuario o el email ya se encuentran registrados en el sistema.";
                                break;
                            case 500:
                                msj = "El servidor ha encontrado un error desconocido.";
                                break;
                            default:
                                msj = "El servidor ha encontrado un error desconocido.";
                                break;
                        }
                    } else if (error.request) {
                        //Server did not respond
                        msj = "Hubo un error con la conexión al servidor."
                    } else {
                        //Something else went wrong
                        msj = "Error desconocido."
                    }
                    toast.error(msj, {
                        position: toast.POSITION.TOP_RIGHT,
                        pauseOnHover: true,
                        theme: 'colored',
                        autoClose: 5000
                    });
                })
        }

        this.setState({ validated: true })

    }

    checkPermissions(toCheck) {
        let perm = false;
        if (typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) !== 'undefined') {
            cookies.get('roles', { path: process.env.REACT_APP_AUTH }).map((rol) => {
                if (rol.description === toCheck) {
                    perm = true;
                    return true;
                }
                return false;
            })
        }
        return perm;
    }

    render() {
        let render = this.props.show
        let closeModal = this.props.closeModal
        return (
            <Modal show={render} onHide={() => { closeModal(); this.setState({ validated: false }) }} id="AddUserModal">
                <Modal.Header closeButton>
                    Agregar Usuario
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label>Cedula:</label>
                            <input name="username" id="username" type="number" placeholder="Cedula del Usuario" min="0" max="999999999" step="1" className="form-control number-input" required />
                        </div>


                        <div className="form-group">
                            <label>Email:</label>
                            <input name="email" id="email" type="email" placeholder="Email del Usuario" className="form-control" required />
                        </div>

                        <div className="form-group">
                            <label>Nombre:</label>
                            <input name="name" id="name" type="text" placeholder="Nombre del Usuario" className="form-control" required />
                        </div>

                        <div className="form-group">
                            <label>Apellido:</label>
                            <input name="surname" id="surname" type="text" className="form-control" placeholder="Apellido del Usuario" required />
                        </div>

                        <FormGroup>
                            <div className="form-group">
                                <Form.Label>Departamento: </Form.Label>
                                <Form.Select name="department" id="department" onChange={this.onChange}>
                                    {
                                        (this.props.departmentMap === null || typeof this.props.departmentMap === 'undefined') ?
                                            <option value={null} key="disabledDepartmentEditUser" disabled>Error cargando departamentos</option> :
                                            this.props.departmentMap.map((dep) => {
                                                return <option value={dep.idDepartment} key={dep.description}>{dep.description}</option>
                                            })
                                    }
                                </Form.Select>
                            </div>
                        </FormGroup>

                        <div className="form-group">
                            <label>Rol: </label>
                            <Form.Select name="roles" id="roles" disabled={(!this.checkPermissions("SUPER_ADMIN"))}>
                                <option value="1" key="ADMIN">ADMIN</option>
                                <option value="2" key="SUPER_ADMIN">SUPER_ADMIN</option>
                                <option value="3" key="USER">USER</option>
                            </Form.Select>
                        </div>

                        <div className="form-group">
                            <label>Contraseña:</label>
                            <input name="password" id="password" type="password" placeholder="Contraseña para el Usuario" className="form-control" required />
                            <p>
                                La contraseña debe ser de al menos 8 caracteres y contener al menos un número y una letra mayúscula.
                            </p>
                        </div>

                        <Button className='btn-perfil' type="submit" id="submit-button-new-item">
                            Guardar
                        </Button>
                    </Form>
                </Modal.Body>

            </Modal>
        );
    }
};
export default AddUserModal;