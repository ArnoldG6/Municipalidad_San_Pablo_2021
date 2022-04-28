import React, { Component } from 'react';
import axios from 'axios';
import '../Perfil.css'
import { Modal, Button, Form } from "react-bootstrap";

class EditPerfilModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
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
                            <label>Usuario:</label>
                            <input name="username" id="username" type="text" className="form-control" placeholder="username" defaultValue={username} required />
                        </div>

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
                            <input name="department" id="department" type="text" className="form-control" placeholder="department" defaultValue={department} required />
                        </div>

                        <div className="form-group">
                            <label>Rol: </label>
                            <Form.Select name="roles" id="roles" defaultValue={roles}>
                                <option value="ADMIN">Administrados</option>
                                <option value="SUPER_ADMIN">Super Administrador</option>
                                <option value="USER">Usuario</option>
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