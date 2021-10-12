import React, { Component } from 'react';
import { Modal, Button, Form } from "react-bootstrap";

class AddPlanModal extends Component {
    constructor(props) {
        super(props);
        //this.state = {
        //    plan: { nombre: "", id: "", estado: "", autor: "" }
        //};
        this.handleAddPlan = this.handleAddPlan.bind(this)
    }

    handleAddPlan() {
        //let newPlan = this.state.plan
        //this.setState({plan: newPlan});
        //let newList = this.state.planes
        //newList.push(this.state.plan);
        //this.setState({ planes: newList });
    };


    render() {
        let render = this.props.show
        let closeModal = this.props.closeModal
        return (
            <Modal show={render} onHide={closeModal} >
                <Modal.Header>
                    Nuevo Item
                </Modal.Header>
                <Modal.Body>
                    <form>
                        <div class="form-group">
                            <label>Nombre: &nbsp;&nbsp;</label>
                            <input name="name" id="name" type="text" placeholder="Nombre" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>ID:</label>
                            <input name="id" id="id " type="text" class="form-control" placeholder="ID" />
                        </div>
                        <div class="form-group">
                            <label>Estado: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <input name="status" id="status" type="text" placeholder="Estado" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>Autor: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <input name="authorName" id="authorName" type="text" placeholder="Autor" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>Tipo: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <Form.Select name = "type" id = "type">
                                <option value="" selected disabled hidden>Seleccione un tipo</option>
                                <option value="1">Proceso</option>
                                <option value="2">Proyecto</option>
                            </Form.Select>
                        </div>
                        <div class="form-group">
                            <label>Descripción: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <textarea name="description" id="description" type="text" placeholder="Descripción" class="form-control" />
                        </div>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={this.handleAddPlan}>
                        Guardar
                    </Button>
                    <Button variant="secondary" onClick={closeModal}>
                        Cerrar
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
};
export default AddPlanModal;
