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
            value: null
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
            url: process.env.REACT_APP_SFR_API_URL + `/User/edit`,
            method: 'PUT',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'emailOriginal': this.props.user.email,
                'username': event.target.username.value,
                'full_name': event.target.full_name.value,
                'email': event.target.email.value,
                'department': event.target.department.value,
                'roles': event.target.roles.value
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

        let username;
        let department;
        let roles;
        let full_name;
        let email;

        if (this.props.user !== null) {
            username = this.props.user.username;
            department = this.props.user.department;
            roles = this.props.user.roles;
            full_name = this.props.user.full_name;
            email = this.props.user.email;
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
                            <input name="full_name" id="full_name" type="text" placeholder="Nombre" className="form-control" defaultValue={full_name} required />
                        </div>

                        <div className="form-group">
                            <label>Email:</label>
                            <input name="email" id="email" type="text" className="form-control" placeholder="email" defaultValue={email} required />
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