import React, { Component } from 'react';
import axios from 'axios';
import '../Plan.css'
import { Modal, Button, Form, Stack, OverlayTrigger, Tooltip } from "react-bootstrap";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class EditPlanModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            value: "Evaluar, Dirigir y Monitorear"
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    componentDidUpdate(prevProps) {
        if(this.props.plan !== prevProps.plan) {
            console.log(this.props.plan)
            this.setState({
                value: this.props.plan.type
            });
        }
    }

    onChange = e => {
        this.setState({ value: e.target.value })
    }

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
                'pkID': this.props.plan.pkId,
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

        let name;
        let type;
        let subtype;
        let id;
        let authorName;
        let description;
        let status;
        
        if (this.props.plan !== null) {
            name = this.props.plan.name;
            type = this.props.plan.type;
            subtype = this.props.plan.subtype;
            id = this.props.plan.id;
            authorName = this.props.plan.authorName;
            description = this.props.plan.description;
            status = this.props.plan.status;
        }
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
                            <label>Autor:</label>
                            <input name="authorName" id="authorName" type="text" placeholder="Autor" className="form-control" defaultValue={authorName} disabled />
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
                            <label>Tipo:</label>
                            <Form.Select name="type" id="type" onChange={this.onChange} defaultValue={type}>
                                {
                                    (this.props.typesMap === null || typeof this.props.typesMap === 'undefined') ?
                                        <option value={null} disabled>Error cargando Tipos</option> :
                                        this.props.typesMap.get("parents").map((tipos) => {
                                            return <option value={tipos.name}>{tipos.name}</option>
                                        })
                                }
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Subtipo:</label>
                            <Form.Select name="subtype" id="subtype" defaultValue={subtype}>
                                {
                                    (this.props.typesMap === null || typeof this.props.typesMap === 'undefined' || typeof this.props.typesMap.get(this.state.value) === 'undefined') ?
                                        <option value={null} disabled>Error cargando Subtipos</option> :
                                        this.props.typesMap.get(this.state.value).map((tipos) => {
                                            return <option value={tipos.name}>{tipos.name}</option>
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
                            <textarea name="description" id="description" type="text" placeholder="Descripción" className="form-control" style={{ height: '150px' }} defaultValue={description} />
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