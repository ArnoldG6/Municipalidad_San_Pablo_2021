import React, { Component } from 'react';
import axios from 'axios';
import '../Perfil.css'
import { Modal, Button, Form} from "react-bootstrap";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

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
                'roles': event.target.roles.value,
                'department': event.target.department.value,
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

        if (this.props.user !== null) {
            username = this.props.user.username;
            department = this.props.user.department;
            roles = this.props.user.roles;
            full_name = this.props.user.full_name;
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
                            <label>Usuario:</label>
                            <input name="username" id="username" type="text" className="form-control" placeholder="username" defaultValue={username} required />
                        </div>
                        <div className="form-group">
                            <Form.Select name="roles" id="roles" defaultValue={roles}>
                                <option value="ADMIN">Activo</option>
                                <option value="SUPER_ADMIN">Inactivo</option>
                                <option value="USER">Completo</option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Departamento:</label>
                            <input name="department" id="department" type="text" className="form-control" placeholder="department" defaultValue={department} required />
                        </div>
                        <Button className='btn-perfil' type="submit" id="submit-button-new-item">
                            Guardar
                        </Button>
                    </Form>
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
};
export default EditPerfilModal;