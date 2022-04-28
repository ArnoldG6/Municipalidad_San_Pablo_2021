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
            email: "",
            hideWritePassword: false,
            validateEmail: false,
            hideCodeInput: true,
            validateCode: false
        };
        this.closeModal = this.closeModal.bind(this);
        this.handleEmailSubmit = this.handleEmailSubmit.bind(this);
        this.handleCodeSubmit = this.handleCodeSubmit.bind(this);
    }

    closeModal() {
        this.setState({
            hideWritePassword: false,
            validateEmail: false
        }, () => {
            this.props.closeModal();
        });

    }

    handleEmailSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
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
                    this.setState({
                        email: event.target.email.value,
                        hideWritePassword: true,
                        hideCodeInput: false
                    })
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
        this.setState({
            validateEmail: true
        })

    }

    handleCodeSubmit() {

    }

    render() {
        return (
            <Modal show={this.props.show} onHide={this.closeModal} id="resetPasswordModal" >
                <Modal.Header>
                    Reinicio de contraseña
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validateEmail} onSubmit={this.handleEmailSubmit} hidden={this.state.hideWritePassword}>
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
                                type="email"
                                placeholder="Correo"
                                className="form-control"
                                required
                            />
                            <Form.Control.Feedback type="invalid">
                                Por favor ingrese un dato valido.
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Button className="btnSFR" type="submit">
                            Ingresar
                        </Button>
                    </Form>

                    <Form noValidate validated={this.state.validateCode} onSubmit={this.handleCodeSubmit} hidden={this.state.hideCodeInput}>
                        <p>
                            Por favor ingrese el código enviado a su correo electrónico y su nueva contraseña.
                        </p>
                        <Form.Group>
                            <Form.Label>
                                Código de verificación:
                            </Form.Label>
                            <Form.Control
                                name="resetCode"
                                id="resetCode"
                                type="text"
                                placeholder="Código de verificación"
                                className="form-control"
                                required
                            />
                            <Form.Control.Feedback type="invalid">
                                Por favor ingrese un dato valido.
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>
                                Contraseña nueva:
                            </Form.Label>
                            <Form.Control
                                name="password"
                                id="password"
                                type="password"
                                placeholder="Contraseña"
                                className="form-control"
                                required
                            />
                            <Form.Control.Feedback type="invalid">
                                Por favor ingrese un dato valido.
                            </Form.Control.Feedback>
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