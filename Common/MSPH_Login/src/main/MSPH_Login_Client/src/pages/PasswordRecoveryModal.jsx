import React, { Component } from 'react';
import { Form, Modal, Button } from 'react-bootstrap';
import '../css/Login.css';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { sha256 } from 'js-sha256';

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
        this.validatePassword = this.validatePassword.bind(this);
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

    closeModal(success) {
        if (success) {
            toast.success("La contraseña ha sido cambiada exitosamente!", {
                position: toast.POSITION.TOP_RIGHT,
                pauseOnHover: true,
                theme: 'colored',
                autoClose: 5000
            });
        }
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
            var email = event.target.email.value;
            let options = {
                url: process.env.REACT_APP_AUTH_API_PATH + "/PasswordReset",
                method: "POST",
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    'userEmail': email
                }
            }
            axios(options)
                .then(response => {
                    this.setState({
                        email: email,
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

    handleCodeSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false || this.validatePassword(event.target.password.value) === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            event.preventDefault();
            let options = {
                url: process.env.REACT_APP_AUTH_API_PATH + "/ValidateResetCode",
                method: "POST",
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    'userEmail': this.state.email,
                    'validationCode': event.target.resetCode.value,
                    'newPassword': sha256(event.target.password.value)
                }
            }
            axios(options)
                .then(response => {
                    this.closeModal(true);
                })
                .catch(error => {
                    var msj = "";
                    if (error.response) {
                        //Server responded with an error
                        switch (error.response.status) {
                            case 400:
                                msj = "No se encontró un usuario con el correo indicado.";
                                break;
                            case 401:
                                msj = "El código ingresado por el usuario es incorrecto.";
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
        //this.setState({
        //    validateCode: true
        //})
    }

    render() {
        return (
            <Modal show={this.props.show} onHide={() => { this.closeModal(false) }} id="resetPasswordModal" >
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
                            La contraseña debe ser de al menos 8 caracteres y contener al menos un número y una letra mayúscula.
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