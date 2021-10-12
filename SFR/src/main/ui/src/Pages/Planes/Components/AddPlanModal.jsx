import React, { Component } from 'react';
import axios from 'axios';
import { Modal, Button, Form } from "react-bootstrap";

class AddPlanModal extends Component {
    constructor(props) {
        super(props);
        //this.state = {
        //    plan: { nombre: "", id: "", estado: "", autor: "" }
        //};
        this.handleSubmit = this.handleSubmit.bind(this)
    }

    handleSubmit = (event) => {
        event.preventDefault();

        let options = {
            url: `http://localhost:8080/sfr/API/Plans`,
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
                console.log(response.status);
            });

    }

    render() {
        let render = this.props.show
        let closeModal = this.props.closeModal
        return (
            <Modal show={render} onHide={closeModal} >
                <Modal.Header>
                    Nuevo Item
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label>Nombre: &nbsp;&nbsp;</label>
                            <input name="name" id="name" type="text" placeholder="Nombre" className="form-control" />
                        </div>
                        <div className="form-group">
                            <label>ID:</label>
                            <input name="id" id="id " type="text" className="form-control" placeholder="ID" />
                        </div>
                        <div className="form-group">
                            <label>Estado: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <input name="status" id="status" type="text" placeholder="Estado" className="form-control" />
                        </div>
                        <div className="form-group">
                            <label>Autor: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <input name="authorName" id="authorName" type="text" placeholder="Autor" className="form-control" />
                        </div>
                        <div className="form-group">
                            <label>Tipo: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <Form.Select name="type" id="type">
                                <option value="" defaultValue disabled hidden>Seleccione un tipo</option>
                                <option value="Proceso">Proceso</option>
                                <option value="Proyecto">Proyecto</option>
                            </Form.Select>
                        </div>
                        <div className="form-group">
                            <label>Descripción: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <textarea name="description" id="description" type="text" placeholder="Descripción" className="form-control" />
                        </div>
                        <Button variant="primary" type="submit">
                            Guardar
                        </Button>

                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeModal}>
                        Cerrar
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
};
export default AddPlanModal;
