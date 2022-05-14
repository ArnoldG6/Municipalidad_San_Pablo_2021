import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import axios from 'axios';
import '../Perfil.css'
import { Modal, Button, Form } from "react-bootstrap";
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
                'usuarioLogeado': this.state.username,
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
        let department;
        let roles;
        let name;
        let surname;
        let username;

        if (this.props.user !== null) {
            username = this.props.user.username;
            department = this.props.user.department;
            roles = this.props.user.roles;
            name = this.props.user.name;
            surname = this.props.user.surname;
        }
        return (
            <Modal show={render} onHide={closeModal} >
                <Modal.Header closeButton>
                    Editar Perfil
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label>Nombre:</label>
                            <input name="name" id="name" type="text" placeholder="Nombre" className="form-control" defaultValue={name} required />
                        </div>

                        <div className="form-group">
                            <label>Apellido:</label>
                            <input name="surname" id="surname" type="text" className="form-control" placeholder="Apellido" defaultValue={surname} required />
                        </div>

                        <div className="form-group">
                            <label>Departamento:</label>
                            <Form.Select name="department" id="department" onChange={this.onChange} defaultValue={department}>
                                {
                                    (this.props.departmentMap === null || typeof this.props.departmentMap === 'undefined') ?
                                        <option value={null} key="disabledDepartmentEditUser" disabled>Error cargando departamentos</option> :

                                        this.props.departmentMap.map((dep) => {
                                            return <option value={dep.idDepartment} key={dep.description}>{dep.description}</option>
                                        })
                                }

                            </Form.Select>
                        </div>

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