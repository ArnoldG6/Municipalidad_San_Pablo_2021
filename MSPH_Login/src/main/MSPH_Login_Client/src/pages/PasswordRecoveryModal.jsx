import React, { Component } from 'react';
import { Form, Modal, Button } from 'react-bootstrap';
import '../css/Login.css';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default class PasswordRecoveryModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showWritePassword: true,
            validateEmail: false
        };
        this.closeModal = this.closeModal.bind(this);
        this.handleEmailSubmit = this.handleEmailSubmit.bind(this);
    }

    closeModal() {
        this.setState({
            showWritePassword: false
        }, () => {
            this.props.closeModal();
        });

    }

    handleEmailSubmit = (event) => {
        event.preventDefault();
        let options = {
            url: process.env.REACT_APP_AUTH_API_PATH + "/PasswordReset",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'userEmail': event.target.email.value
            }
        }
        axios(options)
            .then(response => {
                console.log("sent")
            })
            .catch(error => {
                var msj = "";
                if (error.response) {
                    //Server responded with an error
                    switch (error.response.status) {
                        case 400:
                            msj = "No se encontró un usuario con el correo indicado.";
                            break;
                        case 500:
                            msj = "El servidor ha encontrado un error desconocido enviando el código.";
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
            });

    }

    render() {
        return (
            <Modal show={this.props.show} onHide={this.closeModal} id="resetPasswordModal" >
                <Modal.Header>
                    Reinicio de contraseña
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validateEmail} onSubmit={this.handleEmailSubmit} hidden={this.state.showWritePassword}>
                        <p>
                            Por favor ingrese el correo relacionado con su cuenta.
                            Se le enviará un código de reinicio de contraseña a dicho correo.
                        </p>
                        <Form.Group>
                            <Form.Label>
                                Correo:
                            </Form.Label>
                            <Form.Control
                                name="email"
                                id="email"
                                type="text"
                                placeholder="Correo"
                                className="form-control"
                                required
                            />
                        </Form.Group>
                        <Button className="btnSFR" type="submit">
                            Ingresar
                        </Button>
                    </Form>

                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
}