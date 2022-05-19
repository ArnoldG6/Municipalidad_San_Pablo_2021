import React, { Component } from 'react';
import axios from 'axios';
import '../Plan.css'
import { Modal, Button, Form, OverlayTrigger, Tooltip, Stack } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Cookies from 'universal-cookie';
const cookies = new Cookies();

export default class AddCommentModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            validated: false
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.setValidated = this.setValidated.bind(this);
        // this.getID = this.getID.bind(this);
    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

    handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        else {
            event.preventDefault();
            let options = {
                url: process.env.REACT_APP_SFR_API_URL + `/PlanManager/Insert/Comment`,
                method: 'POST',
                header: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                data: {
                    'userID': cookies.get('username', { path: process.env.REACT_APP_AUTH }),
                    'comentario': event.target.comentario.value,
                    'url': event.target.url.value,
                    'planID': this.props.plan.pkID
                }
            }

            axios(options)
                .then(response => {
                    toast.success("El comentario fue añadido correctamente!", {
                        position: toast.POSITION.TOP_RIGHT,
                        pauseOnHover: true,
                        theme: 'colored',
                        autoClose: 5000
                    });
                    this.setState({
                        validated: false
                    })
                    this.props.closeModal();
                    this.props.refreshPage();
                })
                .catch(error => {
                    var msj = "";
                    if (error.response) {
                        //Server responded with an error
                        switch (error.response.status) {
                            case 400:
                                msj = "Hubo un problema insertando el Comentario solicitado.";
                                break;
                            case 401:
                                msj = "Este usuario no cuenta con permisos para insertar Comentarios a este Plan.";
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
        this.setValidated(true);
    }

    setValidated(value) {
        this.setState({ validated: value });
    }

    render() {
        let render = this.props.show;
        let closeModal = this.props.closeModal;
        return (
            <Modal show={render} onHide={() => { this.setState({ value: "Evaluar, Dirigir y Monitorear" }); closeModal() }} >
                <Modal.Header closeButton>
                    Ingrese el nuevo comentario
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit}>
                        <Form.Group>
                            <div className="form-group">
                                <label>Autor:</label>
                                <input name="authorName" id="authorName" type="text" className="form-control" disabled defaultValue={cookies.get('full_name', { path: process.env.REACT_APP_AUTH })} />
                            </div>
                        </Form.Group>
                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Comentario:</label>
                                <OverlayTrigger

                                    delay={{ hide: 450, show: 300 }}
                                    overlay={(props) => (
                                        <Tooltip {...props}>
                                            {process.env.REACT_APP_PLANES_HELP_DESC}
                                        </Tooltip>
                                    )}
                                    placement="bottom"
                                >
                                    <h5 className='ms-auto mt-1'>
                                        <i className="bi bi-info-circle"></i>
                                    </h5>
                                </OverlayTrigger>
                            </Stack>
                            <textarea name="comentario" id="comentario" type="text" placeholder="Comentario" className="form-control" style={{ height: '150px' }} required />
                        </div>
                        <Form.Group>
                            <div className="form-group">
                                <Form.Label>URL:</Form.Label>
                                <Form.Control
                                    name="url"
                                    id="url"
                                    type="text"
                                    placeholder="url"
                                    className="form-control"
                                />
                            </div>
                        </Form.Group>
                        <div className='text-center'>
                            <Button className='btn-sfr' type="submit">
                                Guardar
                            </Button>
                        </div>
                    </Form>
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
};