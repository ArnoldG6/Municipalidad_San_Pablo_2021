import React, { Component } from 'react';
import axios from 'axios';
import '../Plan.css'
import { Modal, Button, Form, Stack, OverlayTrigger, Tooltip } from "react-bootstrap";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class EditPlanModal extends Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    //closeModal() { }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: process.env.REACT_APP_API_URL + `/PlanManager/Edit`,
            method: 'PUT',
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
                'description': event.target.description.value,
                'riskList': this.props.riskList,
                'entryDate': this.props.entryDate,
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
        let name = this.props.name;
        let typePlan = "";
        switch (this.props.type) {
            case 'PROYECTO':
                typePlan = 'Proyecto';
                break;
            case 'PROCESO':
                typePlan = 'Proceso';
                break;
            case 'Proyecto':
                typePlan = 'Proyecto';
                break;
            case 'Proceso':
                typePlan = 'Proceso';
                break;
            default:
                typePlan = 'unknown';
                break;
        }
        let id = this.props.id;
        let authorName = this.props.authorName;
        let description = this.props.description;
        let status = this.props.status;
        return (
            <Modal show={render} onHide={closeModal} >
                <Modal.Header closeButton>
                    Nuevo Item
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label>Nombre:</label>
                            <input name="name" id="name" type="text" placeholder="Nombre" className="form-control" defaultValue={name} required />
                        </div>
                        <div className="form-group">
                            <label>ID:</label>
                            <input name="id" id="id " type="text" className="form-control" placeholder="ID" defaultValue={id} disabled />
                        </div>
                        <div className="form-group">
                            <label>Estado:</label>
                            <Form.Select name="status" id="status" defaultValue={status}>

                                <option value="Activo">Activo</option>
                                <option value="Inactivo">Inactivo</option>
                                <option value="Completo">Completo</option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Autor:</label>
                            <input name="authorName" id="authorName" type="text" placeholder="Autor" className="form-control" defaultValue={authorName} required />
                        </div>
                        <div className="form-group">
                            <label>Tipo:</label>
                            <Form.Select name="type" id="type" defaultValue={typePlan}>

                                <option value="Proceso">Proceso</option>
                                <option value="Proyecto">Proyecto</option>
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
                            <textarea name="description" id="description" type="text" placeholder="Descripción" className="form-control" defaultValue={description} />
                        </div>
                        <Button className='btn-sfr' type="submit" id="submit-button-new-item">
                            Guardar
                        </Button>
                    </Form>
                </Modal.Body>
                <ToastContainer />
            </Modal>
        );
    }
};
export default EditPlanModal;