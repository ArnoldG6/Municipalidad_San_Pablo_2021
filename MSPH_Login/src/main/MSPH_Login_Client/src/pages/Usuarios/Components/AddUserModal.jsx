import React, { Component } from 'react';
import axios from 'axios';
import '../Usuarios.css'
import { Modal, Button, Form, FormGroup } from "react-bootstrap";
import Cookies from 'universal-cookie';
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
    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: process.env.REACT_APP_AUTH_API_PATH + `/User/add`,
            method: 'PUT',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {

                "username": this.target.username.value,
                "email": this.target.email.value,
                "name": this.target.name.value,
                "surname": this.target.surname.value,
                "department": this.target.department.value,
                "role": this.target.roles.value,
                "password": this.target.password.value
            }
        }
        axios(options)
            .then(response => {
                this.props.refreshPage();
                this.props.closeModal();
            }).catch(error => {
                console.log(error);
            });
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
            <Modal show={render} onHide={closeModal} id="AddUserModal">
                <Modal.Header closeButton>
                    Editar Perfil
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label>Username:</label>
                            <input name="username" id="username" type="number" placeholder="username" min="0" max="999999999" className="form-control" required />
                        </div>


                        <div className="form-group">
                            <label>Email:</label>
                            <input name="email" id="email" type="text" placeholder="email" className="form-control" required />
                        </div>

                        <div className="form-group">
                            <label>Nombre:</label>
                            <input name="name" id="name" type="text" placeholder="name" className="form-control" required />
                        </div>

                        <div className="form-group">
                            <label>Apellido:</label>
                            <input name="surname" id="surname" type="text" className="form-control" placeholder="Apellido" required />
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
                            <input name="password" id="password" type="text" placeholder="password" className="form-control" required />
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