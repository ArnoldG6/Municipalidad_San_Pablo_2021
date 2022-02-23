import React, { Component } from 'react';
import axios from 'axios';
import '../Planes.css'
import { Modal, Button, Form, OverlayTrigger, Tooltip, Stack } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class AddPlanModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: "1"
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: process.env.REACT_APP_API_URL + `/PlanManager/Insert`,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'name': event.target.name.value,
                'id': event.target.id.value,
                'status': event.target.status.value,
                'authorName': event.target.authorName.value,
                'type': event.target.type.value,
                'description': event.target.description.value
            }
        }

        axios(options)
            .then(response => {
                this.props.updatePlanes("add-success");
                this.props.closeModal();
            }).catch(error => {
                toast.error("ID del plan ya se encuentra registrado en el sistema.", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 5000
                });
            });

    }

    render() {
        let render = this.props.show;
        let closeModal = this.props.closeModal;
        return (
            <Modal show={render} onHide={() => {this.setState({value:"1"});closeModal()}} >
                <Modal.Header closeButton>
                    Ingrese los datos para el nuevo Plan
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label>Nombre:</label>
                            <input name="name" id="name" type="text" placeholder="Nombre" className="form-control" required />
                        </div>
                        <div className="form-group">
                            <label>Autor:</label>
                            <input name="authorName" id="authorName" type="text" placeholder="Autor" className="form-control" disabled />
                        </div>
                        <div className="form-group">
                            <label>Estado:</label>
                            <Form.Select name="status" id="status">
                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                                <option value="Completo">Completo</option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Tipo:</label>
                            <Form.Select name="type" id="type" onChange={this.onChange}>
                                {
                                    (this.props.typesMap === null || typeof this.props.typesMap === 'undefined') ?
                                        <option value={null} disabled>Error cargando Tipos</option> :
                                        this.props.typesMap.get("parents").map((tipos) => {
                                            return <option value={tipos.id}>{tipos.name}</option>
                                        })
                                }
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Subtipo:</label>
                            <Form.Select name="subtype" id="subtype">
                                {
                                    (this.props.typesMap === null || typeof this.props.typesMap === 'undefined' || typeof this.props.typesMap.get(this.state.value) === 'undefined') ?
                                        <option value={null} disabled>Error cargando Subtipos</option> :
                                        this.props.typesMap.get(this.state.value).map((tipos) => {
                                            return <option value={tipos.id}>{tipos.name}</option>
                                        })
                                }
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <Stack direction="horizontal" gap={3}>
                                <label>Descripción:</label>
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
                            <textarea name="description" id="description" type="text" placeholder="Descripción" className="form-control" style={{ height: '150px' }} />
                        </div>
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
export default AddPlanModal;