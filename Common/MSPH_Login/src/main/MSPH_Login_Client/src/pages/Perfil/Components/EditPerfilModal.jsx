import React, { Component } from 'react';
import axios from 'axios';
import '../Perfil.css'
import { Modal, Button, Form, FormGroup } from "react-bootstrap";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Cookies from 'universal-cookie';
const cookies = new Cookies();

class EditPerfilModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: null,
            usuarioLogeado: cookies.get('username', { path: process.env.REACT_APP_AUTH })
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.checkPermissions = this.checkPermissions.bind(this);
        this.checkOwner = this.checkOwner.bind(this);
    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: process.env.REACT_APP_AUTH_API_PATH + `/User/edit`,
            method: 'PUT',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'emailOriginal': this.props.user.email,
                'usuarioLogeado': this.state.usuarioLogeado,
                'name': event.target.name.value,
                'surname': event.target.surname.value,
                'department': event.target.department.value,
                'role': event.target.roles.value
            }
        }
        axios(options)
            .then(response => {
                this.props.refreshPage();
                this.props.closeModal();
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 401:
                            msj = "Este usuario no cuenta con permisos para editar usuarios.";
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

    checkOwner() {
        let perm = false;
        if ((typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) !== 'undefined') && (this.state.user.username !== null) && (typeof this.state.user.username !== 'undefined')) {
            if (cookies.get('username', { path: process.env.REACT_APP_AUTH }) === this.state.user.username.toString()) {
                perm = true;
            }
        }
        return perm;
    }

    render() {
        let render = this.props.show
        let closeModal = this.props.closeModal
        let department;
        let roles;
        let name;
        let surname;

        if (this.props.user !== null) {
            department = this.props.user.department;
            roles = this.props.user.roles;
            name = this.props.user.name;
            surname = this.props.user.surname;
        }
        return (
            <Modal show={render} onHide={closeModal} id="editPerfilModal">
                <Modal.Header closeButton>
                    Editar Perfil
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label>Nombre:</label>
                            <input name="name" id="name" type="text" placeholder="Nombre" className="form-control" defaultValue={name} required />
                        </div>

                        <div className="form-group">
                            <label>Apellido:</label>
                            <input name="surname" id="surname" type="text" className="form-control" placeholder="Apellido" defaultValue={surname} required />
                        </div>

                        <FormGroup>
                            <div className="form-group">
                                <Form.Label>Departamento: </Form.Label>
                                <Form.Select name="department" id="department" onChange={this.onChange} defaultValue={department} disabled={(!this.checkPermissions("SUPER_ADMIN"))}>
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
                            <Form.Select name="roles" id="roles" defaultValue={roles} disabled={(!this.checkPermissions("SUPER_ADMIN"))}>
                                <option value="1" key="ADMIN">ADMIN</option>
                                <option value="2" key="SUPER_ADMIN">SUPER_ADMIN</option>
                                <option value="3" key="USER">USER</option>
                            </Form.Select>
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
export default EditPerfilModal;